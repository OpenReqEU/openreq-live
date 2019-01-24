package eu.openreq.statistics;

import eu.openreq.dbo.RequirementDbo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlanningListContainer {
    private Set<RequirementDbo> addedReqs; // contains reqs which were already assigned in this release or in a previous release; only required requs are added
    private Set<RequirementDbo> addedToReleaseReqs; // contains reqs which were assigned in this release
    private List<TreeObject> openReqs; // contains all reqs which were also in a higher level of the tree; needed as list - insertion order
    private List<TreeObject> skipReqs; // contains all reqs to skip - one of the successors contains a exclude req and this req is already in the release
    private Set<RequirementDbo> excludeReqs; // contains all reqs to exclude
    private List<RequirementDbo> reqsAssignedToPreviousReleases;
    private List<RequirementDbo> reqsAssignedToFurtherReleases;
    private List<TreeObject> higherNodes;

    public PlanningListContainer() {
        this.addedReqs = new HashSet<>();
        this.addedToReleaseReqs = new HashSet<>();
        this.openReqs = new ArrayList<>();
        this.skipReqs = new ArrayList<>();
        this.excludeReqs = new HashSet<>();
        this.reqsAssignedToPreviousReleases = new ArrayList<>();
        this.reqsAssignedToFurtherReleases = new ArrayList<>();
        this.higherNodes = new ArrayList<>();
    }

    public PlanningListContainer(Set<RequirementDbo> addedReqs, Set<RequirementDbo> addedToReleaseReqs) {
        this.addedReqs = addedReqs;
        this.addedToReleaseReqs = addedToReleaseReqs;
        this.openReqs = new ArrayList<>();
        this.skipReqs = new ArrayList<>();
        this.excludeReqs = new HashSet<>();
        this.reqsAssignedToPreviousReleases = new ArrayList<>();
        this.reqsAssignedToFurtherReleases = new ArrayList<>();
        this.higherNodes = new ArrayList<>();
    }

    public Set<RequirementDbo> getAddedReqs() {
        return addedReqs;
    }

    public void setAddedReqs(Set<RequirementDbo> addedReqs) {
        this.addedReqs = addedReqs;
    }

    public Set<RequirementDbo> getAddedToReleaseReqs() {
        return addedToReleaseReqs;
    }

    public void setAddedToReleaseReqs(Set<RequirementDbo> addedToReleaseReqs) {
        this.addedToReleaseReqs = addedToReleaseReqs;
    }

    public List<TreeObject> getOpenReqs() {
        return openReqs;
    }

    public void setOpenReqs(List<TreeObject> openReqs) {
        this.openReqs = openReqs;
    }

    public List<TreeObject> getSkipReqs() {
        return skipReqs;
    }

    public void setSkipReqs(List<TreeObject> skipReqs) {
        this.skipReqs = skipReqs;
    }

    public Set<RequirementDbo> getExcludeReqs() {
        return excludeReqs;
    }

    public void setExcludeReqs(Set<RequirementDbo> excludeReqs) {
        this.excludeReqs = excludeReqs;
    }

    public List<RequirementDbo> getReqsAssignedToPreviousReleases() {
        return reqsAssignedToPreviousReleases;
    }

    public void setReqsAssignedToPreviousReleases(List<RequirementDbo> reqsAssignedToPreviousReleases) {
        this.reqsAssignedToPreviousReleases = reqsAssignedToPreviousReleases;
    }

    public List<RequirementDbo> getReqsAssignedToFurtherReleases() {
        return reqsAssignedToFurtherReleases;
    }

    public void setReqsAssignedToFurtherReleases(List<RequirementDbo> reqsAssignedToFurtherReleases) {
        this.reqsAssignedToFurtherReleases = reqsAssignedToFurtherReleases;
    }

    public List<TreeObject> getHigherNodes() {
        return higherNodes;
    }

    public void setHigherNodes(List<TreeObject> higherNodes) {
        this.higherNodes = higherNodes;
    }
}
