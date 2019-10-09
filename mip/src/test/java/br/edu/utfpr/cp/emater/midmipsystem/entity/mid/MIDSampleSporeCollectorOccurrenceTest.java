package br.edu.utfpr.cp.emater.midmipsystem.entity.mid;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class MIDSampleSporeCollectorOccurrenceTest {

    private MIDSampleSporeCollectorOccurrence midSampleSporeCollectorOccurrence;
    private Date date;
    private BladeReadingResponsiblePerson bladeReadingResponsiblePerson;
    @Before
    public void setUp() throws ParseException {
        this.date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-12-2999");
        this.bladeReadingResponsiblePerson = BladeReadingResponsiblePerson.builder()
                .id((long) 442).name("TestBlade").build();
        this.midSampleSporeCollectorOccurrence = new MIDSampleSporeCollectorOccurrence();
        this.midSampleSporeCollectorOccurrence = MIDSampleSporeCollectorOccurrence.builder()
                .bladeReadingRustResultCollector(AsiaticRustTypesSporeCollector.FEASIBLE_SPORES_GROUPED)
                .bladeReadingDate(date).bladeInstalledPreCold(false)
                .bladeReadingResponsiblePerson(this.bladeReadingResponsiblePerson).build();
    }

    @Test
    public void isBladeInstalledPreCold() {
        assertThat(this.midSampleSporeCollectorOccurrence.isBladeInstalledPreCold()).isFalse();
        this.midSampleSporeCollectorOccurrence.setBladeInstalledPreCold(true);
        assertThat(this.midSampleSporeCollectorOccurrence.isBladeInstalledPreCold()).isTrue();
    }

    @Test
    public void getAndSetBladeReadingResponsiblePerson() {
        BladeReadingResponsiblePerson bladeReadingResponsiblePerson = BladeReadingResponsiblePerson.builder()
                .id((long) 442).name("TestBlade").build();
        this.midSampleSporeCollectorOccurrence.setBladeReadingResponsiblePerson(bladeReadingResponsiblePerson);
        assertThat(this.midSampleSporeCollectorOccurrence.getBladeReadingResponsiblePerson()).isEqualTo(bladeReadingResponsiblePerson);
    }

    @Test
    public void getAndSetBladeReadingDate() throws ParseException {
        Date date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("11-12-2009");
        this.midSampleSporeCollectorOccurrence.setBladeReadingDate(date);
        assertThat(this.midSampleSporeCollectorOccurrence.getBladeReadingDate()).isEqualTo(date);
    }

    @Test
    public void getAndSetBladeReadingRustResultCollector() {
        assertThat(this.midSampleSporeCollectorOccurrence.getBladeReadingRustResultCollector()).isEqualTo(AsiaticRustTypesSporeCollector.FEASIBLE_SPORES_GROUPED);
        this.midSampleSporeCollectorOccurrence.setBladeReadingRustResultCollector(AsiaticRustTypesSporeCollector.NO_RUST_SPORES);
        assertThat(this.midSampleSporeCollectorOccurrence.getBladeReadingRustResultCollector()).isEqualTo(AsiaticRustTypesSporeCollector.NO_RUST_SPORES);
    }

    @Test
    public void builderTest() throws ParseException {
        Date date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-12-2113");
        this.midSampleSporeCollectorOccurrence = new MIDSampleSporeCollectorOccurrence();
        this.midSampleSporeCollectorOccurrence = MIDSampleSporeCollectorOccurrence.builder()
                .bladeReadingRustResultCollector(AsiaticRustTypesSporeCollector.FEASIBLE_SPORES_GROUPED)
                .bladeReadingDate(date).bladeInstalledPreCold(false)
                .bladeReadingResponsiblePerson(BladeReadingResponsiblePerson.builder()
                        .id((long) 442).name("TestBlade").build()).build();
        assertThat(this.midSampleSporeCollectorOccurrence.getBladeReadingDate()).isEqualTo(date);
        assertThat(this.midSampleSporeCollectorOccurrence.isBladeInstalledPreCold()).isFalse();
        assertThat(this.midSampleSporeCollectorOccurrence.getBladeReadingRustResultCollector()).isEqualTo(AsiaticRustTypesSporeCollector.FEASIBLE_SPORES_GROUPED);
        assertThat(this.midSampleSporeCollectorOccurrence.getBladeReadingResponsiblePerson().getName()).isEqualTo("Testblade");
    }

    @Test
    public void equalsMIDSampleSporeCollectorOccurrenceTest () throws ParseException {
        MIDSampleSporeCollectorOccurrence midSampleSporeCollectorOccurrence = MIDSampleSporeCollectorOccurrence.builder()
                .bladeReadingRustResultCollector(AsiaticRustTypesSporeCollector.NO_RUST_SPORES).bladeInstalledPreCold(false)
                .bladeReadingDate(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2013"))
                .bladeReadingResponsiblePerson(BladeReadingResponsiblePerson.builder()
                        .id((long) 22).name("TestBlade").build()).build();
        assertThat(this.midSampleSporeCollectorOccurrence.equals(midSampleSporeCollectorOccurrence)).isFalse();
    }

    @Test
    public void equalsTrueMIDSampleSporeCollectorOccurrenceTest () throws ParseException {
        MIDSampleSporeCollectorOccurrence midSampleSporeCollectorOccurrence = MIDSampleSporeCollectorOccurrence.builder()
                .bladeReadingRustResultCollector(AsiaticRustTypesSporeCollector.NO_RUST_SPORES).bladeInstalledPreCold(false)
                .bladeReadingDate(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2013"))
                .bladeReadingResponsiblePerson(BladeReadingResponsiblePerson.builder()
                        .id((long) 22).name("TestBlade").build()).build();
        this.midSampleSporeCollectorOccurrence = midSampleSporeCollectorOccurrence;
        assertThat(this.midSampleSporeCollectorOccurrence.equals(midSampleSporeCollectorOccurrence)).isTrue();
    }

    @Test
    public void hashCodeTrueMIDSampleSporeCollectorOccurrenceTest() throws ParseException {
        MIDSampleSporeCollectorOccurrence midSampleSporeCollectorOccurrence = MIDSampleSporeCollectorOccurrence.builder()
                .bladeReadingRustResultCollector(AsiaticRustTypesSporeCollector.NO_RUST_SPORES).bladeInstalledPreCold(false)
                .bladeReadingDate(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2013"))
                .bladeReadingResponsiblePerson(BladeReadingResponsiblePerson.builder()
                        .id((long) 22).name("TestBlade").build()).build();
        int value = midSampleSporeCollectorOccurrence.hashCode();
        assertThat(midSampleSporeCollectorOccurrence.hashCode()).isEqualTo(value);
    }

    @Test
    public void hashCodeFalseMIDSampleSporeCollectorOccurrenceTest() throws ParseException {
        MIDSampleSporeCollectorOccurrence midSampleSporeCollectorOccurrence = MIDSampleSporeCollectorOccurrence.builder()
                .bladeReadingRustResultCollector(AsiaticRustTypesSporeCollector.NO_RUST_SPORES).bladeInstalledPreCold(false)
                .bladeReadingDate(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2013"))
                .bladeReadingResponsiblePerson(BladeReadingResponsiblePerson.builder()
                        .id((long) 22).name("TestBlade").build()).build();
        int value = midSampleSporeCollectorOccurrence.hashCode();
        value++;
        assertThat(midSampleSporeCollectorOccurrence.hashCode()==value).isFalse();
    }

}