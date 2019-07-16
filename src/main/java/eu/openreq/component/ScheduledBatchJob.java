package eu.openreq.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.openreq.Util.RequestResponseLoggingInterceptor;
import eu.openreq.Util.Utils;
import eu.openreq.dbo.*;
import eu.openreq.remote.request.dto.stakeholderrecommendation.*;
import eu.openreq.repository.ProjectRepository;
import eu.openreq.repository.UserRepository;
import eu.openreq.service.EmailService;
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

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

    @Autowired
    private EmailService emailService;

    private static Date getDeadline() {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZ");
        try {
            return dateformat.parse("2019-03-21T23:59:00+0100");
        } catch (Exception e) {
            return new Date();
        }
    }

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    @Transactional
    //@Scheduled(cron = "0 0 */3 * * ?")
    //@Scheduled(cron = "0 */10 * * * ?")
    @Scheduled(cron = "0 0 0 * * ?")
    public void batchProcess() throws JsonProcessingException, DatatypeConfigurationException {
        System.out.println("[CRON] Batch Process Task :: Execution Time - " + dateTimeFormatter.format(LocalDateTime.now()));
        System.out.println("[CRON] Current Thread : " + Thread.currentThread().getName());

        List<ProjectDbo> projects = StreamSupport.stream(projectRepository.findAll().spliterator(), false)
                .filter(p -> p.isVisible())
                .filter(p -> p.isVisibilityPrivate())
                .sorted(comparing(ProjectDbo::getId))
                .collect(Collectors.toList());

        BatchProcessDto batchProcessDto = new BatchProcessDto();
        Set<Long> allowedUserIDs = new HashSet<>();
        for (UserDbo user : userRepository.findAll()) {
            PersonDto personDto = new PersonDto();
            personDto.setUsername(user.getUsername());
            batchProcessDto.addPerson(personDto);
            allowedUserIDs.add(user.getId());
        }

        for (ProjectDbo project : projects) {
            ProjectDto projectDto = new ProjectDto();
            projectDto.setId(Long.toString(project.getId()));

            UserDbo creatorUser = project.getCreatorUser();
            ParticipantDto participantDto = new ParticipantDto();
            participantDto.setPerson(creatorUser.getUsername());
            participantDto.setProject(Long.toString(project.getId()));
            batchProcessDto.addParticipant(participantDto);

            for (ProjectUserParticipationDbo userParticipation : project.getUserParticipations()) {
                participantDto = new ParticipantDto();
                participantDto.setPerson(userParticipation.getUser().getUsername());
                participantDto.setProject(Long.toString(project.getId()));
                batchProcessDto.addParticipant(participantDto);
            }

            for (RequirementDbo requirement : project.getRequirements()) {
                if (!requirement.isVisible()) {
                    continue;
                }

                String strippedDescription = Utils.removeURL(Jsoup.parse(requirement.getDescription()).text()).trim();
                RequirementDto requirementDto = new RequirementDto();
                requirementDto.setId(Long.toString(requirement.getId()));
                requirementDto.setName(requirement.getTitle());
                requirementDto.setDescription(strippedDescription);
                float sumEffort = 0.0f;
                int countEffort = 0;
                for (UserRequirementAttributeVoteDbo attributeVote : requirement.getUserRequirementAttributeVotes()) {
                    if (attributeVote.getRatingAttribute().getName().toLowerCase().equals("effort")) {
                        sumEffort += attributeVote.getValue();
                        ++countEffort;
                    }
                }
                requirementDto.setEffort(String.format("%.2f", countEffort > 0 ? (sumEffort / countEffort) : 0.0f));
                requirementDto.setModified_at(dateFormat.format(requirement.getLastUpdatedDate()));

                for (RequirementStakeholderAssignment stakeholderAssignment : requirement.getUserStakeholderAssignments()) {
                    if (stakeholderAssignment.isAccepted() && allowedUserIDs.contains(stakeholderAssignment.getStakeholder().getId())) {
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
        restTemplate.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));

        try {
            // deletes all data from UPC stakeholder recommendation service since it might not be up2date any more
            // and also transfers all data to UPC's stakeholder recommendation service
            String url = "http://" + UPC_STAKEHOLDER_RECOMMENDATION_SERVICE_HOST + ":"
                    + UPC_STAKEHOLDER_RECOMMENDATION_SERVICE_PORT + "/upc/stakeholders-recommender/batch_process?withAvailability=false&withComponent=false&keywords=true&autoMapping=true&organization=tugraz";
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            if ((response.getStatusCodeValue() != 200) && (response.getStatusCodeValue() != 201)) {
                throw new Exception("An error occured!");
            }

            String body = response.getBody();
            System.out.println("[CRON] Batch Process Task :: Body: " + body);
            System.out.println("[CRON] Batch Process Task :: Successfully completed - "
                    + dateTimeFormatter.format(LocalDateTime.now()));
            emailService.sendEmailAsync(
                    "cpalomares@essi.upc.edu",
                    "[OpenReq!Live] UPC Stakeholder Recommendation Cronjob",
                    "<b style='color:darkgreen;'>SUCCESSFULLY TRANSFERED!!</b><br /><br />The following input was transfered to UPC's Stakeholder Recommendation Service:<br /><br /><code>" + jsonInString + "</code>",
                    "SUCCESSFULLY TRANSFERED!!\n\n The following input was transfered to UPC's Stakeholder Recommendation Service:\n\n" + jsonInString
            );
            return;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            emailService.sendEmailAsync(
                    "martin.stettinger@ist.tugraz.at",
                    "[OpenReq!Live] UPC Stakeholder Recommendation Cronjob",
                    "<b style='color:darkred;'>FAILED!!</b><br /><br />" + e.getMessage() + "<br /><br />"
                            + "The following input was transfered to UPC's Stakeholder Recommendation Service:<br /><br /><code>" + jsonInString + "</code>",
                    "FAILED!!\n\n" + e.getMessage() + "\n\n The following input was transfered to UPC's Stakeholder Recommendation Service:\n\n" + jsonInString
            );
        }
        System.out.println("[CRON] Batch Process Task :: !!! FAILED !!! - "
                + dateTimeFormatter.format(LocalDateTime.now()));
    }

}
