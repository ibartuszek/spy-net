package hu.elte.progtech.spynet.presentation.fellowship.create;

import com.google.common.base.Preconditions;
import hu.elte.progtech.spynet.domain.fellowship.FellowshipService;
import hu.elte.progtech.spynet.presentation.fellowship.FellowshipData;
import hu.elte.progtech.spynet.presentation.fellowship.FellowshipDataService;
import hu.elte.progtech.spynet.presentation.fellowship.FellowshipDataTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreateFellowshipDataService {

    @Autowired
    private FellowshipService fellowshipService;

    @Autowired
    private FellowshipDataTransformer fellowshipDataTransformer;

    @Autowired
    private FellowshipDataService fellowshipDataService;

    public boolean checkRequestHouses(FellowshipRequest fellowshipRequest) {
        return fellowshipRequest.getHouse1().equals(fellowshipRequest.getHouse2());
    }

    public boolean saveFellowship(FellowshipRequest fellowshipRequest) {
        Preconditions.checkArgument(fellowshipRequest != null, "fellowshipRequest cannot be null!");
        boolean notFound = checkFellowship(fellowshipRequest);
        if (notFound) {
            fellowshipService.saveFellowship(fellowshipDataTransformer.transformFromFellowshipRequest(fellowshipRequest));
        }
        return notFound;
    }

    private boolean checkFellowship(FellowshipRequest fellowshipRequest) {
        if (fellowshipRequest.getHouse1().equals(fellowshipRequest.getHouse2())) {
            return true;
        }
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
