package eu.openreq.dbo;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "or_issue")
public class IssueDbo {

	public enum Status { UNRESOLVED, RESOLVED }

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "name", nullable=false)
	private String name;

	@Column(name = "description", nullable=false)
	private String description;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "status", nullable=false)
	private Status status;

	@ManyToMany(mappedBy = "userIssues", cascade = { CascadeType.ALL })
	private Set<UserDbo> users;

	@Column(name = "created_date", nullable=false)
	private Date createdDate;

	@Column(name = "last_updated_date", nullable=false)
	private Date lastUpdatedDate;

	public IssueDbo(String name, String description, Status status) {
		this.name = name;
		this.description = description;
		this.users = new LinkedHashSet<>();
		this.status = status;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<UserDbo> getUsers() {
		return users;
	}

	public void addUser(UserDbo user) {
		this.users.add(user);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
}
