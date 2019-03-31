package br.edu.utfpr.cp.emater.midmipsystem.domain.mip;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Embeddable
public class PestFluctuationSampleOccurrence {

    private double value;

    @OneToOne
    private Pest pest;

    private PestFluctuationSampleOccurrence() { }

    static PestFluctuationSampleOccurrence create (Pest aPest, double aValue) {
        PestFluctuationSampleOccurrence occurrence = new PestFluctuationSampleOccurrence();
        occurrence.pest = aPest;
        occurrence.value = aValue;

        return occurrence;
    }
}