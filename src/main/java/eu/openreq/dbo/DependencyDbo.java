package eu.openreq.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.*;
import javax.persistence.*;

@Data
@Entity
@Table(name = "or_dependency")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.sourceRequirement", joinColumns = @JoinColumn(name = "source_requirement_id")),
        @AssociationOverride(name = "primaryKey.targetRequirement", joinColumns = @JoinColumn(name = "target_requirement_id")),
        @AssociationOverride(name = "primaryKey.type", joinColumns = @JoinColumn(name = "type"))
})
public class DependencyDbo {

    public enum Type {
        IMPLIES,
        REQUIRES,
        EXCLUDES,
        INCOMPATIBLE
    }

    @JsonIgnore
    @EmbeddedId
    private DependencyId primaryKey = new DependencyId();

    @Column(name = "visible", nullable=false)
    private boolean visible;

    @Column(name = "created_date", nullable=false)
    private Date createdDate;

    @Column(name = "last_updated_date", nullable=false)
    private Date lastUpdatedDate;

    public DependencyDbo() {
        this.visible = true;
    }

    public DependencyDbo(RequirementDbo sourceRequirement, RequirementDbo targetRequirement, Type type) {
        this.visible = true;
        this.getPrimaryKey().setSourceRequirement(sourceRequirement);
        this.getPrimaryKey().setTargetRequirement(targetRequirement);
        this.getPrimaryKey().setType(type);
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
    public Type getType() {
        return getPrimaryKey().getType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DependencyDbo that = (DependencyDbo) o;
        return Objects.equals(primaryKey, that.primaryKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primaryKey);
    }
}
