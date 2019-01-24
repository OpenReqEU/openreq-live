package eu.openreq.remote.dto;

import lombok.Data;
import java.util.List;

@Data
public class RemoteSimilarRequirementsDto {
    private long id;
    private long projectSpecificRequirementId;
    private String title;
    private String description;
    private long[] predictions;
    private List<String> comments;
}
