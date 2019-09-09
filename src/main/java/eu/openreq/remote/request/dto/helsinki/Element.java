package eu.openreq.remote.request.dto.helsinki;

import lombok.Data;
import org.springframework.data.annotation.Id;
import java.util.Date;

@Data
public class Element {

    @Id
    private String id;
    private String name;
    private String text;
    private Date created_at;

}
