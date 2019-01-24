package eu.openreq.dbo;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "or_delegate_user_requirement_vote")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.requirement", joinColumns = @JoinColumn(name = "requirement_id")),
        @AssociationOverride(name = "primaryKey.user", joinColumns = @JoinColumn(name = "user_id")),
        @AssociationOverride(name = "primaryKey.delegatedUser", joinColumns = @JoinColumn(name = "delegated_user_id"))
})
public class DelegateUserRequirementVoteDbo {

    @EmbeddedId
    private DelegateUserRequirementVoteId primaryKey = new DelegateUserRequirementVoteId();

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "last_updated_date", nullable = true)
    private Date lastUpdatedDate;

    public DelegateUserRequirementVoteDbo() {}

    public DelegateUserRequirementVoteDbo(RequirementDbo requirement, UserDbo user, UserDbo delegatedUser) {
        this.setRequirement(requirement);
        this.setUser(user);
        this.setDelegatedUser(delegatedUser);
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
    public UserDbo getDelegatedUser() {
        return primaryKey.getDelegatedUser();
    }

    public void setDelegatedUser(UserDbo delegatedUser) {
        primaryKey.setDelegatedUser(delegatedUser);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DelegateUserRequirementVoteDbo that = (DelegateUserRequirementVoteDbo) o;
        return Objects.equals(primaryKey, that.primaryKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primaryKey);
    }
}
