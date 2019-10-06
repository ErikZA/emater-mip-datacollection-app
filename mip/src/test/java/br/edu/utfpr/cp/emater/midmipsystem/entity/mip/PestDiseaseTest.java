package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;


import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PestDiseaseTest {

    private PestDisease pestDisease;

    @Test
    public void getAndSetIdPestDiseaseTest(){
        PestDisease pestDisease = new PestDisease();
        pestDisease.setId((long)2);
        assertThat(pestDisease.getId()).isEqualTo((long)2);
    }

    //Não normaliza o nome.
    @Test
    public void getAndSetUsualNamePestDiseaseTest(){
        PestDisease pestDisease = new PestDisease();
        pestDisease.setUsualName("Bicho da SEDA");
        assertThat(pestDisease.getUsualName()).isEqualTo("Bicho da SEDA");
    }

    //Não normaliza o nome.
    @Test
    public void constructorTestPestDiseaseTest(){
        this.pestDisease = PestDisease.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        assertThat(pestDisease.getId()).isEqualTo((long)3);
        assertThat(pestDisease.getUsualName()).isEqualTo("BORBOLETA AMARELA");
    }

    @Test
    public void equalsFalsePestDiseaseTest() {
        PestDisease pestDisease = PestDisease.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        this.pestDisease = PestDisease.builder().id((long)55).usualName("Vespa").build();
        assertThat(this.pestDisease.equals(pestDisease)).isFalse();
    }

    @Test
    public void equalsTruePestDiseaseTest() {
        PestDisease pestDisease = PestDisease.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        this.pestDisease = pestDisease;
        assertThat(this.pestDisease.equals(pestDisease)).isTrue();
    }

    @Test
    public void hashCodeTruePestDiseaseTest() {
        PestDisease pestDisease = PestDisease.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        int value = pestDisease.hashCode();
        assertThat(pestDisease.hashCode()).isEqualTo(value);
    }

    @Test
    public void hashCodeFalsePestDiseaseTest() {
        PestDisease pestDisease = PestDisease.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        int value = pestDisease.hashCode();
        value++;
        assertThat(pestDisease.hashCode()==value).isFalse();
    }

}
