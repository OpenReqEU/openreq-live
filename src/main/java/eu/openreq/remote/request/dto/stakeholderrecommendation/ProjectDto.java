package eu.openreq.remote.request.dto.stakeholderrecommendation;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProjectDto {

    private String id;
    private List<String> specifiedRequirements = new ArrayList<>();

    public void addSpecifiedRequirement(String specifiedRequirement) {
        this.specifiedRequirements.add(specifiedRequirement);
    }

}
