package eu.openreq.dbo;

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
@Table(name = "or_project_guest_user_participation")
@AssociationOverrides({
	@AssociationOverride(name = "primaryKey.project", joinColumns = @JoinColumn(name = "project_id"))
})
public class ProjectGuestUserParticipationDbo {

	@EmbeddedId
	private ProjectGuestUserParticipationId primaryKey = new ProjectGuestUserParticipationId();

	@Column(name = "accepted", nullable=false)
    private boolean accepted;

    /*
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "creator_user", nullable=false)
	private UserDbo creatorUser;
	*/

	@Column(name = "invited_date", nullable=false)
	private Date invitedDate;

	public ProjectGuestUserParticipationDbo() {
		this.accepted = false;
	}

	public ProjectGuestUserParticipationDbo(ProjectDbo project, String emailAddress) {
		this.accepted = false;
		this.setProject(project);
		this.setEmailAddress(emailAddress);
	}

    public ProjectGuestUserParticipationId getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(ProjectGuestUserParticipationId primaryKey) {
        this.primaryKey = primaryKey;
    }

	@PrePersist
	protected void onCreate() {
		this.invitedDate = new Date();
	}

	@Transient
    public String getEmailAddress() {
        return getPrimaryKey().getEmailAddress();
    }

	public void setEmailAddress(String emailAddress) {
		getPrimaryKey().setEmailAddress(emailAddress);
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
