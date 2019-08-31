package eu.openreq.remote.request.dto.helsinki;

import eu.openreq.dbo.ReleaseDbo;
import eu.openreq.statistics.Enums;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Release extends Element {

    private int capacity;
    private List<String> requirements;
    private ReleaseDbo.Status status;

    public Release(String id, String name, String description, ReleaseDbo.Status status, int capacity, Date created) {
        this.id = id;
        this.name = name;
        this.text = description;
        this.status = status;
        this.created_at = created;
        this.requirements = new ArrayList<>();
        this.capacity = capacity;
    }
}
