package hu.elte.progtech.spynet.domain.house;

import hu.elte.progtech.spynet.domain.house.House;

import java.util.List;

public interface HouseService {

    void saveHouse(House house);
    List<House> listHouses();

}
