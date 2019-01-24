package eu.openreq.remote.request.dto.helsinki;


import eu.openreq.dbo.RequirementDbo;
import lombok.Data;

import java.util.Date;

@Data
public class Requirement extends Element {

    private int effort;
    private RequirementDbo.Status status;

    public Requirement(Long id,  RequirementDbo.Status status, Date created_at) {
        this.id = id;
        this.status = status;
        this.created_at = created_at;
    }

    public int getEffort() {
        return effort;
    }
}
