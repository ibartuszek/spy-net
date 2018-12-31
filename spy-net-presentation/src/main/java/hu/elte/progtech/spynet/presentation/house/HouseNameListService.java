package hu.elte.progtech.spynet.presentation.house;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The class is a singleton class of which responsibility is to create a List<String> object,
 * which holds the existing houses' name.
 */
@Component
public class HouseNameListService {

    @Autowired
    private List<String> houseNameList;

    @Autowired
    private HouseDataService houseDataService;

    /**
     * It updates the list (ask for the data from domain layer) and get back the new list.
     * @return List<String>
     */
    public List<String> getHouseNameList() {
        updateHouseNameList();
        return houseNameList;
    }

    private void updateHouseNameList() {
        houseNameList.clear();
        houseNameList.add("");
        List<HouseData> houseDataList = houseDataService.getHouseDataList();
        for (HouseData houseData : houseDataList) {
            houseNameList.add(houseData.getName());
        }
    }
}
