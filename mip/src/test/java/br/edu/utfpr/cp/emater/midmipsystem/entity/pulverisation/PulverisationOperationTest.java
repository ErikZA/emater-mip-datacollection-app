package br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.MIDRustSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.GrowthPhase;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.repository.pulverisation.PulverisationOperationRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PulverisationOperationTest {

    private PulverisationOperation pulverisationOperation;
    private Product product;
    private Target target;
    private PulverisationOperationRepository pulverisationOperationRepository = mock(PulverisationOperationRepository.class);
    private Date date;
    private Survey survey;

    @Before
    public void setUp() throws ParseException {
        this.date= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2013");
        this.target = Target.builder().category(TargetCategory.ADJUVANTE)
                .id((long) 33).description("Fortificador NormaLISE CASE").build();
        this.product = Product.builder().id((long)44).dose(2.1).name("Carvao Vegetal")
                .target(this.target).unit(ProductUnit.KG).build();
        this.survey= Survey.builder().id((long) 22).seedName("test harves").rustResistant(true).build();
        this.pulverisationOperation = PulverisationOperation.builder().id((long) 44).caldaVolume(22.22)
                .growthPhase(GrowthPhase.V1).operationCostCurrency(44.44).sampleDate(this.date).soyaPrice(11.11)
                .survey(this.survey).build();
    }

    @Test
    public void getAndSetIdTest() {
        assertThat(this.pulverisationOperation.getId()).isEqualTo((long)44);
        this.pulverisationOperation.setId((long) 55);
        assertThat(this.pulverisationOperation.getId()).isEqualTo((long)55);
    }

    @Test
    public void getAndSetSurveyTest() {
        Survey survey = Survey.builder().id((long) 99).rustResistant(false).seedName("This Test Case").build();
        assertThat(this.pulverisationOperation.getSurvey()).isEqualTo(this.survey);
        this.pulverisationOperation.setSurvey(survey);
        assertThat(this.pulverisationOperation.getSurvey()).isEqualTo(survey);
    }

    @Test
    public void getAndSetSampleDateTest() throws ParseException {
        Date date= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-12-2003");
        assertThat(this.pulverisationOperation.getSampleDate()).isEqualTo(this.date);
        this.pulverisationOperation.setSampleDate(date);
        assertThat(this.pulverisationOperation.getSampleDate()).isEqualTo(date);
    }

    @Test
    public void getAndSetGrowthPhaseTest() {
        assertThat(this.pulverisationOperation.getGrowthPhase()).isEqualTo(GrowthPhase.V1);
        this.pulverisationOperation.setGrowthPhase(GrowthPhase.R4);
        assertThat(this.pulverisationOperation.getGrowthPhase()).isEqualTo(GrowthPhase.R4);
    }

    @Test
    public void getAndSetCaldaVolumeTest() {
        this.pulverisationOperation.setCaldaVolume(34.33);
        assertThat(this.pulverisationOperation.getCaldaVolume()).isEqualTo(34.33);
    }

    @Test
    public void getAndSetDaysAfterEmergenceTest() {
        this.pulverisationOperation.setDaysAfterEmergence(23);
        assertThat(this.pulverisationOperation.getDaysAfterEmergence()).isEqualTo(23);
    }

    @Test
    public void getAndSetSoyaPriceTest() {
        this.pulverisationOperation.setSoyaPrice(23.9);
        assertThat(this.pulverisationOperation.getSoyaPrice()).isEqualTo(23.9);
    }

    @Test
    public void getAndSetOperationCostCurrencyTest() {
        this.pulverisationOperation.setOperationCostCurrency(0.9);
        assertThat(this.pulverisationOperation.getOperationCostCurrency()).isEqualTo(0.9);
    }

    @Test
    public void getAndSetTotalOperationCostCurrencyTest() {
        this.pulverisationOperation.setTotalOperationCostCurrency(7.9);
        assertThat(this.pulverisationOperation.getTotalOperationCostCurrency()).isEqualTo(7.9);
    }

    @Test
    public void getAndSetOperationCostQtyTest() {
        this.pulverisationOperation.setOperationCostQty(27.9);
        assertThat(this.pulverisationOperation.getOperationCostQty()).isEqualTo(27.9);
    }

    @Test
    public void getAndSetTotalOperationCostQtyTest() {
        this.pulverisationOperation.setTotalOperationCostQty(127.9);
        assertThat(this.pulverisationOperation.getTotalOperationCostQty()).isEqualTo(127.9);
    }

    @Test
    public void getAndSetOperationOccurrences() {
        Set<PulverisationOperationOccurrence> occurrences = new HashSet<>();
        Target target = Target.builder().category(TargetCategory.ADUBO_FOLIAR)
                .id((long) 44).description("COlheita amarela").build();
        Product product = Product.builder().id((long)88).dose(77.33).name("Oleo vegetal")
                .target(target).unit(ProductUnit.L).build();
        PulverisationOperationOccurrence pulverisationOperationOccurrence = PulverisationOperationOccurrence.builder()
                .product(product).productPrice(22.22).target(target).build();
        occurrences.add(pulverisationOperationOccurrence);
        this.pulverisationOperation.setOperationOccurrences(occurrences);
        assertThat(this.pulverisationOperation.getOperationOccurrences()).containsExactly(pulverisationOperationOccurrence);
    }

    @Test
    public void builder() {
    }

    @Test
    public void equalsFalsePulverisationOperationTest() throws ParseException {
        PulverisationOperation pulverisationOperation = PulverisationOperation.builder().id((long) 33)
                .operationCostCurrency(44.44).sampleDate(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-12-2993"))
                .survey(Survey.builder().id((long) 222).seedName("test Build").rustResistant(true).build()).build();
        assertThat(this.pulverisationOperation.equals(pulverisationOperation)).isFalse();
    }

    @Test
    public void equalsTrueSurveyTest() throws ParseException {
        PulverisationOperation pulverisationOperation = PulverisationOperation.builder().id((long) 33)
                .operationCostCurrency(44.44).sampleDate(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-12-2993"))
                .survey(Survey.builder().id((long) 222).seedName("test Build").rustResistant(true).build()).build();
        this.pulverisationOperation = pulverisationOperation;
        assertThat(this.pulverisationOperation.equals(pulverisationOperation)).isTrue();
    }

    @Test
    public void hashCodeTrueSurveyTest() throws ParseException {
        PulverisationOperation pulverisationOperation = PulverisationOperation.builder().id((long) 33)
                .operationCostCurrency(44.44).sampleDate(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-12-2993"))
                .survey(Survey.builder().id((long) 222).seedName("test Build").rustResistant(true).build()).build();
        int value = pulverisationOperation.hashCode();
        assertThat(pulverisationOperation.hashCode()).isEqualTo(value);
    }

    @Test
    public void hashCodeFalseSurveyTest() throws ParseException {
        PulverisationOperation pulverisationOperation = PulverisationOperation.builder().id((long) 33)
                .operationCostCurrency(44.44).sampleDate(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-12-2993"))
                .survey(Survey.builder().id((long) 222).seedName("test Build").rustResistant(true).build()).build();
        int value = pulverisationOperation.hashCode();
        value++;
        assertThat(pulverisationOperation.hashCode()==value).isFalse();
    }

    @Test
    public void addOperationOccurrenceTest() {
        assertThat(this.pulverisationOperation.addOperationOccurrence(this.product, 10.1,this.target)).isTrue();
    }


    @Test
    public void addOperationOccurrenceCheckValueOfTotalOperationCostCurrencyTest() {
        this.pulverisationOperation.setSoyaPrice(2.2);
        assertThat(this.pulverisationOperation.addOperationOccurrence(this.product, 10.1,this.target)).isTrue();
        assertThat(this.pulverisationOperation.getOperationOccurrences().stream().findFirst().get().getProductCostQty()).isBetween(9.639, 9.641);
    }

    @Test
    public void addOperationOccurrenceCheckValueOfProductCostQtyTest() {
        Set<PulverisationOperationOccurrence> occurrences = new HashSet<>();
        Target target = Target.builder().category(TargetCategory.ADUBO_FOLIAR)
                .id((long) 44).description("COlheita amarela").build();
        Product product = Product.builder().id((long)88).dose(2.3).name("Oleo vegetal")
                .target(target).unit(ProductUnit.L).build();
        PulverisationOperationOccurrence pulverisationOperationOccurrence = PulverisationOperationOccurrence.builder()
                .product(product).productPrice(3.2).target(target).build();
        occurrences.add(pulverisationOperationOccurrence);
        this.pulverisationOperation.setOperationOccurrences(occurrences);
        this.pulverisationOperation.setOperationCostCurrency(44.44);
        assertThat(this.pulverisationOperation.addOperationOccurrence(this.product, 10.1,this.target)).isTrue();
        assertThat(this.pulverisationOperation.getTotalOperationCostCurrency()).isBetween(73.00, 73.01);
    }

    @Test
    public void addOperationOccurrenceCheckValueOfOperationCostQtyTest() {
        this.pulverisationOperation.setSoyaPrice(2.2);
        this.pulverisationOperation.setOperationCostCurrency(44.44);
        assertThat(this.pulverisationOperation.addOperationOccurrence(this.product, 10.1,this.target)).isTrue();
        assertThat(this.pulverisationOperation.getOperationCostQty()).isBetween(20.199,20.2);
    }

    @Test
    public void addOperationOccurrenceCheckValueOfTotalOperationCostQtyTest() {
        Set<PulverisationOperationOccurrence> occurrences = new HashSet<>();
        Target target = Target.builder().category(TargetCategory.ADUBO_FOLIAR)
                .id((long) 44).description("COlheita amarela").build();
        Product product = Product.builder().id((long)88).dose(2.3).name("Oleo vegetal")
                .target(target).unit(ProductUnit.L).build();
        PulverisationOperationOccurrence pulverisationOperationOccurrence = PulverisationOperationOccurrence.builder()
                .product(product).productPrice(3.2).target(target).build();
        occurrences.add(pulverisationOperationOccurrence);
        pulverisationOperationOccurrence.setProductCostQty(1.2);
        this.pulverisationOperation.setOperationOccurrences(occurrences);
        this.pulverisationOperation.setSoyaPrice(2.2);
        this.pulverisationOperation.setOperationCostCurrency(44.44);
        assertThat(this.pulverisationOperation.addOperationOccurrence(this.product, 10.1,this.target)).isTrue();
        assertThat(this.pulverisationOperation.getTotalOperationCostQty()).isBetween(31.04,31.048);
    }

    @Test (expected = ConstraintViolationException.class)
    public  void setDateNullExceptionTest() {
        when(this.pulverisationOperationRepository.save(any(PulverisationOperation.class)))
                .thenThrow(ConstraintViolationException.class);
        this.pulverisationOperation.setSampleDate(null);
        this.pulverisationOperationRepository.save(this.pulverisationOperation);
    }
}