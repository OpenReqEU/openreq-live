package eu.openreq.repository;

import eu.openreq.dbo.RequirementAnonymousStakeholderAssignment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RequirementAnonymousStakeholderAssignmentRepository extends CrudRepository<RequirementAnonymousStakeholderAssignment, Long> {
    @Query("SELECT a from RequirementAnonymousStakeholderAssignment a where a.primaryKey.requirement.id = ?1 AND a.primaryKey.anonymousStakeholder.id = ?2")
    RequirementAnonymousStakeholderAssignment findOneByRequirementIdAndAnonymousStakeholderId(long requirementID, long userIDOfAnonymousStakeholder);
}
