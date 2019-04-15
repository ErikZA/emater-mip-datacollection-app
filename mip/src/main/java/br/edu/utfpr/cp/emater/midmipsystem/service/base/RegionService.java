package br.edu.utfpr.cp.emater.midmipsystem.service.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.RegionRepository;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.service.ICRUDService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegionService implements ICRUDService<Region> {

    private final RegionRepository regionRepository;
    private final MacroRegionService macroRegionService;
    private final CityService cityService;

    @Autowired
    public RegionService(RegionRepository aRegionRepository, MacroRegionService aMacroRegionService, CityService aCityService) {
        this.regionRepository = aRegionRepository;
        this.macroRegionService = aMacroRegionService;
        this.cityService = aCityService;
    }

    @Override
    public List<Region> readAll() {
        return List.copyOf(regionRepository.findAll());
    }
    
    public List<MacroRegion> readAllMacroRegions() {
        return this.macroRegionService.readAll();
    }
    
    public List<City> readAllCities() {
        var allCities = this.cityService.readAll();
        var citiesWithinARegion = this.readAll().stream().flatMap(currentRegion -> currentRegion.getCities().stream()).collect(Collectors.toList());
        
        var citiesWithoutRegion = new ArrayList<City> (allCities);
        citiesWithoutRegion.removeAll(citiesWithinARegion);
        
        return citiesWithoutRegion;
    }

//    public MacroRegionDTO readById(Long id) throws EntityNotFoundException {
//        MacroRegion entity = macroRegionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
//
//        return this.convertToDTO(entity);
//    }
//
//    public void create(MacroRegionDTO aMacroRegionDTO) throws EntityAlreadyExistsException, AnyPersistenceException {
//
//        MacroRegion persistentEntity = this.prepareEntityForPersistence(aMacroRegionDTO);
//
//        if (macroRegionRepository.findAll().stream().anyMatch(currentMR -> currentMR.equals(persistentEntity))) {
//            throw new EntityAlreadyExistsException();
//        }
//
//        try {
//            macroRegionRepository.save(persistentEntity);
//
//        } catch (Exception e) {
//            throw new AnyPersistenceException();
//        }
//    }
//
//    private MacroRegion prepareEntityForPersistence(MacroRegionDTO aDTO) {
//        return MacroRegion.builder().name(aDTO.getName()).build();
//    }
//
//
//    private void updateMacroRegionAttributes(MacroRegion original, MacroRegionDTO updated) {
//        original.setName(updated.getName());
//    }
//
//    public void update(MacroRegionDTO aMacroRegionDTO) throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
//
//        MacroRegion existentEntity = macroRegionRepository.findById(aMacroRegionDTO.getId()).orElseThrow(EntityNotFoundException::new);
//        MacroRegion aNewEntityJustForEqualsComparison = MacroRegion.builder().name(aMacroRegionDTO.getName()).build();
//
//        if (macroRegionRepository.findAll().stream().anyMatch(currentMR -> currentMR.equals(aNewEntityJustForEqualsComparison)))
//            throw new EntityAlreadyExistsException();
//        
//        this.updateMacroRegionAttributes(existentEntity, aMacroRegionDTO);
//        
//        try {
//            macroRegionRepository.saveAndFlush(existentEntity);
//
//        } catch (Exception e) {
//            throw new AnyPersistenceException();
//        }
//    }
//
//    public void delete(Long anId) throws EntityNotFoundException {
//        MacroRegion existentEntity = macroRegionRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
//        
//        try {
//            macroRegionRepository.delete(existentEntity);
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}