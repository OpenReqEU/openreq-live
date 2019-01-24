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

    public Release(Long id, ReleaseDbo.Status status, int capacity, Date created) {
        this.id = id;
        this.status = status;
        this.created_at = created;
        this.requirements = new ArrayList<>();
        this.capacity = capacity;
    }
}
