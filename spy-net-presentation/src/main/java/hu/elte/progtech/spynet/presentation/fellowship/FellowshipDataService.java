package hu.elte.progtech.spynet.presentation.fellowship;

import hu.elte.progtech.spynet.domain.fellowship.Fellowship;
import hu.elte.progtech.spynet.domain.fellowship.FellowshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The class is a singleton class of which responsibility is to handle of the controller's requests.
 */
@Component
public class FellowshipDataService {

    @Autowired
    private FellowshipService fellowshipService;

    @Autowired
    private FellowshipDataTransformer fellowshipDataTransformer;

    /**
     * It asks for existing objects from domain layer. Transformates the elements a list and give it back.
     * @return List<FellowshipData>
     */
    public List<FellowshipData> getFellowshipDataList() {
        return transformToFellowshipDataList(fellowshipService.listFellowships());
    }

    private List<FellowshipData> transformToFellowshipDataList(List<Fellowship> fellowshipList) {
        List<FellowshipData> fellowshipDataList = new ArrayList<>();
        for (Fellowship fellowship : fellowshipList) {
            fellowshipDataList.add(fellowshipDataTransformer.transformToFellowshipData(fellowship));
        }
        return fellowshipDataList;
    }
}
