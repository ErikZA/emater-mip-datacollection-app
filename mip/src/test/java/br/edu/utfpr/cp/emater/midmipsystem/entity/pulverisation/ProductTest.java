package br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation;

import br.edu.utfpr.cp.emater.midmipsystem.repository.pulverisation.ProductRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductTest {

    private Product product;
    private Target target;
    private ProductRepository productRepository = mock(ProductRepository.class);


    @Before
    public void setUp() {
        this.target = Target.builder().category(TargetCategory.ADJUVANTE)
                .id((long) 33).description("Fortificador NormaLISE CASE").build();
        this.product = Product.builder().id((long)44).dose(22.22).name("Carvao Vegetal")
                .target(this.target).unit(ProductUnit.KG).build();
    }

    @Test
    public void setNameTest() {
        assertThat(this.product.getName()).isEqualTo("Carvao Vegetal");
        this.product.setName("MACAXEIRA NOrmalize");
        assertThat(this.product.getName()).isEqualTo("Macaxeira Normalize");
    }

    @Test
    public void getUnitDescriptionTest() {
        assertThat(this.product.getUnitDescription()).isEqualTo("KG");
    }

    @Test
    public void getTargetDescriptionTest() {
        assertThat(this.product.getTargetDescription()).isEqualTo("Fortificador NormaLISE CASE");
    }

    @Test
    public void getTargetIdTest() {
        assertThat(this.product.getTargetId()).isEqualTo((long)33);
    }

    @Test
    public void getAndSetIdTest() {
        assertThat(this.product.getId()).isEqualTo((long)44);
        this.product.setId((long)99);
        assertThat(this.product.getId()).isEqualTo((long)99);
    }

    @Test
    public void getAndSetDoseTest() {
        assertThat(this.product.getDose()).isEqualTo(22.22);
        this.product.setDose(55.55);
        assertThat(this.product.getDose()).isEqualTo(55.55);
    }

    @Test
    public void getAndSetUnitTest() {
        assertThat(this.product.getUnit()).isEqualTo(ProductUnit.KG);
        this.product.setUnit(ProductUnit.G);
        assertThat(this.product.getUnit()).isEqualTo(ProductUnit.G);
        assertThat(this.product.getUnit().getDescription()).isEqualTo("G");
    }

    @Test
    public void getAndSetTargetTest() {
        Target target = Target.builder().category(TargetCategory.HERBICIDA)
                .id((long) 65).description("NormaLISE CASE").build();
        assertThat(this.product.getTarget()).isEqualTo(this.target);
        this.product.setTarget(target);
        assertThat(this.product.getTarget()).isEqualTo(target);
        assertThat(this.product.getTarget().getDescription()).isEqualTo("NormaLISE CASE");
        assertThat(this.product.getTarget().getId()).isEqualTo((long)65);
    }

    @Test
    public void builderTest() {
        Target target = Target.builder().category(TargetCategory.ADUBO_FOLIAR)
                .id((long) 44).description("COlheita amarela").build();
        Product product = Product.builder().id((long)88).dose(77.33).name("Oleo vegetal")
                .target(target).unit(ProductUnit.L).build();
        assertThat(product.getTarget()).isEqualTo(target);
        assertThat(product.getTarget().getDescription()).isEqualTo("COlheita amarela");
        assertThat(product.getTarget().getId()).isEqualTo((long)44);
        assertThat(product.getUnit().getDescription()).isEqualTo("L");
        assertThat(product.getId()).isEqualTo((long)88);
        assertThat(product.getName()).isEqualTo("Oleo Vegetal");
        assertThat(product.getDose()).isEqualTo(77.33);
    }

    @Test
    public void equalsFalseProductTest(){
        Target target = Target.builder().category(TargetCategory.ADUBO_FOLIAR)
                .id((long) 44).description("COlheita amarela").build();
        Product product = Product.builder().id((long)88).dose(33.33).name("Oleo vegetal")
                .target(target).unit(ProductUnit.L).build();
        assertThat(this.product.equals(product)).isFalse();
    }

    @Test
    public void equalsTrueProductTest(){
        Target target = Target.builder().category(TargetCategory.ADUBO_FOLIAR)
                .id((long) 44).description("COlheita amarela").build();
        Product product = Product.builder().id((long)88).dose(33.33).name("Oleo vegetal")
                .target(target).unit(ProductUnit.L).build();
        this.product = product;
        assertThat(this.product.equals(product)).isTrue();
    }

    @Test
    public void hashCodeTrueProductTest() {
        Target target = Target.builder().category(TargetCategory.ADUBO_FOLIAR)
                .id((long) 44).description("COlheita amarela").build();
        Product product = Product.builder().id((long)88).dose(33.33).name("Oleo vegetal")
                .target(target).unit(ProductUnit.L).build();
        int value = product.hashCode();
        assertThat(product.hashCode()).isEqualTo(value);
    }

    @Test
    public void hashCodeFalseProductTest() {
        Target target = Target.builder().category(TargetCategory.ADUBO_FOLIAR)
                .id((long) 44).description("COlheita amarela").build();
        Product product = Product.builder().id((long)88).dose(33.33).name("Oleo vegetal")
                .target(target).unit(ProductUnit.L).build();
        int value = product.hashCode();
        value++;
        assertThat(product.hashCode()==value).isFalse();
    }

    @Test (expected = ConstraintViolationException.class)
    public  void setDoseNegativeExceptionTest() {
        when(this.productRepository.save(any(Product.class)))
                .thenThrow(ConstraintViolationException.class);
        this.product.setDose(-233.22);
        this.productRepository.save(this.product);
    }

    @Test (expected = ConstraintViolationException.class)
    public  void setNameExceptionNameLessThan5ProductTest() {
        when(this.productRepository.save(any(Product.class))).thenThrow(ConstraintViolationException.class);
        this.product.setName("test");
        this.productRepository.save(this.product);
    }


    @Test (expected = ConstraintViolationException.class)
    public  void setNameExceptionNameBigThan50ProductTest() {
        when(this.productRepository.save(any(Product.class))).thenThrow(ConstraintViolationException.class);
        this.product.setName("123456789-123456789-123456789-123456789-123456789-1");
        this.productRepository.save(this.product);
    }

}