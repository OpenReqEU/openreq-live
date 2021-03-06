package eu.openreq.remote.request.dto.stakeholderrecommendation;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequirementDto {

    private String id;
    private String name;
    private String description;
    private String effort;
    private String modified_at;

}
