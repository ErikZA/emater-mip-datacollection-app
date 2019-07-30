package br.edu.utfpr.cp.emater.midmipsystem.entity.base;

import br.edu.utfpr.cp.emater.midmipsystem.repository.base.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class    FieldTest {

        private  City city;
        private  Farmer farmer;
        private List<Supervisor> supervisor = new ArrayList<Supervisor>();
        private Field field;
        private Region region;
        private MacroRegion macroRegion;

        @Autowired
        private CityRepository cityRepository;
        @Autowired
        private FarmerRepository farmerRepository;
        @Autowired
        private FieldRepository fieldRepository;


        @Before
        public void setUp(){
            this.macroRegion = MacroRegion.builder().name("MACRO NORDESTE").build();
            this.region = Region.builder().name("CORNELIO PROCOPIO").macroRegion(this.macroRegion).build();
            this.city = City.builder().name("NOvA FAtimA").state(State.PR).build();
            this.region.addCity(this.city);
            this.farmer = Farmer.builder().name("PauLO MARIAnO").build();
            this.supervisor.add(Supervisor.builder().name("Franciscano Souza").email("FranciscanoSouza@EMATER.COM").region(this.region).build());
            this.supervisor.add(Supervisor.builder().name("JOAO BEZERRA").email("JOAOBEZERRA@EMATER.COM").region(this.region).build());
            this.field = Field.builder().name("TEST casE").location("1").city(city).farmer(this.farmer).build();
        }

        @Test //testa novo supervisor
        public void test01AddSupervisor(){
            assertThat(this.field.addSupervisor(this.supervisor.get(0))).isTrue();
            assertThat(this.field.addSupervisor(this.supervisor.get(1))).isTrue();
        }


        @Test
        public  void test02GetCityNameTestCase(){
            assertThat(this.field.getCityName()).isEqualTo("Nova Fatima");
        }

        @Test
        public  void test03GetCityIdTest(){
            assertThat(this.cityRepository.save(this.city)).isNotNull();
            assertThat(this.field.getCityId()).isNotNull();
        }

        @Test
        public  void test04SetNameTestCase(){
            assertThat(field.getName()).isEqualTo("Test Case");
        }

        @Test //caso um nulo seja inserido, este metodo gera um nullPointerException
        public  void test05GetSupervisorNamesTestCase(){
            assertThat(this.field.addSupervisor(this.supervisor.get(0))).isTrue();
            assertThat(this.field.addSupervisor(this.supervisor.get(1))).isTrue();
            assertThat(this.field.getSupervisorNames()).contains("Franciscano Souza","Joao Bezerra");
        }

        @Test // adiciona um supervisor nulo, cidade nula e outros??
        public void test06AddSupervisorIsNull(){
            assertThat(field.addSupervisor(null)).isFalse();
        }

        //falta testar limite 50 e 5 caracteres.
        // Deve estar entre os codigos de Repository?

        @Test (expected = ConstraintViolationException.class)
        public  void test07SetNameExceptionNameLessThan5Test() {
            this.field.setName("test");
            this.fieldRepository.save(this.field);
        }

        @Test (expected = ConstraintViolationException.class)
        public  void test08SetNameExceptionNameBigThan50Test() {
            this.field.setName("123456789-123456789-123456789-123456789-123456789-1");
            this.fieldRepository.save(this.field);
        }


        @Test
        public  void test09SetStateNameTestCase(){
            assertThat(this.field.getStateName()).isEqualTo("Paraná");
        }

        @Test
        public  void test10GetFarmerNameTestCase(){
            assertThat(this.field.getFarmerName()).isEqualTo("Paulo Mariano");
        }

        @Test
        public  void test11GetFarmerIdTest(){
            assertThat(this.farmerRepository.save(this.farmer)).isNotNull();
            assertThat(this.field.getFarmerId());
        }


        @Test
        public void test12RemoveSupervisor(){
            assertThat(this.field.addSupervisor(this.supervisor.get(0))).isTrue();
            assertThat(this.field.addSupervisor(this.supervisor.get(1))).isTrue();
            assertThat(this.field.removeSupervisor(this.supervisor.get(0))).isTrue();
            assertThat(this.field.removeSupervisor(this.supervisor.get(1))).isTrue();
        }

        @Test
        public void test13RemoveSupervisorNotExistContainer(){
            Field fieldTest = Field.builder().name("TestCaseContainer").location("1").city(this.city).farmer(this.farmer).build();
            assertThat(fieldTest.removeSupervisor(this.supervisor.get(0))).isFalse();
        }

        @Test
        public void test14RemoveSupervisorNotExist(){
            assertThat(this.field.addSupervisor(this.supervisor.get(0))).isTrue();
            assertThat(this.field.removeSupervisor(this.supervisor.get(0))).isTrue();
            assertThat(this.field.removeSupervisor(this.supervisor.get(0))).isFalse();
        }
}
