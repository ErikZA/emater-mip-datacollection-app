package br.edu.utfpr.cp.emater.midmipsystem.entity.survey;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.*;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.SurveyRepository;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class SurveyTest {

    private Survey survey;
    private Supervisor supervisor1 = Supervisor.builder().id((long)2).name("MariO").build();
    private Supervisor supervisor2 = Supervisor.builder().id((long)3).name("Francisco").build();
    private Supervisor supervisor3 = Supervisor.builder().id((long)4).name("jorge").build();
    private SurveyRepository surveyRepository = mock(SurveyRepository.class);

    @Before
    public void setUp() throws ParseException {
        Set<Supervisor> var = new HashSet<>();
        var.add(this.supervisor1);
        var.add(this.supervisor2);
        var.add(this.supervisor3);
        City city = City.builder().state(State.PR).name("Nova Fatima").build();
        city.setId((long) 99);
        Date date1= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2013");
        Date date2= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-12-2013");
        Date date3= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-12-2015");
        Date date4= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-09-2015");
        Date date5= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-09-2015");
        this.survey = Survey.builder().id((long) 11).seedName("Test Survey Case")
                .bt(false).latitude(22.22).longitude(22.22)
                    .plantPerMeter(55.55).productivityFarmer(33.33).productivityField(33.33)
                        .separatedWeight(false).rustResistant(false).totalArea(55.55).totalPlantedArea(55.55)
                            .sporeCollectorPresent(false).sowedDate(date1).emergenceDate(date2).harvestDate(date3)
                                .field(Field.builder().id((long) 111).name("Field TeSt").location("Não NORMALIZA")
                                    .city(city).supervisors(var)
                                        .farmer(Farmer.builder().id((long)1).name("São FRAncisco").build()).build())
                                            .harvest(Harvest.builder().id((long) 555).name("harvest Test").begin(date4).end(date5).build()).build();
    }

    @Test
    public void isRustResistantTest() {
        assertThat(this.survey.isRustResistant()).isFalse();
        Survey survey = Survey.builder().id((long) 3).seedName("TestPar").rustResistant(true).build();
        assertThat(survey.isRustResistant()).isTrue();
    }

    @Test
    public void isBtTest() {
        assertThat(this.survey.isBt()).isFalse();
        Survey survey = Survey.builder().id((long) 3).seedName("TestPar").bt(true).build();
        assertThat(survey.isBt()).isTrue();
    }

    @Test
    public void getSowedDateTest() throws ParseException {
        Date date1= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2013");
        assertThat(this.survey.getSowedDate()).isEqualTo(date1);
    }

    @Test
    public void getEmergenceDateTest() throws ParseException {
        Date date2= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-12-2013");
        assertThat(this.survey.getEmergenceDate()).isEqualTo(date2);
    }

    @Test
    public void getHarvestDateTest() throws ParseException {
        Date date3= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-12-2015");
        assertThat(this.survey.getHarvestDate()).isEqualTo(date3);
    }

    @Test
    public void getLongitudeTest() {
        assertThat(this.survey.getLongitude()).isEqualTo(22.22);
    }

    @Test
    public void getLatitudeTest() {
        assertThat(this.survey.getLatitude()).isEqualTo(22.22);
    }

    @Test
    public void getProductivityFieldTest() {
        assertThat(this.survey.getProductivityField()).isEqualTo(33.33);
    }

    @Test
    public void getProductivityFarmerTest() {
        assertThat(this.survey.getProductivityFarmer()).isEqualTo(33.33);
    }

    @Test
    public void isSeparatedWeightTest() {
        assertThat(this.survey.isSeparatedWeight()).isEqualTo(false);
        Survey survey = Survey.builder().id((long) 3).seedName("TestPar").separatedWeight(true).build();
        assertThat(survey.isSeparatedWeight()).isEqualTo(true);
    }

    @Test
    public void getTotalAreaTest() {
        assertThat(this.survey.getTotalArea()).isEqualTo(55.55);
    }

    @Test
    public void getTotalPlantedAreaTest() {
        assertThat(this.survey.getTotalPlantedArea()).isEqualTo(55.55);
    }

    @Test
    public void getPlantPerMeterTest() {
        assertThat(this.survey.getPlantPerMeter()).isEqualTo(55.55);
    }

    @Test
    public void getFieldIdTest() {
        assertThat(this.survey.getFieldId()).isEqualTo((long)111);
    }

    @Test
    public void getFieldNameTest() {
        assertThat(this.survey.getFieldName()).isEqualTo("Field Test");
    }

    @Test
    public void getFieldLocationTest() {
        assertThat(this.survey.getFieldLocation()).isEqualTo("Não NORMALIZA");
    }

    @Test
    public void getFieldCityIdTest() {
        assertThat(this.survey.getFieldCityId()).isEqualTo((long)99);
    }

    @Test
    public void getFieldCityNameTest() {
        assertThat(this.survey.getFieldCityName()).isEqualTo("Nova Fatima");
    }

    @Test
    public void getFieldCityStateTest() {
        assertThat(this.survey.getFieldCityState()).isEqualTo(State.PR);
    }

    @Test
    public void getFarmerIdTest() {
        assertThat(this.survey.getFarmerId()).isEqualTo((long)1);
    }

    @Test
    public void getFarmerStringTest() {
        assertThat(this.survey.getFarmerString()).isEqualTo("São Francisco");
    }

    @Test
    public void getFieldSupervisorsTest() {
       assertThat(this.survey.getFieldSupervisors())
               .containsExactlyInAnyOrder(this.supervisor1,this.supervisor2,this.supervisor3)
               .isNotEmpty().doesNotContainNull();
    }

    @Test
    public void getHarvestIdTest() {
        assertThat(this.survey.getHarvestId()).isEqualTo((long)555);
    }

    @Test
    public void getHarvestNameTest() {
        assertThat(this.survey.getHarvestName()).isEqualTo("Harvest Test");
    }

    @Test
    public void getHarvestBeginDateTest() throws ParseException {
        Date date4= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-09-2015");
        assertThat(this.survey.getHarvestBeginDate()).isEqualTo(date4);
    }

    @Test
    public void getHarvestEndDateTest() throws ParseException {
        Date date5= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-09-2015");
        assertThat(this.survey.getHarvestEndDate()).isEqualTo(date5);
    }

    @Description("A ordem dos nomes pode variar")
    @Test
    public void getFieldSupervisorNamesTest() {
        assertThat(this.survey.getFieldSupervisorNames()).isEqualTo("[Jorge, Mario, Francisco]");
    }

    @Test
    public void getAndSetIdTest() {
        this.survey.setId((long) 55);
        assertThat(this.survey.getId()).isEqualTo((long)55);
    }

    @Test
    public void getAndSeedNameTest() {
        this.survey.setSeedName("Normalize CASE");
        assertThat(this.survey.getSeedName()).isEqualTo("Normalize Case");
    }

    @Test
    public void isSporeCollectorPresentTest() {
        this.survey.setSporeCollectorPresent(false);
        assertThat(this.survey.isSporeCollectorPresent()).isFalse();
        this.survey.setSporeCollectorPresent(true);
        assertThat(this.survey.isSporeCollectorPresent()).isTrue();
    }

    @Test
    public void getAndSetQuestionDataTest() {
        QuestionData questionData = QuestionData.builder().bt(false).rustResistant(false).build();
        this.survey.setQuestionData(questionData);
        assertThat(this.survey.getQuestionData()).isEqualTo(questionData);
    }

    @Test
    public void getAndSetDateDataTest() throws ParseException {
        Date date5= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-09-2015");
        DateData dateData = DateData.builder().emergenceDate(date5).sowedDate(date5).harvestDate(date5).build();
        this.survey.setDateData(dateData);
        assertThat(this.survey.getDateData()).isEqualTo(dateData);
    }

    @Test
    public void getSizeDataTest() {
        SizeData sizeData = SizeData.builder().plantPerMeter(22.22).totalArea(11.11).build();
        this.survey.setSizeData(sizeData);
        assertThat(this.survey.getSizeData()).isEqualTo(sizeData);
    }

    @Test
    public void getLocationDataTest() {
        LocationData locationData = LocationData.builder().longitude(22.22).latitude(11.11).build();
        this.survey.setLocationData(locationData);
        assertThat(this.survey.getLocationData()).isEqualTo(locationData);
    }

    @Test
    public void getAndSetProductivityDataTest() {
        ProductivityData productivityData = ProductivityData.builder().separatedWeight(false).productivityField(11.11)
            .productivityFarmer(33.33).build();
        this.survey.setProductivityData(productivityData);
        assertThat(this.survey.getProductivityData()).isEqualTo(productivityData);
    }

    @Test
    public void getAndSetFieldTest() {
        Field field = Field.builder().id((long) 2).name("City test").location("3")
                .city(City.builder().state(State.PR).name("AnyCity").build()).build();
        this.survey.setField(field);
        assertThat(this.survey.getField()).isEqualTo(field);
    }

    @Test
    public void getAndSetHarvestTest() {
        Harvest harvest = Harvest.builder().id((long) 5).name("AnyName").build();
        this.survey.setHarvest(harvest);
        assertThat(this.survey.getHarvest()).isEqualTo(harvest);
    }

    @Test
    public void createTest(){
        this.survey.setLastModified((long) 99);
        this.survey.setCreatedAt((long) 77);
        assertThat(this.survey.getLastModified()).isEqualTo((long)99);
        assertThat(this.survey.getCreatedAt()).isEqualTo((long)77);
    }

    @Test
    public void equalsFalseHarvestTest() throws ParseException {
        Survey survey= Survey.builder().id((long) 22).seedName("test harves").rustResistant(true).build();
        assertThat(this.survey.equals(survey)).isFalse();
    }

    @Test
    public void equalsTrueSurveyTest() {
        Survey survey= Survey.builder().id((long) 22).seedName("test harves").rustResistant(false).build();
        this.survey= survey;
        assertThat(this.survey.equals(survey)).isTrue();
    }

    @Test
    public void hashCodeTrueSurveyTest() {
        Survey survey= Survey.builder().id((long) 22).seedName("test harves").rustResistant(false).build();
        int value = survey.hashCode();
        assertThat(survey.hashCode()).isEqualTo(value);
    }

    @Test
    public void hashCodeFalseSurveyTest() {
        Survey survey= Survey.builder().id((long) 22).seedName("test harves").rustResistant(false).build();
        int value = survey.hashCode();
        value++;
        assertThat(survey.hashCode()==value).isFalse();
    }

    @Test (expected = ConstraintViolationException.class)
    public  void setNameExceptionNameLessThan5SurveyTest() {
        when(this.surveyRepository.save(this.survey)).thenThrow(ConstraintViolationException.class);
        this.survey.setSeedName("test");
        this.surveyRepository.save(this.survey);
    }


    @Test (expected = ConstraintViolationException.class)
    public  void setNameExceptionNameBigThan50SurveyTest() {
        when(this.surveyRepository.save(this.survey)).thenThrow(ConstraintViolationException.class);
        this.survey.setSeedName("123456789-123456789-123456789-123456789-123456789-1");
        this.surveyRepository.save(this.survey);
    }

    @Test (expected = ConstraintViolationException.class)
    public  void setFieldNullExceptionSurveyTest() {
        when(this.surveyRepository.save(this.survey)).thenThrow(ConstraintViolationException.class);
        this.survey.setField(null);
        this.surveyRepository.save(this.survey);
    }

    @Test (expected = ConstraintViolationException.class)
    public void setHarvestNullExceptionSurveyTest() {
        when(this.surveyRepository.save(this.survey)).thenThrow(ConstraintViolationException.class);
        this.survey.setHarvest(null);
        this.surveyRepository.save(this.survey);
    }

}