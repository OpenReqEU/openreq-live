package eu.openreq.controller;

import eu.openreq.Util.Utils;
import eu.openreq.dbo.*;
import eu.openreq.repository.*;
import eu.openreq.service.EmailService;
import eu.openreq.statistics.OptimalReleasePlanCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class StatisticsController {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ReleaseRepository releaseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping("/project/{projectID}/statistics/chart/optimalreleaseplan")
    public String showOptimalReleasePlan(
            HttpServletRequest request, @PathVariable(value="projectID") Long projectID,
            Model model, Authentication authentication)
    {
        // TODO: protect if project is not private!
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }

        /*
        List<ReleaseDbo> sortedReleases = new ArrayList<>(project.getReleases());
        Collections.sort(sortedReleases, (o1, o2) -> (o1.getEndDate().getTime() < o2.getEndDate().getTime()) ? -1 : ((o1.getEndDate().getTime() == o2.getEndDate().getTime()) ? 0 : 1));
        List<String> releaseNames = sortedReleases.stream().map(r -> r.getName()).collect(Collectors.toList());
        */

        Map<Long, Double> requirementUtilityMap = new HashMap<>();
        Map<Long, Float> requirementEffortMap = new HashMap<>();
        Map<Long, RequirementDbo> requirementMap = new HashMap<>();

        /*
        for (RequirementDbo requirement : project.getRequirements()) {
            float effortOfReq = 0.0f;
            int numberOfEffortEvaluations = 0;

            if (project.getVisibilityPrivate()) {
                Set<UserRequirementAttributeVoteDbo> votes = requirement.getUserRequirementAttributeVotes();
                for (UserRequirementAttributeVoteDbo vote : votes) {
                    assert(vote.getRequirementId() == requirement.getId());

                    if (!vote.getRatingAttributeID().getName().toLowerCase().equals("effort")) {
                        continue;
                    }

                    effortOfReq += vote.getValue();
                    numberOfEffortEvaluations++;
                }
            } else {
                Set<AnonymousUserRequirementAttributeVoteDbo> votes = requirement.getAnonymousUserRequirementAttributeVotes();
                for (AnonymousUserRequirementAttributeVoteDbo vote : votes) {
                    assert(vote.getRequirementId() == requirement.getId());

                    if (!vote.getRatingAttributeID().getName().toLowerCase().equals("effort")) {
                        continue;
                    }

                    effortOfReq += vote.getValue();
                    numberOfEffortEvaluations++;
                }
            }

            effortOfReq = effortOfReq / numberOfEffortEvaluations;
            requirementEffortMap.put(requirement.getId(), effortOfReq);
            requirementUtilityMap.put(requirement.getId(), computeUtilityValue(requirement, project));
            requirementMap.put(requirement.getId(), requirement);
        }

        requirementUtilityMap = Utils.sortByValue(requirementUtilityMap, true);
        OptimalReleasePlanCalculator calculator = new OptimalReleasePlanCalculator(requirementMap, requirementUtilityMap, sortedReleases, requirementEffortMap);
        calculator.assignRequirements();
        List<Double> cumulativeRelevanceOfReleases = calculator.calculateCumulativeRelevance();
        */



        //TEST
        List<RequirementDbo> reqs = project
                .getRequirements()
                .stream()
                .sorted((r1, r2) -> Long.compare(r2.getProjectSpecificRequirementId(), r1.getProjectSpecificRequirementId()))
                .collect(Collectors.toList());

        requirementMap.put(reqs.get(0).getId(), reqs.get(0));
        requirementMap.put(reqs.get(1).getId(), reqs.get(1));
        requirementMap.put(reqs.get(2).getId(), reqs.get(2));
        requirementMap.put(reqs.get(3).getId(), reqs.get(3));
        requirementMap.put(reqs.get(4).getId(), reqs.get(4));
        requirementMap.put(reqs.get(5).getId(), reqs.get(5));
        requirementMap.put(reqs.get(6).getId(), reqs.get(6));
        requirementMap.put(reqs.get(7).getId(), reqs.get(7));

        //TODO: values are fixed!
        requirementUtilityMap.put(reqs.get(0).getId(), 22.0);
        requirementUtilityMap.put(reqs.get(1).getId(), 8.0);
        requirementUtilityMap.put(reqs.get(2).getId(), 12.0);
        requirementUtilityMap.put(reqs.get(3).getId(), 16.0);
        requirementUtilityMap.put(reqs.get(4).getId(), 13.0);
        requirementUtilityMap.put(reqs.get(5).getId(), 20.0);
        requirementUtilityMap.put(reqs.get(6).getId(), 18.0);
        requirementUtilityMap.put(reqs.get(7).getId(), 10.0);

        //TODO: values are fixed!
        requirementEffortMap.put(reqs.get(0).getId(), 200.0f);
        requirementEffortMap.put(reqs.get(1).getId(), 150.0f);
        requirementEffortMap.put(reqs.get(2).getId(), 400.0f);
        requirementEffortMap.put(reqs.get(3).getId(), 300.0f);
        requirementEffortMap.put(reqs.get(4).getId(), 250.0f);
        requirementEffortMap.put(reqs.get(5).getId(), 250.0f);
        requirementEffortMap.put(reqs.get(6).getId(), 250.0f);
        requirementEffortMap.put(reqs.get(7).getId(), 300.0f);

        List<String> releaseNames = Arrays.asList("Release 1", "Release 2", "Release 3");
        requirementUtilityMap = Utils.sortByValue(requirementUtilityMap, true);
        List<ReleaseDbo> sortedReleases = new ArrayList<>();

        ReleaseDbo release1 = releaseRepository.findOneByNameAndProject("Release 1", project);
        ReleaseDbo release2 = releaseRepository.findOneByNameAndProject("Release 2", project);
        ReleaseDbo release3 = releaseRepository.findOneByNameAndProject("Release 3", project);

        sortedReleases.add(release1);
        sortedReleases.add(release2);
        sortedReleases.add(release3);

        OptimalReleasePlanCalculator calculator = new OptimalReleasePlanCalculator(requirementMap, requirementUtilityMap, sortedReleases, requirementEffortMap);
        calculator.assignRequirements();
        List<Double> optimalCumulativeRelevanceOfReleases = calculator.calculateCumulativeRelevance();
        List<Double> realCumulativeRelevanceOfReleases = calculateCumulativeRelevanceOfRealReleasePlan(sortedReleases, requirementUtilityMap);

        model.addAttribute("releaseNames", releaseNames);
        model.addAttribute("optimalCumulativeRelevanceOfReleases", optimalCumulativeRelevanceOfReleases);
        model.addAttribute("realCumulativeRelevanceOfReleases", realCumulativeRelevanceOfReleases);
        return "statistics/chart_optimal_release_plan";
    }

    private List<Double> calculateCumulativeRelevanceOfRealReleasePlan(List<ReleaseDbo> sortedReleases,
                                                                       Map<Long, Double> sortedRequirementsUtilitiesMap) {
        List<Double> cummulativeRelevanceOfReleases = new ArrayList<>();
        double relWeight = sortedReleases.size()*0.1;
        double cumulativeRelevance = 0.0;
        for (ReleaseDbo rel : sortedReleases) {
            double sumOfUtilties = 0.0;
            for (RequirementDbo requirement : rel.getRequirements()) {
                sumOfUtilties += sortedRequirementsUtilitiesMap.get(requirement.getId());
            }
            cumulativeRelevance += sumOfUtilties*relWeight; // TODO: consider the free capacity of a release in the future except the last release
            System.out.println("Relevance for "+rel.getName()+"= "+cumulativeRelevance);
            cummulativeRelevanceOfReleases.add(cumulativeRelevance);
            relWeight -= 0.1;
        }
        return cummulativeRelevanceOfReleases;
    }

    private double computeUtilityValue(RequirementDbo requirement, ProjectDbo project) {
        Map<Long, Float> ratingAttributeVotes = new HashMap<>();
        Map<Long, Float> ratingAttributeWeights = new HashMap<>();

        for (RatingAttributeDbo attribute : project.getRatingAttributes()) {
            float attributeWeight = attribute.getWeight();
            ratingAttributeWeights.put(attribute.getId(), attributeWeight);
            List<Integer> votingValues = new ArrayList<>();

            if (project.isVisibilityPrivate()) {
                List<UserRequirementAttributeVoteDbo> requirementVotes = attribute.getUserRequirementAttributeVotes()
                        .stream()
                        .filter(v -> v.getRequirement().getId() == requirement.getId())
                        .collect(Collectors.toList());

                for (UserRequirementAttributeVoteDbo vote : requirementVotes) {
                    assert(vote.getRatingAttribute().getId() == attribute.getId());
                    votingValues.add(vote.getValue());
                }
            } else {
                List<AnonymousUserRequirementAttributeVoteDbo> requirementVotes = attribute.getAnonymousUserRequirementAttributeVotes()
                        .stream()
                        .filter(v -> v.getRequirement().getId() == requirement.getId())
                        .collect(Collectors.toList());

                for (AnonymousUserRequirementAttributeVoteDbo vote : requirementVotes) {
                    assert(vote.getRatingAttribute().getId() == attribute.getId());
                    votingValues.add(vote.getValue());
                }
            }

            float attributeAverage = (votingValues.size() > 0)
                                   ? (votingValues.stream().mapToInt(Integer::intValue).sum() / ((float) votingValues.size()))
                                   : 0.0f;

            float factor = attributeAverage;
            if (attribute.isReverse()) {
                factor = (attribute.getMaxValue() + attribute.getMinValue()) - factor;
            }

            ratingAttributeVotes.put(attribute.getId(), attributeWeight * factor);
        }

        double numerator = ratingAttributeVotes.values().stream().mapToDouble(v -> v.doubleValue()).sum();
        double denominator = ratingAttributeWeights.values().stream().mapToDouble(v -> v.doubleValue()).sum();
        return (denominator > 0.0) ? (numerator / denominator) : 0.0;
    }

    @GetMapping("/project/{projectID}/statistics/graph/dependency")
    public String showDependencyGraph(
            HttpServletRequest request, @PathVariable(value="projectID") Long projectID,
            Model model, Authentication authentication)
    {
        UserDbo currentUser = Utils.getCurrentUser(authentication, userRepository);
        ProjectDbo project = projectRepository.findOne(projectID);

        if (!project.hasCreatorOrParticipantRights(currentUser)) {
            String errorMessage = Utils.generateErrorMessageAndSendEmail(request, currentUser, emailService);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, errorMessage);
        }

        Set<RequirementDbo> requirements = project.getRequirements();
        Set<DependencyDbo> dependencies = new LinkedHashSet<>();
        Map<Long, String> requirementsTitleMap = new HashMap<>();
        Map<Long, String> requirementsDescriptionMap = new HashMap<>();
        for (RequirementDbo requirement : requirements) {
            if (!requirement.isVisible()) {
                continue;
            }
            requirementsTitleMap.put(requirement.getId(), requirement.getTitle());
            requirementsDescriptionMap.put(requirement.getId(), requirement.getDescription());
            dependencies.addAll(requirement.getSourceDependencies());
            dependencies.addAll(requirement.getTargetDependencies());
        }

        Map<Long, Set<Long>> dependenciesMap = new HashMap<>();
        for (DependencyDbo dependency: dependencies) {
            if (!dependency.isVisible()) {
                continue;
            }
            Long sourceID = dependency.getSourceRequirement().getId();
            Long targetID = dependency.getTargetRequirement().getId();
            Set<Long> dependencyList = dependenciesMap.get(sourceID);
            if (dependencyList == null) {
                dependencyList = new LinkedHashSet<>();
            }

            dependencyList.add(targetID);
            dependenciesMap.put(sourceID, dependencyList);
        }

        Map<Long, Long> projectSpecificRequirementsIdMap = new HashMap<>();
        for (RequirementDbo requirement : project.getRequirements()) {
            projectSpecificRequirementsIdMap.put(requirement.getId(), requirement.getProjectSpecificRequirementId());
        }

        model.addAttribute("project", project);
        model.addAttribute("projectSpecificRequirementsIdMap", projectSpecificRequirementsIdMap);
        model.addAttribute("requirementsTitleMap", requirementsTitleMap);
        model.addAttribute("requirementsDescriptionMap", requirementsDescriptionMap);
        model.addAttribute("dependenciesMap", dependenciesMap);
        return "statistics/graph_dependencies";
    }

}
