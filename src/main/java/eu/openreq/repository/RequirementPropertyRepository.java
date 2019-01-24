package eu.openreq.repository;

import org.springframework.data.repository.CrudRepository;
import eu.openreq.dbo.RatingAttributeDbo;

public interface RequirementPropertyRepository extends CrudRepository<RatingAttributeDbo, Long> {
	RatingAttributeDbo findOneByName(String name);
}
