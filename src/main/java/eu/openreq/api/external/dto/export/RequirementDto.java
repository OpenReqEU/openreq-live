package eu.openreq.api.external.dto.export;

import lombok.Data;

@Data
public class RequirementDto {
    private Long id;
    private String title;
    private String description;
    private Long projectSpecificRequirementId;
    private Long releaseID;
}
