package br.edu.utfpr.cp.emater.midmipsystem.service.mid;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.State;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.BladeReadingResponsibleEntity;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.BladeReadingResponsiblePerson;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.MIDRustSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mid.BladeReadingResponsibleEntityRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mid.BladeReadingResponsiblePersonRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mid.MIDRustSampleRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.SurveyRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MIDRustSampleServiceTest {

    @MockBean
    private SurveyRepository surveyRepository;
    @MockBean
    private MIDRustSampleRepository midRustSampleRepository;
    @MockBean
    private BladeReadingResponsiblePersonRepository bladeReadingResponsiblePersonRepository;


    private BladeReadingResponsiblePerson bladeReadingResponsiblePerson1, bladeReadingResponsiblePerson2, bladeReadingResponsiblePerson3;
    private Survey survey1, survey2, survey3;
    private MIDRustSample midRustSample1, midRustSample2, midRustSample3;

    @Autowired
    private MIDRustSampleService midRustSampleService;


    @Before
    public void setUp() throws ParseException {
        this.survey1 = Survey.builder()
                .id((long)101)
                .harvest(Harvest.builder().id((long)1).name("TestCase1").build())
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
                .id((long)202)
                .harvest(Harvest.builder().id((long)2)  .name("TestCase2").build())
                .field(Field.builder().id((long)1).name("TestCase").build())
                .seedName("Test 111 BR2")
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

        this.survey3 = Survey.builder()
                .id((long)303)
                .harvest(Harvest.builder().id((long)31)  .name("TestCase3").build())
                .field(Field.builder().id((long)122).name("TestCaseField").build())
                .seedName("Test 213 BR2")
                .build();


        this.bladeReadingResponsiblePerson1 = BladeReadingResponsiblePerson.builder().name("Test Sistem1").id((long) 99)
                .entity(BladeReadingResponsibleEntity.builder().id((long) 33).name("Test Service")
                        .city(City.builder().name("Nova Fatima").state(State.PR).build()).build()).build();
        this.bladeReadingResponsiblePerson2 = BladeReadingResponsiblePerson.builder().name("Test Abrobinha").id((long) 142)
                .entity(BladeReadingResponsibleEntity.builder().id((long) 223).name("Test Service Entity")
                        .city(City.builder().name("Nova Fatima").state(State.PR).build()).build()).build();
        this.bladeReadingResponsiblePerson3 = BladeReadingResponsiblePerson.builder().name("Test Pepino").id((long) 660)
                .entity(BladeReadingResponsibleEntity.builder().id((long) 94).name("Test Service Unit")
                        .city(City.builder().name("Nova Fatima").state(State.PR).build()).build()).build();

        this.midRustSample1 = MIDRustSample.builder().id((long) 123).survey(this.survey1)
                .sampleDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2226-2-12")).build();

        this.midRustSample2 = MIDRustSample.builder().id((long) 223).survey(this.survey2)
                .sampleDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2346-2-12")).build();

        this.midRustSample3 = MIDRustSample.builder().id((long) 442).survey(this.survey3)
                .sampleDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2344-2-12")).build();

        List<BladeReadingResponsiblePerson> bladeReadingResponsiblePeople = asList(this.bladeReadingResponsiblePerson1,
                this.bladeReadingResponsiblePerson2);

        when(this.bladeReadingResponsiblePersonRepository.findAll())
                .thenReturn(bladeReadingResponsiblePeople);

        List<Survey> listSurvey = asList(this.survey1,this.survey2);
        when(this.surveyRepository.findAll())
                .thenReturn(listSurvey);

        List<MIDRustSample> midRustSamples = asList(this.midRustSample1, this.midRustSample2);
        when(this.midRustSampleRepository.findAll())
                .thenReturn(midRustSamples);

    }

    @Test
    public void readSurveyByIdMIDRustSampleServiceTest() throws EntityNotFoundException {
        when(this.surveyRepository.findById((long) 101)).thenReturn(java.util.Optional.ofNullable(this.survey1));
        assertThat(this.midRustSampleService.readSurveyById((long)101)).isEqualTo(this.survey1);
    }

    @Test (expected = EntityNotFoundException.class)
    public void readSurveyByIdMIDRustSampleServiceEntityNotFoundExceptionTest() throws EntityNotFoundException {
        when(this.surveyRepository.findById((long) 122)).thenReturn(java.util.Optional.ofNullable(null));
        this.midRustSampleService.readSurveyById((long)122);
        Assertions.fail("EntityNotFoundException it is not throws");
    }

    @Test
    public void readAllSurveysUniqueEntriesMIDRustSampleServiceTest() {
        assertThat(this.midRustSampleService.readAllSurveysUniqueEntries()).doesNotContainNull().isNotEmpty()
                .contains(this.survey1,this.survey2).doesNotContain(this.survey3);
    }

    @Test
    public void createMIDRustSampleServiceTest() {
        when(this.surveyRepository.findById((long) 303)).thenReturn(java.util.Optional.ofNullable(this.survey3));
        when(this.midRustSampleRepository.save(this.midRustSample3)).thenReturn(this.midRustSample3);
        try {
            this.midRustSampleService.create(this.midRustSample3);
        } catch (Exception e){
            Assertions.fail("it don't throws exception");
        }
    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void createMIDRustSampleServiceEntityAlreadyExistsExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
            this.midRustSampleService.create(this.midRustSample1);
        Assertions.fail("EntityAlreadyExistsException it is not throws");
    }

    @Test (expected = EntityNotFoundException.class)
    public void createMIDRustSampleServiceEntityNotFoundExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.surveyRepository.findById((long) 88)).thenReturn(java.util.Optional.ofNullable(null));
        this.midRustSample3.setSurvey(Survey.builder().id((long) 88).seedName("TEstFail").rustResistant(false).plantPerMeter(334.3).build());
        this.midRustSampleService.create(this.midRustSample3);
        Assertions.fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void createMIDRustSampleServiceAnyPersistenceExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.surveyRepository.findById((long) 303)).thenReturn(java.util.Optional.ofNullable(this.survey3));
        doThrow(IllegalArgumentException.class).when(this.midRustSampleRepository).save(any());
        this.midRustSampleService.create(this.midRustSample3);
        Assertions.fail("AnyPersistenceException it is not throws");
    }

    @Test
    public void readAllMIPSampleBySurveyIdMIDRustSampleServiceTest() {
        assertThat(this.midRustSampleService.readAllMIPSampleBySurveyId((long) 101)).containsExactly(this.midRustSample1)
                .doesNotContain(this.midRustSample2,this.midRustSample3).isNotEmpty().doesNotContainNull();
    }

    @Test
    public void deleteMIDRustSampleServiceTest() {
        when(this.midRustSampleRepository.findById((long) 123)).thenReturn(java.util.Optional.ofNullable(this.midRustSample1));
        doNothing().when(this.midRustSampleRepository).delete(any(MIDRustSample.class));
        try {
            this.midRustSampleService.delete((long) 123);
        } catch (Exception e){
            Assertions.fail("<"+e.getMessage()+">"+"<"+e.toString()+">");
        }
    }

    @Test (expected = EntityNotFoundException.class)
    public void deleteMIDRustSampleServiceEntityNotFoundExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.midRustSampleRepository.findById((long) 1232)).thenReturn(java.util.Optional.ofNullable(null));
        doNothing().when(this.midRustSampleRepository).delete(any(MIDRustSample.class));
            this.midRustSampleService.delete((long) 1232);
        Assertions.fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void deleteMIDRustSampleServiceAnyPersistenceExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.midRustSampleRepository.findById((long) 123)).thenReturn(java.util.Optional.ofNullable(this.midRustSample1));
        doThrow(IllegalArgumentException.class).when(this.midRustSampleRepository).delete(any(MIDRustSample.class));
        this.midRustSampleService.delete((long) 123);
        Assertions.fail("AnyPersistenceException it is not throws");
    }

    @Test (expected = EntityInUseException.class)
    public void deleteMIDRustSampleServiceEntityInUseExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.midRustSampleRepository.findById((long) 123)).thenReturn(java.util.Optional.ofNullable(this.midRustSample1));
        doThrow(DataIntegrityViolationException.class).when(this.midRustSampleRepository).delete(any(MIDRustSample.class));
        this.midRustSampleService.delete((long) 123);
        Assertions.fail("EntityInUseException it is not throws");
    }

    @Test
    public void readAllBladeResponsiblePersonsMIDRustSampleServiceTest() {
        assertThat(this.midRustSampleService.readAllBladeResponsiblePersons()).doesNotContainNull().isNotEmpty()
                .doesNotContain(this.bladeReadingResponsiblePerson3).containsExactlyInAnyOrder(this.bladeReadingResponsiblePerson1,this.bladeReadingResponsiblePerson2);
    }
}