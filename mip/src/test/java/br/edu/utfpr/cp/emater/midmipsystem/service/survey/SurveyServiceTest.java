package br.edu.utfpr.cp.emater.midmipsystem.service.survey;



import br.edu.utfpr.cp.emater.midmipsystem.entity.base.*;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.exception.*;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.*;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.HarvestRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.SurveyRepository;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.*;
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

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SurveyServiceTest {

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
    private SurveyRepository surveyRepository;
    @MockBean
    private HarvestRepository harvestRepository;

    @Autowired
    private HarvestService harvestService;
    @Autowired
    private FieldService fieldService;
    @Autowired
    private SurveyService surveyService;


    private Field field1, field2, field3;
    private City city1,city2,city3;
    private Farmer farmer1,farmer2;
    private Supervisor supervisor1, supervisor2;
    private Harvest harvest1, harvest2, harvest3;
    private Survey survey2, survey1, survey3;


    @Before
    public void SetUp() throws Exception {

        var mr1 = MacroRegion.builder().name("Macro Leste").id((long) 1).build();
        this.city1 = City.builder().name("Ponta Grossa").state(State.PR).build();
        this.city1.setId((long)1);
        this.city2 = City.builder().name("Pinhais").state(State.PR).build();
        this.city2.setId((long)2);
        this.city3 = City.builder().name("Curitiba").state(State.PR).build();
        this.city3.setId((long)3);

        var region1 = Region.builder().name("Curitiba").macroRegion(mr1).build();
        region1.addCity(city1);
        region1.setId((long)1);
        var r1 = region1;

        var region2 = Region.builder().name("Ponta Grossa").macroRegion(mr1).build();
        region2.addCity(city2);
        region2.addCity(city3);
        region2.setId((long)2);
        var r2 = region2;

        this.farmer1 = Farmer.builder().name("Marcos Paulo").build();
        this.farmer1.setId((long)1);
        this.farmer2 = Farmer.builder().name("Otaviano Gregorio").build();
        this.farmer2.setId((long)2);

        this.supervisor1 = Supervisor.builder().name("Inoan Martins").email("InoanMartins@emater.pr.gov.br").region(r1).build();
        this.supervisor1.setId((long)1);
        this.supervisor2 = Supervisor.builder().name("David Luiz").email("DavidLuiz@emater.pr.gov.br").region(r2).build();
        this.supervisor2.setId((long)2);

        this.field1 = Field.builder().name("Macaxeira").location("").city(city1).farmer(farmer1).build();
        this.field1.addSupervisor(supervisor1);
        this.field1.setId((long)1);

        this.field2 = Field.builder().name("Canela").location("").city(city3).farmer(farmer2).build();
        this.field2.addSupervisor(supervisor1);
        this.field2.setId((long)2);

        this.field3 = Field.builder().name("Pinhao").location("").city(city3).farmer(farmer2).build();
        this.field3.addSupervisor(supervisor2);
        this.field3.setId((long)3);


        this.harvest1 = Harvest.builder()
                        .id((long)1)
                        .name("Safra 2006/2007")
                        .begin(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2006"))
                        .end(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-03-2007"))
                        .build();

        this.harvest2 = Harvest.builder()
                        .id((long)2)
                        .name("Safra 2007/2008")
                        .begin(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2007"))
                        .end(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-03-2008"))
                        .build();

        this.harvest3 = Harvest.builder()
                        .id((long)3)
                        .name("Safra 2008/2009")
                        .begin(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2008"))
                        .end(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-03-2009"))
                        .build();


        this.survey1 = Survey.builder()
                        .id((long)1)
                        .harvest(harvest1)
                        .field(field1)
                        .seedName("Test 222 BR1")
                        .sowedDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2007-10-1"))
                        .emergenceDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2007-10-8"))
                        .harvestDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2008-02-26"))
                        .rustResistant(true)
                        .bt(false)
                        .totalArea(1.4)
                        .totalPlantedArea(8)
                        .plantPerMeter(3)
                        .productivityField(141.7)
                        .productivityFarmer(159.5)
                        .separatedWeight(true)
                        .longitude(1.5)
                        .latitude(2.5)
                        .build();

        this.survey2 = Survey.builder()
                        .id((long)2)
                        .harvest(harvest1)
                        .field(field2)
                        .seedName("Test 111 BR2")
                        .sowedDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2006-10-4"))
                        .emergenceDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2006-10-11"))
                        .harvestDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2006-2-12"))
                        .rustResistant(false)
                        .bt(true)
                        .totalArea(45)
                        .totalPlantedArea(72)
                        .plantPerMeter(19)
                        .productivityField(157)
                        .productivityFarmer(122)
                        .separatedWeight(false)
                        .longitude(3)
                        .latitude(4.9)
                        .build();

        this.survey3 = Survey.builder()
                        .id((long)3)
                        .harvest(harvest2)
                        .field(field2)
                        .seedName("TMG 7262 RR")
                        .sowedDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-4"))
                        .emergenceDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-9"))
                        .harvestDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2018-2-20"))
                        .rustResistant(true)
                        .bt(false)
                        .totalArea(5.74)
                        .totalPlantedArea(35.09)
                        .plantPerMeter(11)
                        .productivityField(137.5)
                        .productivityFarmer(120)
                        .separatedWeight(true)
                        .longitude(4.5)
                        .latitude(5.5)
                        .build();

        List<Survey> listSurvey = asList(this.survey1,this.survey2);
        BDDMockito.when(this.surveyRepository.findAll())
                .thenReturn(listSurvey);
    }

    @Test
    public void surveyServiceTestReadAllSurvey(){
        assertThat(this.surveyService.readAll())
                .doesNotContainNull()
                .containsExactlyInAnyOrder(this.survey2,this.survey1)
                .doesNotContain(this.survey3);
        verify(this.surveyRepository, times(1)).findAll();
        verifyNoMoreInteractions(this.surveyRepository);
    }

    @Test
    public void surveyServiceTestReadByIdSurvey() throws EntityNotFoundException {
        when(this.surveyRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.survey1));

        assertThat(this.surveyService.readById(this.survey1.getId()))
                .isNotNull()
                .isEqualTo(this.survey1);

        verify(this.surveyRepository, times(1)).findById((long)1);
        verifyNoMoreInteractions(this.surveyRepository);
    }

    @Test
    public void surveyServiceTestReadByIdSurveyEntityNotFoundException() {
        when(this.surveyRepository.findById((long)3))
                .thenReturn(java.util.Optional.ofNullable(null));

        try {
            this.surveyService.readById(survey3.getId());
            fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){
            assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }
        verify(this.surveyRepository, times(1)).findById((long)3);
        verifyNoMoreInteractions(this.surveyRepository);
    }

    @Test
    public void surveyServiceTestReadFieldbyId() throws EntityNotFoundException {
        when(this.fieldRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.field1));

        assertThat(this.surveyService.readFieldbyId(this.field1.getId()))
            .isEqualTo(this.field1)
            .isNotNull();

        verify(this.fieldRepository, times(1)).findById((long)1);
        verifyNoMoreInteractions(this.fieldRepository);
    }

    @Test
    public void surveyServiceTestReadFieldbyIdEntityNotFoundException() {
        when(this.fieldRepository.findById((long)3))
                .thenReturn(java.util.Optional.ofNullable(null));

        try {
            this.surveyService.readFieldbyId(this.field3.getId());
            fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){
            assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }
        verify(this.fieldRepository, times(1)).findById((long)3);
        verifyNoMoreInteractions(this.fieldRepository);
    }


    @Test
    public void surveyServiceTestRreadHarvestById() throws EntityNotFoundException {
        when(this.harvestRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.harvest1));

        assertThat(this.surveyService.readHarvestById(this.harvest1.getId()))
                .isEqualTo(this.harvest1)
                .isNotNull();

        verify(this.harvestRepository, times(1)).findById((long)1);
        verifyNoMoreInteractions(this.harvestRepository);
    }


    @Test
    public void surveyServiceTestReadHarvestByIdEntityNotFoundException() {
        when(this.harvestRepository.findById((long)3))
                .thenReturn(java.util.Optional.ofNullable(null));

        try {
            this.surveyService.readHarvestById(this.harvest3.getId());
            fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){
            assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }
        verify(this.harvestRepository, times(1)).findById((long)3);
        verifyNoMoreInteractions(this.harvestRepository);
    }

    @Test
    public void surveyServiceTestReadAllFields(){
        List<Field> listField = asList(this.field1,this.field2);
        BDDMockito.when(this.fieldRepository.findAll())
                .thenReturn(listField);

        assertThat(this.surveyService.readAllFields())
                .doesNotContainNull()
                .contains(this.field1, this.field2)
                .doesNotContain(this.field3);

        verify(this.fieldRepository, times(1)).findAll();
        verifyNoMoreInteractions(this.fieldRepository);
    }

    @Test
    public void surveyServiceTestReadAllHarvests(){
        List<Harvest> listHarvest = asList(this.harvest1,this.harvest2);
        BDDMockito.when(harvestRepository.findAll())
                .thenReturn(listHarvest);

        assertThat(this.surveyService.readAllHarvests())
                .doesNotContainNull()
                .contains(this.harvest1,this.harvest2)
                .doesNotContain(this.harvest3);

        verify(this.harvestRepository, times(1)).findAll();
        verifyNoMoreInteractions(this.harvestRepository);

    }

    @Test
    public void surveyServiceTestReadAllFieldsOutOfCurrentHarvest(){
        List<Field> listField = asList(this.field1,this.field2,this.field3);
        BDDMockito.when(this.fieldRepository.findAll())
                .thenReturn(listField);

        assertThat(this.surveyService.readAllFieldsOutOfCurrentHarvest(this.harvest1.getId()))
                .doesNotContainNull()
                .doesNotContain(this.field1,this.field2)
                .containsExactly(this.field3);

        verify(this.surveyRepository, times(1)).findAll();
        verify(this.fieldRepository, times(1)).findAll();
        verifyNoMoreInteractions(this.surveyRepository);
        verifyNoMoreInteractions(this.fieldRepository);
    }

    @Test
    public void surveyServiceTestReadByHarvestId() throws EntityNotFoundException {
        assertThat(this.surveyService.readByHarvestId(this.harvest1.getId()))
                .isNotNull()
                .doesNotContain(this.survey3)
                .containsExactlyInAnyOrder(this.survey1,this.survey2);

        verify(this.surveyRepository, times(1)).findAll();
        verifyNoMoreInteractions(this.surveyRepository);
    }

    @Test
    public void surveyServiceTestReadByHarvestIdEntityNotFoundException() {
        try {
            this.surveyService.readByHarvestId(this.harvest3.getId());
            fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){
            assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }
        verify(this.surveyRepository, times(1)).findAll();
        verifyNoMoreInteractions(this.surveyRepository);
    }

    @Test
    public void surveyServiceTestDeleteSurveyEntityNotFoundException() throws AnyPersistenceException, EntityInUseException {
        when(this.surveyRepository.findById((long)4))
                .thenReturn(java.util.Optional.ofNullable(null));
        try {
            this.surveyService.delete((long)4);
            fail("EntityNotFoundException it is not throws");
        }  catch (EntityNotFoundException e){
            assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }
        verify(this.surveyRepository, times(1)).findById((long)4);
        verifyNoMoreInteractions(this.surveyRepository);
    }

    @Test
    public void surveyServiceTestDeleteSurveyEntityInUseException() throws EntityNotFoundException, AnyPersistenceException {
        when(this.surveyRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.survey1));
        doThrow(DataIntegrityViolationException.class).when(this.surveyRepository).delete(any());

        try {
            this.surveyService.delete((long)1);
            fail("EntityInUseException it is not throws");
        }  catch (EntityInUseException e){
            assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }

        verify(this.surveyRepository, times(1)).findById((long)1);
        verify(this.surveyRepository, times(1)).delete(any());
        verifyNoMoreInteractions(this.surveyRepository);
    }

    @Test
    public void surveyServiceTestDeleteSurveyAnyPersistenceException() throws EntityNotFoundException, EntityInUseException {
        when(this.surveyRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.survey1));
        doThrow(IllegalArgumentException.class).when(this.surveyRepository).delete(any());

        try {
            this.surveyService.delete((long)1);
            fail("AnyPersistenceException it is not throws");
        }  catch (AnyPersistenceException  e){
            assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }

        verify(this.surveyRepository, times(1)).findById((long)1);
        verify(this.surveyRepository, times(1)).delete(any());
        verifyNoMoreInteractions(this.surveyRepository);
    }

    @Test
    public void surveyServiceTestDeleteSurvey() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.surveyRepository.findById((long)2))
                .thenReturn(java.util.Optional.ofNullable(this.survey2));
        doNothing().when(this.surveyRepository).delete(this.survey2);

        this.surveyService.delete((long)2);


        verify(this.surveyRepository, times(1)).findById((long)2);
        verify(this.surveyRepository, times(1)).delete(this.survey2);
        verifyNoMoreInteractions(this.surveyRepository);
    }

    @Test
    public void surveyServiceTestCreateSurveyEntityNotFoundExceptionField() throws SupervisorNotAllowedInCity, EntityAlreadyExistsException, AnyPersistenceException {

        this.survey3.setField(this.field3);

        when(this.fieldRepository.findById((long)3))
                .thenReturn(java.util.Optional.ofNullable(null));

        try {
            this.surveyService.create(this.survey3);
            fail("EntityInUseException it is not throws");
        }  catch (EntityNotFoundException  e){
            assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }

        verify(this.fieldRepository, times(1)).findById((long)3);
        verify(this.surveyRepository, times(1)).findAll();
        verifyNoMoreInteractions(this.surveyRepository);
        verifyNoMoreInteractions(this.fieldRepository);
    }

    @Test
    public void surveyServiceTestCreateSurveyEntityAlreadyExistsException() throws SupervisorNotAllowedInCity, EntityNotFoundException, AnyPersistenceException {
        try {
            this.surveyService.create(this.survey1);
            fail("EntityInUseException it is not throws");
        }  catch (EntityAlreadyExistsException  e){
            assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }
        verify(this.surveyRepository, times(1)).findAll();
        verifyNoMoreInteractions(this.surveyRepository);
    }

    @Test
    public void surveyServiceTestCreateSurveyEntityNotFoundExceptionHarvers() throws SupervisorNotAllowedInCity, EntityAlreadyExistsException, AnyPersistenceException {

        this.survey3.setHarvest(this.harvest3);

        when(this.fieldRepository.findById((long)2))
                .thenReturn(java.util.Optional.ofNullable(this.field2));
        when(this.harvestRepository.findById((long)3))
                .thenReturn(java.util.Optional.ofNullable(null));

        try {
            this.surveyService.create(this.survey3);
            fail("EntityInUseException it is not throws");
        }  catch (EntityNotFoundException  e){
            assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }

        verify(this.fieldRepository, times(1)).findById((long)2);
        verify(this.harvestRepository, times(1)).findById((long)3);
        verify(this.surveyRepository, times(1)).findAll();
        verifyNoMoreInteractions(this.fieldRepository);
        verifyNoMoreInteractions(this.harvestRepository);
        verifyNoMoreInteractions(this.surveyRepository);
    }

    @Test
    public void surveyServiceTestCreateSurvey() throws SupervisorNotAllowedInCity, EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {

        when(this.fieldRepository.findById((long)2))
                .thenReturn(java.util.Optional.ofNullable(this.field2));
        when(this.harvestRepository.findById((long)2))
                .thenReturn(java.util.Optional.ofNullable(this.harvest2));
        when(this.surveyRepository.save(this.survey3)).thenReturn(this.survey3);

            this.surveyService.create(this.survey3);

        verify(this.fieldRepository, times(1)).findById((long)2);
        verify(this.harvestRepository, times(1)).findById((long)2);
        verify(this.surveyRepository, times(1)).findAll();
        verify(this.surveyRepository, times(1)).save(this.survey3);
        verifyNoMoreInteractions(this.surveyRepository);
        verifyNoMoreInteractions(this.fieldRepository);
        verifyNoMoreInteractions(this.harvestRepository);
    }

    @Test
    public void surveyServiceTestCreateSurveyAnyPersistenceException() throws SupervisorNotAllowedInCity, EntityAlreadyExistsException, EntityNotFoundException {

        when(this.fieldRepository.findById((long)2))
                .thenReturn(java.util.Optional.ofNullable(this.field2));
        when(this.harvestRepository.findById((long)2))
                .thenReturn(java.util.Optional.ofNullable(this.harvest2));
        doThrow(IllegalArgumentException.class).when(this.surveyRepository).save(any());

        try {
            this.surveyService.create(this.survey3);
            fail("AnyPersistenceException it is not throws");
        }  catch (AnyPersistenceException  e){
            assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }

        verify(this.fieldRepository, times(1)).findById((long)2);
        verify(this.harvestRepository, times(1)).findById((long)2);
        verify(this.surveyRepository, times(1)).findAll();
        verify(this.surveyRepository, times(1)).save(any());
        verifyNoMoreInteractions(this.surveyRepository);
        verifyNoMoreInteractions(this.harvestRepository);
        verifyNoMoreInteractions(this.fieldRepository);
    }

    @Test
    public void surveyServiceTestUpdateSurveyEntityNotFoundException() throws AnyPersistenceException, EntityAlreadyExistsException {

        when(this.surveyRepository.findById((long)3))
                .thenReturn(java.util.Optional.ofNullable(null));

        try {
            this.surveyService.update(this.survey3);
            fail("EntityNotFoundException it is not throws");
        }  catch (EntityNotFoundException  e){
            assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }

        verify(this.surveyRepository, times(1)).findById((long)3);
        verifyNoMoreInteractions(this.surveyRepository);
    }

    @Test
    public void surveyServiceTestUpdateSurveyAnyPersistenceException() throws EntityNotFoundException, EntityAlreadyExistsException {

        when(this.surveyRepository.findById((long)2))
                .thenReturn(java.util.Optional.ofNullable(this.survey2));
        doThrow(IllegalArgumentException.class).when(this.surveyRepository).saveAndFlush(any());

        try {
            this.survey2.setSeedName("TesteUpdate");
            this.surveyService.update(this.survey2);
            fail("AnyPersistenceException it is not throws");
        }  catch (AnyPersistenceException  e){
            assertThat(e.getMessage()).isEqualToIgnoringCase(null);
        }

        verify(this.surveyRepository, times(1)).findById((long)2);
        verify(this.surveyRepository, times(1)).saveAndFlush(any());
        verifyNoMoreInteractions(this.surveyRepository);
    }

    @Test
    public void surveyServiceTestUpdateSurvey() throws EntityNotFoundException, EntityAlreadyExistsException, AnyPersistenceException {
        when(this.surveyRepository.findById((long)2))
                .thenReturn(java.util.Optional.ofNullable(this.survey2));
        when(this.surveyRepository.saveAndFlush(this.survey2))
                .thenReturn(this.survey2);

        assertThat(this.survey2.getSeedName()).isEqualToIgnoringCase("Test 111 BR2");

            this.survey2.setSeedName("TesteUpdate");
            this.surveyService.update(this.survey2);


        verify(this.surveyRepository, times(1)).findById((long)2);
        verify(this.surveyRepository, times(1)).saveAndFlush(this.survey2);
        verifyNoMoreInteractions(this.surveyRepository);
    }




}
