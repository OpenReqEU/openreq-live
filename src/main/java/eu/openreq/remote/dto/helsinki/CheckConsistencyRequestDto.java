package eu.openreq.remote.dto.helsinki;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class CheckConsistencyRequestDto {

	private ProjectDto project;
	private List<RequirementDto> requirements;
	private List<ReleaseDto> releases;
	private List<DependencyDto> dependencies;

	public CheckConsistencyRequestDto() {
		this.requirements = new ArrayList<>();
		this.releases = new ArrayList<>();
		this.dependencies = new ArrayList<>();
	}

	public void addRequirement(RequirementDto requirement) {
	    requirements.add(requirement);
    }

	public void addRelease(ReleaseDto release) {
        releases.add(release);
    }

	public void addDependency(DependencyDto dependency) {
        dependencies.add(dependency);
    }

}
