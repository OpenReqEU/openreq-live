package eu.openreq.repository;

import eu.openreq.dbo.AnonymousUserDbo;
import org.springframework.data.repository.CrudRepository;

public interface AnonymousUserRepository extends CrudRepository<AnonymousUserDbo, Long> {
    AnonymousUserDbo findOneByFullName(String fullName);
}
