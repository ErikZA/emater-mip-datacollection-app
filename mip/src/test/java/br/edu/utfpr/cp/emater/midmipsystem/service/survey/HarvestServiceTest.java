package br.edu.utfpr.cp.emater.midmipsystem.service.survey;


import br.edu.utfpr.cp.emater.midmipsystem.entity.base.*;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HarvestServiceTest {


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
    private CityService cityService;
    @Autowired
    private SupervisorService supervisorService;
    @Autowired
    private FarmerService farmerService;

    private Field field1, field2, field3;
    private City c1,c2,c3,c4,c5;
    private Farmer f1,f2, f3;
    private Supervisor s1, s2, s3;
    private Harvest harvest1, harvest2, harvest3, harvest4, harvest5;


    @Before
    public void SetUp() throws Exception {
        var mr1 = this.macroRegionRepository.save(MacroRegion.builder().name("Macro Leste").build());

        this.c1 = this.cityRepository.save(City.builder().name("Ponta Grossa").state(State.PR).build());
        this.c2 = this.cityRepository.save(City.builder().name("Pinhais").state(State.PR).build());
        this.c3 = this.cityRepository.save(City.builder().name("Curitiba").state(State.PR).build());
        this.c4 = City.builder().name("Paranagua").state(State.PR).build();
        this.c5 = City.builder().name("Colombo").state(State.PR).build();
        var region1 = Region.builder().name("Curitiba").macroRegion(mr1).build();
        region1.addCity(c1);
        var r1 = this.regionRepository.save(region1);
        var region2 = Region.builder().name("Ponta Grossa").macroRegion(mr1).build();
        region2.addCity(c2);
        region2.addCity(c3);
        var r2 = this.regionRepository.save(region2);
        this.f1 = this.farmerRepository.save(Farmer.builder().name("Marcos Paulo").build());
        this.f2 = this.farmerRepository.save(Farmer.builder().name("Otaviano Gregorio").build());
        this.f3 = Farmer.builder().name("Malakoi silva").build();

        this.s1 = this.supervisorRepository.save(Supervisor.builder().name("Inoan Martins").email("InoanMartins@emater.pr.gov.br").region(r1).build());
        this.s2 = this.supervisorRepository.save(Supervisor.builder().name("David Luiz").email("DavidLuiz@emater.pr.gov.br").region(r2).build());
        this.s3 = Supervisor.builder().name("Marcos Paulo Nunes").email("MarcosPauloNunes@emater.pr.gov.br").region(r2).build();

        this.field1 = Field.builder().name("Macaxeira").location("").city(c1).farmer(f1).build();
        this.field1.addSupervisor(s1);
        this.fieldRepository.save(field1);
        this.field2 = Field.builder().name("Canela").location("").city(c3).farmer(f2).build();
        this.field2.addSupervisor(s1);
        this.fieldRepository.save(field2);
        this.field3 = Field.builder().name("Pinhao").location("").city(c3).farmer(f2).build();
        this.field3.addSupervisor(s2);

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
    }

    @Test
    public void test01ReadAllHarvest(){
        assertThat(this.harvestService.readAll())
                .doesNotContainNull()
                .contains(harvest1,harvest2)
                .doesNotContain(harvest3);
    }

    @Test
    public void test02ReadByIdHarvest() throws EntityNotFoundException {
        assertThat(this.harvestService.readById(harvest1.getId()))
                .isNotNull()
                .isEqualTo(harvest1);
    }


    @Test (expected = EntityNotFoundException.class)
    public void test03ReadByIdHarvestEntityNotFoundException() throws EntityNotFoundException {
        this.harvestService.readById((long)-999);
    }


    @Test (expected = EntityAlreadyExistsException.class)
    public void test04CreateHarvestEntityNotFoundException() throws EntityAlreadyExistsException, AnyPersistenceException {
        this.harvestService.create(this.harvest1);
    }

    @Test
    public void test05CreateHarvest() throws EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {
        this.harvestService.create(this.harvest3);
        assertThat(this.harvestRepository.findById(harvest3.getId()).isPresent()).isTrue();
        assertThat(this.harvestService.readById(harvest3.getId()))
                .isNotNull()
                .isEqualTo(harvest3);
    }


    @Test (expected = EntityNotFoundException.class)
    public void test06DeleteHarvestEntityNotFoundException() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        this.harvestService.delete((long)-999);
    }

    @Test (expected = EntityNotFoundException.class)
    public void test07DeleteHarvest() throws Exception {
        this.harvest4 = Harvest.builder()
                .name("Safra 2208/2209")
                .begin(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2208"))
                .end(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-03-2209"))
                .build();
        this.harvestService.create(this.harvest4);
        this.harvestService.delete(this.harvest4.getId());
        assertThat(this.harvestService.readAll())
                .doesNotContain(this.harvest4)
                .doesNotContainNull();
        this.harvestService.readById(this.harvest4.getId());
    }


    @Test (expected = EntityInUseException.class)
    public void test08DeleteHarvestEntityInUseException() throws Exception{

        this.harvest5  = Harvest.builder()
                .name("Safra 1108/2209")
                .begin(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-1108"))
                .end(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-03-1109"))
                .build();

        this.harvestService.create(this.harvest5);

        this.surveyRepository.save(
            Survey.builder()
            .harvest(this.harvest5)
            .field(this.field1)
                        .seedName("P95R51")
                        .sowedDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-9-26"))
            .emergenceDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-10"))
            .harvestDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2018-2-15"))
            .rustResistant(false)
                        .bt(false)
                        .totalArea(7.26)
                        .totalPlantedArea(242)
                        .plantPerMeter(15)
                        .productivityField(187)
                        .productivityFarmer(170)
                        .separatedWeight(true)
                        .longitude(7.5)
                        .latitude(8.5)
                        .build()
        );
        this.harvestService.delete(this.harvest5.getId());
    }


    @Test (expected = EntityNotFoundException.class)
    public void test09UpdateHarvestEntityNotFoundException() throws Exception {

        Harvest harvestTest =  Harvest.builder()
                .name("Safra 4408/2209")
                .begin(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-4408"))
                .end(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-03-4409"))
                .build();

        this.harvestService.create(harvestTest);
        assertThat(this.harvestService.readById(harvestTest.getId()))
                .isNotNull()
                .isEqualTo(harvestTest);
        this.harvestService.delete(harvestTest.getId());
        assertThat(this.harvestService.readAll())
                .doesNotContainNull()
                .doesNotContain(harvestTest);
        this.harvestService.update(harvestTest);
    }

    @Test (expected = AnyPersistenceException.class)
    public void test10UpdateHarvestAnyPersistenceException() throws Exception {
//        Harvest harvestTest =  Harvest.builder()
//                .name("Safra 5508/2209")
//                .begin(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-5508"))
//                .end(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-03-5509"))
//                .build();
//
//        assertThat(this.harvestService.readById(harvestTest.getId()))
//                .isNotNull()
//                .isEqualTo(harvestTest);
//        this.harvestService.delete(harvestTest.getId());
//        harvestTest.setName("ABCD");
//        this.harvestService.update(harvestTest);
        //Descubrir uma forma de disparar exception AnyPersistenceException
    }

    @Test
    public void test11UpdateHarvest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException, ParseException {
        Harvest harvestTest =  Harvest.builder()
                .name("Safra 6608/6609")
                .begin(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-6608"))
                .end(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-03-6609"))
                .build();
        this.harvestService.create(harvestTest);
        harvestTest.setName("Safra 2111/2112");
        harvestTest.setBegin(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2111"));
        harvestTest.setEnd(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-03-2112"));
        harvestService.update(harvestTest);
        Harvest harvestTestUpdate = this.harvestService.readById(harvestTest.getId());
        assertThat(harvestTestUpdate)
                .isNotNull()
                .isEqualTo(harvestTest);
        assertThat(harvestTestUpdate.getName()).isEqualToIgnoringCase("Safra 2111/2112");
        assertThat(harvestTestUpdate.getBegin().toString()).isEqualTo("2111-10-01");
        assertThat(harvestTestUpdate.getEnd().toString()).isEqualTo("2112-03-01");
    }
}
