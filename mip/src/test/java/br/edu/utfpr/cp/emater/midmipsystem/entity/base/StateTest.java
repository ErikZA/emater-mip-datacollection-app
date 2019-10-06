package br.edu.utfpr.cp.emater.midmipsystem.entity.base;


import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StateTest {

    @Test
    public  void getAndSetNameStateTeste() {
        State parana = State.PR;
        State santaCatarina = State.SC;
        State rioGrande = State.RS;
        State paranaTest = State.PR;
        paranaTest.setName("Paraná");
        assertThat(paranaTest.getName()).isEqualToIgnoringCase("Paraná");
        assertThat(parana.getName()).isEqualToIgnoringCase("Paraná");
        assertThat(santaCatarina.getName()).isEqualToIgnoringCase("Santa Catarina");
        assertThat(rioGrande.getName()).isEqualToIgnoringCase("Rio Grande do Sul");
    }
}
