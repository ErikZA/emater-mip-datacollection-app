package br.edu.utfpr.cp.emater.midmipsystem.entity.survey;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductivityDataTest {

    private ProductivityData productivityData;

    @Test
    public void builderTest() {
        this.productivityData = new ProductivityData();
        this.productivityData = ProductivityData.builder().productivityFarmer(23)
                .productivityField(23).separatedWeight(false).build();
    }

    @Test
    public void getAndSetProductivityFieldTest() {
        this.productivityData = ProductivityData.builder().build();
        this.productivityData.setProductivityField(23);
        assertThat(this.productivityData.getProductivityField()).isEqualTo(23);
    }

    @Test
    public void getAndSetProductivityFarmerTest() {
        this.productivityData = ProductivityData.builder().build();
        this.productivityData.setProductivityFarmer(22);
        assertThat(this.productivityData.getProductivityFarmer()).isEqualTo(22);
    }

    @Test
    public void isSeparatedWeightTest() {
        this.productivityData = ProductivityData.builder().build();
        this.productivityData.setSeparatedWeight(false);
        assertThat(this.productivityData.isSeparatedWeight()).isFalse();
        this.productivityData.setSeparatedWeight(true);
        assertThat(this.productivityData.isSeparatedWeight()).isTrue();
    }


    @Test
    public void equalsFalseProductivityDataTest() {
        ProductivityData productivityData = ProductivityData.builder().separatedWeight(false)
                .productivityField(33).productivityFarmer(333).build();
        this.productivityData = ProductivityData.builder().separatedWeight(true).productivityField(33).build();
        assertThat(this.productivityData.equals(productivityData)).isFalse();
    }

    @Test
    public void equalsTrueProductivityDataTest() {
        ProductivityData productivityData = ProductivityData.builder().separatedWeight(false)
                .productivityField(33).productivityFarmer(333).build();
        this.productivityData = productivityData;
        assertThat(this.productivityData.equals(productivityData)).isTrue();
    }

    @Test
    public void hashCodeTrueProductivityDataTest() {
        ProductivityData productivityData = ProductivityData.builder().separatedWeight(false)
                .productivityField(33).productivityFarmer(333).build();
        int value = productivityData.hashCode();
        assertThat(productivityData.hashCode()).isEqualTo(value);
    }

    @Test
    public void hashCodeFalseProductivityDataTest() {
        ProductivityData productivityData = ProductivityData.builder().separatedWeight(false)
                .productivityField(33).productivityFarmer(333).build();
        int value = productivityData.hashCode();
        value++;
        assertThat(productivityData.hashCode()==value).isFalse();
    }


}