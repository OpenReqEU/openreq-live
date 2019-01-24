package eu.openreq.dbo;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class ProjectGuestUserParticipationId implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade = CascadeType.ALL)
	private ProjectDbo project;

	private String emailAddress;

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public ProjectDbo getProject() {
		return project;
	}

	public void setProject(ProjectDbo project) {
		this.project = project;
	}
}
