package eu.openreq.repository;

import eu.openreq.dbo.HideAnonymousStakeholderAssignmentDbo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface HideAnonymousStakeholderAssignmentRepository extends CrudRepository<HideAnonymousStakeholderAssignmentDbo, Long> {
    @Query("SELECT h from HideAnonymousStakeholderAssignmentDbo h where h.primaryKey.user.id = ?1 AND h.primaryKey.hiddenAnonymousStakeholder.id = ?2 AND h.primaryKey.requirement.id = ?3")
    HideAnonymousStakeholderAssignmentDbo findOneByUserIdAndHiddenStakeholderUserIdAndRequirementId(long userID, long hiddenAnonymousStakeholderUserID, long requirementID);

    @Query("SELECT h from HideAnonymousStakeholderAssignmentDbo h where h.primaryKey.user.id = ?1 AND h.primaryKey.requirement.id = ?2")
    HideAnonymousStakeholderAssignmentDbo findOneByUserIdAndRequirementId(long userID, long requirementID);

    @Query("SELECT h from HideAnonymousStakeholderAssignmentDbo h where h.primaryKey.requirement.id = ?1")
    Iterable<HideAnonymousStakeholderAssignmentDbo> findAllByRequirementId(long requirementID);
}
