package br.edu.utfpr.cp.emater.midmipsystem.view.mip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.utfpr.cp.emater.midmipsystem.domain.mip.MipPestSurvey;
import br.edu.utfpr.cp.emater.midmipsystem.domain.mip.MipPestSurveyRepository;
import br.edu.utfpr.cp.emater.midmipsystem.domain.mip.SamplePestRepository;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping (value = "/pest-survey/list-collected-samples")
public class SampleController {

    private final SamplePestRepository samplePestRepository;
    private final MipPestSurveyRepository mipPestSurveyRepository;

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
    public SampleController(SamplePestRepository samplePestRepository, MipPestSurveyRepository mipPestSurveyRepository, Environment environment) {
        this.samplePestRepository = samplePestRepository;
        this.mipPestSurveyRepository = mipPestSurveyRepository;
        this.environment = environment;
        this.operationSuccessMessage = false;
    }
    
    @RequestMapping (value = "", method = RequestMethod.GET)
    public String listAll(@RequestParam int mipPestSurveyId, Model data) {
        MipPestSurvey mipPestSurvey = mipPestSurveyRepository.findById(new Long(mipPestSurveyId)).orElseThrow();

        data.addAttribute("mipPestSurvey", mipPestSurvey);
        data.addAttribute("samplePests", samplePestRepository.findAll().stream().filter(e -> e.getMipPestSurvey().getId().equals(mipPestSurvey.getId())).collect(Collectors.toList()));
        data.addAttribute("success", this.operationSuccessMessage);

        this.resetOperationSuccessMessage();
        
        data.addAttribute("urlDelete", this.environment.getProperty("app.view.route.delete.mip.pest"));
        
        return this.environment.getProperty("app.view.route.mip.list.collected-samples");
    }
    
}