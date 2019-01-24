package eu.openreq.api.internal.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RequirementMessageDto {

    private long id;
    private String title;
    private String message;
    private Integer sentiment;
    private List<Long> assignedDimensions;

    public RequirementMessageDto() {
        this.id = 0;
        this.title = null;
        this.message = null;
        this.sentiment = null;
        this.assignedDimensions = new ArrayList<>();
    }

}
