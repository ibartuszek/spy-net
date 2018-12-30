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

@Component
public class CharacterDao {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private HouseRepository houseRepository;

    @PersistenceContext
    private EntityManager entityManager;

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

    @Transactional
    public void saveCharacter(CharacterDto characterDto) {
        Preconditions.checkArgument(characterDto != null, "characterDto cannot be null!");
        characterRepository.save(new CharacterEntity(characterDto, getHouseEntity(characterDto)));
    }

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
