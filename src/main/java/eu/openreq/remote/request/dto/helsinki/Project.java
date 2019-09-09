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

    public Project(String id, String title, String description, ProjectStates status, Date createdAt) {
        setId(id);
        setName(title);
        setText(description);
        this.releases = new ArrayList<>();
        this.specifiedRequirements = new ArrayList<>();
        this.status = status;
        setCreated_at(createdAt);
    }
}
