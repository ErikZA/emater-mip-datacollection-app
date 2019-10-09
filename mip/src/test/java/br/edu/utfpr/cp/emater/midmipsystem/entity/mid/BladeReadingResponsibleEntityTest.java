package br.edu.utfpr.cp.emater.midmipsystem.entity.mid;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.State;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BladeReadingResponsibleEntityTest {

    private BladeReadingResponsibleEntity bladeReadingResponsibleEntity;
    private City city;
    @Before
    public void setUp(){
        this.city = City.builder().state(State.PR).name("Nova Fatima").build();
        this.city.setId((long) 44);
        this.bladeReadingResponsibleEntity = BladeReadingResponsibleEntity.builder().
                id((long) 22).name("Teste CASE").city(city).build();

    }
    @Test
    public void getCityNameTest() {
        assertThat(this.bladeReadingResponsibleEntity.getCityName()).isEqualTo("Nova Fatima");
    }

    @Test
    public void getCityIdTest() {
        assertThat(this.bladeReadingResponsibleEntity.getCityId()).isEqualTo((long)44);
    }

    @Test
    public void getIdAsStringTest() {
        assertThat(this.bladeReadingResponsibleEntity.getIdAsString()).isEqualTo("22");
    }

    @Test
    public void getAndSetCityTest() {
        City city = City.builder().state(State.PR).name("Pinhal").build();
        this.bladeReadingResponsibleEntity.setCity(city);
        assertThat(this.bladeReadingResponsibleEntity.getCity()).isEqualTo(city);
    }

    @Test
    public void builderTest() {
        City city;
        BladeReadingResponsibleEntity bladeReadingResponsibleEntity;
        city = City.builder().state(State.PR).name("Cornelio Proc").build();
        city.setId((long) 44);
        bladeReadingResponsibleEntity = BladeReadingResponsibleEntity.builder().
                id((long) 22).name("Teste CASE").city(city).build();
        assertThat(bladeReadingResponsibleEntity.getCity()).isEqualTo(city);
        assertThat(bladeReadingResponsibleEntity.getId()).isEqualTo((long)22);
        assertThat(bladeReadingResponsibleEntity.getName()).isEqualTo("Teste Case");
    }

    @Test
    public void getAndSetIdTest() {
        this.bladeReadingResponsibleEntity.setId((long) 99);
        assertThat(this.bladeReadingResponsibleEntity.getId()).isEqualTo((long)99);
    }

    @Test
    public void getAndSetNameTest() {
        this.bladeReadingResponsibleEntity.setName("TEST CASE 2");
        assertThat(this.bladeReadingResponsibleEntity.getName()).isEqualTo("Test Case 2");
    }
}