package eu.openreq.repository;

import eu.openreq.dbo.DelegateUserRequirementVoteDbo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DelegateUserRequirementVoteRepository extends CrudRepository<DelegateUserRequirementVoteDbo, Long> {
    @Query("SELECT v from DelegateUserRequirementVoteDbo v where v.primaryKey.user.id = ?1 AND v.primaryKey.delegatedUser.id = ?2 AND v.primaryKey.requirement.id = ?3")
    DelegateUserRequirementVoteDbo findOneByUserIdAndDelegatedUserIdAndRequirementId(long userID, long delegatedUserID, long requirementID);

    @Query("SELECT v from DelegateUserRequirementVoteDbo v where v.primaryKey.user.id = ?1 AND v.primaryKey.requirement.id = ?2")
    DelegateUserRequirementVoteDbo findOneByUserIdAndRequirementId(long userID, long requirementID);

    @Query("SELECT v from DelegateUserRequirementVoteDbo v where v.primaryKey.requirement.id = ?1")
    Iterable<DelegateUserRequirementVoteDbo> findAllByRequirementId(long requirementID);
}
