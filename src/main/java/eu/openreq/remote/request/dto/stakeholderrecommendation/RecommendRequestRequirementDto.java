package eu.openreq.remote.request.dto.stakeholderrecommendation;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecommendRequestRequirementDto {

    public String id;
    public String name;
    public String description;
    public String effort;
    public String modified_at;

}
