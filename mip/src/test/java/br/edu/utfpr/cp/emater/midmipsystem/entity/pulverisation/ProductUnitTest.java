package br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductUnitTest {

    @Test
    public void getDescription() {
        ProductUnit productUnit = ProductUnit.L;
        ProductUnit productUnit1 = ProductUnit.ML;
        ProductUnit productUnit2 = ProductUnit.KG;
        ProductUnit productUnit3 = ProductUnit.G;
        productUnit.setDescription("L");
        assertThat(productUnit.getDescription()).isEqualTo("L");
        assertThat(productUnit1.getDescription()).isEqualTo("ML");
        assertThat(productUnit2.getDescription()).isEqualTo("KG");
        assertThat(productUnit3.getDescription()).isEqualTo("G");
    }
}