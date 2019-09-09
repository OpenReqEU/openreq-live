package eu.openreq.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import eu.openreq.Util.Utils;
import eu.openreq.api.internal.dto.*;
import eu.openreq.component.ScheduledBatchJob;
import eu.openreq.dbo.*;
import eu.openreq.dbo.UserRequirementCommentDbo.Sentiment;
import eu.openreq.dbo.RequirementUpdateDbo.ActionType;
import eu.openreq.remote.request.dto.stakeholderrecommendation.RecommendDto;
import eu.openreq.remote.request.dto.stakeholderrecommendation.RecommendRequestProjectDto;
import eu.openreq.remote.request.dto.stakeholderrecommendation.RecommendRequestRequirementDto;
import eu.openreq.remote.request.dto.stakeholderrecommendation.RecommendRequestUserDto;
import eu.openreq.remote.response.dto.stakeholderrecommendation.RecommendResponse;
import eu.openreq.repository.*;
import eu.openreq.service.DelegateUserRequirementVoteService;
import eu.openreq.service.EmailService;
import eu.openreq.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import eu.openreq.dbo.RequirementDbo.Status;
import eu.openreq.exception.DboConstraintException;
import eu.openreq.remote.dto.RemoteRequirementDto;
import eu.openreq.view.ImportedRequirementsBean;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import static java.util.Comparator.comparing;

@Controller
public class RequirementController {

	private static final Logger logger = LoggerFactory.getLogger(RequirementController.class);

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private RequirementRepository requirementRepository;

	@Autowired
	private DelegateUserRequirementVoteService delegateUserRequirementVoteService;

    @Autowired
    private UserRequirementCommentRepository requirementCommentRepository;

    @Autowired
	private ReleaseRepository releaseRepository;

	@Autowired
	private SkillRepository skillRepository;

	@Autowired
	private BasicRatingRepository basicRatingRepository;

	@Autowired
	private RatingAttributeRepository ratingAttributeRepository;

	@Autowired
	private StakeholderRatingAttributeRepository stakeholderRatingAttributeRepository;

	@Autowired
	private UserRequirementAttributeVoteRepository userRequirementAttributeVoteRepository;

    @Autowired
    private BotUserStakeholderAttributeVoteRepository botUserStakeholderAttributeVoteRepository;

    @Autowired
	private AnonymousUserRequirementAttributeVoteRepository anonymousUserRequirementAttributeVoteRepository;

    @Autowired
    private AnonymousUserStakeholderAttributeVoteRepository anonymousUserStakeholderAttributeVoteRepository;

    @Autowired
    private UserStakeholderAttributeVoteRepository userStakeholderAttributeVoteRepository;

    @Autowired
	private UserRepository userRepository;

    @Autowired
    private AnonymousUserRepository anonymousUserRepository;

    @Autowired
    private RequirementStakeholderAssignmentRepository requirementStakeholderAssignmentRepository;

    @Autowired
    private RequirementAnonymousStakeholderAssignmentRepository requirementAnonymousStakeholderAssignmentRepository;

    @Autowired
    private DelegateUserRequirementVoteRepository delegateUserRequirementVoteRepository;

    @Autowired
    private HideStakeholderAssignmentRepository hideStakeholderAssignmentRepository;

    @Autowired
    private HideAnonymousStakeholderAssignmentRepository hideAnonymousStakeholderAssignmentRepository;

    @Autowired
    private UserRequirementVoteActivityRepository userRequirementVoteActivityRepository;

    @Autowired
    private UserRatingConflictRepository userRatingConflictRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    @ResponseBody
	@GetMapping("/project/{projectID}/requirement/list.json")
    public List<Map<String, Object>> requirementListJson(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
    		@CookieValue(value="nameOfAnonymousUser", defaultValue="") String nameOfAnonymousUser,
    		Authentication authentication)
	{
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }

		long currentUserId = 0;
		boolean isAuthenticated = ((authentication != null) && authentication.isAuthenticated());
		if (isAuthenticated) {
			User userInfo = (User) authentication.getPrincipal();
			currentUserId = userRepository.findOneByUsername(userInfo.getUsername()).getId();
		}

		List<RequirementDbo> requirements = new ArrayList<>(project.getRequirements());
		requirements.sort((r1, r2) -> {
            int result = r1.getStatus().compareTo(r2.getStatus());
            if (result != 0) {
                return result;
            }
            return (new Long(r1.getId())).compareTo(new Long(r2.getId()));
        });

        //requirements = new ArrayList<>(requirements);
        //requirements.sort(Comparator.comparing(RequirementDbo::getUtilityValue));

		List<RequirementDbo> newRequirements = requirements
			.stream()
			.filter(r -> r.getStatus() != Status.RECOMMENDED)
			.filter(r -> r.isVisible())
			.collect(Collectors.toList());
		List<Map<String, Object>> result = new ArrayList<>();

		for (RequirementDbo requirement : newRequirements) {
			Map<Long, Integer> yourRating = new HashMap<>();
			Map<String, List<Map<String, Object>>> anonymousUserVotes = new HashMap<>();

			for (AnonymousUserRequirementAttributeVoteDbo vote : requirement.getAnonymousUserRequirementAttributeVotes()) {
				if (!isAuthenticated && vote.getNameOfAnonymousUser().equals(nameOfAnonymousUser)) {
					yourRating.put(vote.getRatingAttribute().getId(), vote.getValue());
				}

				List<Map<String, Object>> votesData = anonymousUserVotes.get(vote.getNameOfAnonymousUser());
				if (votesData == null) {
					votesData = new ArrayList<>();
				}

				Map<String, Object> voteData = new HashMap<>();
				voteData.put("attributeID", vote.getRatingAttribute().getId());
				voteData.put("value", vote.getValue());
				votesData.add(voteData);
				anonymousUserVotes.put(vote.getNameOfAnonymousUser(), votesData);
			}

			Map<Long, List<Map<String, Object>>> userVotes = new HashMap<>();
			for (UserRequirementAttributeVoteDbo vote : requirement.getUserRequirementAttributeVotes()) {
				if (vote.getUser().getId() == currentUserId) {
					yourRating.put(vote.getRatingAttribute().getId(), vote.getValue());
				}

				List<Map<String, Object>> votesData = userVotes.get(vote.getUser().getId());
				if (votesData == null) {
					votesData = new ArrayList<>();
				}

				Map<String, Object> voteData = new HashMap<>();
				voteData.put("attributeID", vote.getRatingAttribute().getId());
				voteData.put("value", vote.getValue());
				voteData.put("userFirstName", vote.getUser().getFirstName());
				voteData.put("userLastName", vote.getUser().getLastName());
				voteData.put("userEmail", vote.getUser().getMailAddress());
				votesData.add(voteData);
				userVotes.put(vote.getUser().getId(), votesData);
			}

            int yourBasicRating = 0;
			Map<Long, Map<String, Object>> basicUserVotes = new HashMap<>();

			for (UserRequirementVoteDbo vote : requirement.getUserRequirementVotes()) {
				if (vote.getUser().getId() == currentUserId) {
                    yourBasicRating = vote.getValue();
				}

				Map<String, Object> voteData = new HashMap<>();
				voteData.put("value", vote.getValue());
				voteData.put("userFirstName", vote.getUser().getFirstName());
				voteData.put("userLastName", vote.getUser().getLastName());
				voteData.put("userEmail", vote.getUser().getMailAddress());
                basicUserVotes.put(vote.getUser().getId(), voteData);
			}

            List<Map<String, Object>> messageList = new ArrayList<>();
            for (UserRequirementCommentDbo userComment : requirement.getUserComments()) {
                Map<String, Object> messageData = new HashMap<>();
                messageData.put("id", userComment.getId());
                messageData.put("title", userComment.getTitle());
                messageData.put("message", userComment.getText());
                messageData.put("sentiment", userComment.getSentiment());
                messageData.put("assignedDimensions", userComment.getVotedAttributes()
                        .stream()
                        .map(a -> a.getId())
                        .collect(Collectors.toList()));
                messageData.put("createdDate", userComment.getCreatedDate());

                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("userID", userComment.getUser().getId());
                userInfo.put("firstName", userComment.getUser().getFirstName());
                userInfo.put("lastName", userComment.getUser().getLastName());
                userInfo.put("shortFullName", Utils.getShortFullName(userComment.getUser().getFirstName(), userComment.getUser().getLastName(), 10));
                userInfo.put("profileImagePath", userComment.getUser().getProfileImagePath());
                userInfo.put("email", userComment.getUser().getMailAddress());
                messageData.put("userData", userInfo);
                messageList.add(messageData);
            }

            List<ConflictData> basicConflicts = basicRatingConflicts(requirement,
                    ConflictData.CONFLICT_DISTANCE_THRESHOLD_BASIC);
            List<Map<String, Object>> basicConflictsData = new ArrayList<>();
            for (ConflictData conflictData : basicConflicts) {
                basicConflictsData.add(conflictData.toMap());
            }

            List<ConflictData> normalConflicts = normalRatingConflicts(requirement,
                    ConflictData.CONFLICT_DISTANCE_THRESHOLD_NORMAL_AND_ADVANCED);
            List<Map<String, Object>> normalConflictsData = new ArrayList<>();
            for (ConflictData conflictData : normalConflicts) {
                normalConflictsData.add(conflictData.toMap());
            }

            List<ConflictData> advancedConflicts = advancedRatingConflicts(requirement);
            List<Map<String, Object>> advancedConflictsData = new ArrayList<>();
            for (ConflictData conflictData : advancedConflicts) {
                advancedConflictsData.add(conflictData.toMap());
            }

            Map<Long, Map<String, Object>> userVoteForwardDelegations = delegateUserRequirementVoteService.extractUserVoteForwardDelegations(requirement);
			Map<String, Object> requirementData = new HashMap<>();
            String cleanedDescription = Jsoup.clean(requirement.getDescription(), Whitelist.relaxed());

			requirementData.put("id", requirement.getId());
			requirementData.put("projectSpecificRequirementId", requirement.getProjectSpecificRequirementId());
			requirementData.put("title", requirement.getTitle());
			requirementData.put("description", cleanedDescription);
			requirementData.put("importID", requirement.getImportId());
			requirementData.put("status", requirement.getStatus());
			requirementData.put("createdAt", requirement.getCreatedDate().getTime());
			requirementData.put("lastUpdatedAt", requirement.getLastUpdatedDate().getTime());
			requirementData.put("releaseID", (requirement.getRelease() != null) ? requirement.getRelease().getId() : 0);
			requirementData.put("yourBasicRating", yourBasicRating);
			requirementData.put("yourRating", yourRating);
            requirementData.put("yourRatingDelegation", userVoteForwardDelegations.get(currentUserId));
            requirementData.put("ratingForwardDelegations", userVoteForwardDelegations);
            requirementData.put("ratingBackwardDelegations", delegateUserRequirementVoteService.extractUserVoteBackwardDelegations(requirement));
			requirementData.put("userBasicRatings", basicUserVotes);
			requirementData.put("userRatings", userVotes);
			requirementData.put("basicConflicts", basicConflictsData);
			requirementData.put("normalConflicts", normalConflictsData);
			requirementData.put("advancedConflicts", advancedConflictsData);
            requirementData.put("messages", messageList);
            requirementData.put("numberOfComments", requirement.getUserComments().size());
			requirementData.put("numberOfPros", requirement.getUserComments().stream()
                    .filter(c -> c.getSentiment() == Sentiment.PRO)
                    .collect(Collectors.toList()).size());
            requirementData.put("numberOfNeus", requirement.getUserComments().stream()
                    .filter(c -> c.getSentiment() == Sentiment.NEUTRAL)
                    .collect(Collectors.toList()).size());
            requirementData.put("numberOfCons", requirement.getUserComments().stream()
                    .filter(c -> c.getSentiment() == Sentiment.CON)
                    .collect(Collectors.toList()).size());
			requirementData.put("anonymousRatings", anonymousUserVotes);
			requirementData.put("socialPopularity", requirement.getSocialPopularity());

			List<String> skillData = new ArrayList<>();
			for (SkillDbo skill : requirement.getRequirementSkills()) {
				skillData.add(skill.getKeyword());
			}

			requirementData.put("skills", skillData);
			List<String> responsibleStakeholdersData = new ArrayList<>();
			for (RequirementStakeholderAssignment stakeholderAssignment : requirement.getUserStakeholderAssignments()) {
			    UserDbo stakeholder = stakeholderAssignment.getStakeholder();
				responsibleStakeholdersData.add(stakeholder.getFirstName() + " " + stakeholder.getLastName());
			}

            for (RequirementAnonymousStakeholderAssignment stakeholderAssignment : requirement.getAnonymousUserStakeholderAssignments()) {
                AnonymousUserDbo stakeholder = stakeholderAssignment.getAnonymousStakeholder();
                responsibleStakeholdersData.add(stakeholder.getFullName());
            }

			requirementData.put("responsibleStakeholders", responsibleStakeholdersData);
			result.add(requirementData);
		}
		return result;
    }

	@ResponseBody
	@GetMapping("/project/{projectID}/requirement/{requirementID}/message/list.json")
	public Map<String, Object> requirementMessageListJson(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
			@PathVariable(value="requirementID") Long requirementID,
            Authentication authentication)
	{
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        Map<String, Object> result = new HashMap<>();

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

		RequirementDbo requirement = requirementRepository.findOne(requirementID);
		if (requirement.getProject().getId() != projectID) {
			result.put("error", true);
			result.put("errorMessage", "The requirement does not belong to the given project!");
			return result;
		}

		List<UserRequirementCommentDbo> userComments = new ArrayList<>(requirement.getUserComments());
		userComments.sort(comparing(UserRequirementCommentDbo::getCreatedDate));
		List<Map<String, Object>> messageList = new ArrayList<>();

		for (UserRequirementCommentDbo userComment : userComments) {
			Map<String, Object> messageData = new HashMap<>();
			messageData.put("id", userComment.getId());
			messageData.put("title", userComment.getTitle());
			messageData.put("message", userComment.getText());
			messageData.put("sentiment", userComment.getSentiment());
			messageData.put("assignedDimensions", userComment.getVotedAttributes()
                    .stream()
                    .map(a -> a.getId())
                    .collect(Collectors.toList()));
			messageData.put("createdDate", userComment.getCreatedDate());

			Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("userID", userComment.getUser().getId());
            userInfo.put("firstName", userComment.getUser().getFirstName());
            userInfo.put("lastName", userComment.getUser().getLastName());
			userInfo.put("shortFullName", Utils.getShortFullName(userComment.getUser().getFirstName(), userComment.getUser().getLastName(), 10));
			userInfo.put("profileImagePath", userComment.getUser().getProfileImagePath());
            userInfo.put("email", userComment.getUser().getMailAddress());
            messageData.put("userData", userInfo);
			messageList.add(messageData);
		}
		result.put("messages", messageList);
		return result;
	}

    @ResponseBody
    @PostMapping("/project/{projectID}/requirement/{requirementID}/message/create.json")
    public Map<String, Object> createRequirementMessageJson(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
            @PathVariable(value="requirementID") Long requirementID,
            @RequestBody RequirementMessageDto requirementMessageDto,
            Authentication authentication)
    {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        Map<String, Object> result = new HashMap<>();

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

        RequirementDbo requirement = requirementRepository.findOne(requirementID);
        if (requirement.getProject().getId() != projectID) {
            result.put("error", true);
            result.put("errorMessage", "The requirement does not belong to the given project!");
            return result;
        }

        UserRequirementCommentDbo.Sentiment sentiment = null;
        if (requirementMessageDto.getSentiment() == null) {
            sentiment = null;
        } else if (requirementMessageDto.getSentiment() == 0) {
            sentiment = Sentiment.NEUTRAL;
		} else if (requirementMessageDto.getSentiment() == 1) {
			sentiment = Sentiment.PRO;
		} else if (requirementMessageDto.getSentiment() == 2) {
			sentiment = Sentiment.CON;
		}

        UserRequirementCommentDbo userComment = new UserRequirementCommentDbo(requirementMessageDto.getTitle(),
                requirementMessageDto.getMessage(), sentiment, requirement, currentUser);
        for (Long assignedAttributeID : requirementMessageDto.getAssignedDimensions()) {
            List<RatingAttributeDbo> attributes = project.getRatingAttributes()
                    .stream()
                    .filter(a -> a.getId() == assignedAttributeID)
                    .collect(Collectors.toList());

            if (attributes.size() == 0) {
                continue;
            }

            RatingAttributeDbo ratingAttribute = attributes.get(0);
            userComment.getVotedAttributes().add(ratingAttribute);
        }

        for (RatingAttributeDbo votedAttribute : userComment.getVotedAttributes()) {
            logVoteActivity(requirement, ProjectSettingsDbo.EvaluationMode.ADVANCED,
                    UserRequirementVoteActivityDbo.ActionType.CREATED, userComment.toString(),
                    votedAttribute.getId(), currentUser);
        }

        requirementCommentRepository.save(userComment);
        List<ConflictData> conflicts = advancedRatingConflicts(requirement);
        storeRatingConflicts(conflicts, UserRatingConflictDbo.RatingType.ADVANCED, new Date());

        Map<String, Object> userData = new HashMap<>();
        userData.put("userID", currentUser.getId());
        userData.put("firstName", currentUser.getFirstName());
        userData.put("lastName", currentUser.getLastName());
		userData.put("shortFullName", Utils.getShortFullName(currentUser.getFirstName(), currentUser.getLastName(), 10));
        userData.put("profileImagePath", currentUser.getProfileImagePath());
        userData.put("email", currentUser.getMailAddress());

        result.put("error", false);
        result.put("id", userComment.getId());
        result.put("userData", userData);
		result.put("title", userComment.getTitle());
		result.put("message", userComment.getText());
		result.put("sentiment", userComment.getSentiment());
		result.put("assignedDimensions", requirementMessageDto.getAssignedDimensions());
		result.put("createdDate", userComment.getCreatedDate());
        result.put("conflicts", conflicts.stream().map(c -> c.toMap()).collect(Collectors.toList()));
        return result;
    }

    @ResponseBody
    @PostMapping("/project/{projectID}/requirement/{requirementID}/message/{messageID}/delete.json")
    public Map<String, Object> deleteRequirementMessageJson(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
            @PathVariable(value="messageID") Long messageID,
            @PathVariable(value="requirementID") Long requirementID,
            Authentication authentication)
    {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        Map<String, Object> result = new HashMap<>();

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

        RequirementDbo requirement = requirementRepository.findOne(requirementID);
        if (requirement.getProject().getId() != projectID) {
            result.put("error", true);
            result.put("errorMessage", "The requirement does not belong to the given project!");
            return result;
        }

        UserRequirementCommentDbo userComment = requirementCommentRepository.findOne(messageID);
        if (userComment == null) {
            result.put("error", true);
            result.put("errorMessage", "The comment does not exist (any more)!");
            return result;
        }

        if (userComment.getRequirement().getId() != requirement.getId()) {
            result.put("error", true);
            result.put("errorMessage", "This comment does not belong to the given requirement!");
            return result;
        }

        if (userComment.getUser().getId() != currentUser.getId()) {
            result.put("error", true);
            result.put("errorMessage", "This is not your comment!");
            return result;
        }

        for (RatingAttributeDbo votedAttribute : userComment.getVotedAttributes()) {
            logVoteActivity(requirement, ProjectSettingsDbo.EvaluationMode.ADVANCED,
                    UserRequirementVoteActivityDbo.ActionType.DELETED, userComment.toString(),
                    votedAttribute.getId(), currentUser);
        }
        userComment.setVotedAttributes(new HashSet<>());
        requirementCommentRepository.save(userComment);
        userComment = requirementCommentRepository.findOne(messageID);
        requirementCommentRepository.delete(userComment);
        requirement = requirementRepository.findOne(requirementID);
        List<ConflictData> conflicts = advancedRatingConflicts(requirement);
        storeRatingConflicts(conflicts, UserRatingConflictDbo.RatingType.ADVANCED, new Date());
        result.put("error", false);
        result.put("conflicts", conflicts.stream().map(c -> c.toMap()).collect(Collectors.toList()));
        return result;
    }

    @ResponseBody
	@PostMapping("/project/{projectID}/requirement/{requirementID}/stakeholder/{userID}/hide.json")
	public Map<String, Object> hideStakeholderAssignement(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
            @PathVariable(value="requirementID") Long requirementID,
            @PathVariable(value="userID") Long userID,
            Authentication authentication)
	{
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        RequirementDbo requirement = requirementRepository.findOne(requirementID);
        Map<String, Object> result = new HashMap<>();
        UserDbo hiddenStakeholder = userRepository.findOne(userID);

        if (!project.hasCreatorOrParticipantRights(currentUser) || !project.hasCreatorOrParticipantRights(hiddenStakeholder)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

        HideStakeholderAssignmentDbo hideStakeholderAssignment = hideStakeholderAssignmentRepository
                .findOneByUserIdAndHiddenStakeholderUserIdAndRequirementId(
                        currentUser.getId(), userID, requirementID);
        if (hideStakeholderAssignment == null) {
            hideStakeholderAssignment = new HideStakeholderAssignmentDbo(requirement, currentUser, hiddenStakeholder);
            hideStakeholderAssignmentRepository.save(hideStakeholderAssignment);
        }

        result.put("error", false);
		return result;
    }

    @ResponseBody
	@PostMapping("/project/{projectID}/requirement/{requirementID}/stakeholder/{userID}/unhide.json")
	public Map<String, Object> unhideStakeholderAssignement(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
            @PathVariable(value="requirementID") Long requirementID,
            @PathVariable(value="userID") Long userID,
            Authentication authentication)
	{
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        RequirementDbo requirement = requirementRepository.findOne(requirementID);
        Map<String, Object> result = new HashMap<>();
        UserDbo hiddenStakeholder = userRepository.findOne(userID);

        if (!project.hasCreatorOrParticipantRights(currentUser) || !project.hasCreatorOrParticipantRights(hiddenStakeholder)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

        HideStakeholderAssignmentDbo hideStakeholderAssignment = hideStakeholderAssignmentRepository
                .findOneByUserIdAndHiddenStakeholderUserIdAndRequirementId(
                        currentUser.getId(), userID, requirementID);
        if (hideStakeholderAssignment == null) {
            result.put("error", false);
            result.put("errorMessage", "Cannot unhide the stakeholder since he/she is already visible!");
            return result;
        }

        hideStakeholderAssignmentRepository.delete(hideStakeholderAssignment);
        result.put("error", false);
		return result;
    }

    @ResponseBody
	@PostMapping("/project/{projectID}/requirement/{requirementID}/stakeholder/anonymous/hide.json")
	public Map<String, Object> hideAnonymousStakeholderAssignement(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
            @PathVariable(value="requirementID") Long requirementID,
            @RequestParam(value="anonymousUserID", required=true) Long anonymousUserID,
            Authentication authentication)
	{
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        RequirementDbo requirement = requirementRepository.findOne(requirementID);
        Map<String, Object> result = new HashMap<>();
        AnonymousUserDbo hiddenAnonymousStakeholder = anonymousUserRepository.findOne(anonymousUserID);

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

        HideAnonymousStakeholderAssignmentDbo hideAnonymousStakeholderAssignment = hideAnonymousStakeholderAssignmentRepository
                .findOneByUserIdAndHiddenStakeholderUserIdAndRequirementId(
                        currentUser.getId(), hiddenAnonymousStakeholder.getId(), requirementID);
        if (hideAnonymousStakeholderAssignment == null) {
            hideAnonymousStakeholderAssignment = new HideAnonymousStakeholderAssignmentDbo(requirement, currentUser, hiddenAnonymousStakeholder);
            hideAnonymousStakeholderAssignmentRepository.save(hideAnonymousStakeholderAssignment);
        }

        result.put("error", false);
		return result;
    }

    @ResponseBody
	@PostMapping("/project/{projectID}/requirement/{requirementID}/stakeholder/anonymous/unhide.json")
	public Map<String, Object> unhideAnonymousStakeholderAssignement(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
            @PathVariable(value="requirementID") Long requirementID,
            @RequestParam(value="anonymousUserID", required=true) Long anonymousUserID,
            Authentication authentication)
	{
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        RequirementDbo requirement = requirementRepository.findOne(requirementID);
        Map<String, Object> result = new HashMap<>();

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

        HideAnonymousStakeholderAssignmentDbo hideAnonymousStakeholderAssignment = hideAnonymousStakeholderAssignmentRepository
                .findOneByUserIdAndHiddenStakeholderUserIdAndRequirementId(
                        currentUser.getId(), anonymousUserID, requirementID);
        if (hideAnonymousStakeholderAssignment == null) {
            result.put("error", false);
            result.put("errorMessage", "Cannot unhide the stakeholder since he/she is already visible!");
            return result;
        }

        hideAnonymousStakeholderAssignmentRepository.delete(hideAnonymousStakeholderAssignment);
        result.put("error", false);
		return result;
    }

    @ResponseBody
	@PostMapping("/project/{projectID}/requirement/delete.json")
	public Map<String, Object> deleteRequirement(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
			@RequestParam(value="requirementID[]", required=true) Long[] requirementIDs,
            Authentication authentication)
	{
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        Map<String, Object> result = new HashMap<>();

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

        for (long requirementID : requirementIDs) {
			RequirementDbo requirement = requirementRepository.findOne(requirementID);
			RequirementDbo copiedRequirement = requirement.clone();
			if (requirement.getProject().getId() != projectID) {
			    continue;
            }
			requirement.setVisible(false);
            requirement.logRequirementUpdate(ActionType.DELETED, copiedRequirement, currentUser);
            requirementRepository.save(requirement);
		}

		result.put("error", false);
		return result;
    }

	@ResponseBody
	@GetMapping("/project/{projectID}/requirement/rating/attribute/list.json")
    public List<Map<String, Object>> ratingAttributeListJson(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
            Authentication authentication)
    {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }

		List<RatingAttributeDbo> ratingAttributes = new ArrayList<>(project.getRatingAttributes());
		ratingAttributes.sort(comparing(RatingAttributeDbo::getId));

		List<Map<String, Object>> ratingAttributeDataList = new ArrayList<>();
		for (RatingAttributeDbo ratingAttribute : ratingAttributes) {
			Map<String, Object> ratingAttributeData = new HashMap<>();
			ratingAttributeData.put("id", ratingAttribute.getId());
			ratingAttributeData.put("name", ratingAttribute.getName());
			ratingAttributeData.put("description", ratingAttribute.getDescription());
			ratingAttributeData.put("iconName", ratingAttribute.getIconName());
			ratingAttributeData.put("minValue", ratingAttribute.getMinValue());
			ratingAttributeData.put("maxValue", ratingAttribute.getMaxValue());
			ratingAttributeData.put("weight", ratingAttribute.getWeight());
			ratingAttributeData.put("isReverse", ratingAttribute.isReverse());
			ratingAttributeDataList.add(ratingAttributeData);
		}
		return ratingAttributeDataList;
    }

	@ResponseBody
	@PostMapping("/project/{projectID}/requirement/rating/attribute/update.json")
	public Map<String, Object> updateRatingAttributeJson(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
			@RequestBody List<RatingAttributeDto> ratingAttributeDtos, Authentication authentication)
	{
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        Map<String, Object> result = new HashMap<>();

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

		if (project.getProjectSettings().isReadOnly()) {
            result.put("error", false);
            result.put("errorMessage", "You are not allowed to change dimensions! This is a read-only project.");
            return result;
        }

		for (RatingAttributeDto ratingAttributeDto : ratingAttributeDtos) {
			RatingAttributeDbo ratingAttribute = ratingAttributeRepository.findOne(ratingAttributeDto.getId());
			if ((ratingAttribute == null) || (ratingAttribute.getProject().getId() != project.getId())) {
				continue;
			}

			ratingAttribute.setWeight(ratingAttributeDto.getWeight());
			ratingAttributeRepository.save(ratingAttribute);
		}

		result.put("error", false);
		return result;
	}

	@ResponseBody
	@GetMapping("/project/{projectID}/requirement/stakeholder/rating/attribute/list.json")
	public List<Map<String, Object>> stakeholderRatingAttributeListJson(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
            Authentication authentication)
    {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }

		List<StakeholderRatingAttributeDbo> stakeholderRatingAttributes = new ArrayList<>(project.getStakeholderRatingAttributes());
		stakeholderRatingAttributes.sort(comparing(StakeholderRatingAttributeDbo::getId));

		List<Map<String, Object>> stakeholderRatingAttributeDataList = new ArrayList<>();
		for (StakeholderRatingAttributeDbo stakeholderRatingAttribute : stakeholderRatingAttributes) {
			Map<String, Object> stakeholderRatingAttributeData = new HashMap<>();
			stakeholderRatingAttributeData.put("id", stakeholderRatingAttribute.getId());
			stakeholderRatingAttributeData.put("name", stakeholderRatingAttribute.getName());
			stakeholderRatingAttributeData.put("description", stakeholderRatingAttribute.getDescription());
			stakeholderRatingAttributeData.put("iconName", stakeholderRatingAttribute.getIconName());
			stakeholderRatingAttributeData.put("minValue", stakeholderRatingAttribute.getMinValue());
			stakeholderRatingAttributeData.put("maxValue", stakeholderRatingAttribute.getMaxValue());
			stakeholderRatingAttributeData.put("weight", stakeholderRatingAttribute.getWeight());
			stakeholderRatingAttributeDataList.add(stakeholderRatingAttributeData);
		}
		return stakeholderRatingAttributeDataList;
	}

	@ResponseBody
	@PostMapping("/project/{projectID}/requirement/stakeholder/rating/attribute/update.json")
	public Map<String, Object> updateStakeholderRatingAttributeJson(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
            @RequestBody List<RatingAttributeDto> ratingAttributeDtos,
            Authentication authentication)
	{
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        Map<String, Object> result = new HashMap<>();

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

        if (project.getProjectSettings().isReadOnly()) {
            result.put("error", false);
            result.put("errorMessage", "You are not allowed to change dimensions! This is a read-only project.");
            return result;
        }

		for (RatingAttributeDto ratingAttributeDto : ratingAttributeDtos) {
			StakeholderRatingAttributeDbo stakeholderRatingAttribute = stakeholderRatingAttributeRepository.findOne(ratingAttributeDto.getId());
			if (stakeholderRatingAttribute.getProject().getId() != project.getId()) {
				continue;
			}

			// TODO: extend... also call other setters once supported...
			stakeholderRatingAttribute.setWeight(ratingAttributeDto.getWeight());
			stakeholderRatingAttributeRepository.save(stakeholderRatingAttribute);
		}

		result.put("error", false);
		return result;
	}

    @ResponseBody
    @PostMapping("/project/{projectID}/requirement/{requirementID}/stakeholder/vote.json")
    public Map<String, Object> voteJson(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
            @PathVariable(value="requirementID") Long requirementID,
            @RequestBody StakeholderVoteDto stakeholderVoteDto,
            HttpServletResponse response,
            Authentication authentication)
    {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        Map<String, Object> result = new HashMap<>();

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

        RequirementDbo requirement = requirementRepository.findOne(requirementID);
        if (projectID.longValue() != requirement.getProject().getId()) {
            result.put("error", true);
            result.put("errorMessage", "The requirement does not belong to the given project!");
            return result;
        }

        try {
            for (VoteDimensionDto voteDto : stakeholderVoteDto.getAttributeVotes()) {
                StakeholderRatingAttributeDbo ratingAttribute = stakeholderRatingAttributeRepository.findOne(voteDto.getRatingAttributeID());

                if (stakeholderVoteDto.isAnonymousUser()) {
                    AnonymousUserDbo anonymousUser = anonymousUserRepository.findOne(stakeholderVoteDto.getRatedStakeholderID());
                    AnonymousUserStakeholderAttributeVoteDbo vote = anonymousUserStakeholderAttributeVoteRepository
                            .findOneByRatedAnonymousStakeholderIdAndRequirementIdAndRatingAttributeIdAndUserId(
                                    anonymousUser.getId(), requirementID,
                                    ratingAttribute.getId(), currentUser.getId());

                    if (voteDto.getRatingValue() == 0) {
                        if (vote != null) {
                            anonymousUserStakeholderAttributeVoteRepository.delete(vote);
                        }
                        continue;
                    }

                    if (vote == null) {
                        vote = new AnonymousUserStakeholderAttributeVoteDbo(voteDto.getRatingValue(), requirement,
                                ratingAttribute, anonymousUser, currentUser);
                    } else {
                        vote.setValue(voteDto.getRatingValue());
                    }
                    anonymousUserStakeholderAttributeVoteRepository.save(vote);
                    continue;
                }

                UserDbo ratedStakeholder = userRepository.findOne(stakeholderVoteDto.getRatedStakeholderID());
                UserStakeholderAttributeVoteDbo vote = userStakeholderAttributeVoteRepository.findOneByRatedStakeholderIdAndRequirementIdAndRatingAttributeIdAndUserId(
                        stakeholderVoteDto.getRatedStakeholderID(), requirementID,
                        ratingAttribute.getId(), currentUser.getId());

                if (voteDto.getRatingValue() == 0) {
                    if (vote != null) {
                        userStakeholderAttributeVoteRepository.delete(vote);
                    }
                    continue;
                }

                if (vote == null) {
                    vote = new UserStakeholderAttributeVoteDbo(voteDto.getRatingValue(), requirement, ratingAttribute, ratedStakeholder, currentUser);
                } else {
                    vote.setValue(voteDto.getRatingValue());
                }
                userStakeholderAttributeVoteRepository.save(vote);
            }
        } catch (DboConstraintException exception) {
            logger.error("An exception occurred!", exception);
        }
        result.put("error", false);
        return result;
    }

    @ResponseBody
	@PostMapping("/project/{projectID}/requirement/create.json")
	public Map<String, Object> createRequirementJson(
            HttpServletRequest request,
            Authentication authentication,
			@PathVariable(value="projectID") Long projectID,
			@RequestBody List<RequirementDto> requirementDtos)
	{
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        Map<String, Object> result = new HashMap<>();

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

		List<RequirementDbo> requirements = new ArrayList<>();

		for (RequirementDto requirementDto : requirementDtos) {
			project.incrementLastProjectSpecificRequirementId();
            RequirementDbo requirement = new RequirementDbo(project.getLastProjectSpecificRequirementId(),
                    requirementDto.getTitle(), requirementDto.getDescription(), project);

			if (requirementDto.getAssignedReleaseID().longValue() > 0) {
				ReleaseDbo release = releaseRepository.findOne(requirementDto.getAssignedReleaseID());
				if (release.getProject().getId() == projectID) {
				    requirement.setRelease(release);
                } else {
                    String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
                    throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
                }
			} else {
				requirement.setRelease(null);
			}

			requirement.setImportId(requirementDto.getImportID());
			requirement.setStatus((requirementDto.getStatus() != null) ? requirementDto.getStatus() : Status.NEW);
			requirement.setVisible(true);
			requirement.logRequirementUpdate(ActionType.CREATED, null, currentUser);
			requirementRepository.save(requirement);
			requirements.add(requirement);
		}
		projectRepository.save(project);

		result.put("error", false);
		result.put("requirementIDs", requirements.stream().map(requirement -> requirement.getId()).collect(Collectors.toList()));
		return result;
	}

    @ResponseBody
    @GetMapping("/project/{projectID}/requirement/{requirementID}/user/list.json")
    public Map<String, Object> getAssignedUsers(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
            @PathVariable(value="requirementID") Long requirementID,
            Authentication authentication) throws DboConstraintException {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        Map<String, Object> result = new HashMap<>();

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

        RequirementDbo requirement = requirementRepository.findOne(requirementID);

        if (requirement == null) {
            result.put("error", true);
            result.put("errorMessage", "The requirement does not exist!");
            return result;
        }

        if (requirement.getProject().getId() != projectID) {
            result.put("error", true);
            result.put("errorMessage", "The requirement does not belong to the given project!");
            return result;
        }

        List<Map<String, Object>> userInfoList = new ArrayList<>();
        List<RequirementStakeholderAssignment> stakeholderAssignments = new ArrayList<>(requirement.getUserStakeholderAssignments()
                .stream()
                .sorted(comparing(RequirementStakeholderAssignment::getStakeholderId))
                .collect(Collectors.toList()));

        if ((currentUser != null) && !requirement.isStakeholderRecommendationsFetched()) {
            RestTemplate restTemplate = new RestTemplate();
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            Map<String, String> map = new HashMap<>();
            map.put("Content-Type", "application/json");
            headers.setAll(map);

            RecommendRequestProjectDto projectDto = new RecommendRequestProjectDto();
            projectDto.setId(Long.toString(project.getId()));
            RecommendRequestRequirementDto requirementDto = new RecommendRequestRequirementDto();
            RecommendRequestUserDto userDto = new RecommendRequestUserDto();
            userDto.setUsername(currentUser.getUsername());
            requirementDto.setId(Long.toString(requirement.getId()));
            requirementDto.setName(requirement.getTitle());
            String strippedDescription = Utils.removeURL(Jsoup.parse(requirement.getDescription()).text()).trim();
            requirementDto.setDescription(strippedDescription);
            float sumEffort = 0.0f;
            int countEffort = 0;
            for (UserRequirementAttributeVoteDbo attributeVote : requirement.getUserRequirementAttributeVotes()) {
                if (attributeVote.getRatingAttribute().getName().toLowerCase().equals("effort")) {
                    sumEffort += attributeVote.getValue();
                    ++countEffort;
                }
            }
            requirementDto.setEffort(String.format("%.2f", countEffort > 0 ? (sumEffort / countEffort) : 0.0f));
            requirementDto.setModified_at(dateFormat.format(requirement.getLastUpdatedDate()));
            RecommendDto recommendDto = new RecommendDto();
            recommendDto.setProject(projectDto);
            recommendDto.setRequirement(requirementDto);
            recommendDto.setUser(userDto);

            try {
                logger.info("[Stakeholder Recommender] Sending request...");
                HttpEntity<RecommendDto> recommendRequest = new HttpEntity<>(recommendDto, headers);
                int k = 10;
                String url = "http://" + ScheduledBatchJob.UPC_STAKEHOLDER_RECOMMENDATION_SERVICE_HOST + ":"
                        + ScheduledBatchJob.UPC_STAKEHOLDER_RECOMMENDATION_SERVICE_PORT
                        + "/upc/stakeholders-recommender/recommend?k=" + k + "&projectSpecific=true&organization=tugraz";
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                //restTemplate.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));
                ResponseEntity<RecommendResponse[]> response = restTemplate.postForEntity(
                        url, recommendRequest, RecommendResponse[].class);
                RecommendResponse[] recommendations = response.getBody();
                int numOfMeaningfulRecommendations = 0;
                for (RecommendResponse recommendation : recommendations) {
                    if (numOfMeaningfulRecommendations >= 3) {
                        break;
                    }

                    if (requirementID != Long.parseLong(recommendation.getRequirement().getId())) {
                        continue;
                    }

                    logger.info("[Stakeholder Recommender] Recommended Person: " + recommendation.getPerson());
                    UserDbo recommendedUser = userRepository.findOneByUsername(recommendation.getPerson().getUsername());
                    RequirementStakeholderAssignment stakeholderAssignment = requirementStakeholderAssignmentRepository.findOneByRequirementIdAndStakeholderId(requirementID, recommendedUser.getId());
                    if (stakeholderAssignment != null || (!project.isCreator(recommendedUser) && !project.isParticipant(recommendedUser))) {
                        continue;
                    }

                    stakeholderAssignment = new RequirementStakeholderAssignment(requirement, recommendedUser, null);
                    stakeholderAssignments.add(stakeholderAssignment);
                    requirementStakeholderAssignmentRepository.save(stakeholderAssignment);

                    // bot rating
                    StakeholderRatingAttributeDbo ratingAttribute = project.getStakeholderRatingAttributes()
                            .stream()
                            .filter(ra -> ra.getName().toLowerCase().equals("appropriateness"))
                            .collect(Collectors.toList())
                            .get(0);
                    // apply inverse feature scaling
                    float rescaledAppropriatenessValue = (recommendation.getAppropiatenessScore()
                                                       * (ratingAttribute.getMaxValue() - ratingAttribute.getMinValue()))
                                                       + ratingAttribute.getMinValue();
                    int appropriatenessValue = (int) Math.max(Math.round(rescaledAppropriatenessValue), 1);
                    BotUserStakeholderAttributeVoteDbo botUserStakeholderAttributeVoteDbo = new BotUserStakeholderAttributeVoteDbo(
                            appropriatenessValue, requirement, ratingAttribute, recommendedUser);
                    botUserStakeholderAttributeVoteRepository.save(botUserStakeholderAttributeVoteDbo);

                    ratingAttribute = project.getStakeholderRatingAttributes()
                            .stream()
                            .filter(ra -> ra.getName().toLowerCase().equals("availability"))
                            .collect(Collectors.toList())
                            .get(0);
                    // apply inverse feature scaling
                    float rescaledAvailabilityValue = (recommendation.getAvailabilityScore()
                                                    * (ratingAttribute.getMaxValue() - ratingAttribute.getMinValue()))
                                                    + ratingAttribute.getMinValue();
                    int availabilityValue = (int) Math.max(Math.round(rescaledAvailabilityValue), 1);
                    BotUserStakeholderAttributeVoteDbo botUserStakeholderAvailabilityVoteDbo = new BotUserStakeholderAttributeVoteDbo(
                            availabilityValue, requirement, ratingAttribute, recommendedUser);
                    botUserStakeholderAttributeVoteRepository.save(botUserStakeholderAvailabilityVoteDbo);
                    ++numOfMeaningfulRecommendations;
                }

                logger.info("[Stakeholder Recommender] Finished processing request.");
                requirement.setStakeholderRecommendationsFetched(true);
                requirementRepository.save(requirement);
            } catch (Exception e) {
                logger.error("ERROR: Cannot reach UPC Stakeholder Recommendation Service!!!", e);
                emailService.sendEmailAsync(
                        "martin.stettinger@ist.tugraz.at",
                        "[OpenReq!Live] UPC Stakeholder Recommendations Fetch",
                        "<b style='color:darkred;'>UPC Stakeholder Recommendation Service FAILED!!</b><br /><br />Error: " + e.getClass().getSimpleName() + " -> " + e.getMessage(),
                        "UPC Stakeholder Recommendation Service FAILED!!\n\n Error: " + e.getClass().getSimpleName() + " -> " + e.getMessage());
            }
        }

        for (RequirementStakeholderAssignment stakeholderAssignment : stakeholderAssignments) {
            UserDbo user = stakeholderAssignment.getStakeholder();
            HideStakeholderAssignmentDbo hideStakeholderAssignment = hideStakeholderAssignmentRepository
                    .findOneByUserIdAndHiddenStakeholderUserIdAndRequirementId(
                            currentUser.getId(), user.getId(), requirementID);

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", user.getId());
            userInfo.put("username", user.getUsername());
            userInfo.put("firstName", user.getFirstName());
            userInfo.put("lastName", user.getLastName());
            userInfo.put("mailAddress", user.getMailAddress());
            userInfo.put("profileImagePath", user.getProfileImagePath());
            userInfo.put("isAccepted", stakeholderAssignment.isAccepted());
            userInfo.put("proposedBy", (stakeholderAssignment.getProposedByStakeholder() != null) ? stakeholderAssignment.getProposedByStakeholder().getId() : 0);
            userInfo.put("isHidden", (hideStakeholderAssignment != null));
            userInfo.put("proposedDate", stakeholderAssignment.getCreatedDate().getTime());

            Map<Long, Map<String, Object>> stakeholderVotes = new HashMap<>();
            for (BotUserStakeholderAttributeVoteDbo attributeVote : user.getBotUserStakeholderAttributeVotes()) {
                Map<String, Object> attributeVotes = stakeholderVotes.get(0L);
                if (attributeVotes == null) {
                    attributeVotes = new HashMap<>();
                    attributeVotes.put("userID", 0);
                    attributeVotes.put("firstName", "Bot");
                    attributeVotes.put("lastName", "Bot");
                    attributeVotes.put("email", null);
                    attributeVotes.put("attributeVotes", new ArrayList<HashMap<String, Object>>());
                }

                ArrayList<HashMap<String, Object>> votes = (ArrayList<HashMap<String, Object>>) attributeVotes.get("attributeVotes");
                HashMap<String, Object> attributeVoteInfo = new HashMap<>();
                attributeVoteInfo.put("attributeID", attributeVote.getRatingAttribute().getId());
                attributeVoteInfo.put("value", attributeVote.getValue());
                attributeVoteInfo.put("createdDate", attributeVote.getCreatedDate());
                votes.add(attributeVoteInfo);
                attributeVotes.put("attributeVotes", votes);
                stakeholderVotes.put(0L, attributeVotes);
            }

            for (UserStakeholderAttributeVoteDbo attributeVote : user.getRatedStakeholderAttributeVotes()) {
                UserDbo ratingUser = attributeVote.getUser();
                Map<String, Object> attributeVotes = stakeholderVotes.get(ratingUser.getId());
                if (attributeVotes == null) {
                    attributeVotes = new HashMap<>();
                    attributeVotes.put("userID", ratingUser.getId());
                    attributeVotes.put("firstName", ratingUser.getFirstName());
                    attributeVotes.put("lastName", ratingUser.getLastName());
                    attributeVotes.put("email", ratingUser.getMailAddress());
                    attributeVotes.put("attributeVotes", new ArrayList<HashMap<String, Object>>());
                }

                ArrayList<HashMap<String, Object>> votes = (ArrayList<HashMap<String, Object>>) attributeVotes.get("attributeVotes");
                HashMap<String, Object> attributeVoteInfo = new HashMap<>();
                attributeVoteInfo.put("attributeID", attributeVote.getRatingAttribute().getId());
                attributeVoteInfo.put("value", attributeVote.getValue());
                attributeVoteInfo.put("createdDate", attributeVote.getCreatedDate());
                votes.add(attributeVoteInfo);
                attributeVotes.put("attributeVotes", votes);
                stakeholderVotes.put(ratingUser.getId(), attributeVotes);
            }

            userInfo.put("stakeholderVotes", stakeholderVotes);
            userInfoList.add(userInfo);
        }

        List<Map<String, Object>> anonymousUserInfoList = new ArrayList<>();
        List<RequirementAnonymousStakeholderAssignment> anonymousStakeholderAssignments = requirement.getAnonymousUserStakeholderAssignments()
                .stream()
                .sorted(comparing(RequirementAnonymousStakeholderAssignment::getAnonymousStakeholderId))
                .collect(Collectors.toList());

        for (RequirementAnonymousStakeholderAssignment anonymousStakeholderAssignment : anonymousStakeholderAssignments) {
            AnonymousUserDbo anonymousUser = anonymousStakeholderAssignment.getAnonymousStakeholder();
            HideAnonymousStakeholderAssignmentDbo hideAnonymousStakeholderAssignment = hideAnonymousStakeholderAssignmentRepository
                    .findOneByUserIdAndHiddenStakeholderUserIdAndRequirementId(
                            currentUser.getId(), anonymousUser.getId(), requirementID);

            Map<String, Object> anonymousUserInfo = new HashMap<>();
            anonymousUserInfo.put("id", anonymousUser.getId());
            anonymousUserInfo.put("username", anonymousUser.getFullName());
            anonymousUserInfo.put("fullName", anonymousUser.getFullName());
            anonymousUserInfo.put("isAccepted", anonymousStakeholderAssignment.isAccepted());
            anonymousUserInfo.put("proposedBy", 0);
            anonymousUserInfo.put("isHidden", (hideAnonymousStakeholderAssignment != null));
            anonymousUserInfo.put("proposedDate", anonymousStakeholderAssignment.getCreatedDate().getTime());

            Map<Long, Map<String, Object>> stakeholderVotes = new HashMap<>();

            List<AnonymousUserStakeholderAttributeVoteDbo> attributeVs = anonymousUserStakeholderAttributeVoteRepository.findByRatedAnonymousStakeholderIdAndRequirementId(
                    anonymousUser.getId(), requirementID);
            for (AnonymousUserStakeholderAttributeVoteDbo attributeVote : attributeVs) {
                UserDbo ratingUser = attributeVote.getUser();
                Map<String, Object> attributeVotes = stakeholderVotes.get(ratingUser.getId());
                if (attributeVotes == null) {
                    attributeVotes = new HashMap<>();
                    attributeVotes.put("firstName", ratingUser.getFirstName());
                    attributeVotes.put("lastName", ratingUser.getLastName());
                    attributeVotes.put("email", ratingUser.getMailAddress());
                    attributeVotes.put("attributeVotes", new ArrayList<HashMap<String, Object>>());
                }

                ArrayList<HashMap<String, Object>> votes = (ArrayList<HashMap<String, Object>>) attributeVotes.get("attributeVotes");
                HashMap<String, Object> attributeVoteInfo = new HashMap<>();
                attributeVoteInfo.put("attributeID", attributeVote.getRatingAttribute().getId());
                attributeVoteInfo.put("value", attributeVote.getValue());
                attributeVoteInfo.put("createdDate", attributeVote.getCreatedDate());
                votes.add(attributeVoteInfo);
                attributeVotes.put("attributeVotes", votes);
                stakeholderVotes.put(ratingUser.getId(), attributeVotes);
            }

            anonymousUserInfo.put("stakeholderVotes", stakeholderVotes);
            anonymousUserInfoList.add(anonymousUserInfo);
        }

        result.put("error", false);
        result.put("assignedUsers", userInfoList);
        result.put("assignedAnonymousUsers", anonymousUserInfoList);
        return result;
    }

	@ResponseBody
	@PostMapping("/project/{projectID}/requirement/{requirementID}/vote/delegate.json")
	public Map<String, Object> delegateRequirementVote(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
			@PathVariable(value="requirementID") Long requirementID,
			@RequestParam(value="query", required=true) String query,
			Authentication authentication) {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        Map<String, Object> result = new HashMap<>();

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

		RequirementDbo requirement = requirementRepository.findOne(requirementID);
		List<UserDbo> foundUsers = userService.searchUser(query, query.contains("@"));

        if (requirement == null) {
			result.put("error", true);
			result.put("errorMessage", "The requirement does not exist!");
			return result;
		}

		if (requirement.getProject().getId() != projectID) {
			result.put("error", true);
			result.put("errorMessage", "The requirement does not belong to the given project!");
			return result;
		}

		if (!project.isVisibilityPrivate()) {
            result.put("error", true);
            result.put("errorMessage", "Only votes of private projects can be delegated!");
            return result;
        }

        if (foundUsers.size() == 0) {
            result.put("error", true);
            result.put("errorMessage", "No user found!");
            return result;
        }

        UserDbo delegatedUser = foundUsers.get(0);
		if (delegatedUser.getId() == currentUser.getId()) {
            result.put("error", true);
            result.put("errorMessage", "Cannot delegate your vote to yourself!");
            return result;
        }

		if ((delegatedUser.getCreatedProjects().stream().filter(p -> p.getId() == projectID).count() == 0)
                && (delegatedUser.getProjectUserParticipations().stream().filter(p -> p.getProjectId() == projectID).count() == 0)) {
            result.put("error", true);
            result.put("errorMessage", "Cannot delegate your vote to this user since the user does not participate in this project at the moment!");
            return result;
        }

        Map<String, Object> userInfo = new HashMap<>();
        DelegateUserRequirementVoteDbo voteDelegation = delegateUserRequirementVoteRepository
                .findOneByUserIdAndRequirementId(currentUser.getId(), requirementID);

        if (voteDelegation != null) {
            result.put("error", true);
            result.put("errorMessage", "Vote already delegated to user "
                    + voteDelegation.getDelegatedUser().getFirstName() + " "
                    + voteDelegation.getDelegatedUser().getLastName());
            return result;
        }

        if (delegateUserRequirementVoteService.isCircleInDelegationChain(requirement, currentUser, delegatedUser)) {
            result.put("error", true);
            result.put("errorMessage", "Vote cannot be delegated to this user because this would cause a circle in the delegation chain.");
            return result;
        }

        voteDelegation = new DelegateUserRequirementVoteDbo(requirement, currentUser, delegatedUser);
        delegateUserRequirementVoteRepository.save(voteDelegation);

        userInfo.put("id", delegatedUser.getId());
        userInfo.put("firstName", delegatedUser.getFirstName());
        userInfo.put("lastName", delegatedUser.getLastName());
        userInfo.put("mailAddress", delegatedUser.getMailAddress());
        userInfo.put("profileImagePath", delegatedUser.getProfileImagePath());

		Map<String, Object> currentUserInfo = new HashMap<>();
		currentUserInfo.put("id", currentUser.getId());
		currentUserInfo.put("firstName", currentUser.getFirstName());
		currentUserInfo.put("lastName", currentUser.getLastName());
		currentUserInfo.put("mailAddress", currentUser.getMailAddress());
		currentUserInfo.put("profileImagePath", currentUser.getProfileImagePath());

        result.put("error", false);
        result.put("userData", userInfo);
        result.put("currentUserData", currentUserInfo);
        return result;
    }

	@ResponseBody
	@GetMapping("/project/{projectID}/requirement/{requirementID}/vote/delegation/remove.json")
	public Map<String, Object> removeDelegationRequirementVote(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
			@PathVariable(value="requirementID") Long requirementID,
			Authentication authentication) {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        Map<String, Object> result = new HashMap<>();

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

		RequirementDbo requirement = requirementRepository.findOne(requirementID);

		if (requirement == null) {
			result.put("error", true);
			result.put("errorMessage", "The requirement does not exist!");
			return result;
		}

		if (requirement.getProject().getId() != projectID) {
			result.put("error", true);
			result.put("errorMessage", "The requirement does not belong to the given project!");
			return result;
		}

		if (!project.isVisibilityPrivate()) {
            result.put("error", true);
            result.put("errorMessage", "Only votes of private projects can be delegated!");
            return result;
        }

        Map<String, Object> userInfo = new HashMap<>();
        DelegateUserRequirementVoteDbo voteDelegation = delegateUserRequirementVoteRepository
                .findOneByUserIdAndRequirementId(currentUser.getId(), requirementID);

        if (voteDelegation == null) {
            result.put("error", true);
            result.put("errorMessage", "Delegation does not exist!");
            return result;
        }

        delegateUserRequirementVoteRepository.delete(voteDelegation);
        result.put("error", false);
        return result;
    }

	@ResponseBody
	@PostMapping("/project/{projectID}/requirement/{requirementID}/user/assign.json")
	public Map<String, Object> assignUser(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
			@PathVariable(value="requirementID") Long requirementID,
            @RequestParam(value="query", required=true) String query,
            Authentication authentication)
	{
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        Map<String, Object> result = new HashMap<>();

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

		RequirementDbo requirement = requirementRepository.findOne(requirementID);
        List<UserDbo> foundUsers = userService.searchUser(query, query.contains("@"));
        Map<String, Object> userInfo = new HashMap<>();

		if (requirement == null) {
			result.put("error", true);
			result.put("errorMessage", "The requirement does not exist!");
			return result;
		}

		if (requirement.getProject().getId() != projectID) {
			result.put("error", true);
			result.put("errorMessage", "The requirement does not belong to the given project!");
			return result;
		}

		if (project.isVisibilityPrivate() && (currentUser != null) && (foundUsers.size() > 0)) {
            final UserDbo user = foundUsers.get(0);
            boolean isAlreadyAssigned = requirement.getUserStakeholderAssignments().stream().filter(a -> a.getStakeholder().equals(user)).count() == 1;
            if (isAlreadyAssigned) {
                result.put("error", true);
                result.put("errorMessage", "This stakeholder has already been proposed!");
                return result;
            }

			boolean isCreator = (project.getCreatorUser().getId() == user.getId());
			boolean isParticipatingUser = project.getUserParticipations().stream().map(p -> p.getUserId()).collect(Collectors.toList()).contains(user.getId());
			boolean isParticipationgGuestUser = project.getGuestUserParticipations().stream().map(p -> p.getEmailAddress()).collect(Collectors.toList()).contains(user.getMailAddress());

            if (!isCreator && !isParticipatingUser && !isParticipationgGuestUser) {
                result.put("error", true);
                result.put("errorMessage", "This stakeholder cannot be proposed because he/she is not invited to this private project! ");
                return result;
            }

            RequirementStakeholderAssignment stakeholderAssignment = new RequirementStakeholderAssignment(requirement, user, currentUser);
            requirementStakeholderAssignmentRepository.save(stakeholderAssignment);

            userInfo.put("id", user.getId());
            userInfo.put("username", user.getUsername());
            userInfo.put("firstName", user.getFirstName());
            userInfo.put("lastName", user.getLastName());
            userInfo.put("mailAddress", user.getMailAddress());
            userInfo.put("profileImagePath", user.getProfileImagePath());

            result.put("error", false);
            result.put("userData", userInfo);
            return result;
        } else {
            query = query.trim();
            AnonymousUserDbo anonymousUser = anonymousUserRepository.findOneByFullName(query);
            if (anonymousUser == null) {
                anonymousUser = new AnonymousUserDbo(query);
                anonymousUserRepository.save(anonymousUser);
            } else {
                final AnonymousUserDbo temp = anonymousUser;
                boolean isAlreadyAssigned = requirement.getAnonymousUserStakeholderAssignments().stream().filter(a -> a.getAnonymousStakeholder().equals(temp)).count() == 1;
                if (isAlreadyAssigned) {
                    result.put("error", true);
                    result.put("errorMessage", "This user is already assigned to this requirement!");
                    return result;
                }
            }

            RequirementAnonymousStakeholderAssignment stakeholderAssignment = new RequirementAnonymousStakeholderAssignment(requirement, anonymousUser);
            requirementAnonymousStakeholderAssignmentRepository.save(stakeholderAssignment);

            userInfo.put("id", anonymousUser.getId());
            userInfo.put("username", anonymousUser.getFullName());
            userInfo.put("fullName", anonymousUser.getFullName());

            result.put("error", false);
            result.put("anonymousUserData", userInfo);
            return result;
        }
	}

	@ResponseBody
	@PostMapping("/project/{projectID}/requirement/{requirementID}/user/{userID}/unassign.json")
	public Map<String, Object> unassignStakeholder(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
			@PathVariable(value="requirementID") Long requirementID,
            @PathVariable(value="userID") Long userID,
            @RequestParam(value="isAnonymousUser", required=true) Boolean isAnonymousUser,
            Authentication authentication)
	{
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        Map<String, Object> result = new HashMap<>();

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

		RequirementDbo requirement = requirementRepository.findOne(requirementID);

		if (requirement == null) {
			result.put("error", true);
			result.put("errorMessage", "The requirement does not exist!");
			return result;
		}

		if (requirement.getProject().getId() != projectID) {
			result.put("error", true);
			result.put("errorMessage", "The requirement does not belong to the given project!");
			return result;
		}

		if (!isAnonymousUser) {
            RequirementStakeholderAssignment assignment = requirementStakeholderAssignmentRepository.findOneByRequirementIdAndStakeholderId(requirementID, userID);
            if (assignment == null) {
                result.put("error", true);
                result.put("errorMessage", "The user is not assigned to the requirement!");
                return result;
            }
            requirementStakeholderAssignmentRepository.delete(assignment);
        } else {
            RequirementAnonymousStakeholderAssignment assignment = requirementAnonymousStakeholderAssignmentRepository.findOneByRequirementIdAndAnonymousStakeholderId(requirementID, userID);
            if (assignment == null) {
                result.put("error", true);
                result.put("errorMessage", "The user is not assigned to the requirement!");
                return result;
            }
            requirementAnonymousStakeholderAssignmentRepository.delete(assignment);
        }

		result.put("error", false);
		return result;
	}

    @ResponseBody
    @PostMapping("/project/{projectID}/requirement/{requirementID}/user/{userID}/accept.json")
    public Map<String, Object> acceptAssignment(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
            @PathVariable(value="requirementID") Long requirementID,
            @PathVariable(value="userID") Long userID,
            @RequestParam(value="isAnonymousUser", required=true) Boolean isAnonymousUser,
            Authentication authentication)
    {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        Map<String, Object> result = new HashMap<>();

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

        RequirementDbo requirement = requirementRepository.findOne(requirementID);

        if (requirement == null) {
            result.put("error", true);
            result.put("errorMessage", "The requirement does not exist!");
            return result;
        }

        if (requirement.getProject().getId() != projectID) {
            result.put("error", true);
            result.put("errorMessage", "The requirement does not belong to the given project!");
            return result;
        }

        if (isAnonymousUser) {
            RequirementAnonymousStakeholderAssignment requirementAssignment = requirementAnonymousStakeholderAssignmentRepository
                    .findOneByRequirementIdAndAnonymousStakeholderId(requirementID, userID);
            requirementAssignment.setAccepted(true);
            requirementAnonymousStakeholderAssignmentRepository.save(requirementAssignment);
            result.put("error", false);
            return result;
        }

        RequirementStakeholderAssignment requirementAssignment = requirementStakeholderAssignmentRepository
                .findOneByRequirementIdAndStakeholderId(requirementID, userID);
        requirementAssignment.setAccepted(true);
        requirementStakeholderAssignmentRepository.save(requirementAssignment);
        result.put("error", false);
        return result;
    }

    @ResponseBody
    @PostMapping("/project/{projectID}/requirement/{requirementID}/user/{userID}/unaccept.json")
    public Map<String, Object> unacceptAssignment(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
            @PathVariable(value="requirementID") Long requirementID,
            @PathVariable(value="userID") Long userID,
            @RequestParam(value="isAnonymousUser", required=true) Boolean isAnonymousUser,
            Authentication authentication)
    {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        Map<String, Object> result = new HashMap<>();

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

        RequirementDbo requirement = requirementRepository.findOne(requirementID);

        if (requirement == null) {
            result.put("error", true);
            result.put("errorMessage", "The requirement does not exist!");
            return result;
        }

        if (requirement.getProject().getId() != projectID) {
            result.put("error", true);
            result.put("errorMessage", "The requirement does not belong to the given project!");
            return result;
        }

        if (isAnonymousUser) {
            RequirementAnonymousStakeholderAssignment requirementAssignment = requirementAnonymousStakeholderAssignmentRepository
                    .findOneByRequirementIdAndAnonymousStakeholderId(requirementID, userID);
            requirementAssignment.setAccepted(false);
            requirementAnonymousStakeholderAssignmentRepository.save(requirementAssignment);
            result.put("error", false);
            return result;
        }

        RequirementStakeholderAssignment requirementAssignment = requirementStakeholderAssignmentRepository
                .findOneByRequirementIdAndStakeholderId(requirementID, userID);
        requirementAssignment.setAccepted(false);
        requirementStakeholderAssignmentRepository.save(requirementAssignment);
        result.put("error", false);
        return result;
    }

    @Data
	@AllArgsConstructor
    private static class ConflictData {
        public final static float CONFLICT_DISTANCE_THRESHOLD_BASIC = 2.5f;
        public final static float CONFLICT_DISTANCE_THRESHOLD_NORMAL_AND_ADVANCED = 2.5f;

        private RequirementDbo requirement;
		private UserDbo userA;
		private UserDbo userB;
		private long commentAID;
		private long commentBID;
		private int distance;
		private long ratingAttributeID;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ConflictData that = (ConflictData) o;
            return distance == that.distance &&
                    ratingAttributeID == that.ratingAttributeID &&
                    Objects.equals(requirement, that.requirement) &&
                    Objects.equals(userA, that.userA) &&
                    Objects.equals(userB, that.userB);
        }

        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> userAInfo = new HashMap<>();
            userAInfo.put("id", userA.getId());
            userAInfo.put("firstName", userA.getFirstName());
            userAInfo.put("lastName", userA.getLastName());
            userAInfo.put("email", userA.getMailAddress());
            map.put("userA", userAInfo);

            Map<String, Object> userBInfo = new HashMap<>();
            userBInfo.put("id", userB.getId());
            userBInfo.put("firstName", userB.getFirstName());
            userBInfo.put("lastName", userB.getLastName());
            userBInfo.put("email", userB.getMailAddress());
            map.put("userB", userBInfo);
            if (commentAID > 0) {
                map.put("commentAID", commentAID);
            }
            if (commentBID > 0) {
                map.put("commentBID", commentBID);
            }
            map.put("requirementID", requirement.getId());
            map.put("distance", distance);
            map.put("ratingAttributeID", ratingAttributeID);
            return map;
        }

        @Override
        public int hashCode() {
            return Objects.hash(userA, userB, distance, ratingAttributeID);
        }
    }

    private List<ConflictData> basicRatingConflicts(RequirementDbo requirement, float conflictDistanceThreshold) {
        List<UserRequirementVoteDbo> votes = basicRatingRepository.findByRequirementId(requirement.getId());
        List<ConflictData> conflicts = new ArrayList<>();

        Set<UserRequirementVoteDbo> alreadySeenVotes = new HashSet<>();
        for (UserRequirementVoteDbo vote1 : votes) {
            alreadySeenVotes.add(vote1);
			for (UserRequirementVoteDbo vote2 : votes) {
                if (alreadySeenVotes.contains(vote2)) {
                    continue;
                }
				int distance = Math.abs(vote1.getValue() - vote2.getValue());
				if (distance > conflictDistanceThreshold) {
				    conflicts.add(new ConflictData(requirement, vote1.getUser(), vote2.getUser(),
                            0, 0, distance, 0));
				    conflicts.add(new ConflictData(requirement, vote2.getUser(), vote1.getUser(),
                            0, 0, distance, 0));
                }
			}
        }
        return conflicts;
	}

    private List<ConflictData> normalRatingConflicts(RequirementDbo requirement, float conflictDistanceThreshold) {
        List<UserRequirementAttributeVoteDbo> attributeVotes = userRequirementAttributeVoteRepository.findByRequirementID(requirement.getId());
        List<ConflictData> conflicts = new ArrayList<>();

        Set<UserRequirementAttributeVoteDbo> alreadySeenVotes = new HashSet<>();
        for (UserRequirementAttributeVoteDbo attributeVote1 : attributeVotes) {
            alreadySeenVotes.add(attributeVote1);
            for (UserRequirementAttributeVoteDbo attributeVote2 : attributeVotes) {
                if (alreadySeenVotes.contains(attributeVote2)) {
                    continue;
                }
                if (attributeVote1.getRatingAttribute().getId() != attributeVote2.getRatingAttribute().getId()) {
                    continue;
                }
                int distance = Math.abs(attributeVote1.getValue() - attributeVote2.getValue());
                if (distance > conflictDistanceThreshold) {
                    conflicts.add(new ConflictData(requirement, attributeVote1.getUser(), attributeVote2.getUser(),
                            0, 0, distance, attributeVote1.getRatingAttribute().getId()));
                    conflicts.add(new ConflictData(requirement, attributeVote2.getUser(), attributeVote1.getUser(),
                            0, 0, distance, attributeVote2.getRatingAttribute().getId()));
                }
            }
        }
        return conflicts;
	}

    private List<ConflictData> advancedRatingConflicts(RequirementDbo requirement) {
        List<UserRequirementCommentDbo> comments = requirementCommentRepository.findByRequirementID(requirement.getId());
        List<ConflictData> conflicts = new ArrayList<>();

        Set<UserRequirementCommentDbo> alreadySeenComments = new HashSet<>();
        for (UserRequirementCommentDbo comment1 : comments) {
            alreadySeenComments.add(comment1);
            for (UserRequirementCommentDbo comment2 : comments) {
                if (alreadySeenComments.contains(comment2)) {
                    continue;
                }
                for (RatingAttributeDbo commentAttribute1 : comment1.getVotedAttributes()) {
                    for (RatingAttributeDbo commentAttribute2 : comment2.getVotedAttributes()) {
                        if (commentAttribute1.getId() != commentAttribute2.getId()) {
                            continue;
                        }
                        if ((comment1.getSentiment() == Sentiment.PRO && comment2.getSentiment() == Sentiment.CON) ||
                            (comment1.getSentiment() == Sentiment.CON && comment2.getSentiment() == Sentiment.PRO)) {
                            conflicts.add(new ConflictData(requirement, comment1.getUser(), comment2.getUser(),
                                    comment1.getId(), comment2.getId(),9, commentAttribute1.getId()));
                            conflicts.add(new ConflictData(requirement, comment2.getUser(), comment1.getUser(),
                                    comment2.getId(), comment1.getId(), 9, commentAttribute2.getId()));
                        }
                    }
                }
            }
        }
        return conflicts;
    }

    private void storeRatingConflicts(List<ConflictData> conflicts, UserRatingConflictDbo.RatingType ratingType, Date now) {
        String conflictID = Utils.generateRandomKey(30);
        for (ConflictData conflict : conflicts) {
            RequirementDbo requirement = conflict.getRequirement();
            UserRatingConflictDbo ratingConflict = new UserRatingConflictDbo();
            ratingConflict.setRatingType(ratingType);
            ratingConflict.setTime(now);
            ratingConflict.setUserA(conflict.getUserA());
            ratingConflict.setUserB(conflict.getUserB());
            ratingConflict.setCommentAID(conflict.getCommentAID());
            ratingConflict.setCommentBID(conflict.getCommentBID());
            ratingConflict.setConflictID(conflictID);
            ratingConflict.setDistance(conflict.getDistance());
            ratingConflict.setAttributeID(conflict.getRatingAttributeID());
            ratingConflict.setRequirement(requirement);
            userRatingConflictRepository.save(ratingConflict);
        }
    }

    @ResponseBody
	@PostMapping("/project/{projectID}/requirement/basic/vote.json")
	public Map<String, Object> basicVoteJson(
	        @PathVariable(value="projectID") Long projectID,
            @RequestBody BasicVoteDto voteDto,
            HttpServletRequest request, HttpServletResponse response,
            Authentication authentication)
    {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        Map<String, Object> result = new HashMap<>();

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

		RequirementDbo requirement = requirementRepository.findOne(voteDto.getRequirementID());

        if (requirement == null) {
            result.put("error", true);
            result.put("errorMessage", "The requirement does not exist!");
            return result;
        }

        if (requirement.getProject().getId() != projectID) {
            result.put("error", true);
            result.put("errorMessage", "The requirement does not belong to the given project!");
            return result;
        }

        UserRequirementVoteDbo vote = basicRatingRepository.findOneByUserIdAndRequirementId(
                currentUser.getId(), requirement.getId());
        Date now = new Date();
        List<ConflictData> conflicts = new ArrayList<>();
        if (voteDto.getRatingValue() == 0) {
            if (vote != null) {
                basicRatingRepository.delete(vote);
                logVoteActivity(requirement, ProjectSettingsDbo.EvaluationMode.BASIC,
                        UserRequirementVoteActivityDbo.ActionType.DELETED, vote.toString(),
                        0, currentUser);
                conflicts = basicRatingConflicts(requirement, ConflictData.CONFLICT_DISTANCE_THRESHOLD_BASIC);
                storeRatingConflicts(conflicts, UserRatingConflictDbo.RatingType.BASIC, now);
            }

            result.put("error", false);
            result.put("conflicts", conflicts.stream().map(c -> c.toMap()).collect(Collectors.toList()));
            return result;
        }

        if (vote == null) {
            vote = new UserRequirementVoteDbo(voteDto.getRatingValue(), requirement, currentUser);
            logVoteActivity(requirement, ProjectSettingsDbo.EvaluationMode.BASIC,
                    UserRequirementVoteActivityDbo.ActionType.CREATED, vote.toString(),
                    0, currentUser);
        } else {
            vote.setValue(voteDto.getRatingValue());
            logVoteActivity(requirement, ProjectSettingsDbo.EvaluationMode.BASIC,
                    UserRequirementVoteActivityDbo.ActionType.UPDATED, vote.toString(),
                    0, currentUser);
        }

        conflicts = basicRatingConflicts(requirement, ConflictData.CONFLICT_DISTANCE_THRESHOLD_BASIC);
        storeRatingConflicts(conflicts, UserRatingConflictDbo.RatingType.BASIC, now);
        basicRatingRepository.save(vote);

		result.put("error", false);
        result.put("conflicts", conflicts.stream().map(c -> c.toMap()).collect(Collectors.toList()));
		return result;
	}

    public void logVoteActivity(RequirementDbo requirement, ProjectSettingsDbo.EvaluationMode evaluationMode,
                                UserRequirementVoteActivityDbo.ActionType actionType, String stateInfo,
                                long attributeID, UserDbo user) {
        UserRequirementVoteActivityDbo voteActivity = new UserRequirementVoteActivityDbo();
        voteActivity.setTime(new Date());
        voteActivity.setActionType(actionType);
        voteActivity.setAttributeID(attributeID);
        voteActivity.setUser(user);
        voteActivity.setEvaluationMode(evaluationMode);
        voteActivity.setRequirementID(requirement.getId());
        voteActivity.setStateInfo(stateInfo);
        userRequirementVoteActivityRepository.save(voteActivity);
    }

    @ResponseBody
	@PostMapping("/project/{projectID}/requirement/vote.json")
	public Map<String, Object> voteJson(
	        @PathVariable(value="projectID") Long projectID,
            @RequestBody VoteDto requirementVoteDto,
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
    {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        Map<String, Object> result = new HashMap<>();

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

		RequirementDbo requirement = requirementRepository.findOne(requirementVoteDto.getRequirementID());

        if (requirement == null) {
            result.put("error", true);
            result.put("errorMessage", "The requirement does not exist!");
            return result;
        }

        if (requirement.getProject().getId() != projectID) {
            result.put("error", true);
            result.put("errorMessage", "The requirement does not belong to the given project!");
            return result;
        }

        Date now = new Date();
        List<ConflictData> conflicts = new ArrayList<>();

		if ((authentication == null) || !authentication.isAuthenticated()) {
			if (requirementVoteDto.getAnonymousStakeholderName().length() == 0) {
				result.put("error", true);
				result.put("errorMessage", "Unable to vote not authenticated! Also no anonymous user given!");
				return null;
			}

			try {
				for (VoteDimensionDto voteDto : requirementVoteDto.getAttributeVotes()) {
					RatingAttributeDbo ratingAttribute = ratingAttributeRepository.findOne(voteDto.getRatingAttributeID());
					AnonymousUserRequirementAttributeVoteDbo vote = anonymousUserRequirementAttributeVoteRepository.findOneByUserIdAndRequirementIdAndRatingAttributeId(
							requirementVoteDto.getAnonymousStakeholderName(), requirement.getId(), ratingAttribute.getId());
					if (voteDto.getRatingValue() == 0) {
						if (vote != null) {
							anonymousUserRequirementAttributeVoteRepository.delete(vote);
                        }
						continue;
					}

					if (vote == null) {
						vote = new AnonymousUserRequirementAttributeVoteDbo(
								voteDto.getRatingValue(),
								requirement,
								ratingAttribute,
								requirementVoteDto.getAnonymousStakeholderName());
					} else {
						vote.setValue(voteDto.getRatingValue());
					}
					anonymousUserRequirementAttributeVoteRepository.save(vote);
				}

				Cookie nameOfAnonymousUserCookie = new Cookie("nameOfAnonymousUser", requirementVoteDto.getAnonymousStakeholderName());
				nameOfAnonymousUserCookie.setPath("/");
				nameOfAnonymousUserCookie.setMaxAge(365*24*60*60);
				response.addCookie(nameOfAnonymousUserCookie);

                conflicts = normalRatingConflicts(requirement, ConflictData.CONFLICT_DISTANCE_THRESHOLD_NORMAL_AND_ADVANCED);
                storeRatingConflicts(conflicts, UserRatingConflictDbo.RatingType.NORMAL, now);
			} catch (DboConstraintException exception) {
			    logger.error("An exception occured!", exception);
			}

			result.put("error", false);
            result.put("conflicts", conflicts.stream().map(c -> c.toMap()).collect(Collectors.toList()));
			return result;
		}

		try {
			for (VoteDimensionDto voteDto : requirementVoteDto.getAttributeVotes()) {
				RatingAttributeDbo ratingAttribute = ratingAttributeRepository.findOne(voteDto.getRatingAttributeID());
				UserRequirementAttributeVoteDbo vote = userRequirementAttributeVoteRepository.findOneByUserIdAndRequirementIdAndRatingAttributeId(currentUser.getId(), requirement.getId(), ratingAttribute.getId());
				if (voteDto.getRatingValue() == 0) {
					if (vote != null) {
					    logVoteActivity(requirement, ProjectSettingsDbo.EvaluationMode.NORMAL,
                                UserRequirementVoteActivityDbo.ActionType.DELETED, vote.toString(),
                                ratingAttribute.getId(), currentUser);
						userRequirementAttributeVoteRepository.delete(vote);
                    }
					continue;
				}

				if (vote == null) {
					vote = new UserRequirementAttributeVoteDbo(voteDto.getRatingValue(), requirement, ratingAttribute, currentUser);
                    logVoteActivity(requirement, ProjectSettingsDbo.EvaluationMode.NORMAL,
                            UserRequirementVoteActivityDbo.ActionType.CREATED, vote.toString(),
                            ratingAttribute.getId(), currentUser);
				} else {
					vote.setValue(voteDto.getRatingValue());
                    logVoteActivity(requirement, ProjectSettingsDbo.EvaluationMode.NORMAL,
                            UserRequirementVoteActivityDbo.ActionType.UPDATED, vote.toString(),
                            ratingAttribute.getId(), currentUser);
				}
				userRequirementAttributeVoteRepository.save(vote);
			}

            conflicts = normalRatingConflicts(requirement, ConflictData.CONFLICT_DISTANCE_THRESHOLD_NORMAL_AND_ADVANCED);
            storeRatingConflicts(conflicts, UserRatingConflictDbo.RatingType.NORMAL, now);
		} catch (DboConstraintException exception) {
		    logger.error("An exception occurred!", exception);
		}

		result.put("error", false);
        result.put("conflicts", conflicts.stream().map(c -> c.toMap()).collect(Collectors.toList()));
		return result;
	}

	@ResponseBody
	@PostMapping("/project/{projectID}/requirement/update.json")
	public Map<String, Object> updateRequirementJson(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
			@RequestBody List<RequirementDto> requirementDtos,
            Authentication authentication)
	{
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        Map<String, Object> result = new HashMap<>();

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

        List<RequirementDbo> requirements = new ArrayList<>();

		for (RequirementDto requirementDto : requirementDtos) {
			RequirementDbo requirement = requirementRepository.findOne(requirementDto.getId());
			RequirementDbo copiedRequirement = requirement.clone();
			if (requirement.getProject().getId() != projectID) {
			    continue;
            }

            if (!requirement.getTitle().equals(requirementDto.getTitle())) {
                requirement.setTitle(requirementDto.getTitle());
                requirement.logRequirementUpdate(ActionType.TITLE_CHANGED, copiedRequirement, currentUser);
            }

            if (!requirement.getDescription().equals(requirementDto.getDescription())) {
                requirement.setDescription(requirementDto.getDescription());
                requirement.logRequirementUpdate(ActionType.DESCRIPTION_CHANGED, copiedRequirement, currentUser);
            }

            if (requirement.getStatus() != requirementDto.getStatus()) {
                requirement.setStatus(requirementDto.getStatus());
                requirement.logRequirementUpdate(ActionType.STATUS_CHANGED, copiedRequirement, currentUser);
            }

			if (requirementDto.getAssignedReleaseID().longValue() > 0) {
			    if ((requirement.getRelease() == null) || (requirementDto.getAssignedReleaseID() != requirement.getRelease().getId())) {
                    ReleaseDbo release = releaseRepository.findOne(requirementDto.getAssignedReleaseID());
                    requirement.setRelease(release);
                    requirement.logRequirementUpdate(ActionType.RELEASE_ASSIGNMENT_CHANGED, copiedRequirement, currentUser);
                }
            } else if (requirement.getRelease() != null) {
				requirement.setRelease(null);
                requirement.logRequirementUpdate(ActionType.RELEASE_ASSIGNMENT_REMOVED, copiedRequirement, currentUser);
			}

            requirementRepository.save(requirement);
			requirements.add(requirement);
		}

		result.put("error", false);
		result.put("requirementIDs", requirements.stream().map(requirement -> requirement.getId()).collect(Collectors.toList()));
		return result;
	}

	@Deprecated
	@ResponseBody
	@PostMapping("/import/project/{projectID}/requirements")
	public String importExtractedRequirements(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
            @RequestBody ImportedRequirementsBean requirements,
            Authentication authentication)
    {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }

		Set<ReleaseDbo> releases = project.getReleases();
		ReleaseDbo firstRelease = releases.iterator().next();

		if (requirements != null) {
			for (RemoteRequirementDto checkedReq : requirements.getCheckedRequirements()) {
				if (checkedReq != null) {
					//TODO: be careful, I always use release1 and the same text for the name and description
					project.incrementLastProjectSpecificRequirementId();
					RequirementDbo temp = new RequirementDbo(project.getLastProjectSpecificRequirementId(), checkedReq.getText(), checkedReq.getText(), project);
					temp.setStatus(Status.NEW);
					temp.setRelease(firstRelease);
					firstRelease.addRequirement(temp);
					project.addRequirement(temp);
					projectRepository.save(project);
				}
			}

			for (RemoteRequirementDto uncheckedReq : requirements.getUncheckedRequirements()) {
				if(uncheckedReq != null) {
					//TODO: be careful, I always use release1 and the same text for the name and description 
					project.incrementLastProjectSpecificRequirementId();
					RequirementDbo temp = new RequirementDbo(project.getLastProjectSpecificRequirementId(), uncheckedReq.getText(), uncheckedReq.getText(), project);
					temp.setStatus(Status.RECOMMENDED);
					temp.setRelease(firstRelease);
					firstRelease.addRequirement(temp);
					project.addRequirement(temp);
					projectRepository.save(project);
				}
			}
		}
		return "requirements_imported";
	}

}
