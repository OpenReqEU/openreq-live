package eu.openreq.dbo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "or_stakeholder_rating_attribute")
public class StakeholderRatingAttributeDbo {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable=false)
    private String name;

    @Column(name = "description", nullable=false)
    private String description;

    @Column(name = "icon_name", nullable=false)
    private String iconName;

    @Column(name = "min_value", nullable=false)
    private int minValue;

    @Column(name = "max_value", nullable=false)
    private int maxValue;

    @Column(name = "weight", nullable=false)
    private float weight;

    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name="project_id", nullable=false)
    private ProjectDbo project;

    @OneToMany(mappedBy="primaryKey.ratingAttribute", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    private Set<UserStakeholderAttributeVoteDbo> userStakeholderAttributeVotes;

    @OneToMany(mappedBy="primaryKey.ratingAttribute", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    private Set<AnonymousUserStakeholderAttributeVoteDbo> anonymousUserStakeholderAttributeVotes;

    @Column(name = "created_date", nullable=false)
    private Date createdDate;

    @Column(name = "last_updated_date", nullable=false)
    private Date lastUpdatedDate;

    public StakeholderRatingAttributeDbo() {}

    public StakeholderRatingAttributeDbo(String name, String description, String iconName, int minValue, int maxValue,
                                         float weight, ProjectDbo project) {
        this.name = name;
        this.description = description;
        this.iconName = iconName;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.weight = weight;
        this.project = project;
        this.userStakeholderAttributeVotes = new LinkedHashSet<>();
        this.anonymousUserStakeholderAttributeVotes = new LinkedHashSet<>();
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

    public void addUserRequirementAttributeVote(UserStakeholderAttributeVoteDbo userRequirementAttributeVote) {
        this.userStakeholderAttributeVotes.add(userRequirementAttributeVote);
    }

    public void addAnonymousUserRequirementAttributeVote(AnonymousUserStakeholderAttributeVoteDbo anonymousUserRequirementAttributeVote) {
        this.anonymousUserStakeholderAttributeVotes.add(anonymousUserRequirementAttributeVote);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StakeholderRatingAttributeDbo that = (StakeholderRatingAttributeDbo) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}
