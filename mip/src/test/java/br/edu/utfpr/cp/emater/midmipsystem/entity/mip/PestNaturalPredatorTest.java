package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;


import br.edu.utfpr.cp.emater.midmipsystem.repository.mip.PestNaturalPredatorRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PestNaturalPredatorTest {

    private PestNaturalPredatorRepository pestNaturalPredatorRepository = mock(PestNaturalPredatorRepository.class);
    private PestNaturalPredator pestNaturalPredator;

    @Test
    public void getAndSetIdPestNaturalPredatorTest(){
        PestNaturalPredator pestNaturalPredatorTest = new PestNaturalPredator();
        pestNaturalPredatorTest.setId((long)2);
        assertThat(pestNaturalPredatorTest.getId()).isEqualTo((long)2);
    }

    @Test
    public void getAndSetUsualNamePestNaturalPredatorTest(){
        PestNaturalPredator pestNaturalPredatorTest = new PestNaturalPredator();
        pestNaturalPredatorTest.setUsualName("Bicho da SEDA");
        assertThat(pestNaturalPredatorTest.getUsualName()).isEqualTo("Bicho Da Seda");
    }

    @Test
    public void constructorTestPestNaturalPredatorTest(){
       this.pestNaturalPredator = PestNaturalPredator.builder().id((long)3)
               .usualName("BORBOLETA AMARELA").build();
        assertThat(pestNaturalPredator.getId()).isEqualTo((long)3);
        assertThat(pestNaturalPredator.getUsualName()).isEqualTo("Borboleta Amarela");
    }

    @Test
    public void equalsFalsePestNaturalPredatorTest() {
        PestNaturalPredator pestNaturalPredator = PestNaturalPredator.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        this.pestNaturalPredator = PestNaturalPredator.builder().id((long)55).usualName("Vespa").build();
                assertThat(this.pestNaturalPredator.equals(pestNaturalPredator)).isFalse();
    }

    @Test
    public void equalsTruePestNaturalPredatorTest() {
        PestNaturalPredator pestNaturalPredator = PestNaturalPredator.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        this.pestNaturalPredator = pestNaturalPredator;
        assertThat(this.pestNaturalPredator.equals(pestNaturalPredator)).isTrue();
    }

    @Test
    public void hashCodeTrueNaturalPredatorTest() {
        PestNaturalPredator pestNaturalPredator = PestNaturalPredator.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        int value = pestNaturalPredator.hashCode();
        assertThat(pestNaturalPredator.hashCode()).isEqualTo(value);
    }

    @Test
    public void hashCodeFalseNaturalPredatorTest() {
        PestNaturalPredator pestNaturalPredator = PestNaturalPredator.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        int value = pestNaturalPredator.hashCode();
        value++;
        assertThat(pestNaturalPredator.hashCode()==value).isFalse();
    }

    @Test (expected = ConstraintViolationException.class)
    public  void setNameExceptionNameLessThan5PestNaturalPredatorTest() {
        this.pestNaturalPredator = PestNaturalPredator.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        when(this.pestNaturalPredatorRepository.save(this.pestNaturalPredator))
                .thenThrow(ConstraintViolationException.class);
        this.pestNaturalPredator.setUsualName("test");
        this.pestNaturalPredatorRepository.save(this.pestNaturalPredator);
    }


    @Test (expected = ConstraintViolationException.class)
    public  void setNameExceptionNameBigThan50PestNaturalPredatorTest() {
        this.pestNaturalPredator = PestNaturalPredator.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        when(this.pestNaturalPredatorRepository.save(this.pestNaturalPredator))
                .thenThrow(ConstraintViolationException.class);
        this.pestNaturalPredator.setUsualName("123456789-123456789-123456789-123456789-123456789-1");
        this.pestNaturalPredatorRepository.save(this.pestNaturalPredator);
    }

}
