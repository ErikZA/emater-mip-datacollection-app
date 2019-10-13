package br.edu.utfpr.cp.emater.midmipsystem.service.mid;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.State;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.BladeReadingResponsibleEntity;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.CityRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mid.BladeReadingResponsibleEntityRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BladeReadingResponsibleEntityServiceTest {


    @MockBean
    private BladeReadingResponsibleEntityRepository bladeReadingResponsibleEntityRepository;
    @MockBean
    private CityRepository cityRepository;

    private BladeReadingResponsibleEntity bladeReadingResponsibleEntity1, bladeReadingResponsibleEntity2, bladeReadingResponsibleEntity3;
    private City city1, city2, city3, city4;

    @Autowired
    private BladeReadingResponsibleEntityService bladeReadingResponsibleEntityService;

    @Before
    public void setUp(){
        this.city1 = City.builder().name("Nova Fatima").state(State.PR).build();
        this.city1.setId((long) 22);
        this.city2 = City.builder().name("Cornelio Procopio").state(State.PR).build();
        this.city2.setId((long) 20);
        this.city3 = City.builder().name("Moçoro").state(State.SC).build();
        this.city3.setId((long) 87);
        this.city4 = City.builder().name("Joaçaba").state(State.RS).build();
        this.city4.setId((long) 76);
        this.bladeReadingResponsibleEntity1 = BladeReadingResponsibleEntity.builder().id((long) 33).name("Test Service").city(this.city2).build();
        this.bladeReadingResponsibleEntity2 = BladeReadingResponsibleEntity.builder().id((long) 223).name("Test Service Entity").city(this.city2).build();
        this.bladeReadingResponsibleEntity3 = BladeReadingResponsibleEntity.builder().id((long) 94).name("Test Service Unit").city(this.city3).build();
        List<BladeReadingResponsibleEntity> bladeReadingResponsibleEntities = asList(this.bladeReadingResponsibleEntity1,
                this.bladeReadingResponsibleEntity2);
        List<City> cities= asList(this.city1,
                this.city2, this.city3);
        when(this.bladeReadingResponsibleEntityRepository.findAll())
                .thenReturn(bladeReadingResponsibleEntities);
        when(this.cityRepository.findAll())
                .thenReturn(cities);
    }

    @Test
    public void readAllBladeReadingResponsibleEntityTest() {
        assertThat(this.bladeReadingResponsibleEntityService.readAll()).containsExactlyInAnyOrder(this.bladeReadingResponsibleEntity1,this.bladeReadingResponsibleEntity2)
                .doesNotContainNull().isNotEmpty();
    }

    @Test
    public void readByIdBladeReadingResponsibleEntityTest() throws EntityNotFoundException {
        when(this.bladeReadingResponsibleEntityRepository.findById((long)33))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsibleEntity1));
        assertThat(this.bladeReadingResponsibleEntityService.readById((long) 33)).isEqualTo(this.bladeReadingResponsibleEntity1);
    }

    @Test (expected = EntityNotFoundException.class)
    public void readByIdBladeReadingResponsibleEntityEntityNotFoundExceptionTest() throws EntityNotFoundException {
        when(this.bladeReadingResponsibleEntityRepository.findById((long)9))
                .thenReturn(java.util.Optional.ofNullable(null));
        this.bladeReadingResponsibleEntityService.readById((long) 9);
        Assertions.fail("EntityNotFoundException it is not throws");
    }

    @Test
    public void createBladeReadingResponsibleEntityTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.cityRepository.findById((long) 87)).thenReturn(java.util.Optional.ofNullable(this.city3));
        when(this.bladeReadingResponsibleEntityRepository.findById((long)94))
                .thenReturn(java.util.Optional.ofNullable(null));
        when(this.bladeReadingResponsibleEntityRepository.save(this.bladeReadingResponsibleEntity3))
                .thenReturn(any(BladeReadingResponsibleEntity.class));
        try {
            this.bladeReadingResponsibleEntityService.readById(this.bladeReadingResponsibleEntity3.getId());
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){}

        this.bladeReadingResponsibleEntityService.create(this.bladeReadingResponsibleEntity3);

        when(this.bladeReadingResponsibleEntityRepository.findById((long)94))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsibleEntity3));
        assertThat(this.bladeReadingResponsibleEntityService.readById((long) 94))
                .isEqualTo(this.bladeReadingResponsibleEntity3);
    }

    @Test (expected = EntityNotFoundException.class)
    public void createBladeReadingResponsibleEntityTestEntityNotFoundExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.cityRepository.findById((long) 76)).thenReturn(java.util.Optional.ofNullable(null));
        when(this.bladeReadingResponsibleEntityRepository.findById((long)94))
                .thenReturn(java.util.Optional.ofNullable(null));
        when(this.bladeReadingResponsibleEntityRepository.save(this.bladeReadingResponsibleEntity3))
                .thenReturn(any(BladeReadingResponsibleEntity.class));
        this.bladeReadingResponsibleEntity3.setCity(this.city4);

        try {
            this.bladeReadingResponsibleEntityService.readById(this.bladeReadingResponsibleEntity3.getId());
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e  ){}

        this.bladeReadingResponsibleEntityService.create(this.bladeReadingResponsibleEntity3);
        Assertions.fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void createBladeReadingResponsibleEntityAlreadyExistsExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        this.bladeReadingResponsibleEntityService.create(this.bladeReadingResponsibleEntity1);
        Assertions.fail("EntityAlreadyExistsException it is not throws");
    }

    @Test(expected = AnyPersistenceException.class)
    public void createBladeReadingResponsibleEntityAnyPersistenceExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.cityRepository.findById((long) 87)).thenReturn(java.util.Optional.ofNullable(this.city3));
        when(this.bladeReadingResponsibleEntityRepository.findById((long)94))
                .thenReturn(java.util.Optional.ofNullable(null));
        doThrow(IllegalArgumentException.class).when(this.bladeReadingResponsibleEntityRepository).save(any());
        try {
            this.bladeReadingResponsibleEntityService.readById(this.bladeReadingResponsibleEntity3.getId());
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){}

        this.bladeReadingResponsibleEntityService.create(this.bladeReadingResponsibleEntity3);
        Assertions.fail("AnyPersistenceException it is not throws");
    }

    @Test
    public void updateBladeReadingResponsibleEntityTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.bladeReadingResponsibleEntityRepository.findById((long) 33))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsibleEntity1));
        when(this.cityRepository.findById((long) 87)).thenReturn(java.util.Optional.ofNullable(this.city3));

        BladeReadingResponsibleEntity bladeReadingResponsibleEntityTemp = this.bladeReadingResponsibleEntityService.readById((long) 33);

        assertThat(bladeReadingResponsibleEntityTemp.getName()).isEqualTo("Test Service");
        assertThat(bladeReadingResponsibleEntityTemp.getCity()).isEqualTo(this.city2);
        assertThat(bladeReadingResponsibleEntityTemp.getId()).isEqualTo((long)33);

        bladeReadingResponsibleEntityTemp.setCity(this.city3);
        bladeReadingResponsibleEntityTemp.setName("Test Update Service");

        this.bladeReadingResponsibleEntityService.update(bladeReadingResponsibleEntityTemp);

        when(this.bladeReadingResponsibleEntityRepository.findById((long) 33))
                .thenReturn(java.util.Optional.ofNullable(bladeReadingResponsibleEntityTemp));

        this.bladeReadingResponsibleEntity1 = this.bladeReadingResponsibleEntityService.readById((long) 33);
        when(this.bladeReadingResponsibleEntityRepository.saveAndFlush(any(BladeReadingResponsibleEntity.class)))
                .thenReturn(bladeReadingResponsibleEntityTemp);

        assertThat(this.bladeReadingResponsibleEntity1.getCity()).isEqualTo(this.city3);
        assertThat(this.bladeReadingResponsibleEntity1.getName()).isEqualTo("Test Update Service");
        assertThat(this.bladeReadingResponsibleEntity1.getId()).isEqualTo((long)33);
    }

    @Test (expected = EntityNotFoundException.class)
    public void updateBladeReadingResponsibleEntityEntityNotFoundExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.bladeReadingResponsibleEntityRepository.findById((long) 94))
                .thenReturn(java.util.Optional.ofNullable(null));
        this.bladeReadingResponsibleEntityService.update(this.bladeReadingResponsibleEntity3);
        Assertions.fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void updateBladeReadingResponsibleEntityAnyPersistenceExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.bladeReadingResponsibleEntityRepository.findById((long) 33))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsibleEntity1));
        when(this.cityRepository.findById((long) 87)).thenReturn(java.util.Optional.ofNullable(this.city3));
        doThrow(IllegalArgumentException.class)
                .when(this.bladeReadingResponsibleEntityRepository).saveAndFlush(any());
        BladeReadingResponsibleEntity bladeReadingResponsibleEntityTemp = this.bladeReadingResponsibleEntityService.readById((long) 33);
        bladeReadingResponsibleEntityTemp.setCity(this.city3);
        bladeReadingResponsibleEntityTemp.setName("Test Update Service");

        this.bladeReadingResponsibleEntityService.update(bladeReadingResponsibleEntityTemp);
        Assertions.fail("AnyPersistenceException it is not throws");
    }


    @Test(expected = EntityAlreadyExistsException.class)
    public void updateBladeReadingResponsibleEntityEntityAlreadyExistsExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        BladeReadingResponsibleEntity bladeReadingResponsibleEntityCopy = this.bladeReadingResponsibleEntity1;
        List<BladeReadingResponsibleEntity> bladeReadingResponsibleEntities = asList(this.bladeReadingResponsibleEntity1,
                this.bladeReadingResponsibleEntity2,bladeReadingResponsibleEntityCopy);
        when(this.bladeReadingResponsibleEntityRepository.findAll())
                .thenReturn(bladeReadingResponsibleEntities);
        when(this.bladeReadingResponsibleEntityRepository.findById((long) 33))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsibleEntity1));

        BladeReadingResponsibleEntity bladeReadingResponsibleEntityTemp = this.bladeReadingResponsibleEntityService.readById((long) 33);
        bladeReadingResponsibleEntityTemp.setCity(this.city3);
        bladeReadingResponsibleEntityTemp.setName("Test Update Service");

        this.bladeReadingResponsibleEntityService.update(bladeReadingResponsibleEntityTemp);
        Assertions.fail("EntityAlreadyExistsException it is not throws");

    }

    @Test
    public void deleteBladeReadingResponsibleEntityTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.bladeReadingResponsibleEntityRepository.findById((long) 33))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsibleEntity1));
        doNothing().when(this.bladeReadingResponsibleEntityRepository).delete(any(BladeReadingResponsibleEntity.class));

        this.bladeReadingResponsibleEntityService.delete((long) 33);

        when(this.bladeReadingResponsibleEntityRepository.findById((long) 33))
                .thenReturn(java.util.Optional.ofNullable(null));
        try {
            BladeReadingResponsibleEntity bladeReadingResponsibleEntityTemp = this.bladeReadingResponsibleEntityService.readById((long) 33);
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){
            assertThat(e.getClass()).isEqualTo(EntityNotFoundException.class);
        }
    }

    @Test (expected = EntityNotFoundException.class)
    public void deleteBladeReadingResponsibleEntityEntityNotFoundExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.bladeReadingResponsibleEntityRepository.findById((long) 33))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsibleEntity1));
        doNothing().when(this.bladeReadingResponsibleEntityRepository).delete(any(BladeReadingResponsibleEntity.class));

        this.bladeReadingResponsibleEntityService.delete((long) 33);

        when(this.bladeReadingResponsibleEntityRepository.findById((long) 33))
                .thenReturn(java.util.Optional.ofNullable(null));
        this.bladeReadingResponsibleEntityService.delete((long) 33);
        Assertions.fail("EntityNotFoundException it is not throws");

    }

    @Test (expected = EntityInUseException.class)
    public void deleteBladeReadingResponsibleEntityEntityInUseExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.bladeReadingResponsibleEntityRepository.findById((long) 33))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsibleEntity1));
        doThrow(DataIntegrityViolationException.class).when(this.bladeReadingResponsibleEntityRepository)
                .delete(any(BladeReadingResponsibleEntity.class));

        this.bladeReadingResponsibleEntityService.delete((long) 33);
        Assertions.fail("EntityInUseException it is not throws");

    }

    @Test (expected = AnyPersistenceException.class)
    public void deleteBladeReadingResponsibleEntityAnyPersistenceExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.bladeReadingResponsibleEntityRepository.findById((long) 33))
                .thenReturn(java.util.Optional.ofNullable(this.bladeReadingResponsibleEntity1));
        doThrow(IllegalArgumentException.class).when(this.bladeReadingResponsibleEntityRepository)
                .delete(any(BladeReadingResponsibleEntity.class));

        this.bladeReadingResponsibleEntityService.delete((long) 33);
        Assertions.fail("AnyPersistenceException it is not throws");

    }


    @Test
    public void readAllCitiesBladeReadingResponsibleEntityTest() {
        assertThat(this.bladeReadingResponsibleEntityService.readAllCities())
                .isNotEmpty().doesNotContainNull().containsExactlyInAnyOrder(this.city1,this.city2,this.city3)
                .doesNotContain(this.city4);
    }

    @Test
    public void readCityByIdBladeReadingResponsibleEntityTest() throws EntityNotFoundException {
        when(this.cityRepository.findById((long) 87)).thenReturn(java.util.Optional.ofNullable(this.city3));
        assertThat(this.bladeReadingResponsibleEntityService.readCityById((long) 87)).isEqualTo(this.city3);
    }


    @Test (expected = EntityNotFoundException.class)
    public void readCityByIdBladeReadingResponsibleEntityEntityNotFoundExceptionTest() throws EntityNotFoundException {
        when(this.cityRepository.findById((long) 9)).thenReturn(java.util.Optional.ofNullable(null));
        this.bladeReadingResponsibleEntityService.readCityById((long) 9);
        Assertions.fail("EntityNotFoundException it is not throws");
    }
}