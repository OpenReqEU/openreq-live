package eu.openreq.api.external.dto;

import eu.openreq.dbo.RequirementDbo;
import lombok.Data;

@Data
public class CreateRequirementDto {
    String projectUniqueKey;
    String title;
    String description;
    RequirementDbo.Status status;
}
