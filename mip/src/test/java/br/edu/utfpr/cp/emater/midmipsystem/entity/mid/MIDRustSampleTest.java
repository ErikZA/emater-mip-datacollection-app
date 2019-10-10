package br.edu.utfpr.cp.emater.midmipsystem.entity.mid;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.GrowthPhase;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mid.MIDRustSampleRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MIDRustSampleTest {

    private MIDRustSample midRustSample;
    private Date date;
    private Survey survey;
    private MIDSampleSporeCollectorOccurrence midSampleSporeCollectorOccurrence;
    private MIDSampleLeafInspectionOccurrence midSampleLeafInspectionOccurrence;
    private MIDSampleFungicideApplicationOccurrence midSampleFungicideApplicationOccurrence;
    private BladeReadingResponsiblePerson bladeReadingResponsiblePerson;
    private MIDRustSampleRepository midRustSampleRepository = mock(MIDRustSampleRepository.class);


    @Before
    public void setUp() throws ParseException {
        this.date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-12-2013");

        this.midSampleFungicideApplicationOccurrence = MIDSampleFungicideApplicationOccurrence.builder()
                .asiaticRustApplication(false).fungicideApplicationDate(this.date)
                .notes("Test notes").otherDiseasesApplication(false).build();

        this.midSampleLeafInspectionOccurrence = MIDSampleLeafInspectionOccurrence.builder()
                .growthPhase(GrowthPhase.V2).bladeReadingRustResultLeafInspection(AsiaticRustTypesLeafInspection.NO_VISIBLE_DAMAGE).build();

        this.bladeReadingResponsiblePerson = BladeReadingResponsiblePerson.builder()
                .id((long) 442).name("TestBlade").build();

        this.midSampleSporeCollectorOccurrence = MIDSampleSporeCollectorOccurrence.builder()
                .bladeReadingRustResultCollector(AsiaticRustTypesSporeCollector.FEASIBLE_SPORES_GROUPED)
                .bladeReadingDate(this.date).bladeInstalledPreCold(false)
                .bladeReadingResponsiblePerson(this.bladeReadingResponsiblePerson).build();


        this.survey = Survey.builder().id((long) 3).seedName("TestPar").separatedWeight(true).build();

        this.midRustSample = MIDRustSample.builder().id((long)122).survey(this.survey).sampleDate(this.date).build();
    }

    @Test
    public void createTest() {
    }

    @Test
    public void getAndSetId() {
        assertThat(this.midRustSample.getId()).isEqualTo((long)122);
        this.midRustSample.setId((long) 33);
        assertThat(this.midRustSample.getId()).isEqualTo((long)33);
    }

    @Test
    public void getAndSetSurvey() {
        Survey survey = Survey.builder().id((long) 99).seedName("TestSurvey").separatedWeight(false).build();
        assertThat(this.midRustSample.getSurvey()).isEqualTo(this.survey);
        this.midRustSample.setSurvey(survey);
        assertThat(this.midRustSample.getSurvey()).isEqualTo(survey);
        assertThat(this.midRustSample.getSurvey().getSeedName()).isEqualTo("Testsurvey");
    }

    @Test
    public void getAndSetSampleDate() throws ParseException {
        Date date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("11-12-2913");
        assertThat(this.midRustSample.getSampleDate()).isEqualTo(this.date);
        this.midRustSample.setSampleDate(date);
        assertThat(this.midRustSample.getSampleDate()).isEqualTo(date);
    }

    @Test
    public void getAndSetSporeCollectorOccurrence() {
        this.midRustSample.setSporeCollectorOccurrence(this.midSampleSporeCollectorOccurrence);
        assertThat(this.midRustSample.getSporeCollectorOccurrence()).isEqualTo(this.midSampleSporeCollectorOccurrence);
        assertThat(this.midRustSample.getSporeCollectorOccurrence().getBladeReadingRustResultCollector().getDescription())
                .isEqualTo("Com Esporos Viáveis - Aglomerados");
        assertThat(this.midRustSample.getSporeCollectorOccurrence().getBladeReadingResponsiblePerson().getId())
                .isEqualTo((long)442);
    }

    @Test
    public void getAnsSetLeafInspectionOccurrence() {
        this.midRustSample.setLeafInspectionOccurrence(this.midSampleLeafInspectionOccurrence);
        assertThat(this.midRustSample.getLeafInspectionOccurrence()).isEqualTo(this.midSampleLeafInspectionOccurrence);
        assertThat(this.midRustSample.getLeafInspectionOccurrence().getBladeReadingRustResultLeafInspection().getDescription())
                .isEqualTo("Sem Lesões Visíveis");
        assertThat(this.midRustSample.getLeafInspectionOccurrence().getGrowthPhase().getDescription())
                .isEqualTo("V2");

    }

    @Test
    public void getAndSetFungicideOccurrence() {
        this.midRustSample.setFungicideOccurrence(this.midSampleFungicideApplicationOccurrence);
        assertThat(this.midRustSample.getFungicideOccurrence()).isEqualTo(this.midSampleFungicideApplicationOccurrence);
        assertThat(this.midRustSample.getFungicideOccurrence().getFungicideApplicationDate())
                .isEqualTo(this.date);
        assertThat(this.midRustSample.getFungicideOccurrence().getNotes())
                .isEqualTo("Test notes");
    }

    @Test
    public void equalsFalseMIDRustSampleTest() throws ParseException {
        MIDRustSample midRustSample = MIDRustSample.builder().id((long)90)
                .survey(Survey.builder().id((long) 323).seedName("TestFalse").separatedWeight(true).build())
                .sampleDate(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-01-2099")).build();
        assertThat(this.midRustSample.equals(midRustSample)).isFalse();
    }

    @Test
    public void equalsTrueMIDRustSampleTest() throws ParseException {
        MIDRustSample midRustSample = MIDRustSample.builder().id((long)90)
                .survey(Survey.builder().id((long) 323).seedName("TestFalse").separatedWeight(true).build())
                .sampleDate(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-01-2099")).build();
        this.midRustSample = midRustSample;
        assertThat(this.midRustSample.equals(midRustSample)).isTrue();
    }

    @Test
    public void hashCodeTrueMIDRustSampleTest() {
        MIDRustSample midRustSample = MIDRustSample.builder().id((long)90).survey(this.survey).sampleDate(this.date).build();
        int value = midRustSample.hashCode();
        assertThat(midRustSample.hashCode()).isEqualTo(value);
    }

    @Test
    public void hashCodeFalseMIDRustSampleTest() {
        MIDRustSample midRustSample = MIDRustSample.builder().id((long)90).survey(this.survey).sampleDate(this.date).build();
        int value = midRustSample.hashCode();
        value++;
        assertThat(midRustSample.hashCode()==value).isFalse();
    }

    @Test (expected = ConstraintViolationException.class)
    public  void setDateNullExceptionTest() {
        when(this.midRustSampleRepository.save(any(MIDRustSample.class)))
                .thenThrow(ConstraintViolationException.class);
        this.midRustSample.setSampleDate(null);
        this.midRustSampleRepository.save(this.midRustSample);
    }


}