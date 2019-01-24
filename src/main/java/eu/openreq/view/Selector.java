package eu.openreq.view;

import java.util.ArrayList;
import java.util.List;

public class Selector {
	
	private String value;
	
	private String decisionId;
	
	private List<String> selectionsList;
	
	private String nameOfParticipant;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDecisionId() {
		return decisionId;
	}

	public void setDecisionId(String decisionId) {
		this.decisionId = decisionId;
	}
	
	public List<String> getSelectionsList() {
		return selectionsList;
	}

	public void setSelectionsList(List<String> selectionsList) {
		this.selectionsList = selectionsList;
	}

	public void addSelectionsList(String selectionToAdd){
		if(this.selectionsList == null)
			this.selectionsList = new ArrayList<String>();
		this.selectionsList.add(selectionToAdd);
	}

	public String getNameOfParticipant() {
		return nameOfParticipant;
	}

	public void setNameOfParticipant(String nameOfParticipant) {
		this.nameOfParticipant = nameOfParticipant;
	}

}
