package br.edu.utfpr.cp.emater.midmipsystem.entity.base;


import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.State;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SupervisorTest {

    private MacroRegion macroRegion;
    private Region region;

    @Before
    public void setUp() {
        this.macroRegion = MacroRegion.builder().id((long)1).name("MACRO-CENTRO-OESTE").build();
        City citytest1 = City.builder().name("NOvA FAtimA").state(State.PR).build();
        City citytest2 = City.builder().name("Apucarana").state(State.PR).build();
        Set<City> cities = new HashSet<>();
        cities.add(citytest1);
        cities.add(citytest2);
        this.region = Region.builder().name("Paranavai").id((long)100).macroRegion(this.macroRegion).build();
        this.region.setCities(cities);
    }

    @Test
    public  void getAndSetIdSupervisorTest() {
        Supervisor supervisorTest = Supervisor.builder().id((long)10).name("João").build();
        assertThat(supervisorTest.getId()).isEqualTo((long)10);
    }

    @Test
    public  void getAndSetIdAsStringSupervisorTest() {
        Supervisor supervisorTest = Supervisor.builder().id((long)10).name("João").build();
        assertThat(supervisorTest.getIdAsString()).isEqualTo("10");
    }

    @Test
    public  void getMacroRegionIdSupervisorTest() {
        Supervisor supervisorTest = Supervisor.builder().id((long)10).name("João").region(this.region).build();
        assertThat(supervisorTest.getMacroRegionId()).isEqualTo((long)1);
    }

    @Test
    public  void getMacroRegionNameSupervisorTest() {
        Supervisor supervisorTest = Supervisor.builder().id((long)10).name("João").region(this.region).build();
        assertThat(supervisorTest.getMacroRegionName()).isEqualTo("Macro-centro-oeste");
    }

    //é dificil de fazer uma comparação exata pois a ordem das cidades pode mudar, alterando o resultado do teste.
    @Test
    public  void getCitiesInRegionNamesSupervisorTest() {
        Supervisor supervisorTest = Supervisor.builder().id((long)10).name("João").region(this.region).build();
        assertThat(supervisorTest.getCitiesInRegionNames())
                .contains("Apucarana (PR)").contains("Nova Fatima (PR)");
    }

    @Test
    public  void getRegionIdSupervisorTest() {
        Supervisor supervisorTest = Supervisor.builder().id((long)10).name("João").region(this.region).build();
        assertThat(supervisorTest.getRegionId()).isEqualTo((long)100);
    }

    @Test
    public  void getRegionNameSupervisorTest() {
        Supervisor supervisorTest = Supervisor.builder().id((long)10).name("João").region(this.region).build();
        assertThat(supervisorTest.getRegionName()).isEqualTo("Paranavai");
    }

    @Test
    public  void getAndSetNameSupervisorTest() {
        Supervisor supervisorTest = new Supervisor();
        supervisorTest.setName("MARCOS");
        System.out.println(supervisorTest.getName());
        assertThat(supervisorTest.getName()).isEqualTo("Marcos");
    }

    @Test
    public  void getAndSetRegionSupervisorTest() {
        Supervisor supervisorTest = new Supervisor();
        supervisorTest.setRegion(this.region);
        assertThat(supervisorTest.getRegion()).isEqualTo(this.region);
    }


    @Test
    public  void getAndSetEmailSupervisorTest() {
        Supervisor supervisorTest = new Supervisor();
        supervisorTest.setEmail(null);
        supervisorTest.setEmail("marcos@email.com");
        assertThat(supervisorTest.getEmail()).isEqualTo("marcos@email.com");
    }

    @Ignore  //Falta testar as anotação de integração com o banco;
    @Test (expected = ConstraintViolationException.class)
    public  void getAndSetEmailNullSupervisorTest() {
        Supervisor supervisorTest = new Supervisor();
        supervisorTest.setEmail(null);;
    }


}
