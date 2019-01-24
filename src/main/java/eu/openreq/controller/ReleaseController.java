package eu.openreq.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import eu.openreq.Util.Utils;
import eu.openreq.dbo.*;
import eu.openreq.dbo.ReleaseUpdateDbo.ActionType;
import eu.openreq.repository.UserRepository;
import eu.openreq.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import eu.openreq.api.internal.dto.ReleaseDto;
import eu.openreq.dbo.ReleaseDbo.Status;
import eu.openreq.repository.ProjectRepository;
import eu.openreq.repository.ReleaseRepository;
import eu.openreq.repository.RequirementRepository;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ReleaseController {

	private static final Logger logger = LoggerFactory.getLogger(ReleaseController.class);

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ReleaseRepository releaseRepository;

	@Autowired
	private RequirementRepository requirementRepository;

	@Autowired
	private EmailService emailService;

	@ResponseBody
	@PostMapping("/project/{projectID}/release/create.json")
	public Map<String, Object> createReleaseJson(
			HttpServletRequest request,
			@PathVariable(value="projectID") Long projectID,
			@RequestBody List<ReleaseDto> releasesDtos,
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

		List<ReleaseDbo> releases = new ArrayList<>();

		for (ReleaseDto releaseDto : releasesDtos) {
			Date endDate = new Date();
			endDate.setTime(releaseDto.getEndDateTimestamp());

			ReleaseDbo release = new ReleaseDbo();
			release.setName(releaseDto.getName());
			release.setDescription(releaseDto.getDescription());
			release.setEndDate(endDate);
			release.setMaximumCapacity(0);
			release.setProject(project);
			release.setVisible(true);
			project.addRelease(release);
			releaseRepository.save(release);
			releases.add(release);
		}

		result.put("error", false);
		result.put("releaseIDs", releases.stream().map(release -> release.getId()).collect(Collectors.toList()));
		return result;
	}

	@ResponseBody
	@PostMapping("/project/{projectID}/release/update.json")
	public Map<String, Object> updateReleaseJson(
			HttpServletRequest request,
			@PathVariable(value="projectID") Long projectID,
			@RequestBody List<ReleaseDto> releaseDtos,
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
			result.put("error", true);
			result.put("errorMessage", "You are not allowed to edit releases! This project is a read-only project.");
			return result;
		}

		List<ReleaseDbo> releases = new ArrayList<>();
		for (ReleaseDto releaseDto : releaseDtos) {
			Date endDate = new Date();
			endDate.setTime(releaseDto.getEndDateTimestamp());

			ReleaseDbo release = releaseRepository.findOne(releaseDto.getId());
			if (release.getProject().getId() != projectID) {
			    continue;
            }

			if (!release.getName().equals(releaseDto.getName())) {
				release.setName(releaseDto.getName());
				release.logReleaseUpdate(ActionType.NAME_CHANGED, currentUser);
			}

			if (!release.getDescription().equals(releaseDto.getDescription())) {
                release.setDescription(releaseDto.getDescription());
                release.logReleaseUpdate(ActionType.DESCRIPTION_CHANGED, currentUser);
            }

            if (release.getEndDate().getTime() != endDate.getTime()) {
                release.setEndDate(endDate);
                release.logReleaseUpdate(ActionType.DEADLINE_CHANGED, currentUser);
            }

            // TODO: support maximum capacity:
			//release.setMaximumCapacity();

			release.setVisible(true);
			releaseRepository.save(release);
			releases.add(release);
		}

		result.put("error", false);
		result.put("releaseIDs", releases.stream().map(release -> release.getId()).collect(Collectors.toList()));
		return result;
	}

	@ResponseBody
	@PostMapping("/project/{projectID}/release/delete.json")
	public Map<String, Object> deleteRelease(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
			@RequestParam(value="releaseID[]", required=true) Long[] releaseIDs,
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
			result.put("error", true);
			result.put("errorMessage", "You are not allowed to delete releases! This project is a read-only project.");
			return result;
		}

        for (long releaseID : releaseIDs) {
			ReleaseDbo release = releaseRepository.findOne(releaseID);
			if ((release == null) || !release.isVisible()) {
			    continue;
            }

			release.setVisible(false);
            release.logReleaseUpdate(ActionType.DELETED, currentUser);
            releaseRepository.save(release);
		}
		result.put("error", false);
		return result;
    }

	@ResponseBody
	@GetMapping("/project/{projectID}/release/list.json")
    public List<Map<String, Object>> releaseListJson(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
            Authentication authentication)
    {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        Map<String, Object> result = new HashMap<>();

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            List<Map<String, Object>> returnData = new ArrayList<>();
            returnData.add(result);
            return returnData;
        }

		List<ReleaseDbo> releases = new ArrayList<>(project.getReleases());
		releases.sort(Comparator.comparing(ReleaseDbo::getEndDate));
		List<ReleaseDbo> newReleases = releases
				.stream()
				.filter(r -> r.getStatus() == Status.NEW)
				.filter(r -> r.isVisible())
				.collect(Collectors.toList());
		List<Map<String, Object>> returnData = new ArrayList<>();
		SimpleDateFormat humanFriendlyDateFormat = new SimpleDateFormat("MMM d");
		for (ReleaseDbo release : newReleases) {
			Map<String, Object> releaseData = new HashMap<>();
			releaseData.put("id", release.getId());
			releaseData.put("title", release.getName());
			releaseData.put("description", release.getDescription());
			releaseData.put("status", release.getStatus());
	        String humanFriendlyEndDate = humanFriendlyDateFormat.format(release.getEndDate());
			releaseData.put("endDateTimestamp", release.getEndDate().getTime());
			releaseData.put("humanFriendlyEndDate", humanFriendlyEndDate);
			returnData.add(releaseData);
		}
		return returnData;
    }

}
