package hu.elte.progtech.spynet.presentation.fellowship.modify;

/**
 * It is a simple data holder class, which responsibility to point a fellowship by its id.
 */
public class ModifyFellowshipRequest {

    private long fellowshipId;

    public long getFellowshipId() {
        return fellowshipId;
    }

    public void setFellowshipId(long fellowshipId) {
        this.fellowshipId = fellowshipId;
    }
}
