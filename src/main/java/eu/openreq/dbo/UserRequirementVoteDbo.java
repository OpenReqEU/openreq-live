package eu.openreq.dbo;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import eu.openreq.exception.DboConstraintException;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "or_requirement_vote")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.user", joinColumns = @JoinColumn(name = "user_id")),
        @AssociationOverride(name = "primaryKey.requirement", joinColumns = @JoinColumn(name = "requirement_id"))
})
public class UserRequirementVoteDbo {

    @EmbeddedId
    private UserRequirementVoteId primaryKey = new UserRequirementVoteId();

    @Column(name = "value", nullable=false)
    private int value;

    @Column(name = "created_date", nullable=false)
    private Date createdDate;

    @Column(name = "last_updated_date", nullable=false)
    private Date lastUpdatedDate;

    public UserRequirementVoteDbo(int value, RequirementDbo requirement, UserDbo user) {
        this.setRequirement(requirement);
        this.setUser(user);
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

    public UserRequirementVoteId getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(UserRequirementVoteId primaryKey) {
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

    @Override
    public String toString() {
        return "UserRequirementVoteDbo{userID=" + getUserId() +
                ", userName='" + getUser().getFirstName() + " " + getUser().getLastName() + "'" +
                ", requirementID=" + getRequirementId() +
                ", requirementTitle=" + getRequirement().getTitle() +
                ", value=" + value +
                "}";
    }

}
