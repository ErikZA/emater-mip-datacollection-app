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
public class DateDataTest {

    private DateData dateData;

    @Test
    public void getSowedDate() throws ParseException {
        Date dateSample= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2013");
        this.dateData = DateData.builder().build();
        this.dateData.setSowedDate(dateSample);
        assertThat(this.dateData.getSowedDate()).isEqualTo(dateSample);
    }

    @Test
    public void getEmergenceDate() throws ParseException {
        Date dateSample= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2013");
        this.dateData = DateData.builder().build();
        this.dateData.setEmergenceDate(dateSample);
        assertThat(this.dateData.getEmergenceDate()).isEqualTo(dateSample);
    }

    @Test
    public void getHarvestDate() throws ParseException {
        Date dateSample= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2013");
        this.dateData = DateData.builder().build();
        this.dateData.setHarvestDate(dateSample);
        assertThat(this.dateData.getHarvestDate()).isEqualTo(dateSample);
    }

    @Test
    public void builder() throws ParseException {
        Date dateSample= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2013");
        this.dateData = new DateData();
        this.dateData = DateData.builder().emergenceDate(dateSample).harvestDate(dateSample).sowedDate(dateSample).build();

    }
}