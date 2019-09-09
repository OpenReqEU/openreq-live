package eu.openreq.statistics;

import eu.openreq.dbo.DependencyDbo;
import eu.openreq.dbo.ReleaseDbo;
import eu.openreq.dbo.RequirementDbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.util.stream.Collectors;

public class OptimalReleasePlanCalculator {

    private static final Logger logger = LoggerFactory.getLogger(OptimalReleasePlanCalculator.class);
    private Map<Long, RequirementDbo> requirementsMap;
    private Map<Long, Float> requirementEffortMap;
    private Map<Long, Double> sortedRequirementsUtilitiesMap;
    private List<ReleaseDbo> sortedReleases;

    public OptimalReleasePlanCalculator(Map<Long, RequirementDbo> requirementsMap, Map<Long, Double> sortedRequirementsUtilitiesMap, List<ReleaseDbo> sortedReleases, Map<Long, Float> requirementEffortMap) {
        this.requirementsMap = new LinkedHashMap<>();
        this.sortedRequirementsUtilitiesMap = new LinkedHashMap<>();
        for (Map.Entry<Long, Double> entry : sortedRequirementsUtilitiesMap.entrySet()) {
            Long sourceRequirementID = entry.getKey();
            RequirementDbo sourceRequirement = requirementsMap.get(sourceRequirementID);
            try {
                RequirementDbo targetRequirement = new RequirementDbo(0L,
                        sourceRequirement.getTitle(), sourceRequirement.getDescription(), sourceRequirement.getProject());
                targetRequirement.setId(sourceRequirementID);
                targetRequirement.setSourceDependencies(sourceRequirement.getSourceDependencies());
                targetRequirement.setTargetDependencies(sourceRequirement.getTargetDependencies());
                targetRequirement.setRelease(null);
                this.sortedRequirementsUtilitiesMap.put(entry.getKey(), entry.getValue());
                this.requirementsMap.put(entry.getKey(), targetRequirement);
            } catch (Exception e) {
                logger.error("An exception occurred while opening the file.", e);
            }
        }

        this.sortedReleases = new LinkedList<>();
        for (ReleaseDbo sourceRelease : sortedReleases) {
            ReleaseDbo targetRelease = new ReleaseDbo(sourceRelease.getName(), sourceRelease.getDescription(),
                    sourceRelease.getEndDate(), sourceRelease.getCapacity(), sourceRelease.getProject());
            targetRelease.setRequirements(new LinkedHashSet<>());
            this.sortedReleases.add(targetRelease);
        }

        this.requirementEffortMap = requirementEffortMap;
    }

    private double computeConsumedReleaseCapacity(ReleaseDbo release) {
        return release.getRequirements().stream().mapToDouble(r -> requirementEffortMap.get(r.getId())).sum();
    }

    private double computeUtilityPerRelease(ReleaseDbo release) {
        return release.getRequirements().stream().mapToDouble(r -> sortedRequirementsUtilitiesMap.get(r.getId())).sum();
    }

    public void assignRequirements() {
        for (Map.Entry<Long, Double> entry : sortedRequirementsUtilitiesMap.entrySet()) {
            Long requirementID = entry.getKey();
            RequirementDbo requirement = requirementsMap.get(requirementID);
            float effortOfRequirement = requirementEffortMap.get(requirementID);

            // check if already assigned to release
            if (alreadyAssigned(requirement, sortedReleases)) {
                continue;
            }

            List<ReleaseDbo> previousReleases = new ArrayList<>();
            List<ReleaseDbo> furtherReleases = new ArrayList<>(sortedReleases);

            Dictionary<Integer, TreeLevel> treeLevels = getTreeLevels(requirement);
            printTreeLevelsInfo(treeLevels, requirement);
            int checkedReleasesCount = 0;

            for (ReleaseDbo release : sortedReleases) {
                List<DependencyDbo> dependencies = new ArrayList<>();
                dependencies.addAll(requirement.getSourceDependencies());
                dependencies.addAll(requirement.getTargetDependencies());

                long dependencyCount = dependencies.stream().filter(d -> d.getType() == DependencyDbo.Type.REQUIRES).count();

                if (dependencyCount == 0 && (computeConsumedReleaseCapacity(release) + effortOfRequirement) > release.getCapacity()) {
                    previousReleases.add(release);
                    furtherReleases.remove(release);
                    checkedReleasesCount++;
                    continue;
                }

                furtherReleases.remove(release);

                boolean allReqDependenciesAssigned = assignReqs(requirement, release, treeLevels, previousReleases, furtherReleases);

                if (allReqDependenciesAssigned) {
                    break;
                }

                previousReleases.add(release);
                checkedReleasesCount++;
            }

            printReleases(sortedReleases, "");

            if (checkedReleasesCount < sortedReleases.size()) {
                // not able to assign req - not enough capa
                // postpone req
            }
        }
    }

    private boolean alreadyAssigned(RequirementDbo req, List<ReleaseDbo> releases) {
        for (ReleaseDbo release : releases) {
            if (release.getRequirements().contains(req)) {
                return true;
            }
        }
        return false;
    }

    private boolean assignReqs(RequirementDbo req, ReleaseDbo release, Dictionary<Integer, TreeLevel> treeLevels,
                                       List<ReleaseDbo> previousReleases, List<ReleaseDbo> furtherReleases) {
        if (treeLevels.isEmpty() || treeLevels.size() == 1) {
            // add req to release + reduce capa
            return addToRelease(release, req, null);
        }

        Set<RequirementDbo> addedReqs = new HashSet<>(release.getRequirements()); // contains reqs which were already assigned in this release or in a previous release; only required requs are added
        Set<RequirementDbo> addedToReleaseReqs = new HashSet<>(release.getRequirements()); // contains reqs which were assigned in this release
//        List<TreeObject> openReqs = new ArrayList<>(); // contains all reqs which were also in a higher level of the tree; needed as list - insertion order
//        List<TreeObject> skipReqs = new ArrayList<>(); // contains all reqs to skip - one of the successors contains a exclude req and this req is already in the release
//        Set<Requirement> excludeReqs = new HashSet<>(); // contains all reqs to exclude

        PlanningListContainer listContainer = new PlanningListContainer(addedReqs, addedToReleaseReqs);

        for (int currentTreePos = treeLevels.size() - 1; currentTreePos >= 0; currentTreePos--) {
            List<RequirementDbo> alreadyAssigned = new ArrayList<>(); // contains all assigned reqs from previous releases
            List<RequirementDbo> laterAssigned = new ArrayList<>(); // contains all assigned reqs from later releases
            previousReleases.forEach(r -> alreadyAssigned.addAll(r.getRequirements()));
            furtherReleases.forEach(r -> laterAssigned.addAll(r.getRequirements()));

            listContainer.setReqsAssignedToPreviousReleases(alreadyAssigned);
            listContainer.setReqsAssignedToFurtherReleases(laterAssigned);
            listContainer.setHigherNodes(getAllHigherLevelNodes(treeLevels, currentTreePos));

            boolean result = addRequirementsPerLevel(release, treeLevels.get(currentTreePos), listContainer);

            if (!result) {
                return false;
            }
            // if capa is exceeded - return remaining reqs?? possible
        }

        if (listContainer.getSkipReqs().size() > 0 || listContainer.getOpenReqs().size() > 0) {
            return false;
        }
        return true;
    }

    private List<TreeObject> getAllHigherLevelNodes(Dictionary<Integer, TreeLevel> treeLevels, int currentLevel) {
        List<TreeObject> nodes = new ArrayList<>();
        for (currentLevel = currentLevel - 1; currentLevel >= 0; currentLevel--) {
            nodes.addAll(treeLevels.get(currentLevel).getRequiresRequirements());
        }
        return nodes;
    }

    private Dictionary<Integer, TreeLevel> getTreeLevels(RequirementDbo rootReq) {
        Dictionary<Integer, TreeLevel> treeLevels = new Hashtable<>();

        boolean stop = false;
        int levelCount = 0;

        TreeObject root = new TreeObject(rootReq, null);
        root.setRoot(true);
        TreeLevel rootLevel = new TreeLevel();
        rootLevel.addRequiresReq(root);
        treeLevels.put(0, rootLevel);
        List<RequirementDbo> higherRequs = new ArrayList<>();

        // or while true
        while (!stop) {
            TreeLevel level = getTreeLevel(treeLevels.get(levelCount).getRequiresRequirements(), higherRequs);
            if ((level.getExcludesRequirements().size() == 0) && (level.getRequiresRequirements().size() == 0)) {
                stop = true;
            } else {
                levelCount++;
                treeLevels.put(levelCount, level);
            }
        }
        return treeLevels;
    }

    private TreeLevel getTreeLevel(List<TreeObject> previousLevelObjects, List<RequirementDbo> higherRequs) {
        TreeLevel level = new TreeLevel();

        // collect all leaf nodes from the current node
        for (TreeObject node : previousLevelObjects) {
            // check higher level nodes - don´t add
            if (higherRequs.contains(node.getCurrentReq())) {
                continue;
            }

            List<DependencyDbo> dependencies = new ArrayList<>();
            dependencies.addAll(node.getCurrentReq().getSourceDependencies());

            for (DependencyDbo dependency : dependencies) {
                if (dependency.getType() == DependencyDbo.Type.REQUIRES) {
                    node.addChildRequiresRequs(dependency.getTargetRequirement());
                    level.addRequiresReq(new TreeObject(dependency.getTargetRequirement(), node));
                } else {
                    node.addChildExcludeRequs(dependency.getTargetRequirement());
                    level.addExcludesReq(new TreeObject(dependency.getTargetRequirement(), node));
                }
            }

            dependencies = new ArrayList<>();
            dependencies.addAll(node.getCurrentReq().getTargetDependencies());

            for (DependencyDbo dependency : dependencies) {
                if (dependency.getType() == DependencyDbo.Type.REQUIRES) {
                    node.addChildRequiresRequs(dependency.getSourceRequirement());
                    level.addRequiresReq(new TreeObject(dependency.getSourceRequirement(), node));
                } else {
                    node.addChildExcludeRequs(dependency.getSourceRequirement());
                    level.addExcludesReq(new TreeObject(dependency.getSourceRequirement(), node));
                }
            }

            if (node.getChildRequiresRequs().size() > 0) {
                higherRequs.add(node.getCurrentReq());
            }
        }

        return level;
    }

    private boolean addRequirementsPerLevel(ReleaseDbo release, TreeLevel currentLevel, PlanningListContainer listContainer) {

        for (TreeObject currentNode : currentLevel.getRequiresRequirements()) {
            boolean result = addRequirement(currentNode, release, listContainer, true);
            if (!result) {
                return false;
            }
        }

        return true;
    }

    private boolean addRequirement(TreeObject currentNode, ReleaseDbo release, PlanningListContainer listContainer, boolean checkHigherNodes) {

        if (listContainer.getAddedReqs().contains(currentNode.getCurrentReq())) {
            if (listContainer.getAddedToReleaseReqs().contains(currentNode.getCurrentReq())) {
                listContainer.getExcludeReqs().addAll(currentNode.getChildExcludeRequs());
            }

            return true;
        }

        // check if req is already assigned to release or to a previous release -> add to addedReqs
        if (listContainer.getReqsAssignedToPreviousReleases().contains(currentNode.getCurrentReq())) {

            listContainer.getAddedReqs().add(currentNode.getCurrentReq());
            return true;
        }

        // check if req is assigned to a later release
        if (listContainer.getReqsAssignedToFurtherReleases().contains(currentNode.getCurrentReq())) {
            listContainer.getSkipReqs().add(currentNode.getPredecessor());
            return true;
        }

        //excludes
        // check if req is in release - skip all predecessors -> add to skipReqs
        if (listContainer.getExcludeReqs().contains(currentNode.getCurrentReq()) || !Collections.disjoint(listContainer.getAddedToReleaseReqs(), currentNode.getChildExcludeRequs())) {
            listContainer.getSkipReqs().add(currentNode);
            listContainer.getSkipReqs().add(currentNode.getPredecessor());
            return true;
        }

        // check if req is in skipReqs - add predecessor to list
        if (listContainer.getSkipReqs().contains(currentNode)) {
            listContainer.getSkipReqs().add(currentNode.getPredecessor());
            return true;
        }

        // check if req is also in a higher level -  add to open
        if (checkHigherNodes) {
            if (listContainer.getHigherNodes().contains(currentNode)) {
                currentNode.setHigherNodeAvailable(true);
                listContainer.getOpenReqs().add(currentNode);
                listContainer.getOpenReqs().add(currentNode.getPredecessor());
                return true;
            }
            // check if is in openReq list -  add predecessor
            else if (listContainer.getOpenReqs().contains(currentNode)) {
                if (!listContainer.getAddedReqs().containsAll(currentNode.getChildRequiresRequs())) {

                    // don´t add root
                    if (currentNode.getPredecessor() != null && currentNode.getPredecessor().isRoot()) {
                        return true;
                    }

                    listContainer.getOpenReqs().add(currentNode.getPredecessor());
                }
                // all childs are in added/or other releases - add req and all predecessors
                else {
                    if (!addToRelease(release, currentNode.getCurrentReq(), listContainer)) {
                        return false;
                    }

                    listContainer.getOpenReqs().removeAll(Collections.singleton(currentNode));

                    // add all affected nodes from openReqs
                    return addOpenReqs(release, listContainer);
                }

                return true;
            }
        }

        // add + capa check
        if (!addToRelease(release, currentNode.getCurrentReq(), listContainer)) {
//                continue;
            // TODO handle max capa - try remaining nodes - or go to next release? --> at the moment it goes to the next release
            return false;
        }

        listContainer.getExcludeReqs().addAll(currentNode.getChildExcludeRequs());

        return true;
    }

    private boolean addOpenReqs(ReleaseDbo release, PlanningListContainer listContainer) {
        List<TreeObject> currentlyAdded = new ArrayList<>();
        for (TreeObject openNode : listContainer.getOpenReqs()) {

            if (openNode.getChildRequiresRequs().size() > 0 && !listContainer.getAddedReqs().containsAll(openNode.getChildRequiresRequs())) {
                continue;
            }

            if (openNode.isHigherNodeAvailable()) {
                continue;
            }

            boolean result = addRequirement(openNode, release, listContainer, false);
            if (!result) {
                return false;
            }

            currentlyAdded.add(openNode);
        }

        listContainer.getOpenReqs().removeAll(currentlyAdded);
        return true;
    }

    private boolean addToRelease(ReleaseDbo release, RequirementDbo requirement, PlanningListContainer listContainer) {
        float effortOfRequirement = requirementEffortMap.get(requirement.getId());
        double newCapacity = computeConsumedReleaseCapacity(release) + effortOfRequirement;
        if (newCapacity > release.getCapacity()) {
            return false;
        }

        release.getRequirements().add(requirement);
        requirement.setRelease(release);

        if (listContainer != null) {
            listContainer.getAddedReqs().add(requirement);
            listContainer.getAddedToReleaseReqs().add(requirement);
        }

        List<ReleaseDbo> re = new ArrayList<>();
        re.add(release);
        //printReleases(re, String.format("added re: [%s] \n", requirement.getTitle()));

        return true;
    }

    private void printTreeLevelsInfo(Dictionary<Integer, TreeLevel> treeLevels, RequirementDbo requirement) {
        logger.info(String.format("[%s] tree height: %s", requirement.getTitle(), treeLevels.size()));

        for (int level = 0; level < treeLevels.size(); level++) {
            String reqsRequires = treeLevels.get(level).getRequiresRequirements().stream().map(t -> t.getCurrentReq().getTitle()).collect(Collectors.joining(","));
            String reqsExcludes= treeLevels.get(level).getExcludesRequirements().stream().map(t -> t.getCurrentReq().getTitle()).collect(Collectors.joining(","));
            logger.info(String.format("level: %s, - Requires {%s} - Excludes {%s}", level, reqsRequires, reqsExcludes));
        }
    }

    private void printReleases(List<ReleaseDbo> releases, String msg) {
        logger.info("------------------------------");

        for (ReleaseDbo release : releases) {
            String requs = release.getRequirements().stream().map(RequirementDbo::getTitle).collect(Collectors.joining(","));
            logger.info(String.format("%s Release: %s, - Requirements {%s}", msg, release.getName(), requs));
        }
    }


    public List<Double> calculateCumulativeRelevance(){
        List<Double> cummulativeRelevanceOfReleases = new ArrayList<>();
        double relWeight = sortedReleases.size()*0.1;
        double cumulativeRelevance = 0.0;
        for (ReleaseDbo rel : sortedReleases){
            cumulativeRelevance += computeUtilityPerRelease(rel)*relWeight; //TODO: consider the free capacity of a release in the future except the last release
            logger.info("Relevance for " + rel.getName() + "= " + cumulativeRelevance);
            cummulativeRelevanceOfReleases.add(cumulativeRelevance);
            relWeight -= 0.1;
        }
        return cummulativeRelevanceOfReleases;
    }

}
