package eu.openreq.remote.response.dto.checkconsistency;

import eu.openreq.remote.request.dto.checkconsistency.ConstraintDto;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class CheckConsistencyDiagnosisDto {

    private Boolean consistent;
    private List<ConstraintDto> constraints;

    public CheckConsistencyDiagnosisDto() {
        constraints = new ArrayList<>();
    }

    public void addConstraint(ConstraintDto constraint) {
        constraints.add(constraint);
    }

}
