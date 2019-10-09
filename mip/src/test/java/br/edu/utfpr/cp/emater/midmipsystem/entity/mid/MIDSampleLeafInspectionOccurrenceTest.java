package br.edu.utfpr.cp.emater.midmipsystem.entity.mid;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.GrowthPhase;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.GeneratedValue;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MIDSampleLeafInspectionOccurrenceTest {

    private MIDSampleLeafInspectionOccurrence midSampleLeafInspectionOccurrence;

    @Before
    public void setUp() {
        this.midSampleLeafInspectionOccurrence = MIDSampleLeafInspectionOccurrence.builder()
                .growthPhase(GrowthPhase.V2).bladeReadingRustResultLeafInspection(AsiaticRustTypesLeafInspection.NO_VISIBLE_DAMAGE).build();

    }

    @Test
    public void getAndSetGrowthPhaseTest() {
        assertThat(this.midSampleLeafInspectionOccurrence.getGrowthPhase()).isEqualTo(GrowthPhase.V2);
        this.midSampleLeafInspectionOccurrence.setGrowthPhase(GrowthPhase.V1);
        assertThat(this.midSampleLeafInspectionOccurrence.getGrowthPhase()).isEqualTo(GrowthPhase.V1);
    }

    @Test
    public void getAndSetBladeReadingRustResultLeafInspectionTest() {
        assertThat(this.midSampleLeafInspectionOccurrence.getBladeReadingRustResultLeafInspection()).isEqualTo(AsiaticRustTypesLeafInspection.NO_VISIBLE_DAMAGE);
        this.midSampleLeafInspectionOccurrence.setBladeReadingRustResultLeafInspection(AsiaticRustTypesLeafInspection.VISIBLE_DAMAGE_PARTIAL_CROP);
        assertThat(this.midSampleLeafInspectionOccurrence.getBladeReadingRustResultLeafInspection()).isEqualTo(AsiaticRustTypesLeafInspection.VISIBLE_DAMAGE_PARTIAL_CROP);

    }

    @Test
    public void builderTest() {
        this.midSampleLeafInspectionOccurrence = new MIDSampleLeafInspectionOccurrence();
        this.midSampleLeafInspectionOccurrence = MIDSampleLeafInspectionOccurrence.builder()
                .growthPhase(GrowthPhase.V2).bladeReadingRustResultLeafInspection(AsiaticRustTypesLeafInspection.NO_VISIBLE_DAMAGE).build();
        assertThat(this.midSampleLeafInspectionOccurrence.getBladeReadingRustResultLeafInspection()).isEqualTo(AsiaticRustTypesLeafInspection.NO_VISIBLE_DAMAGE);
        assertThat(this.midSampleLeafInspectionOccurrence.getGrowthPhase()).isEqualTo(GrowthPhase.V2);
    }


    @Test
    public void equalsFalseMIDSampleLeafInspectionOccurrenceTest() {
        MIDSampleLeafInspectionOccurrence midSampleLeafInspectionOccurrence = MIDSampleLeafInspectionOccurrence.builder()
                .growthPhase(GrowthPhase.V1).bladeReadingRustResultLeafInspection(AsiaticRustTypesLeafInspection.VISIBLE_DAMAGE_ISOLATED).build();
        assertThat(this.midSampleLeafInspectionOccurrence.equals(midSampleLeafInspectionOccurrence)).isFalse();
    }

    @Test
    public void equalsTrueMIDSampleLeafInspectionOccurrenceTest() {
        MIDSampleLeafInspectionOccurrence midSampleLeafInspectionOccurrence = MIDSampleLeafInspectionOccurrence.builder()
                .growthPhase(GrowthPhase.V2).bladeReadingRustResultLeafInspection(AsiaticRustTypesLeafInspection.NO_VISIBLE_DAMAGE).build();
        this.midSampleLeafInspectionOccurrence = midSampleLeafInspectionOccurrence;
        assertThat(this.midSampleLeafInspectionOccurrence.equals(midSampleLeafInspectionOccurrence)).isTrue();
    }

    @Test
    public void hashCodeTrueMIDSampleLeafInspectionOccurrenceTest() {
        MIDSampleLeafInspectionOccurrence midSampleLeafInspectionOccurrence = MIDSampleLeafInspectionOccurrence.builder()
                .growthPhase(GrowthPhase.V2).bladeReadingRustResultLeafInspection(AsiaticRustTypesLeafInspection.NO_VISIBLE_DAMAGE).build();
        int value = midSampleLeafInspectionOccurrence.hashCode();
        assertThat(midSampleLeafInspectionOccurrence.hashCode()).isEqualTo(value);
    }

    @Test
    public void hashCodeFalseMIDSampleLeafInspectionOccurrenceTest() {
        MIDSampleLeafInspectionOccurrence midSampleLeafInspectionOccurrence = MIDSampleLeafInspectionOccurrence.builder()
                .growthPhase(GrowthPhase.V2).bladeReadingRustResultLeafInspection(AsiaticRustTypesLeafInspection.NO_VISIBLE_DAMAGE).build();
        int value = midSampleLeafInspectionOccurrence.hashCode();
        value++;
        assertThat(midSampleLeafInspectionOccurrence.hashCode()==value).isFalse();
    }
}