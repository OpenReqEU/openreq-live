package eu.openreq.repository;

import eu.openreq.dbo.AnonymousUserStakeholderAttributeVoteDbo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnonymousUserStakeholderAttributeVoteRepository extends CrudRepository<AnonymousUserStakeholderAttributeVoteDbo, Long> {
    @Query("SELECT v from AnonymousUserStakeholderAttributeVoteDbo v where v.primaryKey.ratedAnonymousStakeholder.id = ?1 AND v.primaryKey.requirement.id = ?2 AND v.primaryKey.ratingAttribute.id = ?3 AND v.primaryKey.user.id = ?4")
    AnonymousUserStakeholderAttributeVoteDbo findOneByRatedAnonymousStakeholderIdAndRequirementIdAndRatingAttributeIdAndUserId(long userIDOfRatedAnonymousStakeholder, long requirementID, long ratingAttributeID, long userID);
    @Query("SELECT v from AnonymousUserStakeholderAttributeVoteDbo v where v.primaryKey.ratedAnonymousStakeholder.id = ?1 AND v.primaryKey.requirement.id = ?2")
    List<AnonymousUserStakeholderAttributeVoteDbo> findByRatedAnonymousStakeholderIdAndRequirementId(long userIDOfRatedAnonymousStakeholder, long requirementID);
}
