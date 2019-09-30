package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;

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
public class PestTest {

    private Pest pest;

    @Test
    public void getAndSetIdPestTest(){
        Pest pest = new Pest();
        pest.setId((long) 3);
        assertThat(pest.getId()).isEqualTo((long)3);
    }

    @Test
    public void getAndSetUsualNamePestTest(){
        Pest pest = new Pest();
        pest.setUsualName("LARGATA AmArela");
        assertThat(pest.getUsualName()).isEqualTo("Largata Amarela");
    }

    @Test
    public void getAndSetScientificNamePestTest(){
        Pest pest = new Pest();
        pest.setScientificName("Hora Pronobilis FO");
        assertThat(pest.getScientificName()).isEqualTo("Hora Pronobilis FO");
    }

    @Test
    public void getAndSetPestSizePestTest(){
        Pest pest = new Pest();
        pest.setPestSize(PestSize.ADULT);
        assertThat(pest.getPestSize()).isEqualTo(PestSize.ADULT);
    }

    @Test
    public void getAndSetPestSizeNamePestTest(){
        Pest pest = new Pest();
        pest.setPestSize(PestSize.ADULT);
        assertThat(pest.getPestSizeName()).isEqualTo("Adultos");
    }

    @Test
    public void constructorPestTest(){
        Pest pest = Pest.builder().id((long)5).pestSize(PestSize.THIRD_TO_FIFTH_INSTAR)
                .scientificName("MancicoPloris").usualName("Pulgão").build();
        assertThat(pest.getPestSizeName()).isEqualTo("3. ao 5. Instar");
        assertThat(pest.getScientificName()).isEqualTo("MancicoPloris");
        assertThat(pest.getUsualName()).isEqualTo("Pulgão");
        assertThat(pest.getId()).isEqualTo((long)5);
    }

    @Test
    public void equalsFalsePestTest() {
        Pest pest = Pest.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        this.pest = Pest.builder().id((long)55).usualName("Vespa").build();
        assertThat(this.pest.equals(pest)).isFalse();
    }

    @Test
    public void equalsTruePestTest() {
        Pest pest = Pest.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        this.pest = pest;
        assertThat(this.pest.equals(pest)).isTrue();
    }

    @Test
    public void hashCodeTruePestTest() {
        Pest pest = Pest.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        int value = pest.hashCode();
        assertThat(pest.hashCode()).isEqualTo(value);
    }

    @Test
    public void hashCodeFalsePestTest() {
        Pest pest = Pest.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        int value = pest.hashCode();
        value++;
        assertThat(pest.hashCode()==value).isFalse();
    }




}
