package eu.openreq.dbo;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "or_hide_anonymous_stakeholder_assignment")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.requirement", joinColumns = @JoinColumn(name = "requirement_id")),
        @AssociationOverride(name = "primaryKey.user", joinColumns = @JoinColumn(name = "user_id")),
        @AssociationOverride(name = "primaryKey.hiddenAnonymousStakeholder", joinColumns = @JoinColumn(name = "hidden_anonymous_stakeholder_user_id"))
})
public class HideAnonymousStakeholderAssignmentDbo {

    @EmbeddedId
    private HideAnonymousStakeholderAssignmentId primaryKey = new HideAnonymousStakeholderAssignmentId();

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "last_updated_date", nullable = true)
    private Date lastUpdatedDate;

    public HideAnonymousStakeholderAssignmentDbo() {}

    public HideAnonymousStakeholderAssignmentDbo(RequirementDbo requirement, UserDbo user, AnonymousUserDbo hiddenAnonymousStakeholder) {
        this.setRequirement(requirement);
        this.setUser(user);
        this.setHiddenAnonymousStakeholder(hiddenAnonymousStakeholder);
        this.createdDate = null;
        this.lastUpdatedDate = null;
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdatedDate = new Date();
    }

    @Transient
    public RequirementDbo getRequirement() {
        return primaryKey.getRequirement();
    }

    public void setRequirement(RequirementDbo requirement) {
        primaryKey.setRequirement(requirement);
    }

    @Transient
    public UserDbo getUser() {
        return primaryKey.getUser();
    }

    public void setUser(UserDbo user) {
        primaryKey.setUser(user);
    }

    @Transient
    public AnonymousUserDbo getHiddenAnonymousStakeholder() {
        return primaryKey.getHiddenAnonymousStakeholder();
    }

    public void setHiddenAnonymousStakeholder(AnonymousUserDbo hiddenAnonymousStakeholder) {
        primaryKey.setHiddenAnonymousStakeholder(hiddenAnonymousStakeholder);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HideAnonymousStakeholderAssignmentDbo that = (HideAnonymousStakeholderAssignmentDbo) o;
        return Objects.equals(primaryKey, that.primaryKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primaryKey);
    }

}
