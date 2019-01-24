package eu.openreq.remote.dto;

public class RemoteRequirementDomainAssignmentDto {

	private long id;

	private long requirementId;
	
	private long projectDefinitionId;
	
	public RemoteRequirementDomainAssignmentDto() {}

	public RemoteRequirementDomainAssignmentDto(long requirementId, long projectDefinitionId) {
		super();
		this.requirementId = requirementId;
		this.projectDefinitionId = projectDefinitionId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(long requirementId) {
		this.requirementId = requirementId;
	}

	public long getProjectDefinitionId() {
		return projectDefinitionId;
	}

	public void setProjectDefinitionId(long projectDefinitionId) {
		this.projectDefinitionId = projectDefinitionId;
	}

	@Override
    public String toString() {
        return "RemoteRequirementDomainAssignmentDto{id=" + id +
        		   ", requirementId='" + requirementId +
        		   "', projectDefinitionId='" + projectDefinitionId +
        		   "'}";
    }

	public boolean equals(Object obj) {
		if (! (obj instanceof RemoteRequirementDomainAssignmentDto))
			return false;
		return this.getId() == ((RemoteRequirementDomainAssignmentDto) obj).getId();
	}
}
