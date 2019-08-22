package br.edu.utfpr.cp.emater.midmipsystem.service.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.*;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FieldServiceTest {


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

        this.field1 = Field.builder().name("Macaxeira").location("1").city(city1).farmer(farmer1).build();
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
        BDDMockito.when(this.supervisorRepository.findAll())
                .thenReturn(listSupervisor);

    }

    @Test
    public void fieldServiceTestReadAllCities() {
        assertThat(this.fieldService.readAllCities())
                .containsExactlyInAnyOrder(this.city1,this.city2,this.city3)
                .doesNotContain(this.city4,this.city5)
                .doesNotContainNull();
    }

    @Test
    public void fieldServiceTestReadAllFarmers() {
        assertThat(this.fieldService.readAllFarmers())
                .containsExactlyInAnyOrder(this.farmer1,this.farmer2)
                .doesNotContain(this.farmer3)
                .doesNotContainNull();
    }

    @Test
    public void fieldServiceTestReadCityById() {
        BDDMockito.when(cityRepository.findById((long) 2))
                .thenReturn(Optional.ofNullable(this.city2));
        assertThat(this.fieldService.readCityById(this.city2.getId()))
                .isEqualTo(this.city2)
                .isNotNull();
    }

    @Test // redorna o indice 0 da tabela
    public void fieldServiceTestReadCityByIdNotFound() {
        BDDMockito.when(cityRepository.findById((long)4))
                .thenReturn(Optional.ofNullable(null));
        assertThat(this.fieldService.readCityById(city4.getId())).
                isEqualTo(this.city1)
                .isNotNull();
    }

    @Test
    public void fieldServiceTestReadFarmerById() {
        BDDMockito.when(farmerRepository.findById((long)2))
                .thenReturn(Optional.ofNullable(this.farmer2));
        assertThat(this.fieldService.readFarmerById(this.farmer2.getId()))
                .isEqualTo(this.farmer2)
                .isNotNull();
    }

    @Test // redorna o indice 0 da tabela
    public void fieldServiceTestReadFarmerByIdNotFound() {
        BDDMockito.when(farmerRepository.findById((long)3))
                .thenReturn(Optional.ofNullable(null));
        assertThat(this.fieldService.readFarmerById(this.farmer3.getId()))
                .isEqualTo(this.farmer1)
                .isNotNull();
    }

    @Test
    public void fieldServiceTestReadAllField(){
        assertThat(this.fieldService.readAll())
                .containsExactlyInAnyOrder(this.field1,this.field2)
                .doesNotContain(field3)
                .doesNotContainNull();
    }

    @Test
    public void fieldServiceTestReadAllSupervisors(){
        assertThat(this.fieldService.readAllSupervisors())
                .containsExactlyInAnyOrder(this.supervisor1,this.supervisor2)
                .doesNotContain(supervisor3)
                .doesNotContainNull();
    }

    @Test
    public void fieldServiceTestReadByIdField() throws EntityNotFoundException {
        BDDMockito.when(fieldRepository.findById((long)2))
                .thenReturn(Optional.ofNullable(this.field2));
            assertThat(this.fieldService.readById(this.field2.getId()))
                    .isEqualTo(this.field2)
                    .isNotNull();
    }

    @Test (expected = EntityNotFoundException.class)
    public void fieldServiceTestReadByIdFieldNotFoundException() throws EntityNotFoundException {
        BDDMockito.when(fieldRepository.findById((long)3))
                .thenReturn(Optional.ofNullable(null));

            this.fieldService.readById(field3.getId());
            fail("EntityNotFoundException it is not throws");
    }


    @Test (expected = EntityAlreadyExistsException.class)
    public void fieldServiceTestCreateFieldEntityAlreadyExistsException() throws AnyPersistenceException, SupervisorNotAllowedInCity, EntityAlreadyExistsException, EntityNotFoundException {
            this.fieldService.create(this.field2);
            fail("EntityAlreadyExistsException it is not throws");
    }

    @Test
    public void fieldServiceTestDeleteField() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        BDDMockito.when(fieldRepository.findById((long)2))
                .thenReturn(Optional.ofNullable(this.field2));
        BDDMockito.doNothing().when(fieldRepository).delete(this.field2);
        this.fieldService.delete(field2.getId());
    }

    @Test (expected = EntityNotFoundException.class)
    public void fieldServiceTestDeleteFieldNotFoundException() throws AnyPersistenceException, EntityInUseException, EntityNotFoundException {
        BDDMockito.when(this.fieldRepository.findById((long)3))
                .thenReturn(Optional.ofNullable(null));

            this.fieldService.delete(this.field3.getId());
            fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = EntityInUseException.class)
    public void  fieldServiceTestDeleteFieldUseEntityInUseException() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        BDDMockito.when(fieldRepository.findById((long)2))
                .thenReturn(Optional.ofNullable(this.field2));
        BDDMockito.doThrow(DataIntegrityViolationException.class)
                .when(fieldRepository).delete(this.field2);

        this.fieldService.delete(this.field2.getId());
            fail("EntityInUseException it is not throws");
    }

    @Test (expected = EntityNotFoundException.class)
    public void fieldServiceTestUpdateFieldNotFoundException() throws AnyPersistenceException, SupervisorNotAllowedInCity, EntityAlreadyExistsException, EntityNotFoundException {
        BDDMockito.when(fieldRepository.findById((long)3))
                .thenReturn(Optional.ofNullable(null));

            this.fieldService.update(this.field3);
            fail("EntityInUseException it is not throws");
    }

    @Test (expected = SupervisorNotAllowedInCity.class)
    public void fieldServiceTestUpdateFieldSupervisorNotAllowedInCity() throws AnyPersistenceException, EntityAlreadyExistsException, EntityNotFoundException, SupervisorNotAllowedInCity {
        this.field1.setCity(city3);
        BDDMockito.when(this.fieldRepository.findById((long)1))
                .thenReturn(Optional.ofNullable(this.field1));

            this.fieldService.update(this.field1);
            fail("SupervisorNotAllowedInCity it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)//tenta deletar uma compo inexistente
    public void fieldServiceTestDeleteFieldAnyPersistenceException() throws EntityNotFoundException, EntityInUseException, AnyPersistenceException {
        BDDMockito.when(fieldRepository.findById((long)2))
                .thenReturn(Optional.ofNullable(this.field2));
        BDDMockito.doThrow(IllegalArgumentException.class)
                .when(fieldRepository).delete(any(Field.class));

            this.fieldService.delete(this.field2.getId());
            fail("AnyPersistenceException it is not throws");
    }

    @Test
    public void fieldServiceTestUpdateField() throws AnyPersistenceException, SupervisorNotAllowedInCity, EntityAlreadyExistsException, EntityNotFoundException {
        BDDMockito.when(this.fieldRepository.findById((long)1))
                .thenReturn(Optional.ofNullable(this.field1));
        BDDMockito.when(this.cityRepository.findById((long)2))
                .thenReturn(Optional.ofNullable(this.city2));
        BDDMockito.when(this.farmerRepository.findById((long)2))
                .thenReturn(Optional.ofNullable(this.farmer2));
        BDDMockito.when(this.fieldRepository.saveAndFlush(this.field1))
                .thenReturn(this.field1);
        BDDMockito.when(this.supervisorRepository.findById((long)1))
                .thenReturn(Optional.ofNullable(this.supervisor1));
        BDDMockito.when(this.supervisorRepository.findById((long)2))
                .thenReturn(Optional.ofNullable(this.supervisor2));


//        assertThat(this.field1.getName()).isEqualToIgnoringCase("Macaxeira");
//        assertThat(this.field1.getCity()).isEqualTo(this.city1);
//        assertThat(this.field1.getSupervisors()).containsExactlyInAnyOrder(this.supervisor1);
//        assertThat(this.field1.getLocation()).isEqualToIgnoringCase("1");
//        assertThat(this.field1.getFarmer()).isEqualTo(this.farmer1);

        this.region1.addCity(this.city2);
        this.field1.setCity(this.city2);
        this.field1.addSupervisor(this.supervisor2);
        this.field1.setName("Teste Update");
        this.field1.setLocation("22");
        this.field1.setFarmer(this.farmer2);

        //colocar em uma lista mock e atualizar da lista?

        this.fieldService.update(this.field1);
//
//        assertThat(this.field1.getName()).isEqualToIgnoringCase("Teste Update");
//        assertThat(this.field1.getCity()).isEqualTo(this.city2);
//        assertThat(this.field1.getSupervisors()).containsExactlyInAnyOrder(this.supervisor1,this.supervisor2);
//        assertThat(this.field1.getLocation()).isEqualToIgnoringCase("22");
//        assertThat(this.field1.getFarmer()).isEqualTo(this.farmer2);

    }

    @Test (expected = AnyPersistenceException.class) //Farmer não encontrado no banco
    public void fieldServiceTestUpdateFieldAnyPersistenceException() throws SupervisorNotAllowedInCity, EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        BDDMockito.when(this.fieldRepository.findById((long)1))
                .thenReturn(Optional.ofNullable(this.field1));
        BDDMockito.when(this.cityRepository.findById((long)1))
                .thenReturn(Optional.ofNullable(this.city1));
        BDDMockito.when(this.supervisorRepository.findById((long)1))
                .thenReturn(Optional.ofNullable(this.supervisor1));
        BDDMockito.when(this.farmerRepository.findById((long)3))
                .thenReturn(Optional.ofNullable(null));

        this.field1.setName("Teste Fail");
        this.field1.setFarmer(this.farmer3);

        this.fieldService.update(this.field1);
        fail("AnyPersistenceException it is not throws");
    }


    @Test (expected = SupervisorNotAllowedInCity.class)
    public void fieldServiceTestCreateFieldSupervisorNotAllowedInCity() throws EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException, SupervisorNotAllowedInCity {
        this.field3.setCity(city1);

        BDDMockito.when(this.cityRepository.findById((long)1))
                .thenReturn(Optional.ofNullable(this.city1));
        BDDMockito.when(this.supervisorRepository.findById((long)2))
                .thenReturn(Optional.ofNullable(this.supervisor2));

            this.fieldService.create(field3);
            fail("SupervisorNotAllowedInCity it is not throws");
    }

    @Test
    public void fieldServiceTestCreateField() throws SupervisorNotAllowedInCity, EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {
        BDDMockito.when(this.cityRepository.findById((long)3))
                .thenReturn(Optional.ofNullable(this.city3));
        BDDMockito.when(this.farmerRepository.findById((long)2))
                .thenReturn(Optional.ofNullable(this.farmer2));
        BDDMockito.when(this.fieldRepository.save(this.field3))
                .thenReturn(this.field3);
        BDDMockito.when(this.supervisorRepository.findById((long)2))
                .thenReturn(Optional.ofNullable(this.supervisor2));

        this.fieldService.create(this.field3);
    }

    @Test (expected = AnyPersistenceException.class)//em algum momento o registro passa a ser nullo.
    public void fieldServiceTestCreateFieldAnyPersistenceException() throws SupervisorNotAllowedInCity, EntityAlreadyExistsException, EntityNotFoundException, AnyPersistenceException {
        BDDMockito.when(this.cityRepository.findById((long)3))
                .thenReturn(Optional.ofNullable(this.city3));
        BDDMockito.when(this.farmerRepository.findById((long)2))
                .thenReturn(Optional.ofNullable(this.farmer2));
        BDDMockito.when(this.fieldRepository.save(field3))
                .thenThrow(IllegalArgumentException.class);
        BDDMockito.when(this.supervisorRepository.findById((long)2))
                .thenReturn(Optional.ofNullable(this.supervisor2));

            this.fieldService.create(field3);
            fail("AnyPersistenceException it is not throws");
    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void fieldServiceTestUpdateFieldEntityAlreadyExistsException() throws AnyPersistenceException, SupervisorNotAllowedInCity, EntityNotFoundException, EntityAlreadyExistsException {
        Field copyField = this.field1;
        List<Field> listField = asList(this.field1,this.field2, copyField);
        BDDMockito.when(this.fieldRepository.findAll())
                .thenReturn(listField);
        BDDMockito.when(this.fieldRepository.findById((long)1))
                .thenReturn(Optional.ofNullable(this.field1));
        this.field1.setName("Teste Fail");

            this.fieldService.update(this.field1);
            fail("EntityAlreadyExistsException it is not throws");
    }

}
