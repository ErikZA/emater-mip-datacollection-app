package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;


import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.GrowthPhase;
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
public class GrowthPhaseTest {


    @Test
    public  void getAndSetNameGrowthPhaseTest() {
        GrowthPhase growthPhaseV1 = GrowthPhase.V1;
        GrowthPhase growthPhaseV2 = GrowthPhase.V2;
        growthPhaseV1.setDescription("V1");
        assertThat(growthPhaseV1.getDescription()).isEqualToIgnoringCase("V1");
        assertThat(growthPhaseV2.getDescription()).isEqualToIgnoringCase("V2");
    }
}
