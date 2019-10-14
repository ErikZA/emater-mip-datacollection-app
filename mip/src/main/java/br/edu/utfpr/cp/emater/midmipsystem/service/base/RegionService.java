package br.edu.utfpr.cp.emater.midmipsystem.service.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.RegionRepository;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.ICRUDService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        return cityService.readAll();
    }
    
    public MacroRegion readMacroRegionById(Long id) {
        try {
            return this.macroRegionService.readById(id);
            
        } catch (EntityNotFoundException e) {
            return MacroRegion.builder().build();//// Test falha pois - Ha o lançamento de um NullPointe pois o contrutor precisa de um nome para ser instanciado.
        }
    }

    public List<City> readAllCitiesWithoutRegion() {
        var allCities = this.cityService.readAll();
        var citiesWithinARegion = this.readAll().stream().flatMap(currentRegion -> currentRegion.getCities().stream()).collect(Collectors.toList());

        var citiesWithoutRegion = new ArrayList<City>(allCities);
        citiesWithoutRegion.removeAll(citiesWithinARegion);

        return citiesWithoutRegion;
    }

    public Region readById(Long anId) throws EntityNotFoundException {
        return regionRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
    }

    private Set<City> retrieveCities(Set<Long> ids) throws EntityNotFoundException {
        var result = new HashSet<City>();

        for (Long id : ids) {
            result.add(cityService.readById(id));
        }

        return result;
    }

    public void create(Region aRegion) throws EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {

        var theMacroRegion = macroRegionService.readById(aRegion.getMacroRegionId());
        var theCities = retrieveCities(aRegion.getCities().stream().map(City::getId).collect(Collectors.toSet()));

        var newRegion = Region.builder().name(aRegion.getName()).macroRegion(theMacroRegion).build();
        newRegion.setCities(theCities);

        if (regionRepository.findAll().stream().anyMatch(currentRegion -> currentRegion.equals(newRegion))) {
            throw new EntityAlreadyExistsException();
        }

        try {
            regionRepository.save(newRegion);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }

    public void update(Region aRegion) throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {

        Region existentRegion = regionRepository.findById(aRegion.getId()).orElseThrow(EntityNotFoundException::new);
        
        var allRegionsWithoutExistentRegion = new ArrayList<Region>(regionRepository.findAll());
        allRegionsWithoutExistentRegion.remove(existentRegion);
        
        if (allRegionsWithoutExistentRegion.stream().anyMatch(currentRegion -> currentRegion.equals(aRegion))) {
            throw new EntityAlreadyExistsException();
        }

        var theMacroRegion = macroRegionService.readById(aRegion.getMacroRegionId());
        var theCities = retrieveCities(aRegion.getCities().stream().map(City::getId).collect(Collectors.toSet()));        
        
        try {
            existentRegion.setName(aRegion.getName());
            existentRegion.setMacroRegion(aRegion.getMacroRegion());
            existentRegion.setCities(aRegion.getCities());

            regionRepository.saveAndFlush(existentRegion);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }

    public void delete(Long anId) throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        Region existentRegion = regionRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
        
        try {
            regionRepository.delete(existentRegion);
            
        } catch (DataIntegrityViolationException cve) {
            throw new EntityInUseException();
            
        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }
}
