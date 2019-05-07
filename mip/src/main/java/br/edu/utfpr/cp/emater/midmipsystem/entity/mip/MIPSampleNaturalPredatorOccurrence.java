package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@EqualsAndHashCode (onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MIPSampleNaturalPredatorOccurrence implements Serializable {
    
    @EqualsAndHashCode.Include
    protected double value;
    
    @EqualsAndHashCode.Include
    @ManyToOne (fetch = FetchType.EAGER)
    protected PestNaturalPredator pestNaturalPredator;
    
    public String getPestNaturalPredatorUsualName() {
        return this.getPestNaturalPredator().getUsualName();
    } 
}