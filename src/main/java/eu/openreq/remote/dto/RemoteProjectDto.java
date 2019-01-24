package eu.openreq.remote.dto;

public class RemoteProjectDto {

	private long id;

	private String name;

	private int creationTimestamp;
	
	private boolean archived;
	
	private RemoteProjectDefinitionDto projectDefinitions;
	
	public RemoteProjectDto() {}


	public RemoteProjectDto(String name, int creationTimeStamp, boolean archived,
			RemoteProjectDefinitionDto projectDefinitions) {
		super();
		this.name = name;
		this.creationTimestamp = creationTimeStamp;
		this.archived = archived;
		this.projectDefinitions = projectDefinitions;
	}

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getCreationTimeStamp() {
		return creationTimestamp;
	}


	public void setCreationTimeStamp(int creationTimeStamp) {
		this.creationTimestamp = creationTimeStamp;
	}


	public boolean isArchived() {
		return archived;
	}


	public void setArchived(boolean archived) {
		this.archived = archived;
	}


	public RemoteProjectDefinitionDto getProjectDefinitions() {
		return projectDefinitions;
	}


	public void setProjectDefinitions(RemoteProjectDefinitionDto projectDefinitions) {
		this.projectDefinitions = projectDefinitions;
	}


	
	@Override
    public String toString() {
        return "RemoteProjectDto{id=" + id +
        		   ", name='" + name +
        		   "', creationTimeStamp='" + creationTimestamp +
        		   "', archived='" + archived +
        		   "', projectDefinitions='" + projectDefinitions.toString() +
        		   "'}";
    }

	public boolean equals(Object obj) {
		if (! (obj instanceof RemoteProjectDto))
			return false;
		return this.getId() == ((RemoteProjectDto) obj).getId();
	}
}
