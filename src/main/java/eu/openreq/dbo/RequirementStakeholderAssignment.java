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
import lombok.Data;

@Data
@Entity
@Table(name = "or_requirement_responsible_user")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.anonymousStakeholder", joinColumns = @JoinColumn(name = "user_id")),
        @AssociationOverride(name = "primaryKey.requirement", joinColumns = @JoinColumn(name = "requirement_id"))
})
public class RequirementStakeholderAssignment {

    @EmbeddedId
    private RequirementStakeholderAssignmentId primaryKey = new RequirementStakeholderAssignmentId();

    @Column(name = "value", nullable=false)
    private boolean accepted;

    @Column(name = "created_date", nullable=false)
    private Date createdDate;

    @Column(name = "last_updated_date", nullable=false)
    private Date lastUpdatedDate;

    public RequirementStakeholderAssignment() {}

    public RequirementStakeholderAssignment(RequirementDbo requirement, UserDbo stakeholder) {
        this.setRequirement(requirement);
        this.setStakeholder(stakeholder);
        this.accepted = false;
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

    public UserDbo getStakeholder() {
        return getPrimaryKey().getStakeholder();
    }

    public void setStakeholder(UserDbo stakeholder) {
        getPrimaryKey().setStakeholder(stakeholder);
    }

    @Transient
    public long getStakeholderId() {
        return getStakeholder().getId();
    }

}
