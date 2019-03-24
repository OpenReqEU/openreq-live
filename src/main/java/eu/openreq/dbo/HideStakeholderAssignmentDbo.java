package eu.openreq.dbo;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "or_hide_stakeholder_assignment")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.requirement", joinColumns = @JoinColumn(name = "requirement_id")),
        @AssociationOverride(name = "primaryKey.user", joinColumns = @JoinColumn(name = "user_id")),
        @AssociationOverride(name = "primaryKey.hiddenStakeholder", joinColumns = @JoinColumn(name = "hidden_stakeholder_user_id"))
})
public class HideStakeholderAssignmentDbo {

    @EmbeddedId
    private HideStakeholderAssignmentId primaryKey = new HideStakeholderAssignmentId();

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "last_updated_date", nullable = true)
    private Date lastUpdatedDate;

    public HideStakeholderAssignmentDbo() {}

    public HideStakeholderAssignmentDbo(RequirementDbo requirement, UserDbo user, UserDbo hiddenStakeholder) {
        this.setRequirement(requirement);
        this.setUser(user);
        this.setHiddenStakeholder(hiddenStakeholder);
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
    public UserDbo getHiddenStakeholder() {
        return primaryKey.getHiddenStakeholder();
    }

    public void setHiddenStakeholder(UserDbo hiddenStakeholder) {
        primaryKey.setHiddenStakeholder(hiddenStakeholder);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HideStakeholderAssignmentDbo that = (HideStakeholderAssignmentDbo) o;
        return Objects.equals(primaryKey, that.primaryKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primaryKey);
    }

}
