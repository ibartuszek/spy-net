package hu.elte.progtech.spynet.domain.character;

import java.util.List;

/**
 * It is the main connection to domain characters.
 */
public interface CharacterService {

    /**
     * It fetches for a character by its id.
     * @param characterId it cannot be 0. Throws IllegalArgumentException.
     * @return a Character, which can be null, if it cannot be found in DB.
     */
    Character findById(long characterId);

    /**
     * It fetches the Characters from dal.
     * @return List<Character>, if the dal layer has not such type of element, it is an empty list.
     */
    List<Character> listCharacters();

    /**
     * It fetches the characters from dal and make a filtered list where the characters' name
     * matches the input string. (partially or entirely)
     * @param name if it is a null it throws IllegalArgumentException
     * @return a filtered List<Character>
     *     if there is not matches character
     *     or the dal layer has not such type of element at all, it is an empty list
     */
    List<Character> listCharactersByName(String name);

    /**
     * It save the character into the db.
     * @param character if it is a null it throws IllegalArgumentException
     */
    void saveCharacter(Character character);

    /**
     * It modifies the character and save changes into the db.
     * @param character if it is a null it throws IllegalArgumentException
     */
    void updateCharacter(Character character);

}
