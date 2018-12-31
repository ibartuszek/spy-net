package hu.elte.progtech.spynet.dal.house;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * CrudRepository which is used to find, save and modify houses at the DB. It is implemented by spring.
 */
public interface HouseRepository extends CrudRepository<HouseEntity, Long> {

    /**
     * It fetches a houseEntity from the DB and put it into an optional class.
     * If it cannot be found it is an empty optional.
     * @param name String, which is the name of the house
     * @return an Optional<HouseEntity> class
     */
    Optional<HouseEntity> findByName(String name);
}
