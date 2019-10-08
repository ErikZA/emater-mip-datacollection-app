package br.edu.utfpr.cp.emater.midmipsystem.service.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import java.util.List;

import br.edu.utfpr.cp.emater.midmipsystem.repository.base.MacroRegionRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.SupervisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class MacroRegionService {

    private final MacroRegionRepository macroRegionRepository;

    @Autowired
    public MacroRegionService(MacroRegionRepository macroRegionRepository) {
        this.macroRegionRepository = macroRegionRepository;
    }

    public List<MacroRegion> readAll() {
        return List.copyOf(macroRegionRepository.findAll());
    }

    public MacroRegion readById(Long anId) throws EntityNotFoundException {
        return macroRegionRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
    }

    public void create(MacroRegion aMacroRegion) throws EntityAlreadyExistsException, AnyPersistenceException {

        if (macroRegionRepository.findAll().stream().anyMatch(currentMR -> currentMR.equals(aMacroRegion))) {
            throw new EntityAlreadyExistsException();
        }

        try {
            macroRegionRepository.save(aMacroRegion);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }

    public void update(MacroRegion aMacroRegion) throws EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {

        MacroRegion existentEntity = macroRegionRepository.findById(aMacroRegion.getId()).orElseThrow(EntityNotFoundException::new);

        if (macroRegionRepository.findAll().stream().anyMatch(currentMR -> currentMR.equals(aMacroRegion)))
            throw new EntityAlreadyExistsException();
                
        try {
            existentEntity.setName(aMacroRegion.getName());
            
            macroRegionRepository.saveAndFlush(existentEntity);

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }

    public void delete(Long anId) throws EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        MacroRegion existentEntity = macroRegionRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
        
        try {
            macroRegionRepository.delete(existentEntity);
            
        } catch (DataIntegrityViolationException cve) {
            throw new EntityInUseException();
            
        } catch (Exception e) {
            throw new AnyPersistenceException();
        }
    }
}
