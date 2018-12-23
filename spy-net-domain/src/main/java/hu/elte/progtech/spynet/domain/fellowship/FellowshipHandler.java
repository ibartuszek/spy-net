package hu.elte.progtech.spynet.domain.fellowship;

import hu.elte.progtech.spynet.dal.fellowship.FellowshipDao;
import hu.elte.progtech.spynet.dal.fellowship.FellowshipDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("fellowshipService")
public class FellowshipHandler implements FellowshipService {

    @Autowired
    private FellowshipDao fellowshipDao;

    @Autowired
    private FellowshipTransformer fellowshipTransformer;

    @Override
    public void saveFellowship(Fellowship fellowship) {
        fellowshipDao.saveFellowship(fellowshipTransformer.transformToFellowshipDto(fellowship));
    }

    @Override
    public void updateFellowship(Fellowship fellowship) {
        if (fellowship.getEnd() != null) {
            FellowshipDto fellowshipDto = fellowshipTransformer.transformToFellowshipDto(fellowship);
            fellowshipDao.updateFellowship(fellowshipDto);
        }
    }

    @Override
    public List<Fellowship> listFellowships() {
        return convertToFellowshipList(fellowshipDao.listFellowships());
    }

    private List<Fellowship> convertToFellowshipList(List<FellowshipDto> fellowshipDtoList) {
        List<Fellowship> fellowshipList = new ArrayList<>();
        for (FellowshipDto fellowshipDto : fellowshipDtoList) {
            fellowshipList.add(fellowshipTransformer.transformFromFellowshipDto(fellowshipDto));
        }
        return fellowshipList;
    }
}
