package eu.openreq.api.internal.dto;

public class VoteDimensionDto {

	private Integer ratingValue;

	private Long ratingAttributeID;

	public VoteDimensionDto() {
		this.ratingValue = 0;
		this.ratingAttributeID = 0L;
	}

	public Integer getRatingValue() {
		return ratingValue;
	}

	public void setRatingValue(Integer ratingValue) {
		this.ratingValue = ratingValue;
	}

	public Long getRatingAttributeID() {
		return ratingAttributeID;
	}

	public void setRatingAttributeID(Long ratingAttributeID) {
		this.ratingAttributeID = ratingAttributeID;
	}

}
