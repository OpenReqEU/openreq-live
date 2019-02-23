package eu.openreq.dbo;

import lombok.Data;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.*;

@Data
@Entity
@Table(name = "or_issue")
public class IssueDbo {

	public enum Status { UNRESOLVED, RESOLVED }

	public enum Priority { CRITICAL, HIGH, NORMAL, LOW }

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "name", nullable=false)
	private String name;

	@Column(name = "description", nullable=false)
	private String description;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "status", nullable=false)
	private Status status;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "priority", nullable=false)
	private Priority priority;

	@ManyToMany(mappedBy = "userIssues", cascade = { CascadeType.ALL })
	private Set<UserDbo> users;

	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="project_id", nullable=false)
	private ProjectDbo project;

	@Column(name = "visible", nullable=false)
	private boolean visible;

	@Column(name = "created_date", nullable=false)
	private Date createdDate;

	@Column(name = "last_updated_date", nullable=false)
	private Date lastUpdatedDate;

	public IssueDbo() {
		this.users = new LinkedHashSet<>();
	}

	public IssueDbo(String name, String description, Status status, Priority priority, ProjectDbo project) {
		this.name = name;
		this.description = description;
		this.users = new LinkedHashSet<>();
		this.status = status;
		this.priority = priority;
		this.project = project;
		this.visible = true;
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

	public void addUser(UserDbo user) {
		this.users.add(user);
	}

}
