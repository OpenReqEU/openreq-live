package eu.openreq.api.external.dto.export;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private List<ReleaseDto> releases;
    private List<RequirementDto> unassignedRequirements;
}
