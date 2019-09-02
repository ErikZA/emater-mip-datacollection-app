package br.edu.utfpr.cp.emater.midmipsystem.service.mip;


import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.*;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mip.MIPSampleRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mip.PestDiseaseRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mip.PestNaturalPredatorRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mip.PestRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.SurveyRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.longThat;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MIPSampleServiceTest {


    @Autowired
    private MIPSampleService mipSampleService;

    @MockBean
    private SurveyRepository surveyRepository;
    @MockBean
    private MIPSampleRepository mipSampleRepository;
    @MockBean
    private PestRepository pestRepository;
    @MockBean
    private PestNaturalPredatorRepository pestNaturalPredatorRepository;
    @MockBean
    private  PestDiseaseRepository pestDiseaseRepository;

    private Pest pest1, pest2;
    private MIPSample mipSample1, mipSample2;
    private Survey survey1, survey2;
    private  PestNaturalPredator pestNaturalPredator1, pestNaturalPredator2;
    private PestDisease pestDisease1, pestDisease2;


    @Before
    public void setUp() throws ParseException {

        this.survey1 = Survey.builder()
                .id((long)1)
                .harvest(Harvest.builder().id((long)1).name("TestCase").build())
                .field(Field.builder().id((long)1).name("TestCase").build())
                .seedName("Test 222 BR1")
                .sowedDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2007-10-1"))
                .emergenceDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2007-10-8"))
                .harvestDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2008-02-26"))
                .rustResistant(true)
                .bt(false)
                .totalArea(1.4)
                .totalPlantedArea(8)
                .plantPerMeter(3)
                .productivityField(141.7)
                .productivityFarmer(159.5)
                .separatedWeight(true)
                .longitude(1.5)
                .latitude(2.5)
                .build();

        this.survey2 = Survey.builder()
                .id((long)2)
                .harvest(Harvest.builder().id((long)2)  .name("TestCase").build())
                .field(Field.builder().id((long)1).name("TestCase").build())
                .seedName("Test 111 BR2")
                .sowedDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2006-10-4"))
                .emergenceDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2006-10-11"))
                .harvestDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2006-2-12"))
                .rustResistant(false)
                .bt(true)
                .totalArea(45)
                .totalPlantedArea(72)
                .plantPerMeter(19)
                .productivityField(157)
                .productivityFarmer(122)
                .separatedWeight(false)
                .longitude(3)
                .latitude(4.9)
                .build();

        this.pest1 = Pest.builder().id((long)1).usualName("Lagarta Amarela da soja").scientificName("Anticarsia gemmatalis").pestSize(PestSize.GREATER_15CM).build();
        this.pest2 = Pest.builder().id((long)2).usualName("Lagarta Marron da soja").scientificName("Anticarsia gemmatalis").pestSize(PestSize.SMALLER_15CM).build();
        this.pestNaturalPredator1 = PestNaturalPredator.builder().id((long)1).usualName("Calosoma granulatum Testa").build();
        this.pestNaturalPredator2 = PestNaturalPredator.builder().id((long)2).usualName("Callida sp Testa.").build();
        this.pestDisease1 = PestDisease.builder().id((long)1).usualName("Lagarta com Nomuraea rileyi (Doença Branca)").build();
        this.pestDisease2 = PestDisease.builder().id((long)2).usualName("Lagarta com Baculovírus (Doença Preta)").build();

        this.mipSample1 = MIPSample.builder().id((long)1)
                .daysAfterEmergence(51)
                .defoliation(3)
                .growthPhase(GrowthPhase.R1)
                .sampleDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-11-29"))
                .survey(survey1)
                .build();

        this.mipSample1.addPestOccurrence(pest1, 3.0);
        this.mipSample1.addPestDiseaseOccurrence(pestDisease1, 0.9);
        this.mipSample1.addPestNaturalPredatorOccurrence(pestNaturalPredator1, 4.0);

        this.mipSample2 = MIPSample.builder().id((long)2)
                .daysAfterEmergence(18)
                .defoliation(0)
                .growthPhase(GrowthPhase.V2)
                .sampleDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2017-11-16"))
                .survey(survey2)
                .build();

        this.mipSample2.addPestOccurrence(pest2, 3.0);
        this.mipSample2.addPestDiseaseOccurrence(pestDisease2, 0.9);
        this.mipSample2.addPestNaturalPredatorOccurrence(pestNaturalPredator2, 4.0);


        List<Survey> listSurvey = asList(this.survey1,this.survey2);
        when(this.surveyRepository.findAll())
                .thenReturn(listSurvey);

        List<Pest> listPest = asList(pest1,pest2);
        when(this.pestRepository.findAll()).thenReturn(listPest);

        List<PestNaturalPredator> listPestNaturalPredator = asList(this.pestNaturalPredator1,this.pestNaturalPredator2);
        when(this.pestNaturalPredatorRepository.findAll()).thenReturn(listPestNaturalPredator);

        List<PestDisease> listPestDisease= asList(this.pestDisease1,this.pestDisease2);
        when(this.pestDiseaseRepository.findAll()).thenReturn(listPestDisease);

        List<MIPSample> listMIP= asList(this.mipSample1,this.mipSample2);
        when(this.mipSampleRepository.findAll()).thenReturn(listMIP);

    }

    @Test
    public void mipSampleServiceTestReadAll(){
        assertThat(this.mipSampleService.readAll())
                .containsExactlyInAnyOrder(this.mipSample1,this.mipSample2)
                .doesNotContainNull()
                .isNotEmpty();
    }

    //Deveria retornar somente uma survey??
    @Test
    public void mipSampleServiceTestReadAllSurveysUniqueEntries(){

        assertThat(this.mipSampleService.readAllSurveysUniqueEntries())
                .containsExactly(this.survey1)
                .doesNotContainNull()
                .isNotEmpty();
    }

    @Test
    public void mipSampleServiceTestReadSurveyById() throws EntityNotFoundException {
        when(surveyRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.survey1));

        assertThat(this.mipSampleService.readSurveyById((long)1))
                .isEqualTo(this.survey1)
                .isNotNull();
    }


    @Test (expected = EntityNotFoundException.class)
    public void mipSampleServiceTestReadSurveyByIdEntityNotFoundException() throws EntityNotFoundException {
        when(surveyRepository.findById((long)5))
                .thenReturn(java.util.Optional.ofNullable(null));

        this.mipSampleService.readSurveyById((long)5);
        fail("EntityNotFoundException it is not throws");
    }

    @Test
    public void setMipSampleServiceTestReadAllPests() {

        assertThat(this.mipSampleService.readAllPests())
                .isNotEmpty()
                .isNotNull()
                .containsExactlyInAnyOrder(this.pest1,this.pest2);
    }

    @Test
    public void mipSampleServiceTestReadAllPestDiseases() {
        assertThat(this.mipSampleService.readAllPestDiseases())
                .isNotEmpty()
                .isNotNull()
                .containsExactlyInAnyOrder(this.pestDisease1,this.pestDisease2);
    }

    @Test
    public void mipSampleServiceTestReadAllPestNaturalPredators() {
        assertThat(this.mipSampleService.readAllPestNaturalPredators())
                .isNotEmpty()
                .isNotNull()
                .containsExactlyInAnyOrder(this.pestNaturalPredator1,this.pestNaturalPredator2);
    }

    @Test
    public void setMipSampleServiceTestReadAllMIPSampleBySurveyId() {
        assertThat(this.mipSampleService.readAllMIPSampleBySurveyId((long)1))
                .isNotNull()
                .containsExactly(this.mipSample1);
        assertThat(this.mipSampleService.readAllMIPSampleBySurveyId((long)2))
                .isNotNull()
                .containsExactly(this.mipSample2);
    }

    @Test
    public void setMipSampleServiceTestReadAllMIPSampleBySurveyIdNotFound() {
        assertThat(this.mipSampleService.readAllMIPSampleBySurveyId((long)111))
                .isEmpty();
    }

    @Test
    public void mipSampleServiceTestReadByIdMIPSample() throws EntityNotFoundException {
        when(this.mipSampleRepository.findById((long)1))
                .thenReturn(Optional.ofNullable(this.mipSample1));
        assertThat(this.mipSampleService.readById((long)1))
                .isEqualTo(this.mipSample1)
                .isNotNull();
    }

    @Test (expected =  EntityNotFoundException.class)
    public void mipSampleServiceTestReadByIdMIPSampleEntityNotFoundException() throws EntityNotFoundException {
        when(this.mipSampleRepository.findById((long)6))
                .thenReturn(Optional.ofNullable(null));
        this.mipSampleService.readById((long)6);
        fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void mipSampleServiceTestCreateMIPSampleEntityAlreadyExistsException() throws Exception{
        this.mipSampleService.create(this.mipSample1);
        fail("EntityAlreadyExistsException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void mipSampleServiceTestCreateMIPSampleAnyPersistenceException() throws Exception{
        MIPSample mipSample3 = MIPSample.builder().id((long)3)
                .daysAfterEmergence(43)
                .defoliation(2)
                .growthPhase(GrowthPhase.V2)
                .sampleDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2019-10-12"))
                .survey(this.survey2)
                .build();

        this.mipSample2.addPestOccurrence(this.pest1, 3.0);
        this.mipSample2.addPestDiseaseOccurrence(this.pestDisease2, 0.9);
        this.mipSample2.addPestNaturalPredatorOccurrence(this.pestNaturalPredator2, 4.0);

        when(this.surveyRepository.findById((long)2))
                .thenReturn(Optional.ofNullable(this.survey2));
        doThrow(IllegalArgumentException.class)
                .when(this.mipSampleRepository).save(any(MIPSample.class));

        this.mipSampleService.create(mipSample3);
        fail("AnyPersistenceException it is not throws");
    }

    @Test
    public void mipSampleServiceTestCreateAndCalculateDaysAfterEmergence() throws Exception{
        MIPSample mipSample3 = MIPSample.builder().id((long)3)
                .defoliation(2)
                .growthPhase(GrowthPhase.V2)
                .sampleDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2006-10-12"))
                .survey(survey2)
                .build();

        this.mipSample2.addPestOccurrence(this.pest1, 3.0);
        this.mipSample2.addPestDiseaseOccurrence(this.pestDisease2, 0.9);
        this.mipSample2.addPestNaturalPredatorOccurrence(this.pestNaturalPredator2, 4.0);

        when(this.surveyRepository.findById((long)2))
                .thenReturn(Optional.ofNullable(this.survey2));
        when(this.mipSampleRepository.save(mipSample3))
                .thenReturn(mipSample3);

        this.mipSampleService.create(mipSample3);
        assertThat(mipSample3.getDaysAfterEmergence()).isEqualTo(2);
    }

    @Test (expected = EntityNotFoundException.class)
    public void mipSampleServiceTestDeleteMIPSampleEntityNotFoundException() throws Exception{
        when(this.mipSampleRepository.findById((long)4))
                .thenReturn(Optional.ofNullable(null));
        this.mipSampleService.delete((long)4);
        fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = EntityInUseException.class)
    public void mipSampleServiceTestDeleteMIPSampleEntityInUseException() throws Exception{
        when(this.mipSampleRepository.findById((long)1))
                .thenReturn(Optional.ofNullable(this.mipSample1));
        doThrow(DataIntegrityViolationException.class)
                .when(this.mipSampleRepository).delete(this.mipSample1);
        this.mipSampleService.delete((long)1);
        fail("EntityInUseException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void mipSampleServiceTestDeleteMIPSampleAnyPersistenceException() throws Exception{
        when(this.mipSampleRepository.findById((long)1))
                .thenReturn(Optional.ofNullable(this.mipSample1));
        doThrow(IllegalArgumentException.class)
                .when(this.mipSampleRepository).delete(any(MIPSample.class));
        this.mipSampleService.delete((long)1);
        fail("AnyPersistenceException it is not throws");
    }

    @Test
    public void mipSampleServiceTestDeleteMIPSampleSucess() throws Exception{
        when(this.mipSampleRepository.findById((long)1))
                .thenReturn(Optional.ofNullable(this.mipSample1));
        doNothing().when(this.mipSampleRepository)
                .delete(this.mipSample1);
        this.mipSampleService.delete((long)1);
    }



}
