package eu.openreq.remote.dto;

import java.util.Objects;

public class RemoteRequirementStructureDto {

	private long id;

	private long requirementId;
	
	private long parentId;
	
	private long documentId;
	
	private long level;
	
	public RemoteRequirementStructureDto() {}

	public RemoteRequirementStructureDto(long requirementId, long parentId, long documentId, long level) {
		super();
		this.requirementId = requirementId;
		this.parentId = parentId;
		this.documentId = documentId;
		this.level = level;
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

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(long documentId) {
		this.documentId = documentId;
	}

	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	@Override
    public String toString() {
        return "RemoteRequirementStructureDto{id=" + id +
        		   ", requirementId='" + requirementId +
        		   "', parentId='" + parentId +
        		   "', documentId='" + documentId +
        		   "', level='" + level +
        		   "'}";
    }

    @Override
	public boolean equals(Object obj) {
		if (! (obj instanceof RemoteRequirementStructureDto))
			return false;
		return this.getId() == ((RemoteRequirementStructureDto) obj).getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
