package hu.elte.progtech.spynet.dal.character;

import com.google.common.base.Preconditions;
import hu.elte.progtech.spynet.dal.house.HouseDto;
import hu.elte.progtech.spynet.dal.house.HouseEntity;
import hu.elte.progtech.spynet.dal.house.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * The class is a singleton class of which responsibility is to make contact with the DB.
 * It can save, list, modify Characters from DataBase.
 * It uses CharacterRepository, HouseRepository which are Crudrepositories implemented by Spring.
 */
@Component
public class CharacterDao {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private HouseRepository houseRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * It fethes characters from DB, transform to CharacterDto class and give back as an ArrayList.
     * If there is not element in DB, the list will be an empty list.
     * @return List<CharacterDto>
     */
    public List<CharacterDto> listCharacters(){
        List<CharacterEntity> characterEntityList = (List<CharacterEntity>) characterRepository.findAll();
        return convertToCharacterDtoList(characterEntityList);
    }

    private List<CharacterDto> convertToCharacterDtoList(List<CharacterEntity> characterEntityList) {
        List<CharacterDto> characterDtoList = new ArrayList<>();
        for (CharacterEntity characterEntity : characterEntityList) {
            characterDtoList.add(new CharacterDto(characterEntity));
        }
        return characterDtoList;
    }

    /**
     * It transforms the CharacterDto to CharacterEntity, than save it into DB.
     * @param characterDto cannot be null! It throws IllegalArgument Exception.
     */
    @Transactional
    public void saveCharacter(CharacterDto characterDto) {
        Preconditions.checkArgument(characterDto != null, "characterDto cannot be null!");
        characterRepository.save(new CharacterEntity(characterDto, getHouseEntity(characterDto)));
    }

    /**
     * It fetches the CharacterEntity from DB on the basis of the CharacterDto, than modify its parameter
     * and save back to the DB.
     * @param characterDto cannot be null! It throws IllegalArgument Exception.
     */
    @Transactional
    public void updateCharacter(CharacterDto characterDto) {
        Preconditions.checkArgument(characterDto != null, "characterDto cannot be null!");
        CharacterEntity characterEntity;
        HouseEntity houseEntity = getHouseEntity(characterDto);
        Optional<CharacterEntity> optionalCharacterEntity = characterRepository.findById(characterDto.getCharacterId());
        if (optionalCharacterEntity.isPresent()) {
            characterEntity = optionalCharacterEntity.get();
            modifyCharacterEntity(characterDto, characterEntity, houseEntity);
        } else {
            throw new IllegalArgumentException("CharacterEntity cannot be found in db with the following id: "
                    + characterDto.getCharacterId());
        }
    }

    private HouseEntity getHouseEntity(CharacterDto characterDto) {
        HouseEntity houseEntity = null;
        HouseDto houseDto = characterDto.getHouse();
        // If something goes wrong the houseEntity will be NULL
        if (houseDto != null) {
            Optional<HouseEntity> optionalHouseEntity = houseRepository.findById(houseDto.getHouseId());
            if (optionalHouseEntity.isPresent()) {
                houseEntity = optionalHouseEntity.get();
            }
        }
        return houseEntity;
    }

    private void modifyCharacterEntity(CharacterDto character, CharacterEntity characterEntity, HouseEntity houseEntity) {
        entityManager.detach(characterEntity);
        characterEntity.setArmySize(character.getArmySize());
        characterEntity.setStatus(character.getStatus());
        characterEntity.setHouse(houseEntity);
        characterRepository.save(characterEntity);
    }

    /**
     * It fetches a CharacterEntity and transform it to CharacterDto.
     * @param characterId cannot be zero! It throws IllegalArgument Exception.
     * @return CharacterDto, if it cannot be found it is null.
     */
    public CharacterDto findById(long characterId) {
        Preconditions.checkArgument(characterId != 0, "characterId cannot be 0!");
        Optional<CharacterEntity> optionalCharacterEntity = characterRepository.findById(characterId);
        CharacterDto characterDto = null;
        if (optionalCharacterEntity.isPresent()) {
            characterDto = new CharacterDto(optionalCharacterEntity.get());
        }
        return characterDto;
    }
}
