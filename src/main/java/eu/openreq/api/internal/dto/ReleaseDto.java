package eu.openreq.api.internal.dto;

import lombok.Data;

@Data
public class ReleaseDto {

	private long id;
	private String name;
	private String description;
	private Long endDateTimestamp;

	public ReleaseDto() {
		this.id = 0;
		this.name = null;
		this.description = null;
		this.endDateTimestamp = 0L;
	}
}
