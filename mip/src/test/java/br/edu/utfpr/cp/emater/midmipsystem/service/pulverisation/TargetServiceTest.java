package br.edu.utfpr.cp.emater.midmipsystem.service.pulverisation;

import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.Target;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.TargetCategory;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.pulverisation.TargetRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TargetServiceTest {

    @MockBean
    private TargetRepository targetRepository;

    @Autowired
    private TargetService targetService;

    private Target target1, target2, target3;

    @Before
    public void setUp(){
        this.target1 = Target.builder().id((long) 232).description("Test Target1").category(TargetCategory.ADJUVANTE).build();
        this.target2 = Target.builder().id((long) 343).description("Test Target2").category(TargetCategory.HERBICIDA).build();
        this.target3 = Target.builder().id((long) 545).description("Test Target3").category(TargetCategory.ADUBO_FOLIAR).build();
        List<Target> targets= asList(this.target1,this.target2);
        when(this.targetRepository.findAll())
                .thenReturn(targets);
    }

    @Test
    public void readAllTargetServiceTest() {
        assertThat(this.targetService.readAll()).containsExactlyInAnyOrder(this.target1,this.target2)
                .isNotEmpty().doesNotContain(this.target3).doesNotContainNull();
    }

    @Test
    public void readByIdTargetServiceTest() throws EntityNotFoundException {
        when(this.targetRepository.findById((long)232)).thenReturn(java.util.Optional.ofNullable(this.target1));
        assertThat(this.targetService.readById((long) 232)).isEqualTo(this.target1);
    }

    @Test(expected = EntityNotFoundException.class)
    public void readByIdTargetServiceEntityNotFoundExceptionTest() throws EntityNotFoundException {
        when(this.targetRepository.findById((long)3332)).thenReturn(java.util.Optional.ofNullable(null));
        assertThat(this.targetService.readById((long) 3332)).isEqualTo(this.target1);
        fail("EntityNotFoundException it is not throws");
    }

    @Test
    public void createTargetServiceTest() throws AnyPersistenceException, EntityNotFoundException, EntityAlreadyExistsException {
        when(this.targetRepository.findById((long)545)).thenReturn(java.util.Optional.ofNullable(null));
        when(this.targetRepository.save(this.target3)).thenReturn(this.target3);
        try {
            this.targetService.readById((long) 545);
            fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){

        }
        this.targetService.create(this.target3);
        when(this.targetRepository.findById((long)545)).thenReturn(java.util.Optional.ofNullable(this.target3));
        Target targetTemp = this.targetService.readById((long) 545);
        assertThat(targetTemp.getDescription()).isEqualTo(target3.getDescription());
    }

    @Test (expected = AnyPersistenceException.class)
    public void createTargetServiceAnyPersistenceExceptionTest() throws AnyPersistenceException, EntityNotFoundException, EntityAlreadyExistsException {
        when(this.targetRepository.findById((long)545)).thenReturn(java.util.Optional.ofNullable(null));
        doThrow(IllegalArgumentException.class).when(this.targetRepository).save(this.target3);
        try {
            this.targetService.readById((long) 545);
            fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){

        }
        this.targetService.create(this.target3);
        fail("AnyPersistenceException it is not throws");
    }

    @Ignore // NUnca é lançada EntityNotFoundException.class
    @Test (expected = EntityNotFoundException.class)
    public void createTargetServiceEntityNotFoundExceptionTest() throws AnyPersistenceException, EntityNotFoundException, EntityAlreadyExistsException {
        when(this.targetRepository.findById((long)545)).thenReturn(java.util.Optional.ofNullable(null));
        doThrow(IllegalArgumentException.class).when(this.targetRepository).save(this.target3);
            this.targetService.create(this.target3);
            fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void createTargetServiceEntityAlreadyExistsExceptionTest() throws AnyPersistenceException, EntityNotFoundException, EntityAlreadyExistsException {
        doThrow(IllegalArgumentException.class).when(this.targetRepository).save(this.target3);
        this.targetService.create(this.target1);
        fail("EntityAlreadyExistsException it is not throws");
    }

    @Test
    public void updateTargetServiceTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.targetRepository.findById((long)232)).thenReturn(java.util.Optional.ofNullable(this.target1));
        when(this.targetRepository.saveAndFlush(any(Target.class))).thenReturn(this.target1);
        Target targetTemp = this.targetService.readById((long) 232);
        assertThat(targetTemp.getDescription()).isEqualTo("Test Target1");
        assertThat(targetTemp.getCategory()).isEqualTo(TargetCategory.ADJUVANTE);
        assertThat(targetTemp.getId()).isEqualTo((long)232);

        targetTemp.setDescription("Test Update");
        targetTemp.setCategory(TargetCategory.FUNGICIDA);


        this.targetService.update(targetTemp);

        when(this.targetRepository.findById((long)232)).thenReturn(java.util.Optional.ofNullable(targetTemp));
        this.target1 = this.targetService.readById((long) 232);
        assertThat(this.target1.getDescription()).isEqualTo("Test Update");
        assertThat(this.target1.getCategory()).isEqualTo(TargetCategory.FUNGICIDA);
        assertThat(this.target1.getId()).isEqualTo((long)232);
    }


    @Test (expected = EntityNotFoundException.class)
    public void updateTargetServiceEntityNotFoundExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.targetRepository.findById((long)545)).thenReturn(java.util.Optional.ofNullable(null));
        this.target3.setCategory(TargetCategory.OUTROS);
        this.targetService.update(this.target3);
        fail("EntityAlreadyExistsException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void updateTargetServiceAnyPersistenceExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.targetRepository.findById((long)232)).thenReturn(java.util.Optional.ofNullable(this.target1));
        doThrow(IllegalArgumentException.class).when(this.targetRepository)
                .saveAndFlush(any(Target.class));
        Target targetTemp = this.targetService.readById((long) 232);
        assertThat(targetTemp.getDescription()).isEqualTo("Test Target1");
        assertThat(targetTemp.getCategory()).isEqualTo(TargetCategory.ADJUVANTE);
        assertThat(targetTemp.getId()).isEqualTo((long)232);

        targetTemp.setDescription("Test Update");
        targetTemp.setCategory(TargetCategory.FUNGICIDA);


        this.targetService.update(targetTemp);
        fail("AnyPersistenceException it is not throws");
}

    @Test (expected = EntityAlreadyExistsException.class)
    public void updateTargetServiceEntityAlreadyExistsExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.targetRepository.findById((long)232)).thenReturn(java.util.Optional.ofNullable(this.target1));

        Target targetCopy  = this.target1;
        List<Target> targets= asList(this.target1,this.target2,targetCopy);
        when(this.targetRepository.findAll())
                .thenReturn(targets);

        Target targetTemp = this.targetService.readById((long) 232);
        assertThat(targetTemp.getDescription()).isEqualTo("Test Target1");
        assertThat(targetTemp.getCategory()).isEqualTo(TargetCategory.ADJUVANTE);
        assertThat(targetTemp.getId()).isEqualTo((long)232);

        targetTemp.setDescription("Test Update");
        targetTemp.setCategory(TargetCategory.FUNGICIDA);


        this.targetService.update(targetTemp);

        fail("EntityAlreadyExistsException it is not throws");
    }

    @Test
    public void deleteTargetServiceTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.targetRepository.findById((long)232)).thenReturn(java.util.Optional.ofNullable(this.target1));
        doNothing().when(this.targetRepository).delete(any(Target.class));
        this.targetService.delete((long) 232);
        when(this.targetRepository.findById((long)232)).thenReturn(java.util.Optional.ofNullable(null));
        try {
            this.targetService.readById((long) 232);
        }catch (EntityNotFoundException e){
            assertThat(e.getClass()).isEqualTo(EntityNotFoundException.class);
        }
    }


    @Test (expected = EntityNotFoundException.class)
    public void deleteTargetServiceEntityNotFoundExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.targetRepository.findById((long)232)).thenReturn(java.util.Optional.ofNullable(this.target1));
        doNothing().when(this.targetRepository).delete(any(Target.class));
        this.targetService.delete((long) 232);
        when(this.targetRepository.findById((long)232)).thenReturn(java.util.Optional.ofNullable(null));

        this.targetService.readById((long) 232);
        fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void deleteTargetServiceAnyPersistenceExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.targetRepository.findById((long)232)).thenReturn(java.util.Optional.ofNullable(this.target1));
        doThrow(IllegalArgumentException.class).when(this.targetRepository).delete(any(Target.class));
        this.targetService.delete((long) 232);
        fail("AnyPersistenceException it is not throws");
    }

    @Test (expected = EntityInUseException.class)
    public void deleteTargetServiceEntityInUseExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.targetRepository.findById((long)232)).thenReturn(java.util.Optional.ofNullable(this.target1));
        doThrow(DataIntegrityViolationException.class).when(this.targetRepository).delete(any(Target.class));
        this.targetService.delete((long) 232);
        fail("EntityInUseException it is not throws");
    }

}