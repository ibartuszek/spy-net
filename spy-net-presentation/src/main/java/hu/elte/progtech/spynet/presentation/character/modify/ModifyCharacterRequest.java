package hu.elte.progtech.spynet.presentation.character.modify;

/**
 * It is a simple data holder class, which responsibility to point a character by its id.
 */
public class ModifyCharacterRequest {
    long characterId;

    public long getCharacterId() {
        return characterId;
    }

    public void setCharacterId(long characterId) {
        this.characterId = characterId;
    }
}
