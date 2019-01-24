package eu.openreq.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import eu.openreq.dbo.ProjectGuestUserParticipationDbo;

public interface ProjectGuestUserParticipationRepository extends CrudRepository<ProjectGuestUserParticipationDbo, Long> {
	@Query("SELECT p from ProjectGuestUserParticipationDbo p where p.primaryKey.project.id = ?1 AND p.primaryKey.emailAddress = ?2")
	public List<ProjectGuestUserParticipationDbo> findByProjectIdAndEmailAddress(long projectID, String emailAddress);

	@Query("SELECT p from ProjectGuestUserParticipationDbo p where p.primaryKey.emailAddress = ?1")
	public List<ProjectGuestUserParticipationDbo> findByEmailAddress(String emailAddress);
}
