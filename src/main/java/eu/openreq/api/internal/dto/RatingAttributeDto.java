package eu.openreq.api.internal.dto;

public class RatingAttributeDto {

	private long id;
	private String name;
	private String description;
	private String iconName;
	private int minValue;
	private int maxValue;
	private int interval;
	private float weight;

	public RatingAttributeDto() {
		this.id = 0;
		this.name = null;
		this.description = null;
		this.iconName = null;
		this.minValue = 0;
		this.maxValue = 0;
		this.interval = 0;
		this.weight = 0.0f;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
}
