package eu.openreq.statistics;

import java.util.ArrayList;
import java.util.List;

public class TreeLevel {
    List<TreeObject> requiresRequirements;
    List<TreeObject> excludesRequirements;

    public TreeLevel() {
        this.requiresRequirements = new ArrayList<>();
        this.excludesRequirements = new ArrayList<>();
    }

    public List<TreeObject> getRequiresRequirements() {
        return requiresRequirements;
    }

    public void setRequiresRequirements(List<TreeObject> requiresRequirements) {
        this.requiresRequirements = requiresRequirements;
    }

    public void addRequiresReq(TreeObject object) {
        this.requiresRequirements.add(object);
    }

    public List<TreeObject> getExcludesRequirements() {
        return excludesRequirements;
    }

    public void setExcludesRequirements(List<TreeObject> excludesRequirements) {
        this.excludesRequirements = excludesRequirements;
    }

    public void addExcludesReq(TreeObject object) {
        this.excludesRequirements.add(object);
    }
}
