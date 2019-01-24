package eu.openreq.remote.dto.helsinki;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectDto {
    private String id;
    private String name;
    private List<String> specificRequirements;

    public ProjectDto(String id, String name) {
        this.id = id;
        this.name = name;
        this.specificRequirements = new ArrayList<>();
    }

    public void addSpecificRequirement(String specificRequirement) {
        specificRequirements.add(specificRequirement);
    }
}
