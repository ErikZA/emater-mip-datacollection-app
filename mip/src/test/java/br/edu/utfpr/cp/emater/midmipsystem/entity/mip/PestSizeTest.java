package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PestSizeTest {


    @Test
    public  void getAndSetNamePestSizeTest() {
        PestSize PestSizeSmaller = PestSize.SMALLER_15CM;
        PestSize PestSizeGreater = PestSize.GREATER_15CM;
        PestSizeGreater.setName("> 15 cm");
        assertThat(PestSizeSmaller.getName()).isEqualToIgnoringCase("< 15 cm");
        assertThat(PestSizeGreater.getName()).isEqualToIgnoringCase("> 15 cm");
    }
}
