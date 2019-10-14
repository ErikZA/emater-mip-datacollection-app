package br.edu.utfpr.cp.emater.midmipsystem.service.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.MacroRegionRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MacroRegionServiceTest {

    @MockBean
    private MacroRegionRepository macroRegionRepository;

    @Autowired
    private MacroRegionService macroRegionService;

    private MacroRegion macroRegion1, macroRegion2, macroRegion3, macroRegion4;

    @Before
    public void SetUp(){

        this.macroRegion1 = MacroRegion.builder().id((long) 1).name("Macro Leste").build();
        this.macroRegion2 = MacroRegion.builder().id((long) 2).name("Polo Oeste").build();
        this.macroRegion3 = MacroRegion.builder().id((long) 3).name("Centro Sul").build();
        this.macroRegion4 = MacroRegion.builder().id((long) 4).name("Costa Norte").build();

        List<MacroRegion> macroRegions = asList(this.macroRegion1,this.macroRegion2,this.macroRegion3);
        BDDMockito.when(this.macroRegionRepository.findAll())
                .thenReturn(macroRegions);
    }

    @Test
    public void readAllMacroRegionServiceTest() {
        assertThat(this.macroRegionService.readAll()).doesNotContainNull().isNotEmpty()
                .containsExactlyInAnyOrder(this.macroRegion1, this.macroRegion2, this.macroRegion3).doesNotContain(this.macroRegion4);
    }

    @Test
    public void readByIdMacroRegionServiceTest() throws EntityNotFoundException {
        when(this.macroRegionRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.macroRegion1));
     assertThat(this.macroRegionService.readById((long) 1)).isEqualTo(this.macroRegion1);
    }

    @Test(expected = EntityNotFoundException .class)
    public void readByIdMacroRegionServiceEntityNotFoundExceptionTest() throws EntityNotFoundException {
        when(this.macroRegionRepository.findById((long) 4))
                .thenReturn(java.util.Optional.ofNullable(null));
        this.macroRegionService.readById((long) 4);
        Assertions.fail("EntityNotFoundException it is not throws");
    }

    @Test
    public void createMacroRegionServiceTest() throws AnyPersistenceException, EntityAlreadyExistsException, EntityNotFoundException {
        when(this.macroRegionRepository.findById((long) 4))
                .thenReturn(java.util.Optional.ofNullable(null));
        when(this.macroRegionRepository.save(this.macroRegion4)).thenReturn(this.macroRegion4);

        try {
            this.macroRegionService.readById(this.macroRegion4.getId());
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){    }

        this.macroRegionService.create(this.macroRegion4);

        when(this.macroRegionRepository.findById((long) 4))
                .thenReturn(java.util.Optional.ofNullable(this.macroRegion4));

        MacroRegion macroRegion = this.macroRegionService.readById((long) 4);
        assertThat(macroRegion.getName()).isEqualTo("Costa Norte");
    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void createMacroRegionServiceEntityAlreadyExistsExceptionTest() throws AnyPersistenceException, EntityAlreadyExistsException {

        this.macroRegionService.create(this.macroRegion1);

        Assertions.fail("EntityAlreadyExistsException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void createMacroRegionServiceAnyPersistenceExceptionTest() throws AnyPersistenceException, EntityAlreadyExistsException {
        when(this.macroRegionRepository.findById((long) 4))
                .thenReturn(java.util.Optional.ofNullable(null));
        doThrow(IllegalArgumentException.class).when(this.macroRegionRepository).save(any(MacroRegion.class));
        try {
            this.macroRegionService.readById(this.macroRegion4.getId());
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){    }

        this.macroRegionService.create(this.macroRegion4);

        Assertions.fail("AnyPersistenceException it is not throws");
    }

    @Test //A macroRegião atual não é removida antes da vereficar se ha algum registro corresponddente
    public void updateMacroRegionServiceTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {

        when(this.macroRegionRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.macroRegion1));
        when(this.macroRegionRepository.saveAndFlush(any(MacroRegion.class)))
                .thenReturn(this.macroRegion1);

        MacroRegion macroRegion = this.macroRegionService.readById((long) 1);
        assertThat(macroRegion.getName()).isEqualTo("Macro Leste");

        macroRegion.setName("Update Teste");

        this.macroRegionService.update(macroRegion);

        when(this.macroRegionRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(macroRegion));

        this.macroRegion1 = this.macroRegionService.readById((long) 1);

        assertThat(this.macroRegion1.getName()).isEqualTo("Update Teste");
    }


    @Test(expected = EntityNotFoundException.class)
    public void updateMacroRegionServiceEntityNotFoundExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {

        when(this.macroRegionRepository.findById((long)4))
                .thenReturn(java.util.Optional.ofNullable(null));
        when(this.macroRegionRepository.saveAndFlush(any(MacroRegion.class)))
                .thenReturn(this.macroRegion1);

        this.macroRegion4.setName("Test Update");
        this.macroRegionService.update(this.macroRegion4);

        Assertions.fail("EntityNotFoundException it is not throws");
}

    @Test (expected = AnyPersistenceException.class) //A macroRegião atual não é removida antes da vereficar se ha algum registro corresponddente
    public void updateMacroRegionServiceAnyPersistenceExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {

        when(this.macroRegionRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.macroRegion1));
        doThrow(IllegalArgumentException.class).when(this.macroRegionRepository).saveAndFlush(any(MacroRegion.class));

        MacroRegion macroRegion = this.macroRegionService.readById((long) 1);
        assertThat(macroRegion.getName()).isEqualTo("Macro Leste");

        macroRegion.setName("Update Teste");
        macroRegion.setCreatedAt((long) 1);
        macroRegion.setLastModified((long) 1);

        this.macroRegionService.update(macroRegion);

        Assertions.fail("AnyPersistenceException it is not throws");
    }


    @Test(expected = EntityAlreadyExistsException.class)
    public void updateMacroRegionServiceEntityAlreadyExistsExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        MacroRegion macroRegionCopy = this.macroRegion1;

        List<MacroRegion> macroRegions = asList(this.macroRegion1,this.macroRegion2,this.macroRegion3,macroRegionCopy);
        BDDMockito.when(this.macroRegionRepository.findAll())
                .thenReturn(macroRegions);

        when(this.macroRegionRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.macroRegion1));

        MacroRegion macroRegion = this.macroRegionService.readById((long) 1);
        assertThat(macroRegion.getName()).isEqualTo("Macro Leste");

        macroRegion.setName("Update Teste");

        this.macroRegionService.update(macroRegion);

        Assertions.fail("EntityAlreadyExistsException it is not throws");
    }

    @Test
    public void deleteMacroRegionServiceTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.macroRegionRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.macroRegion1));
        doNothing().when(this.macroRegionRepository).delete(any(MacroRegion.class));

        this.macroRegionService.delete((long) 1);

        when(this.macroRegionRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(null));

        try {
            this.macroRegionService.readById((long) 1);
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){
            assertThat(e.getClass()).isEqualTo(EntityNotFoundException.class);
        }
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteMacroRegionServiceEntityNotFoundExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.macroRegionRepository.findById((long) 12))
                .thenReturn(java.util.Optional.ofNullable(null));

        this.macroRegionService.delete((long) 12);
            Assertions.fail("EntityNotFoundException it is not throws");

    }

    @Test (expected = AnyPersistenceException.class)
    public void deleteMacroRegionServiceAnyPersistenceExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.macroRegionRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.macroRegion1));
        doThrow(IllegalArgumentException.class).when(this.macroRegionRepository).delete(any(MacroRegion.class));

        this.macroRegionService.delete((long) 1);

        when(this.macroRegionRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(null));

         Assertions.fail("AnyPersistenceException it is not throws");
    }

    @Test (expected = EntityInUseException.class)
    public void deleteMacroRegionServiceEntityInUseExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.macroRegionRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.macroRegion1));
        doThrow(DataIntegrityViolationException.class).when(this.macroRegionRepository).delete(any(MacroRegion.class));

        this.macroRegionService.delete((long) 1);

        when(this.macroRegionRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(null));

        Assertions.fail("EntityInUseException it is not throws");
    }
}