package br.edu.utfpr.cp.emater.midmipsystem.entity.survey;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SizeDataTest {

    private SizeData sizeData;

    @Test
    public void builderTest() {
        this.sizeData = new SizeData();
        this.sizeData = SizeData.builder().plantPerMeter(22).totalArea(222).totalPlantedArea(2222).build();
    }

    @Test
    public void getAndSetTotalAreaTest() {
        this.sizeData = SizeData.builder().build();
        this.sizeData.setPlantPerMeter(222);
        assertThat(this.sizeData.getPlantPerMeter()).isEqualTo(222);
    }

    @Test
    public void getAndSetPlantPerMeterTest() {
        this.sizeData = SizeData.builder().build();
        this.sizeData.setTotalArea(222);
        assertThat(this.sizeData.getTotalArea()).isEqualTo(222);
    }

    @Test
    public void getAndSetTotalPlantedAreaTest(){
        this.sizeData = SizeData.builder().build();
        this.sizeData.setTotalPlantedArea(222);
        assertThat(this.sizeData.getTotalPlantedArea()).isEqualTo(222);
    }
}