package br.edu.utfpr.cp.emater.midmipsystem.entity.base;


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
public class FarmerTest {

    private Farmer farmer;

    @Test
    public void constructorFarmerTest(){
        this.farmer = Farmer.builder().id((long)1).name("Tio João").build();
    }

    @Test
    public  void getAndSetIdFarmerTest() {
        Farmer farmerTest = new Farmer();
        farmerTest.setId((long)10);
        assertThat(farmerTest.getId()).isEqualTo((long)10);
    }

    @Test
    public  void getAndSetIdNameFarmerTest() {
        Farmer farmerTest = new Farmer();
        farmerTest.setName("ANCLE Joe");
        assertThat(farmerTest.getName()).isEqualToIgnoringCase("Ancle Joe");
    }

}
