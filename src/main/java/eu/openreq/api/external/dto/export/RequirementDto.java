package eu.openreq.api.external.dto.export;

import eu.openreq.dbo.RequirementDbo;
import lombok.Data;

@Data
public class RequirementDto {
    private Long id;
    private String title;
    private String description;
    private Long projectSpecificRequirementId;
    private Long releaseID;
    private RequirementDbo.Status status;
}
