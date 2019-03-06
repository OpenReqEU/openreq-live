package eu.openreq.remote.request.dto.helsinki;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import eu.openreq.dbo.DependencyDbo;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Dependency {

    private DependencyDbo.Type dependency_type;
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long fromid;
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long toid;
    private Date created_at;

    public Dependency(DependencyDbo.Type dependencyType, Long fromid, Long toid, Date created_at) {
        this.dependency_type = dependencyType;
        this.fromid = fromid;
        this.toid = toid;
        this.created_at = created_at;
    }
}
