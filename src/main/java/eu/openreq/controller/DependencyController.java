package eu.openreq.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.openreq.Util.Utils;
import eu.openreq.api.internal.dto.*;
import eu.openreq.dbo.*;
import eu.openreq.repository.*;
import eu.openreq.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DependencyController {

    private static final Logger logger = LoggerFactory.getLogger(RequirementController.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequirementRepository requirementRepository;

    @Autowired
    private DependencyRepository dependencyRepository;

    @Autowired
    private EmailService emailService;

    @ResponseBody
    @GetMapping("/project/{projectID}/dependency/list.json")
    public List<Map<String, Object>> dependencyListJson(HttpServletRequest request, @PathVariable(value="projectID") Long projectID, Authentication authentication) {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        List<Map<String, Object>> dependencies = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            dependencies.add(result);
            return dependencies;
        }

        for (RequirementDbo requirement : project.getRequirements()) {
            if (!requirement.isVisible()) {
                continue;
            }

            for (DependencyDbo dependency : dependencyRepository.findAllBySourceRequirementID(requirement.getId())) {
                if (!dependency.isVisible() || !dependency.getSourceRequirement().isVisible() || !dependency.getTargetRequirement().isVisible()) {
                    continue;
                }

                Map<String, Object> dependencyData = new HashMap<>();
                dependencyData.put("sourceRequirementID", dependency.getSourceRequirement().getId());
                dependencyData.put("sourceRequirementTitle", dependency.getSourceRequirement().getTitle());
                dependencyData.put("sourceRequirementDescription", dependency.getSourceRequirement().getDescription());
                dependencyData.put("targetRequirementID", dependency.getTargetRequirement().getId());
                dependencyData.put("targetRequirementTitle", dependency.getTargetRequirement().getTitle());
                dependencyData.put("targetRequirementDescription", dependency.getTargetRequirement().getDescription());
                dependencyData.put("type", dependency.getType());
                dependencies.add(dependencyData);
            }
        }
        return dependencies;
    }

    @ResponseBody
    @PostMapping("/project/{projectID}/dependency/create.json")
    public Map<String, Object> createDependencyJson(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
            @RequestBody DependencyDto dependencyDto,
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

        RequirementDbo sourceRequirement = requirementRepository.findOne(dependencyDto.getSourceRequirementID());
        RequirementDbo targetRequirement = requirementRepository.findOne(dependencyDto.getTargetRequirementID());

        if (sourceRequirement.getProject().getId() != projectID) {
            result.put("error", true);
            result.put("errorMessage", "The source requirement does not belong to the given project!");
            return result;
        }

        if (targetRequirement.getProject().getId() != projectID) {
            result.put("error", true);
            result.put("errorMessage", "The target requirement does not belong to the given project!");
            return result;
        }

        DependencyDbo dependency = dependencyRepository.findOneBySourceRequirementIDAndTargetRequirementIDAndType(
                dependencyDto.getSourceRequirementID(), dependencyDto.getTargetRequirementID(),
                dependencyDto.getType());
        if (dependency != null) {
            result.put("error", true);
            result.put("errorMessage", "This dependency already exists!");
            return result;
        }

        dependency = new DependencyDbo(sourceRequirement, targetRequirement, dependencyDto.getType());
        dependencyRepository.save(dependency);

        result.put("error", false);
        Map<String, Object> dependencyData = new HashMap<>();
        dependencyData.put("sourceRequirementID", sourceRequirement.getId());
        dependencyData.put("sourceRequirementTitle", sourceRequirement.getTitle());
        dependencyData.put("sourceRequirementDescription", dependency.getSourceRequirement().getDescription());
        dependencyData.put("targetRequirementID", targetRequirement.getId());
        dependencyData.put("targetRequirementTitle", targetRequirement.getTitle());
        dependencyData.put("targetRequirementDescription", targetRequirement.getDescription());
        dependencyData.put("type", dependencyDto.getType());
        result.put("dependencyInfo", dependencyData);
        return result;
    }

    @ResponseBody
    @PostMapping("/project/{projectID}/dependency/delete.json")
    public Map<String, Object> deleteDependencyJson(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
            @RequestBody DependencyDto dependencyDto,
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

        DependencyDbo dependency = dependencyRepository.findOneBySourceRequirementIDAndTargetRequirementIDAndType(
                dependencyDto.getSourceRequirementID(), dependencyDto.getTargetRequirementID(),
                dependencyDto.getType());

        if (dependency == null) {
            result.put("error", true);
            result.put("errorMessage", "The dependency does not exist!");
            return result;
        }

        dependencyRepository.delete(dependency);
        result.put("error", false);
        return result;
    }

}
