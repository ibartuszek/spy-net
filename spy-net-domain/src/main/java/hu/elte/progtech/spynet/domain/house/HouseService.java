package hu.elte.progtech.spynet.domain.house;

import java.util.List;

/**
 * It is the main connection to domain fellowships.
 */
public interface HouseService {

    /**
     * t save the house into the db.
     * @param house if it is a null it throws IllegalArgumentException
     */
    void saveHouse(House house);

    /**
     * It fetches the houses from dal.
     * @return List<House>, if the dal layer has not such type of element, it is an empty list.
     */
    List<House> listHouses();

    /**
     * It fetches for a house by its name.
     * @param name it cannot be 0. Throws IllegalArgumentException.
     * @return a House, which can be null, if it cannot be found in DB.
     */
    House getHouseByName(String name);

    /**
     * It fetches for a house by its id.
     * @param id it cannot be 0. Throws IllegalArgumentException.
     * @return a House, which can be null, if it cannot be found in DB.
     */
    House getHouseById(long id);
}
