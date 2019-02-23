package eu.openreq.api.internal.dto;

import eu.openreq.dbo.IssueDbo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueDto {
    private String title;
    private String description;
    private IssueDbo.Priority priority;
}
