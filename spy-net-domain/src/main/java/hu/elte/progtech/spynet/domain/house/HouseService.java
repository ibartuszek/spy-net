package hu.elte.progtech.spynet.domain.house;

import java.util.List;

public interface HouseService {

    void saveHouse(House house);

    List<House> listHouses();

    House getHouseByName(String name);

    House getHouseById(long id);
}
