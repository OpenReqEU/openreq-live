package eu.openreq.repository;

import org.springframework.data.repository.CrudRepository;
import eu.openreq.dbo.SkillDbo;

public interface SkillRepository extends CrudRepository<SkillDbo, Long> {
	SkillDbo findOneByKeyword(String keyword);
}
