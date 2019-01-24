package eu.openreq.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import eu.openreq.dbo.ProjectUserParticipationDbo;

public interface ProjectUserParticipationRepository extends CrudRepository<ProjectUserParticipationDbo, Long> {
	@Query("SELECT p from ProjectUserParticipationDbo p where p.primaryKey.project.id = ?1 AND p.primaryKey.user.id = ?2")
	List<ProjectUserParticipationDbo> findByProjectIdAndUserId(long projectID, long userID);
}
