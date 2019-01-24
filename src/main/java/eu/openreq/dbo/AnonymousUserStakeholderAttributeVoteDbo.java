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
import lombok.Data;

@Data
@Entity
@Table(name = "or_anonymous_stakeholder_attribute_vote")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.user", joinColumns = @JoinColumn(name = "user_id")),
        @AssociationOverride(name = "primaryKey.ratedAnonymousStakeholder", joinColumns = @JoinColumn(name = "rated_anonymous_stakeholder_id")),
        @AssociationOverride(name = "primaryKey.requirement", joinColumns = @JoinColumn(name = "requirement_id")),
        @AssociationOverride(name = "primaryKey.ratingAttribute", joinColumns = @JoinColumn(name = "rating_attribute_id"))
})
public class AnonymousUserStakeholderAttributeVoteDbo {

    @EmbeddedId
    private AnonymousUserStakeholderAttributeVoteId primaryKey = new AnonymousUserStakeholderAttributeVoteId();

    @Column(name = "value", nullable=false)
    private int value;

    @Column(name = "created_date", nullable=false)
    private Date createdDate;

    @Column(name = "last_updated_date", nullable=false)
    private Date lastUpdatedDate;

    public AnonymousUserStakeholderAttributeVoteDbo() {}

    public AnonymousUserStakeholderAttributeVoteDbo(int value, RequirementDbo requirement, StakeholderRatingAttributeDbo ratingAttribute, AnonymousUserDbo ratedAnonymousStakeholder, UserDbo user) throws DboConstraintException {
        this.setRequirement(requirement);
        this.setRatedAnonymousStakeholder(ratedAnonymousStakeholder);
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

    public AnonymousUserDbo getRatedAnonymousStakeholder() {
        return getPrimaryKey().getRatedAnonymousStakeholder();
    }

    public void setRatedAnonymousStakeholder(AnonymousUserDbo ratedAnonymousStakeholder) {
        getPrimaryKey().setRatedAnonymousStakeholder(ratedAnonymousStakeholder);
    }

    @Transient
    public long getRatedAnonymousStakeholderId() {
        return getRatedAnonymousStakeholder().getId();
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

}
