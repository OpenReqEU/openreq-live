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
@Table(name = "or_requirement_attribute_vote")
@AssociationOverrides({
	@AssociationOverride(name = "primaryKey.user", joinColumns = @JoinColumn(name = "user_id")),
	@AssociationOverride(name = "primaryKey.requirement", joinColumns = @JoinColumn(name = "requirement_id")),
	@AssociationOverride(name = "primaryKey.ratingAttribute", joinColumns = @JoinColumn(name = "rating_attribute_id"))
})
public class UserRequirementAttributeVoteDbo {

	@EmbeddedId
	private UserRequirementAttributeVoteId primaryKey = new UserRequirementAttributeVoteId();

	@Column(name = "value", nullable=false)
	private int value;

	@Column(name = "created_date", nullable=false)
	private Date createdDate;

	@Column(name = "last_updated_date", nullable=false)
	private Date lastUpdatedDate;

	public UserRequirementAttributeVoteDbo() {}

	public UserRequirementAttributeVoteDbo(int value, RequirementDbo requirement, RatingAttributeDbo ratingAttribute, UserDbo user) throws DboConstraintException {
		this.setRequirement(requirement);
		this.setUser(user);
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

    public UserRequirementAttributeVoteId getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(UserRequirementAttributeVoteId primaryKey) {
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
    public UserDbo getUser() {
        return getPrimaryKey().getUser();
    }

	@Transient
	public long getUserId() {
		return getUser().getId();
	}

	public void setUser(UserDbo user) {
		getPrimaryKey().setUser(user);
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

	@Override
	public String toString() {
		return "UserRequirementAttributeVoteDbo{userID=" + getUserId() +
				", userName='" + getUser().getFirstName() + " " + getUser().getLastName() + "'" +
				", requirementID=" + getRequirementId() +
				", requirementTitle=" + getRequirement().getTitle() +
				", ratingAttributeID=" + getRatingAttribute().getId() +
				", ratingAttributeName=" + getRatingAttribute().getName() +
				", value=" + value +
				"}";
	}


	public static int convertBarsToHours(int noBars)
	{
		return (noBars > 0 && noBars <= 10) ?  2 << (noBars-2) : 0;
	}

}
