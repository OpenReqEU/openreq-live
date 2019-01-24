package eu.openreq.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class DependencyId implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.ALL})
    private RequirementDbo sourceRequirement;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.ALL})
    private RequirementDbo targetRequirement;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable=false)
    private DependencyDbo.Type type = DependencyDbo.Type.REQUIRES;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DependencyId that = (DependencyId) o;
        return Objects.equals(sourceRequirement, that.sourceRequirement) &&
                Objects.equals(targetRequirement, that.targetRequirement) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceRequirement, targetRequirement, type);
    }
}
