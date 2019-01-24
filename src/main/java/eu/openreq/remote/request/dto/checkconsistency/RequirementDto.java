package eu.openreq.remote.request.dto.checkconsistency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequirementDto {
    private Long id;
    private String name;
    private int effort;
}
