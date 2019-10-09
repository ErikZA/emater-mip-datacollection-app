package br.edu.utfpr.cp.emater.midmipsystem;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        br.edu.utfpr.cp.emater.midmipsystem.entity.base.FieldTest.class,
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
        br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSampleNaturalPredatorOccurrenceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSamplePestDiseaseOccurrenceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSamplePestOccurrenceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSampleTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.survey.SurveyTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.survey.HarvestTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.survey.DateDataTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.survey.LocationDataTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.survey.ProductivityDataTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.survey.QuestionDataTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.survey.SizeDataTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.mid.BladeReadingResponsibleEntityTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.mid.AsiaticRustTypesLeafInspectionTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.mid.AsiaticRustTypesSporeCollectorTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.mid.MIDSampleFungicideApplicationOccurrenceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.mid.MIDSampleLeafInspectionOccurrenceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.mid.MIDSampleSporeCollectorOccurrenceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.mid.MIDRustSampleTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.mid.BladeReadingResponsiblePersonTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.ProductTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.ProductUnitTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.PulverisationOperationOccurrenceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.PulverisationOperationTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.TargetCategoryTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.TargetTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.base.FieldServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.mip.MIPSampleServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.mip.PestDiseaseServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.mip.PestNaturalPredatorServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.mip.PestServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.survey.HarvestServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.survey.SurveyServiceTest.class})
public class MipApplicationTest {

}
