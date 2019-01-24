package eu.openreq.remote.dto;

import java.util.List;

public class RemoteDomainAssignmentStatelessRequestDto {

	private List<RemoteRequirementDto> requirements;
	
	private List<RemoteDomainDto> domains;
	
	public RemoteDomainAssignmentStatelessRequestDto() {}
	
	public RemoteDomainAssignmentStatelessRequestDto(List<RemoteRequirementDto> requirements,
			List<RemoteDomainDto> domains) {
		super();
		this.requirements = requirements;
		this.domains = domains;
	}

	public List<RemoteRequirementDto> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<RemoteRequirementDto> requirements) {
		this.requirements = requirements;
	}

	public List<RemoteDomainDto> getDomains() {
		return domains;
	}

	public void setDomains(List<RemoteDomainDto> domains) {
		this.domains = domains;
	}

	//TODO: Check if list<object> to string is correct!
	@Override
    public String toString() {
        return "RemoteDomainAssignmentStatelessRequestDto{requirements=" + requirements.toString() +
        		   ", domains='" + domains.toString() +
        		   "'}";
    }
}
