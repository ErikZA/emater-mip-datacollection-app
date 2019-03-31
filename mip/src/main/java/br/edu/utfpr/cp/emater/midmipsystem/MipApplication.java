package br.edu.utfpr.cp.emater.midmipsystem;

import br.edu.utfpr.cp.emater.midmipsystem.domain.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.domain.base.CityRepository;
import br.edu.utfpr.cp.emater.midmipsystem.domain.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.domain.base.FieldRepository;
import br.edu.utfpr.cp.emater.midmipsystem.domain.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.domain.base.MacroRegionRepository;
import br.edu.utfpr.cp.emater.midmipsystem.domain.base.Farmer;
import br.edu.utfpr.cp.emater.midmipsystem.domain.base.FarmerRepository;
import br.edu.utfpr.cp.emater.midmipsystem.domain.base.Supervisor;
import br.edu.utfpr.cp.emater.midmipsystem.domain.base.SupervisorRepository;
import br.edu.utfpr.cp.emater.midmipsystem.domain.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.domain.base.RegionRepository;
import br.edu.utfpr.cp.emater.midmipsystem.domain.base.State;
import br.edu.utfpr.cp.emater.midmipsystem.domain.mip.GrowthPhase;
import br.edu.utfpr.cp.emater.midmipsystem.domain.mip.Pest;
import br.edu.utfpr.cp.emater.midmipsystem.domain.mip.PestFluctuationSample;
import br.edu.utfpr.cp.emater.midmipsystem.domain.mip.PestFluctuationSampleRepository;
import br.edu.utfpr.cp.emater.midmipsystem.domain.mip.PestRepository;
import br.edu.utfpr.cp.emater.midmipsystem.domain.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.domain.survey.HarvestRepository;
import br.edu.utfpr.cp.emater.midmipsystem.domain.survey.DateData;
import br.edu.utfpr.cp.emater.midmipsystem.domain.survey.LocationData;
import br.edu.utfpr.cp.emater.midmipsystem.domain.survey.ProductivityData;
import br.edu.utfpr.cp.emater.midmipsystem.domain.survey.QuestionData;
import br.edu.utfpr.cp.emater.midmipsystem.domain.survey.SizeData;
import br.edu.utfpr.cp.emater.midmipsystem.domain.survey.SurveyField;
import br.edu.utfpr.cp.emater.midmipsystem.domain.survey.SurveyFieldRepository;
import freemarker.template.Configuration;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@SpringBootApplication
@EnableJpaAuditing
public class MipApplication {

        private FreeMarkerConfigurer freeMarkerConfigurer;
        @Autowired
        public MipApplication (FreeMarkerConfigurer freeMarkerConfigurer) {
                this.freeMarkerConfigurer = freeMarkerConfigurer;

                this.freeMarkerConfigurer.getConfiguration().addAutoImport("spring", "spring.ftl");
        }

    public static void main(String[] args) {
        SpringApplication.run(MipApplication.class, args);
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }
}

@Component
class CLR implements CommandLineRunner {

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private SupervisorRepository supervisorRepository;

    @Autowired
    private FarmerRepository farmerRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private MacroRegionRepository macroRegionRepository;

    @Autowired
    private SurveyFieldRepository surveyFieldRepository;

    @Autowired
    private HarvestRepository harvestRepository;

//    @Autowired
//    private MipPestSurveyRepository mipPestSurveyRepository;

    @Autowired
    private PestRepository pestRepository;

    @Autowired
    private PestFluctuationSampleRepository sampleRepository;

//    @Autowired
//    private SamplePestRepository samplePestRepository;

    @Override
    public void run(String... args) throws Exception {
        MacroRegion mr1 = macroRegionRepository.save(new MacroRegion(null, "Macro Noroeste"));
        MacroRegion mr2 = macroRegionRepository.save(new MacroRegion(null, "Macro Norte"));
        MacroRegion mr3 = macroRegionRepository.save(new MacroRegion(null, "Macro Oeste"));
        MacroRegion mr4 = macroRegionRepository.save(new MacroRegion(null, "Macro Sul"));

        City c1 = cityRepository.save(new City(null, "Itapejara D'Oeste", State.PR));
        City c2 = cityRepository.save(new City(null, "Mariópolis", State.PR));
        City c3 = cityRepository.save(new City(null, "Pato Branco", State.PR));        
        City c4 = cityRepository.save(new City(null, "Apucarana", State.PR));        
        City c5 = cityRepository.save(new City(null, "Campo Mourão", State.PR));        

        Region r1 = regionRepository.save(new Region(null, "Apucarana", mr1, null));
        Region r2 = regionRepository.save(new Region(null, "Campo Mourão", mr2, null));
        Region r3 = regionRepository.save(new Region(null, "Cascavel", mr3, null));
        Region r4 = regionRepository.save(new Region(null, "Cianorte", mr2, null));
        Region r5 = regionRepository.save(new Region(null, "Cornélio Procópio", mr2, null));
        Region r6 = regionRepository.save(new Region(null, "Curitiba", mr2, null));
        Region r7 = regionRepository.save(new Region(null, "Dois Vizinhos", mr4, List.of(c1)));
        Region r8 = regionRepository.save(new Region(null, "Francisco Beltrão", mr4, null));
        Region r9 = regionRepository.save(new Region(null, "Guarapuava", mr2, null));
        Region r10 = regionRepository.save(new Region(null, "Irati", mr2, null));
        Region r11 = regionRepository.save(new Region(null, "Ivaiporã", mr2, null));
        Region r12 = regionRepository.save(new Region(null, "Laranjeiras do Sul", mr2, null));
        Region r13 = regionRepository.save(new Region(null, "Londrina", mr2, null));
        Region r14 = regionRepository.save(new Region(null, "Maringá", mr1, null));
        Region r15 = regionRepository.save(new Region(null, "Paranaguá", mr2, null));
        Region r16 = regionRepository.save(new Region(null, "Paranavaí", mr1, null));
        Region r17 = regionRepository.save(new Region(null, "Pato Branco", mr4, List.of(c2, c3)));
        Region r18 = regionRepository.save(new Region(null, "Ponta Grossa", mr2, null));
        Region r19 = regionRepository.save(new Region(null, "Sto. Antonio da Platina", mr2, null));
        Region r20 = regionRepository.save(new Region(null, "Toledo", mr2, null));
        Region r21 = regionRepository.save(new Region(null, "Umuarama", mr1, null));
        Region r22 = regionRepository.save(new Region(null, "União da Vitória", mr4, null));

        Farmer f1 = farmerRepository.save(new Farmer(null, "Gilson Dariva"));
        Farmer f2 = farmerRepository.save(new Farmer(null, "LUIZ ARCANGELO GIORDANI"));
        Farmer f3 = farmerRepository.save(new Farmer(null, "Maurílio Bertoldo"));
        Farmer f4 = farmerRepository.save(new Farmer(null, "Rafael Oldoni"));
        Farmer f5 = farmerRepository.save(new Farmer(null, "Clemente Carnieletto"));

        Supervisor s1 = supervisorRepository.save(new Supervisor(null, "Lari Maroli", "maroli@emater.pr.gov.br", r7));
        Supervisor s2 = supervisorRepository.save(new Supervisor(null, "IVANDERSON BORELLI", "borelli@emater.pr.gov.br", r17));
        Supervisor s3 = supervisorRepository.save(new Supervisor(null, "José Francisco Vilas Boas", "villas@emater.pr.gov.br", r17));
        Supervisor s4 = supervisorRepository.save(new Supervisor(null, "Vilmar Grando", "grando@emater.pr.gov.br", r17));

        Field fi1 = new Field(null, "Trevo", "", c1, f1, null);
        fi1.addSupervisor(s1);
        fieldRepository.save(fi1);

        Field fi2 = new Field(null, "Carnieletto", "", c3, f5, null);
        fi2.addSupervisor(s4);
        fieldRepository.save(fi2);

        Field fi3 = new Field(null, "Oldoni", "", c3, f4, null);
        fi3.addSupervisor(s4);
        fieldRepository.save(fi3);

        Field fi4 = new Field(null, "MIP e MID", "", c2, f2, null);
        fi4.addSupervisor(s2);
        fieldRepository.save(fi4);

        Field fi5 = new Field(null, "Bertoldo", "1", c3, f3, null);
        fi5.addSupervisor(s3);
        fieldRepository.save(fi5);

        Harvest h1 = harvestRepository.save(new Harvest(null, "Safra 2017/2018",
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-01"),
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2018-3-1")));

        SurveyField sf1 = new SurveyField();
        sf1.setHarvest(h1);
        sf1.setField(fi3);
        sf1.setSeedName("TMG 7262 RR1");
        sf1.setDateData(new DateData(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-1"),
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-8"),
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2018-02-26")));
        sf1.setQuestionData(new QuestionData(true, false));
        sf1.setSizeData(new SizeData(4.4, 10, 9));
        sf1.setProductivityData(new ProductivityData(161.7, 159.5, true));
        sf1.setLocationData(new LocationData(2.5, 3.5));
        surveyFieldRepository.save(sf1);

        SurveyField sf2 = new SurveyField();
        sf2.setHarvest(h1);
        sf2.setField(fi2);
        sf2.setSeedName("BMX RAIO Ipro");
        sf2.setDateData(new DateData(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-4"),
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-11"),
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2018-2-12")));
        sf2.setQuestionData(new QuestionData(false, true));
        sf2.setSizeData(new SizeData(18, 62, 13));
        sf2.setProductivityData(new ProductivityData(197, 182, true));
        sf2.setLocationData(new LocationData(3.5, 4.5));
        surveyFieldRepository.save(sf2);

        SurveyField sf3 = new SurveyField();
        sf3.setHarvest(h1);
        sf3.setField(fi5);
        sf3.setSeedName("TMG 7262 RR");
        sf3.setDateData(new DateData(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-4"),
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-9"),
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2018-2-20")));
        sf3.setQuestionData(new QuestionData(true, false));
        sf3.setSizeData(new SizeData(5.74, 35.09, 11));
        sf3.setProductivityData(new ProductivityData(137.5, 120, true));
        sf3.setLocationData(new LocationData(4.5, 5.5));
        surveyFieldRepository.save(sf3);

        SurveyField sf4 = new SurveyField();
        sf4.setHarvest(h1);
        sf4.setField(fi4);
        sf4.setSeedName("TMG -  7262");
        sf4.setDateData(new DateData(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-24"),
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-10"),
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2018-2-18")));
        sf4.setQuestionData(new QuestionData(true, false));
        sf4.setSizeData(new SizeData(3.63, 3.63, 9));
        sf4.setProductivityData(new ProductivityData(158.5, 158.5, true));
        sf4.setLocationData(new LocationData(6.5, 6.5));
        surveyFieldRepository.save(sf4);

        SurveyField sf5 = new SurveyField();
        sf5.setHarvest(h1);
        sf5.setField(fi1);
        sf5.setSeedName("P95R51");
        sf5.setDateData(new DateData(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-9-26"),
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-10-10"),
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2018-2-15")));
        sf5.setQuestionData(new QuestionData(false, false));
        sf5.setSizeData(new SizeData(7.26, 242, 15));
        sf5.setProductivityData(new ProductivityData(187, 170, true));
        sf5.setLocationData(new LocationData(7.5, 8.5));
        surveyFieldRepository.save(sf5);



        Pest p1  = pestRepository.save(Pest.builder().usualName("Lagarta da soja").scientificName("Anticarsia gemmatalis").pestSize(Pest.PestSize.GREATER_15CM).build());
        Pest p2  = pestRepository.save(Pest.builder().usualName("Lagarta da soja").scientificName("Anticarsia gemmatalis").pestSize(Pest.PestSize.SMALLER_15CM).build());
        Pest p3  = pestRepository.save(Pest.builder().usualName("Falsa medideira").scientificName("Chrysodeixis spp.").pestSize(Pest.PestSize.GREATER_15CM).build());
        Pest p4  = pestRepository.save(Pest.builder().usualName("Falsa medideira").scientificName("Chrysodeixis spp.").pestSize(Pest.PestSize.SMALLER_15CM).build());
        Pest p5  = pestRepository.save(Pest.builder().usualName("Lagarta das vagens").scientificName("Spodoptera spp.").pestSize(Pest.PestSize.GREATER_15CM).build());
        Pest p6  = pestRepository.save(Pest.builder().usualName("Lagarta das vagens").scientificName("Spodoptera spp.").pestSize(Pest.PestSize.SMALLER_15CM).build());
        Pest p7  = pestRepository.save(Pest.builder().usualName("Grupo Heliothinae").scientificName("").pestSize(Pest.PestSize.GREATER_15CM).build());
        Pest p8  = pestRepository.save(Pest.builder().usualName("Grupo Heliothinae").scientificName("Chrysodeixis spp.").pestSize(Pest.PestSize.SMALLER_15CM).build());
        Pest p9  = pestRepository.save(Pest.builder().usualName("Percevejo verde").scientificName("Nezara sp.").pestSize(Pest.PestSize.THIRD_TO_FIFTH_INSTAR).build());
        Pest p10 = pestRepository.save(Pest.builder().usualName("Percevejo verde").scientificName("Nezara sp.").pestSize(Pest.PestSize.ADULT).build());
        Pest p11 = pestRepository.save(Pest.builder().usualName("Percevejo verde pequeno").scientificName("Piezodorus sp.").pestSize(Pest.PestSize.THIRD_TO_FIFTH_INSTAR).build());
        Pest p12 = pestRepository.save(Pest.builder().usualName("Percevejo verde pequeno").scientificName("Piezodorus sp.").pestSize(Pest.PestSize.ADULT).build());
        Pest p13 = pestRepository.save(Pest.builder().usualName("Percevejo Marrom").scientificName("Eushistus sp.").pestSize(Pest.PestSize.THIRD_TO_FIFTH_INSTAR).build());
        Pest p14 = pestRepository.save(Pest.builder().usualName("Percevejo Marrom").scientificName("Eushistus sp.").pestSize(Pest.PestSize.ADULT).build());
        Pest p15 = pestRepository.save(Pest.builder().usualName("Percevejo Barriga verde").scientificName("Dichelops ssp.").pestSize(Pest.PestSize.THIRD_TO_FIFTH_INSTAR).build());
        Pest p16 = pestRepository.save(Pest.builder().usualName("Percevejo Barriga verde").scientificName("Dichelops ssp.").pestSize(Pest.PestSize.ADULT).build());
        Pest p17 = pestRepository.save(Pest.builder().usualName("Outros percevejos").scientificName("").pestSize(Pest.PestSize.THIRD_TO_FIFTH_INSTAR).build());
        Pest p18 = pestRepository.save(Pest.builder().usualName("Outros percevejos").scientificName("").pestSize(Pest.PestSize.ADULT).build());

        PestFluctuationSample sample1 = PestFluctuationSample.builder().collectedDate(LocalDate.now()).daysAfterEmergence(3).defoliation(4).growthPhase(GrowthPhase.R2).surveyFieldId(sf1.getId()).build();

        sample1.addOccurrence(p1, 1.3);
        sample1.addOccurrence(p2, 2.3);
        sample1.addOccurrence(p3, 3.3);
        sample1.addOccurrence(p3, 3.3);
        sample1.addOccurrence(p4, 4.3);
        sample1.addOccurrence(p4, 4.3);

        sampleRepository.save(sample1);

    }

}
