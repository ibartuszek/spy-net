package hu.elte.progtech.spynet.dal.house;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HouseRepository extends CrudRepository<HouseEntity, Long> {

    Optional<HouseEntity> findByName(String name);
}
