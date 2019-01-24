package eu.openreq.remote.dto.helsinki;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReleaseDto {
    private Long id;
    private int capacity;
    private String status;
    private List<String> requirements;

    public ReleaseDto(Long id, int capacity, String status) {
        this.id = id;
        this.capacity = capacity;
        this.status = status;
        this.requirements = new ArrayList<>();
    }

    public void addRequirement(String requirement) {
        this.requirements.add(requirement);
    }
}
