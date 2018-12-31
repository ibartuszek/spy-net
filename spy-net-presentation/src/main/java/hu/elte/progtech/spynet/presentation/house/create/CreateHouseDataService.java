package hu.elte.progtech.spynet.presentation.house.create;

import com.google.common.base.Preconditions;
import hu.elte.progtech.spynet.domain.house.HouseService;
import hu.elte.progtech.spynet.presentation.house.HouseData;
import hu.elte.progtech.spynet.presentation.house.HouseDataService;
import hu.elte.progtech.spynet.presentation.house.HouseDataTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The class is a singleton class of which responsibility is to handle of the controller's requests.
 */
@Component
public class CreateHouseDataService {

    @Autowired
    private HouseService houseService;

    @Autowired
    private HouseDataTransformer houseDataTransformer;

    @Autowired
    private HouseDataService houseDataService;

    /**
     * It saves the character. If It is valid and has transformated then it transfer the
     * request to the domain layer.
     * @param houseRequest cannot be null, if it is null it throws IllegalArgumentException.
     * @return a boolean, which is true if it can send to domain the requests.
     */
    public boolean saveHouse(HouseRequest houseRequest) {
        Preconditions.checkArgument(houseRequest != null, "houseRequest cannot be null!");
        boolean notFound = checkHouse(houseRequest);
        if (notFound) {
            houseService.saveHouse(houseDataTransformer.transformFromHouseRequest(houseRequest));
        }
        return notFound;
    }

    private boolean checkHouse(HouseRequest houseRequest) {
        List<HouseData> houseDataList = houseDataService.getHouseDataList();
        boolean found = false;
        int index = 0;
        while (index < houseDataList.size() && !found) {
            found = houseRequest.getName().equals(houseDataList.get(index).getName());
            index++;
        }
        return !found;
    }

}
