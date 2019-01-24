package eu.openreq.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import eu.openreq.dbo.AnonymousUserRequirementAttributeVoteDbo;

public interface AnonymousUserRequirementAttributeVoteRepository extends CrudRepository<AnonymousUserRequirementAttributeVoteDbo, Long> {
	@Query("SELECT v from AnonymousUserRequirementAttributeVoteDbo v where v.primaryKey.nameOfAnonymousUser = ?1 AND v.primaryKey.requirement.id = ?2 AND v.primaryKey.ratingAttribute.id = ?3")
	AnonymousUserRequirementAttributeVoteDbo findOneByUserIdAndRequirementIdAndRatingAttributeId(String nameOfAnonymousUser, long requirementID, long ratingAttributeID);
	@Query("SELECT v from AnonymousUserRequirementAttributeVoteDbo v where v.primaryKey.ratingAttribute.id = ?1")
	List<AnonymousUserRequirementAttributeVoteDbo> findByRatingAttributeID(long ratingAttributeID);
	@Query("SELECT v from AnonymousUserRequirementAttributeVoteDbo v where v.primaryKey.requirement.id = ?1")
	List<AnonymousUserRequirementAttributeVoteDbo> findByRequirementID(long requirementID);
}
