package eu.openreq.view;

import java.util.List;

import eu.openreq.remote.dto.RemoteRequirementDto;

public class ImportedRequirementsBean {

	private List<RemoteRequirementDto> checkedRequirements;
	
	private List<RemoteRequirementDto> uncheckedRequirements;

	public List<RemoteRequirementDto> getCheckedRequirements() {
		return checkedRequirements;
	}

	public void setCheckedRequirements(List<RemoteRequirementDto> checkedRequirements) {
		this.checkedRequirements = checkedRequirements;
	}

	public List<RemoteRequirementDto> getUncheckedRequirements() {
		return uncheckedRequirements;
	}

	public void setUncheckedRequirements(List<RemoteRequirementDto> uncheckedRequirements) {
		this.uncheckedRequirements = uncheckedRequirements;
	}

	
	
	
	
}
