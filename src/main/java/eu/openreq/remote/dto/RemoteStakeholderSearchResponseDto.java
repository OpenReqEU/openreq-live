package eu.openreq.remote.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteStakeholderSearchResponseDto {

	private Boolean result;

	private List<Map<String, Object>> foundUsers;

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public List<Map<String, Object>> getFoundUsers() {
		return foundUsers;
	}

	public void setFoundUsers(List<Map<String, Object>> foundUsers) {
		this.foundUsers = foundUsers;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> output = new HashMap<>();
		output.put("result", result);
		output.put("foundUsers", foundUsers);
		return output;
	}

}
