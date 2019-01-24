package eu.openreq.remote.request.dto.checkconsistency;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class CheckConsistencyRequestDto {

    private ProjectDto project;
    private List<RequirementDto> requirements;
    private List<ReleaseDto> releases;
    private List<ConstraintDto> constraints;

    public CheckConsistencyRequestDto() {
        this.requirements = new ArrayList<>();
        this.releases = new ArrayList<>();
        this.constraints = new ArrayList<>();
    }

    public void addRequirement(RequirementDto requirement) {
        this.requirements.add(requirement);
    }

    public void addConstraint(ConstraintDto constraint) {
        this.constraints.add(constraint);
    }

    public void addRelease(ReleaseDto release) {
        this.releases.add(release);
    }
}
