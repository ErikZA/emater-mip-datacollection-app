package br.edu.utfpr.cp.emater.midmipsystem.service.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Farmer;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Supervisor;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.FarmerRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.FieldRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.SupervisorRepository;
import br.edu.utfpr.cp.emater.midmipsystem.service.ICRUDService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class FieldService implements ICRUDService<Field> {

    private final FieldRepository fieldRepository;
    private final CityService cityService;
    private final FarmerService farmerService;
    private final SupervisorService supervisorService;

    @Autowired
    public FieldService(FieldRepository aFieldRepository, CityService aCityService, FarmerService aFarmerService, SupervisorService aSupervisorService) {
        this.fieldRepository = aFieldRepository;
        this.cityService = aCityService;
        this.farmerService = aFarmerService;
        this.supervisorService = aSupervisorService;
    }

    @Override
    public List<Field> readAll() {
        return List.copyOf(fieldRepository.findAll());
    }
    
    public List<City> readAllCities() {
        return cityService.readAll();
    }
    
    public List<Farmer> readAllFarmers() {
        return farmerService.readAll();
    }
    
    public List<Supervisor> readAllSupervisors() {
        return supervisorService.readAll();
    }
    
//    public List<Region> readAllRegions() {
//        return this.regionService.readAll();
//    }
//
//    @Override
//    public Supervisor readById(Long anId) throws EntityNotFoundException {
//        return supervisorRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
//    }
    private Set<Supervisor> retrieveSupervisors (Set<Supervisor> someSupervisors) throws EntityNotFoundException {
        var result = new HashSet<Supervisor>();
        
        for (Supervisor currentSupervisor: someSupervisors) 
            result.add(supervisorService.readById(currentSupervisor.getId()));

        return result;
    }

    public void create(Field aField) throws EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {

        if (fieldRepository.findAll().stream().anyMatch(currentField -> currentField.equals(aField))) {
            throw new EntityAlreadyExistsException();
        }
        
        var theCity = cityService.readById(aField.getCityId());
        var theFarmer = farmerService.readById(aField.getFarmerId());
        var someSupervisors = this.retrieveSupervisors(aField.getSupervisors());

        try {
            aField.setCity(theCity);
            aField.setFarmer(theFarmer);
            aField.setSupervisors(someSupervisors);
            
            fieldRepository.save(aField);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }
//
//    public void update(Supervisor aSupervisor) throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException { 
//        
//        var existentSupervisor = supervisorRepository.findById(aSupervisor.getId()).orElseThrow(EntityNotFoundException::new);
//        
//        var allSupervisorsWithoutExistentSupervisor = new ArrayList<Supervisor>(supervisorRepository.findAll());
//        allSupervisorsWithoutExistentSupervisor.remove(existentSupervisor);
//
//        if (allSupervisorsWithoutExistentSupervisor.stream().anyMatch(currentSupervisor -> currentSupervisor.equals(aSupervisor)))
//            throw new EntityAlreadyExistsException();
//                
//        try {
//            existentSupervisor.setName(aSupervisor.getName());
//            existentSupervisor.setEmail(aSupervisor.getEmail());
//            
//            var theRegion = regionService.readById(aSupervisor.getRegionId());
//            existentSupervisor.setRegion(theRegion);
//            
//            supervisorRepository.saveAndFlush(existentSupervisor);
//
//        } catch (Exception e) {
//            throw new AnyPersistenceException();
//        }
//    }
//
//    public void delete(Long anId) throws EntityNotFoundException, EntityInUseException, AnyPersistenceException {
//        
//        var existentSupervisor = supervisorRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
//        
//        try {
//            supervisorRepository.delete(existentSupervisor);
//            
//        } catch (DataIntegrityViolationException cve) {
//            throw new EntityInUseException();
//            
//        } catch (Exception e) {
//            throw new AnyPersistenceException();
//        }
//    }


    @Override
    public Field readById(Long anId) throws EntityNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Field entity) throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Long anId) throws EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
