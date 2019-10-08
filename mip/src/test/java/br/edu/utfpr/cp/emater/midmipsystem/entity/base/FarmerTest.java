package br.edu.utfpr.cp.emater.midmipsystem.entity.base;


import br.edu.utfpr.cp.emater.midmipsystem.repository.base.FarmerRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FarmerTest {

    private Farmer farmer;

    private FarmerRepository farmerRepository= mock(FarmerRepository.class);

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

    @Test (expected = ConstraintViolationException.class)
    public  void setNameExceptionNameLessThan5MacroFarmerTest() {
        this.farmer = Farmer.builder().id((long) 22).name("").build();
        when(this.farmerRepository.save(this.farmer)).thenThrow(ConstraintViolationException.class);
        this.farmer.setName("test");
        this.farmerRepository.save(this.farmer);
    }


    @Test (expected = ConstraintViolationException.class)
    public  void setNameExceptionNameBigThan50MacroFarmerTest() {
        this.farmer = Farmer.builder().id((long) 22).name("").build();
        when(this.farmerRepository.save(this.farmer)).thenThrow(ConstraintViolationException.class);
        this.farmer.setName("123456789-123456789-123456789-123456789-123456789-1");
        this.farmerRepository.save(this.farmer);
    }
}
