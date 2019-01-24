package eu.openreq.repository;

import java.util.List;

import eu.openreq.dbo.AnonymousUserRequirementAttributeVoteDbo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import eu.openreq.dbo.UserRequirementAttributeVoteDbo;

public interface UserRequirementAttributeVoteRepository extends CrudRepository<UserRequirementAttributeVoteDbo, Long> {
	@Query("SELECT v from UserRequirementAttributeVoteDbo v where v.primaryKey.user.id = ?1 AND v.primaryKey.requirement.id = ?2 AND v.primaryKey.ratingAttribute.id = ?3")
	UserRequirementAttributeVoteDbo findOneByUserIdAndRequirementIdAndRatingAttributeId(long userID, long requirementID, long ratingAttributeID);
	@Query("SELECT v from UserRequirementAttributeVoteDbo v where v.primaryKey.ratingAttribute.id = ?1")
	List<UserRequirementAttributeVoteDbo> findByRatingAttributeID(long ratingAttributeID);
	@Query("SELECT v from UserRequirementAttributeVoteDbo v where v.primaryKey.requirement.id = ?1")
	List<UserRequirementAttributeVoteDbo> findByRequirementID(long requirementID);
}
