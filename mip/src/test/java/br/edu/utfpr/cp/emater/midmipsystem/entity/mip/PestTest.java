package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Supervisor;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mip.PestNaturalPredatorRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mip.PestRepository;
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
public class PestTest {

    private PestRepository pestRepository= mock(PestRepository.class);
    private Pest pest;

    @Test
    public void getAndSetIdPestTest(){
        Pest pest = new Pest();
        pest.setId((long) 3);
        assertThat(pest.getId()).isEqualTo((long)3);
    }

    @Test
    public void getAndSetUsualNamePestTest(){
        Pest pest = new Pest();
        pest.setUsualName("LARGATA AmArela");
        assertThat(pest.getUsualName()).isEqualTo("Largata Amarela");
    }

    @Test
    public void getAndSetScientificNamePestTest(){
        Pest pest = new Pest();
        pest.setScientificName("Hora Pronobilis FO");
        assertThat(pest.getScientificName()).isEqualTo("Hora Pronobilis FO");
    }

    @Test
    public void getAndSetPestSizePestTest(){
        Pest pest = new Pest();
        pest.setPestSize(PestSize.ADULT);
        assertThat(pest.getPestSize()).isEqualTo(PestSize.ADULT);
    }

    @Test
    public void getAndSetPestSizeNamePestTest(){
        Pest pest = new Pest();
        pest.setPestSize(PestSize.ADULT);
        assertThat(pest.getPestSizeName()).isEqualTo("Adultos");
    }

    @Test
    public void constructorPestTest(){
        Pest pest = Pest.builder().id((long)5).pestSize(PestSize.THIRD_TO_FIFTH_INSTAR)
                .scientificName("MancicoPloris").usualName("Pulgão").build();
        assertThat(pest.getPestSizeName()).isEqualTo("3. ao 5. Instar");
        assertThat(pest.getScientificName()).isEqualTo("MancicoPloris");
        assertThat(pest.getUsualName()).isEqualTo("Pulgão");
        assertThat(pest.getId()).isEqualTo((long)5);
    }

    @Test
    public void equalsFalsePestTest() {
        Pest pest = Pest.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        this.pest = Pest.builder().id((long)55).usualName("Vespa").build();
        assertThat(this.pest.equals(pest)).isFalse();
    }

    @Test
    public void equalsTruePestTest() {
        Pest pest = Pest.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        this.pest = pest;
        assertThat(this.pest.equals(pest)).isTrue();
    }

    @Test
    public void hashCodeTruePestTest() {
        Pest pest = Pest.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        int value = pest.hashCode();
        assertThat(pest.hashCode()).isEqualTo(value);
    }

    @Test
    public void hashCodeFalsePestTest() {
        Pest pest = Pest.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        int value = pest.hashCode();
        value++;
        assertThat(pest.hashCode()==value).isFalse();
    }

    @Test (expected = ConstraintViolationException.class)
    public  void setNameExceptionNameLessThan5PestTest() {
        Pest pest = Pest.builder().id((long)5).pestSize(PestSize.THIRD_TO_FIFTH_INSTAR)
                .scientificName("MancicoPloris").usualName("Pulgão").build();
        when(this.pestRepository.save(pest)).thenThrow(ConstraintViolationException.class);
        pest.setUsualName("test");
        this.pestRepository.save(pest);
    }


    @Test (expected = ConstraintViolationException.class)
    public  void setNameExceptionNameBigThan50PestTest() {
        Pest pest = Pest.builder().id((long)5).pestSize(PestSize.THIRD_TO_FIFTH_INSTAR)
                .scientificName("MancicoPloris").usualName("Pulgão").build();
        when(this.pestRepository.save(pest)).thenThrow(ConstraintViolationException.class);
        pest.setUsualName("123456789-123456789-123456789-123456789-123456789-1");
        this.pestRepository.save(pest);
    }



}
