package eu.openreq.api.external.dto;

import lombok.Data;

@Data
public class CreateRequirementResponseDto {
    private boolean error;
    private String errorMessage;
}