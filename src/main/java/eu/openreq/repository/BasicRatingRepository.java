package eu.openreq.repository;

import eu.openreq.dbo.UserRequirementVoteDbo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BasicRatingRepository extends CrudRepository<UserRequirementVoteDbo, Long> {
    @Query("SELECT v from UserRequirementVoteDbo v where v.primaryKey.user.id = ?1 AND v.primaryKey.requirement.id = ?2")
    UserRequirementVoteDbo findOneByUserIdAndRequirementId(long userID, long requirementID);

    @Query("SELECT v from UserRequirementVoteDbo v where v.primaryKey.requirement.id = ?1")
    List<UserRequirementVoteDbo> findByRequirementId(long requirementID);
}
