package br.edu.utfpr.cp.emater.midmipsystem.view.survey;

import br.edu.utfpr.cp.emater.midmipsystem.view.base.*;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Harvest;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.MacroRegionService;
import br.edu.utfpr.cp.emater.midmipsystem.view.ICRUDController;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.service.survey.HarvestService;
import br.edu.utfpr.cp.emater.midmipsystem.service.survey.SurveyService;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class SurveyController extends Harvest implements ICRUDController<Survey> {

    private final SurveyService surveyService;

    @Autowired
    public SurveyController(SurveyService aSurveyService) {
        this.surveyService = aSurveyService;
    }

    @Override
    public List<Survey> readAll() {
        return surveyService.readAll();
    }
//
//    @Override
//    public String create() {
//        
//        var newHarvest = Harvest.builder().name(this.getName()).begin(this.getBegin()).end(this.getEnd()).build();
//
//        try {
//            harvestService.create(newHarvest);
//
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", String.format("Safra [%s] criada com sucesso!", this.getName())));
//            return "index.xhtml";
//
//        } catch (EntityAlreadyExistsException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe uma safra iniciando e terminando nessas datas! Use datas diferentes."));
//            return "create.xhtml";
//
//        } catch (AnyPersistenceException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
//            return "index.xhtml";
//        }
//    }
//
//    @Override
//    public String prepareUpdate(Long anId) {
//
//        try {
//            var existentHarvest = harvestService.readById(anId);
//            this.setId(existentHarvest.getId());
//            this.setName(existentHarvest.getName());
//            this.setBegin(existentHarvest.getBegin());
//            this.setEnd(existentHarvest.getEnd());
//
//            return "update.xhtml";
//
//        } catch (EntityNotFoundException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Macrorregião não pode ser alterada porque não foi encontrada na base de dados!"));
//            return "index.xhtml";
//        }
//    }
//
//    @Override
//    public String update() {
//        
//        var updatedEntity = Harvest.builder()
//                                    .id(this.getId())
//                                    .name(this.getName())
//                                    .begin(this.getBegin())
//                                    .end(this.getEnd())
//                                    .build();
//
//        try {
//            harvestService.update(updatedEntity);
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Safra alterada!"));
//            return "index.xhtml";
//
//        } catch (EntityAlreadyExistsException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe uma safra começando e terminando nessas datas! Use um datas diferentes."));
//            return "update.xhtml";
//
//        } catch (EntityNotFoundException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Safra não pode ser alterada porque não foi encontrada na base de dados!"));
//            return "update.xhtml";
//
//        } catch (AnyPersistenceException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
//            return "index.xhtml";
//        }
//
//    }
//
//    @Override
//    public String prepareDelete(Long anId) {
//
//        try {
//            var existentHarvest = harvestService.readById(anId);
//            
//            this.setId(existentHarvest.getId());
//            this.setName(existentHarvest.getName());
//
//            return "delete.xhtml";
//
//        } catch (EntityNotFoundException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Safra não pode ser excluída porque não foi encontrada na base de dados!"));
//            return "index.xhtml";
//        }
//    }
//
//    @Override
//    public String delete() {
//        
//        try {
//            harvestService.delete(this.getId());
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Safra excluída!"));
//            return "index.xhtml";
//
//        } catch (EntityNotFoundException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Safra não pode ser excluída porque não foi encontrada na base de dados!"));
//            return "index.xhtml";
//            
//        } catch (EntityInUseException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Safra não pode ser excluída porque está sendo usada em uma pesquisa!"));
//            return "index.xhtml";
//            
//        } catch (AnyPersistenceException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na gravação dos dados!"));
//            return "index.xhtml";
//        }
//    }

    @Override
    public String create() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String prepareUpdate(Long anId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String prepareDelete(Long anId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}