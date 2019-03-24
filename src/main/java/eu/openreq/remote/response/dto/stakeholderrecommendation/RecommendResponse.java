package eu.openreq.remote.response.dto.stakeholderrecommendation;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RecommendResponse {

    private String requirement;
    private String person;
    private float availabilityScore;
    private float apropiatenessScore;

}
