package br.edu.utfpr.cp.emater.midmipsystem.service.survey;


import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.HarvestRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.SurveyRepository;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.FieldService;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HarvestServiceTest {

    @MockBean
    private HarvestRepository harvestRepository;
    @MockBean
    private FieldService fieldService;
    @MockBean
    private SurveyRepository surveyRepository;

    @Autowired
    private HarvestService harvestService;

    private Harvest harvest1, harvest2, harvest3;


    @Before
    public void SetUp() throws ParseException {
        this.harvest1 = Harvest.builder()
                        .name("Safra 2006/2007")
                        .begin(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2006"))
                        .end(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-03-2007"))
                        .build();
        this.harvest1.setId((long) 1);

        this.harvest2 = Harvest.builder()
                        .name("Safra 2007/2008")
                        .begin(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2007"))
                        .end(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-03-2008"))
                        .build();
        this.harvest2.setId((long) 2);

        this.harvest3 = Harvest.builder()
                        .name("Safra 2008/2009")
                        .begin(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2008"))
                        .end(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-03-2009"))
                        .build();
        this.harvest3.setId((long) 3);


        List<Harvest> listHarvest = asList(this.harvest1,this.harvest2);
        BDDMockito.when(harvestRepository.findAll())
                .thenReturn(listHarvest);

    }

    @Test
    public void harvestServiceTestReadAllHarvest(){
        assertThat(this.harvestService.readAll())
                .doesNotContainNull()
                .containsExactlyInAnyOrder(harvest1,harvest2)
                .doesNotContain(harvest3);
    }

    @Test
    public void harvestServiceTestReadByIdHarvest() throws EntityNotFoundException {
        BDDMockito.when(harvestRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.harvest1));
        assertThat(this.harvestService.readById(harvest1.getId()))
                .isNotNull()
                .isEqualTo(harvest1);
    }


    @Test (expected = EntityNotFoundException.class)
    public void harvestServiceTestReadByIdHarvestEntityNotFoundException() throws EntityNotFoundException {
        BDDMockito.when(harvestRepository.findById((long) 3))
                .thenReturn(java.util.Optional.ofNullable(null));
            this.harvestService.readById(harvest3.getId());
            fail("EntityNotFoundException it is not throws");
    }


    @Test (expected = EntityAlreadyExistsException.class)
    public void harvestServiceTestCreateHarvestEntityNotFoundException() throws AnyPersistenceException, EntityAlreadyExistsException {
            this.harvestService.create(this.harvest1);
            fail("EntityAlreadyExistsException it is not throws");
    }

    @Test
    public void harvestServiceTestCreateHarvest() throws EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {
        BDDMockito.when(harvestRepository.save(this.harvest3))
                .thenReturn(this.harvest3);
        this.harvestService.create(this.harvest3);
        //fazer uma verificação com mock e lista ou verificar se não foi disparada nenhuma excessão?
    }


    @Test (expected = EntityNotFoundException.class)
    public void harvestServiceTestDeleteHarvestEntityNotFoundException() throws AnyPersistenceException, EntityInUseException, EntityNotFoundException {
        BDDMockito.when(harvestRepository.findById((long) 3))
                .thenReturn(java.util.Optional.ofNullable(null));

            this.harvestService.delete(harvest3.getId());
            fail("EntityNotFoundException it is not throws");
    }

    @Test
    public void harvestServiceTestDeleteHarvest() throws Exception {
        BDDMockito.doNothing().when(harvestRepository).delete(harvest1);
        BDDMockito.when(harvestRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.harvest1));
        this.harvestService.delete(this.harvest1.getId());
    }


    @Test (expected = EntityInUseException.class)
    public void harvestServiceTestDeleteHarvestEntityInUseException() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        BDDMockito.when(harvestRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.harvest1));
        BDDMockito.doThrow(DataIntegrityViolationException.class)
                .when(harvestRepository).delete(this.harvest1);

            this.harvestService.delete(this.harvest1.getId());
            fail("EntityInUseException it is not throws");
    }


    @Test (expected = EntityNotFoundException.class)
    public void harvestServiceTestUpdateHarvestEntityNotFoundException() throws Exception {
        BDDMockito.when(harvestRepository.findById((long) 3))
                .thenReturn(java.util.Optional.ofNullable(null));
            this.harvestService.update(this.harvest3);
            fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void harvestServiceTestUpdateHarvestAnyPersistenceException() throws Exception {
        BDDMockito.when(harvestRepository.findById((long) 2))
                .thenReturn(java.util.Optional.ofNullable(this.harvest2));
        BDDMockito.when(harvestRepository.saveAndFlush(any()))
                .thenThrow(IllegalArgumentException.class);

            this.harvest2.setName("01-10-2777");
            this.harvestService.update(this.harvest2);
            fail("AnyPersistenceException it is not throws");
    }

    @Test
    public void harvestServiceTestUpdateHarvest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException, ParseException {
        BDDMockito.when(harvestRepository.findById((long) 2))
                .thenReturn(java.util.Optional.ofNullable(this.harvest2));
        BDDMockito.when(harvestRepository.saveAndFlush(this.harvest2))
                .thenReturn(this.harvest2);

//        assertThat(this.harvest2.getName()).isEqualToIgnoringCase("Safra 2007/2008");
//        assertThat(this.harvest2.getEnd().toString()).isEqualTo("2008-03-01");
//        assertThat(this.harvest2.getBegin().toString()).isEqualTo("2007-10-01");
       //colocar em uma lista e atualizar a lista mock?

        this.harvest2.setName("Safra 2111/2112");
        this.harvest2.setEnd(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2088"));
        this.harvest2.setBegin(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2088"));

        this.harvestService.update(this.harvest2);
    }

    @Test (expected = AnyPersistenceException.class)//tenta salvar um null
    public void harvestServiceTestCreateHarvestAnyPersistenceException() throws EntityAlreadyExistsException, AnyPersistenceException {
        BDDMockito.when(harvestRepository.save(any()))
                .thenThrow(IllegalArgumentException.class);
            this.harvestService.create(null);
            fail("AnyPersistenceException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class) //tenta deletar um null
    public void harvestServiceTestDeleteHarvestAnyPersistenceException() throws EntityInUseException, EntityNotFoundException, AnyPersistenceException {
        BDDMockito.when(harvestRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.harvest1));
        BDDMockito.doThrow(IllegalArgumentException.class)
                .when(harvestRepository).delete(any());
            this.harvestService.delete(this.harvest1.getId());
            fail("AnyPersistenceException it is not throws");
    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void harvestServiceTestUpdateHarvestEntityAlreadyExistsException() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        Harvest harvestCopy = this.harvest1;
        List<Harvest> listHarvest = asList(this.harvest1,this.harvest2,harvestCopy);
        BDDMockito.when(this.harvestRepository.findAll())
                .thenReturn(listHarvest);
        BDDMockito.when(harvestRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.harvest1));

            this.harvest1.setName("Safra 2999/2999");
            this.harvestService.update(this.harvest1);
            fail("EntityAlreadyExistsException it is not throws");
    }

}
