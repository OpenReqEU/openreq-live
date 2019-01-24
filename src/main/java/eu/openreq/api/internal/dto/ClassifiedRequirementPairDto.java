package eu.openreq.api.internal.dto;

import eu.openreq.dbo.PotentialDependencyDbo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassifiedRequirementPairDto {

    private long sourceRequirementID;
    private long targetRequirementID;
    private boolean dependencyExists;
    private PotentialDependencyDbo.Type dependencyType;
    private boolean reverseDirection;
    private long duration;

}
