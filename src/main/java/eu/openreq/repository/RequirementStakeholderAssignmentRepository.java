package eu.openreq.repository;

import eu.openreq.dbo.RequirementStakeholderAssignment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RequirementStakeholderAssignmentRepository extends CrudRepository<RequirementStakeholderAssignment, Long> {
    @Query("SELECT a from RequirementStakeholderAssignment a where a.primaryKey.requirement.id = ?1 AND a.primaryKey.stakeholder.id = ?2")
    RequirementStakeholderAssignment findOneByRequirementIdAndStakeholderId(long requirementID, long userIDOfStakeholder);
}
