package hu.elte.progtech.spynet.domain.fellowship;

import com.google.common.base.Preconditions;
import hu.elte.progtech.spynet.dal.fellowship.FellowshipDao;
import hu.elte.progtech.spynet.dal.fellowship.FellowshipDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * It is a service class, which is responsible for the service methods of fellowships.
 * It is the implementation of FellowshipService interface.
 */
@Component("fellowshipService")
public class FellowshipHandler implements FellowshipService {

    @Autowired
    private FellowshipDao fellowshipDao;

    @Autowired
    private FellowshipTransformer fellowshipTransformer;

    @Override
    public void saveFellowship(Fellowship fellowship) {
        Preconditions.checkArgument(fellowship != null, "fellowship cannot be null!");
        fellowshipDao.saveFellowship(fellowshipTransformer.transformToFellowshipDto(fellowship));
    }

    @Override
    public void updateFellowship(Fellowship fellowship) {
        Preconditions.checkArgument(fellowship != null, "fellowship cannot be null!");
        Preconditions.checkArgument(fellowship.getEnd() != null, "fellowship.end cannot be null!");
        fellowshipDao.updateFellowship(fellowshipTransformer.transformToFellowshipDto(fellowship));
    }

    @Override
    public List<Fellowship> listFellowships() {
        return convertToFellowshipList(fellowshipDao.listFellowships());
    }

    @Override
    public Fellowship findById(long fellowshipId) {
        Preconditions.checkArgument(fellowshipId != 0, "fellowshipId cannot be 0!");
        return fellowshipTransformer.transformFromFellowshipDto(fellowshipDao.findById(fellowshipId));
    }

    private List<Fellowship> convertToFellowshipList(List<FellowshipDto> fellowshipDtoList) {
        List<Fellowship> fellowshipList = new ArrayList<>();
        for (FellowshipDto fellowshipDto : fellowshipDtoList) {
            fellowshipList.add(fellowshipTransformer.transformFromFellowshipDto(fellowshipDto));
        }
        return fellowshipList;
    }
}
