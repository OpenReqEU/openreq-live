package eu.openreq.repository;

import eu.openreq.dbo.PotentialDependencyDbo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface PotentialDependencyRepository extends CrudRepository<PotentialDependencyDbo, Long> {
    @Query("SELECT d from PotentialDependencyDbo d where d.primaryKey.sourceRequirement.id = ?1 AND d.primaryKey.targetRequirement.id = ?2 AND d.primaryKey.user.id = ?3")
    PotentialDependencyDbo findOneBySourceRequirementIDAndTargetRequirementIDAndUserID(long sourceRequirementID, long targetRequirementID, long userID);
    @Query("SELECT d from PotentialDependencyDbo d where d.primaryKey.sourceRequirement.id = ?1")
    List<PotentialDependencyDbo> findAllBySourceRequirementID(long sourceRequirementID);
    @Query("SELECT d from PotentialDependencyDbo d where d.primaryKey.user.id = ?1")
    List<PotentialDependencyDbo> findAllByUserID(long userID);
}
