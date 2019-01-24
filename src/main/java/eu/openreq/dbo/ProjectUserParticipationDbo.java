package eu.openreq.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "or_project_user_participation")
@AssociationOverrides({
	@AssociationOverride(name = "primaryKey.user", joinColumns = @JoinColumn(name = "user_id")),
	@AssociationOverride(name = "primaryKey.project", joinColumns = @JoinColumn(name = "project_id"))
})
public class ProjectUserParticipationDbo {

    @JsonIgnore
	@EmbeddedId
	private ProjectUserParticipationId primaryKey = new ProjectUserParticipationId();

	@JsonIgnore
	@Column(name = "accepted", nullable=false)
    private boolean accepted;

	@Column(name = "invited_date", nullable=false)
	private Date invitedDate;

	public ProjectUserParticipationDbo() {
		this.accepted = false;
	}

	public ProjectUserParticipationDbo(ProjectDbo project, UserDbo user) {
		this.accepted = false;
		this.setProject(project);
		this.setUser(user);
	}

    public ProjectUserParticipationId getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(ProjectUserParticipationId primaryKey) {
        this.primaryKey = primaryKey;
    }

	@PrePersist
	protected void onCreate() {
		this.invitedDate = new Date();
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

	@Transient
	public ProjectDbo getProject() {
		return getPrimaryKey().getProject();
	}

	@Transient
	public long getProjectId() {
		return getProject().getId();
	}

	public void setProject(ProjectDbo project) {
		getPrimaryKey().setProject(project);
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public Date getInvitedDate() {
		return invitedDate;
	}

	public void setInvitedDate(Date invitedDate) {
		this.invitedDate = invitedDate;
	}

}
