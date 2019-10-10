package br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TargetCategoryTest {

    @Test
    public void getDescription() {
        TargetCategory targetCategory = TargetCategory.HERBICIDA;
        TargetCategory targetCategory1 = TargetCategory.INSETICIDA;
        TargetCategory targetCategory2 = TargetCategory.INCETICIDA_BIOLOGICO;
        TargetCategory targetCategory3 = TargetCategory.AGENTE_BIOLOGICO;
        TargetCategory targetCategory4 = TargetCategory.FUNGICIDA;
        TargetCategory targetCategory5 = TargetCategory.ADJUVANTE;
        TargetCategory targetCategory6 = TargetCategory.ADUBO_FOLIAR;
        TargetCategory targetCategory7 = TargetCategory.OUTROS;
        TargetCategory targetCategory8 = TargetCategory.SAL_COMUM;
        targetCategory.setDescription("Herbicida");
        assertThat(targetCategory.getDescription()).isEqualTo("Herbicida");
        assertThat(targetCategory1.getDescription()).isEqualTo("Inseticida");
        assertThat(targetCategory2.getDescription()).isEqualTo("Inseticida Biológico");
        assertThat(targetCategory3.getDescription()).isEqualTo("Agente Biológico");
        assertThat(targetCategory4.getDescription()).isEqualTo("Fungicida");
        assertThat(targetCategory5.getDescription()).isEqualTo("Adjuvante");
        assertThat(targetCategory6.getDescription()).isEqualTo("Adubo Foliar");
        assertThat(targetCategory7.getDescription()).isEqualTo("Outros");
        assertThat(targetCategory8.getDescription()).isEqualTo("Sal Comum");
    }
}