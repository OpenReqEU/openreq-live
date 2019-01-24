package eu.openreq.statistics;

import eu.openreq.dbo.RequirementDbo;
import java.util.*;

public class TreeObject {
    private RequirementDbo currentReq;
    private TreeObject predecessor;
    private boolean higherNodeAvailable = false;
    private boolean isRoot = false;
    private Set<RequirementDbo> childRequiresRequs;
    private Set<RequirementDbo> childExcludeRequs;

    public TreeObject(RequirementDbo rootReq, Object predecessor) {
        this.childRequiresRequs = new HashSet<>();
        this.childExcludeRequs = new HashSet<>();
    }

    public TreeObject(RequirementDbo currentReq, TreeObject predecessor) {
        this.currentReq = currentReq;
        this.predecessor = predecessor;
        this.childRequiresRequs = new HashSet<>();
        this.childExcludeRequs = new HashSet<>();
    }

    public RequirementDbo getCurrentReq() {
        return currentReq;
    }

    public void setCurrentReq(RequirementDbo currentReq) {
        this.currentReq = currentReq;
    }

    public TreeObject getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(TreeObject predecessor) {
        this.predecessor = predecessor;
    }

    public Set<RequirementDbo> getChildRequiresRequs() {
        return childRequiresRequs;
    }

    public void setChildRequiresRequs(Set<RequirementDbo> childRequiresRequs) {
        this.childRequiresRequs = childRequiresRequs;
    }

    public void addChildRequiresRequs(RequirementDbo childRequiresRequ) {
        this.childRequiresRequs.add(childRequiresRequ);
    }

    public Set<RequirementDbo> getChildExcludeRequs() {
        return childExcludeRequs;
    }

    public void setChildExcludeRequs(Set<RequirementDbo> childExcludeRequs) {
        this.childExcludeRequs = childExcludeRequs;
    }

    public void addChildExcludeRequs(RequirementDbo childExcludeRequs) {
        this.childExcludeRequs.add(childExcludeRequs);
    }

    public boolean isHigherNodeAvailable() {
        return higherNodeAvailable;
    }

    public void setHigherNodeAvailable(boolean higherNodeAvailable) {
        this.higherNodeAvailable = higherNodeAvailable;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeObject object = (TreeObject) o;
        return Objects.equals(currentReq, object.currentReq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentReq);
    }
}
