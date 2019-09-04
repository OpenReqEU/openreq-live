package eu.openreq.controller;

import java.io.*;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import eu.openreq.Util.CSVReader;
import eu.openreq.Util.Constants;
import eu.openreq.api.external.dto.export.ProjectDto;
import eu.openreq.api.external.dto.export.ReleaseDto;
import eu.openreq.api.external.dto.export.RequirementDto;
import eu.openreq.dbo.*;
import eu.openreq.repository.*;
import eu.openreq.service.IPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import eu.openreq.Util.Utils;
import eu.openreq.service.EmailService;
import eu.openreq.service.UserService;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;
import static eu.openreq.dbo.ProjectSettingsDbo.EvaluationMode.ADVANCED;
import static eu.openreq.dbo.ProjectSettingsDbo.EvaluationMode.BASIC;
import static eu.openreq.dbo.ProjectSettingsDbo.EvaluationMode.NORMAL;
import static java.util.Comparator.comparing;

@Controller
public class ProjectController {

	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ProjectSettingsRepository projectSettingsRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AnonymousUserRequirementAttributeVoteRepository anonymousUserRequirementAttributeVoteRepository;

	@Autowired
	private UserRequirementAttributeVoteRepository userRequirementAttributeVoteRepository;

	@Autowired
	private ProjectUserParticipationRepository projectUserParticipationRepository;

	@Autowired
	private ProjectGuestUserParticipationRepository projectGuestUserParticipationRepository;

	@Autowired
	private RequirementRepository requirementRepository;

	@Autowired
	private ReleaseRepository releaseRepository;

	@Autowired
	private DependencyRepository dependencyRepository;

	@Autowired
	private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private IPService ipService;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping("/test")
    public String test(Model model, HttpServletResponse http, Authentication authentication) {
		return "test";
	}

	@RequestMapping("/analyzation")
    public String analyzation(Model model, HttpServletResponse http, Authentication authentication) {
		Iterable<UserDbo> users = userRepository.findAll();
		Map<Long, List<UserDbo>> groupStudents = new HashMap();
		Map<Long, ProjectDbo> groupProjects = new HashMap();
		for (UserDbo user : users) {
			Long groupNumber = user.getGroupNumber();
			if (groupNumber == null) {
				continue;
			}

			List<UserDbo> studentsOfGroup = groupStudents.get(groupNumber);
			if (studentsOfGroup == null) {
				studentsOfGroup = new ArrayList<>();
			}
			studentsOfGroup.add(user);
			groupStudents.put(groupNumber, studentsOfGroup);

            ProjectDbo projectOfGroup = groupProjects.get(groupNumber);
            if (projectOfGroup == null) {
                List<ProjectUserParticipationDbo> participations = user.getProjectUserParticipations()
                        .stream()
                        .filter(p -> p.getProject().getProjectSettings().isReadOnly())
                        //.filter(p -> p.getProject().getName().toLowerCase().equals("t-rec project (group " + Long.toString(groupNumber) + ")"))
                        .collect(Collectors.toList());
                if (participations.size() != 1) {
                    System.err.println("WHAT THE HELL!!!!");
                }
                groupProjects.put(groupNumber, participations.get(0).getProject());
            }
        }

        System.out.println("Number of groups: " + groupStudents.size());
        System.out.println("Number of projects: " + groupProjects.size());

        // evaluate each group
        List<String> csvLines = new ArrayList<>();
        csvLines.add("Group number;Requirement Definition points;Assignment to Releases points;Requirement Voting points;Conflict points;Total points;Group members");
        for (Map.Entry<Long, ProjectDbo> entry : groupProjects.entrySet()) {
            Long groupNumber = entry.getKey();
            ProjectDbo project = entry.getValue();
            List<UserDbo> groupParticipants = project.getUserParticipations().stream().map(p -> p.getUser()).collect(Collectors.toList());
            System.out.println();
            System.out.println("------------------------------------------------------------------------------------");
            System.out.println("Evaluating group: #" + groupNumber);
            System.out.println("Number of participants: " + groupParticipants.size());
            System.out.println("Participants:");
            /*
            for () {
            }
            */
            System.out.println("Project ID: " + project.getId());
            System.out.println("Project key: " + project.getUniqueKey());
            System.out.println("Evaluation mode: " + project.getProjectSettings().getEvaluationMode());

            List<RequirementDbo> allRequirements = project.getRequirements()
                    .stream()
                    .filter(r -> r.isVisible())
                    .collect(Collectors.toList());
            System.out.println("Number of total requirements: " + allRequirements.size());

            List<RequirementDbo> releaseRequirements = project.getRequirements()
                    .stream()
                    .filter(r -> r.isVisible())
                    .filter(r -> r.getRelease() != null)
                    .filter(r -> r.getRelease().getName().toLowerCase().equals("übungsblatt 2"))
                    .collect(Collectors.toList());
            System.out.println("Number of requirements of release 'Übungsblatt 2': " + releaseRequirements.size());

            Map<Long, Integer> numberOfRequirementVotedUsers = new HashMap<>();
            Map<Long, Integer> numberOfRequirementConflicts = new HashMap<>();

            for (RequirementDbo requirement : allRequirements) {
                Map<String, List<UserRatingConflictDbo>> uniqueConflicts = new HashMap<>();
                for (UserRatingConflictDbo ratingConflict : requirement.getRequirementConflicts()) {
                    String conflictID = ratingConflict.getConflictID();
                    List<UserRatingConflictDbo> relatedConflicts = uniqueConflicts.get(conflictID);
                    if (relatedConflicts == null) {
                        relatedConflicts = new ArrayList<>();
                    }
                    relatedConflicts.add(ratingConflict);
                    uniqueConflicts.put(conflictID, relatedConflicts);
                }

                numberOfRequirementConflicts.put(requirement.getId(), uniqueConflicts.size());

                if (project.getProjectSettings().getEvaluationMode() == BASIC) {
                    Set<UserRequirementVoteDbo> votes = requirement.getUserRequirementVotes();
                    numberOfRequirementVotedUsers.put(requirement.getId(), votes.size());
                } else if (project.getProjectSettings().getEvaluationMode() == NORMAL) {
                    Set<UserRequirementAttributeVoteDbo> votes = requirement.getUserRequirementAttributeVotes();
                    Set<Long> votedUserIDs = new HashSet<>();
                    for (UserRequirementAttributeVoteDbo vote : votes) {
                        votedUserIDs.add(vote.getUserId());
                    }
                    numberOfRequirementVotedUsers.put(requirement.getId(), votedUserIDs.size());
                } else if (project.getProjectSettings().getEvaluationMode() == ADVANCED) {
                    Set<UserRequirementCommentDbo> comments = requirement.getUserComments();
                    Set<Long> votedUserIDs = new HashSet<>();
                    for (UserRequirementCommentDbo comment : comments) {
                        votedUserIDs.add(comment.getUser().getId());
                    }
                    numberOfRequirementVotedUsers.put(requirement.getId(), votedUserIDs.size());
                }
            }

            Map<Long, Integer> numberOfReleaseRequirementVotedUsers = new HashMap<>();
            Map<Long, Integer> numberOfReleaseRequirementConflicts = new HashMap<>();

            for (RequirementDbo releaseRequirement : releaseRequirements) {
                Map<String, List<UserRatingConflictDbo>> uniqueConflicts = new HashMap<>();
                for (UserRatingConflictDbo ratingConflict : releaseRequirement.getRequirementConflicts()) {
                    String conflictID = ratingConflict.getConflictID();
                    List<UserRatingConflictDbo> relatedConflicts = uniqueConflicts.get(conflictID);
                    if (relatedConflicts == null) {
                        relatedConflicts = new ArrayList<>();
                    }
                    relatedConflicts.add(ratingConflict);
                    uniqueConflicts.put(conflictID, relatedConflicts);
                }

                numberOfReleaseRequirementConflicts.put(releaseRequirement.getId(), uniqueConflicts.size());

                if (project.getProjectSettings().getEvaluationMode() == BASIC) {
                    Set<UserRequirementVoteDbo> votes = releaseRequirement.getUserRequirementVotes();
                    numberOfReleaseRequirementVotedUsers.put(releaseRequirement.getId(), votes.size());
                } else if (project.getProjectSettings().getEvaluationMode() == NORMAL) {
                    Set<UserRequirementAttributeVoteDbo> votes = releaseRequirement.getUserRequirementAttributeVotes();
                    Set<Long> votedUserIDs = new HashSet<>();
                    for (UserRequirementAttributeVoteDbo vote : votes) {
                        votedUserIDs.add(vote.getUserId());
                    }
                    numberOfReleaseRequirementVotedUsers.put(releaseRequirement.getId(), votedUserIDs.size());
                } else if (project.getProjectSettings().getEvaluationMode() == ADVANCED) {
                    Set<UserRequirementCommentDbo> comments = releaseRequirement.getUserComments();
                    Set<Long> votedUserIDs = new HashSet<>();
                    for (UserRequirementCommentDbo comment : comments) {
                        votedUserIDs.add(comment.getUser().getId());
                    }
                    numberOfReleaseRequirementVotedUsers.put(releaseRequirement.getId(), votedUserIDs.size());
                }
            }

            int numberOfTotalUserVotes = numberOfRequirementVotedUsers.values().stream().mapToInt(nv -> nv.intValue()).sum();
            int numberOfTotalConflicts = numberOfRequirementConflicts.values().stream().mapToInt(nc -> nc.intValue()).sum();
            long numberOfTotalRequirementsWithConflict = numberOfRequirementConflicts.values().stream().filter(nv -> nv.intValue() > 0).count();
            int numberOfTotalReleaseRequirementUserVotes = numberOfReleaseRequirementVotedUsers.values().stream().mapToInt(nv -> nv.intValue()).sum();
            int numberOfTotalReleaseRequirementConflicts = numberOfReleaseRequirementConflicts.values().stream().mapToInt(nc -> nc.intValue()).sum();
            long numberOfTotalReleaseRequirementsWithConflict = numberOfReleaseRequirementConflicts.values().stream().filter(nv -> nv.intValue() > 0).count();

            System.out.println("Number of total user votes: " + numberOfTotalUserVotes);
            System.out.println("Number of total conflicts: " + numberOfTotalConflicts);
            System.out.println("Number of total user votes of release 'Übungsblatt 2': " + numberOfTotalReleaseRequirementUserVotes);
            System.out.println("Number of total requirement conflicts of release 'Übungsblatt 2': " + numberOfTotalReleaseRequirementConflicts);

            float averageTotalVotes = (numberOfTotalUserVotes / ((float) allRequirements.size()));
            float averageReleaseVotes = releaseRequirements.size() > 0 ? (numberOfTotalReleaseRequirementUserVotes / ((float) releaseRequirements.size())) : 0.0f;
            float averageTotalConflictDegree = allRequirements.size() > 0 ? (numberOfTotalRequirementsWithConflict / ((float) allRequirements.size())) : 0.0f;
            float averageReleaseConflictDegree = releaseRequirements.size() > 0 ? (numberOfTotalReleaseRequirementsWithConflict / ((float) releaseRequirements.size())) : 0.0f;
            float overallEvaluationDegree = averageTotalVotes / groupParticipants.size();
            float overallReleaseEvaluationDegree = averageReleaseVotes / groupParticipants.size();

            System.out.println();
            System.out.println("Evaluation");
            System.out.println("Number of total requirements with conflicts: " + numberOfTotalRequirementsWithConflict);
            System.out.println("Number of requirements of release 'Übungsblatt 2' with conflicts: " + numberOfTotalReleaseRequirementsWithConflict);
            System.out.println("Number of average user votes: " + averageTotalVotes);
            System.out.println("Number of average user votes in release 'Übungsblatt 2': " + averageReleaseVotes);
            System.out.println("Overall evaluation degree: " + overallEvaluationDegree);
            System.out.println("Evaluation degree in release 'Übungsblatt 2': " + overallReleaseEvaluationDegree);
            System.out.println("Overall conflict degree: " + averageTotalConflictDegree);
            System.out.println("Overall conflict degree in release 'Übungsblatt 2': " + averageReleaseConflictDegree);

            // Calculate points
            int requirementDefinitionPoints = 0;
            if (allRequirements.size() >= 15) {
                requirementDefinitionPoints = 2;
            } else if (allRequirements.size() >= 10) {
                requirementDefinitionPoints = 1;
            }

            int assignmentToReleasesPoints = 0;
            List<RequirementDbo> unassignedRequirements = project.getRequirements()
                    .stream()
                    .filter(r -> r.isVisible())
                    .filter(r -> r.getRelease() == null)
                    .collect(Collectors.toList());

            if ((releaseRequirements.size() == 0) || (releaseRequirements.size() == allRequirements.size()) || (allRequirements.size() - unassignedRequirements.size() - releaseRequirements.size()) == 0) {
                assignmentToReleasesPoints = 0;
            } else {
                assignmentToReleasesPoints = 1;
            }

            int requirementVotingPoints = Math.round(Math.max(overallEvaluationDegree, overallReleaseEvaluationDegree) * 3);

            int conflictPoints = 0;
            if (project.getProjectSettings().getEvaluationMode() == BASIC) {
                // >=0.05 (1P) ;>=0.1 (2P); >= 0.2 (3P)
                if (averageTotalConflictDegree >= 0.2) {
                    conflictPoints = 3;
                } else if (averageTotalConflictDegree >= 0.1) {
                    conflictPoints = 2;
                } else if (averageTotalConflictDegree >= 0.05) {
                    conflictPoints = 1;
                }
            } else {
                // >=0.15 (1P) ;>=0.3 (2P); >= 0.6 (3P)
                if (averageTotalConflictDegree >= 0.6) {
                    conflictPoints = 3;
                } else if (averageTotalConflictDegree >= 0.3) {
                    conflictPoints = 2;
                } else if (averageTotalConflictDegree >= 0.15) {
                    conflictPoints = 1;
                }
            }

            int totalPoints = requirementDefinitionPoints + assignmentToReleasesPoints + requirementVotingPoints + conflictPoints;
            String studentNames = groupParticipants.stream().map(u -> u.getFirstName() + " " + u.getLastName() + " (" + u.getMailAddress() + ")").collect(Collectors.joining(", "));
            csvLines.add(groupNumber + ";" + requirementDefinitionPoints + ";" + assignmentToReleasesPoints + ";" + requirementVotingPoints + ";" + conflictPoints + ";" + totalPoints + ";" + studentNames);
            System.out.println("------------------------------------------------------------------------------------");
        }

        for (String csvLine : csvLines) {
            System.out.println(csvLine);
        }
		return "test";
	}

	@ResponseBody
    @RequestMapping(value = "/project/upload", method = RequestMethod.POST)
    public String uploadProjectImage(@RequestParam("croppedImage") MultipartFile croppedImage) {
        BufferedOutputStream bufferedOutputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            String filename = "test.jpg";
            String directory = "/Users/r4ll3/Development/Web/openReqReleasePlanning/src/main/resources/public/upload";
            String filepath = Paths.get(directory, filename).toString();

            File outputFile = new File(filepath);
            fileOutputStream = new FileOutputStream(outputFile);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(croppedImage.getBytes());
            bufferedOutputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "NOT OK!";
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		return "OK! Moved: " + croppedImage.getOriginalFilename() + " " + croppedImage.getSize();
	}

	@ResponseBody
    @GetMapping("/project/{projectUniqueKey}/export")
    public ProjectDto exportProject(@PathVariable(value="projectUniqueKey") String projectUniqueKey, Authentication authentication) {
        ProjectDbo project = projectRepository.findOneByUniqueKey(projectUniqueKey);
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(project.getId());
        projectDto.setName(project.getName());
        projectDto.setDescription(project.getDescription());

        // project settings
        /*
        Map<String, Object> projectSettingsData = new HashMap<>();
        ProjectSettingsDbo projectSettings = project.getProjectSettings();
        projectSettingsData.put("isReadOnly", projectSettings.isReadOnly());
        projectSettingsData.put("isDependencyAnalysisProject", projectSettings.isDependencyAnalysisProject());
        projectData.put("projectSettings", projectSettingsData);
        */

        List<RequirementDbo> unassignedRequirements = project.getRequirements().stream().filter(r -> r.getRelease() == null).collect(Collectors.toList());
        List<RequirementDto> unassignedRequirementDtos = new ArrayList<>();
        for (RequirementDbo unassignedRequirement : unassignedRequirements) {
            RequirementDto requirementDto = new RequirementDto();
            requirementDto.setId(unassignedRequirement.getId());
            requirementDto.setTitle(unassignedRequirement.getTitle());
            requirementDto.setDescription(unassignedRequirement.getDescription());
            requirementDto.setProjectSpecificRequirementId(unassignedRequirement.getProjectSpecificRequirementId());
            requirementDto.setReleaseID(0L);
            unassignedRequirementDtos.add(requirementDto);
        }
        projectDto.setUnassignedRequirements(unassignedRequirementDtos);

        List<ReleaseDto> releaseDtos = new ArrayList<>();
        for (ReleaseDbo release : project.getSortedReleases()) {
            ReleaseDto releaseDto = new ReleaseDto();
            releaseDto.setId(release.getId());
            releaseDto.setName(release.getName());
            releaseDto.setDescription(release.getDescription());
            releaseDto.setMaximumCapacity(release.getCapacity());
            releaseDto.setEndDateTimestamp(release.getEndDate().getTime());
            releaseDto.setVisible(release.isVisible());
            releaseDto.setStatus(release.getStatus());
            List<RequirementDto> requirementDtos = new ArrayList<>();

            for (RequirementDbo requirement : release.getRequirements()) {
                RequirementDto requirementDto = new RequirementDto();
                requirementDto.setId(requirement.getId());
                requirementDto.setTitle(requirement.getTitle());
                requirementDto.setDescription(requirement.getDescription());
                requirementDto.setProjectSpecificRequirementId(requirement.getProjectSpecificRequirementId());
                requirementDto.setStatus(requirement.getStatus());
                requirementDto.setReleaseID(release.getId());
                requirementDtos.add(requirementDto);
            }
            releaseDto.setRequirements(requirementDtos);
            releaseDtos.add(releaseDto);
        }
        projectDto.setReleases(releaseDtos);
        return projectDto;
	}

	@ResponseBody
    @PostMapping("/project/{projectUniqueKey}/import")
    public String importProject(@PathVariable(value="projectUniqueKey") String projectUniqueKey,
                                @RequestBody ProjectDto projectDto, Authentication authentication) {
        ProjectDbo project = projectRepository.findOneByUniqueKey(projectUniqueKey);
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());

        for (RequirementDto unassignedRequirementDto : projectDto.getUnassignedRequirements()) {
            RequirementDbo requirement = new RequirementDbo();
            requirement.setTitle(unassignedRequirementDto.getTitle());
            requirement.setDescription(unassignedRequirementDto.getDescription());
            requirement.setProjectSpecificRequirementId(unassignedRequirementDto.getProjectSpecificRequirementId());
            requirement.setProject(project);
            requirement.setRelease(null);
            project.addRequirement(requirement);
            requirementRepository.save(requirement);
        }

        for (ReleaseDto releaseDto : projectDto.getReleases()) {
            ReleaseDbo release = new ReleaseDbo();
            release.setName(releaseDto.getName());
            release.setDescription(releaseDto.getDescription());
            release.setCapacity(releaseDto.getMaximumCapacity());
            release.setEndDate(new Date(releaseDto.getEndDateTimestamp()));
            release.setVisible(releaseDto.isVisible());
            release.setStatus(releaseDto.getStatus());
            release.setProject(project);
            releaseRepository.save(release);
            project.addRelease(release);

            for (RequirementDto requirementDto : releaseDto.getRequirements()) {
                RequirementDbo requirement = new RequirementDbo();
                requirement.setTitle(requirementDto.getTitle());
                requirement.setDescription(requirementDto.getDescription());
                requirement.setProjectSpecificRequirementId(requirementDto.getProjectSpecificRequirementId());
                requirement.setStatus(requirementDto.getStatus());
                requirement.setProject(project);
                requirement.setRelease(release);
                release.addRequirement(requirement);
                project.addRequirement(requirement);
                requirementRepository.save(requirement);
            }
        }

        projectRepository.save(project);
        return "ok!";
	}

    @RequestMapping("/project/list")
    public String projectList(Model model, HttpServletResponse http, Authentication authentication) {
        if ((authentication == null) || !authentication.isAuthenticated()) {
			try {
				http.sendRedirect("/");
				return null;
			} catch (IOException ex) {}
			return "project_list";
		}

		User userInfo = (User) authentication.getPrincipal();
		UserDbo currentUser = userRepository.findOneByUsername(userInfo.getUsername());

		List<ProjectDbo> allCreatedProjects = currentUser.getCreatedProjects()
				.stream()
				.filter(p -> p.isVisible())
				.filter(p -> ((p.getCreatorUser() != null) && (p.getCreatorUser().getId() == currentUser.getId())))
				.sorted((p1, p2) -> Long.compare(p1.getCreatedDate().getTime(), p2.getCreatedDate().getTime()))
				.collect(Collectors.toList());

		List<ProjectDbo> allUserInvitedProjects = currentUser.getProjectUserParticipations()
				.stream()
				.map(p -> p.getProject())
				.filter(p -> p.isVisible())
				.sorted((p1, p2) -> Long.compare(p1.getCreatedDate().getTime(), p2.getCreatedDate().getTime()))
				.collect(Collectors.toList());

		List<ProjectDbo> allGuestUserInvitedProjects = projectGuestUserParticipationRepository.findByEmailAddress(currentUser.getMailAddress())
				.stream()
				.map(p -> p.getProject())
				.filter(p -> p.isVisible())
				.sorted((p1, p2) -> Long.compare(p1.getCreatedDate().getTime(), p2.getCreatedDate().getTime()))
				.collect(Collectors.toList());

		List<ProjectDbo> dependencyAnalysisProjects = StreamSupport.stream(projectRepository.findAll().spliterator(), false)
                .filter(p -> p.isVisible())
				.filter(p -> p.getProjectSettings().isDependencyAnalysisProject())
				.filter(p -> p.getProjectSettings().isDependencyAnalysisProjectVisible())
				.sorted((p1, p2) -> Long.compare(p1.getCreatedDate().getTime(), p2.getCreatedDate().getTime()))
				.collect(Collectors.toList());

		List<ProjectDbo> allInvitedProjects = new ArrayList<>(allGuestUserInvitedProjects);
		allInvitedProjects.addAll(allUserInvitedProjects);

		/*
		if (currentUser.isAdministrator()) {
            List<ProjectDbo> allOtherProjects = StreamSupport.stream(projectRepository.findAll().spliterator(), false)
                    .filter(p -> p.isVisible())
                    .filter(p -> !allCreatedProjects.contains(p))
                    .filter(p -> !allUserInvitedProjects.contains(p))
                    .filter(p -> !allGuestUserInvitedProjects.contains(p))
                    .collect(Collectors.toList());
            allInvitedProjects.addAll(allOtherProjects);
        }
        */

		model.addAttribute("allCreatedProjects", allCreatedProjects);
		model.addAttribute("allInvitedProjects", allInvitedProjects);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("isAdmin", currentUser.isAdministrator());
	    return "project_list";
    }


    @RequestMapping("/project/list/others")
    public String projectListOthers(Model model,
                                    @RequestParam(value="page", required=true) Integer page,
                                    HttpServletRequest request,
                                    HttpServletResponse response,
                                    Authentication authentication)
    {
        if ((authentication == null) || !authentication.isAuthenticated()) {
            try {
                response.sendRedirect("/");
                return null;
            } catch (IOException ex) {}
            return "project_list";
        }

        User userInfo = (User) authentication.getPrincipal();
        UserDbo currentUser = userRepository.findOneByUsername(userInfo.getUsername());

        if (!currentUser.isAdministrator()) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }

        List<ProjectDbo> allCreatedProjects = currentUser.getCreatedProjects()
                .stream()
                .filter(p -> p.isVisible())
                .filter(p -> ((p.getCreatorUser() != null) && (p.getCreatorUser().getId() == currentUser.getId())))
                .sorted((p1, p2) -> Long.compare(p1.getCreatedDate().getTime(), p2.getCreatedDate().getTime()))
                .collect(Collectors.toList());

        List<ProjectDbo> allUserInvitedProjects = currentUser.getProjectUserParticipations()
                .stream()
                .map(p -> p.getProject())
                .filter(p -> p.isVisible())
                .sorted((p1, p2) -> Long.compare(p1.getCreatedDate().getTime(), p2.getCreatedDate().getTime()))
                .collect(Collectors.toList());

        List<ProjectDbo> allGuestUserInvitedProjects = projectGuestUserParticipationRepository.findByEmailAddress(currentUser.getMailAddress())
                .stream()
                .map(p -> p.getProject())
                .filter(p -> p.isVisible())
                .sorted((p1, p2) -> Long.compare(p1.getCreatedDate().getTime(), p2.getCreatedDate().getTime()))
                .collect(Collectors.toList());

        List<ProjectDbo> allOtherProjects = StreamSupport.stream(projectRepository.findAll().spliterator(), false)
                .filter(p -> p.isVisible())
                .filter(p -> !allCreatedProjects.contains(p))
                .filter(p -> !allUserInvitedProjects.contains(p))
                .filter(p -> !allGuestUserInvitedProjects.contains(p))
                .sorted(comparing(ProjectDbo::getId).reversed())
                .collect(Collectors.toList());

        int itemsPerPage = 9;
        int numOfPages = (int) Math.ceil(allOtherProjects.size() / ((float) itemsPerPage));
        int currentPageNumber = Math.max(Math.min(page, numOfPages), 1);
        int startIndex = (currentPageNumber-1)*itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, allOtherProjects.size());
        allOtherProjects = allOtherProjects.subList(startIndex, endIndex);

        model.addAttribute("allOtherProjects", allOtherProjects);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("isAdmin", currentUser.isAdministrator());
        model.addAttribute("currentPageNumber", currentPageNumber);
        model.addAttribute("numOfPages", numOfPages);
        return "project_list_others";
    }

	@GetMapping("/project/p/{projectUniqueKey}/manage")
	public String manageProject(HttpServletRequest request, HttpServletResponse response, @PathVariable(value="projectUniqueKey") String projectUniqueKey, Authentication authentication, Model model) {
		ProjectDbo project = projectRepository.findOneByUniqueKey(projectUniqueKey);
		if (project == null) {
			return "redirect:/";
		}

        long currentUserId = 0;
		UserDbo currentUser = null;
		if (project.isVisibilityPrivate()) {
			if ((authentication == null) || (!authentication.isAuthenticated()) || project.getCreatorUser() == null) {
				return "redirect:/login";
			}
			User userInfo = (User) authentication.getPrincipal();
			currentUser = userRepository.findOneByUsername(userInfo.getUsername());
			currentUserId = currentUser.getId();

			// redeem invitation
			List<String> emailAddressesOfInvitedGuestUsers = project.getGuestUserParticipations().stream().map(p -> p.getEmailAddress()).collect(Collectors.toList());
			if (emailAddressesOfInvitedGuestUsers.contains(currentUser.getMailAddress())) {
				List<ProjectGuestUserParticipationDbo> guestUserParticipations = projectGuestUserParticipationRepository.findByProjectIdAndEmailAddress(project.getId(), currentUser.getMailAddress());
				List<ProjectUserParticipationDbo> userParticipations = projectUserParticipationRepository.findByProjectIdAndUserId(project.getId(), currentUser.getId());

				ProjectGuestUserParticipationDbo guestUserParticipation = guestUserParticipations.size() > 0 ? guestUserParticipations.get(0) : null;
				ProjectUserParticipationDbo userParticipation = userParticipations.size() > 0 ? userParticipations.get(0) : null;

				if (userParticipation == null) {
					userParticipation = new ProjectUserParticipationDbo(project, currentUser);
					userParticipation.setInvitedDate(new Date()); // FIXME: use date of guestUserPatricipation object...
					userParticipation.setAccepted(true);
					//currentUser.addProjectUserParticipation(newUserParticipation);
					project.addUserParticipation(userParticipation);
				}

				project.removeGuestUserParticipation(guestUserParticipation);
				projectRepository.save(project);
				projectGuestUserParticipationRepository.delete(guestUserParticipation);
			}

			List<ProjectUserParticipationDbo> userParticipations = projectUserParticipationRepository.findByProjectIdAndUserId(project.getId(), currentUser.getId());
			ProjectUserParticipationDbo userParticipation = userParticipations.size() > 0 ? userParticipations.get(0) : null;
			if (!currentUser.isAdministrator() && (project.getCreatorUser().getId() != currentUser.getId()) && (userParticipation == null)) {
				return "redirect:/";
			}

			if ((userParticipation != null) && (!userParticipation.isAccepted())) {
				userParticipation.setAccepted(true);
				projectUserParticipationRepository.save(userParticipation);
			}
		} else {
			if ((authentication != null) && authentication.isAuthenticated()) {
				new SecurityContextLogoutHandler().logout(request, response, authentication);
			}
		}

        if ((project.getRatingAttributes() == null) || (project.getRatingAttributes().size() == 0) || (project.getStakeholderRatingAttributes().size() == 0)) {
            Utils.generateDefaultRatingSchemeAndAddToProject(project);
            projectRepository.save(project);
        }

        ProjectSettingsDbo settings = projectSettingsRepository.findOne(project.getId());
		model.addAttribute("currentUserID", currentUserId);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("project", project);
		model.addAttribute("projectSettings", settings);
		return "project_manage";
	}

	@GetMapping("/project/p/{projectUniqueKey}/visibility/set/{visibilityState}")
	public String setVisibilityOfProject(
			@PathVariable(value="projectUniqueKey") String projectUniqueKey,
			@PathVariable(value="visibilityState") String visibilityState,
			Authentication authentication,
			HttpServletRequest request,
			HttpServletResponse response)
	{
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOneByUniqueKey(projectUniqueKey);

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }

        // FIXME: disabled during study {
        return "redirect:/project/p/" + projectUniqueKey + "/manage";
        // }

        /*
        boolean isNewVibilityStatePrivate = visibilityState.equals("private");
		ProjectDbo project = projectRepository.findOneByUniqueKey(projectUniqueKey);
		project.setVisibilityPrivate(isNewVibilityStatePrivate);
		project.setCreatorUser(isNewVibilityStatePrivate ? currentUser : null);
		projectRepository.save(project);

		for (RatingAttributeDbo ratingAttributeID : project.getRatingAttributes()) {
			for (AnonymousUserRequirementAttributeVoteDbo vote : anonymousUserRequirementAttributeVoteRepository.findByRatingAttributeID(ratingAttributeID.getId())) {
				anonymousUserRequirementAttributeVoteRepository.delete(vote);
			}

			for (UserRequirementAttributeVoteDbo vote : userRequirementAttributeVoteRepository.findByRatingAttributeID(ratingAttributeID.getId())) {
				userRequirementAttributeVoteRepository.delete(vote);
			}
		}

		if (project.getUserParticipations() != null) {
            for (ProjectUserParticipationDbo userParticipation : project.getUserParticipations()) {
                userParticipation = projectUserParticipationRepository.findByProjectIdAndUserId(userParticipation.getProjectId(), userParticipation.getUserId()).get(0);
                project.removeUserParticipation(userParticipation);
                projectUserParticipationRepository.delete(userParticipation);
            }
        }

        if (project.getGuestUserParticipations() != null) {
            for (ProjectGuestUserParticipationDbo userParticipation : project.getGuestUserParticipations()) {
                userParticipation = projectGuestUserParticipationRepository.findByProjectIdAndEmailAddress(userParticipation.getProjectId(), userParticipation.getEmailAddress()).get(0);
                project.removeGuestUserParticipation(userParticipation);
                projectGuestUserParticipationRepository.delete(userParticipation);
            }
        }

		if (!isNewVibilityStatePrivate && (authentication != null) && authentication.isAuthenticated()) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		return "redirect:/project/p/" + projectUniqueKey + "/manage";
		*/
	}

	@ResponseBody
	@GetMapping("/project/{projectID}/user/list.json")
    public Map<String, Object> participatingUserListJson(
			HttpServletRequest request,
			@PathVariable(value="projectID") Long projectID,
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

		Map<String, Object> creatorUserData = new HashMap<>();
		UserDbo creatorUser = project.getCreatorUser();
		if (creatorUser != null) {
			creatorUserData.put("id", creatorUser.getId());
			creatorUserData.put("firstName", creatorUser.getFirstName());
			creatorUserData.put("lastName", creatorUser.getLastName());
			creatorUserData.put("email", creatorUser.getMailAddress());
            creatorUserData.put("profileImagePath", creatorUser.getProfileImagePath());
            result.put("creatorUser", creatorUserData);
		} else {
			result.put("creatorUser", null);
		}

		List<ProjectUserParticipationDbo> projectUserParticipations = new ArrayList<>(project.getUserParticipations());
		projectUserParticipations.sort(Comparator.comparing(ProjectUserParticipationDbo::getUserId));
		List<Map<String, Object>> userParticipationList = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		for (ProjectUserParticipationDbo userParticipation : projectUserParticipations) {
			Map<String, Object> userParticipationData = new HashMap<>();
	        String invitedDate = dateFormat.format(userParticipation.getInvitedDate());
			UserDbo user = userParticipation.getUser();
			userParticipationData.put("id", user.getId());
			userParticipationData.put("firstName", user.getFirstName());
			userParticipationData.put("lastName", user.getLastName());
			userParticipationData.put("email", user.getMailAddress());
            userParticipationData.put("profileImagePath", user.getProfileImagePath());
			userParticipationData.put("invitedDate", invitedDate);
			userParticipationData.put("isAccepted", userParticipation.isAccepted());
			userParticipationList.add(userParticipationData);
		}

		List<ProjectGuestUserParticipationDbo> projectGuestUserParticipations = new ArrayList<>(project.getGuestUserParticipations());
		projectGuestUserParticipations.sort(Comparator.comparing(ProjectGuestUserParticipationDbo::getEmailAddress));
		List<Map<String, Object>> userGuestParticipationList = new ArrayList<>();

		for (ProjectGuestUserParticipationDbo guestUserParticipation : projectGuestUserParticipations) {
			Map<String, Object> guestUserParticipationData = new HashMap<>();
			String invitedDate = dateFormat.format(guestUserParticipation.getInvitedDate());
			guestUserParticipationData.put("email", guestUserParticipation.getEmailAddress());
			guestUserParticipationData.put("invitedDate", invitedDate);
			guestUserParticipationData.put("isAccepted", guestUserParticipation.isAccepted());
			userGuestParticipationList.add(guestUserParticipationData);
		}

		result.put("participatingUsers", userParticipationList);
		result.put("invitedGuestUsers", userGuestParticipationList);
		return result;
    }

	@ResponseBody
	@PostMapping("/project/{projectID}/user/invite.json")
	public Map<String, Object> inviteUser(
			HttpServletRequest request,
			@PathVariable(value="projectID") Long projectID,
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

		List<UserDbo> foundUsers = userService.searchUser(query, query.contains("@"));
		String fullName = currentUser.getFirstName() + " " + currentUser.getLastName();
        // FIXME: disabled during study {
		if (!currentUser.isAdministrator()) {
			result.put("error", true);
			result.put("errorMessage", "This feature has been temporarily disabled.");
			return result;
		}
        // }

		if (foundUsers.size() == 0) {
			if (!emailService.isValidEmailAddress(query)) {
				result.put("error", true);
				result.put("errorMessage", "Cannot invite user! No such user found!");
				return result;
			}

			List<ProjectGuestUserParticipationDbo> guestUserParticipations = projectGuestUserParticipationRepository.findByProjectIdAndEmailAddress(projectID, query);
			if (guestUserParticipations.size() > 0) {
				result.put("error", true);
				result.put("errorMessage", "The user has already received an invitation to the project.");
				return result;
			}

			ProjectGuestUserParticipationDbo guestUserParticipation = new ProjectGuestUserParticipationDbo(project, query);
			guestUserParticipation.setInvitedDate(new Date());
			project.addGuestUserParticipation(guestUserParticipation);
			projectRepository.save(project);

			String htmlMessage = "<p><strong>Hello</strong>,</p>\n" +
                    "<p>" + fullName + " invited you to participate in the project \"" + project.getName() + "\":</p>\n" +
                    "<hr />\n" +
                    "<a class=\"btn\" href=\"https://" + ipService.getHost() + ":" + ipService.getHost() + "/project/p/" + project.getUniqueKey() + "/manage\">View project</a>\n";

			emailService.sendEmailAsync(
				guestUserParticipation.getEmailAddress(),
				"[InnoSensr] Invitation for " + project.getName(),
				htmlMessage,
				fullName + " invited you to participate in the project \"" + project.getName() + "\":</p>\n" +
				"https://" + ipService.getHost() + ":" + ipService.getPort() + "/project/p/" + project.getUniqueKey() + "/manage"
			);

			Map<String, Object> userParticipationData = new HashMap<>();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        String invitedDate = dateFormat.format(guestUserParticipation.getInvitedDate());
			userParticipationData.put("email", guestUserParticipation.getEmailAddress());
			userParticipationData.put("invitedDate", invitedDate);
			userParticipationData.put("isAccepted", guestUserParticipation.isAccepted());

			result.put("error", false);
			result.put("guestUserParticipationData", userParticipationData);
			return result;
		}

		UserDbo userToBeInvited = foundUsers.get(0);

		UserDbo creatorUser = project.getCreatorUser();
		if ((creatorUser != null) && (creatorUser.getId() == userToBeInvited.getId())) {
			result.put("error", true);
			result.put("errorMessage", "Cannot invite the user! The user is the creator of this project.");
			return result;
		}

		List<ProjectUserParticipationDbo> userParticipations = projectUserParticipationRepository.findByProjectIdAndUserId(projectID, userToBeInvited.getId());
		if (userParticipations.size() > 0) {
			result.put("error", true);
			result.put("errorMessage", "Cannot invite the user! The user is already participating in the project.");
			return result;
		}

		ProjectUserParticipationDbo userParticipation = new ProjectUserParticipationDbo(project, userToBeInvited);
		userParticipation.setInvitedDate(new Date());
		project.addUserParticipation(userParticipation);
		projectRepository.save(project);

		String htmlMessage = "<p><strong>Hello " + userToBeInvited.getFirstName() + " " + userToBeInvited.getLastName() + "</strong>,</p>\n" +
                "<p>" + fullName + " invited you to participate in the project \"" + project.getName() + "\":</p>\n" +
                "<hr />\n" +
                "<a class=\"btn\" href=\"https://" + ipService.getHost() + ":" + ipService.getPort() + "/project/p/" + project.getUniqueKey() + "/manage\">View project</a>\n";

		emailService.sendEmailAsync(
			userToBeInvited.getMailAddress(),
			"[InnoSensr] Invitation for " + project.getName(),
			htmlMessage,
			fullName + " invited you to participate in the project \"" + project.getName() + "\":\n" +
			"https://" + ipService.getHost() + ":" + ipService.getPort() + "/project/p/" + project.getUniqueKey() + "/manage"
		);

		Map<String, Object> userParticipationData = new HashMap<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String invitedDate = dateFormat.format(userParticipation.getInvitedDate());
		UserDbo user = userParticipation.getUser();
		userParticipationData.put("id", user.getId());
		userParticipationData.put("firstName", user.getFirstName());
		userParticipationData.put("lastName", user.getLastName());
		userParticipationData.put("email", user.getMailAddress());
        userParticipationData.put("profileImagePath", user.getProfileImagePath());
        userParticipationData.put("invitedDate", invitedDate);
		userParticipationData.put("isAccepted", userParticipation.isAccepted());

		result.put("error", false);
		result.put("userParticipationData", userParticipationData);
		return result;
	}

	@ResponseBody
	@GetMapping("/project/{projectID}/user/{userID}/uninvite.json")
	public Map<String, Object> uninviteUser(
			HttpServletRequest request,
			@PathVariable(value="projectID") Long projectID,
			@PathVariable(value="userID") Long userID,
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

		// FIXME: disabled during study {
		if (!currentUser.isAdministrator()) {
			result.put("error", true);
			result.put("errorMessage", "This feature has been temporarily disabled.");
			return result;
		}
		// }

		UserDbo creatorUser = project.getCreatorUser();
		if ((creatorUser != null) && (creatorUser.getId() == userID)) {
			result.put("error", true);
			result.put("errorMessage", "Cannot remove the user from the project! The user is the creator of this project.");
			return result;
		}

		List<ProjectUserParticipationDbo> userParticipations = projectUserParticipationRepository.findByProjectIdAndUserId(projectID, userID);
		if (userParticipations.size() == 0) {
			result.put("error", true);
			result.put("errorMessage", "Cannot remove the user from the project! The user is not participating in this project (any more).");
			return result;
		}

		project.removeUserParticipation(userParticipations.get(0));
		projectUserParticipationRepository.delete(userParticipations.get(0));
		result.put("error", false);
		return result;
	}

	@ResponseBody
	@PostMapping("/project/{projectID}/user/guest/uninvite.json")
	public Map<String, Object> uninviteGuestUser(
			HttpServletRequest request,
			@PathVariable(value="projectID") Long projectID,
			@RequestParam(value="email", required=true) String emailAddress,
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

        // FIXME: disabled during study {
        if (!currentUser.isAdministrator()) {
            result.put("error", true);
            result.put("errorMessage", "This feature has been temporarily disabled.");
            return result;
        }
		// }

		List<ProjectGuestUserParticipationDbo> guestUserParticipations = projectGuestUserParticipationRepository.findByProjectIdAndEmailAddress(projectID, emailAddress);
		if (guestUserParticipations.size() == 0) {
			result.put("error", true);
			result.put("errorMessage", "Cannot remove the guest user from the project! The guest user is not been invited to this project (any more).");
			return result;
		}

		project.removeGuestUserParticipation(guestUserParticipations.get(0));
		projectGuestUserParticipationRepository.delete(guestUserParticipations.get(0));
		result.put("error", false);
		return result;
	}

	@ResponseBody
	@PostMapping("/project/{projectID}/update.json")
	public Map<String, Object> updateProject(
			HttpServletRequest request,
			@PathVariable(value="projectID") Long projectID,
			@RequestParam(value="projectName", required=false) String projectTitle,
			@RequestParam(value="projectDescription", required=false) String projectDescription,
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
            result.put("errorMessage", "Cannot change the title or description of the project. This project is readonly!");
            return result;
        }

		if (projectTitle != null) {
			project.setName(projectTitle);
		}

		if (projectDescription != null) {
			project.setDescription(projectDescription);
		}

		projectRepository.save(project);
		result.put("error", false);
		return result;
    }

	@GetMapping("/project/generate")
	public String generateProject(Authentication authentication, HttpServletRequest request) {
		UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		ProjectDbo project = null;

		try {
	        Date projectStart = dateformat.parse("2018-01-01");
	        Date projectEnd = dateformat.parse("2018-12-31");
	        project = new ProjectDbo(Utils.generateRandomProjectKey(projectRepository), "Untitled Project",
                    "Some description.", projectStart, projectEnd, "/images/innosensr_logo.png",
                    (currentUser != null), currentUser);
	        Utils.generateDefaultRatingSchemeAndAddToProject(project);

	        if (currentUser != null) {
	            currentUser.addCreatedProject(project);
	        } else {
				String clientIpAddress = request.getRemoteAddr();
				String forwardedIpAddress = request.getHeader("X-FORWARDED-FOR");
				String userAgentInfo = request.getHeader("User-Agent");

				project.setCreatorUserIpAddress(clientIpAddress);
				project.setCreatorUserForwardedIpAddress(forwardedIpAddress);
	        	project.setCreatorUserAgent(userAgentInfo);
	        	project.setCreatorUserSessionID(request.getRequestedSessionId());
			}

            ProjectSettingsDbo settings = new ProjectSettingsDbo();
            projectRepository.save(project);
	        settings.setId(project.getId());
            settings.setProject(project);
            settings.setReadOnly(false);
            settings.setDependencyAnalysisProject(false);
            settings.setDependencyAnalysisProjectVisible(false);
            settings.setShowStatistics(false);
            settings.setTwitterChannel("");
			settings.setEvaluationMode(BASIC);
			project.setProjectSettings(settings);
	        projectRepository.save(project);
		} catch (Exception e) {
			System.out.println(e.getClass().getSimpleName());
			System.out.println(e.getMessage());
			return null;
		}
		return "redirect:/project/p/" + project.getUniqueKey() + "/manage";
	}

	@GetMapping("/project/generatesportswatchproject")
	public String generateHelsinkiProject(HttpServletRequest request, Authentication authentication) {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);

        if (!currentUser.isAdministrator()) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		ProjectDbo project = null;

		try {
	        Date projectStart = dateformat.parse("2019-01-01");
	        Date projectEnd = dateformat.parse("2020-01-31");
	        project = new ProjectDbo(Utils.generateRandomProjectKey(projectRepository), "Sportswatch project",
                    "Some description.", projectStart, projectEnd, "/images/innosensr_logo.png",
                    (currentUser != null), currentUser);
	        Utils.generateDefaultRatingSchemeAndAddToProject(project);
	        if (currentUser != null) {
	            currentUser.addCreatedProject(project);
	        }

			RequirementDbo requirement1 = new RequirementDbo(1L, "Evaluation Software",
					"For evaluating the recorded training data, an evaluation software is required. This "+
							"software requires the connection and the access to the clock's internal memory. The "+
							"evaluation should contain measured information regarding the distance, the height, "+
							"the average heart rate, and the calorie consumption.", project);
			RequirementDbo requirement2 = new RequirementDbo(2L, "Distance Measurement",
					"For statistical purposes, a distance measurement is necessary which needs data from a GPS "+
							"sensor. This data is needed for the evaluation software and therefore stored in memory.",
					project);
			RequirementDbo requirement3 = new RequirementDbo(3L, "GPS",
					"To capture position data, a GPS sensor should be used. Through the measured position and "+
							"time information, the speed and the distance can be measured.", project);
			RequirementDbo requirement4 = new RequirementDbo(4L, "Speed Measurement",
					"As evaluation after a workout, the average speed must be shown. The following statistics "+
							"should be displayed: average speed and maximum speed. For measuring the average and maximum "+
							"speed, time and distance have to be measured, and a storage unit for storing the data is "+
							"necessary.", project);
			RequirementDbo requirement5 = new RequirementDbo(5L, "Ideal BMI",
					"Based on the data on height, weight, body fat, age and gender, the watch should be able "+
							"to calculate the ideal BMI for a user.", project);
			RequirementDbo requirement6 = new RequirementDbo(6L, "Infrared",
					"In order to be able to connect the watch with a computer, WLAN, Bluetooth, and infrared "+
							"modules must be available.", project);
			RequirementDbo requirement7 = new RequirementDbo(7L, "Data-Storage Function",
					"For evaluation purposes, the data should be stored in "+
							"the internal memory. The memory is used for saving the measured information such as the distance, "+
							"the height, the average heart rate, and the calorie consumption. The stored data in the memory will "+
							"then be used by the evaluation software.", project);
			RequirementDbo requirement8 = new RequirementDbo(8L, "Time Measurement",
					"The clock must have an internal timer which is used for saving the current time and the "+
							"measured time during a workout. This time information is then stored in memory and used for "+
							"further evaluation of the data.", project);
			requirement1.setSocialPopularity(0.21f);
			requirement2.setSocialPopularity(0.12f);
			requirement3.setSocialPopularity(0.22f);
			requirement4.setSocialPopularity(0.10f);
			requirement5.setSocialPopularity(0.04f);
			requirement6.setSocialPopularity(0.11f);
			requirement7.setSocialPopularity(0.09f);
			requirement8.setSocialPopularity(0.11f);

            Date deadlineOfRelease1 = dateformat.parse("2019-03-31");
            Date deadlineOfRelease2 = dateformat.parse("2019-04-15");
            Date deadlineOfRelease3 = dateformat.parse("2020-04-30");
			ReleaseDbo release1 = new ReleaseDbo("Release 1", "", deadlineOfRelease1, 1400, project);
			ReleaseDbo release2 = new ReleaseDbo("Release 2", "", deadlineOfRelease2, 900, project);
			ReleaseDbo release3 = new ReleaseDbo("Release 3", "", deadlineOfRelease3, 500, project);
			requirement1.setRelease(release1);
			requirement2.setRelease(release1);
			requirement3.setRelease(release1);
			requirement7.setRelease(release1);
			requirement8.setRelease(release1);

			requirement4.setRelease(release2);

			requirement5.setRelease(release3);
			requirement6.setRelease(release3);

			requirementRepository.save(requirement1);
			requirementRepository.save(requirement2);
			requirementRepository.save(requirement3);
			requirementRepository.save(requirement4);
			requirementRepository.save(requirement5);
			requirementRepository.save(requirement6);
			requirementRepository.save(requirement7);
			requirementRepository.save(requirement8);

			dependencyRepository.save(new DependencyDbo(requirement1, requirement2, DependencyDbo.Type.REQUIRES));
			dependencyRepository.save(new DependencyDbo(requirement1, requirement7, DependencyDbo.Type.REQUIRES));
			dependencyRepository.save(new DependencyDbo(requirement1, requirement8, DependencyDbo.Type.REQUIRES));

			dependencyRepository.save(new DependencyDbo(requirement2, requirement3, DependencyDbo.Type.REQUIRES));
			dependencyRepository.save(new DependencyDbo(requirement2, requirement7, DependencyDbo.Type.REQUIRES));

			dependencyRepository.save(new DependencyDbo(requirement4, requirement1, DependencyDbo.Type.REQUIRES));
			dependencyRepository.save(new DependencyDbo(requirement4, requirement2, DependencyDbo.Type.REQUIRES));
			dependencyRepository.save(new DependencyDbo(requirement4, requirement3, DependencyDbo.Type.REQUIRES));
			dependencyRepository.save(new DependencyDbo(requirement4, requirement7, DependencyDbo.Type.REQUIRES));
			dependencyRepository.save(new DependencyDbo(requirement4, requirement8, DependencyDbo.Type.REQUIRES));

			dependencyRepository.save(new DependencyDbo(requirement5, requirement1, DependencyDbo.Type.REQUIRES));

			dependencyRepository.save(new DependencyDbo(requirement8, requirement7, DependencyDbo.Type.REQUIRES));

			release1.addRequirement(requirement1);
			release1.addRequirement(requirement2);
			release1.addRequirement(requirement3);
			release1.addRequirement(requirement7);
			release1.addRequirement(requirement8);

			release2.addRequirement(requirement4);

			release3.addRequirement(requirement5);
			release3.addRequirement(requirement6);

			releaseRepository.save(release1);
			releaseRepository.save(release2);
			releaseRepository.save(release3);
            projectRepository.save(project);

            ProjectSettingsDbo projectSettings = new ProjectSettingsDbo();
            projectSettings.setId(project.getId());
            projectSettings.setProject(project);
            projectSettings.setShowStatistics(true);
            projectSettings.setReadOnly(false);
            projectSettings.setDependencyAnalysisProject(false);
            projectSettings.setDependencyAnalysisProjectVisible(false);
            projectSettings.setTwitterChannel("#FitbitSupport");
            projectSettings.setEvaluationMode(NORMAL);
            project.setLastProjectSpecificRequirementId(8);
            project.setProjectSettings(projectSettings);
			projectRepository.save(project);
		} catch (Exception e) {
			System.out.println(e.getClass().getSimpleName());
			System.out.println(e.getMessage());
			return null;
		}
		return "redirect:/project/p/" + project.getUniqueKey() + "/manage";
	}

	@GetMapping("/project/generateoadstudyproject/type/{type}")
	public String generateOADStudyProject(HttpServletRequest request, Authentication authentication, @PathVariable(value="type") Integer type) {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);

        if (!currentUser.isAdministrator()) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }

		if (type < 0 || type > 2) {
			return "redirect:/";
		}

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		ProjectDbo project = null;
        String[] linkToManual = { Constants.LINK_MANUAL_BASIC, Constants.LINK_MANUAL_NORMAL, Constants.LINK_MANUAL_ADVANCED };

        try {
            Date projectStart = dateformat.parse("2018-10-01");
            Date projectEnd = dateformat.parse("2019-01-31");
            project = new ProjectDbo(Utils.generateRandomProjectKey(projectRepository), "T-REC project",
                    "Intelligent Tourist Recommendation. Link to manual: " + linkToManual[type],
                    projectStart, projectEnd, "/images/innosensr_logo.png",
                    (currentUser != null), currentUser);
            Utils.generateDefaultRatingSchemeAndAddToProject(project);
            if (currentUser != null) {
                currentUser.addCreatedProject(project);
            }

            Date deadlineOfRelease1 = dateformat.parse("2018-11-09");
            Date deadlineOfRelease2 = dateformat.parse("2018-12-04");
            Date deadlineOfRelease3 = dateformat.parse("2019-01-22");
            ReleaseDbo release1 = new ReleaseDbo("Übungsblatt 2", "", deadlineOfRelease1, 14000, project);
            ReleaseDbo release2 = new ReleaseDbo("Übungsblatt 3", "", deadlineOfRelease2, 14000, project);
            ReleaseDbo release3 = new ReleaseDbo("Übungsblatt 4", "", deadlineOfRelease3, 14000, project);

            releaseRepository.save(release1);
            releaseRepository.save(release2);
            releaseRepository.save(release3);
            projectRepository.save(project);

            ProjectSettingsDbo projectSettings = new ProjectSettingsDbo();
            projectSettings.setId(project.getId());
            projectSettings.setProject(project);
            projectSettings.setReadOnly(true);
            projectSettings.setDependencyAnalysisProject(false);
            projectSettings.setDependencyAnalysisProjectVisible(false);
            projectSettings.setShowDependencies(false);
            projectSettings.setShowStatistics(false);
            projectSettings.setShowSocialPopularityIndicator(false);
            projectSettings.setShowStakeholderAssignment(false);
            projectSettings.setShowAmbiguityAnalysis(false);
            if (type == 0) {
				projectSettings.setEvaluationMode(BASIC);
			} else if (type == 1) {
            	projectSettings.setEvaluationMode(NORMAL);
			} else {
            	projectSettings.setEvaluationMode(ADVANCED);
			}
            projectSettings.setTwitterChannel("#FitbitSupport");
            project.setProjectSettings(projectSettings);
            projectRepository.save(project);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
            return null;
        }
		return "redirect:/project/p/" + project.getUniqueKey() + "/manage";
	}

	/*
	@GetMapping("/project/generatealloadstudyprojects")
	public String generateAllOADStudyProject(HttpServletRequest request, Authentication authentication) {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);

        if (!currentUser.isAdministrator()) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] linkToManual = { Constants.LINK_MANUAL_BASIC, Constants.LINK_MANUAL_NORMAL, Constants.LINK_MANUAL_ADVANCED };
		ProjectDbo project;

		int groupCounter = 1;
        Map<Long, List<CSVReader.StudentDto>> groupStudents = CSVReader.parseStudents();
        List<EmailService.EmailData> emails = new ArrayList<>();
        for (Map.Entry<Long, List<CSVReader.StudentDto>> entry : groupStudents.entrySet()) {
            long groupNumber = entry.getKey();
            int evaluationType = (groupCounter % 3);
            List<CSVReader.StudentDto> studentsOfGroup = entry.getValue();

            System.out.println("-----------------------------");
            System.out.println("Group number: " + groupNumber);

            try {
                Date projectStart = dateFormat.parse("2018-10-01");
                Date projectEnd = dateFormat.parse("2019-01-31");
                project = new ProjectDbo(Utils.generateRandomProjectKey(projectRepository, 22),
						"T-REC project (Group " + groupNumber + ")",
                        "Intelligent Tourist Recommendation. Link to manual: " + linkToManual[evaluationType],
                        projectStart, projectEnd, "/images/innosensr_logo.png",
                        true, currentUser);
                Utils.generateDefaultRatingSchemeAndAddToProject(project);
                if (currentUser != null) {
                    currentUser.addCreatedProject(project);
                }

                for (CSVReader.StudentDto student : studentsOfGroup) {
                    System.out.println("Student: " + student.getFirstName() + " " + student.getLastName());
                    UserDbo groupUser = userRepository.findOneByMailAddress(student.getEmail());
                    String password = null;

                    if (groupUser == null) {
                        password = Utils.generateRandomPassword();
                        final String confirmationKey = Utils.generateRandomKey(12);
                        groupUser = new UserDbo();
                        groupUser.setUsername(student.getEmail());
                        groupUser.setFirstName(student.getFirstName());
                        groupUser.setLastName(student.getLastName());
                        groupUser.setMailAddress(student.getEmail());
                        groupUser.setGroupNumber(groupNumber);
                        groupUser.setPassword(passwordEncoder.encode(password));
                        groupUser.setRoles(new HashSet<RoleDbo>() {{
                            add(new RoleDbo(RoleDbo.Role.ROLE_STAKEHOLDER));
                        }});
                        groupUser.setConfirmationKey(confirmationKey);
                        groupUser.setEnabled(false);
                        userRepository.save(groupUser);
                    }

                    ProjectUserParticipationDbo participation = new ProjectUserParticipationDbo(project, groupUser);
                    project.addUserParticipation(participation);

                    // TODO FIXME change Email!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    // TODO FIXME change Email!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    // TODO FIXME change Email!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    // TODO FIXME change Email!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    // TODO FIXME change Email!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    emails.add(new EmailService.EmailData(
                            //student.getEmail(),
                            "ralph.samer@student.tugraz.at",
                            "[InnoSensr] Invitation to register on InnoSensr!",
                            "<p>Dear student,</p><p>we are pleased to inform you that your account and your " +
                                    "group's project (group number: " + groupNumber + ") for the OAD course have been " +
                                    "created. To confirm your participation you are asked to accept the invitation by " +
                                    "clicking on the following link:</p><p><a href=\"https://" + ipService.getHost() +
                                    ":" + ipService.getPort() + "/registration/user/" + groupUser.getId() + "/confirm/" +
                                    groupUser.getConfirmationKey() + "\">Click here to confirm your participation!</a></p>" +
									"<p style=\"color:darkred;\"><b>NOTE:</b> Before you get started, please take a look " +
									"at the following document: " + linkToManual[evaluationType] + "</p>" +
									"<p>Your user credentials are as follows:<br />" +
                                    "Username: " + groupUser.getUsername() + "<br />" + "Password: " +
                                    ((password != null) ? password : "<a href=\"https://" + ipService.getHost() +
                                            ":" + ipService.getPort() + "/forgotpassword\">Click here to reset your password</a>")
                                    + "</p><p>Once your participation has been confirmed by you, you are allowed to log in " +
                                    "and to edit your group's project.</p>" +
                                    "<p>Link to your group's project: <a href=\"https://" + ipService.getHost() +
                                    ":" + ipService.getPort() + "/project/p/" + project.getUniqueKey() + "/manage\">" +
                                    "https://" + ipService.getHost() + ":" + ipService.getPort() + "/project/p/" +
                                    project.getUniqueKey() + "/manage</a></p>" +
                                    "<p>Good Luck!</p>",
                            ""));
                }

                Date deadlineOfRelease1 = dateFormat.parse("2018-11-09");
                Date deadlineOfRelease2 = dateFormat.parse("2018-12-04");
                Date deadlineOfRelease3 = dateFormat.parse("2019-01-22");
                ReleaseDbo release1 = new ReleaseDbo("Übungsblatt 2", "", deadlineOfRelease1, 14000, project);
                ReleaseDbo release2 = new ReleaseDbo("Übungsblatt 3", "", deadlineOfRelease2, 14000, project);
                ReleaseDbo release3 = new ReleaseDbo("Übungsblatt 4", "", deadlineOfRelease3, 14000, project);

                releaseRepository.save(release1);
                releaseRepository.save(release2);
                releaseRepository.save(release3);
                projectRepository.save(project);

                ProjectSettingsDbo.EvaluationMode evaluationMode;
                if (evaluationType == 0) {
                    evaluationMode = BASIC;
                } else if (evaluationType == 1) {
                    evaluationMode = NORMAL;
                } else {
                    evaluationMode = ADVANCED;
                }

                ProjectSettingsDbo projectSettings = new ProjectSettingsDbo();
                projectSettings.setId(project.getId());
                projectSettings.setProject(project);
                projectSettings.setReadOnly(true);
                projectSettings.setDependencyAnalysisProject(false);
                projectSettings.setDependencyAnalysisProjectVisible(false);
                projectSettings.setShowDependencies(false);
                projectSettings.setShowStatistics(false);
                projectSettings.setShowSocialPopularityIndicator(false);
                projectSettings.setShowStakeholderAssignment(false);
                projectSettings.setShowAmbiguityAnalysis(false);
                projectSettings.setEvaluationMode(evaluationMode);
                projectSettings.setTwitterChannel("#FitbitSupport");
                project.setProjectSettings(projectSettings);
                projectRepository.save(project);
                System.out.println("/project/p/" + project.getUniqueKey() + "/manage");
                ++groupCounter;
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
                System.out.println(e.getMessage());
            }
        }

        emailService.sendMassEmailAsync("Dr. DI Martin Stettinger BSc <martin.stettinger@ist.tugraz.at>", emails);
        return "test"; //"redirect:/project/p/" + project.getUniqueKey() + "/manage";
	}
	*/

	@GetMapping("/project/generatedependencyproject")
	public String generateDependencyProject(HttpServletRequest request, Authentication authentication) {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);

        if (!currentUser.isAdministrator()) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ProjectDbo project = null;

        try {
			Date projectStart = dateFormat.parse("2018-10-01");
			Date projectEnd = dateFormat.parse("2019-01-31");
			project = new ProjectDbo(Utils.generateRandomProjectKey(projectRepository, 22),
					"Dependency", "", projectStart, projectEnd, "/images/innosensr_logo.png",
					true, currentUser);
			Utils.generateDefaultRatingSchemeAndAddToProject(project);
			projectRepository.save(project);
            ProjectSettingsDbo projectSettings = new ProjectSettingsDbo();
            projectSettings.setId(project.getId());
            projectSettings.setProject(project);
            projectSettings.setReadOnly(true);
            projectSettings.setDependencyAnalysisProject(true);
            projectSettings.setDependencyAnalysisProjectVisible(false);
            projectSettings.setShowDependencies(false);
            projectSettings.setShowStatistics(false);
            projectSettings.setShowSocialPopularityIndicator(false);
            projectSettings.setShowStakeholderAssignment(false);
            projectSettings.setShowAmbiguityAnalysis(false);
            projectSettings.setEvaluationMode(BASIC);
            projectSettings.setTwitterChannel("#FitbitSupport");
            project.setProjectSettings(projectSettings);
            projectRepository.save(project);
            System.out.println("/project/p/" + project.getUniqueKey() + "/manage");

			List<CSVReader.RequirementDto> requirements = CSVReader.parseRequirements();
			for (CSVReader.RequirementDto requirementDto : requirements) {
				System.out.println("-----------------------------");
				System.out.println("Requirement ID: " + requirementDto.getID());
				System.out.println("Requirement Description: " + requirementDto.getDescription());
				long lastProjectSpecificRequirementId = project.getLastProjectSpecificRequirementId();
				project.incrementLastProjectSpecificRequirementId();
				RequirementDbo requirement = new RequirementDbo((lastProjectSpecificRequirementId + 1),
                        requirementDto.getID(), requirementDto.getDescription(), project);
				requirementRepository.save(requirement);
				project.addRequirement(requirement);
			}

			projectRepository.save(project);
		} catch (Exception e) {
			System.out.println(e.getClass().getSimpleName());
			System.out.println(e.getMessage());
			return null;
		}

        return "redirect:/project/p/" + project.getUniqueKey() + "/manage";
	}

	@GetMapping("/project/{projectID}/delete")
    public String deleteProject(HttpServletRequest request, @PathVariable(value="projectID") Long projectID,
								Authentication authentication)
	{
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);

        if (!project.isCreator(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }

        if (project.getProjectSettings().isReadOnly()) {
            return "redirect:/project/list";
        }

		project.setVisible(false);
		projectRepository.save(project);
		// FIXME: Delete doesn't work correctly with Hibernate -> fix foreign keys/diagnosis!
//		projectRepository.delete(projectID);
		return "redirect:/project/list";
    }
}
