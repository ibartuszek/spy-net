package hu.elte.progtech.spynet.presentation.house;

import hu.elte.progtech.spynet.domain.house.House;
import hu.elte.progtech.spynet.domain.house.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The class is a singleton class of which responsibility is to handle of the controller's requests.
 */
@Component
public class HouseDataService {

    @Autowired
    private HouseService houseService;

    @Autowired
    private HouseDataTransformer houseDataTransformer;

    /**
     * It asks for existing objects from domain layer. Transformates the elements a list and give it back.
     * @return List<HouseData>
     */
    public List<HouseData> getHouseDataList() {
        return transformToHouseDataList(houseService.listHouses());
    }

    private List<HouseData> transformToHouseDataList(List<House> houseList) {
        List<HouseData> houseDataList = new ArrayList<>();
        for (House house : houseList) {
            houseDataList.add(houseDataTransformer.transformToHouseData(house));
        }
        return houseDataList;
    }

    /**
     * It asks for domain layer a house object by its name. Then transformates it.
     * @param name name of the house.
     * @return a houseData object.
     */
    public HouseData getHouseDataByName(String name) {
        return houseDataTransformer.transformToHouseData(houseService.getHouseByName(name));
    }
}
