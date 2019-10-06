package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Farmer;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Supervisor;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tags;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Repeat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static org.junit.Assert.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MIPSampleTest {

    private MIPSample mipSample;
    private MIPSamplePestDiseaseOccurrence mipSamplePestDiseaseOccurrence;
    private MIPSamplePestOccurrence mipSamplePestOccurrence;
    private MIPSampleNaturalPredatorOccurrence mipSampleNaturalPredatorOccurrence;
    private Survey survey;

    @Before
    public void setUp() throws ParseException {
        Set<Supervisor> var = new HashSet<>();
        var.add(Supervisor.builder().id((long)2).name("MariO").build());
        var.add(Supervisor.builder().id((long)3).name("Francisco").build());
        var.add(Supervisor.builder().id((long)4).name("jorge").build());
        this.mipSamplePestOccurrence = MIPSamplePestOccurrence.builder().value(300).build();
        this.mipSamplePestDiseaseOccurrence = MIPSamplePestDiseaseOccurrence.builder().value(200).build();
        this.mipSampleNaturalPredatorOccurrence = MIPSampleNaturalPredatorOccurrence.builder().value(100).build();
        this.survey = Survey.builder().id((long)11).seedName("Test UnitaRIo")
                .field(Field.builder().id((long)1).name("FieLD test")
                        .farmer(Farmer.builder().id((long)1).name("São FRAncisco").build())
                            .supervisors(var)
                                .city(City.builder().name("NOva FatiMa").build()).build())
                                    .harvest(Harvest.builder().id((long)1).name("Safra TeStE").build()).build();
        this.mipSample = MIPSample.builder().id((long)1).defoliation(100).growthPhase(GrowthPhase.V2).survey(this.survey)
                .sampleDate(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2006"))
                .daysAfterEmergence(10).build();
    }

    @Test
    public void createTest() throws ParseException {
        Date dateSample= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2013");
        this.mipSample = MIPSample.builder().id((long)2).defoliation(110).growthPhase(GrowthPhase.V1).survey(this.survey)
                .sampleDate(dateSample)
                .daysAfterEmergence(103).build();
        assertThat(this.mipSample.getId()).isEqualTo((long)2);
        assertThat(this.mipSample.getDaysAfterEmergence()).isEqualTo(103);
        assertThat(this.mipSample.getSurvey()).isEqualTo(this.survey);
        assertThat(this.mipSample.getSampleDate()).isEqualTo(dateSample);
        assertThat(this.mipSample.getDefoliation()).isEqualTo(110);
        assertThat(this.mipSample.getGrowthPhase()).isEqualTo(GrowthPhase.V1);
    }

    @Test
    public void addPestOccurrenceTrueTest() {
     Pest pest = Pest.builder().id((long)1).pestSize(PestSize.ADULT).scientificName("Zamfiro").usualName("Marmota").build();
     double var = 12.22;
        assertThat(this.mipSample.addPestOccurrence(pest,var)).isTrue();
    }

    @Test
    public void addPestDiseaseOccurrenceTrueTest() {
        PestDisease pestDisease = PestDisease.builder().id((long)1).usualName("Borboleta").build();
        double var = 112.2;
        assertThat(this.mipSample.addPestDiseaseOccurrence(pestDisease,var)).isTrue();
    }

    @Test
    public void addPestNaturalPredatorOccurrence() {
        PestNaturalPredator pestNaturalPredator = PestNaturalPredator.builder().id((long)2).usualName("Marmota").build();
        double var = 33.33;
        assertThat(this.mipSample.addPestNaturalPredatorOccurrence(pestNaturalPredator,var)).isTrue();
    }

    @Test
    public void getHarvestNameTest() {
        assertThat(this.mipSample.getHarvestName()).isEqualTo("Safra Teste");
    }

    @Test
    public void getFarmerNameTest() {
        assertThat(this.mipSample.getFarmerName()).isEqualTo("São Francisco");
    }

    @Test
    public void getFieldNameTest() {
        assertThat(this.mipSample.getFieldName()).isEqualTo("Field Test");
    }

    @Test
    public void getCityNameTest() {
        assertThat(this.mipSample.getCityName()).isEqualTo("Nova Fatima");
    }

    @Description("A ordem dos nomes pode variar")
    @Test
    public void getSupervisorNamesTest() {
        assertThat(this.mipSample.getSupervisorNames()).isEqualTo("[Jorge, Mario, Francisco]");
    }

    @Test
    public void getSeedNameTest() {
        assertThat(this.mipSample.getSeedName()).isEqualTo("Test Unitario");
    }

    @Test
    public void getAndSetIdTest() {
        this.mipSample.setId((long)22);
        assertThat(this.mipSample.getId()).isEqualTo((long)22);

    }

    @Test
    public void getAndSetSampleDateTest() throws ParseException {
        Date dateSample= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2019");
        this.mipSample.setSampleDate(dateSample);
        assertThat(this.mipSample.getSampleDate()).isEqualTo(dateSample);
    }

    @Test
    public void getAndSetDaysAfterEmergenceTest() {
        this.mipSample.setDaysAfterEmergence(44);
        assertThat(this.mipSample.getDaysAfterEmergence()).isEqualTo(44);
    }

    @Test
    public void getAndSetDefoliationTest() {
        this.mipSample.setDefoliation(22);
        assertThat(this.mipSample.getDefoliation()).isEqualTo(22);
    }

    @Test
    public void getAndSetGrowthPhase() {
        this.mipSample.setGrowthPhase(GrowthPhase.R4);
        assertThat(this.mipSample.getGrowthPhase()).isEqualTo(GrowthPhase.R4);
    }

    @Test
    public void getAndSetMipSamplePestOccurrenceTest() {
        MIPSamplePestOccurrence mipSamplePestOccurrence = MIPSamplePestOccurrence.builder().value(11).build();
        Set<MIPSamplePestOccurrence> var2 = new HashSet<>();
        var2.add(this.mipSamplePestOccurrence);
        var2.add(mipSamplePestOccurrence);
        this.mipSample.setMipSamplePestOccurrence(var2);
        assertThat(this.mipSample.getMipSamplePestOccurrence()).containsExactlyInAnyOrder(this.mipSamplePestOccurrence,mipSamplePestOccurrence);
    }

    @Test
    public void getMipSamplePestDiseaseOccurrenceTest() {
        MIPSamplePestDiseaseOccurrence mipSamplePestDiseaseOccurrence = MIPSamplePestDiseaseOccurrence.builder().value(11).build();
        Set<MIPSamplePestDiseaseOccurrence> var1 = new HashSet<>();
        var1.add(this.mipSamplePestDiseaseOccurrence);
        var1.add(mipSamplePestDiseaseOccurrence);
        this.mipSample.setMipSamplePestDiseaseOccurrence(var1);
        assertThat(this.mipSample.getMipSamplePestDiseaseOccurrence()).containsExactlyInAnyOrder(this.mipSamplePestDiseaseOccurrence,mipSamplePestDiseaseOccurrence);
    }

    @Test
    public void getMipSampleNaturalPredatorOccurrenceTest() {
        MIPSampleNaturalPredatorOccurrence mipSampleNaturalPredatorOccurrence = MIPSampleNaturalPredatorOccurrence.builder().value(11).build();
        Set<MIPSampleNaturalPredatorOccurrence> var = new HashSet<>();
        var.add(this.mipSampleNaturalPredatorOccurrence);
        var.add(mipSampleNaturalPredatorOccurrence);
        this.mipSample.setMipSampleNaturalPredatorOccurrence(var);
    }

    @Test
    public void getAndSetSurvey() {
        this.mipSample.setSurvey(this.survey);
        assertThat(this.mipSample.getSurvey()).isEqualTo(this.survey);
    }


    @Test
    public void equalsFalseMIPSampleTest() {
        MIPSample mipSample = MIPSample.builder().id((long)11).survey(this.survey).build();
        this.mipSample = MIPSample.builder().id((long)33).survey(Survey.builder().id((long)1).bt(false).seedName("Test").build()).build();
        assertThat(this.mipSample.equals(mipSample)).isFalse();
    }

    @Test
    public void equalsTrueMIPSampleTest() {
        MIPSample mipSample= MIPSample.builder().id((long)11).survey(this.survey).build();
        this.mipSample = mipSample;
        assertThat(this.mipSample.equals(mipSample)).isTrue();
    }

    @Test
    public void hashCodeTrueMIPSampleTest() {
        MIPSample mipSample= MIPSample.builder().id((long)11).survey(this.survey).build();
        int value = mipSample.hashCode();
        assertThat(mipSample.hashCode()).isEqualTo(value);
    }

    @Test
    public void hashCodeFalseMIPSampleTest() {
        MIPSample mipSample= MIPSample.builder().id((long)11).survey(this.survey).build();
        int value = mipSample.hashCode();
        value++;
        assertThat(mipSample.hashCode()==value).isFalse();
    }
}