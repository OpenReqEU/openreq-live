package eu.openreq.repository;

import eu.openreq.dbo.OmittedPotentialDependencyDbo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface OmittedPotentialDependencyRepository extends CrudRepository<OmittedPotentialDependencyDbo, Long> {
    @Query("SELECT d from OmittedPotentialDependencyDbo d where d.primaryKey.sourceRequirement.id = ?1 AND d.primaryKey.targetRequirement.id = ?2 AND d.primaryKey.user.id = ?3")
    OmittedPotentialDependencyDbo findOneBySourceRequirementIDAndTargetRequirementIDAndUserID(long sourceRequirementID, long targetRequirementID, long userID);
    @Query("SELECT d from OmittedPotentialDependencyDbo d where d.primaryKey.user.id = ?1")
    List<OmittedPotentialDependencyDbo> findAllByUserID(long userID);
}
