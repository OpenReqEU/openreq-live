package eu.openreq.remote.dto;

public class RemoteDomainDto {

	private long id;
	
	private long requirementId;

	private String name;
	
	private String description;
	
	private RemoteAnswerDto answer;
	
	private RemoteStakeholderDto stakeholder;
	
	private RemoteProjectDefinitionDto projectDefinition;
	
	public RemoteDomainDto() {}

	public RemoteDomainDto(long requirementId, String name, String description, RemoteAnswerDto answer,
			RemoteStakeholderDto stakeholder, RemoteProjectDefinitionDto projectDefinition) {
		super();
		this.requirementId = requirementId;
		this.name = name;
		this.description = description;
		this.answer = answer;
		this.stakeholder = stakeholder;
		this.projectDefinition = projectDefinition;
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

	public RemoteAnswerDto getAnswer() {
		return answer;
	}

	public void setAnswer(RemoteAnswerDto answer) {
		this.answer = answer;
	}

	public RemoteStakeholderDto getStakeholder() {
		return stakeholder;
	}

	public void setStakeholder(RemoteStakeholderDto stakeholder) {
		this.stakeholder = stakeholder;
	}

	public RemoteProjectDefinitionDto getProjectDefinition() {
		return projectDefinition;
	}

	public void setProjectDefinition(RemoteProjectDefinitionDto projectDefinition) {
		this.projectDefinition = projectDefinition;
	}

	@Override
    public String toString() {
        return "RemoteProjectDto{id=" + id +
        		   ", requirementId='" + requirementId +   
        		   ", name='" + name +
        		   "', description='" + description +
        		   "', answer='" + answer.toString() +
        		   "', stakeholder='" + stakeholder.toString() +
        		   "', projectDefinition='" + projectDefinition.toString() +
        		   "'}";
    }

	public boolean equals(Object obj) {
		if (! (obj instanceof RemoteDomainDto))
			return false;
		return this.getId() == ((RemoteDomainDto) obj).getId();
	}
}
