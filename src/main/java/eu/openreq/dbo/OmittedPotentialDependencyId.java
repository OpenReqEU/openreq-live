package eu.openreq.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class OmittedPotentialDependencyId implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.ALL})
    private RequirementDbo sourceRequirement;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.ALL})
    private RequirementDbo targetRequirement;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.ALL})
    private UserDbo user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OmittedPotentialDependencyId that = (OmittedPotentialDependencyId) o;
        return Objects.equals(sourceRequirement, that.sourceRequirement) &&
                Objects.equals(targetRequirement, that.targetRequirement) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceRequirement, targetRequirement, user);
    }
}
