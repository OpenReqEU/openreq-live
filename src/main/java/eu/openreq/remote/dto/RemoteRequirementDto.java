  package eu.openreq.remote.dto;

import java.util.List;
import java.util.Objects;

  public class RemoteRequirementDto {

	public enum ReqType { DEF, PROSE, NOT_CLASSIFIED}
	
	private long id;
	
	private String toolId;
	
	private String text;
	
	private String heading;
	
	private String type;

	private long documentId;

	private List<RemoteDomainDto> domains;
	
	private double score;
	
	public RemoteRequirementDto() {}

	public RemoteRequirementDto(long id, String toolId, String text, String heading, String type, long documentId,
			List<RemoteDomainDto> domains, double score) {
		super();
		this.id = id;
		this.toolId = toolId;
		this.text = text;
		this.heading = heading;
		this.type = type;
		this.documentId = documentId;
		this.domains = domains;
		this.score = score;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToolId() {
		return toolId;
	}

	public void setToolId(String toolId) {
		this.toolId = toolId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<RemoteDomainDto> getDomains() {
		return domains;
	}

	public void setDomains(List<RemoteDomainDto> domains) {
		this.domains = domains;
	}

	public long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(long documentId) {
		this.documentId = documentId;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	@Override
    public String toString() {
        return "RemoteRequirementDto{id=" + id +
        		   ", toolId='" + toolId +
        		   "', text='" + text +
        		   "', heading='" + heading +
        		   "', type='" + type +
        		   "', domains='" + domains.toString() +
        		   "'}";
    }

    @Override
	public boolean equals(Object obj) {
		if (! (obj instanceof RemoteRequirementDto))
			return false;
		return this.getId() == ((RemoteRequirementDto) obj).getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
