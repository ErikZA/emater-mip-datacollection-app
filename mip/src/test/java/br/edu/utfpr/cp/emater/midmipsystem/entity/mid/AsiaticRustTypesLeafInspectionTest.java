package br.edu.utfpr.cp.emater.midmipsystem.entity.mid;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AsiaticRustTypesLeafInspectionTest {

    @Test
    public void getAndSetDescriptionTest() {
        AsiaticRustTypesLeafInspection semLesoes = AsiaticRustTypesLeafInspection.NO_VISIBLE_DAMAGE;
        AsiaticRustTypesLeafInspection comLesoesPlantas = AsiaticRustTypesLeafInspection.VISIBLE_DAMAGE_ISOLATED;
        AsiaticRustTypesLeafInspection comLesoesParte = AsiaticRustTypesLeafInspection.VISIBLE_DAMAGE_PARTIAL_CROP;
        AsiaticRustTypesLeafInspection comLesoesToda = AsiaticRustTypesLeafInspection.VISIBLE_DAMAGE_ALL_CROP;
        AsiaticRustTypesLeafInspection comDoenca = AsiaticRustTypesLeafInspection.NO_RUST_LIKELY_OTHER_DISESES;
        semLesoes.setDescription("Sem Lesões Visíveis");
        assertThat(semLesoes.getDescription()).isEqualTo("Sem Lesões Visíveis");
        assertThat(comLesoesParte.getDescription()).isEqualTo("Com Lesões Visíveis - em Parte da Lavoura");
        assertThat(comDoenca.getDescription()).isEqualTo("Sem Ferrugem, mas c/ sinais de Outras Doenças");
        assertThat(comLesoesToda.getDescription()).isEqualTo("Com Lesões Visíveis - em Toda a Lavoura");
        assertThat(comLesoesPlantas.getDescription()).isEqualTo("Com Lesões Visíveis - em Plantas Isoladas");
    }
}