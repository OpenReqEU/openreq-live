package eu.openreq.repository;

import eu.openreq.dbo.ProjectDbo;
import org.springframework.data.repository.CrudRepository;
import eu.openreq.dbo.ReleaseDbo;

public interface ReleaseRepository extends CrudRepository<ReleaseDbo, Long> {
    ReleaseDbo findOneByNameAndProject(String name, ProjectDbo project);
}
