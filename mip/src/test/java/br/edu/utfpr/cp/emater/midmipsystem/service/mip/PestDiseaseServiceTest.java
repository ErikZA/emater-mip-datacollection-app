package br.edu.utfpr.cp.emater.midmipsystem.service.mip;


import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestDisease;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mip.PestDiseaseRepository;
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
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PestDiseaseServiceTest {

    @MockBean
    private PestDiseaseRepository pestDiseaseRepository;

    @Autowired
    private PestDiseaseService pestDiseaseService;

    private PestDisease pestDisease1, pestDisease2, pestDisease3;

    @Before
    public void setUp(){

        this.pestDisease1 = PestDisease.builder().id((long)1).usualName("Lagarta com Nomuraea rileyi (Doença Branca)").build();
        this.pestDisease2 = PestDisease.builder().id((long)2).usualName("Lagarta com Baculovírus (Doença Preta)").build();
        this.pestDisease3 = PestDisease.builder().id((long)3).usualName("Lagarta com Traconimose (Doença Amarela)").build();

        List<PestDisease> listPestDisease= asList(this.pestDisease1,this.pestDisease2);
        BDDMockito.when(this.pestDiseaseRepository.findAll()).thenReturn(listPestDisease);
    }


    @Test
    public void pestDiseaseServiceTestReadAllPestNaturalPredatorService(){
        assertThat(this.pestDiseaseService.readAll())
                .containsExactlyInAnyOrder(this.pestDisease1,this.pestDisease2)
                .doesNotContain(this.pestDisease3)
                .isNotEmpty().doesNotContainNull();
    }

    @Test
    public void pestDiseaseServiceTestReadByIdPestNaturalPredatorService() throws EntityNotFoundException {
        when(this.pestDiseaseRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pestDisease1));
        assertThat(this.pestDiseaseService.readById((long)1))
                .isEqualTo(this.pestDisease1);
    }

    @Test (expected = EntityNotFoundException.class)
    public void pestDiseaseServiceTestReadByIdPestNaturalPredatorServiceEntityNotFoundException() throws EntityNotFoundException {
        when(this.pestDiseaseRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(null));

        this.pestDiseaseService.readById((long)1);
        fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = EntityNotFoundException.class)
    public void pestDiseaseServiceTestDeleteEntityNotFoundException() throws AnyPersistenceException, EntityInUseException, EntityNotFoundException {
        when(this.pestDiseaseRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(null));

        this.pestDiseaseService.delete(this.pestDisease1.getId());
        fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = EntityInUseException.class)
    public void pestDiseaseServiceTestDeleteEntityInUseException() throws AnyPersistenceException, EntityNotFoundException, EntityInUseException {
        when(this.pestDiseaseRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pestDisease1));
        doThrow(DataIntegrityViolationException.class).when(this.pestDiseaseRepository).delete(this.pestDisease1);

        this.pestDiseaseService.delete(this.pestDisease1.getId());
        fail("EntityInUseException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void pestDiseaseServiceTestDeleteAnyPersistenceException() throws EntityInUseException, EntityNotFoundException, AnyPersistenceException {
        when(this.pestDiseaseRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pestDisease1));
        doThrow(IllegalArgumentException.class).when(this.pestDiseaseRepository).delete(this.pestDisease1);

        this.pestDiseaseService.delete(this.pestDisease1.getId());
        fail("AnyPersistenceException it is not throws");
    }

    @Test
    public void pestDiseaseServiceTestDeletePestSucess() {
        when(this.pestDiseaseRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pestDisease1));
        doNothing().when(this.pestDiseaseRepository).delete(this.pestDisease1);

        try {
            this.pestDiseaseService.delete(this.pestDisease1.getId());
        }  catch (Exception  e){
            fail("An exception throws, delete fails.");
        }
    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void pestDiseaseServiceTestCreateEntityAlreadyExistsException() throws AnyPersistenceException, EntityAlreadyExistsException {
        this.pestDiseaseService.create(this.pestDisease1);
        fail("EntityAlreadyExistsException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void pestDiseaseServiceTestCreateAnyPersistenceException() throws EntityAlreadyExistsException, AnyPersistenceException {
        doThrow(IllegalArgumentException.class)
                .when(this.pestDiseaseRepository).save(any());

        this.pestDiseaseService.create(this.pestDisease3);
        fail("AnyPersistenceException it is not throws");
    }

    @Test
    public void pestDiseaseServiceTestCreateSucess() {
        when(this.pestDiseaseRepository.save(this.pestDisease3))
                .thenReturn(this.pestDisease3);
        try {
            this.pestDiseaseService.create(this.pestDisease3);
        }  catch (Exception  e){
            fail("An exception throws, delete fails.s");
        }
    }

    @Test (expected = EntityNotFoundException.class)
    public void pestDiseaseServiceTestUpdateEntityNotFoundException() throws EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {
        when(this.pestDiseaseRepository.findById((long)3))
                .thenReturn(java.util.Optional.ofNullable(null));

        this.pestDiseaseService.update(this.pestDisease3);
        fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void pestDiseaseServiceTestUpdateEntityAlreadyExistsException() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        PestDisease copyPest = this.pestDisease1;
        List<PestDisease> listPest = asList(this.pestDisease1,this.pestDisease2,copyPest);
        BDDMockito.when(pestDiseaseRepository.findAll()).thenReturn(listPest);

        when(this.pestDiseaseRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pestDisease1));

        this.pestDiseaseService.update(this.pestDisease1);
        fail("EntityAlreadyExistsException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void pestDiseaseServiceTestUpdateAnyPersistenceException() throws EntityNotFoundException, EntityAlreadyExistsException, AnyPersistenceException {
        when(this.pestDiseaseRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pestDisease1));
        doThrow(IllegalArgumentException.class).when(this.pestDiseaseRepository).saveAndFlush(any());

        this.pestDisease1.setUsualName("Test Fail");
        this.pestDiseaseService.update(this.pestDisease1);
        fail("AnyPersistenceException it is not throws");
    }

    @Test
    public void pestDiseaseServiceTestUpdateSucess() {
        when(this.pestDiseaseRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pestDisease1));

        when(this.pestDiseaseRepository.saveAndFlush(this.pestDisease1)).thenReturn(this.pestDisease1);

        assertThat(this.pestDisease1.getUsualName()).isEqualToIgnoringCase("Lagarta com Nomuraea rileyi (Doença Branca)");

        try {
            this.pestDisease1.setUsualName("Test sucess");
            this.pestDiseaseService.update(this.pestDisease1);
        }  catch (Exception  e){
            fail("An exception throws, delete fails.");
        }
    }

}
