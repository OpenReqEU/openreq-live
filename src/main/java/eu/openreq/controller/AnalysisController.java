package eu.openreq.controller;

import eu.openreq.Util.Utils;
import eu.openreq.api.internal.dto.ClassifiedRequirementPairDto;
import eu.openreq.dbo.*;
import eu.openreq.repository.*;
import eu.openreq.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Controller
public class AnalysisController {

    private static final Logger logger = LoggerFactory.getLogger(AnalysisController.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PotentialDependencyRepository potentialDependencyRepository;

    @Autowired
    private OmittedPotentialDependencyRepository omittedPotentialDependencyRepository;

    @Autowired
    private RequirementRepository requirementRepository;

    private static ReentrantLock lock = new ReentrantLock();

    private static Date getDeadline() {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZ");
        try {
            return dateformat.parse("2018-12-04T23:59:00+0100");
            //return dateformat.parse("2018-11-17T02:33:00+0100");
        } catch (Exception e) {
            return new Date();
        }
    }

    @GetMapping("/project/{projectUniqueKey}/requirement/pairs/welcome")
    public String welcome(@PathVariable(value="projectUniqueKey") String projectUniqueKey,
                          Authentication authentication,
                          HttpServletRequest request,
                          Model model)
    {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOneByUniqueKey(projectUniqueKey);

        if ((currentUser == null) || (currentUser.getGroupNumber() == null) || !project.getProjectSettings().isDependencyAnalysisProject()) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }

        Date now = new Date();

        model.addAttribute("project", project);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("continue", (currentUser.getRequirementPairEndIndex() > 0));
        model.addAttribute("deadlineExpired", now.after(getDeadline()));
        return "welcome";
    }

    @GetMapping("/project/{projectUniqueKey}/requirement/pairs/recommend")
    public String recommend(@PathVariable(value="projectUniqueKey") String projectUniqueKey,
                            Authentication authentication,
                            HttpServletRequest request,
                            Model model)
    {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOneByUniqueKey(projectUniqueKey);

        if ((currentUser == null) || (currentUser.getGroupNumber() == null) || !project.getProjectSettings().isDependencyAnalysisProject()) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }

        boolean alreadyCompleted = (project.getUsersWhoCompletedDependencyAnalysisProject().stream().filter(u -> u.getId() == currentUser.getId()).count() == 1);

        Date now = new Date();
        if (now.after(getDeadline()) && (alreadyCompleted == false)) {
            String errorMessage = Utils.generateDeadlineErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "The deadline is over!!! " + errorMessage);
        }

        model.addAttribute("project", project);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("alreadyCompleted", alreadyCompleted);
        return "recommend";
    }

    @PostMapping("/project/{projectUniqueKey}/requirement/pairs/recommend/complete")
    public void submitAndComplete(@PathVariable(value="projectUniqueKey") String projectUniqueKey, Authentication authentication,
                                  HttpServletRequest request, HttpServletResponse response, Model model)
    {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOneByUniqueKey(projectUniqueKey);

        if ((currentUser == null) || (currentUser.getGroupNumber() == null) || !project.getProjectSettings().isDependencyAnalysisProject()) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }

        Date now = new Date();
        if (now.after(getDeadline())) {
            String errorMessage = Utils.generateDeadlineErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "The deadline is over!!! " + errorMessage);
        }

        Set<UserDbo> usersWhoCompleted = project.getUsersWhoCompletedDependencyAnalysisProject();
        usersWhoCompleted.add(currentUser);
        project.setUsersWhoCompletedDependencyAnalysisProject(usersWhoCompleted);
        projectRepository.save(project);

        Set<ProjectDbo> completedProjects = currentUser.getCompletedDependencyAnalysisProject();
        completedProjects.add(project);
        currentUser.setCompletedDependencyAnalysisProject(completedProjects);
        userRepository.save(currentUser);

        try {
            response.sendRedirect("/project/" + projectUniqueKey + "/requirement/pairs/recommend");
        } catch (IOException ex) {}
    }

    @ResponseBody
    @GetMapping("/project/{projectUniqueKey}/requirement/pairs/recommend.json")
    public Map<String, Object> recommendRequirementPairs(@PathVariable(value="projectUniqueKey") String projectUniqueKey,
                                                         Authentication authentication,
                                                         HttpServletRequest request)
    {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOneByUniqueKey(projectUniqueKey);

        if ((currentUser == null) || (currentUser.getGroupNumber() == null) || !project.getProjectSettings().isDependencyAnalysisProject()) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }

        Date now = new Date();
        if (now.after(getDeadline())) {
            String errorMessage = Utils.generateDeadlineErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "The deadline is over!!! " + errorMessage);
        }

        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> recommendedPairs = new ArrayList<>();
        Map<String, Map<String, Object>> alreadyDefinedPotentialDependenciesList = new HashMap<>();

        List<PotentialDependencyDbo> alreadyDefinedPotentialDependencies = potentialDependencyRepository.findAllByUserID(currentUser.getId());
        for (PotentialDependencyDbo potentialDependency : alreadyDefinedPotentialDependencies) {
            RequirementDbo sourceRequirement = potentialDependency.getSourceRequirement();
            RequirementDbo targetRequirement = potentialDependency.getTargetRequirement();
            if (sourceRequirement.getProject().getId() != project.getId()) {
                continue;
            }

            if (targetRequirement.getProject().getId() != project.getId()) {
                continue;
            }

            Map<String, Object> alreadyDefinedPotentialDependency = new HashMap<>();
            alreadyDefinedPotentialDependency.put("sourceRequirementID", sourceRequirement.getId());
            alreadyDefinedPotentialDependency.put("targetRequirementID", targetRequirement.getId());
            alreadyDefinedPotentialDependency.put("type", potentialDependency.getType());
            alreadyDefinedPotentialDependency.put("uniqueKey", potentialDependency.getUniqueKey());
            alreadyDefinedPotentialDependency.put("isReverseDirection", potentialDependency.isReverseDirection());
            alreadyDefinedPotentialDependenciesList.put(potentialDependency.getUniqueKey(), alreadyDefinedPotentialDependency);
        }

        Map<String, Boolean> unsurePairs = new HashMap<>();
        List<OmittedPotentialDependencyDbo> omittedPotentialDependencies = omittedPotentialDependencyRepository.findAllByUserID(currentUser.getId());
        for (OmittedPotentialDependencyDbo omittedPotentialDependency : omittedPotentialDependencies) {
            unsurePairs.put(omittedPotentialDependency.getUniqueKey(), true);
        }

        List<RequirementDbo> requirements = project.getRequirements()
                .stream()
                .sorted(Comparator.comparing(RequirementDbo::getId))
                .collect(Collectors.toList());

        long totalNumberOfPairs = Utils.binomial(requirements.size(), 2) * 2;
        updateIndexRangeIfNecessary(project, currentUser);

        long pairIndex = 0;
        for (RequirementDbo sourceRequirement : requirements) {
            for (RequirementDbo targetRequirement : requirements) {
                if (sourceRequirement.getId() == targetRequirement.getId()) {
                    continue;
                }

                if (pairIndex < currentUser.getRequirementPairStartIndex()) {
                    ++pairIndex;
                    continue;
                }

                if (pairIndex >= currentUser.getRequirementPairEndIndex()) {
                    break;
                }

                Map<String, Object> recommendedPair = new HashMap<>();
                recommendedPair.put("sourceRequirementID", sourceRequirement.getId());
                recommendedPair.put("sourceRequirementProjectSpecificId", sourceRequirement.getProjectSpecificRequirementId());
                recommendedPair.put("sourceRequirementTitle", sourceRequirement.getTitle());
                recommendedPair.put("sourceRequirementDescription", sourceRequirement.getDescription());
                recommendedPair.put("targetRequirementID", targetRequirement.getId());
                recommendedPair.put("targetRequirementProjectSpecificId", targetRequirement.getProjectSpecificRequirementId());
                recommendedPair.put("targetRequirementTitle", targetRequirement.getTitle());
                recommendedPair.put("targetRequirementDescription", targetRequirement.getDescription());
                recommendedPairs.add(recommendedPair);
                ++pairIndex;
            }
        }

        result.put("recommendedPairs", recommendedPairs);
        result.put("unsurePairs", unsurePairs);
        result.put("alreadyDefinedPotentialDependencies", alreadyDefinedPotentialDependenciesList);
        return result;
    }

    //@Transacational(isolation=REPEATABLE_READ)
    private void updateIndexRangeIfNecessary(ProjectDbo project, UserDbo user) {
        lock.lock();
        try {
            if (user.getRequirementPairEndIndex() == 0) {
                long numberOfRequirements = project.getRequirements().size();
                long lastRecommendedRequirementPairListStudentIndex = project.getLastRecommendedRequirementPairListStudentIndex();
                long slotID = lastRecommendedRequirementPairListStudentIndex % numberOfRequirements;
                ++lastRecommendedRequirementPairListStudentIndex;
                project.setLastRecommendedRequirementPairListStudentIndex(lastRecommendedRequirementPairListStudentIndex);
                projectRepository.save(project);

                long numberOfPairsForStudent = (numberOfRequirements - 1);
                long startIndex = slotID * numberOfPairsForStudent;
                long endIndex = startIndex + numberOfPairsForStudent;

                user.setRequirementPairStartIndex(startIndex);
                user.setRequirementPairEndIndex(endIndex);
                userRepository.save(user);
            }
        } finally {
            lock.unlock();
        }
    }

    @ResponseBody
    @PostMapping("/project/{projectUniqueKey}/requirement/pair/classify.json")
    public Map<String, Object> classifyRequirementPairs(@PathVariable(value="projectUniqueKey") String projectUniqueKey,
                                                        Authentication authentication,
                                                        @RequestBody ClassifiedRequirementPairDto classifiedRequirementPairDto,
                                                        HttpServletRequest request)
    {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOneByUniqueKey(projectUniqueKey);

        if ((currentUser == null) || (currentUser.getGroupNumber() == null) || !project.getProjectSettings().isDependencyAnalysisProject()) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }

        Date now = new Date();
        if (now.after(getDeadline())) {
            String errorMessage = Utils.generateDeadlineErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "The deadline is over!!! " + errorMessage);
        }

        long sourceRequirementID = classifiedRequirementPairDto.getSourceRequirementID();
        long targetRequirementID = classifiedRequirementPairDto.getTargetRequirementID();

        RequirementDbo sourceRequirement = requirementRepository.findOne(sourceRequirementID);
        RequirementDbo targetRequirement = requirementRepository.findOne(targetRequirementID);
        if ((sourceRequirement.getProject().getId() != project.getId()) || (targetRequirement.getProject().getId() != project.getId())) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "The given requirement does not belong to the project!");
        }

        OmittedPotentialDependencyDbo omittedPotentialDependency = omittedPotentialDependencyRepository
                .findOneBySourceRequirementIDAndTargetRequirementIDAndUserID(sourceRequirementID, targetRequirementID, currentUser.getId());
        if (omittedPotentialDependency != null) {
            omittedPotentialDependencyRepository.delete(omittedPotentialDependency);
        }

        PotentialDependencyDbo.Type type = classifiedRequirementPairDto.getDependencyType();
        if (!classifiedRequirementPairDto.isDependencyExists()) {
            type = PotentialDependencyDbo.Type.NONE;
        }

        PotentialDependencyDbo potentialDependency = potentialDependencyRepository
                .findOneBySourceRequirementIDAndTargetRequirementIDAndUserID(sourceRequirementID, targetRequirementID, currentUser.getId());
        if (potentialDependency == null) {
            potentialDependency = new PotentialDependencyDbo(sourceRequirement, targetRequirement, currentUser, type,
                    classifiedRequirementPairDto.isReverseDirection(), classifiedRequirementPairDto.getDuration());
        } else {
            potentialDependency.setVisible(true);
            potentialDependency.setType(type);
            potentialDependency.setReverseDirection(classifiedRequirementPairDto.isReverseDirection());
            potentialDependency.setDuration(classifiedRequirementPairDto.getDuration());
        }
        potentialDependencyRepository.save(potentialDependency);

        Map<String, Object> result = new HashMap<>();
        result.put("error", false);
        Map<String, Object> definedPotentialDependency = new HashMap<>();
        definedPotentialDependency.put("sourceRequirementID", sourceRequirementID);
        definedPotentialDependency.put("targetRequirementID", targetRequirementID);
        definedPotentialDependency.put("type", type);
        definedPotentialDependency.put("uniqueKey", potentialDependency.getUniqueKey());
        definedPotentialDependency.put("isReverseDirection", potentialDependency.isReverseDirection());
        result.put("definedPotentialDependency", definedPotentialDependency);
        return result;
    }

    @ResponseBody
    @PostMapping("/project/{projectUniqueKey}/requirement/pair/unclassify.json")
    public Map<String, Object> unclassifyRequirementPairs(@PathVariable(value="projectUniqueKey") String projectUniqueKey,
                                                          Authentication authentication,
                                                          @RequestBody ClassifiedRequirementPairDto classifiedRequirementPairDto,
                                                          HttpServletRequest request)
    {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOneByUniqueKey(projectUniqueKey);

        if ((currentUser == null) || (currentUser.getGroupNumber() == null) || !project.getProjectSettings().isDependencyAnalysisProject()) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }

        Date now = new Date();
        if (now.after(getDeadline())) {
            String errorMessage = Utils.generateDeadlineErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "The deadline is over!!! " + errorMessage);
        }

        long sourceRequirementID = classifiedRequirementPairDto.getSourceRequirementID();
        long targetRequirementID = classifiedRequirementPairDto.getTargetRequirementID();
        RequirementDbo sourceRequirement = requirementRepository.findOne(sourceRequirementID);
        RequirementDbo targetRequirement = requirementRepository.findOne(targetRequirementID);
        if ((sourceRequirement.getProject().getId() != project.getId()) || (targetRequirement.getProject().getId() != project.getId())) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "The given requirement does not belong to the project!");
        }

        Map<String, Object> result = new HashMap<>();

        PotentialDependencyDbo potentialDependency = potentialDependencyRepository
                .findOneBySourceRequirementIDAndTargetRequirementIDAndUserID(sourceRequirementID, targetRequirementID, currentUser.getId());
        if (potentialDependency != null) {
            potentialDependencyRepository.delete(potentialDependency);
        }

        OmittedPotentialDependencyDbo omittedPotentialDependency = omittedPotentialDependencyRepository
                .findOneBySourceRequirementIDAndTargetRequirementIDAndUserID(sourceRequirementID, targetRequirementID, currentUser.getId());
        if (omittedPotentialDependency == null) {
            omittedPotentialDependency = new OmittedPotentialDependencyDbo(sourceRequirement, targetRequirement, currentUser);
        }
        omittedPotentialDependencyRepository.save(omittedPotentialDependency);

        result.put("error", false);
        result.put("uniqueKey", omittedPotentialDependency.getUniqueKey());
        return result;
    }

}
