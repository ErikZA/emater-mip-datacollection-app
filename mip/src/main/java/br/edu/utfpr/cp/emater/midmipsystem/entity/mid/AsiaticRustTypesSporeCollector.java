package br.edu.utfpr.cp.emater.midmipsystem.entity.mid;

public enum AsiaticRustTypesSporeCollector {
    NO_RUST_SPORES("Sem Esporos de Ferrugem"),
    NO_RUST_SPORES_NO_FEASIBILITY_TEST("Com Esporos - Mas, sem testar viabilidade"),
    UNFEASIBLE_SPORES_AFTER_TEST("Com Esporos - Mas, inviáveis após teste"),
    FEASIBLE_SPORES_ISOLATED("Com Esporos Viáveis - Isolados"),
    FEASIBLE_SPORES_GROUPED("Com Esporos Viáveis - Aglomerados");

    private String description;

    AsiaticRustTypesSporeCollector(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
