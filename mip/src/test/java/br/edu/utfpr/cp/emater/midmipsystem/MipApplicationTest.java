package br.edu.utfpr.cp.emater.midmipsystem;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({br.edu.utfpr.cp.emater.midmipsystem.entity.base.FieldTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.base.RegionTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.base.FarmerTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.base.StateTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegionTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.base.CityTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.base.SupervisorTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.mip.GrowthPhaseTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestDiseaseTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestNaturalPredatorTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestSizeTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.base.FieldServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.mip.MIPSampleServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.mip.PestDiseaseServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.mip.PestNaturalPredatorServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.mip.PestServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.survey.HarvestServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.survey.SurveyServiceTest.class})
public class MipApplicationTest {

}
