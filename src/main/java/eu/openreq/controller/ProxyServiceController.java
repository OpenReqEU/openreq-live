package eu.openreq.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.openreq.Util.Utils;
import eu.openreq.dbo.*;
import eu.openreq.remote.dto.RemoteRequirementDependencyDto;
import eu.openreq.remote.dto.RemoteSimilarRequirementsDto;
import eu.openreq.remote.errorhandler.HelsinkiDependencyDetectionResponseErrorHandler;
import eu.openreq.remote.request.dto.helsinki.*;
import eu.openreq.remote.response.dto.helsinki.CheckConsistencyResponse;
import eu.openreq.repository.DependencyRepository;
import eu.openreq.repository.ProjectRepository;
import eu.openreq.repository.RequirementRepository;
import eu.openreq.repository.UserRepository;
import eu.openreq.service.EmailService;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;

@Controller
public class ProxyServiceController {


    private static final Logger logger = LoggerFactory.getLogger(ProxyServiceController.class);
    private static final String SIEMENS_SERVICE_HOST = "169.254.6.175";
    private static final String TUG_SERVICE_HOST = "217.172.12.199";
    //    private static final String TUG_SERVICE_HOST = "localhost";
//	private static final String SIEMENS_SERVICE_HOST = "192.168.178.23";
//	private static final String SIEMENS_SERVICE_HOST = "172.20.10.9";
    private static final String HELSINKI_SERVICE_HOST = "localhost";
    private static final int SIEMENS_EXTRACTION_SERVICE_PORT = 8081;
    private static final int SIEMENS_CLASSIFICATION_SERVICE_PORT = 8080;
    private static final int TUG_SIMILAR_REQUIREMENTS_RECOMMENDATION_SERVICE_PORT = 9006;
    private static final int TUG_REQUIREMENT_DEPENDENCY_RECOMMENDATION_SERVICE_PORT = 9007;
    private static final int TUG_CONSISTENCY_CHECK_SERVICE_PORT = 9004;
    private static final int HELSINKI_CONSISTENCY_SERVICE_PORT = 9202;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private RequirementRepository requirementRepository;
    @Autowired
    private DependencyRepository dependencyRepository;
    @Autowired
    private EmailService emailService;

	/*
    @ResponseBody
	@GetMapping("/document/{documentId}/extractRequirements")
	public RemoteRequirementDto[] proxyExtractRequirementFromDocument(@PathVariable(value="documentId") Long documentId) {

		//TODO: The simulateFunction is only for the testing purpose
//		return simulateExtractedRequirements(documentId);

		final int MAX_NUMBER_OF_REQUIREMENTS = 10;

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://" + SIEMENS_SERVICE_HOST + ":" + SIEMENS_EXTRACTION_SERVICE_PORT + "/requirements/doc/" + (documentId + 1);
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

		ResponseEntity<RemoteRequirementDto[]> response = restTemplate.getForEntity(url, RemoteRequirementDto[].class);
		RemoteRequirementDto[] body = response.getBody();

		if (body != null) {
			RemoteRequirementDto[] requirements = Arrays.copyOfRange(body, 0, (MAX_NUMBER_OF_REQUIREMENTS - 1));
			for (RemoteRequirementDto r : requirements) {
				Double score = proxyClassifyRequirments(r);
				r.setScore(score);
			}
			Collections.sort(Arrays.asList(requirements), new Comparator<RemoteRequirementDto>() {
			    @Override
			    public int compare(RemoteRequirementDto c1, RemoteRequirementDto c2) {
			        return Double.compare(c2.getScore(), c1.getScore());
			    }
			});
			return requirements;
		}
		
				
		return new RemoteRequirementDto[] {};
	}
	*/

	/*
    private RemoteRequirementDto[] simulateExtractedRequirements(Long documentId){
		RemoteRequirementDto[] requirements = new RemoteRequirementDto[5];
		for(long i= 0; i<5; i++){
			RemoteRequirementDto delegationChainDepthFirstSearch = new RemoteRequirementDto((Long)i,"toolId"+i, "Text of Doc = "+(documentId+1)+"|| Req="+i, "heading"+i,
					"type"+i, i, null,((1 - 0) * new Random().nextDouble()));
			requirements[(int) i] = delegationChainDepthFirstSearch;
		}
		return requirements;
	}

	@ResponseBody
	@PostMapping("/classify/requirements")
	public Double proxyClassifyRequirments(@RequestBody RemoteRequirementDto requirement) {
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		Map<String, String> map = new HashMap<>();
		map.put("Content-Type", "application/json");
		headers.setAll(map);
		HttpEntity<RemoteRequirementDto> request = new HttpEntity<>(requirement, headers);
		String url = "http://" + SIEMENS_SERVICE_HOST + ":" + SIEMENS_CLASSIFICATION_SERVICE_PORT + "/classification";
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		ResponseEntity<Double> response = restTemplate.postForEntity(url, request, Double.class);
		Double body = response.getBody();
		return body;
	}
	*/

    @ResponseBody
    @GetMapping("/project/{projectID}/requirement/recommend/similar")
    public RemoteSimilarRequirementsDto[] proxySimilarRequirementsRecommender(@PathVariable(value = "projectID") Long projectID) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        headers.setAll(map);

        ProjectDbo project = projectRepository.findOne(projectID);

        List<RemoteSimilarRequirementsDto> requirements = new ArrayList<>();
        Map<Long, Long> projectSpecificRequirementIDMap = new HashMap<>();
        for (RequirementDbo requirement : project.getRequirements()) {
            if (!requirement.isVisible() || (requirement.getRelease() != null && !requirement.getRelease().isVisible())) {
                continue;
            }

            String strippedDescription = Utils.removeURL(Jsoup.parse(requirement.getDescription()).text());
            RemoteSimilarRequirementsDto similarRequirementsDto = new RemoteSimilarRequirementsDto();
            similarRequirementsDto.setId(requirement.getId());
            similarRequirementsDto.setTitle(requirement.getTitle());
            similarRequirementsDto.setDescription(strippedDescription);
            List<String> comments = requirement.getUserComments()
                    .stream()
                    .map(c -> c.getTitle() + ((c.getText() != null && c.getText().length() > 0) ? " " + c.getText() : ""))
                    .collect(Collectors.toList());
            similarRequirementsDto.setComments(comments);
            requirements.add(similarRequirementsDto);
            projectSpecificRequirementIDMap.put(requirement.getId(), requirement.getProjectSpecificRequirementId());
        }

        if (projectSpecificRequirementIDMap.size() == 0) {
            return new RemoteSimilarRequirementsDto[]{};
        }

        RemoteSimilarRequirementsDto[] requirementsArray = new RemoteSimilarRequirementsDto[requirements.size()];
        requirementsArray = requirements.toArray(requirementsArray);

        HttpEntity<RemoteSimilarRequirementsDto[]> request = new HttpEntity<>(requirementsArray, headers);
        String url = "http://" + TUG_SERVICE_HOST + ":"
                + TUG_SIMILAR_REQUIREMENTS_RECOMMENDATION_SERVICE_PORT
                + "/v1/recommend";
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        try {
            ResponseEntity<RemoteSimilarRequirementsDto[]> response = restTemplate.postForEntity(url, request, RemoteSimilarRequirementsDto[].class);
            RemoteSimilarRequirementsDto[] body = response.getBody();
            for (RemoteSimilarRequirementsDto similarRequirementsDto : body) {
                similarRequirementsDto.setProjectSpecificRequirementId(projectSpecificRequirementIDMap.get(similarRequirementsDto.getId()));
            }
            return body;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new RemoteSimilarRequirementsDto[]{};
    }

    @ResponseBody
    @GetMapping("/project/{projectKey}/dependency/recommend")
    public RemoteRequirementDependencyDto[] proxyDependenciesRecommender(@PathVariable(value = "projectKey") String projectKey) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        headers.setAll(map);

        ProjectDbo project = projectRepository.findOneByUniqueKey(projectKey);

        List<RemoteRequirementDependencyDto> requirements = new ArrayList<>();
        Map<Long, Long> projectSpecificRequirementIDMap = new HashMap<>();
        for (RequirementDbo requirement : project.getRequirements()) {
            if (!requirement.isVisible() || (requirement.getRelease() != null && !requirement.getRelease().isVisible())) {
                continue;
            }

            String strippedDescription = Utils.removeURL(Jsoup.parse(requirement.getDescription()).text());
            RemoteRequirementDependencyDto requirementDependenciesDto = new RemoteRequirementDependencyDto();
            requirementDependenciesDto.setId(requirement.getId());
            requirementDependenciesDto.setTitle(requirement.getTitle());
            requirementDependenciesDto.setDescription(strippedDescription);
            List<String> comments = requirement.getUserComments()
                    .stream()
                    .map(c -> c.getTitle() + ((c.getText() != null && c.getText().length() > 0) ? " " + c.getText() : ""))
                    .collect(Collectors.toList());
            requirementDependenciesDto.setComments(comments);
            requirements.add(requirementDependenciesDto);
            projectSpecificRequirementIDMap.put(requirement.getId(), requirement.getProjectSpecificRequirementId());
        }

        if (projectSpecificRequirementIDMap.size() == 0) {
            return new RemoteRequirementDependencyDto[]{};
        }

        RemoteRequirementDependencyDto[] requirementsArray = new RemoteRequirementDependencyDto[requirements.size()];
        requirementsArray = requirements.toArray(requirementsArray);

        HttpEntity<RemoteRequirementDependencyDto[]> request = new HttpEntity<>(requirementsArray, headers);
        String url = "http://" + TUG_SERVICE_HOST + ":"
                + TUG_REQUIREMENT_DEPENDENCY_RECOMMENDATION_SERVICE_PORT
                + "/v1/recommend";
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        ResponseEntity<RemoteRequirementDependencyDto[]> response = restTemplate.postForEntity(url, request, RemoteRequirementDependencyDto[].class);
        RemoteRequirementDependencyDto[] body = response.getBody();
        for (RemoteRequirementDependencyDto requirementDependenciesDto : body) {
            requirementDependenciesDto.setProjectSpecificRequirementId(projectSpecificRequirementIDMap.get(requirementDependenciesDto.getId()));
        }
        return body;
    }

    @ResponseBody
    @GetMapping("/project/{projectUniqueKey}/checkconsistency.json")
    public Map<String, Object> checkConsistencyAndPerformDiagnosis(
            HttpServletRequest request,
            @PathVariable(value = "projectUniqueKey") String projectUniqueKey,
            Authentication authentication) {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOneByUniqueKey(projectUniqueKey);
        Map<String, Object> result = new HashMap<>();

        if (project == null) {
            result.put("error", true);
            result.put("errorMessage", "Project does not exist!");
            return result;
        }

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            result.put("error", false);
            result.put("errorMessage", errorMessage);
            return result;
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new HelsinkiDependencyDetectionResponseErrorHandler());
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        headers.setAll(map);

        final CheckConsistencyRequest checkConsistencyRequest = getCheckConsistencyRequestDto(project);
        final String url = "http://" + HELSINKI_SERVICE_HOST + ":" + HELSINKI_CONSISTENCY_SERVICE_PORT + "models/projects/consistencyCheckAndDiagnosis";
        final HttpEntity<CheckConsistencyRequest> request1 = new HttpEntity<>(checkConsistencyRequest, headers);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(TEXT_PLAIN, APPLICATION_JSON));
        restTemplate.getMessageConverters().add(converter);

        try {
            String jsonReq = mapper.writeValueAsString(checkConsistencyRequest);
            System.out.println(jsonReq);
            ResponseEntity<CheckConsistencyResponse> responseEntity = restTemplate.postForEntity(url, request1, CheckConsistencyResponse.class);

            CheckConsistencyResponse body = responseEntity.getBody();

            result.put("status", responseEntity.getStatusCodeValue());
            result.put("response", responseEntity.getBody());
            result.put("error", false);
            result.put("errorMessage", null);
            result.put("consistent", body.getResponse().get(0).getConsistent());
            //    result.put("diagnoses", diagnoses);
           // result.put("explanation", body.getResponse().get(1).getDiagnosisMsg());
        } catch (HttpMessageNotReadableException exception) {
            try {
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request1, String.class);
                result.put("error", true);
                result.put("errorMessage", responseEntity.getBody());
            } catch (Exception innerException) {
                result.put("error", true);
                result.put("errorMessage", innerException.getMessage());
            }
        } catch (RestClientException exception) {
            result.put("error", true);
            result.put("errorMessage", exception.getMessage());
        } catch (Exception exception) {
            result.put("error", true);
            result.put("errorMessage", "Ups something unexpected has happened! Please try again!");
        }
        return result;
    }

    private CheckConsistencyRequest getCheckConsistencyRequestDto(ProjectDbo project) {

        //FIXME projectstate
        Project projectDto = new Project(project.getId(), ProjectStates.ONGOING, project.getCreatedDate());

        List<Requirement> requirements = new ArrayList<>();
        List<Release> releases = new ArrayList<>();
        List<Dependency> dependencies = new ArrayList<>();
        for (RequirementDbo requirement : project.getRequirements()) {
            if (!requirement.isVisible() || (requirement.getRelease() == null)) {
                continue;
            }

            // TODO: compute effort based on MAUT, scale and convert to int!
            int effort = 0;
            Requirement requirementDto = getRequirementDtoFromDbo(requirement);
            requirements.add(requirementDto);

            for (DependencyDbo dependency : requirement.getSourceDependencies()) {
                if (!dependency.isVisible()) {
                    continue;
                }

                if ((dependency.getSourceRequirement().getRelease() == null) || (dependency.getTargetRequirement().getRelease() == null)) {
                    continue;
                }

                dependencies.add(new Dependency(dependency.getType() == DependencyDbo.Type.EXCLUDES ?
                        Dependency.DependencyType.INCOMPATIBLE : Dependency.DependencyType.REQUIRES,
                        dependency.getSourceRequirement().getId(),
                        dependency.getTargetRequirement().getId(),
                        dependency.getCreatedDate()));
                /*
                DependencyDto dependencyDto = new DependencyDto(
                    dependency.getType(),
                    Long.toString(dependency.getSourceRequirement().getId()),
                    Long.toString(dependency.getTargetRequirement().getId())
                );
                requestCheckConsistencyDTO.addDependency(dependencyDto);
                */
            }
        }

        //	Map<Long, Long> releaseIDMap = new HashMap<>();
        List<ReleaseDbo> sortedReleases = new ArrayList<>(project.getReleases());
        Collections.sort(sortedReleases, new Comparator<ReleaseDbo>() {
            public int compare(ReleaseDbo r1, ReleaseDbo r2) {
                return r1.getEndDate().compareTo(r2.getEndDate());
            }
        });

        long releaseCounter = 1;
        for (ReleaseDbo release : sortedReleases) {
            if (!release.isVisible()) {
                continue;
            }

            List<String> requirementsPerRelease = new ArrayList<>();
            Release releaseDto = new Release(releaseCounter++, release.getStatus(),
                    release.getMaximumCapacity(),
                    release.getCreatedDate());
            for (RequirementDbo requirement : release.getRequirements()) {
                requirementsPerRelease.add(Long.toString(requirement.getId()));
            }
            releaseDto.setRequirements(requirementsPerRelease);
            releases.add(releaseDto);
        }
        return new CheckConsistencyRequest(projectDto, dependencies, releases, requirements);
    }

    private Requirement getRequirementDtoFromDbo(RequirementDbo requirement) {
        return new Requirement(requirement.getId(), requirement.getStatus(), requirement.getCreatedDate());
    }

}
