package hu.elte.progtech.spynet.presentation.fellowship.create;

import com.google.common.base.Preconditions;
import hu.elte.progtech.spynet.domain.fellowship.FellowshipService;
import hu.elte.progtech.spynet.presentation.fellowship.FellowshipData;
import hu.elte.progtech.spynet.presentation.fellowship.FellowshipDataService;
import hu.elte.progtech.spynet.presentation.fellowship.FellowshipDataTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The class is a singleton class of which responsibility is to handle of the controller's requests.
 */
@Component
public class CreateFellowshipDataService {

    @Autowired
    private FellowshipService fellowshipService;

    @Autowired
    private FellowshipDataTransformer fellowshipDataTransformer;

    @Autowired
    private FellowshipDataService fellowshipDataService;

    /**
     * It checks the request data: house1 and house2 cannot be equal. If they are the same the method
     * returns true.
     * @param fellowshipRequest
     * @return true if the both house is the same.
     */
    public boolean checkRequestHouses(FellowshipRequest fellowshipRequest) {
        return fellowshipRequest.getHouse1().equals(fellowshipRequest.getHouse2());
    }

    /**
     * It saves the character. If It is valid and the data has transformated then it transfer the
     * request to the domain layer.
     * @param fellowshipRequest cannot be null, if it is null it throws IllegalArgumentException.
     * @return a boolean, which is true if it can send to domain the requests.
     */
    public boolean saveFellowship(FellowshipRequest fellowshipRequest) {
        Preconditions.checkArgument(fellowshipRequest != null, "fellowshipRequest cannot be null!");
        boolean notFound = checkFellowship(fellowshipRequest);
        if (notFound) {
            fellowshipService.saveFellowship(fellowshipDataTransformer.transformFromFellowshipRequest(fellowshipRequest));
        }
        return notFound;
    }

    private boolean checkFellowship(FellowshipRequest fellowshipRequest) {
        List<FellowshipData> fellowshipDataList = fellowshipDataService.getFellowshipDataList();
        boolean found = false;
        int index = 0;
        while (index < fellowshipDataList.size() && !found) {
            found = checkHouse(fellowshipRequest, fellowshipDataList.get(index));
            if (found) {
                found = checkDate(fellowshipRequest, fellowshipDataList.get(index));
            }
            index++;
        }
        return !found;
    }

    private boolean checkHouse(FellowshipRequest fellowshipRequest, FellowshipData fellowshipData) {
        boolean found = fellowshipRequest.getHouse1().equals(fellowshipData.getHouse1()) ||
                        fellowshipRequest.getHouse1().equals(fellowshipData.getHouse2());
        if (found) {
            found = fellowshipRequest.getHouse2().equals(fellowshipData.getHouse1()) ||
                    fellowshipRequest.getHouse2().equals(fellowshipData.getHouse2());
        }
        return found;
    }

    private boolean checkDate(FellowshipRequest fellowshipRequest, FellowshipData fellowshipData) {
        boolean found = "".equals(fellowshipData.getEnd());
        if (!found) {
            found = fellowshipRequest.getBegin().equals(fellowshipData.getBegin());
        }
        return found;
    }
}
