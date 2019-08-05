package br.edu.utfpr.cp.emater.midmipsystem.service.survey;


import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.*;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.HarvestRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.SurveyRepository;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.FieldService;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.MacroRegionRepository;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HarvestServiceTest {

    @MockBean
    private HarvestRepository harvestRepository;
    @MockBean
    private MacroRegionRepository macroRegionRepository;
    @MockBean
    private RegionRepository regionRepository;
    @MockBean
    private CityRepository cityRepository;
    @MockBean
    private FarmerRepository farmerRepository;
    @MockBean
    private SupervisorRepository supervisorRepository;
    @MockBean
    private FieldRepository fieldRepository;
    @MockBean
    private FieldService fieldService;
    @MockBean
    private SurveyRepository surveyRepository;



    @Autowired
    private HarvestService harvestService;

    @Autowired
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
    public void test01ReadAllHarvest(){
        assertThat(this.harvestService.readAll())
                .doesNotContainNull()
                .containsExactlyInAnyOrder(harvest1,harvest2)
                .doesNotContain(harvest3);
    }

    @Test
    public void test02ReadByIdHarvest() throws EntityNotFoundException {
        BDDMockito.when(harvestRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.harvest1));
        assertThat(this.harvestService.readById(harvest1.getId()))
                .isNotNull()
                .isEqualTo(harvest1);
    }


    @Test (expected = EntityNotFoundException.class)
    public void test03ReadByIdHarvestEntityNotFoundException() throws EntityNotFoundException {
        BDDMockito.when(harvestRepository.findById((long) 3))
                .thenReturn(java.util.Optional.ofNullable(null));
        this.harvestService.readById(harvest3.getId());
    }


    @Test (expected = EntityAlreadyExistsException.class)
    public void test04CreateHarvestEntityNotFoundException() throws EntityAlreadyExistsException, AnyPersistenceException {
        this.harvestService.create(this.harvest1);
    }

    @Test
    public void test05CreateHarvest() throws EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {
        BDDMockito.when(harvestRepository.save(this.harvest3))
                .thenReturn(this.harvest3);
        this.harvestService.create(this.harvest3);
    }


    @Test (expected = EntityNotFoundException.class)
    public void test06DeleteHarvestEntityNotFoundException() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        BDDMockito.when(harvestRepository.findById((long) 3))
                .thenReturn(java.util.Optional.ofNullable(null));
        this.harvestService.delete(harvest3.getId());
    }

    @Test
    public void test07DeleteHarvest() throws Exception {
        BDDMockito.doNothing().when(harvestRepository).delete(harvest1);
        BDDMockito.when(harvestRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.harvest1));
        this.harvestService.delete(this.harvest1.getId());
    }


    @Test (expected = EntityInUseException.class)
    public void test08DeleteHarvestEntityInUseException() throws Exception{
        BDDMockito.when(harvestRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.harvest1));
        BDDMockito.doThrow(DataIntegrityViolationException.class)
                .when(harvestRepository).delete(this.harvest1);
        this.harvestService.delete(this.harvest1.getId());
    }


    @Test (expected = EntityNotFoundException.class)
    public void test09UpdateHarvestEntityNotFoundException() throws Exception {
        BDDMockito.when(harvestRepository.findById((long) 3))
                .thenReturn(java.util.Optional.ofNullable(null));
        this.harvestService.update(this.harvest3);
    }

    @Test (expected = AnyPersistenceException.class)//adiciona datas nullas!!
    public void test10UpdateHarvestAnyPersistenceException() throws Exception {
        BDDMockito.when(harvestRepository.findById((long) 2))
                .thenReturn(java.util.Optional.ofNullable(this.harvest2));
        BDDMockito.when(harvestRepository.saveAndFlush(this.harvest2))
                .thenThrow(IllegalArgumentException.class);//deveria lançar a exception
        this.harvest2.setEnd(null);
        this.harvest2.setBegin(null);
        this.harvestService.update(this.harvest2);
    }

    @Test
    public void test11UpdateHarvest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException, ParseException {
        BDDMockito.when(harvestRepository.findById((long) 2))
                .thenReturn(java.util.Optional.ofNullable(this.harvest2));
        BDDMockito.when(harvestRepository.saveAndFlush(this.harvest2))
                .thenReturn(this.harvest2);
        this.harvest2.setName("Safra 2111/2112");
        this.harvest2.setEnd(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2088"));
        this.harvest2.setBegin(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2088"));
        this.harvestService.update(this.harvest2);
    }

    @Test (expected = AnyPersistenceException.class)//tenta salvar um null
    public void test12CreateHarvestAnyPersistenceException() throws EntityAlreadyExistsException, AnyPersistenceException {
        BDDMockito.when(harvestRepository.save(this.harvest3))
                .thenThrow(IllegalArgumentException.class);
        this.harvestService.create(this.harvest3);
    }

    @Test (expected = AnyPersistenceException.class)//tenta salvar um null
    public void test13DeleteHarvestAnyPersistenceException() throws  AnyPersistenceException, EntityInUseException, EntityNotFoundException {
        BDDMockito.when(harvestRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.harvest1));
        BDDMockito.doThrow(IllegalArgumentException.class)
                .when(harvestRepository).delete(this.harvest1);
        this.harvestService.delete(this.harvest1.getId());
    }
}
