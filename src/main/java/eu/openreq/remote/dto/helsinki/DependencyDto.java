package eu.openreq.remote.dto.helsinki;

import lombok.Data;

@Data
public class DependencyDto {
    private DependencyTypeDto dependency_type;
    private String from;
    private String to;

    public DependencyDto(DependencyTypeDto dependencyType, String from, String to) {
        this.dependency_type = dependencyType;
        this.from = from;
        this.to = to;
    }
}
