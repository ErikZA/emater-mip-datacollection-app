package br.edu.utfpr.cp.emater.midmipsystem.view.mip.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.utfpr.cp.emater.midmipsystem.domain.mip.PestFluctuationSample;
import br.edu.utfpr.cp.emater.midmipsystem.domain.mip.PestFluctuationSampleRepository;
import br.edu.utfpr.cp.emater.midmipsystem.domain.survey.SurveyField;
import br.edu.utfpr.cp.emater.midmipsystem.domain.survey.SurveyFieldRepository;
import br.edu.utfpr.cp.emater.midmipsystem.view.mip.dto.FieldPestFluctuationDTO;

@Controller
@RequestMapping(value = "/pest-survey")
public class PestFluctuationSampleController {

    private final PestFluctuationSampleRepository pestFluctuationSampleRepository;
    private final SurveyFieldRepository surveyFieldRepository;

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
    public PestFluctuationSampleController(PestFluctuationSampleRepository pestFluctuationSampleRepository, SurveyFieldRepository surveyFieldRepository, Environment environment) {
        this.pestFluctuationSampleRepository = pestFluctuationSampleRepository;
        this.surveyFieldRepository = surveyFieldRepository;
        this.environment = environment;
        this.operationSuccessMessage = false;
    }

    private List<FieldPestFluctuationDTO> retrieveFieldPestFluctuationDTO () {
        List<FieldPestFluctuationDTO> result = new ArrayList<>();

        pestFluctuationSampleRepository.findAll().stream().mapToLong(c -> c.getSurveyFieldId()).distinct().forEach(e -> {
            SurveyField sf = surveyFieldRepository.findById(e);
            FieldPestFluctuationDTO dto = FieldPestFluctuationDTO.builder().seedName(sf.getSeedName()).farmerName(sf.getField().getFarmer().getName()).fieldCity(sf.getField().getCity().getName()).fieldLocation(sf.getField().getLocation()).fieldName(sf.getField().getName()).harvestName(sf.getHarvest().getName()).supervisorNames(sf.getField().getSupervisors().stream().map(e -> e.getName()).collect(Collectors.toList())
        });
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listAll(Model data) {

        

        data.addAttribute("mipPestSurveys", );
        data.addAttribute("success", this.operationSuccessMessage);

        this.resetOperationSuccessMessage();

        return this.environment.getProperty("app.view.route.template.main.mip.pest-survey");
    }

}