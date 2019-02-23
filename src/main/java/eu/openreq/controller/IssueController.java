package eu.openreq.controller;

import eu.openreq.Util.Utils;
import eu.openreq.api.internal.dto.DependencyDto;
import eu.openreq.api.internal.dto.IssueDto;
import eu.openreq.dbo.*;
import eu.openreq.repository.*;
import eu.openreq.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IssueController {

    private static final Logger logger = LoggerFactory.getLogger(RequirementController.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequirementRepository requirementRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private EmailService emailService;

    @ResponseBody
    @GetMapping("/project/{projectID}/issue/list.json")
    public Map<String, Object> issueListJson(HttpServletRequest request, @PathVariable(value="projectID") Long projectID, Authentication authentication) {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);
        Map<String, Object> result = new HashMap<>();

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

        List<Map<String, Object>> issueList = new ArrayList<>();
        for (IssueDbo issue : project.getIssues()) {
            if (!issue.isVisible()) {
                continue;
            }

            Map<String, Object> issueData = new HashMap<>();
            issueData.put("id", issue.getId());
            issueData.put("title", issue.getName());
            issueData.put("description", issue.getDescription());
            issueData.put("status", issue.getStatus());
            issueData.put("priority", issue.getPriority());
            issueList.add(issueData);
        }

        result.put("issues", issueList);
        return result;
    }

    @ResponseBody
    @PostMapping("/project/{projectID}/issue/create.json")
    public Map<String, Object> createDependencyJson(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
            @RequestBody IssueDto issueDto,
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

        IssueDbo issue = new IssueDbo(issueDto.getTitle(), issueDto.getDescription(), IssueDbo.Status.RESOLVED,
                issueDto.getPriority(), project);
        issueRepository.save(issue);

        result.put("error", false);
        result.put("issueID", issue.getId());
        return result;
    }

    @ResponseBody
    @GetMapping("/project/{projectID}/issue/{issueID}/delete.json")
    public Map<String, Object> deleteIssueJson(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
            @PathVariable(value="issueID") Long issueID,
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

        IssueDbo issue = issueRepository.findOne(issueID);

        if (issue == null) {
            result.put("error", true);
            result.put("errorMessage", "The issue does not exist!");
            return result;
        }

        issue.setVisible(false);
        issueRepository.save(issue);
        result.put("error", false);
        return result;
    }

}
