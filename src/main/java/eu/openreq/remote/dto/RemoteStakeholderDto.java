package eu.openreq.remote.dto;

import java.util.Objects;

public class RemoteStakeholderDto {

	private long id;

	private String name;
	
	private String email;
	
	private String department;
	
	public RemoteStakeholderDto() {}

	public RemoteStakeholderDto(String name, String email, String department) {
		super();
		this.name = name;
		this.email = email;
		this.department = department;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
    public String toString() {
        return "RemoteStakeholderDto{id=" + id +
        		   ", name='" + name +
        		   "', email='" + email +
        		   "', department='" + department +
        		   "'}";
    }

    @Override
	public boolean equals(Object obj) {
		if (! (obj instanceof RemoteStakeholderDto))
			return false;
		return this.getId() == ((RemoteStakeholderDto) obj).getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
