package eu.openreq.dbo;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "or_skill")
public class SkillDbo {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "keyword", nullable=false)
	private String keyword;

	@ManyToMany(mappedBy = "userSkills", cascade = { CascadeType.ALL })
	private Set<UserDbo> users;

	@ManyToMany(mappedBy = "requirementSkills", cascade = { CascadeType.ALL })
	private Set<RequirementDbo> requirements;

	public SkillDbo() {}

	public SkillDbo(String keyword) {
		this.keyword = keyword;
		this.users = new LinkedHashSet<>();
		this.requirements = new LinkedHashSet<>();
	}

	public long getId() {
		return id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Set<UserDbo> getUsers() {
		return users;
	}

	public void addUser(UserDbo user) {
		users.add(user);
	}

	public Set<RequirementDbo> getRequirements() {
		return requirements;
	}

	public void addRequirement(RequirementDbo requirement) {
		requirements.add(requirement);
	}

	public boolean equals(Object obj) {
		if (! (obj instanceof SkillDbo))
			return false;
		return getId() == ((SkillDbo) obj).getId();
	}

	public String toString() {
		return keyword;
	}
}
