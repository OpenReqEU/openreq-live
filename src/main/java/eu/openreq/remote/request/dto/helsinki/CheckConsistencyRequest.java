package eu.openreq.remote.request.dto.helsinki;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CheckConsistencyRequest {

    private Project project;
    private List<Dependency> dependencies;
    private List<Release> releases;
    private List<Requirement> requirements;

    public CheckConsistencyRequest(Project project, List<Dependency> dependencies,
                                   List<Release> releases,
                                   List<Requirement> requirements) {
        this.dependencies = dependencies;
        this.releases = releases;
        this.requirements = requirements;
        this.project = project;

        if (releases != null)
        {
            project.setReleases(releases.stream().map(x -> x.getId().toString())
                    .collect(Collectors.toList()));
        }

        if (requirements != null)
        {
            project.setSpecifiedRequirements(requirements.stream().map(x -> x.getId().toString())
                    .collect(Collectors.toList()));
        }


    }

}
