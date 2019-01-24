package eu.openreq.repository;

import org.springframework.data.repository.CrudRepository;
import eu.openreq.dbo.ProjectDbo;

public interface ProjectRepository extends CrudRepository<ProjectDbo, Long> {
	ProjectDbo findOneByUniqueKey(String uniqueKey);
}
