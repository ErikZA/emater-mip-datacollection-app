package br.edu.utfpr.cp.emater.midmipsystem.entity.mid;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MIDSampleFungicideApplicationOccurrenceTest {

    private MIDSampleFungicideApplicationOccurrence midSampleFungicideApplicationOccurrence;
    private Date date;

    @Before
    public void setUp() throws ParseException {
        this.date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-12-2013");
        this.midSampleFungicideApplicationOccurrence = MIDSampleFungicideApplicationOccurrence.builder()
                .asiaticRustApplication(false).fungicideApplicationDate(this.date)
                .notes("Test notes").otherDiseasesApplication(false).build();
    }

    @Test
    public void isAsiaticRustApplicationTest() {
        assertThat(this.midSampleFungicideApplicationOccurrence.isAsiaticRustApplication()).isFalse();
        this.midSampleFungicideApplicationOccurrence.setAsiaticRustApplication(true);
        assertThat(this.midSampleFungicideApplicationOccurrence.isAsiaticRustApplication()).isTrue();
    }

    @Test
    public void isOtherDiseasesApplicationTest() {
        assertThat(this.midSampleFungicideApplicationOccurrence.isOtherDiseasesApplication()).isFalse();
        this.midSampleFungicideApplicationOccurrence.setOtherDiseasesApplication(true);
        assertThat(this.midSampleFungicideApplicationOccurrence.isOtherDiseasesApplication()).isTrue();
    }

    @Test
    public void getAndSetFungicideApplicationDateTest() throws ParseException {
        Date date1 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-12-2413");
        assertThat(this.midSampleFungicideApplicationOccurrence.getFungicideApplicationDate()).isEqualTo(this.date);
        this.midSampleFungicideApplicationOccurrence.setFungicideApplicationDate(date1);
        assertThat(this.midSampleFungicideApplicationOccurrence.getFungicideApplicationDate()).isEqualTo(date1);
    }

    @Test //não normaliza
    public void getAndSetNotesTest() {
        assertThat(this.midSampleFungicideApplicationOccurrence.getNotes()).isEqualTo("Test notes");
        this.midSampleFungicideApplicationOccurrence.setNotes("NO Normalize CASE");
        assertThat(this.midSampleFungicideApplicationOccurrence.getNotes()).isEqualTo("NO Normalize CASE");
    }

    @Test
    public void builderTest() throws ParseException {
        this.date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("11-12-2013");
        this.midSampleFungicideApplicationOccurrence = new MIDSampleFungicideApplicationOccurrence();
        this.midSampleFungicideApplicationOccurrence = MIDSampleFungicideApplicationOccurrence.builder()
                .asiaticRustApplication(false).fungicideApplicationDate(this.date)
                .notes("Test notes").otherDiseasesApplication(false).build();
        assertThat(this.midSampleFungicideApplicationOccurrence.getNotes()).isEqualTo("Test notes");
        assertThat(this.midSampleFungicideApplicationOccurrence.getFungicideApplicationDate()).isEqualTo(this.date);
        assertThat(this.midSampleFungicideApplicationOccurrence.isOtherDiseasesApplication()).isEqualTo(false);
        assertThat(this.midSampleFungicideApplicationOccurrence.isAsiaticRustApplication()).isEqualTo(false);
    }

    @Test
    public void equalsMIDtTest() {
        MIDSampleFungicideApplicationOccurrence midSampleFungicideApplicationOccurrence = MIDSampleFungicideApplicationOccurrence.builder()
                .notes("test notes").asiaticRustApplication(false).otherDiseasesApplication(true).build();
        assertThat(this.midSampleFungicideApplicationOccurrence.equals(midSampleFungicideApplicationOccurrence)).isFalse();
    }

    @Test
    public void equalsTrueMIDSampleFungicideApplicationOccurrenceTest() {
        MIDSampleFungicideApplicationOccurrence midSampleFungicideApplicationOccurrence = MIDSampleFungicideApplicationOccurrence.builder()
                .notes("test notes").asiaticRustApplication(false).otherDiseasesApplication(true).build();
        this.midSampleFungicideApplicationOccurrence = midSampleFungicideApplicationOccurrence;
        assertThat(this.midSampleFungicideApplicationOccurrence.equals(midSampleFungicideApplicationOccurrence)).isTrue();
    }

    @Test
    public void hashCodeTrueMIDSampleFungicideApplicationOccurrenceTest() {
        MIDSampleFungicideApplicationOccurrence midSampleFungicideApplicationOccurrence = MIDSampleFungicideApplicationOccurrence.builder()
                .notes("test notes").asiaticRustApplication(false).otherDiseasesApplication(true).build();
        int value = midSampleFungicideApplicationOccurrence.hashCode();
        assertThat(midSampleFungicideApplicationOccurrence.hashCode()).isEqualTo(value);
    }

    @Test
    public void hashCodeFalseMIDSampleFungicideApplicationOccurrenceTest() {
        MIDSampleFungicideApplicationOccurrence midSampleFungicideApplicationOccurrence = MIDSampleFungicideApplicationOccurrence.builder()
                .notes("test notes").asiaticRustApplication(false).otherDiseasesApplication(true).build();
        int value = midSampleFungicideApplicationOccurrence.hashCode();
        value++;
        assertThat(midSampleFungicideApplicationOccurrence.hashCode()==value).isFalse();
    }


}