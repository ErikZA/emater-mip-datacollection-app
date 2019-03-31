package br.edu.utfpr.cp.emater.midmipsystem.domain.mip;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class PestFluctuationSample {

    @Id @GeneratedValue
    private Long id;
    private Long surveyFieldId;

    @Basic
    private LocalDate collectedDate;
    private int daysAfterEmergence;
    private int defoliation;

    @Enumerated (EnumType.STRING)
    private GrowthPhase growthPhase;

    @ElementCollection
    private Set<PestFluctuationSampleOccurrence> pestOccurrences;

    private PestFluctuationSample() {}

    @Builder
    public static PestFluctuationSample create (Long surveyFieldId, LocalDate collectedDate, int daysAfterEmergence, int defoliation, GrowthPhase growthPhase) {
        PestFluctuationSample instance = new PestFluctuationSample();
        instance.surveyFieldId = surveyFieldId;
        instance.collectedDate = collectedDate;
        instance.daysAfterEmergence = daysAfterEmergence;
        instance.defoliation = defoliation;
        instance.growthPhase = growthPhase;

        return instance;
    }

    public boolean addOccurrence (Pest aPest, double aValue) {
        if (pestOccurrences == null)
            pestOccurrences = new HashSet<>();

        return pestOccurrences.add(PestFluctuationSampleOccurrence.create(aPest, aValue));
    }

    public Set<PestFluctuationSampleOccurrence> getPestOccurrences() {
        return Set.copyOf(this.pestOccurrences);
    }
}