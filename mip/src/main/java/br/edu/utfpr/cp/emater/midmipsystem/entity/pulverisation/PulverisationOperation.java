package br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.AuditingPersistenceEntity;
import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.GrowthPhase;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PulverisationOperation extends AuditingPersistenceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    @ManyToOne
    private Survey survey;

    @EqualsAndHashCode.Include
    @Temporal(TemporalType.DATE)
    @NotNull(message = "A data da coleta precisa ser informada!")
    private Date sampleDate;

    @Enumerated(EnumType.STRING)
    private GrowthPhase growthPhase;

    private double caldaVolume;

    private int daysAfterEmergence;

    private double soyaPrice;

    private double operationCostCurrency;
    
    private double totalOperationCostCurrency;
    
    private double operationCostQty;
    
    private double totalOperationCostQty;

    @ElementCollection
    private Set<PulverisationOperationOccurrence> operationOccurrences;

    @Builder
    public static PulverisationOperation create(Long id,
                                                Survey survey,
                                                Date sampleDate,
                                                double soyaPrice,
                                                double operationCostCurrency,
                                                GrowthPhase growthPhase,
                                                double caldaVolume) {
        
        var instance = new PulverisationOperation();
        instance.setId(id);
        instance.setSurvey(survey);
        instance.setSampleDate(sampleDate);
        instance.setSoyaPrice(soyaPrice);
        instance.setOperationCostCurrency(operationCostCurrency);
        instance.setGrowthPhase(growthPhase);
        instance.setCaldaVolume(caldaVolume);
        
        return instance;
    }

    public PulverisationOperation() {
        super();
        
        this.setOperationOccurrences(new HashSet<>());
    }
    
    public boolean addOperationOccurrence (Product product, double productPrice, Target target) {
        
        var occurrence = PulverisationOperationOccurrence.builder().product(product).productPrice(productPrice).target(target).build();
            occurrence.setProductCostQty(occurrence.getProductCostCurrency()/this.getSoyaPrice());
        
        var result = this.getOperationOccurrences().add(occurrence);
        
        this.setTotalOperationCostCurrency(updateTotalOperationCostCurrency());
        
        this.updateOperationCostQty();
        
        this.setTotalOperationCostQty(this.updateTotalOperationCostQty());
        
        return result;
    }
    
    private double updateTotalOperationCostCurrency() {        
        return 
                (this.getOperationOccurrences().stream().mapToDouble(PulverisationOperationOccurrence::getProductCostCurrency).sum() 
                + this.getOperationCostCurrency());
    }
    
    private void updateOperationCostQty() {
        this.setOperationCostQty(this.getOperationCostCurrency() / this.getSoyaPrice());
    }

    private double updateTotalOperationCostQty() {
        return 
                (this.getOperationOccurrences().stream().mapToDouble(PulverisationOperationOccurrence::getProductCostQty).sum() 
                + this.getOperationCostQty());
    }
    
}
