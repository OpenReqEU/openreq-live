package eu.openreq.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import eu.openreq.dbo.RequirementDbo;

public interface RequirementRepository extends CrudRepository<RequirementDbo, Long> {
	RequirementDbo findOneByTitle(String title);
}
