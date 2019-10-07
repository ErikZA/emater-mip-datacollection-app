package br.edu.utfpr.cp.emater.midmipsystem.entity.survey;

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
public class HarvestTest {

    private Harvest harvest;

    @Test
    public void builder() throws ParseException {
        Date dateSampleBegin= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2013");
        Date dateSampleEnd= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-12-2013");
        this.harvest = Harvest.builder().name("Test Field").id((long) 22).end(dateSampleEnd).begin(dateSampleBegin).build();
    }

    @Test
    public void setAndSetNameTest() {
        this.harvest = Harvest.builder().id((long) 22).name("Test Case").build();
        this.harvest.setName("TestCase name");
        assertThat(this.harvest.getName()).isEqualTo("Testcase Name");
    }

    @Test
    public void getAndSetIdTest() {
        this.harvest = Harvest.builder().id((long) 22).name("Test Case").build();
        this.harvest.setId((long) 33);
        assertThat(this.harvest.getId()).isEqualTo((long)33);
    }


    @Test
    public void getAndSetBeginTest() throws ParseException {
        this.harvest = Harvest.builder().id((long) 22).name("Test Case").build();
        Date dateSampleBegin= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2013");
        this.harvest.setBegin(dateSampleBegin);
        assertThat(this.harvest.getBegin()).isEqualTo(dateSampleBegin);

    }

    @Test
    public void getEnd() throws ParseException {
        this.harvest = Harvest.builder().id((long) 22).name("Test Case").build();
        Date dateSampleEnd= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-12-2013");
        this.harvest.setEnd(dateSampleEnd);
        assertThat(this.harvest.getEnd()).isEqualTo(dateSampleEnd);
    }

    @Test
    public void equalsFalseHarvestTest() throws ParseException {
        Date dateSampleBegin= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2013");
        Date dateSampleEnd= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-12-2013");
        Harvest harvest = Harvest.builder().id((long)22).begin(null).end(null).name("test harves").build();
        this.harvest = Harvest.builder().id((long)33).name("test1 harves2").end(dateSampleEnd).begin(dateSampleBegin).build();;
        assertThat(this.harvest.equals(harvest)).isFalse();
    }

    @Test
    public void equalsTrueHarvestTest() {
        Harvest harvest = Harvest.builder().id((long) 22).name("test harves").build();
        this.harvest = harvest;
        assertThat(this.harvest.equals(harvest)).isTrue();
    }

    @Test
    public void hashCodeTrueHarvestTest() {
        Harvest harvest = Harvest.builder().id((long) 22).name("test harves").build();
        int value = harvest.hashCode();
        assertThat(harvest.hashCode()).isEqualTo(value);
    }

    @Test
    public void hashCodeFalseHarvestTest() {
        Harvest harvest = Harvest.builder().id((long) 22).name("test harves").build();
        int value = harvest.hashCode();
        value++;
        assertThat(harvest.hashCode()==value).isFalse();
    }

}