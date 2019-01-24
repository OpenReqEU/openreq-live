package eu.openreq.dbo;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class AnonymousUserRequirementAttributeVoteId implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade = CascadeType.DETACH)
	private RequirementDbo requirement;

	@ManyToOne(cascade = CascadeType.DETACH)
	private RatingAttributeDbo ratingAttribute;

	private String nameOfAnonymousUser;

	public RequirementDbo getRequirement() {
		return requirement;
	}

	public void setRequirement(RequirementDbo requirement) {
		this.requirement = requirement;
	}

	public RatingAttributeDbo getRatingAttribute() {
		return ratingAttribute;
	}

	public void setRatingAttribute(RatingAttributeDbo ratingAttribute) {
		this.ratingAttribute = ratingAttribute;
	}

	public String getNameOfAnonymousUser() {
		return nameOfAnonymousUser;
	}

	public void setNameOfAnonymousUser(String nameOfAnonymousUser) {
		this.nameOfAnonymousUser = nameOfAnonymousUser;
	}

}
