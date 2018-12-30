package hu.elte.progtech.spynet.dal.house;

import java.util.Arrays;

public class HouseDto {

    private long houseId;
    private String name;
    private String slogan;
    private byte[] crest;

    public HouseDto() {}

    public HouseDto(HouseEntity houseEntity) {
        this.houseId = houseEntity.getHouseId();
        this.name = houseEntity.getName();
        this.slogan = houseEntity.getSlogan();
        this.crest = houseEntity.getCrest();
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

        HouseDto houseDto = (HouseDto) o;

        return houseId == houseDto.houseId;
    }

    @Override
    public int hashCode() {
        return (int) (houseId ^ (houseId >>> 32));
    }

    @Override
    public String toString() {
        return "HouseDto{" +
                "houseId=" + houseId +
                ", name='" + name + '\'' +
                ", slogan='" + slogan + '\'' +
                ", crest=" + Arrays.toString(crest) +
                '}';
    }
}
