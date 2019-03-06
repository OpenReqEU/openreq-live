package eu.openreq.controller;

import eu.openreq.Util.Utils;
import eu.openreq.api.external.dto.*;
import eu.openreq.dbo.*;
import eu.openreq.repository.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@Api(value="project", description="Operations pertaining to projects of InnoSensr.")
public class APIController {

    private static final Logger logger = LoggerFactory.getLogger(APIController.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ReleaseRepository releaseRepository;

    @Autowired
    private RequirementRepository requirementRepository;

    @Autowired
    private ProjectUserParticipationRepository projectUserParticipationRepository;

    @ApiOperation(value = "Create a new project", response = CreateProjectResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created project"),
            @ApiResponse(code = 400, message = "The project can not be created.")
    })
    @RequestMapping(value="/project/create", method=RequestMethod.POST, produces="application/json")
    @ResponseBody
    public CreateProjectResponseDto createProjectJson(@RequestBody CreateProjectDto createProjectDto)
    {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        CreateProjectResponseDto responseDto = new CreateProjectResponseDto();
        String uniqueKey = Utils.generateRandomProjectKey(projectRepository);

        try {
            Date projectStart = dateformat.parse("2018-01-01");
            Date projectEnd = dateformat.parse("2018-12-31");
            ProjectDbo project = new ProjectDbo(uniqueKey, createProjectDto.getTitle(), createProjectDto.getDescription(),
                    projectStart, projectEnd, "/images/innosensr_small.png",false, null);
            Utils.generateDefaultRatingSchemeAndAddToProject(project);
            projectRepository.save(project);
        } catch (Exception e) {
            responseDto.setError(true);
            responseDto.setErrorMessage(e.getMessage());
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
            return responseDto;
        }

        responseDto.setError(false);
        responseDto.setErrorMessage(null);
        responseDto.setUniqueKey(uniqueKey);
        return responseDto;
    }

    @CrossOrigin(origins = "http://217.172.12.199:9703")
    @RequestMapping(value="/requirement/create", method=RequestMethod.OPTIONS)
    public ResponseEntity<CreateRequirementResponseDto> optionsCreateRequirement() {
        HttpHeaders responseHeaders = new HttpHeaders();
        List<HttpMethod> allowedMethods = new ArrayList<>();
        allowedMethods.add(HttpMethod.GET);
        allowedMethods.add(HttpMethod.POST);
        allowedMethods.add(HttpMethod.OPTIONS);

        List<String> allowedHeaders = new ArrayList<>();
        allowedHeaders.add("Origin");
        allowedHeaders.add("X-Requested-With");
        allowedHeaders.add("Content-Type");
        allowedHeaders.add("Accept");
        allowedHeaders.add("Accept-Encoding");
        allowedHeaders.add("Accept-Language");
        allowedHeaders.add("Access-Control-Request-Headers");
        allowedHeaders.add("Access-Control-Request-Method");
        allowedHeaders.add("Connection");
        allowedHeaders.add("Host");
        allowedHeaders.add("User-Agent");

        responseHeaders.setAccessControlAllowOrigin("*");
        responseHeaders.setAccessControlAllowCredentials(true);
        responseHeaders.setAccessControlAllowMethods(allowedMethods);
        responseHeaders.setAccessControlAllowHeaders(allowedHeaders);
        responseHeaders.set("X-Frame-Options", "*");

        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://217.172.12.199:9703")
    @ApiOperation(value = "Create a new requirement", response = CreateRequirementResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created requirement"),
            @ApiResponse(code = 404, message = "The project can not be created.")
    })
    @RequestMapping(value="/requirement/create", method=RequestMethod.POST, produces="application/json")
    @ResponseBody
    public ResponseEntity<CreateRequirementResponseDto> createRequirementJson(@RequestBody CreateRequirementDto createRequirementDto)
    {
        HttpHeaders responseHeaders = new HttpHeaders();
        List<HttpMethod> allowedMethods = new ArrayList<>();
        allowedMethods.add(HttpMethod.GET);
        allowedMethods.add(HttpMethod.POST);
        allowedMethods.add(HttpMethod.OPTIONS);

        List<String> allowedHeaders = new ArrayList<>();
        allowedHeaders.add("Origin");
        allowedHeaders.add("X-Requested-With");
        allowedHeaders.add("Content-Type");
        allowedHeaders.add("Accept");
        allowedHeaders.add("Accept-Encoding");
        allowedHeaders.add("Accept-Language");
        allowedHeaders.add("Access-Control-Request-Headers");
        allowedHeaders.add("Access-Control-Request-Method");
        allowedHeaders.add("Connection");
        allowedHeaders.add("Host");
        allowedHeaders.add("User-Agent");

        responseHeaders.setAccessControlAllowOrigin("*");
        responseHeaders.setAccessControlAllowCredentials(true);
        responseHeaders.setAccessControlAllowMethods(allowedMethods);
        responseHeaders.setAccessControlAllowHeaders(allowedHeaders);
        responseHeaders.set("X-Frame-Options", "*");

        CreateRequirementResponseDto responseDto = new CreateRequirementResponseDto();
        ProjectDbo project = projectRepository.findOneByUniqueKey(createRequirementDto.getProjectUniqueKey());
        if (project == null) {
            return new ResponseEntity<>(responseHeaders, HttpStatus.NOT_FOUND);
        }

        project.incrementLastProjectSpecificRequirementId();
        RequirementDbo requirement = new RequirementDbo(project.getLastProjectSpecificRequirementId(), createRequirementDto.getTitle(), createRequirementDto.getDescription(), project);
        requirement.setStatus(createRequirementDto.getStatus());
        requirementRepository.save(requirement);
        projectRepository.save(project);

        responseDto.setError(false);
        responseDto.setErrorMessage(null);

        return new ResponseEntity<>(responseDto, responseHeaders, HttpStatus.OK);
    }

    @ApiOperation(value = "Show unassigned requirements of project", response = CreateRequirementResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request was successful"),
            @ApiResponse(code = 404, message = "Project not found.")
    })
    @RequestMapping(value="/project/{uniqueKey}/unassigned", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public Map<String, Object> createRequirementJson(@PathVariable(value="uniqueKey") String uniqueKey, HttpServletResponse response)
    {
        ProjectDbo project = projectRepository.findOneByUniqueKey(uniqueKey);
        if (project == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        List<RequirementDbo> requirements = project.getRequirements()
                .stream()
                .filter(r -> r.getRelease() == null)
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> requirementInfoList = new ArrayList<>();
        for (RequirementDbo requirement : requirements) {
            Map<String, Object> requirementData = new HashMap<>();
            requirementData.put("id", requirement.getId());
            requirementData.put("projectSpecificRequirementId", requirement.getProjectSpecificRequirementId());
            requirementData.put("title", requirement.getTitle());
            requirementData.put("description", requirement.getDescription());
            requirementData.put("status", requirement.getStatus());
            requirementData.put("numberOfComments", requirement.getUserComments().size());
            requirementData.put("numberOfPros", requirement.getUserComments().stream()
                    .filter(c -> c.getSentiment() == UserRequirementCommentDbo.Sentiment.PRO)
                    .collect(Collectors.toList()).size());
            requirementData.put("numberOfNeus", requirement.getUserComments().stream()
                    .filter(c -> c.getSentiment() == UserRequirementCommentDbo.Sentiment.NEUTRAL)
                    .collect(Collectors.toList()).size());
            requirementData.put("numberOfCons", requirement.getUserComments().stream()
                    .filter(c -> c.getSentiment() == UserRequirementCommentDbo.Sentiment.CON)
                    .collect(Collectors.toList()).size());
            requirementData.put("socialPopularity", requirement.getSocialPopularity());
            requirementInfoList.add(requirementData);
        }
        result.put("requirements", requirementInfoList);
        return result;
    }

    /*
    --------------------------------------------------------------------------------------------------------------------
     !!! NEW JSON FORMAT !!!
    --------------------------------------------------------------------------------------------------------------------

    @ApiOperation(value = "View the details of a project", response = ResponseDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list"),
        @ApiResponse(code = 404, message = "The project can not be found")
    })
    @RequestMapping(value="/project/{projectID}", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public ResponseDto viewProjectJson(@PathVariable(value="projectID") Long projectID)
    {
        ProjectDbo project = projectRepository.findOne(projectID);
        Map<String, Object> projectProperties  = new LinkedHashMap<>();

        Map<String, Object> idProperty  = new LinkedHashMap<>();
        idProperty.put("description", project.getId());
        idProperty.put("type", "integer");
        projectProperties.put("id", idProperty);

        Map<String, Object> statusProperty  = new LinkedHashMap<>();
        statusProperty.put("description", project.isVisible() ? "ongoing" : "archived");
        statusProperty.put("type", "integer");
        projectProperties.put("status", statusProperty);

        Map<String, Object> startDateProperty  = new LinkedHashMap<>();
        startDateProperty.put("description", project.getStartDate().getTime());
        startDateProperty.put("type", "long");
        projectProperties.put("start_date", startDateProperty);

        Map<String, Object> endDateProperty  = new LinkedHashMap<>();
        endDateProperty.put("description", project.getStartDate().getTime());
        endDateProperty.put("type", "long");
        projectProperties.put("end_date", endDateProperty);

        Map<String, Object> projectImageProperty  = new LinkedHashMap<>();
        projectImageProperty.put("description", project.getImagePath());
        projectImageProperty.put("type", "string");
        projectProperties.put("project_image", projectImageProperty);

        Map<String, Object> nameProperty  = new LinkedHashMap<>();
        nameProperty.put("description", project.getName());
        nameProperty.put("type", "string");
        projectProperties.put("name", nameProperty);

        Map<String, Object> textProperty = new LinkedHashMap<>();
        textProperty.put("description", project.getDescription());
        textProperty.put("type", "string");
        projectProperties.put("text", textProperty);

        Map<String, Object> creationDateProperty = new LinkedHashMap<>();
        creationDateProperty.put("description", project.getCreatedDate().getTime());
        creationDateProperty.put("type", "string");
        projectProperties.put("creation_date", creationDateProperty);

        Map<String, Object> lastUpdateDateProperty = new LinkedHashMap<>();
        lastUpdateDateProperty.put("description", project.getLastUpdatedDate().getTime());
        lastUpdateDateProperty.put("type", "long");
        projectProperties.put("last_update_date", lastUpdateDateProperty);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setTitle("Project");
        responseDto.setDescription("A Project");
        responseDto.setType("object");
        responseDto.setProperties(projectProperties);

        List<String> requiredList = new ArrayList<>();
        requiredList.add("id");
        requiredList.add("name");
        requiredList.add("creation_date");
        responseDto.setRequired(requiredList);

        return responseDto;
    }

    @ApiOperation(value = "View the details of a release", response = ResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The release can not be found")
    })
    @RequestMapping(value="/release/{releaseID}", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public ResponseDto createReleaseJson(@PathVariable(value="releaseID") Long releaseID)
    {
        ReleaseDbo release = releaseRepository.findOne(releaseID);
        Map<String, Object> releaseProperties  = new LinkedHashMap<>();

        Map<String, Object> idProperty  = new LinkedHashMap<>();
        idProperty.put("description", release.getId());
        idProperty.put("type", "integer");
        releaseProperties.put("id", idProperty);

        List<String> statusType = new ArrayList<>();
        statusType.add("new");
        statusType.add("planned");
        statusType.add("completed");
        statusType.add("rejected");

        Map<String, Object> statusProperty  = new LinkedHashMap<>();
        statusProperty.put("description", release.getStatus().toString().toLowerCase());
        statusProperty.put("type", "string");
        statusProperty.put("enum", statusType);
        releaseProperties.put("status", statusProperty);

        Map<String, Object> endDateProperty  = new LinkedHashMap<>();
        endDateProperty.put("description", release.getEndDate().getTime());
        endDateProperty.put("type", "long");
        releaseProperties.put("release_date", endDateProperty);

        Map<String, Object> capacityProperty  = new LinkedHashMap<>();
        capacityProperty.put("description", release.getCapacity());
        capacityProperty.put("type", "integer");
        releaseProperties.put("capacity", capacityProperty);

        Map<String, Object> nameProperty  = new LinkedHashMap<>();
        nameProperty.put("description", release.getName());
        nameProperty.put("type", "string");
        releaseProperties.put("name", nameProperty);

        Map<String, Object> textProperty = new LinkedHashMap<>();
        textProperty.put("description", release.getDescription());
        textProperty.put("type", "string");
        releaseProperties.put("text", textProperty);

        Map<String, Object> creationDateProperty = new LinkedHashMap<>();
        creationDateProperty.put("description", release.getCreatedDate().getTime());
        creationDateProperty.put("type", "string");
        releaseProperties.put("creation_date", creationDateProperty);

        Map<String, Object> lastUpdateDateProperty = new LinkedHashMap<>();
        lastUpdateDateProperty.put("description", release.getLastUpdatedDate().getTime());
        lastUpdateDateProperty.put("type", "long");
        releaseProperties.put("last_update_date", lastUpdateDateProperty);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setTitle("Release");
        responseDto.setDescription("A Release");
        responseDto.setType("object");
        responseDto.setProperties(releaseProperties);

        List<String> requiredList = new ArrayList<>();
        requiredList.add("id");
        requiredList.add("capacity");
        responseDto.setRequired(requiredList);

        return responseDto;
    }

    @ApiOperation(value = "View the details of a person", response = ResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The release can not be found")
    })
    @RequestMapping(value="/person/{userID}", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public ResponseDto createPersonJson(@PathVariable(value="userID") Long userID)
    {
        UserDbo user = userRepository.findOne(userID);
        Map<String, Object> personProperties  = new LinkedHashMap<>();

        Map<String, Object> idProperty  = new LinkedHashMap<>();
        idProperty.put("description", user.getId());
        idProperty.put("type", "integer");
        personProperties.put("id", idProperty);

        Map<String, Object> usernameProperty  = new LinkedHashMap<>();
        usernameProperty.put("description", user.getUsername());
        usernameProperty.put("type", "string");
        personProperties.put("username", usernameProperty);

        Map<String, Object> emailProperty  = new LinkedHashMap<>();
        emailProperty.put("description", user.getMailAddress());
        emailProperty.put("type", "string");
        personProperties.put("email", emailProperty);

        Map<String, Object> profileImageProperty  = new LinkedHashMap<>();
        profileImageProperty.put("description", user.getProfileImagePath());
        profileImageProperty.put("type", "string");
        personProperties.put("profile_image", profileImageProperty);

        Map<String, Object> creationDateProperty = new LinkedHashMap<>();
        creationDateProperty.put("description", user.getCreatedDate().getTime());
        creationDateProperty.put("type", "string");
        personProperties.put("creation_date", creationDateProperty);

        Map<String, Object> lastUpdateDateProperty = new LinkedHashMap<>();
        lastUpdateDateProperty.put("description", (user.getLastUpdatedDate() != null) ? user.getLastUpdatedDate().getTime() : 0);
        lastUpdateDateProperty.put("type", "long");
        personProperties.put("last_update_date", lastUpdateDateProperty);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setTitle("Person");
        responseDto.setDescription("A Person");
        responseDto.setType("object");
        responseDto.setProperties(personProperties);

        List<String> requiredList = new ArrayList<>();
        requiredList.add("id");
        responseDto.setRequired(requiredList);

        return responseDto;
    }

    @ApiOperation(value = "View the details of a participant in a project", response = ResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The release can not be found")
    })
    @RequestMapping(value="/participant/{userID}", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public ResponseDto createParticipantJson(@PathVariable(value="userID") Long userID)
    {
        UserDbo user = userRepository.findOne(userID);

        Map<String, Object> participationProperties  = new LinkedHashMap<>();

        Map<String, Object> idProperty  = new LinkedHashMap<>();
        idProperty.put("description", user.getId());
        idProperty.put("type", "integer");
        participationProperties.put("id", idProperty);

        Map<String, Object> roleNameProperty  = new LinkedHashMap<>();
        roleNameProperty.put("description", user
                .getRoles()
                .stream()
                .map(r -> r.getName().toLowerCase().replace("role_user", "requirements engineer"))
                .collect(Collectors.toList()));
        roleNameProperty.put("type", "string");
        participationProperties.put("role_name", roleNameProperty);

        Map<String, Object> projectProperty  = new LinkedHashMap<>();
        projectProperty.put("description", user
                .getProjectUserParticipations()
                .stream()
                .map(p -> p.getProjectId())
                .collect(Collectors.toList())
        );
        projectProperty.put("type", "array");
        projectProperty.put("items", new LinkedHashMap<String, Object>() {{ put("$ref", "#project"); }});
        participationProperties.put("project", projectProperty);

        Map<String, Object> creationDateProperty = new LinkedHashMap<>();
        creationDateProperty.put("description", user.getCreatedDate().getTime());
        creationDateProperty.put("type", "string");
        participationProperties.put("created_at", creationDateProperty);

        Map<String, Object> lastUpdateDateProperty = new LinkedHashMap<>();
        lastUpdateDateProperty.put("description", (user.getLastUpdatedDate() != null) ? user.getLastUpdatedDate().getTime() : 0);
        lastUpdateDateProperty.put("type", "long");
        participationProperties.put("modified_at", lastUpdateDateProperty);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setTitle("Participant");
        responseDto.setDescription("A Participant of a project");
        responseDto.setType("object");
        responseDto.setProperties(participationProperties);

        List<String> requiredList = new ArrayList<>();
        requiredList.add("id");
        requiredList.add("role_name");
        requiredList.add("projects");
        requiredList.add("person");
        responseDto.setRequired(requiredList);

        return responseDto;
    }

    @ApiOperation(value = "View the details of a participant in a project", response = ResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The release can not be found")
    })
    @RequestMapping(value="/responsible/{userID}", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public ResponseDto createResponsibleJson(@PathVariable(value="userID") Long userID)
    {
        UserDbo user = userRepository.findOne(userID);

        Map<String, Object> participationProperties  = new LinkedHashMap<>();

        Map<String, Object> idProperty  = new LinkedHashMap<>();
        idProperty.put("description", user.getId());
        idProperty.put("type", "integer");
        participationProperties.put("id", idProperty);

        Map<String, Object> projectProperty  = new LinkedHashMap<>();
        projectProperty.put("description", user
                .getProjectUserParticipations()
                .stream()
                .map(p -> p.getProjectId())
                .collect(Collectors.toList())
        );
        projectProperty.put("type", "array");
        projectProperty.put("items", new LinkedHashMap<String, Object>() {{ put("$ref", "#project"); }});
        participationProperties.put("project", projectProperty);

        Map<String, Object> creationDateProperty = new LinkedHashMap<>();
        creationDateProperty.put("description", user.getCreatedDate().getTime());
        creationDateProperty.put("type", "string");
        participationProperties.put("created_at", creationDateProperty);

        Map<String, Object> lastUpdateDateProperty = new LinkedHashMap<>();
        lastUpdateDateProperty.put("description", (user.getLastUpdatedDate() != null) ? user.getLastUpdatedDate().getTime() : 0);
        lastUpdateDateProperty.put("type", "long");
        participationProperties.put("modified_at", lastUpdateDateProperty);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setTitle("Responsible");
        responseDto.setDescription("A Responsible person for a requirement of a project");
        responseDto.setType("object");
        responseDto.setProperties(participationProperties);

        List<String> requiredList = new ArrayList<>();
        requiredList.add("id");
        requiredList.add("type");
        requiredList.add("requirements");
        requiredList.add("person");
        responseDto.setRequired(requiredList);

        return responseDto;
    }
    */

}
