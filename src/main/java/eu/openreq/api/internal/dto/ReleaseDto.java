package eu.openreq.api.internal.dto;

import lombok.Data;

@Data
public class ReleaseDto {

	private long id;
	private String name;
	private String description;
	private int capacity;
	private Long endDateTimestamp;

	public ReleaseDto() {
		this.id = 0;
		this.name = null;
		this.description = null;
		this.capacity = 0;
		this.endDateTimestamp = 0L;
	}
}
