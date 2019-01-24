package eu.openreq.remote.dto;

import java.util.List;

public class RemoteDomainAssignmentStatelessResponseDto {

	private RemoteRequirementDto requirements;
	
	private List<RemoteDomainCandidateDto> domainCandidates;
	
	public RemoteDomainAssignmentStatelessResponseDto() {}
	
	public RemoteDomainAssignmentStatelessResponseDto(RemoteRequirementDto requirements,
			List<RemoteDomainCandidateDto> domainCandidates) {
		super();
		this.requirements = requirements;
		this.domainCandidates = domainCandidates;
	}
	
	public RemoteRequirementDto getRequirements() {
		return requirements;
	}

	public void setRequirements(RemoteRequirementDto requirements) {
		this.requirements = requirements;
	}

	public List<RemoteDomainCandidateDto> getDomainCandidates() {
		return domainCandidates;
	}

	public void setDomainCandidates(List<RemoteDomainCandidateDto> domainCandidates) {
		this.domainCandidates = domainCandidates;
	}

	@Override
    public String toString() {
        return "RemoteDomainAssignmentStatelessResponseDto{requirements=" + requirements.toString() +
        		   ", domains='" + domainCandidates.toString() +
        		   "'}";
    }
}
