package eu.openreq.dbo;

import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import eu.openreq.exception.DboConstraintException;

@Entity
@Table(name = "or_requirement_anonymous_attribute_vote")
@AssociationOverrides({
	@AssociationOverride(name = "primaryKey.requirement", joinColumns = @JoinColumn(name = "requirement_id")),
	@AssociationOverride(name = "primaryKey.ratingAttribute", joinColumns = @JoinColumn(name = "rating_attribute_id"))
})
public class AnonymousUserRequirementAttributeVoteDbo {

	@EmbeddedId
	private AnonymousUserRequirementAttributeVoteId primaryKey = new AnonymousUserRequirementAttributeVoteId();

	@Column(name = "value", nullable=false)
	private int value;

	@Column(name = "created_date", nullable=false)
	private Date createdDate;

	@Column(name = "last_updated_date", nullable=false)
	private Date lastUpdatedDate;

	public AnonymousUserRequirementAttributeVoteDbo() {}

	public AnonymousUserRequirementAttributeVoteDbo(int value, RequirementDbo requirement, RatingAttributeDbo ratingAttribute, String nameOfAnonymousUser) throws DboConstraintException {
		this.setRequirement(requirement);
		this.setNameOfAnonymousUser(nameOfAnonymousUser);
		this.setRatingAttribute(ratingAttribute);
		setValue(value);
		this.createdDate = null;
		this.lastUpdatedDate = null;
	}

	@PrePersist
	protected void onCreate() {
		this.createdDate = new Date();
		this.lastUpdatedDate = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.lastUpdatedDate = new Date();
	}

    public AnonymousUserRequirementAttributeVoteId getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(AnonymousUserRequirementAttributeVoteId primaryKey) {
        this.primaryKey = primaryKey;
    }

	@Transient
	public RequirementDbo getRequirement() {
		return getPrimaryKey().getRequirement();
	}

	@Transient
	public long getRequirementId() {
		return getRequirement().getId();
	}

	public void setRequirement(RequirementDbo requirement) {
		getPrimaryKey().setRequirement(requirement);
	}

	public RatingAttributeDbo getRatingAttribute() {
		return getPrimaryKey().getRatingAttribute();
	}

	public void setRatingAttribute(RatingAttributeDbo ratingAttribute) {
		getPrimaryKey().setRatingAttribute(ratingAttribute);
	}

	@Transient
	public String getNameOfAnonymousUser() {
		return getPrimaryKey().getNameOfAnonymousUser();
	}

	@Transient
	public void setNameOfAnonymousUser(String nameOfAnonymousUser) {
		getPrimaryKey().setNameOfAnonymousUser(nameOfAnonymousUser);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) throws DboConstraintException {
		// sanity check
		if (value < getRatingAttribute().getMinValue() || value > getRatingAttribute().getMaxValue()) {
			throw new DboConstraintException("value must lie in between " + getRatingAttribute().getMinValue()
			                               + " and " + getRatingAttribute().getMaxValue());
		}
		this.value = value;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

}
