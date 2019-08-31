package eu.openreq.remote.request.dto.helsinki;

import org.springframework.data.annotation.Id;
import java.util.Date;

public class Element {

    @Id
    public String id;
    public String name;
    public String text;
    public Date created_at;

}
