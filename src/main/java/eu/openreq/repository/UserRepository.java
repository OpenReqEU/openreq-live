package eu.openreq.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import eu.openreq.dbo.UserDbo;

public interface UserRepository extends CrudRepository<UserDbo, Long> {
	UserDbo findOneByUsername(String username);
	UserDbo findOneByMailAddress(String mailAddress);

	@Query("SELECT u FROM UserDbo u WHERE u.username LIKE %?1% OR u.firstName LIKE %?1% OR u.lastName LIKE %?1% OR u.mailAddress LIKE %?1%")
	List<UserDbo> findUserBySearchTerm(String searchTerm);

	@Query("SELECT u FROM UserDbo u WHERE u.username NOT LIKE %?2% AND (u.username LIKE %?1% OR u.firstName LIKE %?1% OR u.lastName LIKE %?1%)")
	List<UserDbo> findUserBySearchTermExcludeEmailAddress(String searchTerm, String atCharacter);
}
