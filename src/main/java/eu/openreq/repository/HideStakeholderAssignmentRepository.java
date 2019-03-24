package eu.openreq.repository;

import eu.openreq.dbo.HideStakeholderAssignmentDbo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface HideStakeholderAssignmentRepository extends CrudRepository<HideStakeholderAssignmentDbo, Long> {
    @Query("SELECT h from HideStakeholderAssignmentDbo h where h.primaryKey.user.id = ?1 AND h.primaryKey.hiddenStakeholder.id = ?2 AND h.primaryKey.requirement.id = ?3")
    HideStakeholderAssignmentDbo findOneByUserIdAndHiddenStakeholderUserIdAndRequirementId(long userID, long hiddenStakeholderUserID, long requirementID);

    @Query("SELECT h from HideStakeholderAssignmentDbo h where h.primaryKey.user.id = ?1 AND h.primaryKey.requirement.id = ?2")
    HideStakeholderAssignmentDbo findOneByUserIdAndRequirementId(long userID, long requirementID);

    @Query("SELECT h from HideStakeholderAssignmentDbo h where h.primaryKey.requirement.id = ?1")
    Iterable<HideStakeholderAssignmentDbo> findAllByRequirementId(long requirementID);
}
