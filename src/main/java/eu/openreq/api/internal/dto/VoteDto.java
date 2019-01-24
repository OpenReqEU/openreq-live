package eu.openreq.api.internal.dto;

import java.util.ArrayList;
import java.util.List;

public class VoteDto {

	private long requirementID;

	private List<VoteDimensionDto> attributeVotes;

	private boolean anonymousVoting;

	private String anonymousStakeholderName;

	public VoteDto() {
		this.requirementID = 0;
		this.attributeVotes = new ArrayList<>();
		this.anonymousVoting = true;
		this.anonymousStakeholderName = null;
	}

	public long getRequirementID() {
		return requirementID;
	}

	public void setRequirementID(long requirementID) {
		this.requirementID = requirementID;
	}

	public List<VoteDimensionDto> getAttributeVotes() {
		return attributeVotes;
	}

	public void setAttributeVotes(List<VoteDimensionDto> attributeVotes) {
		this.attributeVotes = attributeVotes;
	}

	public boolean isAnonymousVoting() {
		return anonymousVoting;
	}

	public void setAnonymousVoting(boolean anonymousVoting) {
		this.anonymousVoting = anonymousVoting;
	}

	public String getAnonymousStakeholderName() {
		return anonymousStakeholderName;
	}

	public void setAnonymousStakeholderName(String anonymousStakeholderName) {
		this.anonymousStakeholderName = anonymousStakeholderName;
	}

}
