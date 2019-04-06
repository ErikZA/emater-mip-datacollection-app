package br.edu.utfpr.cp.emater.midmipsystem.view.mid;

import br.edu.utfpr.cp.emater.midmipsystem.domain.mid.RustMonitoringSample;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.utfpr.cp.emater.midmipsystem.domain.mid.RustMonitoringSampleRepository;
import br.edu.utfpr.cp.emater.midmipsystem.domain.survey.SurveyField;
import br.edu.utfpr.cp.emater.midmipsystem.service.survey.SurveyFieldService;

import org.springframework.core.env.Environment;

@Controller
@RequestMapping (value = "/mid/rust-monitoring")
public class RustMonitoringController {

    private RustMonitoringSampleRepository rustMonitoringSampleRepository;
    private SurveyFieldService surveyFieldService;

    private final Environment environment;

    private boolean operationSuccessMessage;

    private void resetOperationSuccessMessage() {
        if (this.operationSuccessMessage)
            this.operationSuccessMessage = false;
    }

    private void setOperationSuccessMessage() {
        this.operationSuccessMessage = true;
    }


    @Autowired
    public RustMonitoringController(Environment environment, RustMonitoringSampleRepository rustMonitoringSampleRepository, SurveyFieldService surveyFieldService) {
        this.environment = environment;
        this.rustMonitoringSampleRepository = rustMonitoringSampleRepository;
        this.surveyFieldService = surveyFieldService;
        this.operationSuccessMessage = false;
    }

    private List<SurveyFieldDTO> populateSurveyFieldDTOList () {
        List<RustMonitoringSample> allRustMonitoringSamples = rustMonitoringSampleRepository.findAll();

        if (allRustMonitoringSamples.size() > 0) {
            var surveyFieldDTOs = new ArrayList<SurveyFieldDTO>();
            
            for (RustMonitoringSample aRMS: allRustMonitoringSamples) 
                surveyFieldDTOs.add(surveyFieldService.convertFrom(surveyFieldService.findById(aRMS.getSurveyFieldId()).orElseThrow()));
            
            return surveyFieldDTOs;
        } else
            return new ArrayList<>();
    }

    @RequestMapping (value = "/list", method = RequestMethod.GET)
    public String listAll(Model data) {

        data.addAttribute("surveyFieldList", this.populateSurveyFieldDTOList());
        data.addAttribute("success", this.operationSuccessMessage);

        this.resetOperationSuccessMessage();
                
        return this.environment.getProperty("app.view.route.template.list.mid.rust-monitoring");
    }

    @RequestMapping (value = "/create", method = RequestMethod.GET)
    public String create(@RequestParam int surveyFieldId, Model data) {
        data.addAttribute("asiaticRustTypesSpore", RustMonitoringSampleDTO.AsiaticRustTypesSporeCollector.values());
        data.addAttribute("asiaticRustTypesInspection", RustMonitoringSampleDTO.AsiaticRustTypesInspection.values());
        data.addAttribute("growthPhases", RustMonitoringSampleDTO.GrowthPhase.values());
        data.addAttribute("surveyFieldId", surveyFieldId);

        return this.environment.getProperty("app.view.route.template.sample.create.rust-monitoring"); 
    }
}