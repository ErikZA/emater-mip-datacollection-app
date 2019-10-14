package br.edu.utfpr.cp.emater.midmipsystem.service.base;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalDateTimeConverterTest {

    private LocalDateTimeConverter localDateTimeConverter = new LocalDateTimeConverter();

    Object dateValue = LocalDate.of(2018, 07, 22);

    @Test
    public void getAsObjectLocalDateTimeConverterTest() {
        assertThat(this.localDateTimeConverter.getAsObject(null,null,"2018-07-22")).isEqualTo(dateValue);
    }

    @Test
    public void getAsStringLocalDateTimeConverterTest() {
        assertThat(this.localDateTimeConverter.getAsString(null,null,dateValue)).isEqualTo("22/07/2018");
    }

}