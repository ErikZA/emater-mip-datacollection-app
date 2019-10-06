package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;


import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

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
