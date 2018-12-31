package hu.elte.progtech.spynet.dal.character;

import hu.elte.progtech.spynet.dal.house.HouseDto;
import hu.elte.progtech.spynet.dal.house.HouseEntity;

/**
 * Data transfer object between Data access layer and domain layer.
 */
public class CharacterDto {

    private long characterId;
    private String name;
    private int armySize;
    private String status;
    private HouseDto house;

    public CharacterDto() {}

    /**
     * Create a characterDto from characterEntity.
     * @param characterEntity If it is null it throws a NullPointerException.
     */
    public CharacterDto(CharacterEntity characterEntity) {
        this.characterId = characterEntity.getCharacterId();
        this.name = characterEntity.getName();
        this.armySize = characterEntity.getArmySize();
        this.status = characterEntity.getStatus();
        HouseDto houseDto = null;
        HouseEntity houseEntity = characterEntity.getHouse();
        if (houseEntity != null) {
            houseDto = new HouseDto(houseEntity);
        }
        this.house = houseDto;
    }

    public long getCharacterId() {
        return characterId;
    }

    public void setCharacterId(long characterId) {
        this.characterId = characterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArmySize() {
        return armySize;
    }

    public void setArmySize(int armySize) {
        this.armySize = armySize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HouseDto getHouse() {
        return house;
    }

    public void setHouse(HouseDto house) {
        this.house = house;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CharacterDto that = (CharacterDto) o;

        return characterId == that.characterId;
    }

    @Override
    public int hashCode() {
        return (int) (characterId ^ (characterId >>> 32));
    }

    @Override
    public String toString() {
        return "CharacterDto{" +
                "characterId=" + characterId +
                ", name='" + name + '\'' +
                ", armySize=" + armySize +
                ", status='" + status + '\'' +
                ", house=" + house +
                '}';
    }
}
