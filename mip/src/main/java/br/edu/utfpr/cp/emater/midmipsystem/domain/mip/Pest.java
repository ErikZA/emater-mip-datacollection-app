package br.edu.utfpr.cp.emater.midmipsystem.domain.mip;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode (onlyExplicitlyIncluded = true)
@Entity
public class Pest {

    public enum PestSize {
        GREATER_15CM,
        SMALLER_15CM,
        THIRD_TO_FIFTH_INSTAR,
        ADULT;
    }

    @EqualsAndHashCode.Include
    @Id @GeneratedValue
    private Long id;
    private String usualName;

    @EqualsAndHashCode.Include
    private String scientificName;

    @Enumerated (EnumType.STRING)
    private PestSize pestSize;

    private Pest() {}

    @Builder
    public static Pest create (String usualName, String scientificName, PestSize pestSize) {
        Pest instance = new Pest();
        instance.usualName = usualName;
        instance.scientificName = scientificName;
        instance.pestSize = pestSize;

        return instance;
    }
}