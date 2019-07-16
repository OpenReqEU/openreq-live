package eu.openreq.dbo;

import lombok.Data;
import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import static eu.openreq.dbo.UserRequirementAttributeVoteDbo.convertBarsToHours;

@Data
@Entity
@Table(name = "or_requirement")
public class RequirementDbo {

	public enum Status { NEW, INDISCUSSION, PLANNED, ONGOING, COMPLETED, REJECTED, RECOMMENDED }

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "project_specific_requirement_id", columnDefinition = "int default 0", nullable=false)
	private long projectSpecificRequirementId;

	@Column(name = "title", length=1000, nullable=false)
	private String title;

	@Column(name = "description", columnDefinition="text", nullable=false)
	private String description;

	@Column(name = "import_id", nullable=true)
	private String importId;

	@ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="project_id", nullable=false)
	private ProjectDbo project;

	@ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="release_id", nullable=true)
	private ReleaseDbo release;

	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinTable(
        name = "or_requirement_skill",
        joinColumns = { @JoinColumn(name = "requirement_id") },
        inverseJoinColumns = { @JoinColumn(name = "skill_id") }
    )
	private Set<SkillDbo> requirementSkills;

    @OneToMany(mappedBy="primaryKey.requirement", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    private Set<RequirementStakeholderAssignment> userStakeholderAssignments;

    @OneToMany(mappedBy="primaryKey.requirement", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<RequirementAnonymousStakeholderAssignment> anonymousUserStakeholderAssignments;

	@OneToMany(mappedBy="primaryKey.requirement", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<UserRequirementVoteDbo> userRequirementVotes;

	@OneToMany(mappedBy="primaryKey.requirement", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<UserRequirementAttributeVoteDbo> userRequirementAttributeVotes;

	@OneToMany(mappedBy="primaryKey.requirement", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<AnonymousUserRequirementAttributeVoteDbo> anonymousUserRequirementAttributeVotes;

	@OneToMany(mappedBy="primaryKey.requirement", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<UserStakeholderAttributeVoteDbo> userStakeholderAttributeVotes;

	@OneToMany(mappedBy="primaryKey.requirement", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<BotUserStakeholderAttributeVoteDbo> botUserStakeholderAttributeVotes;

    @OneToMany(mappedBy="primaryKey.requirement", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    private Set<AnonymousUserStakeholderAttributeVoteDbo> anonymousUserStakeholderAttributeVotes;

	@OneToMany(mappedBy="primaryKey.requirement", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<DelegateUserRequirementVoteDbo> userDelegationVotes;

	@OneToMany(mappedBy="primaryKey.requirement", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<HideStakeholderAssignmentDbo> hideStakeholderAssignments;

	@OneToMany(mappedBy="requirement", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<UserRequirementCommentDbo> userComments;

	@OneToMany(mappedBy="primaryKey.sourceRequirement", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<DependencyDbo> sourceDependencies;

	@OneToMany(mappedBy="primaryKey.targetRequirement", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<DependencyDbo> targetDependencies;

	@OneToMany(mappedBy="primaryKey.sourceRequirement", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<PotentialDependencyDbo> potentialSourceDependencies;

	@OneToMany(mappedBy="primaryKey.targetRequirement", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<PotentialDependencyDbo> potentialTargetDependencies;

    @OneToMany(mappedBy="requirement", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    private List<RequirementUpdateDbo> requirementUpdates;

    @OneToMany(mappedBy="requirement", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    private List<UserRatingConflictDbo> requirementConflicts;

    @Enumerated(EnumType.ORDINAL)
	@Column(name = "status", nullable=false)
	private Status status;

	@Column(name = "visible", nullable=false)
	private boolean visible;

	@Column(name = "stakeholder_recommendations_fetched", columnDefinition = "boolean default false", nullable=false)
	private boolean stakeholderRecommendationsFetched;

	@Column(name = "social_popularity", nullable=true)
	private Float socialPopularity;

	@Column(name = "created_date", nullable=false)
	private Date createdDate;

	@Column(name = "last_updated_date", nullable=false)
	private Date lastUpdatedDate;

	public RequirementDbo() {
		this.projectSpecificRequirementId = 0;
		this.title = null;
		this.description = null;
		this.importId = null;
		this.project = null;
		this.release = null;
		this.requirementSkills = new LinkedHashSet<>();
		this.userStakeholderAssignments = new LinkedHashSet<>();
		this.anonymousUserStakeholderAssignments = new LinkedHashSet<>();
		this.userRequirementAttributeVotes = new LinkedHashSet<>();
		this.anonymousUserRequirementAttributeVotes = new LinkedHashSet<>();
        this.userStakeholderAttributeVotes = new LinkedHashSet<>();
        this.botUserStakeholderAttributeVotes = new LinkedHashSet<>();
        this.anonymousUserStakeholderAttributeVotes = new LinkedHashSet<>();
        this.userDelegationVotes = new LinkedHashSet<>();
        this.hideStakeholderAssignments = new LinkedHashSet<>();
        this.userComments = new LinkedHashSet<>();
        this.sourceDependencies = new LinkedHashSet<>();
        this.targetDependencies = new LinkedHashSet<>();
        this.potentialSourceDependencies = new LinkedHashSet<>();
        this.potentialTargetDependencies = new LinkedHashSet<>();
        this.requirementUpdates = new ArrayList<>();
        this.requirementConflicts = new ArrayList<>();
		this.status = Status.NEW;
		this.visible = true;
		this.stakeholderRecommendationsFetched = false;
		this.socialPopularity = null;
	}

	public RequirementDbo(Long projectSpecificRequirementId, String title, String description, ProjectDbo project) {
		this.projectSpecificRequirementId = projectSpecificRequirementId;
		this.title = title;
		this.description = description;
		this.importId = null;
		this.project = project;
		this.release = null;
        this.requirementSkills = new LinkedHashSet<>();
        this.userStakeholderAssignments = new LinkedHashSet<>();
        this.anonymousUserStakeholderAssignments = new LinkedHashSet<>();
        this.userRequirementAttributeVotes = new LinkedHashSet<>();
        this.anonymousUserRequirementAttributeVotes = new LinkedHashSet<>();
        this.userStakeholderAttributeVotes = new LinkedHashSet<>();
		this.botUserStakeholderAttributeVotes = new LinkedHashSet<>();
        this.anonymousUserStakeholderAttributeVotes = new LinkedHashSet<>();
		this.userDelegationVotes = new LinkedHashSet<>();
		this.hideStakeholderAssignments = new LinkedHashSet<>();
		this.userComments = new LinkedHashSet<>();
		this.sourceDependencies = new LinkedHashSet<>();
		this.targetDependencies = new LinkedHashSet<>();
		this.potentialSourceDependencies = new LinkedHashSet<>();
		this.potentialTargetDependencies = new LinkedHashSet<>();
		this.requirementUpdates = new ArrayList<>();
		this.requirementConflicts = new ArrayList<>();
		this.status = Status.NEW;
        this.visible = true;
		this.stakeholderRecommendationsFetched = false;
		this.socialPopularity = null;
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

	public void addRequirementSkill(SkillDbo requirementSkill) {
		requirementSkills.add(requirementSkill);
	}

	public void assignUserStakeholder(RequirementStakeholderAssignment stakeholderAssignment) {
        userStakeholderAssignments.add(stakeholderAssignment);
	}

    public void assignAnonymousUser(RequirementAnonymousStakeholderAssignment anonymousStakeholderAssignment) {
        anonymousUserStakeholderAssignments.add(anonymousStakeholderAssignment);
    }

	public void addUserRequirementAttributeVote(UserRequirementAttributeVoteDbo userRequirementAttributeVote) {
		userRequirementAttributeVotes.add(userRequirementAttributeVote);
	}

	public void addAnonymousUserRequirementAttributeVote(AnonymousUserRequirementAttributeVoteDbo anonymousUserRequirementAttributeVote) {
		anonymousUserRequirementAttributeVotes.add(anonymousUserRequirementAttributeVote);
	}

    public void addUserStakeholderAttributeVote(UserStakeholderAttributeVoteDbo userStakeholderAttributeVote) {
        userStakeholderAttributeVotes.add(userStakeholderAttributeVote);
    }

    public void addAnonymousUserStakeholderAttributeVote(AnonymousUserStakeholderAttributeVoteDbo anonymousUserStakeholderAttributeVote) {
        anonymousUserStakeholderAttributeVotes.add(anonymousUserStakeholderAttributeVote);
    }

	public void addUserComment(UserRequirementCommentDbo userComment) {
		userComments.add(userComment);
	}

	public void logRequirementUpdate(RequirementUpdateDbo.ActionType actionType,
									 RequirementDbo previousRequirementState, UserDbo user) {
	    RequirementUpdateDbo requirementUpdate = new RequirementUpdateDbo();
	    requirementUpdate.setRequirement(this);
	    requirementUpdate.setTime(new Date());
	    requirementUpdate.setActionType(actionType);
	    requirementUpdate.setUser(user);
	    requirementUpdate.setCurrentStateInfo(toString());
	    if (previousRequirementState != null) {
	        requirementUpdate.setPreviousStateInfo(previousRequirementState.toString());
        }
		requirementUpdates.add(requirementUpdate);
	}

	public float getUtilityValue() {
		ProjectSettingsDbo.EvaluationMode evaluationMode = this.getProject().getProjectSettings().getEvaluationMode();
		boolean isPrivateProject = this.getProject().isVisibilityPrivate();
		if (evaluationMode == ProjectSettingsDbo.EvaluationMode.BASIC) {
		    float sum = 0.0f;
		    int numberOfVotes = userRequirementVotes.size();
			for (UserRequirementVoteDbo vote : userRequirementVotes) {
				sum += vote.getValue();
			}
			return (sum > 0.0f) ? (sum / numberOfVotes) : 0.0f;
		} else if (evaluationMode == ProjectSettingsDbo.EvaluationMode.NORMAL) {
			if (isPrivateProject) {
				for (UserRequirementAttributeVoteDbo vote : this.getUserRequirementAttributeVotes()) {
                    // TODO: implement
				}
			} else {
                // TODO: implement
            }
		} else if (evaluationMode == ProjectSettingsDbo.EvaluationMode.ADVANCED) {
		    // TODO: implement
		}
		return 0.0f;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequirementDbo that = (RequirementDbo) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

	@Override
	public String toString() {
		return "Requirement{id=" + id +
                ", projectSpecificRequirementId=" + projectSpecificRequirementId +
				", title='" + title + "'" +
				", description='" + description + "'" +
				", projectID='" + project.getId() + "'" +
				", release=" + ((release != null) ? release.getId() : 0) +
				", visible=" + visible +
				", status=" + status +
				"'}";
	}

	public RequirementDbo clone() {
		RequirementDbo clonedRequirement = new RequirementDbo();
		clonedRequirement.setId(this.id);
		clonedRequirement.setProjectSpecificRequirementId(this.projectSpecificRequirementId);
		clonedRequirement.setTitle(this.title);
		clonedRequirement.setDescription(this.description);
		clonedRequirement.setProject(this.project);
		clonedRequirement.setRelease(this.release);
		clonedRequirement.setRequirementSkills(this.requirementSkills);
		clonedRequirement.setUserStakeholderAssignments(this.userStakeholderAssignments);
		clonedRequirement.setAnonymousUserStakeholderAssignments(this.anonymousUserStakeholderAssignments);
		clonedRequirement.setUserRequirementVotes(this.userRequirementVotes);
		clonedRequirement.setUserRequirementAttributeVotes(this.userRequirementAttributeVotes);
		clonedRequirement.setAnonymousUserRequirementAttributeVotes(this.anonymousUserRequirementAttributeVotes);
		clonedRequirement.setUserStakeholderAttributeVotes(this.userStakeholderAttributeVotes);
		clonedRequirement.setAnonymousUserStakeholderAttributeVotes(this.anonymousUserStakeholderAttributeVotes);
		clonedRequirement.setUserDelegationVotes(this.userDelegationVotes);
		clonedRequirement.setUserComments(this.userComments);
		clonedRequirement.setSourceDependencies(this.sourceDependencies);
		clonedRequirement.setTargetDependencies(this.targetDependencies);
		clonedRequirement.setRequirementUpdates(this.requirementUpdates);
		clonedRequirement.setRequirementConflicts(this.requirementConflicts);
		clonedRequirement.setStatus(this.status);
		clonedRequirement.setVisible(this.visible);
		clonedRequirement.setSocialPopularity(this.socialPopularity);
		clonedRequirement.setCreatedDate(this.createdDate);
		clonedRequirement.setLastUpdatedDate(this.lastUpdatedDate);
		return clonedRequirement;
	}

	public int getAvgEffort() {
		double hours = 0;
		int noValidVotes = 0;
		for (UserRequirementAttributeVoteDbo vote : getUserRequirementAttributeVotes()) {
			if(vote.getRatingAttribute().getName().toLowerCase().equals("effort")
					&& vote.getValue() > 0) {
				hours +=  convertBarsToHours(vote.getValue());
				noValidVotes++;
			}
		}
		return noValidVotes > 0 ? (int)Math.round(hours / noValidVotes) : 0;
	}

}
