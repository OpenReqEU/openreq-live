package eu.openreq.remote.response.dto.helsinki;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "AnalysisVersion",
        "AnalysisVersion_msg",
        "Consistent",
        "Consistent_msg",
        "RelationshipsInconsistent",
        "RelationshipsInconsistent_msg",
        "Releases",
        "Diagnosis",
        "Diagnosis_msg"
})
public class Response {

    @JsonProperty("AnalysisVersion")
    private String analysisVersion;
    @JsonProperty("AnalysisVersion_msg")
    private String analysisVersionMsg;
    @JsonProperty("Consistent")
    private Boolean consistent;
    @JsonProperty("Consistent_msg")
    private String consistentMsg;
    @JsonProperty("RelationshipsInconsistent")
    private List<RelationshipsInconsistent> relationshipsInconsistent = null;
    @JsonProperty("RelationshipsInconsistent_msg")
    private String relationshipsInconsistentMsg;
    @JsonProperty("Releases")
    private List<Release> releases = null;
    @JsonProperty("Diagnosis")
    private Diagnosis diagnosis;
    @JsonProperty("Diagnosis_msg")
    private String diagnosisMsg;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("AnalysisVersion")
    public String getAnalysisVersion() {
        return analysisVersion;
    }

    @JsonProperty("AnalysisVersion")
    public void setAnalysisVersion(String analysisVersion) {
        this.analysisVersion = analysisVersion;
    }

    @JsonProperty("AnalysisVersion_msg")
    public String getAnalysisVersionMsg() {
        return analysisVersionMsg;
    }

    @JsonProperty("AnalysisVersion_msg")
    public void setAnalysisVersionMsg(String analysisVersionMsg) {
        this.analysisVersionMsg = analysisVersionMsg;
    }

    @JsonProperty("Consistent")
    public Boolean isConsistent() {
        return consistent;
    }

    @JsonProperty("Consistent")
    public void setConsistent(Boolean consistent) {
        this.consistent = consistent;
    }

    @JsonProperty("Consistent_msg")
    public String getConsistentMsg() {
        return consistentMsg;
    }

    @JsonProperty("Consistent_msg")
    public void setConsistentMsg(String consistentMsg) {
        this.consistentMsg = consistentMsg;
    }

    @JsonProperty("RelationshipsInconsistent")
    public List<RelationshipsInconsistent> getRelationshipsInconsistent() {
        return relationshipsInconsistent;
    }

    @JsonProperty("RelationshipsInconsistent")
    public void setRelationshipsInconsistent(List<RelationshipsInconsistent> relationshipsInconsistent) {
        this.relationshipsInconsistent = relationshipsInconsistent;
    }

    @JsonProperty("RelationshipsInconsistent_msg")
    public String getRelationshipsInconsistentMsg() {
        return relationshipsInconsistentMsg;
    }

    @JsonProperty("RelationshipsInconsistent_msg")
    public void setRelationshipsInconsistentMsg(String relationshipsInconsistentMsg) {
        this.relationshipsInconsistentMsg = relationshipsInconsistentMsg;
    }

    @JsonProperty("Releases")
    public List<Release> getReleases() {
        return releases;
    }

    @JsonProperty("Releases")
    public void setReleases(List<Release> releases) {
        this.releases = releases;
    }

    @JsonProperty("Diagnosis")
    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    @JsonProperty("Diagnosis")
    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    @JsonProperty("Diagnosis_msg")
    public String getDiagnosisMsg() {
        return diagnosisMsg;
    }

    @JsonProperty("Diagnosis_msg")
    public void setDiagnosisMsg(String diagnosisMsg) {
        this.diagnosisMsg = diagnosisMsg;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}