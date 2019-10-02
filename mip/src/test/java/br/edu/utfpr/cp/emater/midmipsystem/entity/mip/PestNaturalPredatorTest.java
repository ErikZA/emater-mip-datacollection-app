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
public class PestNaturalPredatorTest {

    private PestNaturalPredator pestNaturalPredator;

    @Test
    public void getAndSetIdPestNaturalPredatorTest(){
        PestNaturalPredator pestNaturalPredatorTest = new PestNaturalPredator();
        pestNaturalPredatorTest.setId((long)2);
        assertThat(pestNaturalPredatorTest.getId()).isEqualTo((long)2);
    }

    @Test
    public void getAndSetUsualNamePestNaturalPredatorTest(){
        PestNaturalPredator pestNaturalPredatorTest = new PestNaturalPredator();
        pestNaturalPredatorTest.setUsualName("Bicho da SEDA");
        assertThat(pestNaturalPredatorTest.getUsualName()).isEqualTo("Bicho Da Seda");
    }

    @Test
    public void constructorTestPestNaturalPredatorTest(){
       this.pestNaturalPredator = PestNaturalPredator.builder().id((long)3)
               .usualName("BORBOLETA AMARELA").build();
        assertThat(pestNaturalPredator.getId()).isEqualTo((long)3);
        assertThat(pestNaturalPredator.getUsualName()).isEqualTo("Borboleta Amarela");
    }

    @Test
    public void equalsFalsePestNaturalPredatorTest() {
        PestNaturalPredator pestNaturalPredator = PestNaturalPredator.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        this.pestNaturalPredator = PestNaturalPredator.builder().id((long)55).usualName("Vespa").build();
                assertThat(this.pestNaturalPredator.equals(pestNaturalPredator)).isFalse();
    }

    @Test
    public void equalsTruePestNaturalPredatorTest() {
        PestNaturalPredator pestNaturalPredator = PestNaturalPredator.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        this.pestNaturalPredator = pestNaturalPredator;
        assertThat(this.pestNaturalPredator.equals(pestNaturalPredator)).isTrue();
    }

    @Test
    public void hashCodeTrueNaturalPredatorTest() {
        PestNaturalPredator pestNaturalPredator = PestNaturalPredator.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        int value = pestNaturalPredator.hashCode();
        assertThat(pestNaturalPredator.hashCode()).isEqualTo(value);
    }

    @Test
    public void hashCodeFalseNaturalPredatorTest() {
        PestNaturalPredator pestNaturalPredator = PestNaturalPredator.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        int value = pestNaturalPredator.hashCode();
        value++;
        assertThat(pestNaturalPredator.hashCode()==value).isFalse();
    }

}
