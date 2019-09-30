package br.edu.utfpr.cp.emater.midmipsystem.entity.base;


import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.State;
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
