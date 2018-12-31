package hu.elte.progtech.spynet.presentation.fellowship;

import hu.elte.progtech.spynet.domain.fellowship.Fellowship;
import hu.elte.progtech.spynet.presentation.fellowship.create.FellowshipRequest;
import hu.elte.progtech.spynet.presentation.house.HouseDataService;
import hu.elte.progtech.spynet.presentation.house.HouseDataTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class FellowshipDataTransformer {

    @Autowired
    private HouseDataService houseDataService;

    @Autowired
    private HouseDataTransformer houseDataTransformer;

    @Autowired
    private DateTimeFormatter dateTimeFormatter;

    public FellowshipData transformToFellowshipData(Fellowship fellowship) {
        FellowshipData fellowshipData = null;
        if (fellowship != null && fellowship.getBegin() != null
                && fellowship.getHouse1() != null && fellowship.getHouse2() != null) {
            fellowshipData = new FellowshipData();
            fellowshipData.setFellowshipId(fellowship.getFellowshipId());
            fellowshipData.setHouse1(fellowship.getHouse1().getName());
            fellowshipData.setHouse2(fellowship.getHouse2().getName());
            fellowshipData.setBegin(fellowship.getBegin().toString());
            if (fellowship.getEnd() != null) {
                fellowshipData.setEnd(fellowship.getEnd().toString());
                fellowshipData.setFellowshipUrl("");
            } else {
                fellowshipData.setEnd("");
                fellowshipData.setFellowshipUrl(String.format("%s?fellowshipId=%d",
                        ModifyFellowshipDataController.REQUEST_MAPPING, fellowshipData.getFellowshipId()));
            }
        }
        return fellowshipData;
    }

    public Fellowship transformFromFellowshipRequest(FellowshipRequest fellowshipRequest) {
        Fellowship fellowship = null;
        if (fellowshipRequest != null && fellowshipRequest.getBegin() != null
                && fellowshipRequest.getHouse1() != null && fellowshipRequest.getHouse2() != null) {
            fellowship = new Fellowship();
            fellowship.setHouse1(houseDataTransformer.transformFromHouseData(
                    houseDataService.getHouseDataByName(fellowshipRequest.getHouse1())));
            fellowship.setHouse2(houseDataTransformer.transformFromHouseData(
                    houseDataService.getHouseDataByName(fellowshipRequest.getHouse2())));
            fellowship.setBegin(LocalDate.parse(fellowshipRequest.getBegin(), dateTimeFormatter));
            if (!"".equals(fellowshipRequest.getEnd())) {
                fellowship.setEnd(LocalDate.parse(fellowshipRequest.getEnd(), dateTimeFormatter));
            }
        }
        return fellowship;
    }

    public FellowshipRequest transformToFellowshipRequest(Fellowship fellowship) {
        FellowshipRequest fellowshipRequest = null;
        if (fellowship != null) {
            fellowshipRequest = new FellowshipRequest();
            fellowshipRequest.setHouse1(fellowship.getHouse1().getName());
            fellowshipRequest.setHouse2(fellowship.getHouse2().getName());
            fellowshipRequest.setBegin(fellowship.getBegin().toString());
            LocalDate end = fellowship.getEnd();
            if (end == null) {
                fellowshipRequest.setEnd("");
            } else {
                fellowshipRequest.setEnd(end.toString());
            }
        }
        return fellowshipRequest;
    }

    public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }
}
