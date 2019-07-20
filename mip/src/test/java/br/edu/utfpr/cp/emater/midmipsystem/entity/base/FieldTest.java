package br.edu.utfpr.cp.emater.midmipsystem.entity.base;

import br.edu.utfpr.cp.emater.midmipsystem.repository.base.*;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.MacroRegionRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
// @SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FieldTest {

        private City city;
        private Farmer farmer;
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
        @Autowired
        private RegionRepository regionRepository;
        @Autowired
        private MacroRegionRepository macroRegionRepository;

        @Before
        public void setUp(){
            this.macroRegion = MacroRegion.builder().name("MACRO NORDESTE").build();
            this.macroRegionRepository.save(this.macroRegion);
            this.region = Region.builder().name("CORNELIO PROCOPIO").macroRegion(this.macroRegion).build();
            this.regionRepository.save(this.region);
            this.city = City.builder().name("NOvA FAtimA").state(State.PR).build();
            this.region.addCity(this.city);
            this.cityRepository.save(this.city);
            this.farmer = Farmer.builder().name("PauLO MARIAnO").build();
            this.farmerRepository.save(this.farmer);
            this.supervisor.add(Supervisor.builder().name("Franciscano Souza").email("FranciscanoSouza@EMATER.COM").region(this.region).build());
            this.supervisor.add(Supervisor.builder().name("JOAO BEZERRA").email("JOAOBEZERRA@EMATER.COM").region(this.region).build());
            this.field = Field.builder().name("TEST casE").location("1").city(this.city).farmer(this.farmer).build();
        }

        @Test //testa novo supervisor
        public void test01_addSupervisor(){
            assertThat(this.field.addSupervisor(this.supervisor.get(0))).isTrue();
            assertThat(this.field.addSupervisor(this.supervisor.get(1))).isTrue();
        }

        @Test // adiciona um supervisor nulo, cidade nula e outros??
        public void test02_addSupervisorIsNull(){
            assertThat(this.field.addSupervisor(null)).isFalse();
        }

        @Test
        public  void getCityName(){
            assertThat(this.field.getCityName()).isEqualTo("Nova Fatima");
        }

        @Test
        public  void getCityId(){
            assertThat(this.field.getCityId()).isNotNull();
        }

        @Test
        public  void setName(){
            assertThat(this.field.getName()).isEqualTo("Test Case");
        }

        //falta testar limite 50 e 5 caracteres - Deve estar entre os codigos de Repository?

        @Test (expected = ConstraintViolationException.class)
        public  void setNameExceptionNameLessThan5() {
            this.field.setName("test");
            this.fieldRepository.save(this.field);
        }

        @Test (expected = ConstraintViolationException.class)
        public  void setNameExceptionNameBigThan50() {
            this.field.setName("123456789-123456789-123456789-123456789-123456789-1");
            this.fieldRepository.save(field);
        }


        @Test
        public  void getStateName(){
            assertThat(this.field.getStateName()).isEqualTo("Paraná");
        }

        @Test
        public  void getFarmerName(){
            assertThat(this.field.getFarmerName()).isEqualTo("Paulo Mariano");
        }

        @Test
        public  void getFarmerId(){
            assertThat(this.field.getFarmerId()).isNotNull();
        }

        @Test
        public  void getSupervisorNames(){
            this.field.addSupervisor(this.supervisor.get(0));
            this.field.addSupervisor(this.supervisor.get(1));
            assertThat(this.field.getSupervisorNames()).contains("Franciscano Souza","Joao Bezerra");
        }

        @Test
        public void removeSupervisor(){
            this.field.addSupervisor(this.supervisor.get(0));
            this.field.addSupervisor(this.supervisor.get(1));
            assertThat(this.field.removeSupervisor(supervisor.get(0))).isTrue();
        }

        @Test
        public void removeSupervisorNotExistContainer(){
            assertThat(this.field.removeSupervisor(supervisor.get(1))).isFalse();
        }

        @Test
        public void removeSupervisorNotExist(){
            this.field.addSupervisor(this.supervisor.get(0));
            assertThat(this.field.removeSupervisor(supervisor.get(0))).isTrue();
            assertThat(this.field.removeSupervisor(supervisor.get(0))).isFalse();
        }
}
