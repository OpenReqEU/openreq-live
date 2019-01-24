package eu.openreq.repository;

import eu.openreq.dbo.UserStakeholderAttributeVoteDbo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserStakeholderAttributeVoteRepository extends CrudRepository<UserStakeholderAttributeVoteDbo, Long> {
    @Query("SELECT v from UserStakeholderAttributeVoteDbo v where v.primaryKey.ratedStakeholder.id = ?1 AND v.primaryKey.requirement.id = ?2 AND v.primaryKey.ratingAttribute.id = ?3 AND v.primaryKey.user.id = ?4")
    UserStakeholderAttributeVoteDbo findOneByRatedStakeholderIdAndRequirementIdAndRatingAttributeIdAndUserId(long userIdOfRatedAnonymousStakeholder, long requirementID, long ratingAttributeID, long userID);
}
