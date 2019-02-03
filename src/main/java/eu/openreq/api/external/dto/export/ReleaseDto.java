package eu.openreq.api.external.dto.export;

import eu.openreq.dbo.ReleaseDbo;
import lombok.Data;
import java.util.List;

@Data
public class ReleaseDto {
    private Long id;
    private String name;
    private String description;
    private int maximumCapacity;
    private Long endDateTimestamp;
    private boolean isVisible;
    private ReleaseDbo.Status status;
    private List<RequirementDto> requirements;
}
