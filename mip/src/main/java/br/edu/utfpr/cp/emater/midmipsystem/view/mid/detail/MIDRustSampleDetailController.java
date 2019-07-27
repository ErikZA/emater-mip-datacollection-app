package br.edu.utfpr.cp.emater.midmipsystem.view.mid.detail;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mid.MIDRustSample;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.mid.MIDRustSampleService;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component(value = "midRustSampleDetailController")
@SessionScope
public class MIDRustSampleDetailController {

    private final MIDRustSampleService midRustSampleService;

    @Setter
    @Getter
    private Survey currentSurvey;

    @Autowired
    public MIDRustSampleDetailController(MIDRustSampleService aMIDRustSampleService) {
        this.midRustSampleService = aMIDRustSampleService;
    }

    public List<MIDRustSample> readAllMIDRustSampleByCurrentSurvey() {
        return midRustSampleService.readAllMIPSampleBySurveyId(this.getCurrentSurvey().getId());
    }

    public String selectTargetSurvey(Long id) {

        try {
            this.setCurrentSurvey(midRustSampleService.readSurveyById(id));

            return "/mid/rust-sample/sample-detail/view-sample-details.xhtml?faces-redirect=true";

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Monitoramento da ferrugem não pode ser feito porque a UR não foi encontrada na base de dados!"));
            return "index.xhtml";
        }
    }
}
