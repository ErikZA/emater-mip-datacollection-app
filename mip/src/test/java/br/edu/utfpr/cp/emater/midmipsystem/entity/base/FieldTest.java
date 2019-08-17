package br.edu.utfpr.cp.emater.midmipsystem.entity.base;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class    FieldTest {

    private City city;
    @Autowired
    private  Farmer farmer;
    @Autowired
    private Supervisor supervisor1, supervisor2;
    @Autowired
    private Field field;
    @Autowired
    private Region region;
    @Autowired
    private MacroRegion macroRegion;


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
        }

        @Test //testa novo supervisor
        public void test01AddSupervisor(){
            assertThat(this.field.addSupervisor(this.supervisor1)).isTrue();
            assertThat(this.field.addSupervisor(this.supervisor2)).isTrue();
        }


        @Test
        public  void test02GetCityNameTestCase(){
            assertThat(this.field.getCityName()).isEqualTo("Nova Fatima");
        }

        @Test
        public  void test03GetCityIdTest(){
            assertThat(this.field.getCityId())
                    .isNotNull()
                    .isEqualTo((long)1);
        }

        @Test
        public  void test04SetNameTestCase(){
            assertThat(field.getName()).isEqualTo("Test Case");
        }

        @Test //caso um nulo seja inserido, este metodo gera um nullPointerException
        public  void test05GetSupervisorNamesTestCase(){
            assertThat(this.field.addSupervisor(this.supervisor1)).isTrue();
            assertThat(this.field.addSupervisor(this.supervisor2)).isTrue();
            assertThat(this.field.getSupervisorNames()).containsExactlyInAnyOrder("Franciscano Souza","Joao Bezerra");
        }

        @Test // adiciona um supervisor nulo, cidade nula e outros??
        public void test06AddSupervisorIsNull(){
            assertThat(field.addSupervisor(null)).isFalse();
        }


        @Test
        public  void test07SetStateNameTestCase(){
            assertThat(this.field.getStateName()).isEqualTo("Paraná");
        }

        @Test
        public  void test08GetFarmerNameTestCase(){
            assertThat(this.field.getFarmerName()).isEqualTo("Paulo Mariano");
        }

        @Test
        public  void test09GetFarmerIdTest(){
            assertThat(this.field.getFarmerId())
                    .isEqualTo((long)1)
                    .isNotNull();
        }


        @Test
        public void test10RemoveSupervisor(){
            assertThat(this.field.addSupervisor(this.supervisor1)).isTrue();
            assertThat(this.field.addSupervisor(this.supervisor2)).isTrue();
            assertThat(this.field.removeSupervisor(this.supervisor1)).isTrue();
            assertThat(this.field.removeSupervisor(this.supervisor2)).isTrue();
        }

        @Test
        public void test11RemoveSupervisorNotExistContainer(){
            Field fieldTest = Field.builder().name("TestCaseContainer").location("1").city(this.city).farmer(this.farmer).build();
            assertThat(fieldTest.removeSupervisor(this.supervisor1)).isFalse();
        }

        @Test
        public void test12RemoveSupervisorNotExist(){
            assertThat(this.field.addSupervisor(this.supervisor1)).isTrue();
            assertThat(this.field.removeSupervisor(this.supervisor1)).isTrue();
            assertThat(this.field.removeSupervisor(this.supervisor1)).isFalse();
        }

    @Test
    public void test13FieldEqualsFalse() {
        Field fieldTest = Field.builder().name("TestCaseContainer").location("1").city(this.city).farmer(this.farmer).build();
        assertThat(this.field.equals(fieldTest)).isFalse();
    }

    @Test
    public void test14FieldEqualsTrue() {
            Field fielTest = this.field;
            assertThat(this.field.equals(fielTest)).isTrue();
    }

    @Test
    public void test15FieldHashCodeTrue() {
        Field fielTest = this.field;
        assertThat(this.field.hashCode()).isEqualTo(fielTest.hashCode());
    }
}
