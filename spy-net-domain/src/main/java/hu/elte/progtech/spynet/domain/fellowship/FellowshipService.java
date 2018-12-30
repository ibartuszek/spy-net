package hu.elte.progtech.spynet.domain.fellowship;

import java.util.List;

public interface FellowshipService {

    void saveFellowship(Fellowship fellowship);
    void updateFellowship(Fellowship fellowship);
    List<Fellowship> listFellowships();
    Fellowship findById(long fellowshipId);
}
