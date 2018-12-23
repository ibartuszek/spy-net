package hu.elte.progtech.spynet.dal.character;

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
    public void save(CharacterDto character) {
        characterRepository.save(new CharacterEntity(character, getHouseEntity(character)));
    }

    @Transactional
    public void updateCharacter(CharacterDto character) {
        CharacterEntity characterEntity;
        HouseEntity houseEntity = getHouseEntity(character);
        Optional<CharacterEntity> optionalCharacterEntity = characterRepository.findById(character.getCharacterId());
        if (optionalCharacterEntity.isPresent()) {
            characterEntity = optionalCharacterEntity.get();
            modifyCharacterEntity(character, characterEntity, houseEntity);
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

}
