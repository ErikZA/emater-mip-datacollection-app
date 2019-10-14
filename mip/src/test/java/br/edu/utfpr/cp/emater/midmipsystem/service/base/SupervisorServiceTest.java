package br.edu.utfpr.cp.emater.midmipsystem.service.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Supervisor;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.RegionRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.SupervisorRepository;
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
import static org.junit.Assert.*;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SupervisorServiceTest {

    @MockBean
    private RegionRepository regionRepository;
    @MockBean
    private SupervisorRepository supervisorRepository;

    @Autowired
    private SupervisorService supervisorService;

    private Region region1, region2, region3, region4;
    private Supervisor supervisor1, supervisor2, supervisor3, supervisor4;

    @Before
    public void setUp(){

        this.region1 = Region.builder().id((long) 11).name("Capital Norte")
                .macroRegion(MacroRegion.builder().id((long) 1).name("Macro Leste").build()).build();
        this.region2 = Region.builder().id((long) 12).name("Interior Norte")
                .macroRegion(MacroRegion.builder().id((long) 2).name("Polo Oeste").build()).build();
        this.region3 = Region.builder().id((long) 13).name("Centro Oeste")
                .macroRegion(MacroRegion.builder().id((long) 3).name("Centro Sul").build()).build();
        this.region4 = Region.builder().id((long) 14)
                .name("Capital Sul").macroRegion(MacroRegion.builder().id((long) 4).name("Costa Norte").build()).build();

        this.supervisor1 = Supervisor.builder().id((long) 21).name("Marcos").email("marcos@gmail.com")
                .region(this.region1).build();
        this.supervisor2 = Supervisor.builder().id((long) 22).name("Henrique").email("henrique@gmail.com")
                .region(this.region2).build();
        this.supervisor3 = Supervisor.builder().id((long) 23).name("Antonio").email("antonio@gmail.com")
                .region(this.region3).build();
        this.supervisor4 = Supervisor.builder().id((long) 24).name("Jorge").email("jorge@gmail.com")
                .region(this.region4).build();

        List<Region> regions = asList(this.region1,this.region2,this.region3);
        BDDMockito.when(this.regionRepository.findAll())
                .thenReturn(regions);

        List<Supervisor> supervisors = asList(this.supervisor1,this.supervisor2,this.supervisor3);
        BDDMockito.when(this.supervisorRepository.findAll())
                .thenReturn(supervisors);
    }


    @Test
    public void readAllSupervisorServiceTest() {
        assertThat(this.supervisorService.readAll()).containsExactly(this.supervisor1,this.supervisor2,this.supervisor3)
                .doesNotContainNull().doesNotContain(this.supervisor4).isNotEmpty();
    }

    @Test
    public void readAllRegionsSupervisorServiceTest() {
        assertThat(this.supervisorService.readAllRegions()).doesNotContain(this.region4).isNotEmpty().doesNotContainNull()
                .containsExactlyInAnyOrder(this.region1,this.region2,this.region3);
    }

    @Test
    public void readByIdSupervisorServiceTest() throws EntityNotFoundException {
        when(this.supervisorRepository.findById((long) 21))
                .thenReturn(java.util.Optional.ofNullable(this.supervisor1));
        assertThat(this.supervisorService.readById((long) 21)).isEqualTo(this.supervisor1);
    }

    @Test (expected = EntityNotFoundException.class)
    public void readByIdSupervisorServiceEntityNotFoundExceptionTest() throws EntityNotFoundException {
        when(this.supervisorRepository.findById((long) 121))
                .thenReturn(java.util.Optional.ofNullable(null));
        this.supervisorService.readById((long) 121);
        Assertions.fail("EntityNotFoundException it is not throws");
    }

    @Test
    public void createSupervisorServiceTest() throws AnyPersistenceException, EntityAlreadyExistsException, EntityNotFoundException {
        when(this.supervisorRepository.findById((long) 4))
                .thenReturn(java.util.Optional.ofNullable(null));
        when(this.supervisorRepository.save(this.supervisor4)).thenReturn(this.supervisor4);

        try {
            this.supervisorService.readById(this.supervisor4.getId());
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){    }

        this.supervisorService.create(this.supervisor4);

        when(this.supervisorRepository.findById((long) 4))
                .thenReturn(java.util.Optional.ofNullable(this.supervisor4));

        Supervisor supervisor = this.supervisorService.readById((long) 4);
        assertThat(supervisor.getName()).isEqualTo("Jorge");
        assertThat(supervisor.getEmail()).isEqualTo("jorge@gmail.com");
    }

    @Test (expected = AnyPersistenceException.class)
    public void createSupervisorServiceAnyPersistenceExceptionTest() throws AnyPersistenceException, EntityAlreadyExistsException{
        when(this.supervisorRepository.findById((long) 4))
                .thenReturn(java.util.Optional.ofNullable(null));
        doThrow(IllegalArgumentException.class).when(this.supervisorRepository)
                .save(this.supervisor4);

        try {
            this.supervisorService.readById(this.supervisor4.getId());
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){    }

        this.supervisorService.create(this.supervisor4);
        Assertions.fail("AnyPersistenceException it is not throws");
    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void createSupervisorServiceEntityAlreadyExistsExceptionTest () throws AnyPersistenceException, EntityAlreadyExistsException{
        this.supervisorService.create(this.supervisor1);
        Assertions.fail("EntityAlreadyExistsException it is not throws");
    }


    @Test
    public void updateSupervisorServiceTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.supervisorRepository.findById((long)21))
                .thenReturn(java.util.Optional.ofNullable(this.supervisor1));
        when(this.regionRepository.findById((long)11))
                .thenReturn(java.util.Optional.ofNullable(this.region1));
        when(this.regionRepository.findById((long)12))
                .thenReturn(java.util.Optional.ofNullable(this.region2));
        when(this.supervisorRepository.saveAndFlush(any(Supervisor.class)))
                .thenReturn(this.supervisor1);

        Supervisor supervisor = this.supervisorService.readById((long) 21);
        assertThat(supervisor.getName()).isEqualTo("Marcos");
        assertThat(supervisor.getEmail()).isEqualTo("marcos@gmail.com");
        assertThat(supervisor.getRegion()).isEqualTo(region1);

        supervisor.setName("Update Teste");
        supervisor.setEmail("update@gmail.com");
        supervisor.setRegion(this.region2);

        this.supervisorService.update(supervisor);

        when(this.supervisorRepository.findById((long)21))
                .thenReturn(java.util.Optional.ofNullable(supervisor));

        this.supervisor1= this.supervisorService.readById((long) 21);

        assertThat(this.supervisor1.getName()).isEqualTo("Update Teste");
        assertThat(this.supervisor1.getEmail()).isEqualTo("update@gmail.com");
        assertThat(this.supervisor1.getRegion()).isEqualTo(this.region2);
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void updateSupervisorServiceEntityAlreadyExistsExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        Supervisor supervisorCopy = this.supervisor1;

        List<Supervisor> supervisors = asList(this.supervisor1,this.supervisor2,this.supervisor3,supervisorCopy);
        BDDMockito.when(this.supervisorRepository.findAll())
                .thenReturn(supervisors);

        when(this.supervisorRepository.findById((long)21))
                .thenReturn(java.util.Optional.ofNullable(this.supervisor1));
        when(this.supervisorRepository.saveAndFlush(any(Supervisor.class)))
                .thenReturn(this.supervisor1);

        Supervisor supervisor = this.supervisorService.readById((long) 21);
        assertThat(supervisor.getName()).isEqualTo("Marcos");
        assertThat(supervisor.getEmail()).isEqualTo("marcos@gmail.com");

        supervisor.setName("Update Teste");
        supervisor.setEmail("update@gmail.com");

        this.supervisorService.update(supervisor);
        Assertions.fail("EntityAlreadyExistsException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void updateSupervisorServiceAnyPersistenceExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.supervisorRepository.findById((long)21))
                .thenReturn(java.util.Optional.ofNullable(this.supervisor1));
        doThrow(IllegalArgumentException.class).when(this.supervisorRepository).saveAndFlush(any(Supervisor.class));

        Supervisor supervisor = this.supervisorService.readById((long) 21);
        assertThat(supervisor.getName()).isEqualTo("Marcos");
        assertThat(supervisor.getEmail()).isEqualTo("marcos@gmail.com");

        supervisor.setName("Update Teste");
        supervisor.setEmail("update@gmail.com");

        this.supervisorService.update(supervisor);

        Assertions.fail("AnyPersistenceException it is not throws");
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateSupervisorServiceEntityNotFoundExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.supervisorRepository.findById((long)21))
                .thenReturn(java.util.Optional.ofNullable(this.supervisor1));
        when(this.supervisorRepository.findById((long)33))
                .thenReturn(java.util.Optional.ofNullable(null));
        when(this.supervisorRepository.saveAndFlush(any(Supervisor.class)))
                .thenReturn(this.supervisor1);

        Supervisor supervisor = this.supervisorService.readById((long) 21);
        assertThat(supervisor.getName()).isEqualTo("Marcos");
        assertThat(supervisor.getEmail()).isEqualTo("marcos@gmail.com");

        supervisor.setName("Update Teste");
        supervisor.setEmail("update@gmail.com");
        supervisor.setId((long) 33);

        this.supervisorService.update(supervisor);
        Assertions.fail("EntityNotFoundException it is not throws");
    }

    @Test
    public void deleteSupervisorServiceTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.supervisorRepository.findById((long) 21))
                .thenReturn(java.util.Optional.ofNullable(this.supervisor1));
        doNothing().when(this.supervisorRepository).delete(any(Supervisor.class));

        this.supervisorService.delete((long) 21);

        when(this.supervisorRepository.findById((long) 21))
                .thenReturn(java.util.Optional.ofNullable(null));

        try {
            this.supervisorService.readById((long) 21);
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){
            assertThat(e.getClass()).isEqualTo(EntityNotFoundException.class);
        }
    }

    @Test (expected = AnyPersistenceException.class)
    public void deleteSupervisorServiceAnyPersistenceExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.supervisorRepository.findById((long) 21))
                .thenReturn(java.util.Optional.ofNullable(this.supervisor1));
        doThrow(IllegalArgumentException.class).when(this.supervisorRepository).delete(any(Supervisor.class));

        this.supervisorService.delete((long) 21);
        Assertions.fail("AnyPersistenceException it is not throws");
    }

    @Test (expected = EntityInUseException.class)
    public void deleteSupervisorServiceEntityInUseExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.supervisorRepository.findById((long) 21))
                .thenReturn(java.util.Optional.ofNullable(this.supervisor1));
        doThrow(DataIntegrityViolationException.class).when(this.supervisorRepository).delete(any(Supervisor.class));

        this.supervisorService.delete((long) 21);
        Assertions.fail("EntityInUseException it is not throws");
    }

    @Test (expected = EntityNotFoundException.class)
    public void deleteSupervisorServiceEntityNotFoundExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.supervisorRepository.findById((long) 211))
                .thenReturn(java.util.Optional.ofNullable(null));
        this.supervisorService.delete((long) 1);
        Assertions.fail("EntityNotFoundException it is not throws");
    }

    @Test
    public void readRegionByIdSupervisorServiceTest() {
        when(this.regionRepository.findById((long) 11))
                .thenReturn(java.util.Optional.ofNullable(this.region1));
        assertThat(this.supervisorService.readRegionById((long) 11)).isEqualTo(this.region1);
    }

    @Test // Test falha pois - Ha o lançamento de um NullPointe pois o contrutor precisa de um nome, e região email para ser executado, não ha construtor vazio.
    public void readRegionByIdSupervisorServiceEntityNotFoundExceptionTest() {
        when(this.regionRepository.findById((long) 112))
                .thenReturn(java.util.Optional.ofNullable(null));
        assertThat(this.supervisorService.readRegionById((long) 112).getClass()).isEqualTo(Region.class);
    }
}