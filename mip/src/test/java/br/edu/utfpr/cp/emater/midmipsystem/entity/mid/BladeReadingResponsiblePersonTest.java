package br.edu.utfpr.cp.emater.midmipsystem.entity.mid;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.State;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BladeReadingResponsiblePersonTest {

    private BladeReadingResponsiblePerson bladeReadingResponsiblePerson;

    @Before
    public void setUp() {
        City city;
        city = City.builder().state(State.PR).name("Cornelio Proc").build();
        city.setId((long) 55);
        this.bladeReadingResponsiblePerson = BladeReadingResponsiblePerson.builder()
                .id((long) 22).name("Test Case")
                .entity(BladeReadingResponsibleEntity.builder().id((long) 11).name("Test Case 2").city(city).build()).build();
    }

    @Test
    public void getEntityNameTest() {
        assertThat(this.bladeReadingResponsiblePerson.getEntityName()).isEqualTo("Test Case 2");
    }

    @Test
    public void getEntityIdTest() {
        assertThat(this.bladeReadingResponsiblePerson.getEntityId()).isEqualTo((long)11);
    }

    @Test
    public void getEntityCityNameTest() {
        assertThat(this.bladeReadingResponsiblePerson.getEntityCityName()).isEqualTo("Cornelio Proc");
    }

    @Test
    public void getEntityCityIdTest() {
        assertThat(this.bladeReadingResponsiblePerson.getEntityCityId()).isEqualTo((long)55);
    }

    @Test
    public void getAndSetEntityTest() {
        BladeReadingResponsibleEntity bladeReadingResponsibleEntity =
                BladeReadingResponsibleEntity.builder().id((long) 34).name("TEsT CASE TwO").build();
        this.bladeReadingResponsiblePerson.setEntity(bladeReadingResponsibleEntity);
        assertThat(this.bladeReadingResponsiblePerson.getEntity()).isEqualTo(bladeReadingResponsibleEntity);
        assertThat(this.bladeReadingResponsiblePerson.getEntityName()).isEqualTo("Test Case Two");
    }

    @Test
    public void builderTest() {
        City city;
        city = City.builder().state(State.PR).name("Nova Fatima").build();
        city.setId((long) 12);
        this.bladeReadingResponsiblePerson = BladeReadingResponsiblePerson.builder()
                .id((long) 62).name("Marcos AN")
                .entity(BladeReadingResponsibleEntity.builder().id((long) 14).name("TEsT CASE TwO").city(city).build()).build();
        assertThat(this.bladeReadingResponsiblePerson.getEntityId()).isEqualTo((long)14);
        assertThat(this.bladeReadingResponsiblePerson.getId()).isEqualTo((long)62);
        assertThat(this.bladeReadingResponsiblePerson.getName()).isEqualTo("Marcos An");
        assertThat(this.bladeReadingResponsiblePerson.getEntityCityName()).isEqualTo("Nova Fatima");
        assertThat(this.bladeReadingResponsiblePerson.getEntityCityId()).isEqualTo((long)12);
        assertThat(this.bladeReadingResponsiblePerson.getEntityName()).isEqualTo("Test Case Two");

    }

    @Test
    public void getAndSetIdTest() {
        this.bladeReadingResponsiblePerson.setId((long) 99);
        assertThat(this.bladeReadingResponsiblePerson.getId()).isEqualTo((long)99);
    }

    @Test
    public void getAndSetNameTest() {
        this.bladeReadingResponsiblePerson.setName("TEST CASE 2");
        assertThat(this.bladeReadingResponsiblePerson.getName()).isEqualTo("Test Case 2");
    }

}