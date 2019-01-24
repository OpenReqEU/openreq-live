package eu.openreq.dbo;

import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "or_user", uniqueConstraints = @UniqueConstraint(columnNames = "mail_address"))
public class UserDbo {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "username", nullable=false)
	private String username;

	@Column(name = "first_name", nullable=false)
	private String firstName;

	@Column(name = "last_name", nullable=false)
	private String lastName;

	@Column(name = "mail_address", nullable=false)
	private String mailAddress;

	@Column(name = "group_number", nullable=true)
	private Long groupNumber;

	@JsonIgnore
	@Column(name = "password", nullable=false)
	private String password;

	@JsonIgnore
	@Column(name = "enabled", nullable=false)
	private boolean enabled;

	@JsonIgnore
	@Column(name = "confirmation_key", nullable=true)
	private String confirmationKey;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<RoleDbo> roles;

	@Column(name = "profile_image_path", nullable=true)
	private String profileImagePath;

	@Column(name = "score", nullable=false)
	private short score;

	@Column(name = "reset_password_key", nullable=true)
	private String resetPasswordKey;

    @Column(name = "last_reset_password_key_date", nullable=true)
    private Date lastResetPasswordKeyDate;

	@JsonIgnore
	@Column(name = "requirement_pair_start_index", columnDefinition = "int default 0", nullable=false)
	private long requirementPairStartIndex;

	@JsonIgnore
	@Column(name = "requirement_pair_end_index", columnDefinition = "int default 0", nullable=false)
	private long requirementPairEndIndex;

    @JsonIgnore
	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinTable(
        name = "or_user_skill",
        joinColumns = { @JoinColumn(name = "user_id") },
        inverseJoinColumns = { @JoinColumn(name = "skill_id") }
    )
	private Set<SkillDbo> userSkills;

	@JsonIgnore
	@OneToMany(mappedBy="primaryKey.stakeholder", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<RequirementStakeholderAssignment> requirementAssignments;

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
		@JoinTable(
			name = "or_user_issue",
			joinColumns = { @JoinColumn(name = "user_id") },
			inverseJoinColumns = { @JoinColumn(name = "issue_id") }
		)
	private Set<IssueDbo> userIssues;

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
		@JoinTable(
			name = "or_completed_dependency_analysis_project",
			joinColumns = { @JoinColumn(name = "user_id") },
			inverseJoinColumns = { @JoinColumn(name = "project_id") }
		)
	private Set<ProjectDbo> completedDependencyAnalysisProject;

    @JsonIgnore
	@OneToMany(mappedBy="user", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<UserRequirementCommentDbo> userComments;

	@JsonIgnore
	@OneToMany(mappedBy="primaryKey.user", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<UserRequirementAttributeVoteDbo> userRequirementAttributeVotes;

	@JsonIgnore
    @OneToMany(mappedBy="primaryKey.ratedStakeholder", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    private Set<UserStakeholderAttributeVoteDbo> ratedStakeholderAttributeVotes;

	@JsonIgnore
    @OneToMany(mappedBy="primaryKey.user", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<UserStakeholderAttributeVoteDbo> userStakeholderAttributeVotes;

	@JsonIgnore
    @OneToMany(mappedBy="primaryKey.user", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    private Set<AnonymousUserStakeholderAttributeVoteDbo> anonymousUserStakeholderAttributeVotes;

	@JsonIgnore
    @OneToMany(mappedBy="creatorUser", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<ProjectDbo> createdProjects;

	@JsonIgnore
    @OneToMany(mappedBy="user", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<ReleaseUpdateDbo> releaseUpdates;

	@JsonIgnore
    @OneToMany(mappedBy="user", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<RequirementUpdateDbo> requirementUpdates;

	@JsonIgnore
	@OneToMany(mappedBy="primaryKey.user", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<ProjectUserParticipationDbo> projectUserParticipations;

	@OneToMany(mappedBy="user", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<UserActivityDbo> activities;

	@OneToMany(mappedBy="userA", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<UserRatingConflictDbo> ratingConflictsFrom;

	@OneToMany(mappedBy="userB", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<UserRatingConflictDbo> ratingConflictsTo;

	@OneToMany(mappedBy="user", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<UserRequirementVoteActivityDbo> voteActivities;

	@JsonIgnore
	@Column(name = "created_date", nullable=false)
	private Date createdDate;

	@JsonIgnore
	@Column(name = "last_updated_date", nullable=true)
	private Date lastUpdatedDate;

	@JsonIgnore
	@Column(name = "last_login_date", nullable=true)
	private Date lastLoginDate;

	public UserDbo(String firstName, String lastName, String username, String mailAddress, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.mailAddress = mailAddress;
		this.groupNumber = null;
		this.password = password;
		this.enabled = false;
		this.confirmationKey = null;
		this.profileImagePath = null;
		this.score = 0;
		this.resetPasswordKey = null;
		this.userSkills = new LinkedHashSet<>();
		this.userIssues = new LinkedHashSet<>();
		this.userComments = new LinkedHashSet<>();
		this.completedDependencyAnalysisProject = new HashSet<>();
		this.userRequirementAttributeVotes = new LinkedHashSet<>();
		this.ratedStakeholderAttributeVotes = new LinkedHashSet<>();
		this.userStakeholderAttributeVotes = new LinkedHashSet<>();
		this.anonymousUserStakeholderAttributeVotes = new LinkedHashSet<>();
		this.createdProjects = new LinkedHashSet<>();
		this.releaseUpdates = new LinkedHashSet<>();
		this.requirementUpdates = new LinkedHashSet<>();
		this.projectUserParticipations = new LinkedHashSet<>();
		this.requirementAssignments = new LinkedHashSet<>();
		this.activities = new ArrayList<>();
		this.ratingConflictsFrom = new ArrayList<>();
		this.ratingConflictsTo = new ArrayList<>();
		this.voteActivities = new ArrayList<>();
		this.createdDate = null;
		this.lastUpdatedDate = null;
		this.lastLoginDate = null;
		this.requirementPairStartIndex = 0;
		this.requirementPairEndIndex = 0;
	}

	@PrePersist
	protected void onCreate() {
		this.createdDate = new Date();
		this.lastLoginDate = null;
	}

	@PreUpdate
	protected void onUpdate() {
		this.lastUpdatedDate = new Date();
	}

	public void addUserSkill(SkillDbo userSkill) {
		userSkills.add(userSkill);
	}

	public void addUserComment(UserRequirementCommentDbo userComment) {
		userComments.add(userComment);
	}

	public void addUserIssue(IssueDbo userIssue) {
		userIssues.add(userIssue);
	}

	public void addUserRequirementAttributeVote(UserRequirementAttributeVoteDbo userRequirementPropertyVote) {
		userRequirementAttributeVotes.add(userRequirementPropertyVote);
	}

    public void addRatedStakeholderAttributeVote(UserStakeholderAttributeVoteDbo ratedStakeholderAttributeVote) {
        ratedStakeholderAttributeVotes.add(ratedStakeholderAttributeVote);
    }

    public void addUserStakeholderAttributeVote(UserStakeholderAttributeVoteDbo userStakeholderAttributeVote) {
        userStakeholderAttributeVotes.add(userStakeholderAttributeVote);
    }

    public void addAnonymousUserStakeholderAttributeVote(AnonymousUserStakeholderAttributeVoteDbo anonymousUserStakeholderAttributeVote) {
        anonymousUserStakeholderAttributeVotes.add(anonymousUserStakeholderAttributeVote);
    }

	public void addCreatedProject(ProjectDbo project) {
		createdProjects.add(project);
	}

	public Set<ProjectUserParticipationDbo> getProjectUserParticipations() {
		return projectUserParticipations;
	}

	public void addProjectUserParticipation(ProjectUserParticipationDbo projectParticipation) {
		projectUserParticipations.add(projectParticipation);
	}

	public Set<RequirementStakeholderAssignment> getRequirementAssignments() {
		return requirementAssignments;
	}

	public void assignResponsibleRequirement(RequirementStakeholderAssignment requirementStakeholderAssignment) {
		requirementAssignments.add(requirementStakeholderAssignment);
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void updateLastLoginDate() {
		lastLoginDate = new Date();
	}

	public boolean isAdministrator() {
        return (roles.stream().filter(r -> r.getRole() == RoleDbo.Role.ROLE_ADMIN).count() > 0);
	}

	public boolean isRequirementsManager() {
        return (isAdministrator() || (roles.stream().filter(r -> r.getRole() == RoleDbo.Role.ROLE_REQUIREMENTS_MANAGER).count() > 0));
    }

	public void logLogin(String ipAddress, String sessionID, String forwardedIpAddress, String userAgentInfo) {
		UserActivityDbo userActivity = new UserActivityDbo();
		userActivity.setActionType(UserActivityDbo.ActionType.LOGIN);
		userActivity.setUser(this);
		userActivity.setTime(new Date());
		userActivity.setIpAddress(ipAddress);
		userActivity.setSessionID(sessionID);
		userActivity.setForwardedIpAddress(forwardedIpAddress);
		userActivity.setUserAgentInfo(userAgentInfo);
		activities.add(userActivity);
	}

	@Override
	public String toString() {
        return "User{id=" + id +
     		   ", username='" + username +
     		   "', firstname='" + firstName +
     		   "', lastname='" + lastName +
     		   "', email='" + mailAddress +
     		   "', score='" + score +
     		   "'}";
	}

	public boolean equals(Object obj) {
		if (! (obj instanceof  UserDbo))
			return false;
		return this.getId() == ((UserDbo) obj).getId();
	}

	public int hashCode() {
        return new Long(this.getId()).hashCode();
    }

}
