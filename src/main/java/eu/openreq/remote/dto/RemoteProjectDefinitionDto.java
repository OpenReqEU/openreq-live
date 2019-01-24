package eu.openreq.remote.dto;

public class RemoteProjectDefinitionDto {

	private long id;
	
	private long projectId;
	
	private RemoteDomainDto domain;
	
	private String domainDescription;
	
	private RemoteStakeholderDto stakeholder;

	public RemoteProjectDefinitionDto() {}

	public RemoteProjectDefinitionDto(long projectId, RemoteDomainDto domain, String domainDescription,
			RemoteStakeholderDto stakeholder) {
		super();
		this.projectId = projectId;
		this.domain = domain;
		this.domainDescription = domainDescription;
		this.stakeholder = stakeholder;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public RemoteDomainDto getDomain() {
		return domain;
	}

	public void setDomain(RemoteDomainDto domain) {
		this.domain = domain;
	}

	public String getDomainDescription() {
		return domainDescription;
	}

	public void setDomainDescription(String domainDescription) {
		this.domainDescription = domainDescription;
	}

	public RemoteStakeholderDto getStakeholder() {
		return stakeholder;
	}

	public void setStakeholder(RemoteStakeholderDto stakeholder) {
		this.stakeholder = stakeholder;
	}

	@Override
    public String toString() {
        return "RemoteProjectDefinitionDto{id=" + id +
        		   ", projectId='" + projectId +
        		   "', domain='" + domain.toString() +
        		   "', domainDescription='" + domainDescription +
        		   "', stakeholder='" + stakeholder.toString() +
        		   "'}";
    }

	public boolean equals(Object obj) {
		if (! (obj instanceof RemoteProjectDefinitionDto))
			return false;
		return this.getId() == ((RemoteProjectDefinitionDto) obj).getId();
	}
}
