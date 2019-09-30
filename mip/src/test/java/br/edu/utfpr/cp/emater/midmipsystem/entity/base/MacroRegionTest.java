package br.edu.utfpr.cp.emater.midmipsystem.entity.base;

import br.edu.utfpr.cp.emater.midmipsystem.service.base.MacroRegionRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MacroRegionTest {

    @Mock
    private MacroRegionRepository macroRegionRepository=null;

    private MacroRegion macroRegion;

    @Before
    public void setUp() {
        this.macroRegion = MacroRegion.builder().id((long)1).name("MACRO-CENTRO-OESTE").build();
    }

    @Test
    public  void getAndSetIdMacroRegionTest() {
        MacroRegion macroRegionTest = new MacroRegion();
        macroRegionTest.setId((long)10);
        assertThat(macroRegionTest.getId()).isEqualTo((long)10);
    }


    @Test
    public  void getAndSetIdNameMacroRegionTest() {
        MacroRegion macroRegionTest = new MacroRegion();
        macroRegionTest.setName("SULDESTE");
        assertThat(macroRegionTest.getName()).isEqualTo("Suldeste");
    }

    @Test (expected = ConstraintViolationException.class)
    public  void setNameExceptionNameLessThan5MacroRegionTest() {
        when(this.macroRegionRepository.save(this.macroRegion)).thenThrow(ConstraintViolationException.class);
        this.macroRegion.setName("test");
        this.macroRegionRepository.save(this.macroRegion);
    }


    @Test (expected = ConstraintViolationException.class)
    public  void setNameExceptionNameBigThan50MacroRegionTest() {
        when(this.macroRegionRepository.save(this.macroRegion)).thenThrow(ConstraintViolationException.class);
        this.macroRegion.setName("123456789-123456789-123456789-123456789-123456789-1");
        this.macroRegionRepository.save(this.macroRegion);
    }


    @Test
    public void macroRegiaoTestEqualsFalse() {
        MacroRegion macroRegionTest = MacroRegion.builder().name("MACRO-NORDESTE").build();
        assertThat(this.macroRegion.equals(macroRegionTest)).isFalse();
    }

    @Test
    public void macroRegionTestEqualsTrue() {
        MacroRegion macroRegionTest = this.macroRegion;
        assertThat(this.macroRegion.equals(macroRegionTest)).isTrue();
    }

    @Test
    public void macroRegionTestHashCodeTrue() {
        int value = this.macroRegion.hashCode();
        assertThat(this.macroRegion.hashCode()).isEqualTo(value);
    }


    @Test
    public void macroRegionTestHashCodeFalse() {
        int value = this.macroRegion.hashCode();
        value++;
        assertThat(this.macroRegion.hashCode()==value).isFalse();
    }



}
