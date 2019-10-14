package br.edu.utfpr.cp.emater.midmipsystem.service.pulverisation;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.GrowthPhase;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.*;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.pulverisation.ProductRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.pulverisation.PulverisationOperationRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.pulverisation.TargetRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.SurveyRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
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

import static org.junit.Assert.*;


import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PulverisationOperationServiceTest {

    @MockBean
    private TargetRepository targetRepository;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private SurveyRepository surveyRepository;
    @MockBean
    private PulverisationOperationRepository pulverisationOperationRepository;


    @Autowired
    private PulverisationOperationService pulverisationOperationService;

    private Target target1, target2, target3;
    private Product product1, product2, product3, product4;
    private Survey survey1, survey2, survey3;
    private PulverisationOperation pulverisationOperation1, pulverisationOperation2, pulverisationOperation3, pulverisationOperation4;

    @Before
    public void setUp() throws ParseException {

        this.survey1 = Survey.builder()
                .id((long)1)
                .harvest(Harvest.builder().id((long)1).name("TestCase1").build())
                .field(Field.builder().id((long)1).name("TestCase").build())
                .seedName("Test 222 BR1")
                .sowedDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2007-10-1"))
                .emergenceDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2007-10-8"))
                .harvestDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2008-02-26"))
                .rustResistant(true)
                .build();

        this.survey2 = Survey.builder()
                .id((long)2)
                .harvest(Harvest.builder().id((long)2)  .name("TestCase2").build())
                .field(Field.builder().id((long)1).name("TestCase").build())
                .seedName("Test 111 BR2")
                .rustResistant(false)
                .sowedDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2007-10-1"))
                .emergenceDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2007-10-8"))
                .harvestDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2008-02-26"))
                .build();

        this.survey3 = Survey.builder()
                .id((long)3)
                .harvest(Harvest.builder().id((long)31)  .name("TestCase3").build())
                .field(Field.builder().id((long)122).name("TestCaseField").build())
                .sowedDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2007-10-1"))
                .emergenceDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2007-10-8"))
                .harvestDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2008-02-26"))
                .seedName("Test 213 BR2")
                .build();


        this.target1 = Target.builder().id((long) 232).description("Test Target1").category(TargetCategory.ADJUVANTE).build();
        this.target2 = Target.builder().id((long) 343).description("Test Target2").category(TargetCategory.HERBICIDA).build();
        this.target3 = Target.builder().id((long) 545).description("Test Target3").category(TargetCategory.ADJUVANTE).build();

        this.product1 = Product.builder().id((long) 121).unit(ProductUnit.L).name("Veneno Amarelo").target(this.target1).build();
        this.product2 = Product.builder().id((long) 232).unit(ProductUnit.G).name("Veneno Laranja").target(this.target2).build();
        this.product3 = Product.builder().id((long) 454).unit(ProductUnit.KG).name("Veneno Vermelho").target(this.target1).build();
        this.product4 = Product.builder().id((long) 787).unit(ProductUnit.ML).name("Veneno Vermelho").target(this.target3).build();

        this.pulverisationOperation1 = PulverisationOperation.builder().id((long) 333).survey(this.survey1)
                .growthPhase(GrowthPhase.R1).soyaPrice(11.11).sampleDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2777-10-1")).build();
        this.pulverisationOperation2 = PulverisationOperation.builder().id((long) 444).survey(this.survey2)
                .growthPhase(GrowthPhase.R2).soyaPrice(22.22).sampleDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2888-10-1")).build();
        this.pulverisationOperation3 = PulverisationOperation.builder().id((long) 555).survey(this.survey1)
                .growthPhase(GrowthPhase.R3).soyaPrice(33.33).sampleDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2999-10-1")).build();
        this.pulverisationOperation4 = PulverisationOperation.builder().id((long) 666).survey(this.survey3)
                .growthPhase(GrowthPhase.R4).soyaPrice(133.33).sampleDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("1999-10-1")).build();

        List<Target> targets = asList(this.target1,this.target2);
        when(this.targetRepository.findAll())
                .thenReturn(targets);

        List<Product> products= asList(this.product1,this.product2,this.product3);
        when(this.productRepository.findAll())
                .thenReturn(products);

        List<Survey> listSurvey = asList(this.survey1,this.survey2);
        when(this.surveyRepository.findAll())
                .thenReturn(listSurvey);

        List<PulverisationOperation> pulverisationOperations =
                asList(this.pulverisationOperation1,this.pulverisationOperation2, this.pulverisationOperation3);
        when(this.pulverisationOperationRepository.findAll())
                .thenReturn(pulverisationOperations);
    }

    @Test
    public void readAllSurveysUniqueEntriesPulverisationOperationServiceTest() {
        assertThat(this.pulverisationOperationService.readAllSurveysUniqueEntries()).doesNotContainNull().isNotEmpty()
                .contains(this.survey1,this.survey2).doesNotContain(this.survey3);
    }

    @Test
    public void createPulverisationOperationServiceTest() throws AnyPersistenceException, EntityNotFoundException, EntityAlreadyExistsException {
        when(this.pulverisationOperationRepository.findById((long) 666))
                .thenReturn(java.util.Optional.ofNullable(null));
        when(this.pulverisationOperationRepository.save(this.pulverisationOperation4)).thenReturn(this.pulverisationOperation4);

    try {
        this.pulverisationOperationService.readById(this.pulverisationOperation4.getId());
        Assertions.fail("EntityNotFoundException it is not throws");
    } catch (EntityNotFoundException e){    }

        this.pulverisationOperationService.create(this.pulverisationOperation4);

        when(this.pulverisationOperationRepository.findById((long) 666))
                .thenReturn(java.util.Optional.ofNullable(this.pulverisationOperation4));

           PulverisationOperation pulverisationOperationTemp = this.pulverisationOperationService.readById((long) 666);
           assertThat(pulverisationOperationTemp.getSurvey()).isEqualTo(this.survey3);
        assertThat(pulverisationOperationTemp.getSoyaPrice()).isEqualTo(133.33);
        assertThat(pulverisationOperationTemp.getGrowthPhase()).isEqualTo(GrowthPhase.R4);
    }

    @Test
    public void createCalcDaysEmergencePulverisationOperationServiceTest() throws AnyPersistenceException, EntityNotFoundException, EntityAlreadyExistsException {
        int esperado = 2930;

        when(this.pulverisationOperationRepository.findById((long) 666))
                .thenReturn(java.util.Optional.ofNullable(null));
        when(this.pulverisationOperationRepository.save(this.pulverisationOperation4)).thenReturn(this.pulverisationOperation4);

        try {
            this.pulverisationOperationService.readById(this.pulverisationOperation4.getId());
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){    }

        this.pulverisationOperationService.create(this.pulverisationOperation4);

        when(this.pulverisationOperationRepository.findById((long) 666))
                .thenReturn(java.util.Optional.ofNullable(this.pulverisationOperation4));

        PulverisationOperation pulverisationOperationTemp = this.pulverisationOperationService.readById((long) 666);
        assertThat(pulverisationOperationTemp.getDaysAfterEmergence()).isEqualTo(esperado);
    }

    @Test(expected = AnyPersistenceException.class)
    public void createPulverisationOperationServiceAnyPersistenceExceptionTest() throws AnyPersistenceException, EntityNotFoundException, EntityAlreadyExistsException {
        when(this.pulverisationOperationRepository.findById((long) 666))
                .thenReturn(java.util.Optional.ofNullable(null));
        doThrow(IllegalArgumentException.class).when(this.pulverisationOperationRepository).save(any(PulverisationOperation.class));

        try {
            this.pulverisationOperationService.readById(this.pulverisationOperation4.getId());
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){    }

        this.pulverisationOperationService.create(this.pulverisationOperation4);

        Assertions.fail("AnyPersistenceException it is not throws");
     }

    @Test (expected = EntityAlreadyExistsException.class)
    public void createPulverisationOperationServiceEntityAlreadyExistsExceptionTest() throws AnyPersistenceException, EntityNotFoundException, EntityAlreadyExistsException {

        this.pulverisationOperationService.create(this.pulverisationOperation1);

        Assertions.fail("EntityAlreadyExistsException it is not throws");
    }

    @Ignore // EntityNotFoundException nunca é lançada
    @Test (expected = EntityNotFoundException.class)
    public void createPulverisationOperationServiceEntityNotFoundExceptionTest() throws AnyPersistenceException, EntityNotFoundException, EntityAlreadyExistsException {
        when(this.pulverisationOperationRepository.findById((long) 666))
                .thenReturn(java.util.Optional.ofNullable(null));
            this.pulverisationOperationService.readById(this.pulverisationOperation4.getId());
            Assertions.fail("EntityNotFoundException it is not throws");
            this.pulverisationOperationService.create(this.pulverisationOperation4);
    }

    @Test
    public void readByIdPulverisationOperationServiceTest() throws EntityNotFoundException {
        when(this.pulverisationOperationRepository.findById((long) 333))
                .thenReturn(java.util.Optional.ofNullable(this.pulverisationOperation1));
        assertThat(this.pulverisationOperationService.readById((long) 333)).isEqualTo(this.pulverisationOperation1);
    }


    @Test (expected = EntityNotFoundException.class)
    public void readByIdPulverisationOperationServiceEntityNotFoundExceptionTest() throws EntityNotFoundException {
        when(this.pulverisationOperationRepository.findById((long) 1333))
                .thenReturn(java.util.Optional.ofNullable(null));
        this.pulverisationOperationService.readById((long) 1333);
        Assertions.fail("EntityNotFoundException it is not throws");
    }

    @Test
    public void deletePulverisationOperationServiceTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.pulverisationOperationRepository.findById((long) 333))
                .thenReturn(java.util.Optional.ofNullable(this.pulverisationOperation1));
        doNothing().when(this.pulverisationOperationRepository).delete(any(PulverisationOperation.class));

        this.pulverisationOperationService.delete((long) 333);

        when(this.pulverisationOperationRepository.findById((long) 333))
                .thenReturn(java.util.Optional.ofNullable(null));

        try {
            this.pulverisationOperationService.readById((long) 333);
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){
            assertThat(e.getClass()).isEqualTo(EntityNotFoundException.class);
        }
    }

    @Test (expected = EntityNotFoundException.class)
    public void deletePulverisationOperationEntityNotFoundExceptionServiceTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.pulverisationOperationRepository.findById((long) 1333))
                .thenReturn(java.util.Optional.ofNullable(null));
        this.pulverisationOperationService.delete((long) 1333);

            Assertions.fail("EntityNotFoundException it is not throws");
    }

    @Test(expected = AnyPersistenceException.class)
    public void deletePulverisationOperationServiceAnyPersistenceExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.pulverisationOperationRepository.findById((long) 333))
                .thenReturn(java.util.Optional.ofNullable(this.pulverisationOperation1));
        doThrow(IllegalArgumentException.class).when(this.pulverisationOperationRepository).delete(any(PulverisationOperation.class));

        this.pulverisationOperationService.delete((long) 333);

        Assertions.fail("AnyPersistenceException it is not throws");
    }

    @Test (expected = EntityInUseException.class)
    public void deletePulverisationOperationServiceEntityInUseExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.pulverisationOperationRepository.findById((long) 333))
                .thenReturn(java.util.Optional.ofNullable(this.pulverisationOperation1));
        doThrow(DataIntegrityViolationException.class).when(this.pulverisationOperationRepository).delete(any(PulverisationOperation.class));

        this.pulverisationOperationService.delete((long) 333);

        Assertions.fail("EntityInUseException it is not throws");

    }

    @Test
    public void readSurveyByIdPulverisationOperationServiceTest() throws EntityNotFoundException {
        when(this.surveyRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.survey1));
        assertThat(this.pulverisationOperationService.readSurveyById((long) 1)).isEqualTo(this.survey1);
    }


    @Test(expected = EntityNotFoundException.class)
    public void readSurveyByIdPulverisationOperationServiceEntityNotFoundExceptionTest() throws EntityNotFoundException {
        when(this.surveyRepository.findById((long) 221))
                .thenReturn(java.util.Optional.ofNullable(null));
        this.pulverisationOperationService.readSurveyById((long) 221);
        fail("EntityNotFoundException it is not throws");
    }

    @Test
    public void readAllPulverisationOperationBySurveyIdPulverisationOperationServiceTest() {
        assertThat(this.pulverisationOperationService.readAllPulverisationOperationBySurveyId((long) 1)).doesNotContainNull().isNotEmpty()
                .contains(this.pulverisationOperation1,this.pulverisationOperation3)
                .doesNotContain(this.pulverisationOperation2,this.pulverisationOperation4);

        assertThat(this.pulverisationOperationService.readAllPulverisationOperationBySurveyId((long) 122))
                        .isEmpty();
    }

    @Test
    public void readAllTargetsByCategoryPulverisationOperationServiceTest() {
        this.target1.setLastModified((long) 1);
        this.target2.setLastModified((long) 2);
        this.target3.setLastModified((long) 3);
        assertThat(this.pulverisationOperationService.readAllTargetsByCategory(TargetCategory.ADJUVANTE))
                .doesNotContainNull().isNotEmpty()
                .contains(this.target1)
                .doesNotContain(this.target2,this.target3);

        assertThat(this.pulverisationOperationService.readAllTargetsByCategory(TargetCategory.HERBICIDA))
                .doesNotContainNull().isNotEmpty()
                .contains(this.target2)
                .doesNotContain(this.target1,this.target3);

        assertThat(this.pulverisationOperationService.readAllTargetsByCategory(TargetCategory.OUTROS))
                .isEmpty();
    }

    @Test
    public void readAllProductByTargetPulverisationOperationServiceTest() {
        assertThat(this.pulverisationOperationService.readAllProductByTarget((long) 232))
                .doesNotContainNull().isNotEmpty()
                .contains(this.product1,this.product3)
                .doesNotContain(this.product2, this.product4);


        assertThat(this.pulverisationOperationService.readAllProductByTarget((long) 343))
                .doesNotContainNull().isNotEmpty()
                .contains(this.product2)
                .doesNotContain(this.product1, this.product3, this.product4);


        assertThat(this.pulverisationOperationService.readAllProductByTarget((long) 3343))
                .isEmpty();
    }

    @Test
    public void readProductByIdPulverisationOperationServiceTest() throws EntityNotFoundException {
        when(this.productRepository.findById((long) 121)).thenReturn(java.util.Optional.ofNullable(this.product1));
        assertThat(this.pulverisationOperationService.readProductById((long) 121)).isEqualTo(this.product1);
    }

    @Test (expected = EntityNotFoundException.class)
    public void readProductByIdPulverisationOperationServiceEntityNotFoundExceptionTest() throws EntityNotFoundException {
        when(this.productRepository.findById((long) 1221)).thenReturn(java.util.Optional.ofNullable(null));
        this.pulverisationOperationService.readProductById((long) 1221);
        fail("EntityNotFoundException it is not throws");
    }

    @Test
    public void readTargetByIdPulverisationOperationServiceTest() throws EntityNotFoundException {
        when(this.targetRepository.findById((long) 232)).thenReturn(java.util.Optional.ofNullable(this.target1));
        assertThat(this.pulverisationOperationService.readTargetById((long) 232)).isEqualTo(this.target1);
    }

    @Test (expected = EntityNotFoundException.class)
    public void readTargetByIdPulverisationOperationServiceEntityNotFoundExceptionTest() throws EntityNotFoundException {
        when(this.targetRepository.findById((long) 1221)).thenReturn(java.util.Optional.ofNullable(null));
        this.pulverisationOperationService.readTargetById((long) 1221);
        fail("EntityNotFoundException it is not throws");
    }
}