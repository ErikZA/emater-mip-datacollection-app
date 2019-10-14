package br.edu.utfpr.cp.emater.midmipsystem.service.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Farmer;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.FarmerRepository;
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

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.*;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FarmerServiceTest {

    @Autowired
    private FarmerService farmerService;

    @MockBean
    private FarmerRepository farmerRepository;

    private Farmer farmer1, farmer2, farmer3, farmer4;

    @Before
    public void setUp() {
        this.farmer1 = Farmer.builder().id((long) 1).name("Uncle Joe").build();
        this.farmer2 = Farmer.builder().id((long) 2).name("Uncle Max").build();
        this.farmer3 = Farmer.builder().id((long) 3).name("Uncle Ralf").build();
        this.farmer4 = Farmer.builder().id((long) 4).name("Uncle Marcos").build();

        List<Farmer> farmers = asList(this.farmer1,this.farmer2,this.farmer3);
        BDDMockito.when(this.farmerRepository.findAll())
                .thenReturn(farmers);
    }

    @Test
    public void readAllFarmerServiceTest() {
        assertThat(this.farmerService.readAll()).containsExactlyInAnyOrder(this.farmer1,this.farmer2,this.farmer3)
                .doesNotContainNull().isNotEmpty().doesNotContain(this.farmer4);
    }

    @Test
    public void readByIdFarmerServiceTest() throws EntityNotFoundException {
        when(this.farmerRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.farmer1));
        assertThat(this.farmerService.readById((long) 1)).isEqualTo(this.farmer1);
    }

    @Test (expected = EntityNotFoundException.class)
    public void readByIdFarmerServiceEntityNotFoundExceptionTest() throws EntityNotFoundException {
        when(this.farmerRepository.findById((long) 12))
                .thenReturn(java.util.Optional.ofNullable(null));
        this.farmerService.readById((long) 12);
        Assertions.fail("EntityNotFoundException it is not throws");
    }

    @Test
    public void createFarmerServiceTest() throws AnyPersistenceException, EntityAlreadyExistsException, EntityNotFoundException {
        when(this.farmerRepository.findById((long) 4))
                .thenReturn(java.util.Optional.ofNullable(null));
        when(this.farmerRepository.save(this.farmer4)).thenReturn(this.farmer4);

        try {
            this.farmerService.readById(this.farmer4.getId());
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){    }

        this.farmerService.create(this.farmer4);

        when(this.farmerRepository.findById((long) 4))
                .thenReturn(java.util.Optional.ofNullable(this.farmer4));

        Farmer farmer = this.farmerService.readById((long) 4);
        assertThat(farmer.getName()).isEqualTo("Uncle Marcos");
    }

    @Test (expected = AnyPersistenceException.class)
    public void createFarmerServiceAnyPersistenceExceptionTest() throws AnyPersistenceException, EntityAlreadyExistsException {
        when(this.farmerRepository.findById((long) 4))
                .thenReturn(java.util.Optional.ofNullable(null));
        doThrow(IllegalArgumentException.class).when(this.farmerRepository).save(this.farmer4);

        try {
            this.farmerService.readById(this.farmer4.getId());
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){    }

        this.farmerService.create(this.farmer4);

        Assertions.fail("AnyPersistenceException it is not throws");
    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void createFarmerServiceEntityAlreadyExistsExceptionTest() throws AnyPersistenceException, EntityAlreadyExistsException {
        this.farmerService.create(this.farmer1);
        Assertions.fail("EntityAlreadyExistsException it is not throws");
    }

    @Test //O farmer atual não é removido antes de vereficar se ha algum registro corresponddente
    public void updateFarmerServiceTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.farmerRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.farmer1));
        when(this.farmerRepository.saveAndFlush(any(Farmer.class)))
                .thenReturn(this.farmer1);

        Farmer farmer = this.farmerService.readById((long) 1);
        assertThat(farmer.getName()).isEqualTo("Uncle Joe");

        farmer.setName("Update Teste");

        this.farmerService.update(farmer);

        when(this.farmerRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(farmer));

        this.farmer1 = this.farmerService.readById((long) 1);

        assertThat(this.farmer1.getName()).isEqualTo("Update Teste");
    }

    @Test (expected = EntityNotFoundException.class)
    public void updateFarmerServiceEntityNotFoundExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.farmerRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.farmer1));
        when(this.farmerRepository.findById((long)12))
                .thenReturn(java.util.Optional.ofNullable(null));
        when(this.farmerRepository.saveAndFlush(any(Farmer.class)))
                .thenReturn(this.farmer1);

        Farmer farmer = this.farmerService.readById((long) 1);
        assertThat(farmer.getName()).isEqualTo("Uncle Joe");

        farmer.setName("Update Teste");
        farmer.setId((long) 12);
        this.farmerService.update(farmer);

        Assertions.fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class) //O farmer atual não é removido antes de vereficar se ha algum registro corresponddente
    public void updateFarmerServiceAnyPersistenceExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.farmerRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.farmer1));
        doThrow(IllegalArgumentException.class).when(this.farmerRepository).saveAndFlush(any(Farmer.class));

        Farmer farmer = this.farmerService.readById((long) 1);
        assertThat(farmer.getName()).isEqualTo("Uncle Joe");

        farmer.setName("Update Teste");

        this.farmerService.update(farmer);
        Assertions.fail("AnyPersistenceException it is not throws");
    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void updateFarmerServiceEntityAlreadyExistsExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        Farmer farmerCopy = this.farmer1;
        List<Farmer> farmers = asList(this.farmer1,this.farmer2,this.farmer3,farmerCopy);
        BDDMockito.when(this.farmerRepository.findAll())
                .thenReturn(farmers);
        when(this.farmerRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.farmer1));

        Farmer farmer = this.farmerService.readById((long) 1);
        assertThat(farmer.getName()).isEqualTo("Uncle Joe");

        farmer.setName("Update Teste");

        this.farmerService.update(farmer);
        Assertions.fail("EntityAlreadyExistsException it is not throws");
    }

    @Test
    public void deleteFarmerServiceTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.farmerRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.farmer1));
        doNothing().when(this.farmerRepository).delete(any(Farmer.class));

        this.farmerService.delete((long) 1);

        when(this.farmerRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(null));

        try {
            this.farmerService.readById((long) 1);
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){
            assertThat(e.getClass()).isEqualTo(EntityNotFoundException.class);
        }
    }

    @Test (expected = EntityNotFoundException.class)
    public void deleteFarmerServiceEntityNotFoundExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.farmerRepository.findById((long) 21))
                .thenReturn(java.util.Optional.ofNullable(null));
        doNothing().when(this.farmerRepository).delete(any(Farmer.class));

        this.farmerService.delete((long) 21);
        Assertions.fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void deleteFarmerServiceAnyPersistenceExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.farmerRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.farmer1));
        doThrow(IllegalArgumentException.class).when(this.farmerRepository).delete(any(Farmer.class));

        this.farmerService.delete((long) 1);

        Assertions.fail("AnyPersistenceException it is not throws");
    }

    @Test (expected = EntityInUseException.class)
    public void deleteFarmerServiceEntityInUseExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.farmerRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.farmer1));
        doThrow(DataIntegrityViolationException.class).when(this.farmerRepository).delete(any(Farmer.class));
        this.farmerService.delete((long) 1);

        when(this.farmerRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(null));
        Assertions.fail("EntityInUseException it is not throws");
    }
}