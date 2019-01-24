package eu.openreq.dbo;

import java.util.Date;
import java.util.Objects;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "or_stakeholder_attribute_vote")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.user", joinColumns = @JoinColumn(name = "user_id")),
        @AssociationOverride(name = "primaryKey.ratedStakeholder", joinColumns = @JoinColumn(name = "rated_stakeholder_id")),
        @AssociationOverride(name = "primaryKey.requirement", joinColumns = @JoinColumn(name = "requirement_id")),
        @AssociationOverride(name = "primaryKey.ratingAttribute", joinColumns = @JoinColumn(name = "rating_attribute_id"))
})
public class UserStakeholderAttributeVoteDbo {

    @EmbeddedId
    private UserStakeholderAttributeVoteId primaryKey = new UserStakeholderAttributeVoteId();

    @Column(name = "value", nullable=false)
    private int value;

    @Column(name = "created_date", nullable=false)
    private Date createdDate;

    @Column(name = "last_updated_date", nullable=false)
    private Date lastUpdatedDate;

    public UserStakeholderAttributeVoteDbo(int value, RequirementDbo requirement, StakeholderRatingAttributeDbo ratingAttribute, UserDbo ratedStakeholder, UserDbo user) throws DboConstraintException {
        this.setRequirement(requirement);
        this.setRatedStakeholder(ratedStakeholder);
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

    public StakeholderRatingAttributeDbo getRatingAttribute() {
        return getPrimaryKey().getRatingAttribute();
    }

    public void setRatingAttribute(StakeholderRatingAttributeDbo ratingAttribute) {
        getPrimaryKey().setRatingAttribute(ratingAttribute);
    }

    @Transient
    public UserDbo getRatedStakeholder() {
        return getPrimaryKey().getRatedStakeholder();
    }

    @Transient
    public long getUserIdOfRatedStakeholder() {
        return getRatedStakeholder().getId();
    }

    public void setRatedStakeholder(UserDbo stakeholder) {
        getPrimaryKey().setRatedStakeholder(stakeholder);
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

    public void setValue(int value) throws DboConstraintException {
        // sanity check
        if (value < getRatingAttribute().getMinValue() || value > getRatingAttribute().getMaxValue()) {
            throw new DboConstraintException("value must lie in between " + getRatingAttribute().getMinValue()
                    + " and " + getRatingAttribute().getMaxValue());
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStakeholderAttributeVoteDbo that = (UserStakeholderAttributeVoteDbo) o;
        return Objects.equals(primaryKey, that.primaryKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primaryKey);
    }
}
