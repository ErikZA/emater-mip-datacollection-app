package br.edu.utfpr.cp.emater.midmipsystem.service.mip;

import br.edu.utfpr.cp.emater.midmipsystem.service.survey.*;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.MIPSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.Pest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestDisease;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.PestNaturalPredator;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.SupervisorNotAllowedInCity;
import br.edu.utfpr.cp.emater.midmipsystem.repository.mip.MIPSampleRepository;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class MIPSampleService {

    private final MIPSampleRepository mipSampleRepository;
    private final SurveyService surveyService;
    private final PestService pestService;
    private final PestDiseaseService pestDiseaseService;
    private final PestNaturalPredatorService pestNaturalPredatorService;

    @Autowired
    public MIPSampleService(MIPSampleRepository aMIPSampleRepository,
            SurveyService aSurveyService,
            PestService aPestService,
            PestDiseaseService aPestDiseaseService,
            PestNaturalPredatorService aPestNaturalPredatorService) {

        this.mipSampleRepository = aMIPSampleRepository;
        this.surveyService = aSurveyService;
        this.pestService = aPestService;
        this.pestDiseaseService = aPestDiseaseService;
        this.pestNaturalPredatorService = aPestNaturalPredatorService;
    }

    public List<MIPSample> readAll() {
        return List.copyOf(mipSampleRepository.findAll());
    }
    //utilizando mock este metodo não retorna todas as pesquisas vinculadas ao modelo MIP mas somente o indice 0 - Utilizanod o Repositorio lança um nullpointerException
    public List<Survey> readAllSurveysUniqueEntries() {
        return List.copyOf(mipSampleRepository.findAll().stream().map(MIPSample::getSurvey).distinct().collect(Collectors.toList()));
    }
    //SupervisorNotAllowedInCity nuca é lançada.
    public void create(MIPSample aSample) throws SupervisorNotAllowedInCity, EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException {

        if (mipSampleRepository.findAll().stream().anyMatch(currentSample -> currentSample.equals(aSample))) {
            throw new EntityAlreadyExistsException();
        }

        var theSurvey = surveyService.readById(aSample.getSurvey().getId());

        aSample.setSurvey(theSurvey);
        aSample.setDaysAfterEmergence(this.calculateDaysAfterEmergence(theSurvey.getEmergenceDate(), aSample.getSampleDate()));

        try {
            mipSampleRepository.save(aSample);

        } catch (Exception e) {
            throw new AnyPersistenceException();

        }

    }

    private int calculateDaysAfterEmergence(Date emergenceDate, Date sampleDate) {

        long diffInMillies = Math.abs(sampleDate.getTime() - emergenceDate.getTime());
        
        var result = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        
        return (int) (result + 1);
    }

    public MIPSample readById(Long anId) throws EntityNotFoundException {
        return mipSampleRepository.findById(anId).orElseThrow(EntityNotFoundException::new);
    }

    public void delete(Long anId) throws EntityNotFoundException, EntityInUseException, AnyPersistenceException {

        var existentSample = mipSampleRepository.findById(anId).orElseThrow(EntityNotFoundException::new);

        try {
            mipSampleRepository.delete(existentSample);

        } catch (DataIntegrityViolationException cve) {
            throw new EntityInUseException();

        } catch (Exception e) {
            throw new AnyPersistenceException();
        }

    }

    public Survey readSurveyById(Long id) throws EntityNotFoundException {
        return surveyService.readById(id);
    }

    public List<Pest> readAllPests() {
        return pestService.readAll();
    }

    public List<PestDisease> readAllPestDiseases() {
        return pestDiseaseService.readAll();
    }

    public List<PestNaturalPredator> readAllPestNaturalPredators() {
        return pestNaturalPredatorService.readAll();
    }

    public List<MIPSample> readAllMIPSampleBySurveyId(Long aSurveyId) {
        return List.copyOf(mipSampleRepository.findAll().stream().filter(sample -> sample.getSurvey().getId().equals(aSurveyId)).collect(Collectors.toList()));
    }
}
