package hu.elte.progtech.spynet.presentation.character;

/**
 * This class is a pojo class, which responsibility to show a Character by a controller.
 */
public class CharacterData {

    private long characterId;
    private String name;
    private int armySize;
    private String status;
    private String house;
    private String characterUrl;

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

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getCharacterUrl() {
        return characterUrl;
    }

    public void setCharacterUrl(String characterUrl) {
        this.characterUrl = characterUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CharacterData that = (CharacterData) o;

        if (armySize != that.armySize) return false;
        if (!name.equals(that.name)) return false;
        if (!status.equals(that.status)) return false;
        return house.equals(that.house);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + armySize;
        result = 31 * result + status.hashCode();
        result = 31 * result + house.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CharacterData{" +
                "characterId=" + characterId +
                ", name='" + name + '\'' +
                ", armySize=" + armySize +
                ", status='" + status + '\'' +
                ", house='" + house + '\'' +
                ", characterUrl='" + characterUrl + '\'' +
                '}';
    }
}
