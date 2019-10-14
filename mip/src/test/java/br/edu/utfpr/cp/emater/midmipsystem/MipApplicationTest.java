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
        br.edu.utfpr.cp.emater.midmipsystem.service.base.FarmerServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.base.CityServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.base.MacroRegionServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.base.LocalDateTimeConverterTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.base.FieldServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.base.RegionServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.base.SupervisorServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.mip.MIPSampleServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.mip.PestDiseaseServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.mip.PestNaturalPredatorServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.mip.PestServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.survey.HarvestServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.survey.SurveyServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.mid.BladeReadingResponsibleEntityServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.mid.MIDRustSampleServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.mid.BladeReadingResponsiblePersonServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.pulverisation.PulverisationOperationServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.pulverisation.ProductServiceTest.class,
        br.edu.utfpr.cp.emater.midmipsystem.service.pulverisation.TargetServiceTest.class})
public class MipApplicationTest {
    //packt entity/base - classe FieldTest- agregaria uma validação para supervisores e cidadess nulas
    // packt entity/base - classe SupervisorTest - é dificil de fazer uma comparação exata pois a ordem das cidades pode mudar dee acordo com a alocação em ememoria, alterando o resultado do teste.
    // packt entity/mip - classes PestDisease - pestDisease não normaliza o name - atraves do SETNAME
    // packt entity/mip - classes Pest- pestnão normaliza o name - atraves do SETNAME
    // packt service/mip - classe MIPSampleServiceTest - SupervisorNotAllowedInCity nunca é lançada.
    // packt service/pulverisation - calsse TargetService - Nunca é lançada EntityNotFoundException.class;
    // packt service/pulverisation - calsse ProductService - EntityNotFoundException Nunca é disparada
    // packt service/pulverisation - calsse PulverisationOperationService EntityNotFoundException nunca é lançada
    //***(IMPORTANTE)**** packt entity/base - classe MacroRegionService - A macroRegião atual não é removida antes de vereficar se ha algum registro corresponddente durante o update
    //***(IMPORTANTE)**** packt entity/base - classe FarmerService - O farmer atual não é removido antes de vereficar se ha algum registro corresponddente durante o update
    //***(IMPORTANTE)**** packt entity/base - classe RegionService - Ha o lançamento de um NullPointe pois o contrutor precisa de um nome para ser instanciado, não ha construtor vazio
    //***(IMPORTANTE)**** packt entity/base - classe SupervisorService - Test falha pois - Ha o lançamento de um NullPointe pois o contrutor precisa de um nome, e região email para ser executado, não ha construtor vazio.
}
