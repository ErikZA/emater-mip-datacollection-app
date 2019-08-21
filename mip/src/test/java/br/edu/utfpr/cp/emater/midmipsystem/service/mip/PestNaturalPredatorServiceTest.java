package br.edu.utfpr.cp.emater.midmipsystem.service.mip;


import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestNaturalPredator;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mip.PestNaturalPredatorRepository;
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
    public void pestNaturalPredatorServiceTestReadAllPestNaturalPredatorService(){
        assertThat(this.pestNaturalPredatorService.readAll())
                .containsExactlyInAnyOrder(this.pestNaturalPredator1,this.pestNaturalPredator2)
                .doesNotContain(this.pestNaturalPredator3)
                .isNotEmpty().doesNotContainNull();
        verify(this.pestNaturalPredatorRepository, times(1)).findAll();
        verifyNoMoreInteractions(this.pestNaturalPredatorRepository);
    }

    @Test
    public void pestNaturalPredatorServiceTestReadByIdPestNaturalPredatorService() throws EntityNotFoundException {
        when(this.pestNaturalPredatorRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pestNaturalPredator1));
        assertThat(this.pestNaturalPredatorService.readById((long)1))
                .isEqualTo(this.pestNaturalPredator1);
        verify(this.pestNaturalPredatorRepository, times(1)).findById((long)1);
        verifyNoMoreInteractions(this.pestNaturalPredatorRepository);
    }

    @Test
    public void pestNaturalPredatorServiceTestReadByIdPestNaturalPredatorServiceEntityNotFoundException() {
        when(this.pestNaturalPredatorRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(null));

        try {
            this.pestNaturalPredatorService.readById((long)1);
            fail("EntityNotFoundException it is not throws");
        }  catch (EntityNotFoundException  e){
            Assertions.assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }
        verify(this.pestNaturalPredatorRepository, times(1)).findById((long)1);
        verifyNoMoreInteractions(this.pestNaturalPredatorRepository);
    }

    @Test
    public void pestNaturalPredatorServiceTestDeleteEntityNotFoundException() throws AnyPersistenceException, EntityInUseException {
        when(this.pestNaturalPredatorRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(null));
        try {
            this.pestNaturalPredatorService.delete(this.pestNaturalPredator1.getId());
            fail("EntityNotFoundException it is not throws");
        }  catch (EntityNotFoundException  e){
            Assertions.assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }
        verify(this.pestNaturalPredatorRepository, times(1)).findById((long)1);
        verifyNoMoreInteractions(this.pestNaturalPredatorRepository);
    }

    @Test
    public void pestNaturalPredatorServiceTestDeleteEntityInUseException() throws  AnyPersistenceException, EntityNotFoundException {
        when(this.pestNaturalPredatorRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pestNaturalPredator1));
        doThrow(DataIntegrityViolationException.class).when(this.pestNaturalPredatorRepository).delete(this.pestNaturalPredator1);
        try {
            this.pestNaturalPredatorService.delete(this.pestNaturalPredator1.getId());
            fail("EntityInUseException it is not throws");
        }  catch (EntityInUseException  e){
            Assertions.assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }
        verify(this.pestNaturalPredatorRepository, times(1)).findById((long)1);
        verify(this.pestNaturalPredatorRepository, times(1)).delete(this.pestNaturalPredator1);
        verifyNoMoreInteractions(this.pestNaturalPredatorRepository);
    }

    @Test
    public void pestNaturalPredatorServiceTestDeleteAnyPersistenceException() throws  EntityInUseException, EntityNotFoundException {
        when(this.pestNaturalPredatorRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pestNaturalPredator1));
        doThrow(IllegalArgumentException.class).when(this.pestNaturalPredatorRepository).delete(this.pestNaturalPredator1);

        try {
            this.pestNaturalPredatorService.delete(this.pestNaturalPredator1.getId());
            fail("AnyPersistenceException it is not throws");
        }  catch (AnyPersistenceException  e){
            Assertions.assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }

        verify(this.pestNaturalPredatorRepository, times(1)).findById((long)1);
        verify(this.pestNaturalPredatorRepository, times(1)).delete(this.pestNaturalPredator1);
        verifyNoMoreInteractions(this.pestNaturalPredatorRepository);
    }

    @Test
    public void pestNaturalPredatorServiceTestDeletePestSucess() {
        when(this.pestNaturalPredatorRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pestNaturalPredator1));
        doNothing().when(this.pestNaturalPredatorRepository).delete(this.pestNaturalPredator1);

        try {
            this.pestNaturalPredatorService.delete(this.pestNaturalPredator1.getId());
        }  catch (Exception  e){
            fail("An exception throws, delete fails.");
        }

        verify(this.pestNaturalPredatorRepository, times(1)).findById((long)1);
        verify(this.pestNaturalPredatorRepository, times(1)).delete(this.pestNaturalPredator1);
        verifyNoMoreInteractions(this.pestNaturalPredatorRepository);
    }

    @Test
    public void pestNaturalPredatorServiceTestCreateEntityAlreadyExistsException() throws AnyPersistenceException {
        try {
            this.pestNaturalPredatorService.create(this.pestNaturalPredator1);
            fail("EntityAlreadyExistsException it is not throws");
        }  catch (EntityAlreadyExistsException e){
            Assertions.assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }

        verify(this.pestNaturalPredatorRepository, times(1)).findAll();
        verifyNoMoreInteractions(this.pestNaturalPredatorRepository);
    }

    @Test
    public void pestNaturalPredatorServiceTestCreateAnyPersistenceException() throws EntityAlreadyExistsException {
        doThrow(IllegalArgumentException.class)
                .when(this.pestNaturalPredatorRepository).save(any());

        try {
            this.pestNaturalPredatorService.create(this.pestNaturalPredator3);
            fail("AnyPersistenceException it is not throws");
        }  catch (AnyPersistenceException  e){
            Assertions.assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }

        verify(this.pestNaturalPredatorRepository, times(1)).findAll();
        verify(this.pestNaturalPredatorRepository, times(3)).save(any());
        verifyNoMoreInteractions(this.pestNaturalPredatorRepository);
    }

    @Test
    public void pestNaturalPredatorServiceTestCreateSucess() {
        when(this.pestNaturalPredatorRepository.save(this.pestNaturalPredator3))
                .thenReturn(this.pestNaturalPredator3);
        try {
            this.pestNaturalPredatorService.create(this.pestNaturalPredator3);
        }  catch (Exception  e){
            fail("An exception throws, delete fails.s");
        }
        verify(this.pestNaturalPredatorRepository, times(1)).findAll();
        verify(this.pestNaturalPredatorRepository, times(1)).save(this.pestNaturalPredator3);
        verifyNoMoreInteractions(this.pestNaturalPredatorRepository);
    }

    @Test
    public void pestNaturalPredatorServiceTestUpdateEntityNotFoundException() throws EntityAlreadyExistsException, AnyPersistenceException {
        when(this.pestNaturalPredatorRepository.findById((long)3))
                .thenReturn(java.util.Optional.ofNullable(null));
        try {
            this.pestNaturalPredatorService.update(this.pestNaturalPredator3);
            fail("EntityNotFoundException it is not throws");
        }  catch (EntityNotFoundException  e){
            Assertions.assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }
        verify(this.pestNaturalPredatorRepository, times(1)).findById((long)3);
        verifyNoMoreInteractions(this.pestNaturalPredatorRepository);
    }

    @Test
    public void pestNaturalPredatorServiceTestUpdateEntityAlreadyExistsException() throws EntityNotFoundException, AnyPersistenceException {
        PestNaturalPredator copyPest = this.pestNaturalPredator1;
        List<PestNaturalPredator> listPest = asList(this.pestNaturalPredator1,this.pestNaturalPredator2,copyPest);
        BDDMockito.when(pestNaturalPredatorRepository.findAll()).thenReturn(listPest);

        when(this.pestNaturalPredatorRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pestNaturalPredator1));

        try {
            this.pestNaturalPredatorService.update(this.pestNaturalPredator1);
            fail("EntityAlreadyExistsException it is not throws");
        }  catch (EntityAlreadyExistsException  e){
            Assertions.assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }
        verify(this.pestNaturalPredatorRepository, times(1)).findById((long)1);
        verify(this.pestNaturalPredatorRepository,times(1)).findAll();
        verifyNoMoreInteractions(this.pestNaturalPredatorRepository);
    }

    @Test
    public void pestNaturalPredatorServiceTestUpdateAnyPersistenceException() throws EntityNotFoundException,EntityAlreadyExistsException {
        when(this.pestNaturalPredatorRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pestNaturalPredator1));
        doThrow(IllegalArgumentException.class).when(this.pestNaturalPredatorRepository).saveAndFlush(any());

        try {
            this.pestNaturalPredator1.setUsualName("Test Fail");
            this.pestNaturalPredatorService.update(this.pestNaturalPredator1);
            fail("AnyPersistenceException it is not throws");
        }  catch (AnyPersistenceException  e){
            Assertions.assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }
        verify(this.pestNaturalPredatorRepository, times(1)).findById((long)1);
        verify(this.pestNaturalPredatorRepository, times(1)).saveAndFlush(this.pestNaturalPredator1);
        verify(this.pestNaturalPredatorRepository,times(1)).findAll();
        verifyNoMoreInteractions(this.pestNaturalPredatorRepository);
    }

    @Test
    public void pestNaturalPredatorServiceTestUpdateSucess() {
        when(this.pestNaturalPredatorRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.pestNaturalPredator1));

        when(this.pestNaturalPredatorRepository.saveAndFlush(this.pestNaturalPredator1)).thenReturn(this.pestNaturalPredator1);

        assertThat(this.pestNaturalPredator1.getUsualName()).isEqualToIgnoringCase("Calosoma granulatum Testa");

        try {
            this.pestNaturalPredator1.setUsualName("Test sucess");
            this.pestNaturalPredatorService.update(this.pestNaturalPredator1);
        }  catch (Exception  e){
            fail("An exception throws, delete fails.");
        }
        verify(this.pestNaturalPredatorRepository, times(1)).findById((long)1);
        verify(this.pestNaturalPredatorRepository, times(1)).saveAndFlush(this.pestNaturalPredator1);
        verify(this.pestNaturalPredatorRepository,times(1)).findAll();
        verifyNoMoreInteractions(this.pestNaturalPredatorRepository);
    }



}
