package eu.openreq.api.internal.dto;

import lombok.Data;

@Data
public class BasicVoteDto {
    private long requirementID;
    private Integer ratingValue;
}
