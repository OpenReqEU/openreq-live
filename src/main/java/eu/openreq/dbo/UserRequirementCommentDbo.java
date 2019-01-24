package eu.openreq.dbo;

import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

@Data
@Entity
@Table(name = "or_user_requirement_comment")
public class UserRequirementCommentDbo {

	public enum Sentiment { PRO, CON, NEUTRAL }

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "title", columnDefinition="text", nullable=false)
	// TODO: limit string length/size everywhere!!! not only here!!!
	private String title;

	@Column(name = "text", columnDefinition="text", nullable=false)
	// TODO: limit
	private String text;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "sentiment", nullable=true)
    private UserRequirementCommentDbo.Sentiment sentiment;

    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name="requirement_id", nullable=false)
	private RequirementDbo requirement;

	@ManyToOne(cascade = {CascadeType.DETACH})
	@JoinColumn(name="user_id", nullable=false)
	private UserDbo user;

    @ManyToMany(cascade = { CascadeType.DETACH }, fetch = FetchType.LAZY)
    @JoinTable(
            name = "or_voted_attribute",
            joinColumns = { @JoinColumn(name = "comment_id") },
            inverseJoinColumns = { @JoinColumn(name = "attribute_id") }
    )
    private Set<RatingAttributeDbo> votedAttributes;

	@Column(name = "created_date", nullable=false)
	private Date createdDate;

	@Column(name = "last_updated_date", nullable=false)
	private Date lastUpdatedDate;

	public UserRequirementCommentDbo() {}

	public UserRequirementCommentDbo(String title, String text, Sentiment sentiment, RequirementDbo requirement, UserDbo user) {
		this.title = title;
		this.text = text;
		this.sentiment = sentiment;
		this.requirement = requirement;
		this.user = user;
		this.votedAttributes = new HashSet<>();
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserRequirementCommentDbo that = (UserRequirementCommentDbo) o;
		return id == that.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "UserRequirementCommentDbo{userID=" + user.getId() +
				", userName='" + getUser().getFirstName() + " " + getUser().getLastName() + "'" +
				", requirementID=" + requirement.getId() +
				", requirementTitle=" + requirement.getTitle() +
				", votedAttributes={" + votedAttributes + "}" +
				", sentiment=" + sentiment +
				"}";
	}

}
