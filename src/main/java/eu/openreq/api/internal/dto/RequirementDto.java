package eu.openreq.api.internal.dto;

import eu.openreq.dbo.RequirementDbo;
import lombok.Data;

@Data
public class RequirementDto {

	private long id;
	private String title;
	private String description;
	private String importID;
	private Long assignedReleaseID;
	private RequirementDbo.Status status;

	public RequirementDto() {
		this.id = 0;
		this.title = null;
		this.description = null;
		this.importID = null;
		this.assignedReleaseID = 0L;
	}
}
