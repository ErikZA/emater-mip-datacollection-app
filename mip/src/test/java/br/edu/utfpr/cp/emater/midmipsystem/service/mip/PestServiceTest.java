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
    public void pestServiceTestReadAllPestService(){
        assertThat(this.pestService.readAll())
                .containsExactlyInAnyOrder(this.pest1,this.pest2)
                .doesNotContain(this.pest3)
                .isNotEmpty().doesNotContainNull();
    }

    @Test
    public void pestServiceTestReadByIdPestService() throws EntityNotFoundException {
        when(this.pestRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pest1));
        assertThat(this.pestService.readById((long)1))
                .isEqualTo(this.pest1);
    }

    @Test (expected = EntityNotFoundException.class)
    public void pestServiceTestReadByIdPestServiceEntityNotFoundException() throws EntityNotFoundException {
        when(this.pestRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(null));

            this.pestService.readById((long)1);
            fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = EntityNotFoundException.class)
    public void pestServiceTestDeletePestServiceEntityNotFoundException() throws AnyPersistenceException, EntityInUseException, EntityNotFoundException {
        when(this.pestRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(null));
            this.pestService.delete(this.pest1.getId());
            fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = EntityInUseException.class)
    public void pestServiceTestDeletePestServiceEntityInUseException() throws AnyPersistenceException, EntityNotFoundException, EntityInUseException {
        when(this.pestRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pest1));
        doThrow(DataIntegrityViolationException.class).when(this.pestRepository).delete(this.pest1);

            this.pestService.delete(this.pest1.getId());
            fail("EntityInUseException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void pestServiceTestDeletePestServiceAnyPersistenceException() throws EntityInUseException, EntityNotFoundException, AnyPersistenceException {
        when(this.pestRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pest1));
        doThrow(IllegalArgumentException.class).when(this.pestRepository).delete(this.pest1);

            this.pestService.delete(this.pest1.getId());
            fail("AnyPersistenceException it is not throws");
    }

    @Test
    public void pestServiceTestDeletePestSucess() {
        when(this.pestRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pest1));
        doNothing().when(this.pestRepository).delete(this.pest1);

        try {
            this.pestService.delete(this.pest1.getId());
        }  catch (Exception  e){
            fail("An exception throws, delete fails.");
        }
    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void createPestEntityAlreadyExistsException() throws AnyPersistenceException, EntityAlreadyExistsException {
            this.pestService.create(this.pest1);
            fail("EntityAlreadyExistsException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void pestServiceTestCreatePestAnyPersistenceException() throws EntityAlreadyExistsException, AnyPersistenceException {
        doThrow(IllegalArgumentException.class)
                .when(this.pestRepository).save(any());

            this.pestService.create(this.pest3);
            fail("AnyPersistenceException it is not throws");
    }

    @Test
    public void pestServiceTestCreatePestSucess() {
        when(this.pestRepository.save(this.pest3))
                .thenReturn(this.pest3);
        try {
            this.pestService.create(this.pest3);
        }  catch (Exception  e){
            fail("An exception throws, delete fails.s");
        }
    }

    @Test (expected = EntityNotFoundException.class)
    public void pestServiceTestUpdatePestEntityNotFoundException() throws EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {
        when(this.pestRepository.findById((long)3))
                .thenReturn(java.util.Optional.ofNullable(null));

            this.pestService.update(this.pest3);
            fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void pestServiceTestUpdatePestEntityAlreadyExistsException() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        Pest copyPest = this.pest1;
        List<Pest> listPest = asList(this.pest1,this.pest2,copyPest);
        BDDMockito.when(pestRepository.findAll()).thenReturn(listPest);

        when(this.pestRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pest1));

            this.pestService.update(this.pest1);
            fail("EntityAlreadyExistsException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void pestServiceTestUpdatePestAnyPersistenceException() throws EntityNotFoundException, EntityAlreadyExistsException, AnyPersistenceException {
        when(this.pestRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pest1));
        doThrow(IllegalArgumentException.class).when(this.pestRepository).saveAndFlush(any());

            this.pest1.setUsualName("Test Fail");
            this.pestService.update(this.pest1);
            fail("AnyPersistenceException it is not throws");
    }

    @Test
    public void pestServiceTestUpdatePestSucess() {
        when(this.pestRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pest1));

        when(this.pestRepository.saveAndFlush(this.pest1)).thenReturn(this.pest1);

        assertThat(this.pest1.getUsualName()).isEqualToIgnoringCase("Lagarta Amarela da soja");

        try {
            this.pest1.setUsualName("Test sucess");
            this.pest1.setPestSize(PestSize.GREATER_15CM);
            this.pest1.setScientificName("Test Cientific");
            this.pestService.update(this.pest1);
        }  catch (Exception  e){
            fail("An exception throws, delete fails.");
        }
    }

}
