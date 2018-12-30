package hu.elte.progtech.spynet.domain.house;

import java.util.Arrays;

public class House {

    private long houseId;
    private String name;
    private String slogan;
    private byte[] crest;

    public static House createHouse(String name, String slogan) {
        House house = new House();
        house.setName(name);
        house.setSlogan(slogan);
        return house;
    }

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

    public byte[] getCrest() {
        return crest;
    }

    public void setCrest(byte[] crest) {
        this.crest = crest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        House house = (House) o;

        return houseId == house.houseId;
    }

    @Override
    public int hashCode() {
        return (int) (houseId ^ (houseId >>> 32));
    }

    @Override
    public String toString() {
        return "House{" +
                "houseId=" + houseId +
                ", name='" + name + '\'' +
                ", slogan='" + slogan + '\'' +
                ", crest=" + Arrays.toString(crest) +
                '}';
    }
}
