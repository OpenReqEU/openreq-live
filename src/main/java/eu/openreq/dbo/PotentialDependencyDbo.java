package eu.openreq.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.*;
import javax.persistence.*;

@Data
@Entity
@Table(name = "or_potential_dependency")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.sourceRequirement", joinColumns = @JoinColumn(name = "source_requirement_id")),
        @AssociationOverride(name = "primaryKey.targetRequirement", joinColumns = @JoinColumn(name = "target_requirement_id")),
        @AssociationOverride(name = "primaryKey.user", joinColumns = @JoinColumn(name = "user_id"))
})
public class PotentialDependencyDbo {

    public enum Type { REQUIRES, EXCLUDES, SIMILAR, PART_OF, NONE }

    @JsonIgnore
    @EmbeddedId
    private PotentialDependencyId primaryKey = new PotentialDependencyId();

    @Column(name = "visible", nullable=false)
    private boolean visible;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable=false)
    private Type type = Type.REQUIRES;

    @Column(name = "reverse_direction", nullable=true)
    private boolean reverseDirection;

    @Column(name = "duration", nullable=true)
    private Long duration;

    @Column(name = "created_date", nullable=false)
    private Date createdDate;

    @Column(name = "last_updated_date", nullable=false)
    private Date lastUpdatedDate;

    public PotentialDependencyDbo() {
        this.visible = true;
    }

    public PotentialDependencyDbo(RequirementDbo sourceRequirement, RequirementDbo targetRequirement, UserDbo user,
                                  Type type, boolean isreverseDirection, long duration) {
        this.visible = true;
        this.type = type;
        this.reverseDirection = isreverseDirection;
        this.duration = duration;
        this.getPrimaryKey().setSourceRequirement(sourceRequirement);
        this.getPrimaryKey().setTargetRequirement(targetRequirement);
        this.getPrimaryKey().setUser(user);
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = new Date();
        this.lastUpdatedDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdatedDate = new Date();
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
        PotentialDependencyDbo that = (PotentialDependencyDbo) o;
        return Objects.equals(primaryKey, that.primaryKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primaryKey);
    }
}
