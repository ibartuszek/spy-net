package hu.elte.progtech.spynet.presentation.fellowship.modify;

import hu.elte.progtech.spynet.domain.fellowship.Fellowship;
import hu.elte.progtech.spynet.domain.fellowship.FellowshipService;
import hu.elte.progtech.spynet.presentation.fellowship.FellowshipData;
import hu.elte.progtech.spynet.presentation.fellowship.FellowshipDataService;
import hu.elte.progtech.spynet.presentation.fellowship.FellowshipDataTransformer;
import hu.elte.progtech.spynet.presentation.fellowship.create.FellowshipRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ModifyFellowshipDataService {

    @Autowired
    private FellowshipService fellowshipService;

    @Autowired
    private FellowshipDataService fellowshipDataService;

    @Autowired
    private FellowshipDataTransformer fellowshipDataTransformer;

    @Autowired
    private DateTimeFormatter dateTimeFormatter;

    public FellowshipRequest createFellowshipRequest(ModifyFellowshipRequest modifyFellowshipRequest) {
        FellowshipRequest fellowshipRequest = new FellowshipRequest();
        if (modifyFellowshipRequest.getFellowshipId() != 0) {
            Fellowship fellowship = fellowshipService.findById(modifyFellowshipRequest.getFellowshipId());
            fellowshipRequest = fellowshipDataTransformer.transformToFellowshipRequest(fellowship);
        }
        return fellowshipRequest;
    }

    public boolean saveFellowship(FellowshipRequest fellowshipRequest) {
        boolean modified = false;
        List<FellowshipData> fellowshipDataList = fellowshipDataService.getFellowshipDataList();
        FellowshipData fellowshipData = getFellowshipDataFromList(fellowshipDataList, fellowshipRequest);
        if (fellowshipData != null) {
            modified = modifyFellowship(fellowshipRequest, fellowshipData);
        }
        return modified;
    }

    private FellowshipData getFellowshipDataFromList(List<FellowshipData> fellowshipDataList, FellowshipRequest fellowshipRequest) {
        FellowshipData fellowshipData = null;
        boolean found = false;
        for (int i = 0; i < fellowshipDataList.size() && !found; i++) {
            found = equals(fellowshipDataList.get(i), fellowshipRequest);
            if (found) {
                fellowshipData = fellowshipDataList.get(i);
            }
        }
        return  fellowshipData;
    }

    private boolean equals(FellowshipData fellowshipData, FellowshipRequest fellowshipRequest) {
        boolean equal = checkHouses(fellowshipData, fellowshipRequest);
        if (equal) {
            equal = checkDates(fellowshipData, fellowshipRequest);
        }
        return equal;
    }

    private boolean checkHouses(FellowshipData fellowshipData, FellowshipRequest fellowshipRequest) {
        boolean equal = false;
        if (fellowshipData.getHouse1().equals(fellowshipRequest.getHouse1()) ||
                fellowshipData.getHouse1().equals(fellowshipRequest.getHouse2())) {
            if (fellowshipData.getHouse2().equals(fellowshipRequest.getHouse1()) ||
                    fellowshipData.getHouse2().equals(fellowshipRequest.getHouse2())) {
                equal = true;
            }
        }
        return equal;
    }

    private boolean checkDates(FellowshipData fellowshipData, FellowshipRequest fellowshipRequest) {
        boolean equal = false;
        if (fellowshipData.getBegin().equals(fellowshipRequest.getBegin()) && "".equals(fellowshipData.getEnd())) {
            equal = fellowshipData.getBegin().equals(fellowshipRequest.getBegin());
        }
        return equal;
    }

    private boolean modifyFellowship(FellowshipRequest fellowshipRequest, FellowshipData fellowshipData) {
        boolean modified = true;
        Fellowship fellowship = fellowshipService.findById(fellowshipData.getFellowshipId());
        if (fellowship.getEnd() == null) {
            modifyFellowship(fellowship, fellowshipRequest);
            fellowshipService.updateFellowship(fellowship);
        } else {
            modified = false;
        }
        return modified;
    }

    private void modifyFellowship(Fellowship fellowship, FellowshipRequest fellowshipRequest) {
        fellowship.setEnd(LocalDate.parse(fellowshipRequest.getEnd(), dateTimeFormatter));
    }

    public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }
}
