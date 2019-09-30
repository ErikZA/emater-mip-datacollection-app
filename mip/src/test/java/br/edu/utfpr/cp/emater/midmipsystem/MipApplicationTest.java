package br.edu.utfpr.cp.emater.midmipsystem;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


import static org.junit.Assume.assumeTrue;

@RunWith(Suite.class)
@Suite.SuiteClasses({br.edu.utfpr.cp.emater.midmipsystem.entity.base.FieldTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.base.RegionTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.base.FarmerTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.base.StateTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegionTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.base.CityTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.base.SupervisorTest.class})
public class MipApplicationTest {

}
