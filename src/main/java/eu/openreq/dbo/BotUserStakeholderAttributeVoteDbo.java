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
@Table(name = "or_bot_stakeholder_attribute_vote")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.ratedStakeholder", joinColumns = @JoinColumn(name = "rated_user_id")),
        @AssociationOverride(name = "primaryKey.requirement", joinColumns = @JoinColumn(name = "requirement_id")),
        @AssociationOverride(name = "primaryKey.ratingAttribute", joinColumns = @JoinColumn(name = "rating_attribute_id"))
})
public class BotUserStakeholderAttributeVoteDbo {

    @EmbeddedId
    private BotUserStakeholderAttributeVoteId primaryKey = new BotUserStakeholderAttributeVoteId();

    @Column(name = "value", nullable=false)
    private int value;

    @Column(name = "created_date", nullable=false)
    private Date createdDate;

    @Column(name = "last_updated_date", nullable=false)
    private Date lastUpdatedDate;

    public BotUserStakeholderAttributeVoteDbo() {}

    public BotUserStakeholderAttributeVoteDbo(int value, RequirementDbo requirement, StakeholderRatingAttributeDbo ratingAttribute, UserDbo ratedStakeholder) throws DboConstraintException {
        this.setRequirement(requirement);
        this.setRatedStakeholder(ratedStakeholder);
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

    public UserDbo getRatedStakeholder() {
        return getPrimaryKey().getRatedStakeholder();
    }

    public void setRatedStakeholder(UserDbo ratedStakeholder) {
        getPrimaryKey().setRatedStakeholder(ratedStakeholder);
    }

    @Transient
    public long getRatedStakeholderId() {
        return getRatedStakeholder().getId();
    }

    public void setValue(int value) throws DboConstraintException {
        // sanity check
        if (value < getRatingAttribute().getMinValue() || value > getRatingAttribute().getMaxValue()) {
            throw new DboConstraintException("value must lie in between " + getRatingAttribute().getMinValue()
                    + " and " + getRatingAttribute().getMaxValue() + " but is " + value);
        }
        this.value = value;
    }

}
