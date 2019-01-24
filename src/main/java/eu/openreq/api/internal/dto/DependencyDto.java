package eu.openreq.api.internal.dto;

import eu.openreq.dbo.DependencyDbo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DependencyDto {
    private Long sourceRequirementID;
    private Long targetRequirementID;
    private DependencyDbo.Type type;
}
