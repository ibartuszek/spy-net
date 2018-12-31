package hu.elte.progtech.spynet.domain.fellowship;

import java.util.List;

/**
 * It is the main connection to domain fellowships.
 */
public interface FellowshipService {

    /**
     * t save the fellowship into the db.
     * @param fellowship if it is a null it throws IllegalArgumentException
     */
    void saveFellowship(Fellowship fellowship);

    /**
     * It modifies the fellowship and save changes into the db.
     * @param fellowship if it is a null it throws IllegalArgumentException
     */
    void updateFellowship(Fellowship fellowship);

    /**
     * It fetches the Fellowships from dal.
     * @return List<Fellowship>, if the dal layer has not such type of element, it is an empty list.
     */
    List<Fellowship> listFellowships();

    /**
     * It fetches for a fellowship by its id.
     * @param fellowshipId it cannot be 0. Throws IllegalArgumentException.
     * @return a Fellowship, which can be null, if it cannot be found in DB.
     */
    Fellowship findById(long fellowshipId);
}
