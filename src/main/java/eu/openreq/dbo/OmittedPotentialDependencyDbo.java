package eu.openreq.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;
import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "or_ommited_potential_dependency")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.sourceRequirement", joinColumns = @JoinColumn(name = "source_requirement_id")),
        @AssociationOverride(name = "primaryKey.targetRequirement", joinColumns = @JoinColumn(name = "target_requirement_id")),
        @AssociationOverride(name = "primaryKey.user", joinColumns = @JoinColumn(name = "user_id"))
})
public class OmittedPotentialDependencyDbo {

    @JsonIgnore
    @EmbeddedId
    private OmittedPotentialDependencyId primaryKey = new OmittedPotentialDependencyId();

    @Column(name = "created_date", nullable=false)
    private Date createdDate;

    public OmittedPotentialDependencyDbo(RequirementDbo sourceRequirement, RequirementDbo targetRequirement, UserDbo user) {
        this.getPrimaryKey().setSourceRequirement(sourceRequirement);
        this.getPrimaryKey().setTargetRequirement(targetRequirement);
        this.getPrimaryKey().setUser(user);
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = new Date();
    }

    @Transient
    public RequirementDbo getSourceRequirement() {
        return getPrimaryKey().getSourceRequirement();
    }

    @Transient
    public RequirementDbo getTargetRequirement() {
        return getPrimaryKey().getTargetRequirement();
    }

    @Transient
    public UserDbo getUser() {
        return getPrimaryKey().getUser();
    }

    public String getUniqueKey() {
        return getSourceRequirement().getId() + "-" + getTargetRequirement().getId() + "-" + getUser().getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OmittedPotentialDependencyDbo that = (OmittedPotentialDependencyDbo) o;
        return Objects.equals(primaryKey, that.primaryKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primaryKey);
    }
}
