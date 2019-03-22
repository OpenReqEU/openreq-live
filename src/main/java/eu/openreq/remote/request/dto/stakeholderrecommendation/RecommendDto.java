package eu.openreq.remote.request.dto.stakeholderrecommendation;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecommendDto {

    private String project;
    private String requirement;
    private String user;

}
