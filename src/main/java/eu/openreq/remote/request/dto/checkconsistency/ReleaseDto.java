package eu.openreq.remote.request.dto.checkconsistency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseDto {
    private Long id;
    private String status;
    private List<Long> requirements;

    public void addRequirement(Long requirementID) {
        this.requirements.add(requirementID);
    }
}
