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
        "DiagnosisRequirements",
        "DiagnosisRelationships"
})
public class Diagnosis {

    @JsonProperty("DiagnosisRequirements")
    private List<String> diagnosisRequirements = null;
    @JsonProperty("DiagnosisRelationships")
    private List<DiagnosisRelationship> diagnosisRelationships = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("DiagnosisRequirements")
    public List<String> getDiagnosisRequirements() {
        return diagnosisRequirements;
    }

    @JsonProperty("DiagnosisRequirements")
    public void setDiagnosisRequirements(List<String> diagnosisRequirements) {
        this.diagnosisRequirements = diagnosisRequirements;
    }

    @JsonProperty("DiagnosisRelationships")
    public List<DiagnosisRelationship> getDiagnosisRelationships() {
        return diagnosisRelationships;
    }

    @JsonProperty("DiagnosisRelationships")
    public void setDiagnosisRelationships(List<DiagnosisRelationship> diagnosisRelationships) {
        this.diagnosisRelationships = diagnosisRelationships;
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