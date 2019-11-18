package eu.openreq.controller;

import java.util.HashMap;
import java.util.Map;
import eu.openreq.Util.Utils;
import eu.openreq.api.internal.dto.ProjectSettingsDto;
import eu.openreq.dbo.ProjectDbo;
import eu.openreq.dbo.ProjectSettingsDbo;
import eu.openreq.dbo.UserDbo;
import eu.openreq.repository.ProjectRepository;
import eu.openreq.repository.ProjectSettingsRepository;
import eu.openreq.repository.UserRepository;
import eu.openreq.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProjectSettingsController {

	private static final Logger logger = LoggerFactory.getLogger(ProjectSettingsController.class);

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectSettingsRepository projectSettingsRepository;

	@Autowired
	private EmailService emailService;

	@ResponseBody
	@GetMapping("/project/{projectID}/settings.json")
	public Map<String, Object> showSettings(
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
            return result;
        }

		ProjectSettingsDbo projectSettings = projectSettingsRepository.findOne(projectID);
        Map<String, Object> settingsData = new HashMap<>();
		if (projectSettings != null) {
            settingsData.put("showDependencies", projectSettings.isShowDependencies());
            settingsData.put("showStatistics", projectSettings.isShowStatistics());
            settingsData.put("showSocialPopularityIndicator", projectSettings.isShowSocialPopularityIndicator());
            settingsData.put("showStakeholderAssignment", projectSettings.isShowStakeholderAssignment());
            settingsData.put("showAmbiguityAnalysis", projectSettings.isShowAmbiguityAnalysis());
            settingsData.put("evaluationMode", projectSettings.getEvaluationMode());
            settingsData.put("twitterChannel", projectSettings.getTwitterChannel());
        } else {
            settingsData.put("showStatistics", false);
            settingsData.put("twitterChannel", "");
        }
		return settingsData;
	}

	@ResponseBody
	@PostMapping("/project/{projectID}/settings/update.json")
	public Map<String, Object> updateSettings(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
            @RequestBody ProjectSettingsDto projectSettingsDto,
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

        ProjectSettingsDbo projectSettings = projectSettingsRepository.findOne(projectID);

        if (projectSettings.isReadOnly()) {
            result.put("error", true);
            result.put("errorMessage", "You are not allowed to change the settings of this project! This is a read-only project.");
            return result;
        }

		projectSettings.setShowDependencies(projectSettingsDto.isShowDependencies());
        projectSettings.setShowStatistics(projectSettingsDto.isShowStatistics());
        projectSettings.setShowSocialPopularityIndicator(projectSettingsDto.isShowSocialPopularityIndicator());
        projectSettings.setShowStakeholderAssignment(projectSettingsDto.isShowStakeholderAssignment());
        projectSettings.setShowAmbiguityAnalysis(projectSettingsDto.isShowAmbiguityAnalysis());
        projectSettings.setEvaluationMode(projectSettingsDto.getEvaluationMode());
        projectSettings.setTwitterChannel(projectSettingsDto.getTwitterChannel());
        projectSettingsRepository.save(projectSettings);

        // TODO: perform Twitter call here
        /*
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        headers.setAll(map);

        HttpEntity<RemoteRequirementDependencyDto[]> twitterRegistrationRequest = new HttpEntity<>(headers);
        // https://api.openreq.eu/ri-orchestration-twitter/hitec/orchestration/twitter/observe/tweet/account/{account_name}/interval/2h/lang/{lang}
        String url = "https://api.openreq.eu/ri-orchestration-twitter/hitec/orchestration/twitter/observe/tweet/account/"
                + projectSettingsDto.getTwitterChannel() + "/interval/2h/lang/en";
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        ResponseEntity<String> response = restTemplate.postForEntity(url,
                twitterRegistrationRequest, String.class);

        if (response.getStatusCodeValue() != 200) {
            result.put("error", true);
            result.put("errorMessage", "Could not register Twitter cronjob!");
            return result;
        }
        */

        result.put("error", false);
		return result;
	}

}
