package eu.openreq.remote.dto;

import java.util.Objects;

public class RemoteDocumentDto {

	private long id;

	private String name;

	private int uploadTimestamp;
	
	public RemoteDocumentDto() {}


	public RemoteDocumentDto(String name, int uploadTimestamp) {
		super();
		this.name = name;
		this.uploadTimestamp = uploadTimestamp;
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


	public int getUploadTimestamp() {
		return uploadTimestamp;
	}


	public void setUploadTimestamp(int uploadTimestamp) {
		this.uploadTimestamp = uploadTimestamp;
	}
	
	@Override
    public String toString() {
        return "RemoteDocumentDto{id=" + id +
        		   ", name='" + name +
        		   "', uploadTimestamp='" + uploadTimestamp +
        		   "'}";
    }

    @Override
	public boolean equals(Object obj) {
		if (! (obj instanceof RemoteDocumentDto))
			return false;
		return this.getId() == ((RemoteDocumentDto) obj).getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
