package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MIPSampleNaturalPredatorOccurrenceTest {

    private MIPSampleNaturalPredatorOccurrence mipSampleNaturalPredatorOccurrence;
    private PestNaturalPredator pestNaturalPredator;

    @Test
    public void getPestNaturalPredatorUsualNameTest() {
        this.pestNaturalPredator = PestNaturalPredator.builder().usualName("Vespa amarela").build();
        this.mipSampleNaturalPredatorOccurrence = MIPSampleNaturalPredatorOccurrence.builder().pestNaturalPredator(this.pestNaturalPredator).build();
        assertThat(this.mipSampleNaturalPredatorOccurrence.getPestNaturalPredatorUsualName()).isEqualTo("Vespa Amarela");
    }

    @Test
    public void builderTest() {
        this.mipSampleNaturalPredatorOccurrence = new MIPSampleNaturalPredatorOccurrence();
        this.mipSampleNaturalPredatorOccurrence = MIPSampleNaturalPredatorOccurrence.builder().build();
        this.pestNaturalPredator = PestNaturalPredator.builder().id((long)1).usualName("Vespa amarela").build();
        this.mipSampleNaturalPredatorOccurrence = MIPSampleNaturalPredatorOccurrence.builder().value(100.34).pestNaturalPredator(this.pestNaturalPredator).build();
    }

    @Test
    public void setAndGetValueTest() {
        double var = 100.34;
        this.pestNaturalPredator = PestNaturalPredator.builder().id((long)1).usualName("Vespa amarela").build();
        this.mipSampleNaturalPredatorOccurrence = MIPSampleNaturalPredatorOccurrence.builder().value(var).pestNaturalPredator(this.pestNaturalPredator).build();
        assertThat(this.mipSampleNaturalPredatorOccurrence.getValue()).isEqualTo(var);
        var++;
        this.mipSampleNaturalPredatorOccurrence.setValue(var);
        assertThat(this.mipSampleNaturalPredatorOccurrence.getValue()).isEqualTo(var);
    }

    @Test
    public void setAndGetPestNaturalPredatorTest() {
        this.pestNaturalPredator = PestNaturalPredator.builder().id((long)1).usualName("Vespa amarela").build();
        this.mipSampleNaturalPredatorOccurrence = MIPSampleNaturalPredatorOccurrence.builder().value(100.34).pestNaturalPredator(this.pestNaturalPredator).build();
        assertThat(this.mipSampleNaturalPredatorOccurrence.getPestNaturalPredator()).isEqualTo(this.pestNaturalPredator);
        this.pestNaturalPredator = PestNaturalPredator.builder().id((long)2).usualName("Vespa verde").build();
        this.mipSampleNaturalPredatorOccurrence.setPestNaturalPredator(this.pestNaturalPredator);
        assertThat(this.mipSampleNaturalPredatorOccurrence.getPestNaturalPredator()).isEqualTo(this.pestNaturalPredator);
    }

    @Test
    public void equalsFalseMIPSampleNaturalPredatorOccurrenceTest() {
        MIPSampleNaturalPredatorOccurrence mipSampleNaturalPredatorOccurrence = MIPSampleNaturalPredatorOccurrence.builder().value(22).build();
        this.mipSampleNaturalPredatorOccurrence = MIPSampleNaturalPredatorOccurrence.builder().pestNaturalPredator(this.pestNaturalPredator).value(202).build();
        assertThat(this.mipSampleNaturalPredatorOccurrence.equals(mipSampleNaturalPredatorOccurrence)).isFalse();
    }

    @Test
    public void equalsTrueMIPSampleNaturalPredatorOccurrenceTest() {
        MIPSampleNaturalPredatorOccurrence mipSampleNaturalPredatorOccurrence = MIPSampleNaturalPredatorOccurrence.builder().value(22).build();
        this.mipSampleNaturalPredatorOccurrence = mipSampleNaturalPredatorOccurrence;
        assertThat(this.mipSampleNaturalPredatorOccurrence.equals(mipSampleNaturalPredatorOccurrence)).isTrue();
    }

    @Test
    public void hashCodeTrueMIPSampleNaturalPredatorOccurrenceTest() {
        MIPSampleNaturalPredatorOccurrence mipSampleNaturalPredatorOccurrence = MIPSampleNaturalPredatorOccurrence.builder().value(33).build();
        int value = mipSampleNaturalPredatorOccurrence.hashCode();
        assertThat(mipSampleNaturalPredatorOccurrence.hashCode()).isEqualTo(value);
    }

    @Test
    public void hashCodeFalseMIPSampleNaturalPredatorOccurrenceTest() {
        MIPSampleNaturalPredatorOccurrence mipSampleNaturalPredatorOccurrence = MIPSampleNaturalPredatorOccurrence.builder().value(11).build();
        int value = mipSampleNaturalPredatorOccurrence.hashCode();
        value++;
        assertThat(mipSampleNaturalPredatorOccurrence.hashCode()==value).isFalse();
    }

}