package br.edu.utfpr.cp.emater.midmipsystem.service.mip;


import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestNaturalPredator;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mip.PestNaturalPredatorRepository;
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
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PestNaturalPredatorServiceTest {

    @MockBean
    private PestNaturalPredatorRepository pestNaturalPredatorRepository;

    @Autowired
    private PestNaturalPredatorService pestNaturalPredatorService;

    private PestNaturalPredator pestNaturalPredator1, pestNaturalPredator2, pestNaturalPredator3;

    @Before
    public void setUp(){
        this.pestNaturalPredator1 = PestNaturalPredator.builder().id((long)1).usualName("Calosoma granulatum Testa").build();
        this.pestNaturalPredator2 = PestNaturalPredator.builder().id((long)2).usualName("Callida sp Testa.").build();
        this.pestNaturalPredator3 = PestNaturalPredator.builder().id((long)3).usualName("Callida scutellaris Testa").build();

        List<PestNaturalPredator> listPestNaturalPredator = asList(this.pestNaturalPredator1,this.pestNaturalPredator2);
        BDDMockito.when(this.pestNaturalPredatorRepository.findAll()).thenReturn(listPestNaturalPredator);
    }

    @Test
    public void readAllPestNaturalPredatorServiceTest(){
        assertThat(this.pestNaturalPredatorService.readAll())
                .containsExactlyInAnyOrder(this.pestNaturalPredator1,this.pestNaturalPredator2)
                .doesNotContain(this.pestNaturalPredator3)
                .isNotEmpty().doesNotContainNull();
    }

    @Test
    public void readByIdPestNaturalPredatorServiceTest() throws EntityNotFoundException {
        when(this.pestNaturalPredatorRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pestNaturalPredator1));
        assertThat(this.pestNaturalPredatorService.readById((long)1))
                .isEqualTo(this.pestNaturalPredator1);
    }

    @Test (expected = EntityNotFoundException.class)
    public void readByIdPestNaturalPredatorServiceEntityNotFoundExceptionTest() throws EntityNotFoundException {
        when(this.pestNaturalPredatorRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(null));

            this.pestNaturalPredatorService.readById((long)1);
            fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = EntityNotFoundException.class)
    public void deleteEntityNotFoundExceptionTest() throws AnyPersistenceException, EntityInUseException, EntityNotFoundException {
        when(this.pestNaturalPredatorRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(null));

            this.pestNaturalPredatorService.delete((long) 1);
            fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = EntityInUseException.class)
    public void deleteEntityInUseExceptionTest() throws AnyPersistenceException, EntityNotFoundException, EntityInUseException {
        when(this.pestNaturalPredatorRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pestNaturalPredator1));
        doThrow(DataIntegrityViolationException.class).when(this.pestNaturalPredatorRepository).delete(this.pestNaturalPredator1);

            this.pestNaturalPredatorService.delete((long) 1);
            fail("EntityInUseException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void deleteAnyPersistenceExceptionTest() throws EntityInUseException, EntityNotFoundException, AnyPersistenceException {
        when(this.pestNaturalPredatorRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pestNaturalPredator1));
        doThrow(IllegalArgumentException.class).when(this.pestNaturalPredatorRepository).delete(this.pestNaturalPredator1);

            this.pestNaturalPredatorService.delete((long) 1);
            fail("AnyPersistenceException it is not throws");
    }

    @Test
    public void deletePestSucessTest() {
        when(this.pestNaturalPredatorRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pestNaturalPredator1));
        doNothing().when(this.pestNaturalPredatorRepository).delete(this.pestNaturalPredator1);

        try {
            this.pestNaturalPredatorService.delete((long) 1);
        }  catch (Exception  e){
            fail("An exception throws, delete fails.");
        }
        when(this.pestNaturalPredatorRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(null));

        try {
            this.pestNaturalPredatorService.readById((long)1);
        } catch (EntityNotFoundException e) {
            assertThat(e.getClass()).isEqualTo(EntityNotFoundException.class);
        }
    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void createEntityAlreadyExistsExceptionTest() throws AnyPersistenceException, EntityAlreadyExistsException {
            this.pestNaturalPredatorService.create(this.pestNaturalPredator1);
            fail("EntityAlreadyExistsException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void createAnyPersistenceExceptionTest() throws EntityAlreadyExistsException, AnyPersistenceException {
        doThrow(IllegalArgumentException.class)
                .when(this.pestNaturalPredatorRepository).save(any());

            this.pestNaturalPredatorService.create(this.pestNaturalPredator3);
            fail("AnyPersistenceException it is not throws");
    }

    @Test
    public void createSucessTest() {
        when(this.pestNaturalPredatorRepository.save(this.pestNaturalPredator3))
                .thenReturn(this.pestNaturalPredator3);
        try {
            this.pestNaturalPredatorService.create(this.pestNaturalPredator3);
        }  catch (Exception  e){
            fail("An exception throws, delete fails.s");
        }
    }

    @Test (expected = EntityNotFoundException.class)
    public void updateEntityNotFoundExceptionTest() throws EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {
        when(this.pestNaturalPredatorRepository.findById((long)3))
                .thenReturn(java.util.Optional.ofNullable(null));

        this.pestNaturalPredator3.setUsualName("Test Update Case");
            this.pestNaturalPredatorService.update(this.pestNaturalPredator3);
            fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void updateEntityAlreadyExistsExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        PestNaturalPredator copyPest = this.pestNaturalPredator1;
        List<PestNaturalPredator> listPest = asList(this.pestNaturalPredator1,this.pestNaturalPredator2,copyPest);
        BDDMockito.when(this.pestNaturalPredatorRepository.findAll()).thenReturn(listPest);

        when(this.pestNaturalPredatorRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pestNaturalPredator1));

        this.pestNaturalPredator3.setUsualName("Test Update Case");
            this.pestNaturalPredatorService.update(this.pestNaturalPredator1);
            fail("EntityAlreadyExistsException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void updateAnyPersistenceExceptionTest() throws EntityNotFoundException, EntityAlreadyExistsException, AnyPersistenceException {
        when(this.pestNaturalPredatorRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pestNaturalPredator1));
        doThrow(IllegalArgumentException.class).when(this.pestNaturalPredatorRepository).saveAndFlush(any());

            this.pestNaturalPredator1.setUsualName("Test Fail");
            this.pestNaturalPredatorService.update(this.pestNaturalPredator1);
            fail("AnyPersistenceException it is not throws");
    }

    @Test
    public void updateSucessTest() throws EntityNotFoundException {
        when(this.pestNaturalPredatorRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pestNaturalPredator1));
        when(this.pestNaturalPredatorRepository.saveAndFlush(any(PestNaturalPredator.class)))
                .thenReturn(any(PestNaturalPredator.class));

        PestNaturalPredator pestNaturalPredatorTemp = this.pestNaturalPredatorService.readById((long) 1);

        assertThat(pestNaturalPredatorTemp.getUsualName()).isEqualToIgnoringCase("Calosoma granulatum Testa");

        try {
            pestNaturalPredatorTemp.setUsualName("Test sucess");
            this.pestNaturalPredatorService.update(pestNaturalPredatorTemp);
        }  catch (Exception  e){
            fail("An exception throws, delete fails.");
        }

        when(this.pestNaturalPredatorRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pestNaturalPredator1));


        assertThat(this.pestNaturalPredatorService.readById((long) 1).getUsualName()).isEqualTo("Test Sucess");
    }
}
