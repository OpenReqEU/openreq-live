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
        "Release",
        "Release_msg",
        "RequirementsAssigned",
        "RequirementsAssigned_msg",
        "CapacityUsed",
        "CapacityUsed_msg",
        "AvailableCapacity",
        "CapacityBalance",
        "CapacityUsageCombined_msg"
})
public class Release {

    @JsonProperty("Release")
    private Integer release;
    @JsonProperty("Release_msg")
    private String releaseMsg;
    @JsonProperty("RequirementsAssigned")
    private List<String> requirementsAssigned = null;
    @JsonProperty("RequirementsAssigned_msg")
    private String requirementsAssignedMsg;
    @JsonProperty("CapacityUsed")
    private Integer capacityUsed;
    @JsonProperty("CapacityUsed_msg")
    private String capacityUsedMsg;
    @JsonProperty("AvailableCapacity")
    private Integer availableCapacity;
    @JsonProperty("CapacityBalance")
    private Integer capacityBalance;
    @JsonProperty("CapacityUsageCombined_msg")
    private String capacityUsageCombinedMsg;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Release")
    public Integer getRelease() {
        return release;
    }

    @JsonProperty("Release")
    public void setRelease(Integer release) {
        this.release = release;
    }

    @JsonProperty("Release_msg")
    public String getReleaseMsg() {
        return releaseMsg;
    }

    @JsonProperty("Release_msg")
    public void setReleaseMsg(String releaseMsg) {
        this.releaseMsg = releaseMsg;
    }

    @JsonProperty("RequirementsAssigned")
    public List<String> getRequirementsAssigned() {
        return requirementsAssigned;
    }

    @JsonProperty("RequirementsAssigned")
    public void setRequirementsAssigned(List<String> requirementsAssigned) {
        this.requirementsAssigned = requirementsAssigned;
    }

    @JsonProperty("RequirementsAssigned_msg")
    public String getRequirementsAssignedMsg() {
        return requirementsAssignedMsg;
    }

    @JsonProperty("RequirementsAssigned_msg")
    public void setRequirementsAssignedMsg(String requirementsAssignedMsg) {
        this.requirementsAssignedMsg = requirementsAssignedMsg;
    }

    @JsonProperty("CapacityUsed")
    public Integer getCapacityUsed() {
        return capacityUsed;
    }

    @JsonProperty("CapacityUsed")
    public void setCapacityUsed(Integer capacityUsed) {
        this.capacityUsed = capacityUsed;
    }

    @JsonProperty("CapacityUsed_msg")
    public String getCapacityUsedMsg() {
        return capacityUsedMsg;
    }

    @JsonProperty("CapacityUsed_msg")
    public void setCapacityUsedMsg(String capacityUsedMsg) {
        this.capacityUsedMsg = capacityUsedMsg;
    }

    @JsonProperty("AvailableCapacity")
    public Integer getAvailableCapacity() {
        return availableCapacity;
    }

    @JsonProperty("AvailableCapacity")
    public void setAvailableCapacity(Integer availableCapacity) {
        this.availableCapacity = availableCapacity;
    }

    @JsonProperty("CapacityBalance")
    public Integer getCapacityBalance() {
        return capacityBalance;
    }

    @JsonProperty("CapacityBalance")
    public void setCapacityBalance(Integer capacityBalance) {
        this.capacityBalance = capacityBalance;
    }

    @JsonProperty("CapacityUsageCombined_msg")
    public String getCapacityUsageCombinedMsg() {
        return capacityUsageCombinedMsg;
    }

    @JsonProperty("CapacityUsageCombined_msg")
    public void setCapacityUsageCombinedMsg(String capacityUsageCombinedMsg) {
        this.capacityUsageCombinedMsg = capacityUsageCombinedMsg;
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