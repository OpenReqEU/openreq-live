package eu.openreq.remote.dto.helsinki;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class CheckConsistencyResponseDto {
    private Boolean error;
    private Boolean consistent;
    private List<String> diagnosis;

    public CheckConsistencyResponseDto() {
        diagnosis = new ArrayList<>();
    }

    public void addDiagnosis(String conflictingRequirement) {
        diagnosis.add(conflictingRequirement);
    }

}
