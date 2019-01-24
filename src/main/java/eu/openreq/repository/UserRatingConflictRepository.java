package eu.openreq.repository;

import eu.openreq.dbo.UserRatingConflictDbo;
import eu.openreq.dbo.UserRequirementCommentDbo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRatingConflictRepository extends CrudRepository<UserRatingConflictDbo, Long> {
    @Query("SELECT c from UserRatingConflictDbo c where c.requirement.id = ?1 ORDER BY c.time DESC")
    List<UserRatingConflictDbo> findByRequirementID(long requirementID);
}
