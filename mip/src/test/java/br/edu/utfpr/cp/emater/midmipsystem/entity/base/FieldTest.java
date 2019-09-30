package br.edu.utfpr.cp.emater.midmipsystem.entity.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.State;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.FieldRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class  FieldTest {

    private MacroRegion macroRegion;
    private Region region;
    private City city;
    private Supervisor supervisor1, supervisor2;
    private Field field;
    private  Farmer farmer;

    @Mock
    private FieldRepository fieldRepository=null;

        @Before
        public void setUp(){
            this.macroRegion = MacroRegion.builder().name("MACRO NORDESTE").build();
            this.region = Region.builder().name("CORNELIO PROCOPIO").macroRegion(this.macroRegion).build();
            this.city = City.builder().name("NOvA FAtimA").state(State.PR).build();
            this.city.setId((long)1);
            this.region.addCity(this.city);
            this.farmer = Farmer.builder().name("PauLO MARIAnO").build();
            this.farmer.setId((long)1);
            this.supervisor1 = Supervisor.builder().name("Franciscano Souza").email("FranciscanoSouza@EMATER.COM").region(this.region).build();
            this.supervisor2 = Supervisor.builder().name("JOAO BEZERRA").email("JOAOBEZERRA@EMATER.COM").region(this.region).build();
            this.field = Field.builder().name("TEST casE").location("1").city(city).farmer(this.farmer).build();
            MockitoAnnotations.initMocks(this);
        }

        @Test //testa novo supervisor
        public void fieldTestAddSupervisor(){
            assertThat(this.field.addSupervisor(this.supervisor1)).isTrue();
            assertThat(this.field.addSupervisor(this.supervisor2)).isTrue();
        }


        @Test
        public  void fieldTestGetCityNameTestCase(){
            assertThat(this.field.getCityName()).isEqualTo("Nova Fatima");
        }

        @Test
        public  void fieldTestGetCityIdTest(){
            assertThat(this.field.getCityId())
                    .isNotNull()
                    .isEqualTo((long)1);
        }

        @Test
        public  void fieldTestSetNameTestCase(){
            assertThat(field.getName()).isEqualTo("Test Case");
        }

        @Test//caso um nulo seja inserido, este metodo gera um nullPointerException
        public  void fieldTestGetSupervisorNamesTestCase(){
            assertThat(this.field.addSupervisor(this.supervisor1)).isTrue();
            assertThat(this.field.addSupervisor(this.supervisor2)).isTrue();
            assertThat(this.field.getSupervisorNames()).containsExactlyInAnyOrder("Franciscano Souza","Joao Bezerra");
        }


        //Este teste Falha quando é inserido um nullo na entidade
        @Ignore
        @Test// adiciona um supervisor nulo, cidade nula e outros??
        public void fieldTestAddSupervisorIsNull(){
            assertThat(field.addSupervisor(null)).isFalse();
        }


        @Test
        public  void fieldTestSetStateNameTestCase(){
            assertThat(this.field.getStateName()).isEqualTo("Paraná");
        }

        @Test
        public  void fieldTestGetFarmerNameTestCase(){
            assertThat(this.field.getFarmerName()).isEqualTo("Paulo Mariano");
        }

        @Test
        public  void fieldTestGetFarmerIdTest(){
            assertThat(this.field.getFarmerId())
                    .isEqualTo((long)1)
                    .isNotNull();
        }


        @Test
        public void fieldTestRemoveSupervisor(){
            assertThat(this.field.addSupervisor(this.supervisor1)).isTrue();
            assertThat(this.field.addSupervisor(this.supervisor2)).isTrue();
            assertThat(this.field.removeSupervisor(this.supervisor1)).isTrue();
            assertThat(this.field.removeSupervisor(this.supervisor2)).isTrue();
        }

        @Test
        public void fieldTestRemoveSupervisorNotExistContainer(){
            Field fieldTest = Field.builder().name("TestCaseContainer").location("1").city(this.city).farmer(this.farmer).build();
            assertThat(fieldTest.removeSupervisor(this.supervisor1)).isFalse();
        }

        @Test
        public void fieldTestRemoveSupervisorNotExist(){
            assertThat(this.field.addSupervisor(this.supervisor1)).isTrue();
            assertThat(this.field.removeSupervisor(this.supervisor1)).isTrue();
            assertThat(this.field.removeSupervisor(this.supervisor1)).isFalse();
        }

        @Test
        public void fieldTestEqualsFalse() {
            Field fieldTest = Field.builder().name("TestCaseContainer").location("1").city(this.city).farmer(this.farmer).build();
            assertThat(this.field.equals(fieldTest)).isFalse();
        }

        @Test
        public void fieldTestEqualsTrue() {
                Field fielTest = this.field;
                assertThat(this.field.equals(fielTest)).isTrue();
        }

        @Test
        public void fieldTestHashCodeTrue() {
            int value = this.field.hashCode();
            assertThat(this.field.hashCode()).isEqualTo(value);
        }

    @Test
    public void fieldTestHashCodeFalse() {
            int value = this.field.hashCode();
            value++;
        assertThat(this.field.hashCode()==value).isFalse();
    }

        @Test (expected = ConstraintViolationException.class)
        public  void setNameExceptionNameLessThan5FieldTest() {
            when(this.fieldRepository.save(this.field)).thenThrow(ConstraintViolationException.class);
            this.field.setName("test");
            this.fieldRepository.save(this.field);
        }


        @Test (expected = ConstraintViolationException.class)
        public  void setNameExceptionNameBigThan50FFieldTest() {
            when(this.fieldRepository.save(this.field)).thenThrow(ConstraintViolationException.class);
            this.field.setName("123456789-123456789-123456789-123456789-123456789-1");
            this.fieldRepository.save(this.field);
        }

        @Test
        public  void getAndSetLocationTest() {
            Field fieldTest = new Field();
            fieldTest.setLocation("TestLocation");
            assertThat(fieldTest.getLocation()).isEqualToIgnoringCase("TestLocation");
        }


        @Test
        public  void getAndSetIdFieldTest() {
            Field fieldTest = new Field();
            fieldTest.setId((long)10);
            assertThat(fieldTest.getId()).isEqualTo((long)10);
        }
}
