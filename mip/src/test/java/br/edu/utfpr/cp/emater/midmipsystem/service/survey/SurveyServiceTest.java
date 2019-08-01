package br.edu.utfpr.cp.emater.midmipsystem.service.survey;



import br.edu.utfpr.cp.emater.midmipsystem.entity.base.*;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.*;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.HarvestRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.SurveyRepository;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SurveyServiceTest {

    @Autowired
    private MacroRegionRepository macroRegionRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private FarmerRepository farmerRepository;
    @Autowired
    private SupervisorRepository supervisorRepository;
    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private FieldService fieldService;
    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private HarvestRepository harvestRepository;
    @Autowired
    private HarvestService harvestService;
    @Autowired
    private SurveyService surveyService;

    @Autowired
    private CityService cityService;
    @Autowired
    private SupervisorService supervisorService;
    @Autowired
    private FarmerService farmerService;

    private Field field1, field2, field3;
    private City city1,city2,city3,city4,city5;
    private Farmer farmer1,farmer2, farmer3;
    private Supervisor supervisor1, supervisor2, supervisor3;
    private Harvest harvest1, harvest2, harvest3, harvest4, harvest5;
    private Survey survey2, survey1, survey3;


    @Before
    public void SetUp() throws Exception {
        var mr1 = this.macroRegionRepository.save(MacroRegion.builder().name("Macro Leste").build());

        this.city1
 = this.cityRepository.save(City.builder().name("Ponta Grossa").state(State.PR).build());
        this.city2 = this.cityRepository.save(City.builder().name("Pinhais").state(State.PR).build());
        this.city3 = this.cityRepository.save(City.builder().name("Curitiba").state(State.PR).build());
        this.city4 = City.builder().name("Paranagua").state(State.PR).build();
        this.city5 = City.builder().name("Colombo").state(State.PR).build();
        var region1 = Region.builder().name("Curitiba").macroRegion(mr1).build();
        region1.addCity(city1
);
        var r1 = this.regionRepository.save(region1);
        var region2 = Region.builder().name("Ponta Grossa").macroRegion(mr1).build();
        region2.addCity(city2);
        region2.addCity(city3);
        var r2 = this.regionRepository.save(region2);
        this.farmer1 = this.farmerRepository.save(Farmer.builder().name("Marcos Paulo").build());
        this.farmer2 = this.farmerRepository.save(Farmer.builder().name("Otaviano Gregorio").build());
        this.farmer3 = Farmer.builder().name("Malakoi silva").build();

        this.supervisor1 = this.supervisorRepository.save(Supervisor.builder().name("Inoan Martins").email("InoanMartins@emater.pr.gov.br").region(r1).build());
        this.supervisor2 = this.supervisorRepository.save(Supervisor.builder().name("David Luiz").email("DavidLuiz@emater.pr.gov.br").region(r2).build());
        this.supervisor3 = Supervisor.builder().name("Marcos Paulo Nunes").email("MarcosPauloNunes@emater.pr.gov.br").region(r2).build();

        this.field1 = Field.builder().name("Macaxeira").location("").city(city1
).farmer(farmer1).build();
        this.field1.addSupervisor(supervisor1);
        this.fieldRepository.save(field1);
        this.field2 = Field.builder().name("Canela").location("").city(city3).farmer(farmer2).build();
        this.field2.addSupervisor(supervisor1);
        this.fieldRepository.save(field2);
        this.field3 = Field.builder().name("Pinhao").location("").city(city3).farmer(farmer2).build();
        this.field3.addSupervisor(supervisor2);
        this.fieldRepository.save(field3);


        this.harvest1 = harvestRepository.save(
                Harvest.builder()
                        .name("Safra 2006/2007")
                        .begin(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2006"))
                        .end(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-03-2007"))
                        .build()
        );

        this.harvest2 = harvestRepository.save(
                Harvest.builder()
                        .name("Safra 2007/2008")
                        .begin(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2007"))
                        .end(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-03-2008"))
                        .build()
        );

        this.harvest3 = Harvest.builder()
                .name("Safra 2008/2009")
                .begin(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2008"))
                .end(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-03-2009"))
                .build();


        this.survey1 = surveyRepository.save(
                Survey.builder()
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
                        .build()
        );

        this.survey2 = surveyRepository.save(
                Survey.builder()
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
                        .build()
        );

        this.survey3 = Survey.builder()
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
    }

    @Test
    public void test01ReadAllSurvey(){
        assertThat(this.surveyService.readAll())
                .doesNotContainNull()
                .contains(survey2,survey1)
                .doesNotContain(survey3);
    }

    @Test
    public void test02ReadByIdSurvey() throws EntityNotFoundException {
        assertThat(this.surveyService.readById(survey1.getId()))
                .isNotNull()
                .isEqualTo(survey1);
    }

    @Test (expected = EntityNotFoundException.class)
    public void test03ReadByIdSurveyEntityNotFoundException() throws EntityNotFoundException {
        this.surveyRepository.deleteById(survey1.getId());
        this.surveyService.readById(survey1.getId());
    }

    @Test
    public void test04ReadFieldbyId() throws EntityNotFoundException {
        assertThat(this.surveyService.readFieldbyId(this.field1.getId()))
            .isEqualTo(this.field1)
            .isNotNull();
    }

    @Test (expected = EntityNotFoundException.class)
    public void test05ReadFieldbyIdEntityNotFoundException() throws EntityNotFoundException {
        this.field3 = this.fieldRepository.save(this.field3);
        this.fieldRepository.deleteById(this.field3.getId());
        this.surveyService.readFieldbyId(this.field3.getId());
    }


    @Test
    public void test06ReadByHarvestId() throws EntityNotFoundException {
        assertThat(this.surveyService.readByHarvestId(this.harvest1.getId()))
                .containsExactly(this.survey1,survey2)
                .isNotNull();
    }


    @Test
    public void test07ReadByHarvestIdEntityNotFoundException() throws EntityNotFoundException {
        assertThat(this.surveyService.readByHarvestId(this.harvest2.getId())).isEmpty();
    }

    @Test
    public void test08ReadAllFields(){
        assertThat(this.surveyService.readAllFields())
                .doesNotContainNull()
                .contains(field1,field2,field3);
    }

    @Test
    public void test09ReadAllHarvests(){
        assertThat(this.surveyService.readAllHarvests())
                .doesNotContainNull()
                .contains(this.harvest1,this.harvest2)
                .doesNotContain(this.harvest3);
    }

    @Test //necessita de mais aprofundamento
    public void test10ReadAllFieldsOutOfCurrentHarvest(){
        assertThat(this.surveyService.readAllFieldsOutOfCurrentHarvest(this.harvest1.getId()))
                .doesNotContainNull()
                .contains(this.field3);
    }

    @Test
    public void test11ReadHarvestById() throws EntityNotFoundException {
        assertThat(this.surveyService.readHarvestById(this.harvest1.getId()))
                .isNotNull()
                .isEqualTo(this.harvest1);
    }
    @Test (expected = EntityNotFoundException.class)
    public void test12ReadHarvestByIdEntityNotFoundException() throws EntityNotFoundException {
        this.harvest3 = this.harvestRepository.save(this.harvest3);
        this.harvestRepository.deleteById(this.harvest3.getId());
        assertThat(this.surveyService.readHarvestById(this.harvest3.getId()));
    }
}
