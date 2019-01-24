package eu.openreq.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "or_release")
public class ReleaseDbo {

	public enum Status { NEW, PLANNED, COMPLETED, REJECTED }

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "name", nullable=false)
	private String name;
	
	@Column(name = "description", columnDefinition="text", nullable=false)
	private String description;

	@Column(name = "maximumCapacity", nullable=false)
	private int maximumCapacity;

	@Column(name = "end_date", nullable=false)
	private Date endDate;

	@Column(name = "visible", nullable=false)
	private boolean visible;

	@JsonIgnore
	@ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="project_id", nullable=false)
	private ProjectDbo project;

	@JsonIgnore
	@OneToMany(mappedBy="release", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<RequirementDbo> requirements;

	@OneToMany(mappedBy="release", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<ReleaseUpdateDbo> releaseUpdates;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "status", nullable=false)
	private Status status;

	@Column(name = "created_date", nullable=false)
	private Date createdDate;

	@Column(name = "last_updated_date", nullable=false)
	private Date lastUpdatedDate;

	public ReleaseDbo() {
		this.name = null;
		this.description = null;
		this.maximumCapacity = 0;
		this.endDate = null;
		this.visible = true;
		this.project = null;
		this.requirements = new LinkedHashSet<>();
        this.releaseUpdates = new LinkedHashSet<>();
		this.status = Status.NEW;
	}

	public ReleaseDbo(String name, String description, Date endDate, int maximumCapacity, ProjectDbo project) {
		this.name = name;
		this.description = description;
		this.maximumCapacity = maximumCapacity;
		this.endDate = endDate;
		this.visible = true;
		this.project = project;
		this.requirements = new LinkedHashSet<>();
        this.releaseUpdates = new LinkedHashSet<>();
		this.status = Status.NEW;
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

	@JsonIgnore
	public List<RequirementDbo> getSortedRequirements() {
		List<RequirementDbo> sortedRequirements = new ArrayList<>(requirements);
		Collections.sort(sortedRequirements, new Comparator<RequirementDbo>() {
			public int compare(RequirementDbo o1, RequirementDbo o2) {
				return (o1.getId() < o2.getId()) ? -1 : ((o1.getId() == o2.getId()) ? 0 : 1);
			}
		});
		return sortedRequirements;
	}

	public void addRequirement(RequirementDbo requirement) {
		requirements.add(requirement);
	}

    public void logReleaseUpdate(ReleaseUpdateDbo.ActionType actionType, UserDbo user) {
        ReleaseUpdateDbo releaseUpdate = new ReleaseUpdateDbo();
        releaseUpdate.setRelease(this);
        releaseUpdate.setTime(new Date());
        releaseUpdate.setActionType(actionType);
        releaseUpdate.setUser(user);
        releaseUpdate.setStateInfo(toString());
        releaseUpdates.add(releaseUpdate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReleaseDbo that = (ReleaseDbo) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

	@Override
	public String toString() {
		return "Release{id=" + id +
				", name='" + name + "'" +
				", description='" + description + "'" +
				", status=" + status +
				"'}";
	}

}
