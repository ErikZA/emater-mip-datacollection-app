package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MIPSamplePestDiseaseOccurrenceTest {

    private MIPSamplePestDiseaseOccurrence mipSamplePestDiseaseOccurrence;
    private PestDisease pestDisease;

    @Test //pestDisease não normaliza o usuas name
    public void mipSamplePestDiseaseOccurrenceGetPestDiseaseUsualNameTest() {
        this.pestDisease = PestDisease.builder().id((long)1).usualName("Vespa amarela").build();
        this.mipSamplePestDiseaseOccurrence = MIPSamplePestDiseaseOccurrence.builder().pestDisease(this.pestDisease).build();
        assertThat(this.mipSamplePestDiseaseOccurrence.getPestDiseaseUsualName()).isEqualTo("Vespa amarela");
    }

    @Test
    public void mipSamplePestDiseaseOccurrenceBuilderTest() {
        this.mipSamplePestDiseaseOccurrence = new MIPSamplePestDiseaseOccurrence();
        this.mipSamplePestDiseaseOccurrence = MIPSamplePestDiseaseOccurrence.builder().build();
        this.pestDisease = PestDisease.builder().id((long)1).usualName("Vespa amarela").build();
        this.mipSamplePestDiseaseOccurrence = MIPSamplePestDiseaseOccurrence.builder().value(100.34).pestDisease(this.pestDisease).build();
    }

    @Test
    public void mipSamplePestDiseaseOccurrenceSetAndGetValueTest() {
        double var = 100.34;
        this.pestDisease = PestDisease.builder().id((long)1).usualName("Vespa amarela").build();
        this.mipSamplePestDiseaseOccurrence = MIPSamplePestDiseaseOccurrence.builder().value(var).pestDisease(this.pestDisease).build();
        assertThat(this.mipSamplePestDiseaseOccurrence.getValue()).isEqualTo(var);
        var++;
        this.mipSamplePestDiseaseOccurrence.setValue(var);
        assertThat(this.mipSamplePestDiseaseOccurrence.getValue()).isEqualTo(var);
    }

    @Test
    public void mipSamplePestDiseaseOccurrenceSetAndGetPestDiseaseTest() {
        this.pestDisease = PestDisease.builder().id((long)1).usualName("Vespa amarela").build();
        this.mipSamplePestDiseaseOccurrence = MIPSamplePestDiseaseOccurrence.builder().value(100.34).pestDisease(this.pestDisease).build();
        assertThat(this.mipSamplePestDiseaseOccurrence.getPestDisease()).isEqualTo(this.pestDisease);
        this.pestDisease = PestDisease.builder().id((long)2).usualName("Vespa verde").build();
        this.mipSamplePestDiseaseOccurrence.setPestDisease(this.pestDisease);
        assertThat(this.mipSamplePestDiseaseOccurrence.getPestDisease()).isEqualTo(this.pestDisease);
    }

    @Test
    public void equalsFalseMIPSamplePestDiseaseOccurrenceTest() {
        MIPSamplePestDiseaseOccurrence mipSamplePestDiseaseOccurrence = MIPSamplePestDiseaseOccurrence.builder().pestDisease(this.pestDisease).value(22).build();
        this.mipSamplePestDiseaseOccurrence = MIPSamplePestDiseaseOccurrence.builder().value(222).build();
        assertThat(this.mipSamplePestDiseaseOccurrence.equals(mipSamplePestDiseaseOccurrence)).isFalse();
    }

    @Test
    public void equalsTrueMIPSamplePestDiseaseOccurrenceTest() {
        MIPSamplePestDiseaseOccurrence mipSamplePestDiseaseOccurrence = MIPSamplePestDiseaseOccurrence.builder().value(22).build();
        this.mipSamplePestDiseaseOccurrence = mipSamplePestDiseaseOccurrence;
        assertThat(this.mipSamplePestDiseaseOccurrence.equals(mipSamplePestDiseaseOccurrence)).isTrue();
    }

    @Test
    public void hashCodeTrueMIPSamplePestDiseaseOccurrenceTest() {
        MIPSamplePestDiseaseOccurrence mipSamplePestDiseaseOccurrence = MIPSamplePestDiseaseOccurrence.builder().value(33).build();
        int value = mipSamplePestDiseaseOccurrence.hashCode();
        assertThat(mipSamplePestDiseaseOccurrence.hashCode()).isEqualTo(value);
    }

    @Test
    public void hashCodeFalseMIPSamplePestDiseaseOccurrenceTest() {
        MIPSamplePestDiseaseOccurrence mipSamplePestDiseaseOccurrence = MIPSamplePestDiseaseOccurrence.builder().value(11).build();
        int value = mipSamplePestDiseaseOccurrence.hashCode();
        value++;
        assertThat(mipSamplePestDiseaseOccurrence.hashCode()==value).isFalse();
    }


}