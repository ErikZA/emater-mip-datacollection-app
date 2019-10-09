package br.edu.utfpr.cp.emater.midmipsystem.entity.mid;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AsiaticRustTypesSporeCollectorTest {

    @Test
    public void getAndSetDescriptionTest() {
        AsiaticRustTypesSporeCollector asiaticRustTypesSporeCollector = AsiaticRustTypesSporeCollector.NO_RUST_SPORES;
        AsiaticRustTypesSporeCollector asiaticRustTypesSporeCollector1 = AsiaticRustTypesSporeCollector.NO_RUST_SPORES_NO_FEASIBILITY_TEST;
        AsiaticRustTypesSporeCollector asiaticRustTypesSporeCollector2 = AsiaticRustTypesSporeCollector.UNFEASIBLE_SPORES_AFTER_TEST;
        AsiaticRustTypesSporeCollector asiaticRustTypesSporeCollector3 = AsiaticRustTypesSporeCollector.FEASIBLE_SPORES_GROUPED;
        AsiaticRustTypesSporeCollector asiaticRustTypesSporeCollector4 = AsiaticRustTypesSporeCollector.FEASIBLE_SPORES_ISOLATED;
        asiaticRustTypesSporeCollector.setDescription("Sem Esporos de Ferrugem");
        assertThat(asiaticRustTypesSporeCollector.getDescription()).isEqualTo("Sem Esporos de Ferrugem");
        assertThat(asiaticRustTypesSporeCollector1.getDescription()).isEqualTo("Com Esporos - Mas, sem testar viabilidade");
        assertThat(asiaticRustTypesSporeCollector2.getDescription()).isEqualTo("Com Esporos - Mas, inviáveis após teste");
        assertThat(asiaticRustTypesSporeCollector3.getDescription()).isEqualTo("Com Esporos Viáveis - Aglomerados");
        assertThat(asiaticRustTypesSporeCollector4.getDescription()).isEqualTo("Com Esporos Viáveis - Isolados");

    }

}