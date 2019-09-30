package br.edu.utfpr.cp.emater.midmipsystem.entity.base;


import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CityTest {


    @Test
    public  void getAndSetIdCityTest() {
        City cityTest = new City();
        cityTest.setId((long)10);
        assertThat(cityTest.getId()).isEqualTo((long)10);
    }


    @Test
    public  void getAndSetNameCityTest() {
        City cityTest = new City();
        cityTest.setName("NOVA FATIMA");
        assertThat(cityTest.getName()).isEqualTo("Nova Fatima");
    }


    @Test
    public  void getAndSetStateCityTest() {
        City cityTest = new City();
        cityTest.setState(State.PR);
        assertThat(cityTest.getState()).isEqualTo(State.PR);
    }

    @Test
    public  void toStringCityTest() {
        City cityTest = City.builder().name("Nova Fatima").state(State.PR).build();
        assertThat(cityTest.toString()).isEqualTo("Nova Fatima (PR)");
    }

    @Test
    public  void getIdAsStringCityTest() {
        City cityTest = City.builder().name("Nova Fatima").state(State.PR).build();
        cityTest.setId((long)10);
        assertThat(cityTest.getIdAsString()).isEqualTo("10");
    }



}
