package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;


import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Supervisor;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mip.PestDiseaseRepository;
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
public class PestDiseaseTest {

    private PestDisease pestDisease;
    private PestDiseaseRepository pestDiseaseRepository = mock(PestDiseaseRepository.class);

    @Test
    public void getAndSetIdPestDiseaseTest(){
        PestDisease pestDisease = new PestDisease();
        pestDisease.setId((long)2);
        assertThat(pestDisease.getId()).isEqualTo((long)2);
    }

    //Não normaliza o nome.
    @Test
    public void getAndSetUsualNamePestDiseaseTest(){
        PestDisease pestDisease = new PestDisease();
        pestDisease.setUsualName("Bicho da SEDA");
        assertThat(pestDisease.getUsualName()).isEqualTo("Bicho da SEDA");
    }

    //Não normaliza o nome.
    @Test
    public void constructorTestPestDiseaseTest(){
        this.pestDisease = PestDisease.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        assertThat(pestDisease.getId()).isEqualTo((long)3);
        assertThat(pestDisease.getUsualName()).isEqualTo("BORBOLETA AMARELA");
    }

    @Test
    public void equalsFalsePestDiseaseTest() {
        PestDisease pestDisease = PestDisease.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        this.pestDisease = PestDisease.builder().id((long)55).usualName("Vespa").build();
        assertThat(this.pestDisease.equals(pestDisease)).isFalse();
    }

    @Test
    public void equalsTruePestDiseaseTest() {
        PestDisease pestDisease = PestDisease.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        this.pestDisease = pestDisease;
        assertThat(this.pestDisease.equals(pestDisease)).isTrue();
    }

    @Test
    public void hashCodeTruePestDiseaseTest() {
        PestDisease pestDisease = PestDisease.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        int value = pestDisease.hashCode();
        assertThat(pestDisease.hashCode()).isEqualTo(value);
    }

    @Test
    public void hashCodeFalsePestDiseaseTest() {
        PestDisease pestDisease = PestDisease.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        int value = pestDisease.hashCode();
        value++;
        assertThat(pestDisease.hashCode()==value).isFalse();
    }

    @Test (expected = ConstraintViolationException.class)
    public  void setNameExceptionNameLessThan5PestDiseaseTest() {
        this.pestDisease = PestDisease.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        when(this.pestDiseaseRepository.save(this.pestDisease)).thenThrow(ConstraintViolationException.class);
        this.pestDisease.setUsualName("test");
        this.pestDiseaseRepository.save(this.pestDisease);
    }


    @Test (expected = ConstraintViolationException.class)
    public  void setNameExceptionNameBigThan50PestDiseaseTest() {
        this.pestDisease = PestDisease.builder().id((long)3)
                .usualName("BORBOLETA AMARELA").build();
        when(this.pestDiseaseRepository.save(this.pestDisease)).thenThrow(ConstraintViolationException.class);
        this.pestDisease.setUsualName("123456789-123456789-123456789-123456789-123456789-1");
        this.pestDiseaseRepository.save(this.pestDisease);
    }

}
