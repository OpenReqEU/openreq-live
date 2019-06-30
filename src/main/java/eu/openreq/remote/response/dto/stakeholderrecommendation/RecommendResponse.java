package eu.openreq.remote.response.dto.stakeholderrecommendation;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RecommendResponse {

    private RecommendResponseRequirementDto requirement;
    private RecommendResponsePersonDto person;
    private float availabilityScore;
    private float appropiatenessScore;

}
