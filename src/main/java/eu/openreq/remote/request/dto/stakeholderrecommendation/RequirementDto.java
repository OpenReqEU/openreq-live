package eu.openreq.remote.request.dto.stakeholderrecommendation;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequirementDto {

    private String id;
    private String description;
    private int effort;
    private String modified_at;

}
