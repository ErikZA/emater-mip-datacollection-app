package br.edu.utfpr.cp.emater.midmipsystem.view.mip.controller;

import br.edu.utfpr.cp.emater.midmipsystem.domain.mip.GrowthPhase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//@Controller
//@RequestMapping("/pest-survey")
public class SamplePestController {
    
//    private final MipPestSurveyRepository mipPestSurveyRepository;
//    private final PestRepository pestRepository;
//    private final SamplePestRepository samplePestRepository;
//
//    private final Environment environment;
//
//    private boolean operationSuccessMessage;
//
//    private void resetOperationSuccessMessage() {
//        if (this.operationSuccessMessage)
//            this.operationSuccessMessage = false;
//    }
//
//    private void setOperationSuccessMessage() {
//        this.operationSuccessMessage = true;
//    }    
//
//    @Autowired
//    public SamplePestController(MipPestSurveyRepository mipPestSurveyRepository, PestRepository pestRepository, SamplePestRepository samplePestRepository, Environment environment) {
//        this.mipPestSurveyRepository = mipPestSurveyRepository;
//        this.pestRepository = pestRepository;
//        this.samplePestRepository = samplePestRepository;
//        this.environment = environment;
//        this.operationSuccessMessage = false;
//    }
//    
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public String listAll(Model data) {
//        data.addAttribute("mipPestSurveys", mipPestSurveyRepository.findAll());
//        data.addAttribute("success", this.operationSuccessMessage);
//
//        this.resetOperationSuccessMessage();
//                
//        return this.environment.getProperty("app.view.route.template.main.mip.pest-survey");
//    }
//    
//    @RequestMapping(value = "/add-sample", method = RequestMethod.GET)
//    public String addSample(@RequestParam int mipPestSurveyId, Model data) {
//        data.addAttribute("pageTitle", "Nova Amostra");
//        
//        data.addAttribute("growthPhases", GrowthPhase.values());
//        data.addAttribute("pestList", pestRepository.findAll());
//        data.addAttribute("mipPestSurveyId", mipPestSurveyId);
//        
//        return this.environment.getProperty("app.view.route.create.sample.mip.pest-survey");
//    }
//
//    private int calculateDaysAfterEmergence (Date emergenceDate, Date sampleDate) {
//        int aDay = (24 * 60 * 60 * 1000);
//        return (int) ((sampleDate.getTime() - emergenceDate.getTime()) / aDay);
//    }
//
//    @PostMapping ("/save-sample")
//    public String saveSample(@RequestParam Map<String, String> values) {
//
//        SamplePest samplePest = validateEntries(values);
//        samplePest.setDaysAfterEmergence(
//            this.calculateDaysAfterEmergence(
//                samplePest.getMipPestSurvey().getSurveyField().getDateData().getEmergenceDate(),
//                samplePest.getSampleDate()
//            )
//        );
//
//        samplePestRepository.save(samplePest);
//
//        this.setOperationSuccessMessage();
//        
//        return this.environment.getProperty("app.view.route.create.success.mip.pest-survey");
//    }
//
//    private SamplePest validateEntries (Map<String, String> values) {
//
//        String mipPestSurveyId = values.entrySet().stream().filter(e -> e.getKey().equalsIgnoreCase("mipPestSurveyId")).findAny().orElseThrow(IllegalArgumentException::new).getValue();
//        String sampleDate = values.entrySet().stream().filter(e -> e.getKey().equalsIgnoreCase("sampleDate")).findAny().orElseThrow(IllegalArgumentException::new).getValue();
//        String growthPhase = values.entrySet().stream().filter(e -> e.getKey().equalsIgnoreCase("growthPhase")).findAny().orElseThrow(IllegalArgumentException::new).getValue();
//        String defoliation = values.entrySet().stream().filter(e -> e.getKey().equalsIgnoreCase("defoliation")).findAny().orElseThrow(IllegalArgumentException::new).getValue();
//
//        Date parsedSampleDate;
//
//        try {
//            parsedSampleDate = new SimpleDateFormat("yyyy-MM-dd").parse(sampleDate);
//
//        } catch (ParseException e1) {
//            throw new ClassCastException("Error parsing sample date");
//		}
//
//        SamplePest sp = new SamplePest();
//        sp.setMipPestSurvey(mipPestSurveyRepository.findById(new Long(mipPestSurveyId)).orElseThrow(IllegalArgumentException::new));
//        sp.setSampleDate(parsedSampleDate);
//        sp.setGrowthPhase(GrowthPhase.valueOf(growthPhase));
//        sp.setDefoliation(new Integer(defoliation));
//        sp.setPestOccurrenceList(validatePestEntries(values));
//
//        return sp;
//    }
//
//    private List<PestOccurrence> validatePestEntries (Map<String, String> values) {
//
//        List<PestOccurrence> occurrencesFound = null;
//
//        List<String> pestsFound = values.entrySet()
//                                        .stream()
//                                        .filter(e -> !e.getValue().isEmpty())
//                                        .map(e -> e.getKey())
//                                        .filter(e -> e.startsWith("pest_"))
//                                        .collect(Collectors.toList());
//
//        if (pestsFound == null || pestsFound.size() == 0)
//            throw new IllegalArgumentException("No pest was informed as a sample ");
//
//        else {
//            occurrencesFound = new ArrayList<>();
//            PestOccurrence currentPestOccurrence = null;
//
//            for (String pestFound: pestsFound) {
//                currentPestOccurrence = new PestOccurrence();
//
//                Long pestId = new Long(pestFound.split("_")[1]);
//                Pest currentPest = pestRepository.findById(pestId).orElseThrow(IllegalArgumentException::new);
//                currentPestOccurrence.setPest(currentPest);
//
//                currentPestOccurrence.setValue(new Double (values.get(pestFound))); 
//
//                occurrencesFound.add(currentPestOccurrence);
//            }
//        }
//        
//        return occurrencesFound;
//    }
    
}
