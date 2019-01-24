package eu.openreq.dbo;

import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.openreq.dbo.ReleaseDbo.Status;
import lombok.Data;

@Data
@Entity
@Table(name = "or_project")
public class ProjectDbo {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", nullable=false)
	private long id = 0;

	@Column(name = "unique_key", unique=true, nullable=false)
	private String uniqueKey;

	@Column(name = "name", nullable=false)
	private String name = null;

	@Column(name = "description", columnDefinition="text", nullable=true)
	private String description = null;

	@Column(name = "start_date", nullable=false)
	private Date startDate = null;

	@Column(name = "end_date", nullable=false)
	private Date endDate = null;

	@OneToMany(mappedBy="project", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<ReleaseDbo> releases;

	@JsonIgnore
	@OneToMany(mappedBy="project", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<RequirementDbo> requirements;

	@JsonIgnore
	@OneToMany(mappedBy="project", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<RatingAttributeDbo> ratingAttributes;

	@JsonIgnore
	@OneToMany(mappedBy="project", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<StakeholderRatingAttributeDbo> stakeholderRatingAttributes;

	@JsonIgnore
	@Column(name = "last_project_specific_requirement_id", columnDefinition = "int default 0", nullable=false)
	private long lastProjectSpecificRequirementId;

	@JsonIgnore
	@Column(name = "last_recommended_requirement_pair_list_student_index", columnDefinition = "int default 0", nullable=false)
	private long lastRecommendedRequirementPairListStudentIndex;

	@Column(name = "image_path", nullable=false)
	private String imagePath = null;

	@Column(name = "visible", nullable=false)
	private boolean visible;

	@Column(name = "visibilityPrivate", nullable=false)
	private Boolean visibilityPrivate;

	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "creator_user", nullable=true)
	private UserDbo creatorUser;

	@Column(name = "creator_user_ip_address", nullable=true)
	private String creatorUserIpAddress;

	@Column(name = "creator_user_forwarded_ip_address", nullable=true)
	private String creatorUserForwardedIpAddress;

	@Column(name = "creator_user_session_id", nullable=true)
	private String creatorUserSessionID;

	@Column(name = "creator_user_user_agent", columnDefinition="text", nullable=true)
	private String creatorUserAgent;

	@ManyToMany(mappedBy = "completedDependencyAnalysisProject", cascade = { CascadeType.ALL })
	private Set<UserDbo> usersWhoCompletedDependencyAnalysisProject;

	@OneToMany(mappedBy="primaryKey.project", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<ProjectUserParticipationDbo> userParticipations;

	@JsonIgnore
	@OneToMany(mappedBy="primaryKey.project", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<ProjectGuestUserParticipationDbo> guestUserParticipations;

	@OneToOne(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private ProjectSettingsDbo projectSettings;

	@Column(name = "created_date", nullable=false)
	private Date createdDate = null;

	@Column(name = "last_updated_date", nullable=false)
	private Date lastUpdatedDate = null;

	public ProjectDbo() {
		this.releases = new LinkedHashSet<>();
		this.requirements = new LinkedHashSet<>();
		this.ratingAttributes = new ArrayList<>();
		this.stakeholderRatingAttributes = new LinkedHashSet<>();
		this.lastProjectSpecificRequirementId = 0;
		this.lastRecommendedRequirementPairListStudentIndex = 0;
		this.usersWhoCompletedDependencyAnalysisProject = new HashSet<>();
		this.userParticipations = new LinkedHashSet<>();
		this.guestUserParticipations = new LinkedHashSet<>();
	}

	public ProjectDbo(String uniqueKey, String name, String description, Date startDate, Date endDate,
			          String imagePath, Boolean visibilityPrivate, UserDbo creatorUser) {
		this.uniqueKey = uniqueKey;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.releases = new LinkedHashSet<>();
		this.requirements = new LinkedHashSet<>();
		this.ratingAttributes = new ArrayList<>();
		this.stakeholderRatingAttributes = new LinkedHashSet<>();
		this.lastProjectSpecificRequirementId = 0;
		this.lastRecommendedRequirementPairListStudentIndex = 0;
		this.imagePath = imagePath;
		this.visible = true;
		this.visibilityPrivate = visibilityPrivate;
		this.creatorUser = creatorUser;
		this.creatorUserIpAddress = null;
		this.creatorUserForwardedIpAddress = null;
		this.creatorUserAgent = null;
		this.creatorUserSessionID = null;
        this.usersWhoCompletedDependencyAnalysisProject = new HashSet<>();
		this.userParticipations = new LinkedHashSet<>();
		this.guestUserParticipations = new LinkedHashSet<>();
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

	public void incrementLastProjectSpecificRequirementId() {
		++this.lastProjectSpecificRequirementId;
	}

	public void addRelease(ReleaseDbo release) {
		releases.add(release);
	}

	@JsonIgnore
	public List<ReleaseDbo> getSortedReleases() {
		List<ReleaseDbo> sortedReleases = new ArrayList<>(releases);
		Collections.sort(sortedReleases, (o1, o2) -> (o1.getId() < o2.getId()) ? -1 : ((o1.getId() == o2.getId()) ? 0 : 1));
		return sortedReleases;
	}

	public int getNumberOfFinishedReleases() {
		return releases.stream().filter(release -> release.getStatus() == Status.COMPLETED).collect(Collectors.toList()).size();
	}

	public void addRequirement(RequirementDbo requirement) {
		requirements.add(requirement);
	}

	public void addRatingAttribute(RatingAttributeDbo ratingAttribute) {
		ratingAttributes.add(ratingAttribute);
	}

    public void addStakeholderRatingAttribute(StakeholderRatingAttributeDbo stakeholderRatingAttribute) {
        stakeholderRatingAttributes.add(stakeholderRatingAttribute);
    }

	public void addUserParticipation(ProjectUserParticipationDbo userParticipation) {
		userParticipations.add(userParticipation);
	}

	public void removeUserParticipation(ProjectUserParticipationDbo userParticipation) {
		userParticipations.remove(userParticipation);
	}

	public void addGuestUserParticipation(ProjectGuestUserParticipationDbo guestUserParticipation) {
		guestUserParticipations.add(guestUserParticipation);
	}

	public void removeGuestUserParticipation(ProjectGuestUserParticipationDbo guestUserParticipation) {
		guestUserParticipations.remove(guestUserParticipation);
	}

	public boolean isCreator(UserDbo user) {
		return ((this.creatorUser != null) && (user != null) && (this.creatorUser.getId() == user.getId()));
	}

	public boolean isParticipant(UserDbo user) {
		return ((user != null) && (userParticipations.stream().filter(p -> p.getUserId() == user.getId()).count() == 1));
	}

	public boolean hasCreatorOrParticipantRights(UserDbo user) {
		return (!getVisibilityPrivate() || ((user != null) && user.isAdministrator()) || isCreator(user) || isParticipant(user));
	}

	@Override
    public String toString() {
        return "Project{id=" + id +
        		   ", name='" + name +
        		   "', startDate='" + startDate +
        		   "', endDate='" + endDate +
        		   "'}";
    }

	public boolean equals(Object obj) {
		if (! (obj instanceof ProjectDbo))
			return false;
		return this.getId() == ((ProjectDbo) obj).getId();
	}

}
