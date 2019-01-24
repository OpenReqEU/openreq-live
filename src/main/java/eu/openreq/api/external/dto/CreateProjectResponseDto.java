package eu.openreq.api.external.dto;

import lombok.Data;

@Data
public class CreateProjectResponseDto {
    private boolean error;
    private String errorMessage;
    private String uniqueKey;
}
