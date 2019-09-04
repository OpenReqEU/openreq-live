package eu.openreq.remote.dto;

import java.util.Objects;

public class RemoteDomainCandidateDto {

	private double confidence;
	
	private RemoteDomainDto domain; 
	
	private long domainId;

	public RemoteDomainCandidateDto() {}

	public RemoteDomainCandidateDto(double confidence, RemoteDomainDto domain) {
		super();
		this.confidence = confidence;
		this.domain = domain;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	public RemoteDomainDto getDomain() {
		return domain;
	}

	public void setDomain(RemoteDomainDto domain) {
		this.domain = domain;
	}

	public long getDomainId() {
		return domainId;
	}

	public void setDomainId(long domainId) {
		this.domainId = domainId;
	}

	@Override
    public String toString() {
        return "RemoteDomainCandidateDto{domainId=" + domainId +
        		   ", confidence='" + confidence +
        		   "', domain='" + domain.toString() +
        		   "'}";
    }

    @Override
	public boolean equals(Object obj) {
		if (! (obj instanceof RemoteDomainCandidateDto))
			return false;
		return this.getDomainId() == ((RemoteDomainCandidateDto) obj).getDomainId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(confidence, domain, domainId);
	}

}
