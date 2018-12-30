package hu.elte.progtech.spynet.presentation.house;

import java.util.Arrays;

public class HouseData {
    private long houseId;
    private String name;
    private String slogan;
    private boolean hasCrest;
    private byte[] crest;
    private String crestUrl;

    public long getHouseId() {
        return houseId;
    }

    public void setHouseId(long houseId) {
        this.houseId = houseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public boolean isHasCrest() {
        return hasCrest;
    }

    public void setHasCrest(boolean hasCrest) {
        this.hasCrest = hasCrest;
    }

    public byte[] getCrest() {
        return crest;
    }

    public void setCrest(byte[] crest) {
        this.crest = crest;
    }

    public String getCrestUrl() {
        return crestUrl;
    }

    public void setCrestUrl(String crestUrl) {
        this.crestUrl = crestUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HouseData houseData = (HouseData) o;

        return houseId == houseData.houseId;
    }

    @Override
    public int hashCode() {
        return (int) (houseId ^ (houseId >>> 32));
    }

    @Override
    public String toString() {
        return "HouseData{" +
                "houseId=" + houseId +
                ", name='" + name + '\'' +
                ", slogan='" + slogan + '\'' +
                ", hasCrest=" + hasCrest +
                ", crest=" + Arrays.toString(crest) +
                ", crestUrl='" + crestUrl + '\'' +
                '}';
    }
}
