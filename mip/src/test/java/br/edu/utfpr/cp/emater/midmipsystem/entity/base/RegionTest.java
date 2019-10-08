package br.edu.utfpr.cp.emater.midmipsystem.entity.base;


import br.edu.utfpr.cp.emater.midmipsystem.repository.base.RegionRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Repeat;

import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegionTest {

    private MacroRegion macroRegion;
    private Region region;

//Avaliar mover esse tipo de teste para repository

    private RegionRepository regionRepository = mock(RegionRepository.class);

    @Before
    public void setUp() {
        this.macroRegion = MacroRegion.builder().id((long)1).name("MACRO-CENTRO-OESTE").build();
        this.region = Region.builder().id((long) 99).name("Paranavai").build();
    }



    @Test
    public  void getAndSetIdRegionTest() {
        Region regionTest = new Region();
        regionTest.setId((long)10);
        assertThat(regionTest.getId()).isEqualTo((long)10);
    }


    @Test
    public  void getAndSetNameRegionTest() {
        Region regionTest = new Region();
        regionTest.setName("PROCOPENSE");
        assertThat(regionTest.getName()).isEqualTo("Procopense");
    }

    @Test (expected = ConstraintViolationException.class)
    public  void setNameExceptionNameLessThan5RegionTest() {
        when(this.regionRepository.save(this.region)).thenThrow(ConstraintViolationException.class);
        this.region.setName("test");
        this.regionRepository.save(this.region);
    }


    @Test (expected = ConstraintViolationException.class)
    public  void setNameExceptionNameBigThan50RegionTest() {
        when(this.regionRepository.save(this.region)).thenThrow(ConstraintViolationException.class);
        this.region.setName("123456789-123456789-123456789-123456789-123456789-1");
        this.regionRepository.save(this.region);
    }

    @Test
    public void getAndSetMacroRegionTest(){
        MacroRegion macroRegionTest = new MacroRegion();
        this.region.setMacroRegion(macroRegionTest);
        assertThat(this.region.getMacroRegion()).isEqualTo(macroRegionTest);
    }


    @Test
    public void getMacroRegionNameTest(){
        MacroRegion macroRegionTest = MacroRegion.builder().name("NORDESTE").build();
        this.region.setMacroRegion(macroRegionTest);
        assertThat(this.region.getMacroRegionName()).isEqualTo("Nordeste");
    }

    @Test
    public void getMacroRegionIdTest(){
        MacroRegion macroRegionTest = MacroRegion.builder().id((long)10).name("Desconhecido").build();
        this.region.setMacroRegion(macroRegionTest);
        assertThat(this.region.getMacroRegionId()).isEqualTo((long)10);
    }

    @Test
    public void getAndSetCitiesTest(){
        City citytest1 = City.builder().name("NOvA FAtimA").state(State.PR).build();
        City citytest2 = City.builder().name("Apucarana").state(State.PR).build();
        Set<City> cities = new HashSet<>();
        cities.add(citytest1);
        cities.add(citytest2);
        this.region.setCities(cities);
        assertThat(this.region.getCities())
                .containsExactlyInAnyOrder(citytest1,citytest2)
                .doesNotContainNull().isNotEmpty();
    }

    @Test
    public void regiaoTestEqualsFalse() {
        Region regionTest = Region.builder().name("Medianeira").build();
        assertThat(this.region.equals(regionTest)).isFalse();
    }

    @Test
    public void regionTestEqualsTrue() {
        Region regionTest = this.region;
        assertThat(this.region.equals(regionTest)).isTrue();
    }

    @Test
    public void macroRegionTestHashCodeTrue() {
        int value = this.region.hashCode();
        assertThat(this.region.hashCode()).isEqualTo(value);
    }


    @Test
    public void macroRegionTestHashCodeFalse() {
        int value = this.region.hashCode();
        value++;
        assertThat(this.macroRegion.hashCode()==value).isFalse();
    }

    @Test
    public void addCitySuccessTestRegion(){
        City citytest1 = City.builder().name("NOvA FAtimA").state(State.PR).build();
        assertThat(this.region.addCity(citytest1)).isTrue();
    }

    @Test
    public void removeCitySuccessTestRegion(){
        City citytest1 = City.builder().name("NOvA FAtimA").state(State.PR).build();
        City citytest2 = City.builder().name("Apucarana").state(State.PR).build();
        Set<City> cities = new HashSet<>();
        cities.add(citytest1);
        cities.add(citytest2);
        this.region.setCities(cities);
        assertThat(this.region.removeCity(citytest1)).isTrue();
    }


    @Test
    public void removeCityFalseTestRegion(){
        City citytest1 = City.builder().name("NOvA FAtimA").state(State.PR).build();
        City citytest2 = City.builder().name("Apucarana").state(State.PR).build();
        City citytest3 = City.builder().name("Toledo").state(State.PR).build();
        Set<City> cities = new HashSet<>();
        cities.add(citytest1);
        cities.add(citytest2);
        this.region.setCities(cities);
        assertThat(this.region.removeCity(citytest3)).isFalse();
    }

    @Test
    public void removeCityContainerFalseTestRegion(){
        City citytest3 = City.builder().name("Toledo").state(State.PR).build();
        assertThat(this.region.removeCity(citytest3)).isFalse();
    }

    @Test
    public void getCitiesNamesTestRegion(){
        City citytest1 = City.builder().name("NOvA FAtimA").state(State.PR).build();
        City citytest2 = City.builder().name("Apucarana").state(State.PR).build();
        Set<City> cities = new HashSet<>();
        cities.add(citytest1);
        cities.add(citytest2);
        this.region.setCities(cities);
        assertThat(this.region.getCityNames()).containsExactlyInAnyOrder("Nova Fatima", "Apucarana");
    }


    @Test
    @Repeat(3)
    public void toStringTestRegion(){
        City citytest1 = City.builder().name("NOvA FAtimA").state(State.PR).build();
        City citytest2 = City.builder().name("Apucarana").state(State.PR).build();
        MacroRegion macroRegionTest = MacroRegion.builder().id((long)10).name("Nordeste").build();
        this.region.setMacroRegion(macroRegionTest);
        Set<City> cities = new HashSet<>();
        cities.add(citytest1);
        cities.add(citytest2);
        this.region.setCities(cities);
        assertThat(this.region.toString())
                .contains("[Region: name = Paranavai, MacroRegion = Nordeste, Cities = [Nova Fatima, Apucarana]]");
    }



}
