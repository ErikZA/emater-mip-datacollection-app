package br.edu.utfpr.cp.emater.midmipsystem.service.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.*;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.exception.*;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.*;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.HarvestRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.SurveyRepository;
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
public class FieldServiceTest {

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
    private  CityService cityService;
    @Autowired
    private SupervisorService supervisorService;
    @Autowired
    private FarmerService farmerService;

    private Field field1, field2, field3;
    private City city1,city2,city3,city4,city5;
    private Farmer farmer1,farmer2, farmer3;
    private Supervisor supervisor1, supervisor2, supervisor3;
    private Region region1;
    @Before
    public void SetUp(){
        var mr1 = this.macroRegionRepository.save(MacroRegion.builder().name("Macro Leste").build());

        this.city1
 = this.cityRepository.save(City.builder().name("Ponta Grossa").state(State.PR).build());
        this.city2 = this.cityRepository.save(City.builder().name("Pinhais").state(State.PR).build());
        this.city3 = this.cityRepository.save(City.builder().name("Curitiba").state(State.PR).build());
        this.city4
 = City.builder().name("Paranagua").state(State.PR).build();
        this.city5 = City.builder().name("Colombo").state(State.PR).build();
        this.region1 = Region.builder().name("Curitiba").macroRegion(mr1).build();
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
        this.field3 = Field.builder().name("Pinhao").location("").city(city3).farmer(farmer2).build();
        this.field3.addSupervisor(supervisor2);
    }

    @Test
    public void test01ReadAllCities() {
        assertThat(this.fieldService.readAllCities())
                .contains(city1
        ,city2,city3)
                .doesNotContain(city4
        ,city5)
                .doesNotContainNull();
    }

    @Test
    public void test02ReadAllFarmers() {
        assertThat(this.fieldService.readAllFarmers()).contains(farmer1,farmer2)
                .doesNotContain(farmer3)
                .doesNotContainNull();
    }

    @Test
    public void test03ReadCityById() {
        assertThat(this.fieldService.readCityById(this.city1
.getId()))
                .isEqualTo(city1
        )
                .isNotNull();
    }

    @Test //os dados do CLR entrão primeiro no banco - redorna o indice 0 da tabela
    public void test04ReadCityByIdNotFound() {
        this.city4
 = this.cityRepository.save(city4
);
        this.cityRepository.deleteById(city4
.getId());
        assertThat(this.fieldService.readCityById(city4
.getId())).
                isEqualTo(this.cityRepository.findAll().get(0))
                .isNotNull();
    }

    @Test
    public void test05ReadFarmerById() {
        assertThat(this.fieldService.readFarmerById(this.farmer1.getId()))
                .isEqualTo(farmer1)
                .isNotNull();
    }

    @Test //os dados do CLR entrão primeiro no banco - redorna o indice 0 da tabela
    public void test06ReadFarmerByIdNotFound() {
        this.farmer3 = this.farmerRepository.save(this.farmer3);
        this.farmerRepository.deleteById(this.farmer3.getId());
        assertThat(this.fieldService.readFarmerById(this.farmer3.getId()))
                .isEqualTo(this.farmerRepository.findAll().get(0))
                .isNotNull();
    }

    @Test
    public void test07ReadAllField(){
        assertThat(this.fieldService.readAll()).contains(field1)
                .doesNotContain(field2,field3)
                .doesNotContainNull();
    }

    @Test
    public void test08ReadAllSupervisors(){
        assertThat(this.fieldService.readAllSupervisors()).contains(supervisor1,supervisor2)
                .doesNotContain(supervisor3)
                .doesNotContainNull();
    }

    @Test
    public void test09readByIdField() throws EntityNotFoundException {
            assertThat(this.fieldService.readById(this.field1.getId()))
                    .isEqualTo(field1)
                    .isNotNull();
    }

    @Test (expected = EntityNotFoundException.class)
    public void test10ReadByIdFieldNotFoundException() throws EntityNotFoundException {
        this.field3 = this.fieldRepository.save(this.field3);
        this.fieldRepository.deleteById(field3.getId());
        this.fieldService.readById(field3.getId());
    }


    @Test //se id de cidade ou id de produtor for null, gera exception.
    public void test11CreateField() throws SupervisorNotAllowedInCity, EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {
        //Teste
        this.region1.addCity(this.city4
);
        this.regionRepository.save(this.region1);
        this.fieldRepository.delete(field2);
        this.fieldService.create(field2);
    }

    @Test (expected =EntityNotFoundException.class )
    public void test12DeleteField() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        this.fieldService.delete(field1.getId());
        assertThat(this.fieldRepository.findById(this.field1.getId()).isPresent()).isEqualTo(false);
        this.fieldService.readById(this.field1.getId());
    }

    @Test (expected = EntityNotFoundException.class)
    public void test13DeleteFieldNotFoundException() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        this.field3 = this.fieldRepository.save(this.field3);
        this.fieldRepository.deleteById(field3.getId());
        this.fieldService.delete(field3.getId());
    }

    @Test(expected = EntityInUseException.class)
    public void  test14DeleteFieldUseException() throws ParseException, EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        this.surveyRepository.save(
                Survey.builder()
                        .harvest(harvestRepository.save(
                                Harvest.builder()
                                        .name("Safra 2016/2017")
                                        .begin(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2016"))
                                        .end(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-03-2017"))
                                        .build()))
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
        this.fieldService.delete(this.field1.getId());
    }

    @Test (expected = EntityNotFoundException.class)
    public void test15UpdateFieldNotFoundException() throws AnyPersistenceException, SupervisorNotAllowedInCity, EntityAlreadyExistsException, EntityNotFoundException {
        this.fieldRepository.deleteById(this.field1.getId());
        this.fieldService.update(this.field1);
    }

    @Test (expected = SupervisorNotAllowedInCity.class)
    public void test16UpdateFieldSupervisorNotAllowedInCity() throws AnyPersistenceException, SupervisorNotAllowedInCity, EntityAlreadyExistsException, EntityNotFoundException {
        this.field1.setCity(city3);
        this.fieldService.update(this.field1);
    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void test17UpdateFieldEntityAlreadyExistsException() throws AnyPersistenceException, SupervisorNotAllowedInCity, EntityAlreadyExistsException, EntityNotFoundException {
        this.fieldService.create(this.field1);
    }

    @Test
    public void test18UpdateField() throws AnyPersistenceException, SupervisorNotAllowedInCity, EntityAlreadyExistsException, EntityNotFoundException {
        this.region1.addCity(this.city2);
        this.field1.setCity(this.city2);
        this.field1.addSupervisor(this.supervisor2);
        this.field1.setName("Modulo Teste");
        this.fieldService.update(this.field1);
        //Esta falando que cidades não foi inicializada; Aparentemente funcionou!!
    }

}