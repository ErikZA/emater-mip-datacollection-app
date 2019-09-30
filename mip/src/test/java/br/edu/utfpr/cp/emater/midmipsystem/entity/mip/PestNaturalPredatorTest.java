package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;


import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PestNaturalPredatorTest {

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
        assertThat(pestNaturalPredatorTest.getUsualName()).isEqualTo("Bicho da Seda");
    }



}
