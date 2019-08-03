package br.edu.utfpr.cp.emater.midmipsystem.service.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.*;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.exception.*;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.*;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.HarvestRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.SurveyRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


import javax.enterprise.inject.Any;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FieldServiceTest {

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
    private FieldService fieldService;


    private City city1,city2,city3,city4,city5;
    @Autowired
    private Field field1, field2, field3;
    @Autowired
    private Farmer farmer1,farmer2, farmer3;
    @Autowired
    private Supervisor supervisor1, supervisor2, supervisor3;
    @Autowired
    private Region region1, region2;
    @Autowired
    private  MacroRegion macroRegion;

    @Before
    public void SetUp(){

        this.macroRegion = MacroRegion.builder().name("Macro Leste").build();
        macroRegion.setId((long) 1);

        this.city1 = City.builder().name("Ponta Grossa").state(State.PR).build();
        this.city1.setId((long) 1);

        this.city2 = City.builder().name("Pinhais").state(State.PR).build();
        this.city2.setId((long) 2);

        this.city3 = City.builder().name("Curitiba").state(State.PR).build();
        this.city3.setId((long) 3);

        this.city4 = City.builder().name("Paranagua").state(State.PR).build();
        this.city4.setId((long) 4);

        this.city5 = City.builder().name("Colombo").state(State.PR).build();
        this.city5.setId((long) 5);

        this.region1 = Region.builder().name("Curitiba").macroRegion(this.macroRegion).build();
        this.region1.addCity(city1);
        this.region1.setId((long) 1);

        this.region2 = Region.builder().name("Ponta Grossa").macroRegion(this.macroRegion).build();
        this.region2.addCity(city2);
        this.region2.addCity(city3);
        this.region2.setId((long) 2);

        this.farmer1 = Farmer.builder().name("Marcos Paulo").build();
        this.farmer1.setId((long) 1);
        this.farmer2 = Farmer.builder().name("Otaviano Gregorio").build();
        this.farmer2.setId((long) 2);
        this.farmer3 = Farmer.builder().name("Malakoi silva").build();
        this.farmer3.setId((long) 3);

        this.supervisor1 = Supervisor.builder().name("Inoan Martins").email("InoanMartins@emater.pr.gov.br").region(this.region1).build();
        this.supervisor1.setId((long) 1);
        this.supervisor2 = Supervisor.builder().name("David Luiz").email("DavidLuiz@emater.pr.gov.br").region(this.region2).build();
        this.supervisor2.setId((long) 2);
        this.supervisor3 = Supervisor.builder().name("Marcos Paulo Nunes").email("MarcosPauloNunes@emater.pr.gov.br").region(this.region2).build();
        this.supervisor3.setId((long) 3);

        this.field1 = Field.builder().name("Macaxeira").location("").city(city1).farmer(farmer1).build();
        this.field1.addSupervisor(supervisor1);
        this.field1.setId((long) 1);
        this.field2 = Field.builder().name("Canela").location("").city(city3).farmer(farmer2).build();
        this.field2.setId((long) 2);
        this.field3 = Field.builder().name("Pinhao").location("").city(city3).farmer(farmer2).build();
        this.field3.addSupervisor(supervisor2);
        this.field3.setId((long) 3);

        List<City> listCity = asList(this.city1,this.city2,this.city3);
        BDDMockito.when(cityRepository.findAll())
                .thenReturn(listCity);

        List<Farmer> listFarmer = asList(this.farmer1,this.farmer2);
        BDDMockito.when(farmerRepository.findAll())
                .thenReturn(listFarmer);

        List<Field> listField = asList(this.field1,this.field2);
        BDDMockito.when(fieldRepository.findAll())
                .thenReturn(listField);

        List<Supervisor> listSupervisor = asList(this.supervisor1,this.supervisor2);
        BDDMockito.when(supervisorRepository.findAll())
                .thenReturn(listSupervisor);

        BDDMockito.doNothing().when(fieldRepository).delete(this.field2);

    }


    @Test
    public void test01ReadAllCities() {
        assertThat(this.fieldService.readAllCities())
                .containsExactlyInAnyOrder(this.city1,this.city2,this.city3)
                .doesNotContain(this.city4,this.city5)
                .doesNotContainNull();
    }

    @Test
    public void test02ReadAllFarmers() {
        assertThat(this.fieldService.readAllFarmers())
                .containsExactlyInAnyOrder(farmer1,farmer2)
                .doesNotContain(farmer3)
                .doesNotContainNull();
    }

    @Test
    public void test03ReadCityById() {
        BDDMockito.when(cityRepository.findById((long) 2))
                .thenReturn(Optional.ofNullable(this.city2));
        assertThat(this.fieldService.readCityById(this.city2.getId()))
                .isEqualTo(city2)
                .isNotNull();
    }

    @Test //os dados do CLR entrão primeiro no banco - redorna o indice 0 da tabela
    public void test04ReadCityByIdNotFound() {
        BDDMockito.when(cityRepository.findById((long)4))
                .thenReturn(Optional.ofNullable(null));
        assertThat(this.fieldService.readCityById(city4.getId())).
                isEqualTo(this.city1)
                .isNotNull();
    }

    @Test
    public void test05ReadFarmerById() {
        BDDMockito.when(farmerRepository.findById((long)2))
                .thenReturn(Optional.ofNullable(this.farmer2));
        assertThat(this.fieldService.readFarmerById(this.farmer2.getId()))
                .isEqualTo(this.farmer2)
                .isNotNull();
    }

    @Test //os dados do CLR entrão primeiro no banco - redorna o indice 0 da tabela
    public void test06ReadFarmerByIdNotFound() {
        BDDMockito.when(farmerRepository.findById((long)3))
                .thenReturn(Optional.ofNullable(null));
        assertThat(this.fieldService.readFarmerById(this.farmer3.getId()))
                .isEqualTo(this.farmer1)
                .isNotNull();
    }

    @Test
    public void test07ReadAllField(){
        assertThat(this.fieldService.readAll())
                .containsExactlyInAnyOrder(this.field1,this.field2)
                .doesNotContain(field3)
                .doesNotContainNull();
    }

    @Test
    public void test08ReadAllSupervisors(){
        assertThat(this.fieldService.readAllSupervisors())
                .containsExactlyInAnyOrder(this.supervisor1,this.supervisor2)
                .doesNotContain(supervisor3)
                .doesNotContainNull();
    }

    @Test
    public void test09readByIdField() throws EntityNotFoundException {
        BDDMockito.when(fieldRepository.findById((long)2))
                .thenReturn(Optional.ofNullable(this.field2));
            assertThat(this.fieldService.readById(this.field2.getId()))
                    .isEqualTo(this.field2)
                    .isNotNull();
    }

    @Test (expected = EntityNotFoundException.class)
    public void test10ReadByIdFieldNotFoundException() throws EntityNotFoundException {
        BDDMockito.when(fieldRepository.findById((long)3))
                .thenReturn(Optional.ofNullable(null));
        this.fieldService.readById(field3.getId());
    }


    @Test //se id de cidade ou id de produtor for null, gera exception.
    public void test11CreateField() throws SupervisorNotAllowedInCity, EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {
//        Teste
//        this.region1.addCity(this.city4);
//        this.regionRepository.save(this.region1);
//        this.fieldRepository.delete(field2);
//        this.fieldService.create(field2);
    }

    @Test
    public void test12DeleteField() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        this.fieldService.delete(field2.getId());
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
