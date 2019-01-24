package eu.openreq.api.external.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ResponseDto {

    private String $schema = "http://json-schema.org/draft-06/schema#";
    private String title;
    private String description;
    private String type;
    private Map<String, Object> properties;
    private List<String> required;

}
