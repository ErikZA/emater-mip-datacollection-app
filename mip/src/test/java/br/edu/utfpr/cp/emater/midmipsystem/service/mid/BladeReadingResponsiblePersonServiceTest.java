package br.edu.utfpr.cp.emater.midmipsystem.service.mid;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.State;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.BladeReadingResponsibleEntity;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.BladeReadingResponsiblePerson;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mid.BladeReadingResponsibleEntityRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mid.BladeReadingResponsiblePersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BladeReadingResponsiblePersonServiceTest {


    @MockBean
    private BladeReadingResponsibleEntityRepository bladeReadingResponsibleEntityRepository;
    @MockBean
    private BladeReadingResponsiblePersonRepository bladeReadingResponsiblePersonRepository;


    private BladeReadingResponsiblePerson bladeReadingResponsiblePerson1, bladeReadingResponsiblePerson2, bladeReadingResponsiblePerson3;
    private BladeReadingResponsibleEntity bladeReadingResponsibleEntity1, bladeReadingResponsibleEntity2, bladeReadingResponsibleEntity3;

    @Autowired
    private BladeReadingResponsiblePersonService bladeReadingResponsiblePersonService;


    @Before
    public void setUp(){
        this.bladeReadingResponsibleEntity1 = BladeReadingResponsibleEntity.builder().id((long) 33).name("Test Service")
                .city(City.builder().name("Nova Fatima").state(State.PR).build()).build();
        this.bladeReadingResponsibleEntity2 = BladeReadingResponsibleEntity.builder().id((long) 223).name("Test Service Entity")
                .city(City.builder().name("Nova Fatima").state(State.PR).build()).build();
        this.bladeReadingResponsibleEntity3 = BladeReadingResponsibleEntity.builder().id((long) 94).name("Test Service Unit")
                .city(City.builder().name("Nova Fatima").state(State.PR).build()).build();

        this.bladeReadingResponsiblePerson1 = BladeReadingResponsiblePerson.builder().name("Test Sistem1").id((long) 99)
                .entity(this.bladeReadingResponsibleEntity1).build();
        this.bladeReadingResponsiblePerson2 = BladeReadingResponsiblePerson.builder().name("Test Abrobinha").id((long) 142)
                .entity(this.bladeReadingResponsibleEntity2).build();
        this.bladeReadingResponsiblePerson3 = BladeReadingResponsiblePerson.builder().name("Test Pepino").id((long) 660)
                .entity(this.bladeReadingResponsibleEntity3).build();

        List<BladeReadingResponsiblePerson> bladeReadingResponsiblePeople = asList(this.bladeReadingResponsiblePerson1,
                this.bladeReadingResponsiblePerson2);
        when(this.bladeReadingResponsiblePersonRepository.findAll())
                .thenReturn(bladeReadingResponsiblePeople);

        List<BladeReadingResponsibleEntity> bladeReadingResponsibleEntities = asList(this.bladeReadingResponsibleEntity1,
                this.bladeReadingResponsibleEntity2);
        when(this.bladeReadingResponsibleEntityRepository.findAll())
                .thenReturn(bladeReadingResponsibleEntities);
    }

    @Test
    public void readAllBladeReadingResponsiblePersonServiceTest() {
        assertThat(this.bladeReadingResponsiblePersonService.readAll()).doesNotContain(this.bladeReadingResponsiblePerson3)
                .doesNotContainNull().isNotEmpty()
                .containsExactlyInAnyOrder(this.bladeReadingResponsiblePerson1,this.bladeReadingResponsiblePerson2);
    }

    @Test
    public void readByIdBladeReadingResponsiblePersonServiceTest() throws EntityNotFoundException {
        when(this.bladeReadingResponsiblePersonRepository.findById((long)99))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsiblePerson1));
        assertThat(this.bladeReadingResponsiblePersonService.readById((long) 99)).isEqualTo(this.bladeReadingResponsiblePerson1);
    }


    @Test (expected = EntityNotFoundException.class)
    public void readByIdBladeReadingResponsiblePersonServiceEntityNotFoundExceptionTest() throws EntityNotFoundException {
        when(this.bladeReadingResponsiblePersonRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(null));
        assertThat(this.bladeReadingResponsiblePersonService.readById((long) 99)).isEqualTo(this.bladeReadingResponsibleEntity1);
        Assertions.fail("EntityNotFoundException it is not throws");
    }

    @Test
    public void createBladeReadingResponsiblePersonServiceTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.bladeReadingResponsibleEntityRepository.findById((long)94))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsibleEntity3));
        when(this.bladeReadingResponsiblePersonRepository.findById((long)660))
                .thenReturn(java.util.Optional.ofNullable(null));
        when(this.bladeReadingResponsiblePersonRepository.save(this.bladeReadingResponsiblePerson3))
                .thenReturn(any(BladeReadingResponsiblePerson.class));
        try {
            this.bladeReadingResponsiblePersonService.readById(this.bladeReadingResponsiblePerson3.getEntityId());
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e) {
        }

        this.bladeReadingResponsiblePersonService.create(this.bladeReadingResponsiblePerson3);

        when(this.bladeReadingResponsiblePersonRepository.findById((long)660))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsiblePerson3));

        assertThat(this.bladeReadingResponsiblePersonService.readById((long) 660)).isEqualTo(this.bladeReadingResponsiblePerson3);
    }

    @Test (expected = EntityNotFoundException.class)
    public void createBladeReadingResponsiblePersonServiceEntityNotFoundExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.bladeReadingResponsibleEntityRepository.findById((long)94))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsibleEntity3));
        when(this.bladeReadingResponsiblePersonRepository.findById((long)660))
                .thenReturn(java.util.Optional.ofNullable(null));
        try {
            this.bladeReadingResponsiblePersonService.readById(this.bladeReadingResponsiblePerson3.getEntityId());
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e) {
        }
        this.bladeReadingResponsiblePerson3.setEntity(BladeReadingResponsibleEntity.builder().id((long) 8).name("TestFail").build());
        this.bladeReadingResponsiblePersonService.create(this.bladeReadingResponsiblePerson3);
        Assertions.fail("EntityNotFoundException it is not throws");
    }


    @Test (expected = AnyPersistenceException.class)
    public void createBladeReadingResponsiblePersonServiceAnyPersistenceExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.bladeReadingResponsibleEntityRepository.findById((long)94))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsibleEntity3));
        when(this.bladeReadingResponsiblePersonRepository.findById((long)660))
                .thenReturn(java.util.Optional.ofNullable(null));
        doThrow(IllegalArgumentException.class).when(this.bladeReadingResponsiblePersonRepository).save(any());

        try {
            this.bladeReadingResponsiblePersonService.readById(this.bladeReadingResponsiblePerson3.getEntityId());
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e) {
        }

        this.bladeReadingResponsiblePersonService.create(this.bladeReadingResponsiblePerson3);
        Assertions.fail("AnyPersistenceException it is not throws");
    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void createBladeReadingResponsiblePersonServiceEntityAlreadyExistsExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        this.bladeReadingResponsiblePersonService.create(this.bladeReadingResponsiblePerson1);
        Assertions.fail("EntityAlreadyExistsException it is not throws");
    }

    @Test
    public void updateBladeReadingResponsiblePersonServiceTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.bladeReadingResponsiblePersonRepository.findById((long)99))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsiblePerson1));
        when(this.bladeReadingResponsiblePersonRepository.saveAndFlush(any(BladeReadingResponsiblePerson.class)))
                .thenReturn(this.bladeReadingResponsiblePerson1);
        when(this.bladeReadingResponsibleEntityRepository.findById((long) 223))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsibleEntity2));

        BladeReadingResponsiblePerson bladeReadingResponsiblePersonTemp = this.bladeReadingResponsiblePersonService
                .readById(this.bladeReadingResponsiblePerson1.getId());

        assertThat(bladeReadingResponsiblePersonTemp.getName()).isEqualTo("Test Sistem1");
        assertThat(bladeReadingResponsiblePersonTemp.getId()).isEqualTo((long)99);
        assertThat(bladeReadingResponsiblePersonTemp.getEntity()).isEqualTo(this.bladeReadingResponsibleEntity1);

        bladeReadingResponsiblePersonTemp.setName("Update Teste");
        bladeReadingResponsiblePersonTemp.setEntity(this.bladeReadingResponsibleEntity2);

        this.bladeReadingResponsiblePersonService.update(bladeReadingResponsiblePersonTemp);

        when(this.bladeReadingResponsiblePersonRepository.findById((long)99))
                .thenReturn(java.util.Optional.ofNullable(bladeReadingResponsiblePersonTemp));

        this.bladeReadingResponsiblePerson1 = this.bladeReadingResponsiblePersonService
                .readById((long) 99);

        assertThat(this.bladeReadingResponsiblePerson1.getName()).isEqualTo("Update Teste");
        assertThat(this.bladeReadingResponsiblePerson1.getId()).isEqualTo((long)99);
        assertThat(this.bladeReadingResponsiblePerson1.getEntity()).isEqualTo(this.bladeReadingResponsibleEntity2);
    }

    @Test (expected = EntityNotFoundException.class)
    public void updateBladeReadingResponsiblePersonServiceEntityNotFoundExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.bladeReadingResponsiblePersonRepository.findById((long)660))
                .thenReturn(java.util.Optional.ofNullable(null));

        this.bladeReadingResponsiblePersonService.update(this.bladeReadingResponsiblePerson3);
        Assertions.fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void updateBladeReadingResponsiblePersonServiceAnyPersistenceExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.bladeReadingResponsiblePersonRepository.findById((long)99))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsiblePerson1));
        doThrow(IllegalArgumentException.class).when(this.bladeReadingResponsiblePersonRepository)
                .saveAndFlush(any());
        when(this.bladeReadingResponsibleEntityRepository.findById((long) 223))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsibleEntity2));

        BladeReadingResponsiblePerson bladeReadingResponsiblePersonTemp = this.bladeReadingResponsiblePersonService
                .readById(this.bladeReadingResponsiblePerson1.getId());

        assertThat(bladeReadingResponsiblePersonTemp.getName()).isEqualTo("Test Sistem1");
        assertThat(bladeReadingResponsiblePersonTemp.getId()).isEqualTo((long)99);
        assertThat(bladeReadingResponsiblePersonTemp.getEntity()).isEqualTo(this.bladeReadingResponsibleEntity1);

        bladeReadingResponsiblePersonTemp.setName("Update Teste");
        bladeReadingResponsiblePersonTemp.setEntity(this.bladeReadingResponsibleEntity2);

        this.bladeReadingResponsiblePersonService.update(bladeReadingResponsiblePersonTemp);
        Assertions.fail("AnyPersistenceException it is not throws");
    }


    @Test (expected = EntityAlreadyExistsException.class)
    public void updateBladeReadingResponsiblePersonServiceEntityAlreadyExistsExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        BladeReadingResponsiblePerson bladeReadingResponsiblePersonCopy = this.bladeReadingResponsiblePerson1;
        List<BladeReadingResponsiblePerson> bladeReadingResponsiblePeople = asList(this.bladeReadingResponsiblePerson1,
                this.bladeReadingResponsiblePerson2,bladeReadingResponsiblePersonCopy);
        when(this.bladeReadingResponsiblePersonRepository.findAll())
                .thenReturn(bladeReadingResponsiblePeople);
        when(this.bladeReadingResponsiblePersonRepository.findById((long)99))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsiblePerson1));
        doThrow(IllegalArgumentException.class).when(this.bladeReadingResponsiblePersonRepository)
                .saveAndFlush(any());
        when(this.bladeReadingResponsibleEntityRepository.findById((long) 223))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsibleEntity2));

        BladeReadingResponsiblePerson bladeReadingResponsiblePersonTemp = this.bladeReadingResponsiblePersonService
                .readById(this.bladeReadingResponsiblePerson1.getId());

        assertThat(bladeReadingResponsiblePersonTemp.getName()).isEqualTo("Test Sistem1");
        assertThat(bladeReadingResponsiblePersonTemp.getId()).isEqualTo((long)99);
        assertThat(bladeReadingResponsiblePersonTemp.getEntity()).isEqualTo(this.bladeReadingResponsibleEntity1);

        bladeReadingResponsiblePersonTemp.setName("Update Teste");
        bladeReadingResponsiblePersonTemp.setEntity(this.bladeReadingResponsibleEntity2);

        this.bladeReadingResponsiblePersonService.update(bladeReadingResponsiblePersonTemp);
        Assertions.fail("AnyPersistenceException it is not throws");
    }

    @Test
    public void deleteBladeReadingResponsiblePersonServiceTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.bladeReadingResponsiblePersonRepository.findById((long)99))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsiblePerson1));
        doNothing().when(this.bladeReadingResponsiblePersonRepository).delete(any(BladeReadingResponsiblePerson.class));

        this.bladeReadingResponsiblePersonService.delete((long) 99);

        when(this.bladeReadingResponsiblePersonRepository.findById((long)99))
                .thenReturn(java.util.Optional.ofNullable(null));
        try {
            BladeReadingResponsiblePerson bladeReadingResponsiblePerson = this.bladeReadingResponsiblePersonService.readById((long) 99);
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){
            assertThat(e.getClass()).isEqualTo(EntityNotFoundException.class);
        }
    }

    @Test (expected = EntityNotFoundException.class)
    public void deleteBladeReadingResponsiblePersonServiceEntityNotFoundExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.bladeReadingResponsiblePersonRepository.findById((long)919))
                .thenReturn(java.util.Optional.ofNullable(null));
        this.bladeReadingResponsiblePersonService.delete((long) 919);
        Assertions.fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void deleteBladeReadingResponsiblePersonServiceAnyPersistenceExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.bladeReadingResponsiblePersonRepository.findById((long)99))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsiblePerson1));
        doThrow(IllegalArgumentException.class).when(this.bladeReadingResponsiblePersonRepository).delete(any(BladeReadingResponsiblePerson.class));
        this.bladeReadingResponsiblePersonService.delete((long) 99);
        Assertions.fail("AnyPersistenceException it is not throws");
    }

    @Test (expected = EntityInUseException.class)
    public void deleteBladeReadingResponsiblePersonServiceEntityInUseExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.bladeReadingResponsiblePersonRepository.findById((long)99))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsiblePerson1));
        doThrow(DataIntegrityViolationException.class).when(this.bladeReadingResponsiblePersonRepository).delete(any(BladeReadingResponsiblePerson.class));
        this.bladeReadingResponsiblePersonService.delete((long) 99);
        Assertions.fail("EntityInUseException it is not throws");
    }

    @Test
    public void readAllEntitiesBladeReadingResponsiblePersonServiceTest() {
        assertThat(this.bladeReadingResponsiblePersonService.readAllEntities())
                .containsExactlyInAnyOrder(this.bladeReadingResponsibleEntity1, this.bladeReadingResponsibleEntity2).isNotEmpty()
                .doesNotContain(this.bladeReadingResponsibleEntity3).doesNotContainNull();
    }

    @Test
    public void readEntityByIdBladeReadingResponsiblePersonServiceTest() throws EntityNotFoundException {
        when(this.bladeReadingResponsibleEntityRepository.findById((long) 223))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsibleEntity2));
        assertThat(this.bladeReadingResponsiblePersonService.readEntityById((long) 223))
                .isEqualTo(this.bladeReadingResponsibleEntity2);
    }

    @Test (expected = EntityNotFoundException.class)
    public void readEntityByIdBladeReadingResponsiblePersonServiceEntityNotFoundExceptionTest() throws EntityNotFoundException {
        when(this.bladeReadingResponsibleEntityRepository.findById((long) 989))
                .thenReturn(java.util.Optional.ofNullable(null));
        assertThat(this.bladeReadingResponsiblePersonService.readEntityById((long) 989))
                .isEqualTo(this.bladeReadingResponsibleEntity2);
    }
}