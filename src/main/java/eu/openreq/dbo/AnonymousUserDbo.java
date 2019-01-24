package eu.openreq.dbo;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "or_anonymous_user", uniqueConstraints = @UniqueConstraint(columnNames = "full_name"))
public class AnonymousUserDbo {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "full_name", nullable=false)
    private String fullName;

    @OneToMany(mappedBy="primaryKey.anonymousStakeholder", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    private Set<RequirementAnonymousStakeholderAssignment> requirementAssignments;

    @OneToMany(mappedBy="primaryKey.ratedAnonymousStakeholder", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    private Set<AnonymousUserStakeholderAttributeVoteDbo> ratedStakeholderAttributeVotes;

    @Column(name = "created_date", nullable=false)
    private Date createdDate;

    @Column(name = "last_updated_date", nullable=true)
    private Date lastUpdatedDate;

    public AnonymousUserDbo() {}

    public AnonymousUserDbo(String fullName) {
        this.fullName = fullName;
        this.requirementAssignments = new LinkedHashSet<>();
        this.ratedStakeholderAttributeVotes = new LinkedHashSet<>();
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public Set<RequirementAnonymousStakeholderAssignment> getRequirementAssignments() {
        return requirementAssignments;
    }

    public void assignResponsibleRequirement(RequirementAnonymousStakeholderAssignment requirementAssignment) {
        requirementAssignments.add(requirementAssignment);
    }

    public Set<AnonymousUserStakeholderAttributeVoteDbo> getRatedStakeholderAttributeVotes() {
        return ratedStakeholderAttributeVotes;
    }

    public void setRatedStakeholderAttributeVotes(Set<AnonymousUserStakeholderAttributeVoteDbo> ratedStakeholderAttributeVotes) {
        this.ratedStakeholderAttributeVotes = ratedStakeholderAttributeVotes;
    }

    @Override
    public String toString() {
        return "AnonymousUser{id=" + id +
                ", fullname='" + fullName +
                "'}";
    }

    public boolean equals(Object obj) {
        if (! (obj instanceof AnonymousUserDbo))
            return false;
        return this.getId() == ((AnonymousUserDbo) obj).getId();
    }

    public int hashCode() {
        return new Long(this.getId()).hashCode();
    }

}
