package hu.elte.progtech.spynet.domain.character;

import hu.elte.progtech.spynet.domain.house.House;

public class Character {

    private long characterId;
    private String name;
    private int armySize;
    private CharacterStatus status;
    private House house;

    public static Character createCharacter(String name, int armySize, House house) {
        Character character = new Character();
        character.setName(name);
        character.setArmySize(armySize);
        character.setStatus(CharacterStatus.ALIVE);
        character.setHouse(house);
        return character;
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

    public CharacterStatus getStatus() {
        return status;
    }

    public void setStatus(CharacterStatus status) {
        this.status = status;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Character character = (Character) o;

        if (armySize != character.armySize) return false;
        if (!name.equals(character.name)) return false;
        if (status != character.status) return false;
        return house != null ? house.equals(character.house) : character.house == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + armySize;
        result = 31 * result + status.hashCode();
        result = 31 * result + (house != null ? house.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Character{" +
                "characterId=" + characterId +
                ", name='" + name + '\'' +
                ", armySize=" + armySize +
                ", status=" + status +
                ", house=" + house +
                '}';
    }
}
