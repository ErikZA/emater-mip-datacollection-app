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
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        verify(this.harvestRepository, times(1)).findAll();
    }

    @Test
    public void test02ReadByIdHarvest() throws EntityNotFoundException {
        BDDMockito.when(harvestRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.harvest1));
        assertThat(this.harvestService.readById(harvest1.getId()))
                .isNotNull()
                .isEqualTo(harvest1);
        verify(this.harvestRepository, times(1)).findById(harvest1.getId());
    }


    @Test
    public void test03ReadByIdHarvestEntityNotFoundException() {
        BDDMockito.when(harvestRepository.findById((long) 3))
                .thenReturn(java.util.Optional.ofNullable(null));
        try {
            this.harvestService.readById(harvest3.getId());
            fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){
            assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }
        verify(this.harvestRepository, times(1)).findById((long) 3);
    }


    @Test
    public void test04CreateHarvestEntityNotFoundException() throws AnyPersistenceException {
        try {
            this.harvestService.create(this.harvest1);
            fail("EntityAlreadyExistsException it is not throws");
        } catch (EntityAlreadyExistsException e){
            assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }
        verify(this.harvestRepository, times(1)).findAll();
    }

    @Test
    public void test05CreateHarvest() throws EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {
        BDDMockito.when(harvestRepository.save(this.harvest3))
                .thenReturn(this.harvest3);
        this.harvestService.create(this.harvest3);

        verify(this.harvestRepository, times(1)).findAll();
        verify(this.harvestRepository, times(1)).save(this.harvest3);
        //fazer uma verificação com mock e lista?
    }


    @Test
    public void test06DeleteHarvestEntityNotFoundException() throws  AnyPersistenceException, EntityInUseException {
        BDDMockito.when(harvestRepository.findById((long) 3))
                .thenReturn(java.util.Optional.ofNullable(null));

        try {
            this.harvestService.delete(harvest3.getId());
            fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){
            assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }
        verify(this.harvestRepository, times(1)).findById((long) 3);
    }

    @Test
    public void test07DeleteHarvest() throws Exception {
        BDDMockito.doNothing().when(harvestRepository).delete(harvest1);
        BDDMockito.when(harvestRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.harvest1));
        this.harvestService.delete(this.harvest1.getId());
        verify(this.harvestRepository, times(1)).findById((long) 1);
        verify(this.harvestRepository, times(1)).delete(this.harvest1);
    }


    @Test
    public void test08DeleteHarvestEntityInUseException() throws Exception{
        BDDMockito.when(harvestRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.harvest1));
        BDDMockito.doThrow(DataIntegrityViolationException.class)
                .when(harvestRepository).delete(this.harvest1);

        try {
            this.harvestService.delete(this.harvest1.getId());
            fail("EntityInUseException it is not throws");
        } catch (EntityInUseException e){
            assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }

        verify(this.harvestRepository, times(1)).findById((long) 1);
        verify(this.harvestRepository, times(1)).delete(this.harvest1);
    }


    @Test
    public void test09UpdateHarvestEntityNotFoundException() throws Exception {
        BDDMockito.when(harvestRepository.findById((long) 3))
                .thenReturn(java.util.Optional.ofNullable(null));
        try {
            this.harvestService.update(this.harvest3);
            fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e) {
            assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }
        verify(this.harvestRepository, times(1)).findById((long) 3);
    }

    @Test
    public void test10UpdateHarvestAnyPersistenceException() throws Exception {
        BDDMockito.when(harvestRepository.findById((long) 2))
                .thenReturn(java.util.Optional.ofNullable(this.harvest2));
        BDDMockito.when(harvestRepository.saveAndFlush(any()))
                .thenThrow(IllegalArgumentException.class);
        try {
            this.harvest2.setName("01-10-2777");
            this.harvestService.update(this.harvest2);
            fail("AnyPersistenceException it is not throws");
        } catch (AnyPersistenceException e){
            assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }
        verify(this.harvestRepository, times(1)).findById((long) 2);
        verify(this.harvestRepository, times(1)).findAll();
        verify(this.harvestRepository, times(1)).saveAndFlush(this.harvest2);
    }

    @Test
    public void test11UpdateHarvest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException, ParseException {
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

        verify(this.harvestRepository, times(1)).findById(this.harvest2.getId());
        verify(this.harvestRepository, times(1)).saveAndFlush(this.harvest2);
        verify(this.harvestRepository, times(1)).findAll();

    }

    @Test //tenta salvar um null
    public void test12CreateHarvestAnyPersistenceException() throws EntityAlreadyExistsException {
        BDDMockito.when(harvestRepository.save(null))
                .thenThrow(IllegalArgumentException.class);
        try {
            this.harvestService.create(null);
            fail("AnyPersistenceException it is not throws");
        } catch (AnyPersistenceException e){
            assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }

        verify(this.harvestRepository, times(1)).findAll();
        verify(this.harvestRepository, times(1)).save(null);
    }

    @Test //tenta deletar um null
    public void test13DeleteHarvestAnyPersistenceException() throws  EntityInUseException, EntityNotFoundException {
        BDDMockito.when(harvestRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.harvest1));
        BDDMockito.doThrow(IllegalArgumentException.class)
                .when(harvestRepository).delete(any());
        try {
            this.harvestService.delete(this.harvest1.getId());
            fail("AnyPersistenceException it is not throws");
        } catch (AnyPersistenceException e){
            assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }
        verify(this.harvestRepository, times(1)).findById(this.harvest1.getId());
        verify(this.harvestRepository, times(1)).delete(any());
    }

    @Test
    public void test14UpdateHarvestEntityAlreadyExistsException() throws Exception {
        Harvest harvestCopy = this.harvest1;
        List<Harvest> listHarvest = asList(this.harvest1,this.harvest2,harvestCopy);
        BDDMockito.when(this.harvestRepository.findAll())
                .thenReturn(listHarvest);

        BDDMockito.when(harvestRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.harvest1));

        try {
            this.harvest1.setName("Safra 2999/2999");
            this.harvestService.update(this.harvest1);
            fail("EntityAlreadyExistsException it is not throws");
        } catch (EntityAlreadyExistsException e) {
            assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }
        verify(this.harvestRepository, times(1)).findById((long) 1);
        verify(this.harvestRepository, times(1)).findAll();
    }

}
