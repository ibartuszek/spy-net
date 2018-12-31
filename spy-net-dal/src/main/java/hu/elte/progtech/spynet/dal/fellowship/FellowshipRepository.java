package hu.elte.progtech.spynet.dal.fellowship;

import org.springframework.data.repository.CrudRepository;

/**
 * CrudRepository which is used to find, save and modify fellowships at the DB. It is implemented by spring.
 */
public interface FellowshipRepository extends CrudRepository<FellowshipEntity, Long> {
}
