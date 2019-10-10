package br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation;

import br.edu.utfpr.cp.emater.midmipsystem.repository.pulverisation.PulverisationOperationRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import java.util.HashSet;
import java.util.Set;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PulverisationOperationOccurrenceTest {

    private PulverisationOperationOccurrence pulverisationOperationOccurrence;
    private Product product;
    private Target target;
    private PulverisationOperationRepository pulverisationOperationRepository = mock(PulverisationOperationRepository.class);


    @Before
    public void setUp() {
        this.target = Target.builder().category(TargetCategory.ADJUVANTE)
                .id((long) 33).description("Fortificador NormaLISE CASE").build();
        this.product = Product.builder().id((long)44).dose(22.22).name("Carvao Vegetal")
                .target(this.target).unit(ProductUnit.KG).build();
        this.pulverisationOperationOccurrence = PulverisationOperationOccurrence.builder()
                .product(this.product).productPrice(22.22).target(this.target).build();
    }

    @Test
    public void setProductPriceTest() {
        this.pulverisationOperationOccurrence.setProductPrice(44.44);
        assertThat(this.pulverisationOperationOccurrence.getProductCostCurrency()).isBetween(987.456,987.457);
    }

    @Test(expected = RuntimeException.class)
    public void setProductPriceRuntimeExceptionTest() {
        this.pulverisationOperationOccurrence.setProduct(null);
        this.pulverisationOperationOccurrence.setProductPrice(44.44);
        Assertions.fail("RuntimeException it is not throws");
    }

    @Test
    public void getTargetCategoryDescriptionTest() {
        assertThat(this.pulverisationOperationOccurrence.getTargetCategoryDescription()).isEqualTo("Adjuvante");
    }

    @Test
    public void getTargetDescriptionTest() {
        assertThat(this.pulverisationOperationOccurrence.getTargetDescription()).isEqualTo("Fortificador NormaLISE CASE");
    }

    @Test
    public void getProductFormattedNameTest() {
        assertThat(this.pulverisationOperationOccurrence.getProductFormattedName())
                .isEqualTo("Carvao Vegetal - 22,22 (KG)");
    }

    @Test
    public void getAndSetProductTest() {
        Target target = Target.builder().category(TargetCategory.ADUBO_FOLIAR)
                .id((long) 44).description("COlheita amarela").build();
        Product product = Product.builder().id((long)88).dose(77.33).name("Oleo vegetal")
                .target(target).unit(ProductUnit.L).build();
        assertThat(this.pulverisationOperationOccurrence.getProduct()).isEqualTo(this.product);
        this.pulverisationOperationOccurrence.setProduct(product);
        assertThat(this.pulverisationOperationOccurrence.getProduct()).isEqualTo(product);
    }

    @Test
    public void getAndGetProductPriceTest() {
        assertThat(this.pulverisationOperationOccurrence.getProductPrice()).isEqualTo(22.22);
        this.pulverisationOperationOccurrence.setProductPrice(33.33);
        assertThat(this.pulverisationOperationOccurrence.getProductPrice()).isEqualTo(33.33);
    }

    @Test
    public void getAndSetProductCostCurrencyTest() {
        this.pulverisationOperationOccurrence.setProductCostCurrency(44.33);
        assertThat(this.pulverisationOperationOccurrence.getProductCostCurrency()).isEqualTo(44.33);
    }

    @Test
    public void getAndSetProductCostQtyTest() {
        this.pulverisationOperationOccurrence.setProductCostQty(44.33);
        assertThat(this.pulverisationOperationOccurrence.getProductCostQty()).isEqualTo(44.33);
    }

    @Test
    public void getAndSetTargetTest() {
        Target target = Target.builder().category(TargetCategory.ADUBO_FOLIAR)
                .id((long) 44).description("COlheita amarela").build();
        assertThat(this.pulverisationOperationOccurrence.getTarget()).isEqualTo(this.target);
        this.pulverisationOperationOccurrence.setTarget(target);
        assertThat(this.pulverisationOperationOccurrence.getTarget()).isEqualTo(target);
    }

    @Test
    public void builderTest() {
        this.pulverisationOperationOccurrence = new PulverisationOperationOccurrence();
        this.pulverisationOperationOccurrence = new PulverisationOperationOccurrence(this.product, 0, 0, 0,this.target);
        assertThat(this.pulverisationOperationOccurrence.getTarget()).isEqualTo(this.target);
        assertThat(this.pulverisationOperationOccurrence.getProduct()).isEqualTo(this.product);
        assertThat(this.pulverisationOperationOccurrence.getProductPrice()).isEqualTo(0);
        assertThat(this.pulverisationOperationOccurrence.getProductCostQty()).isEqualTo(0);
        assertThat(this.pulverisationOperationOccurrence.getProductCostQty()).isEqualTo(0);
        Target target = Target.builder().category(TargetCategory.ADUBO_FOLIAR)
                .id((long) 44).description("COlheita amarela").build();
        Product product = Product.builder().id((long)88).dose(77.33).name("Oleo vegetal")
                .target(target).unit(ProductUnit.L).build();
        PulverisationOperationOccurrence pulverisationOperationOccurrence = PulverisationOperationOccurrence.builder()
                .product(product).productPrice(22.22).target(target).build();
        assertThat(pulverisationOperationOccurrence.getTarget()).isEqualTo(target);
        assertThat(pulverisationOperationOccurrence.getProduct()).isEqualTo(product);
        assertThat(pulverisationOperationOccurrence.getProductPrice()).isEqualTo(22.22);
    }

    @Test (expected = ConstraintViolationException.class)
    public  void setProductPrinceNegativeExceptionTest() {
        Set<PulverisationOperationOccurrence> occurrences = new HashSet<>();
        PulverisationOperation pulverisationOperation = PulverisationOperation.builder().build();
        when(this.pulverisationOperationRepository.save(any(PulverisationOperation.class)))
                .thenThrow(ConstraintViolationException.class);
        this.pulverisationOperationOccurrence.setProductPrice(-233.22);
        pulverisationOperation.setOperationOccurrences(occurrences);
        this.pulverisationOperationRepository.save(pulverisationOperation);
    }

    @Test (expected = ConstraintViolationException.class)
    public  void setProductNullExceptionTest() {
        Set<PulverisationOperationOccurrence> occurrences = new HashSet<>();
        PulverisationOperation pulverisationOperation = PulverisationOperation.builder().build();
        when(this.pulverisationOperationRepository.save(any(PulverisationOperation.class)))
                .thenThrow(ConstraintViolationException.class);
        this.pulverisationOperationOccurrence.setProduct(null);
        occurrences.add(this.pulverisationOperationOccurrence);
        pulverisationOperation.setOperationOccurrences(occurrences);
        this.pulverisationOperationRepository.save(pulverisationOperation);
    }
}