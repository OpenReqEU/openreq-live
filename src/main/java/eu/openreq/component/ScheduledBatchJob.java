package eu.openreq.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.openreq.Util.Utils;
import eu.openreq.dbo.ProjectDbo;
import eu.openreq.dbo.RequirementDbo;
import eu.openreq.dbo.RequirementStakeholderAssignment;
import eu.openreq.dbo.UserDbo;
import eu.openreq.remote.request.dto.stakeholderrecommendation.*;
import eu.openreq.repository.ProjectRepository;
import eu.openreq.repository.UserRepository;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import static java.util.Comparator.comparing;

@Component
public class ScheduledBatchJob {

    public static final String UPC_STAKEHOLDER_RECOMMENDATION_SERVICE_HOST = "217.172.12.199";
    public static final int UPC_STAKEHOLDER_RECOMMENDATION_SERVICE_PORT = 9410;
    private static final Logger logger = LoggerFactory.getLogger(ScheduledBatchJob.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Transactional
    //@Scheduled(cron = "20 49 12 * * ?")
    @Scheduled(cron = "40 3 * * * ?")
    @Scheduled(cron = "0 6 * * * ?")
    @Scheduled(cron = "0 18 * * * ?")
    public void batchProcess() throws JsonProcessingException {
        System.out.println("[CRON] Batch Process Task :: Execution Time - " + dateTimeFormatter.format(LocalDateTime.now()));
        System.out.println("[CRON] Current Thread : " + Thread.currentThread().getName());

        List<ProjectDbo> projects = StreamSupport.stream(projectRepository.findAll().spliterator(), false)
                .filter(p -> p.isVisible())
                .filter(p -> p.isVisibilityPrivate())
                .sorted(comparing(ProjectDbo::getId))
                .collect(Collectors.toList());

        BatchProcessDto batchProcessDto = new BatchProcessDto();
        for (UserDbo user : userRepository.findAll()) {
            PersonDto personDto = new PersonDto();
            personDto.setUsername(user.getUsername());
            personDto.setEmail(user.getMailAddress());
            batchProcessDto.addPerson(personDto);
        }

        for (ProjectDbo project : projects) {
            ProjectDto projectDto = new ProjectDto();
            projectDto.setId(Long.toString(project.getId()));
            projectDto.setCreated_at((int) (project.getCreatedDate().getTime() / 1000));

            for (RequirementDbo requirement : project.getRequirements()) {
                if (!requirement.isVisible()) {
                    continue;
                }

                String strippedDescription = Utils.removeURL(Jsoup.parse(requirement.getDescription()).text()).trim();
                RequirementDto requirementDto = new RequirementDto();
                requirementDto.setId(Long.toString(requirement.getId()));
                requirementDto.setName(requirement.getTitle());
                requirementDto.setDescription(strippedDescription);
                requirementDto.setCreated_at((int) (requirement.getCreatedDate().getTime() / 1000));

                for (RequirementStakeholderAssignment stakeholderAssignment : requirement.getUserStakeholderAssignments()) {
                    if (stakeholderAssignment.isAccepted()) {
                        ResponsibleDto responsibleDto = new ResponsibleDto();
                        responsibleDto.setPerson(stakeholderAssignment.getStakeholder().getUsername());
                        responsibleDto.setRequirement(Long.toString(requirement.getId()));
                        batchProcessDto.addResponsible(responsibleDto);
                    }
                }

                projectDto.addSpecifiedRequirement(Long.toString(requirement.getId()));
                batchProcessDto.addRequirement(requirementDto);
            }

            batchProcessDto.addProject(projectDto);
        }

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/json");
        headers.setAll(map);

        HttpEntity<BatchProcessDto> request = new HttpEntity<>(batchProcessDto, headers);
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(batchProcessDto);
        System.out.println("[CRON] Batch Process Task :: Request: " + jsonInString);

        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        try {
            // delete all data from UPC stakeholder recommendation service since it might not be up2date any more
            String deleteUrl = "http://" + UPC_STAKEHOLDER_RECOMMENDATION_SERVICE_HOST + ":"
                    + UPC_STAKEHOLDER_RECOMMENDATION_SERVICE_PORT + "/upc/stakeholders-recommender/purge";
            restTemplate.delete(deleteUrl);

            // transfer all data to UPC stakeholder recommendation service
            String url = "http://" + UPC_STAKEHOLDER_RECOMMENDATION_SERVICE_HOST + ":"
                    + UPC_STAKEHOLDER_RECOMMENDATION_SERVICE_PORT + "/upc/stakeholders-recommender/batch_process";
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            if ((response.getStatusCodeValue() != 200) && (response.getStatusCodeValue() != 201)) {
                throw new Exception("An error occured!");
            }

            String body = response.getBody();
            System.out.println("[CRON] Batch Process Task :: Body: " + body);
            System.out.println("[CRON] Batch Process Task :: Successfully completed - "
                    + dateTimeFormatter.format(LocalDateTime.now()));
            return;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("[CRON] Batch Process Task :: !!! FAILED !!! - "
                + dateTimeFormatter.format(LocalDateTime.now()));
    }

}
