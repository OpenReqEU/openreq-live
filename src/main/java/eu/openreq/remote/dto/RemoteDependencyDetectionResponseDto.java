package eu.openreq.remote.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class RemoteDependencyDetectionResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private String consistent;

	@XmlElement
	private String explanation;

	public RemoteDependencyDetectionResponseDto() {}

	public String getConsistent() {
		return consistent;
	}

	public void setConsistent(String consistent) {
		this.consistent = consistent;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	@Override
	public String toString() {
		return "RemoteDependencyDetectionRequestResponseDto{consistent=" + consistent + ", " + explanation + "}";
	}

}
