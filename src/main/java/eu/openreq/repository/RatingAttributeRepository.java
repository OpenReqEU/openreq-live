package eu.openreq.repository;

import org.springframework.data.repository.CrudRepository;
import eu.openreq.dbo.RatingAttributeDbo;

public interface RatingAttributeRepository extends CrudRepository<RatingAttributeDbo, Long> {}
