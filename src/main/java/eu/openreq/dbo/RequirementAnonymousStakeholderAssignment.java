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
import lombok.Data;

@Data
@Entity
@Table(name = "or_requirement_responsible_anonymous_user")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.anonymousStakeholder", joinColumns = @JoinColumn(name = "anonymous_user_id")),
        @AssociationOverride(name = "primaryKey.requirement", joinColumns = @JoinColumn(name = "requirement_id"))
})
public class RequirementAnonymousStakeholderAssignment {

    @EmbeddedId
    private RequirementAnonymousStakeholderAssignmentId primaryKey = new RequirementAnonymousStakeholderAssignmentId();

    @Column(name = "value", nullable=false)
    private boolean accepted;

    @Column(name = "created_date", nullable=false)
    private Date createdDate;

    @Column(name = "last_updated_date", nullable=false)
    private Date lastUpdatedDate;

    public RequirementAnonymousStakeholderAssignment() {}

    public RequirementAnonymousStakeholderAssignment(RequirementDbo requirement, AnonymousUserDbo anonymousStakeholder) {
        this.setRequirement(requirement);
        this.setAnonymousStakeholder(anonymousStakeholder);
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

    public AnonymousUserDbo getAnonymousStakeholder() {
        return getPrimaryKey().getAnonymousStakeholder();
    }

    public void setAnonymousStakeholder(AnonymousUserDbo anonymousStakeholder) {
        getPrimaryKey().setAnonymousStakeholder(anonymousStakeholder);
    }

    @Transient
    public long getAnonymousStakeholderId() {
        return getAnonymousStakeholder().getId();
    }

}
