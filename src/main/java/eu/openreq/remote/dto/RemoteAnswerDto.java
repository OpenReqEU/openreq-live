package eu.openreq.remote.dto;

public class RemoteAnswerDto {

	public enum ComplianceType { COMPLIANT, COMPLIANT_W_COMMENT, NON_COMPLIANT}
	
	public enum ApproachType {EXISTING_PRODUCT, NON_TECHN, OPER_RULES, ENGIN_CONF, DEVELOP, EXT_SUP, BID_DOC};
	
	private long id;
	
	private ComplianceType compliance;
	
	private String complianceComment;
	
	private ApproachType approach;
	
	private String approachComment;
	
	private RemoteStakeholderDto stakeholder;
	
	private long requirementDomainAssignmentId;

	public RemoteAnswerDto() {}

	public RemoteAnswerDto(ComplianceType compliance, String complianceComment, ApproachType approach,
			String approachComment, RemoteStakeholderDto stakeholder, long requirementDomainAssignmentId) {
		super();
		this.compliance = compliance;
		this.complianceComment = complianceComment;
		this.approach = approach;
		this.approachComment = approachComment;
		this.stakeholder = stakeholder;
		this.requirementDomainAssignmentId = requirementDomainAssignmentId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ComplianceType getCompliance() {
		return compliance;
	}

	public void setCompliance(ComplianceType compliance) {
		this.compliance = compliance;
	}

	public String getComplianceComment() {
		return complianceComment;
	}

	public void setComplianceComment(String complianceComment) {
		this.complianceComment = complianceComment;
	}

	public ApproachType getApproach() {
		return approach;
	}

	public void setApproach(ApproachType approach) {
		this.approach = approach;
	}

	public String getApproachComment() {
		return approachComment;
	}

	public void setApproachComment(String approachComment) {
		this.approachComment = approachComment;
	}

	public RemoteStakeholderDto getStakeholder() {
		return stakeholder;
	}

	public void setStakeholder(RemoteStakeholderDto stakeholder) {
		this.stakeholder = stakeholder;
	}

	public long getRequirementDomainAssignmentId() {
		return requirementDomainAssignmentId;
	}

	public void setRequirementDomainAssignmentId(long requirementDomainAssignmentId) {
		this.requirementDomainAssignmentId = requirementDomainAssignmentId;
	}

	@Override
    public String toString() {
        return "RemoteAnswerDto{id=" + id +
        		   ", compliance='" + compliance +
        		   "', complianceComment='" + complianceComment +
        		   "', approach='" + approach +
        		   "', approachComment='" + approachComment +
        		   "', stakeholder='" + stakeholder.toString() +
        		   "', requirementDomainAssignmentId='" + requirementDomainAssignmentId +
        		   "'}";
    }

	public boolean equals(Object obj) {
		if (! (obj instanceof RemoteAnswerDto))
			return false;
		return this.getId() == ((RemoteAnswerDto) obj).getId();
	}
}
