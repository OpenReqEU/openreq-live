package eu.openreq.dbo;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

@Data
@Entity
@Table(name = "or_rating_attribute")
public class RatingAttributeDbo {

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

	//---------------------------------------------------------
	// FIXME: name="interval" does not work with Hibernate!!!!
	//---------------------------------------------------------
//	@Column(name = "interval", nullable=false)
//	private int interval;

	@Column(name = "weight", nullable=false)
	private float weight;

    @ColumnDefault("0")
	@Column(name = "reverse", nullable=false)
	private boolean reverse;

	@ManyToOne(cascade = {CascadeType.DETACH})
	@JoinColumn(name="project_id", nullable=false)
	private ProjectDbo project;

	@OneToMany(mappedBy="primaryKey.ratingAttribute", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<UserRequirementAttributeVoteDbo> userRequirementAttributeVotes;

	@OneToMany(mappedBy="primaryKey.ratingAttribute", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<AnonymousUserRequirementAttributeVoteDbo> anonymousUserRequirementAttributeVotes;

	@ManyToMany(mappedBy = "votedAttributes", cascade = { CascadeType.DETACH })
	private Set<UserRequirementCommentDbo> comments;

	@Column(name = "created_date", nullable=false)
	private Date createdDate;

	@Column(name = "last_updated_date", nullable=false)
	private Date lastUpdatedDate;

	public RatingAttributeDbo() {}

	public RatingAttributeDbo(String name, String description, String iconName, int minValue, int maxValue,
                              int interval, float weight, boolean isReverse, ProjectDbo project) {
		this.name = name;
		this.description = description;
		this.iconName = iconName;
		this.minValue = minValue;
		this.maxValue = maxValue;
//		this.interval = interval;
		this.weight = weight;
		this.reverse = isReverse;
		this.project = project;
		this.userRequirementAttributeVotes = new LinkedHashSet<>();
		this.anonymousUserRequirementAttributeVotes = new LinkedHashSet<>();
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

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RatingAttributeDbo that = (RatingAttributeDbo) o;
		return id == that.id;
	}

	@Override
	public String toString() {
		return "RatingAttributeDbo{id=" + id + ", name=" + name + "}";
	}

}
