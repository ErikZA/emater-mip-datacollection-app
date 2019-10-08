package br.edu.utfpr.cp.emater.midmipsystem.service.mip;


import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.Pest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestSize;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mip.PestRepository;
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
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PestServiceTest {

    @MockBean
    private PestRepository pestRepository;

    @Autowired
    private PestService pestService;

    private Pest pest1, pest2, pest3;

    @Before
    public void SetUp(){
        this.pest1 = Pest.builder().id((long)1).usualName("Lagarta Amarela da soja").scientificName("Anticarsia gemmatalis").pestSize(PestSize.GREATER_15CM).build();
        this.pest2 = Pest.builder().id((long)2).usualName("Lagarta Marron da soja").scientificName("Anticarsia gemmatalis").pestSize(PestSize.SMALLER_15CM).build();
        this.pest3 = Pest.builder().id((long)3).usualName("Falsa medideira Verde").scientificName("Chrysodeixis spp.").pestSize(PestSize.GREATER_15CM).build();

        List<Pest> listPest = asList(this.pest1,this.pest2);
        BDDMockito.when(pestRepository.findAll()).thenReturn(listPest);
    }

    @Test
    public void readAllPestServiceTest(){
        assertThat(this.pestService.readAll())
                .containsExactlyInAnyOrder(this.pest1,this.pest2)
                .doesNotContain(this.pest3)
                .isNotEmpty().doesNotContainNull();
    }

    @Test
    public void readByIdPestServiceTest() throws EntityNotFoundException {
        when(this.pestRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pest1));
        assertThat(this.pestService.readById((long)1))
                .isEqualTo(this.pest1);
    }

    @Test (expected = EntityNotFoundException.class)
    public void readByIdPestServiceEntityNotFoundExceptionTest() throws EntityNotFoundException {
        when(this.pestRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(null));

            this.pestService.readById((long)1);
            fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = EntityNotFoundException.class)
    public void deletePestServiceEntityNotFoundExceptionTest() throws AnyPersistenceException, EntityInUseException, EntityNotFoundException {
        when(this.pestRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(null));
            this.pestService.delete((long) 1);
            fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = EntityInUseException.class)
    public void deletePestServiceEntityInUseExceptionTest() throws AnyPersistenceException, EntityNotFoundException, EntityInUseException {
        when(this.pestRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pest1));
        doThrow(DataIntegrityViolationException.class).when(this.pestRepository).delete(this.pest1);

            this.pestService.delete((long) 1);
            fail("EntityInUseException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void deletePestServiceAnyPersistenceExceptionTest() throws EntityInUseException, EntityNotFoundException, AnyPersistenceException {
        when(this.pestRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pest1));
        doThrow(IllegalArgumentException.class).when(this.pestRepository).delete(this.pest1);

            this.pestService.delete((long) 1);
            fail("AnyPersistenceException it is not throws");
    }

    @Test
    public void deletePestSucessTest() {
        when(this.pestRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pest1));
        doNothing().when(this.pestRepository).delete(this.pest1);

        try {
            this.pestService.delete((long) 1);
        }  catch (Exception  e){
            fail("An exception throws, delete fails.");
        }
        when(this.pestRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(null));
        try {
            this.pestService.readById((long)1);
        } catch (EntityNotFoundException e) {
            assertThat(e.getClass()).isEqualTo(EntityNotFoundException.class);
        }
    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void createPestEntityAlreadyExistsExceptionTest() throws AnyPersistenceException, EntityAlreadyExistsException {
            this.pestService.create(this.pest1);
            fail("EntityAlreadyExistsException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void createPestAnyPersistenceExceptionTest() throws EntityAlreadyExistsException, AnyPersistenceException {
        doThrow(IllegalArgumentException.class)
                .when(this.pestRepository).save(any());

            this.pestService.create(this.pest3);
            fail("AnyPersistenceException it is not throws");
    }

    @Test
    public void createPestSucessTest() throws EntityNotFoundException {
        when(this.pestRepository.save(this.pest3))
                .thenReturn(this.pest3);
        try {
            this.pest3.setUsualName("Largata Amarela");
            this.pestService.create(this.pest3);
        }  catch (Exception  e){
            fail("An exception throws, delete fails.s");
        }
        when(this.pestRepository.findById((long)3))
                .thenReturn(java.util.Optional.ofNullable(this.pest3));
        assertThat(this.pestService.readById((long) 3).getUsualName()).isEqualTo("Largata Amarela");
    }

    @Test (expected = EntityNotFoundException.class)
    public void updatePestEntityNotFoundExceptionTest() throws EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {
        when(this.pestRepository.findById((long)3))
                .thenReturn(java.util.Optional.ofNullable(null));

        this.pest3.setUsualName("Test Fail");
        this.pestService.update(this.pest3);
            fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void updatePestEntityAlreadyExistsExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        Pest copyPest = this.pest1;
        List<Pest> listPest = asList(this.pest1,this.pest2,copyPest);
        BDDMockito.when(pestRepository.findAll()).thenReturn(listPest);

        when(this.pestRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pest1));

        this.pest1.setUsualName("Test Fail");
        this.pestService.update(this.pest1);
            fail("EntityAlreadyExistsException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void updatePestAnyPersistenceExceptionTest() throws EntityNotFoundException, EntityAlreadyExistsException, AnyPersistenceException {
        when(this.pestRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pest1));
        doThrow(IllegalArgumentException.class).when(this.pestRepository).saveAndFlush(any());

            this.pest1.setUsualName("Test Fail");
            this.pestService.update(this.pest1);
            fail("AnyPersistenceException it is not throws");
    }

    @Test
    public void updatePestSucessTest() throws EntityNotFoundException {
        when(this.pestRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pest1));
        when(this.pestRepository.saveAndFlush(any(Pest.class))).thenReturn(any(Pest.class));

        Pest pestTemp = this.pestService.readById((long) 1);
        assertThat(pestTemp.getUsualName()).isEqualToIgnoringCase("Lagarta Amarela da soja");

        try {
            pestTemp.setUsualName("Test sucess");
            pestTemp.setPestSize(PestSize.GREATER_15CM);
            pestTemp.setScientificName("Test Cientific");
            this.pestService.update(pestTemp);
        }  catch (Exception  e){
            fail("An exception throws, delete fails.");
        }
        when(this.pestRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(pestTemp));
        this.pest1 = this.pestService.readById((long) 1);
        assertThat(this.pest1.getUsualName()).isEqualTo("Test Sucess");
        assertThat(this.pest1.getScientificName()).isEqualTo("Test Cientific");
        assertThat(this.pest1.getPestSize()).isEqualTo(PestSize.GREATER_15CM);
    }

}
