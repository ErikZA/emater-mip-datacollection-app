package br.edu.utfpr.cp.emater.midmipsystem;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.MacroRegionRepository;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Farmer;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.CityRepository;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.RegionRepository;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.State;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Supervisor;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.AsiaticRustTypesLeafInspection;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.AsiaticRustTypesSporeCollector;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.BladeReadingResponsibleEntity;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.BladeReadingResponsiblePerson;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.MIDRustSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.MIDSampleFungicideApplicationOccurrence;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.MIDSampleLeafInspectionOccurrence;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.MIDSampleSporeCollectorOccurrence;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.GrowthPhase;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.Pest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestDisease;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestNaturalPredator;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestSize;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.Product;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.ProductUnit;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.PulverisationOperation;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.Target;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.TargetCategory;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.FarmerRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.FieldRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.SupervisorRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mid.BladeReadingResponsibleEntityRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mid.BladeReadingResponsiblePersonRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mip.MIPSampleRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mip.PestDiseaseRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mip.PestNaturalPredatorRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mip.PestRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.HarvestRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.SurveyRepository;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mid.MIDRustSampleRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.pulverisation.ProductRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.pulverisation.PulverisationOperationRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.pulverisation.TargetRepository;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableJpaAuditing
@EnableWebSecurity
public class MipApplication  {

    public static void main(String[] args) {
        SpringApplication.run(MipApplication.class, args);
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("pt", "BR"));
        return slr;
    }
}

@Component
class CLR implements CommandLineRunner {

    private MacroRegionRepository macroRegionRepository;
    private RegionRepository regionRepository;
    private CityRepository cityRepository;
    private FarmerRepository farmerRepository;
    private SupervisorRepository supervisorRepository;
    private FieldRepository fieldRepository;

    private HarvestRepository harvestRepository;
    private SurveyRepository surveyRepository;

    private PestRepository pestRepository;
    private PestDiseaseRepository pestDiseaseRepository;
    private PestNaturalPredatorRepository pestNaturalPredatorRepository;

    private MIPSampleRepository mipSampleRepository;

    private MIDRustSampleRepository midRustRepository;
    private BladeReadingResponsibleEntityRepository bladeEntityRepository;
    private BladeReadingResponsiblePersonRepository bladePersonRepository;

    private TargetRepository targetRepository;
    private ProductRepository productRepository;
    private PulverisationOperationRepository pulverisationRepository;

    @Autowired
    CLR(MacroRegionRepository macroRegionRepository,
            RegionRepository aRegionRepository,
            CityRepository aCityRepository,
            FarmerRepository aFarmerRepository,
            SupervisorRepository aSupervisorRepository,
            FieldRepository aFieldRepository,
            HarvestRepository aHarvestRepository,
            SurveyRepository aSurveyRepository,
            PestRepository aPestRepository,
            PestDiseaseRepository aPestDiseaseRepository,
            PestNaturalPredatorRepository aPestNaturalPredatorRepository,
            MIPSampleRepository aMIPSampleRepository,
            MIDRustSampleRepository aMIDRustRepository,
            BladeReadingResponsibleEntityRepository aBladeEntityRepository,
            BladeReadingResponsiblePersonRepository aBladePersonRepository,
            TargetRepository aTargetRepository,
            ProductRepository aProductRepository,
            PulverisationOperationRepository aPulverisationRepository) {

        this.macroRegionRepository = macroRegionRepository;
        this.regionRepository = aRegionRepository;
        this.cityRepository = aCityRepository;
        this.farmerRepository = aFarmerRepository;
        this.supervisorRepository = aSupervisorRepository;
        this.fieldRepository = aFieldRepository;

        this.harvestRepository = aHarvestRepository;
        this.surveyRepository = aSurveyRepository;

        this.pestRepository = aPestRepository;
        this.pestDiseaseRepository = aPestDiseaseRepository;
        this.pestNaturalPredatorRepository = aPestNaturalPredatorRepository;

        this.mipSampleRepository = aMIPSampleRepository;

        this.midRustRepository = aMIDRustRepository;
        this.bladeEntityRepository = aBladeEntityRepository;
        this.bladePersonRepository = aBladePersonRepository;

        this.targetRepository = aTargetRepository;
        this.productRepository = aProductRepository;
        this.pulverisationRepository = aPulverisationRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        var mr1 = macroRegionRepository.save(MacroRegion.builder().name("Macro Noroeste").build());
        var mr2 = macroRegionRepository.save(MacroRegion.builder().name("Macro Norte").build());
        var mr3 = macroRegionRepository.save(MacroRegion.builder().name("Macro Oeste").build());
        var mr4 = macroRegionRepository.save(MacroRegion.builder().name("Macro Sul").build());

        var c1 = cityRepository.save(City.builder().name("Itapejara D'Oeste").state(State.PR).build());
        var c2 = cityRepository.save(City.builder().name("Mariópolis").state(State.PR).build());
        var c3 = cityRepository.save(City.builder().name("Pato Branco").state(State.PR).build());
        var c4 = cityRepository.save(City.builder().name("Apucarana").state(State.PR).build());
        var c5 = cityRepository.save(City.builder().name("Campo Mourão").state(State.PR).build());

//        var r1 = regionRepository.save(Region.builder().name("Apucarana").macroRegion(mr1).build());
//        var r2 = regionRepository.save(Region.builder().name("Campo Mourão").macroRegion(mr2).build());
//        var r3 = regionRepository.save(Region.builder().name("Cascavel").macroRegion(mr3).build());
//        var r4 = regionRepository.save(Region.builder().name("Cianorte").macroRegion(mr2).build());
//        var r5 = regionRepository.save(Region.builder().name("Cornélio Procópio").macroRegion(mr2).build());
//        var r6 = regionRepository.save(Region.builder().name("Curitiba").macroRegion(mr2).build());
        var region7 = Region.builder().name("Dois Vizinhos").macroRegion(mr4).build();
        region7.addCity(c1);
        var r7 = regionRepository.save(region7);

//        var r8 = regionRepository.save(Region.builder().name("Francisco Beltrão").macroRegion(mr4).build());
//        var r9 = regionRepository.save(Region.builder().name("Guarapuava").macroRegion(mr2).build());
//        var r10 = regionRepository.save(Region.builder().name("Irati").macroRegion(mr2).build());
//        var r11 = regionRepository.save(Region.builder().name("Ivaiporã").macroRegion(mr2).build());
//        var r12 = regionRepository.save(Region.builder().name("Laranjeiras do Sul").macroRegion(mr2).build());
//        var r13 = regionRepository.save(Region.builder().name("Londrina").macroRegion(mr2).build());
//        var r14 = regionRepository.save(Region.builder().name("Maringá").macroRegion(mr1).build());
//        var r15 = regionRepository.save(Region.builder().name("Paranaguá").macroRegion(mr2).build());
//        var r16 = regionRepository.save(Region.builder().name("Paranavaí").macroRegion(mr1).build());
        var region17 = Region.builder().name("Pato Branco").macroRegion(mr4).build();
        region17.addCity(c2);
        region17.addCity(c3);
        var r17 = regionRepository.save(region17);

//        var r18 = regionRepository.save(Region.builder().name("Ponta Grossa").macroRegion(mr2).build());
//        var r19 = regionRepository.save(Region.builder().name("Sto. Antonio da Platina").macroRegion(mr2).build());
//        var r20 = regionRepository.save(Region.builder().name("Toledo").macroRegion(mr2).build());
//        var r21 = regionRepository.save(Region.builder().name("Umuarama").macroRegion(mr1).build());
//        var r22 = regionRepository.save(Region.builder().name("União da Vitória").macroRegion(mr4).build());
        var f1 = farmerRepository.save(Farmer.builder().name("Gilson Dariva").build());
        var f2 = farmerRepository.save(Farmer.builder().name("LUIZ ARCANGELO GIORDANI").build());
        var f3 = farmerRepository.save(Farmer.builder().name("Maurílio Bertoldo").build());
        var f4 = farmerRepository.save(Farmer.builder().name("Rafael Oldoni").build());
        var f5 = farmerRepository.save(Farmer.builder().name("Clemente Carnieletto").build());

        var s1 = supervisorRepository.save(Supervisor.builder().name("Lari Maroli").email("maroli@emater.pr.gov.br").region(r7).build());
        var s2 = supervisorRepository.save(Supervisor.builder().name("IVANDERSON BORELLI").email("borelli@emater.pr.gov.br").region(r17).build());
        var s3 = supervisorRepository.save(Supervisor.builder().name("José Francisco Vilas Boas").email("villas@emater.pr.gov.br").region(r17).build());
        var s4 = supervisorRepository.save(Supervisor.builder().name("Vilmar Grando").email("grando@emater.pr.gov.br").region(r17).build());

        var field1 = Field.builder().name("Trevo").location("").city(c1).farmer(f1).build();
        field1.addSupervisor(s1);
        var persistentField1 = fieldRepository.save(field1);

        var field2 = Field.builder().name("Carnieletto").location("").city(c3).farmer(f5).build();
        field2.addSupervisor(s4);
        var persistentField2 = fieldRepository.save(field2);

        var field3 = Field.builder().name("Oldoni").location("").city(c3).farmer(f4).build();
        field3.addSupervisor(s4);
        var persistentField3 = fieldRepository.save(field3);

        var field4 = Field.builder().name("MIP e MID").location("").city(c2).farmer(f2).build();
        field4.addSupervisor(s2);
        var persistentField4 = fieldRepository.save(field4);

        var field5 = Field.builder().name("Bertoldo").location("1").city(c3).farmer(f3).build();
        field5.addSupervisor(s3);
        var persistentField5 = fieldRepository.save(field5);

        var harvest1 = harvestRepository.save(
                Harvest.builder()
                        .name("Safra 2016/2017")
                        .begin(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2016"))
                        .end(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-03-2017"))
                        .build()
        );

        var harvest2 = harvestRepository.save(
                Harvest.builder()
                        .name("Safra 2017/2018")
                        .begin(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-10-2017"))
                        .end(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-03-2018"))
                        .build()
        );

        var survey1 = surveyRepository.save(
                Survey.builder()
                        .harvest(harvest1)
                        .field(persistentField3)
                        .seedName("TMG 7262 RR1")
                        .sowedDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-1"))
                        .emergenceDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-8"))
                        .harvestDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2018-02-26"))
                        .rustResistant(true)
                        .bt(false)
                        .totalArea(4.4)
                        .totalPlantedArea(10)
                        .plantPerMeter(9)
                        .productivityField(161.7)
                        .productivityFarmer(159.5)
                        .separatedWeight(true)
                        .longitude(2.5)
                        .latitude(3.5)
                        .build()
        );

        var survey2 = surveyRepository.save(
                Survey.builder()
                        .harvest(harvest1)
                        .field(persistentField2)
                        .seedName("BMX RAIO Ipro")
                        .sowedDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-4"))
                        .emergenceDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-11"))
                        .harvestDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2018-2-12"))
                        .rustResistant(false)
                        .bt(true)
                        .totalArea(18)
                        .totalPlantedArea(62)
                        .plantPerMeter(13)
                        .productivityField(197)
                        .productivityFarmer(182)
                        .separatedWeight(true)
                        .longitude(3.5)
                        .latitude(4.5)
                        .build()
        );

        var survey3 = surveyRepository.save(
                Survey.builder()
                        .harvest(harvest1)
                        .field(persistentField5)
                        .seedName("TMG 7262 RR")
                        .sowedDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-4"))
                        .emergenceDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-9"))
                        .harvestDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2018-2-20"))
                        .rustResistant(true)
                        .bt(false)
                        .totalArea(5.74)
                        .totalPlantedArea(35.09)
                        .plantPerMeter(11)
                        .productivityField(137.5)
                        .productivityFarmer(120)
                        .separatedWeight(true)
                        .longitude(4.5)
                        .latitude(5.5)
                        .build()
        );

        var survey4 = surveyRepository.save(
                Survey.builder()
                        .harvest(harvest1)
                        .field(persistentField4)
                        .seedName("TMG -  7262")
                        .sowedDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-24"))
                        .emergenceDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-10"))
                        .harvestDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2018-2-18"))
                        .rustResistant(true)
                        .bt(false)
                        .totalArea(3.63)
                        .totalPlantedArea(3.63)
                        .plantPerMeter(9)
                        .productivityField(158.5)
                        .productivityFarmer(158.5)
                        .separatedWeight(true)
                        .longitude(6.5)
                        .latitude(6.5)
                        .build()
        );

        var survey5 = surveyRepository.save(
                Survey.builder()
                        .harvest(harvest1)
                        .field(persistentField1)
                        .seedName("P95R51")
                        .sowedDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-9-26"))
                        .emergenceDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-10"))
                        .harvestDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2018-2-15"))
                        .rustResistant(false)
                        .bt(false)
                        .totalArea(7.26)
                        .totalPlantedArea(242)
                        .plantPerMeter(15)
                        .productivityField(187)
                        .productivityFarmer(170)
                        .separatedWeight(true)
                        .longitude(7.5)
                        .latitude(8.5)
                        .build()
        );

//        **** MIP ****
        var p1 = pestRepository.save(Pest.builder().usualName("Lagarta da soja").scientificName("Anticarsia gemmatalis").pestSize(PestSize.GREATER_15CM).build());
        var p2 = pestRepository.save(Pest.builder().usualName("Lagarta da soja").scientificName("Anticarsia gemmatalis").pestSize(PestSize.SMALLER_15CM).build());
        var p3 = pestRepository.save(Pest.builder().usualName("Falsa medideira").scientificName("Chrysodeixis spp.").pestSize(PestSize.GREATER_15CM).build());
        var p4 = pestRepository.save(Pest.builder().usualName("Falsa medideira").scientificName("Chrysodeixis spp.").pestSize(PestSize.SMALLER_15CM).build());
        var p5 = pestRepository.save(Pest.builder().usualName("Lagarta das vagens").scientificName("Spodoptera spp.").pestSize(PestSize.GREATER_15CM).build());
        var p6 = pestRepository.save(Pest.builder().usualName("Lagarta das vagens").scientificName("Spodoptera spp.").pestSize(PestSize.SMALLER_15CM).build());
        var p7 = pestRepository.save(Pest.builder().usualName("Grupo Heliothinae").scientificName("").pestSize(PestSize.GREATER_15CM).build());
        var p8 = pestRepository.save(Pest.builder().usualName("Grupo Heliothinae").scientificName("Chrysodeixis spp.").pestSize(PestSize.SMALLER_15CM).build());
        var p9 = pestRepository.save(Pest.builder().usualName("Percevejo verde").scientificName("Nezara sp.").pestSize(PestSize.THIRD_TO_FIFTH_INSTAR).build());
        var p10 = pestRepository.save(Pest.builder().usualName("Percevejo verde").scientificName("Nezara sp.").pestSize(PestSize.ADULT).build());
        var p11 = pestRepository.save(Pest.builder().usualName("Percevejo verde pequeno").scientificName("Piezodorus sp.").pestSize(PestSize.THIRD_TO_FIFTH_INSTAR).build());
        var p12 = pestRepository.save(Pest.builder().usualName("Percevejo verde pequeno").scientificName("Piezodorus sp.").pestSize(PestSize.ADULT).build());
        var p13 = pestRepository.save(Pest.builder().usualName("Percevejo Marrom").scientificName("Eushistus sp.").pestSize(PestSize.THIRD_TO_FIFTH_INSTAR).build());
        var p14 = pestRepository.save(Pest.builder().usualName("Percevejo Marrom").scientificName("Eushistus sp.").pestSize(PestSize.ADULT).build());
        var p15 = pestRepository.save(Pest.builder().usualName("Percevejo Barriga verde").scientificName("Dichelops ssp.").pestSize(PestSize.THIRD_TO_FIFTH_INSTAR).build());
        var p16 = pestRepository.save(Pest.builder().usualName("Percevejo Barriga verde").scientificName("Dichelops ssp.").pestSize(PestSize.ADULT).build());
        var p17 = pestRepository.save(Pest.builder().usualName("Outros percevejos").scientificName("").pestSize(PestSize.THIRD_TO_FIFTH_INSTAR).build());
        var p18 = pestRepository.save(Pest.builder().usualName("Outros percevejos").scientificName("").pestSize(PestSize.ADULT).build());

        var pestDisease1 = pestDiseaseRepository.save(PestDisease.builder().usualName("Lagarta com Nomuraea rileyi (Doença Branca)").build());
        var pestDisease2 = pestDiseaseRepository.save(PestDisease.builder().usualName("Lagarta com Baculovírus (Doença Preta)").build());

        var pestNaturalPredator1 = pestNaturalPredatorRepository.save(PestNaturalPredator.builder().usualName("Calosoma granulatum").build());
        var pestNaturalPredator2 = pestNaturalPredatorRepository.save(PestNaturalPredator.builder().usualName("Callida sp.").build());
        var pestNaturalPredator3 = pestNaturalPredatorRepository.save(PestNaturalPredator.builder().usualName("Callida scutellaris").build());
        var pestNaturalPredator4 = pestNaturalPredatorRepository.save(PestNaturalPredator.builder().usualName("Lebia concinna").build());
        var pestNaturalPredator5 = pestNaturalPredatorRepository.save(PestNaturalPredator.builder().usualName("Eriopsis connexa").build());
        var pestNaturalPredator6 = pestNaturalPredatorRepository.save(PestNaturalPredator.builder().usualName("Cycloneda sanguinea").build());
        var pestNaturalPredator7 = pestNaturalPredatorRepository.save(PestNaturalPredator.builder().usualName("Podisus sp.").build());
        var pestNaturalPredator8 = pestNaturalPredatorRepository.save(PestNaturalPredator.builder().usualName("Tropiconabis sp.").build());
        var pestNaturalPredator9 = pestNaturalPredatorRepository.save(PestNaturalPredator.builder().usualName("Geocoris sp.").build());
        var pestNaturalPredator10 = pestNaturalPredatorRepository.save(PestNaturalPredator.builder().usualName("Doru sp. (Tesourinha)").build());
        var pestNaturalPredator11 = pestNaturalPredatorRepository.save(PestNaturalPredator.builder().usualName("Aranhas").build());
        var pestNaturalPredator12 = pestNaturalPredatorRepository.save(PestNaturalPredator.builder().usualName("Formigas").build());
        var pestNaturalPredator13 = pestNaturalPredatorRepository.save(PestNaturalPredator.builder().usualName("Outros").build());

        var mipSurvey3Sample1 = MIPSample.builder()
                .daysAfterEmergence(22)
                .defoliation(0)
                .growthPhase(GrowthPhase.V3)
                .sampleDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-31"))
                .survey(survey3)
                .build();

        mipSurvey3Sample1.addPestOccurrence(p6, 1.0);
        mipSurvey3Sample1.addPestNaturalPredatorOccurrence(pestNaturalPredator5, 1.0);
        mipSurvey3Sample1.addPestNaturalPredatorOccurrence(pestNaturalPredator11, 1.0);

        mipSampleRepository.save(mipSurvey3Sample1);

        var mipSurvey3Sample2 = MIPSample.builder()
                .daysAfterEmergence(51)
                .defoliation(3)
                .growthPhase(GrowthPhase.R1)
                .sampleDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-11-29"))
                .survey(survey3)
                .build();

        mipSurvey3Sample2.addPestOccurrence(p1, 3.0);
        mipSurvey3Sample2.addPestDiseaseOccurrence(pestDisease1, 0.9);
        mipSurvey3Sample2.addPestNaturalPredatorOccurrence(pestNaturalPredator12, 4.0);

        mipSampleRepository.save(mipSurvey3Sample2);

        var mipSurvey4Sample1 = MIPSample.builder()
                .daysAfterEmergence(18)
                .defoliation(0)
                .growthPhase(GrowthPhase.V2)
                .sampleDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-11-16"))
                .survey(survey4)
                .build();

        mipSampleRepository.save(mipSurvey4Sample1);

        var mipSurvey4Sample2 = MIPSample.builder()
                .daysAfterEmergence(81)
                .defoliation(3)
                .growthPhase(GrowthPhase.R3)
                .sampleDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2018-01-18"))
                .survey(survey4)
                .build();

        mipSurvey4Sample2.addPestOccurrence(p2, 0.2);
        mipSurvey4Sample2.addPestOccurrence(p3, 1.3);
        mipSurvey4Sample2.addPestOccurrence(p4, 0.6);
        mipSurvey4Sample2.addPestOccurrence(p6, 0.1);
        mipSurvey4Sample2.addPestOccurrence(p13, 0.7);
        mipSurvey4Sample2.addPestOccurrence(p14, 0.1);
        mipSurvey4Sample2.addPestOccurrence(p15, 0.2);

        mipSurvey4Sample2.addPestDiseaseOccurrence(pestDisease1, 1.5);

        mipSurvey4Sample2.addPestNaturalPredatorOccurrence(pestNaturalPredator8, 0.4);
        mipSurvey4Sample2.addPestNaturalPredatorOccurrence(pestNaturalPredator11, 1.6);
        mipSurvey4Sample2.addPestNaturalPredatorOccurrence(pestNaturalPredator13, 1.6);

        mipSampleRepository.save(mipSurvey4Sample2);

        var bladeEntity1 = bladeEntityRepository.save(BladeReadingResponsibleEntity.builder().name("EMATER - PB").city(c3).build());
        var bladeEntity2 = bladeEntityRepository.save(BladeReadingResponsibleEntity.builder().name("EMATER - CP").city(c3).build());

        var bladePerson1 = bladePersonRepository.save(BladeReadingResponsiblePerson.builder().name("Gustavo M. de Oliveira").entity(bladeEntity1).build());
        var bladePerson2 = bladePersonRepository.save(BladeReadingResponsiblePerson.builder().name("Otávio Augusto").entity(bladeEntity2).build());

        var rustSurvey3Sample1 = MIDRustSample.builder().survey(survey3).sampleDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-11-01")).build();
        var sporeCollectorOccurrenceRustSurvey3Sample1 = MIDSampleSporeCollectorOccurrence.builder()
                .bladeInstalledPreCold(true)
                .bladeReadingDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-11-10"))
                .bladeReadingResponsiblePerson(bladePerson1)
                .bladeReadingRustResultCollector(AsiaticRustTypesSporeCollector.NO_RUST_SPORES)
                .build();

        var leafInspectionOccurrenceRustSurvey3Sample1 = MIDSampleLeafInspectionOccurrence.builder()
                .bladeReadingRustResultLeafInspection(AsiaticRustTypesLeafInspection.NO_VISIBLE_DAMAGE)
                .growthPhase(GrowthPhase.V3)
                .build();

        var fungicideOccurrenceRustSurvey3Sample1 = MIDSampleFungicideApplicationOccurrence.builder()
                .asiaticRustApplication(false)
                .otherDiseasesApplication(false)
                .fungicideApplicationDate(null)
                .notes(null)
                .build();

        rustSurvey3Sample1.setSporeCollectorOccurrence(sporeCollectorOccurrenceRustSurvey3Sample1);
        rustSurvey3Sample1.setLeafInspectionOccurrence(leafInspectionOccurrenceRustSurvey3Sample1);
        rustSurvey3Sample1.setFungicideOccurrence(fungicideOccurrenceRustSurvey3Sample1);

        midRustRepository.save(rustSurvey3Sample1);

        var rustSurvey3Sample2 = MIDRustSample.builder().survey(survey3).sampleDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-11-08")).build();
        var sporeCollectorOccurrenceRustSurvey3Sample2 = MIDSampleSporeCollectorOccurrence.builder()
                .bladeInstalledPreCold(true)
                .bladeReadingDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-11-14"))
                .bladeReadingResponsiblePerson(bladePerson1)
                .bladeReadingRustResultCollector(AsiaticRustTypesSporeCollector.NO_RUST_SPORES)
                .build();

        var leafInspectionOccurrenceRustSurvey3Sample2 = MIDSampleLeafInspectionOccurrence.builder()
                .bladeReadingRustResultLeafInspection(AsiaticRustTypesLeafInspection.NO_VISIBLE_DAMAGE)
                .growthPhase(GrowthPhase.R1)
                .build();

        var fungicideOccurrenceRustSurvey3Sample2 = MIDSampleFungicideApplicationOccurrence.builder()
                .asiaticRustApplication(false)
                .otherDiseasesApplication(false)
                .fungicideApplicationDate(null)
                .notes(null)
                .build();

        rustSurvey3Sample2.setSporeCollectorOccurrence(sporeCollectorOccurrenceRustSurvey3Sample2);
        rustSurvey3Sample2.setLeafInspectionOccurrence(leafInspectionOccurrenceRustSurvey3Sample2);
        rustSurvey3Sample2.setFungicideOccurrence(fungicideOccurrenceRustSurvey3Sample2);

        midRustRepository.save(rustSurvey3Sample2);

        var rustSurvey3Sample3 = MIDRustSample.builder().survey(survey3).sampleDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-11-14")).build();

        var sporeCollectorOccurrenceRustSurvey3Sample3 = MIDSampleSporeCollectorOccurrence.builder()
                .bladeInstalledPreCold(true)
                .bladeReadingDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-11-23"))
                .bladeReadingResponsiblePerson(bladePerson1)
                .bladeReadingRustResultCollector(AsiaticRustTypesSporeCollector.NO_RUST_SPORES)
                .build();

        var leafInspectionOccurrenceRustSurvey3Sample3 = MIDSampleLeafInspectionOccurrence.builder()
                .bladeReadingRustResultLeafInspection(AsiaticRustTypesLeafInspection.NO_VISIBLE_DAMAGE)
                .growthPhase(GrowthPhase.R2)
                .build();

        var fungicideOccurrenceRustSurvey3Sample3 = MIDSampleFungicideApplicationOccurrence.builder()
                .asiaticRustApplication(false)
                .otherDiseasesApplication(false)
                .fungicideApplicationDate(null)
                .notes(null)
                .build();

        rustSurvey3Sample3.setSporeCollectorOccurrence(sporeCollectorOccurrenceRustSurvey3Sample3);
        rustSurvey3Sample3.setLeafInspectionOccurrence(leafInspectionOccurrenceRustSurvey3Sample3);
        rustSurvey3Sample3.setFungicideOccurrence(fungicideOccurrenceRustSurvey3Sample3);

        midRustRepository.save(rustSurvey3Sample3);

        var rustSurvey2Sample1 = MIDRustSample.builder().survey(survey2).sampleDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-11-01")).build();

        var sporeCollectorOccurrenceRustSurvey2Sample1 = MIDSampleSporeCollectorOccurrence.builder()
                .bladeInstalledPreCold(false)
                .bladeReadingDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-12-19"))
                .bladeReadingResponsiblePerson(bladePerson1)
                .bladeReadingRustResultCollector(AsiaticRustTypesSporeCollector.NO_RUST_SPORES)
                .build();

        var leafInspectionOccurrenceRustSurvey2Sample1 = MIDSampleLeafInspectionOccurrence.builder()
                .bladeReadingRustResultLeafInspection(AsiaticRustTypesLeafInspection.NO_VISIBLE_DAMAGE)
                .growthPhase(GrowthPhase.R3)
                .build();

        var fungicideOccurrenceRustSurvey2Sample1 = MIDSampleFungicideApplicationOccurrence.builder()
                .asiaticRustApplication(false)
                .otherDiseasesApplication(true)
                .fungicideApplicationDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-12-14"))
                .notes("OIDIO")
                .build();

        rustSurvey2Sample1.setSporeCollectorOccurrence(sporeCollectorOccurrenceRustSurvey2Sample1);
        rustSurvey2Sample1.setLeafInspectionOccurrence(leafInspectionOccurrenceRustSurvey2Sample1);
        rustSurvey2Sample1.setFungicideOccurrence(fungicideOccurrenceRustSurvey2Sample1);

        midRustRepository.save(rustSurvey2Sample1);

        var rustSurvey2Sample2 = MIDRustSample.builder().survey(survey2).sampleDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2018-01-02")).build();

        var sporeCollectorOccurrenceRustSurvey2Sample2 = MIDSampleSporeCollectorOccurrence.builder()
                .bladeInstalledPreCold(false)
                .bladeReadingDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2018-01-05"))
                .bladeReadingResponsiblePerson(bladePerson1)
                .bladeReadingRustResultCollector(AsiaticRustTypesSporeCollector.NO_RUST_SPORES)
                .build();

        var leafInspectionOccurrenceRustSurvey2Sample2 = MIDSampleLeafInspectionOccurrence.builder()
                .bladeReadingRustResultLeafInspection(AsiaticRustTypesLeafInspection.NO_VISIBLE_DAMAGE)
                .growthPhase(GrowthPhase.R4)
                .build();

        var fungicideOccurrenceRustSurvey2Sample2 = MIDSampleFungicideApplicationOccurrence.builder()
                .asiaticRustApplication(true)
                .otherDiseasesApplication(true)
                .fungicideApplicationDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-12-31"))
                .notes("FERRUGEM,OIDIO")
                .build();

        rustSurvey2Sample2.setSporeCollectorOccurrence(sporeCollectorOccurrenceRustSurvey2Sample2);
        rustSurvey2Sample2.setLeafInspectionOccurrence(leafInspectionOccurrenceRustSurvey2Sample2);
        rustSurvey2Sample2.setFungicideOccurrence(fungicideOccurrenceRustSurvey2Sample2);

        midRustRepository.save(rustSurvey2Sample2);

        // ****** Pulverisation ******
        var target1 = targetRepository.save(Target.builder().description("Folha Larga (Pós-emergência)").category(TargetCategory.HERBICIDA).build());
        var target2 = targetRepository.save(Target.builder().description("Dessecação").category(TargetCategory.OUTROS).build());

        var target3 = targetRepository.save(Target.builder().description("Folha Estreita (Pré-emergência)").category(TargetCategory.HERBICIDA).build());
        var target4 = targetRepository.save(Target.builder().description("Lagartas Grupo Heliotinae").category(TargetCategory.INSETICIDA).build());
        var target5 = targetRepository.save(Target.builder().description("Folha Larga (Pré-emergência)").category(TargetCategory.HERBICIDA).build());

        var product1 = productRepository.save(Product.builder().name("ROUND UP TRANSORB").dose(2.0).unit(ProductUnit.L).target(target3).build());
        var product2 = productRepository.save(Product.builder().name("TRIFLUBENZURON").dose(0.13).unit(ProductUnit.L).target(target4).build());
        var product3 = productRepository.save(Product.builder().name("ZAPP QI").dose(1.2).unit(ProductUnit.L).target(target1).build());

        var pulverisationOp1Survey3 = PulverisationOperation.builder()
                .survey(survey3)
                .soyaPrice(70.0)
                .operationCostCurrency(30.0)
                .sampleDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-11-01"))
                .growthPhase(GrowthPhase.R4)
                .caldaVolume(150.0)
                .build();

        pulverisationOp1Survey3.addOperationOccurrence(product3, 15.0, target1);

        pulverisationRepository.save(pulverisationOp1Survey3);

        var pulverisationOp1Survey2 = PulverisationOperation.builder()
                .survey(survey2)
                .soyaPrice(70.0)
                .operationCostCurrency(49.0)
                .sampleDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-08-28"))
                .growthPhase(GrowthPhase.V6)
                .caldaVolume(100.0)
                .build();

        pulverisationOp1Survey2.addOperationOccurrence(product1, 13.0, target3);
        pulverisationOp1Survey2.addOperationOccurrence(product2, 35.0, target4);

        pulverisationRepository.save(pulverisationOp1Survey2);

        var pulverisationOp2Survey2 = PulverisationOperation.builder()
                .survey(survey2)
                .soyaPrice(70.0)
                .operationCostCurrency(49.0)
                .sampleDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-11-16"))
                .growthPhase(GrowthPhase.V6)
                .caldaVolume(100.0)
                .build();

        pulverisationOp2Survey2.addOperationOccurrence(product1, 13.0, target5);

        pulverisationRepository.save(pulverisationOp2Survey2);
    }

}
