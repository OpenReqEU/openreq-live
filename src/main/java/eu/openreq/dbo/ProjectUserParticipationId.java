package eu.openreq.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class ProjectUserParticipationId implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	private ProjectDbo project;

	@ManyToOne(cascade = CascadeType.ALL)
	private UserDbo user;

	public UserDbo getUser() {
		return user;
	}

	public void setUser(UserDbo user) {
		this.user = user;
	}

	public ProjectDbo getProject() {
		return project;
	}

	public void setProject(ProjectDbo project) {
		this.project = project;
	}
}
