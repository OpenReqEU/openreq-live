package eu.openreq.remote.request.dto.helsinki;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.data.annotation.Id;

import java.util.Date;

public class Element {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    public Long id;
    public String name;
    public String text;
    public Date created_at;
}
