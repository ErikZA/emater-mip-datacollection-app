package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MIPSamplePestOccurrenceTest {


    private MIPSamplePestOccurrence mipSamplePestOccurrence;
    private Pest pest;

    @Test
    public void getPestUsualNameTest() {
        this.pest = Pest.builder().usualName("Vespa amarela").pestSize(PestSize.ADULT).build();
        this.mipSamplePestOccurrence = MIPSamplePestOccurrence.builder().pest(this.pest).build();
        assertThat(this.mipSamplePestOccurrence.getPestUsualName()).isEqualTo("Vespa Amarela");
    }

    @Test // não normaliza o nome
    public void getPestScientificNameTest() {
        this.pest = Pest.builder().usualName("Vespa amarela").pestSize(PestSize.ADULT).scientificName("Vespa amarela").build();
        this.mipSamplePestOccurrence = MIPSamplePestOccurrence.builder().pest(this.pest).build();
        assertThat(this.mipSamplePestOccurrence.getPestScientificName()).isEqualTo("Vespa amarela");
    }

    @Test
    public void getPestSizeNameTest() {
        this.pest = Pest.builder().usualName("Vespa amarela").scientificName("Vespa amarela").pestSize(PestSize.ADULT).build();
        this.mipSamplePestOccurrence = MIPSamplePestOccurrence.builder().pest(this.pest).build();
        assertThat(this.mipSamplePestOccurrence.getPestSizeName()).isEqualTo("Adultos");
    }

    @Test
    public void builderTest() {
        this.mipSamplePestOccurrence = new MIPSamplePestOccurrence();
        this.mipSamplePestOccurrence = MIPSamplePestOccurrence.builder().build();
        this.pest = Pest.builder().id((long)1).usualName("Vespa amarela").build();
        this.mipSamplePestOccurrence = MIPSamplePestOccurrence.builder().value(100.34).pest(this.pest).build();
    }

    @Test
    public void setAndGetValueTest() {
        double var = 100.34;
        this.pest = Pest.builder().id((long)1).usualName("Vespa amarela").build();
        this.mipSamplePestOccurrence = MIPSamplePestOccurrence.builder().value(var).pest(this.pest).build();
        assertThat(this.mipSamplePestOccurrence.getValue()).isEqualTo(var);
        var++;
        this.mipSamplePestOccurrence.setValue(var);
        assertThat(this.mipSamplePestOccurrence.getValue()).isEqualTo(var);
    }

    @Test
    public void setAndGetPestTest() {
        this.pest = Pest.builder().id((long)1).usualName("Vespa amarela").build();
        this.mipSamplePestOccurrence = MIPSamplePestOccurrence.builder().value(100.34).pest(this.pest).build();
        assertThat(this.mipSamplePestOccurrence.getPest()).isEqualTo(this.pest);
        this.pest = Pest.builder().id((long)2).usualName("Vespa verde").build();
        this.mipSamplePestOccurrence.setPest(this.pest);
        assertThat(this.mipSamplePestOccurrence.getPest()).isEqualTo(this.pest);
    }

    @Test
    public void equalsFalseMIPSamplePestOccurrenceTest() {
        MIPSamplePestOccurrence mipSamplePestOccurrence = MIPSamplePestOccurrence.builder().value(22).build();
        this.mipSamplePestOccurrence = MIPSamplePestOccurrence.builder().pest(this.pest).value(202).build();
        assertThat(this.mipSamplePestOccurrence.equals(mipSamplePestOccurrence)).isFalse();
    }

    @Test
    public void equalsTrueMIPSampleNaturalPredatorOccurrenceTest() {
        MIPSamplePestOccurrence mipSamplePestOccurrence = MIPSamplePestOccurrence.builder().value(22).build();
        this.mipSamplePestOccurrence = mipSamplePestOccurrence;
        assertThat(this.mipSamplePestOccurrence.equals(mipSamplePestOccurrence)).isTrue();
    }

    @Test
    public void hashCodeTrueMIPSampleNaturalPredatorOccurrenceTest() {
        MIPSamplePestOccurrence mipSamplePestOccurrence = MIPSamplePestOccurrence.builder().value(33).build();
        int value = mipSamplePestOccurrence.hashCode();
        assertThat(mipSamplePestOccurrence.hashCode()).isEqualTo(value);
    }

    @Test
    public void hashCodeFalseMIPSampleNaturalPredatorOccurrenceTest() {
        MIPSamplePestOccurrence mipSamplePestOccurrence = MIPSamplePestOccurrence.builder().value(11).build();
        int value = mipSamplePestOccurrence.hashCode();
        value++;
        assertThat(mipSamplePestOccurrence.hashCode()==value).isFalse();
    }


}