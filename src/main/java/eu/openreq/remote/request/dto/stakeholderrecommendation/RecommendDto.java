package eu.openreq.remote.request.dto.stakeholderrecommendation;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecommendDto {

    private RecommendRequestProjectDto project;
    private RecommendRequestRequirementDto requirement;
    private RecommendRequestUserDto user;

}
