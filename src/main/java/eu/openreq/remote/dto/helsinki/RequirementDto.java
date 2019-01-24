package eu.openreq.remote.dto.helsinki;

import lombok.Data;

@Data
public class RequirementDto {
    private String id;
    private String name;
    private int effort;

    public RequirementDto(String id, String name, int effort) {
        this.id = id;
        this.name = name;
        this.effort = effort;
    }
}
