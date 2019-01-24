package eu.openreq.remote.response.dto.checkconsistency;

import lombok.Data;

@Data
public class CheckConsistencyResponseDto {
    private boolean error;
    private String errorMessage;
    private CheckConsistencyDiagnosisDto diagnosis;
}
