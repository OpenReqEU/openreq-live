package eu.openreq.remote.request.dto.helsinki;


import eu.openreq.dbo.RequirementDbo;
import lombok.Data;

import java.util.Date;

@Data
public class Requirement extends Element {

    private int effort;
    private RequirementDbo.Status status;

    public Requirement(String id, String title, String description, RequirementDbo.Status status, int effort, Date created_at) {
        setId(id);
        setName(title);
        setText(description);
        this.status = status;
        setCreated_at(created_at);
        this.effort = effort;
    }

    public int getEffort() {
        return effort;
    }
}
