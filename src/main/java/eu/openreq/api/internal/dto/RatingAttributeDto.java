package eu.openreq.api.internal.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RatingAttributeDto {
	private long id;
	private String name;
	private String description;
	private String iconName;
	private int minValue;
	private int maxValue;
	private int interval;
	private float weight;
}
