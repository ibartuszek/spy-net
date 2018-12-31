package hu.elte.progtech.spynet.dal.character;

import org.springframework.data.repository.CrudRepository;

/**
 * CrudRepository which is used to find, save and modify characters at the DB. It is implemented by spring.
 */
public interface CharacterRepository extends CrudRepository<CharacterEntity, Long> {

}
