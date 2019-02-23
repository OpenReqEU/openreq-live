package eu.openreq.repository;

import eu.openreq.dbo.IssueDbo;
import org.springframework.data.repository.CrudRepository;

public interface IssueRepository extends CrudRepository<IssueDbo, Long> { }
