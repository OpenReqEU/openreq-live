package eu.openreq.remote.request.dto.helsinki;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class Project extends Element {

    private List<String> releases;
    private List<String> specifiedRequirements;
    private ProjectStates status;

    public Project(Long id, ProjectStates status, Date createdAt) {
        this.releases = new ArrayList<>();
        this.specifiedRequirements = new ArrayList<>();
        this.id = id;
        this.status = status;
        this.created_at = createdAt;
    }
}
