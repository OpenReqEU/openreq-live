package eu.openreq.repository;

import eu.openreq.dbo.DependencyDbo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DependencyRepository extends CrudRepository<DependencyDbo, Long> {
    @Query("SELECT d from DependencyDbo d where d.primaryKey.sourceRequirement.id = ?1 AND d.primaryKey.targetRequirement.id = ?2 AND d.primaryKey.type = ?3")
    DependencyDbo findOneBySourceRequirementIDAndTargetRequirementIDAndType(long sourceRequirementID, long targetRequirementID, DependencyDbo.Type type);
    @Query("SELECT d from DependencyDbo d where d.primaryKey.sourceRequirement.id = ?1")
    List<DependencyDbo> findAllBySourceRequirementID(long sourceRequirementID);
}
