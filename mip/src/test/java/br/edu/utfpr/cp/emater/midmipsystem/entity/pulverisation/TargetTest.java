package br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation;

import br.edu.utfpr.cp.emater.midmipsystem.repository.pulverisation.TargetRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TargetTest {

    private Target target;
    private TargetRepository targetRepository = mock(TargetRepository.class);
    @Before
    public void setUp() {
        this.target = Target.builder().category(TargetCategory.ADJUVANTE)
                .id((long) 33).description("Fortificador NormaLISE CASE").build();
    }

    @Test
    public void descriptionTest() {
        this.target.description(this.target.getDescription());
        assertThat(this.target.getDescription()).isEqualTo("Fortificador Normalise Case");
    }

    @Test
    public void getAndIdTest() {
        assertThat(this.target.getId()).isEqualTo((long)33);
        this.target.setId((long) 55);
        assertThat(this.target.getId()).isEqualTo((long)55);
    }

    @Test
    public void getAndSetDescriptionTest() {
        this.target.description(this.target.getDescription());
        assertThat(this.target.getDescription()).isEqualTo("Fortificador Normalise Case");
        this.target.setDescription("Fortificador nOT NORmalise Case");
        assertThat(this.target.getDescription()).isEqualTo("Fortificador nOT NORmalise Case");
    }

    @Test
    public void getAndSetCategoryTest() {
        assertThat(this.target.getCategory()).isEqualTo(TargetCategory.ADJUVANTE);
        this.target.setCategory(TargetCategory.FUNGICIDA);
        assertThat(this.target.getCategory()).isEqualTo(TargetCategory.FUNGICIDA);
        assertThat(this.target.getCategory().getDescription()).isEqualTo("Fungicida");
    }

    @Test
    public void builderTest() {
        this.target = Target.builder().category(TargetCategory.OUTROS)
                .id((long) 233).description("Fortificador CASE").build();
        assertThat(this.target.getDescription()).isEqualTo("Fortificador CASE");
        assertThat(this.target.getCategory().getDescription()).isEqualTo("Outros");
        assertThat(this.target.getId()).isEqualTo((long)233);
    }

    @Test (expected = ConstraintViolationException.class)
    public  void setNameExceptionNameLessThan5TargetTest() {
        when(this.targetRepository.save(any(Target.class))).thenThrow(ConstraintViolationException.class);
        this.target.setDescription("test");
        this.targetRepository.save(this.target);
    }


    @Test (expected = ConstraintViolationException.class)
    public  void setNameExceptionNameBigThan50TargetTest() {
        when(this.targetRepository.save(any(Target.class))).thenThrow(ConstraintViolationException.class);
        this.target.setDescription("123456789-123456789-123456789-123456789-123456789-1");
        this.targetRepository.save(this.target);
    }
    @Test
    public void equalsFalseTargetTest() throws ParseException {
        Target target = Target.builder().category(TargetCategory.OUTROS)
                .id((long) 233).description("Fortificador CASE").build();
        assertThat(this.target.equals(target)).isFalse();
    }

    @Test
    public void equalsTrueTargetTest() {
        Target target = Target.builder().category(TargetCategory.OUTROS)
                .id((long) 233).description("Fortificador CASE").build();
        this.target = target;
        assertThat(this.target.equals(target)).isTrue();
    }

    @Test
    public void hashCodeTrueTargetTest() {
        Target target = Target.builder().category(TargetCategory.OUTROS)
                .id((long) 233).description("Fortificador CASE").build();
        int value = target.hashCode();
        assertThat(target.hashCode()).isEqualTo(value);
    }

    @Test
    public void hashCodeFalseTargetTest() {
        Target target = Target.builder().category(TargetCategory.OUTROS)
                .id((long) 233).description("Fortificador CASE").build();
        int value = target.hashCode();
        value++;
        assertThat(target.hashCode()==value).isFalse();
    }
}