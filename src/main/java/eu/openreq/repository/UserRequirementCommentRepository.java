package eu.openreq.repository;

import eu.openreq.dbo.UserRequirementCommentDbo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UserRequirementCommentRepository extends CrudRepository<UserRequirementCommentDbo, Long> {
    @Query("SELECT c from UserRequirementCommentDbo c where c.requirement.id = ?1")
    List<UserRequirementCommentDbo> findByRequirementID(long requirementID);
}
