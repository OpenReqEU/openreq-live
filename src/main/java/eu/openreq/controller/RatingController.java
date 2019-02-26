package eu.openreq.controller;

import eu.openreq.Util.Utils;
import eu.openreq.api.internal.dto.RatingAttributeDto;
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
import java.util.HashMap;
import java.util.Map;

@Controller
public class RatingController {

    private static final Logger logger = LoggerFactory.getLogger(RequirementController.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingAttributeRepository ratingAttributeRepository;

    @Autowired
    private EmailService emailService;

    @ResponseBody
    @PostMapping("/project/{projectID}/rating/attribute/create.json")
    public Map<String, Object> createRatingAttributeJson(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
            @RequestBody RatingAttributeDto ratingAttributeDto,
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

        RatingAttributeDbo ratingAttributeDbo = new RatingAttributeDbo(ratingAttributeDto.getName(),
                ratingAttributeDto.getDescription(), ratingAttributeDto.getIconName(), ratingAttributeDto.getMinValue(),
                ratingAttributeDto.getMaxValue(), ratingAttributeDto.getInterval(), ratingAttributeDto.getWeight(),
                false, project);
        ratingAttributeRepository.save(ratingAttributeDbo);

        result.put("error", false);
        result.put("attributeID", ratingAttributeDbo.getId());
        return result;
    }

    @ResponseBody
    @GetMapping("/project/{projectID}/rating/attribute/{attributeID}/delete.json")
    public Map<String, Object> deleteIssueJson(
            HttpServletRequest request,
            @PathVariable(value="projectID") Long projectID,
            @PathVariable(value="attributeID") Long attributeID,
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

        RatingAttributeDbo ratingAttribute = ratingAttributeRepository.findOne(attributeID);

        if (ratingAttribute == null) {
            result.put("error", true);
            result.put("errorMessage", "The issue does not exist!");
            return result;
        }

        ratingAttributeRepository.delete(ratingAttribute);
        result.put("error", false);
        return result;
    }

}
