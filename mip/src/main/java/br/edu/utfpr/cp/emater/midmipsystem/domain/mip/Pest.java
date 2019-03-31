package br.edu.utfpr.cp.emater.midmipsystem.domain.mip;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pest {

    public enum PestSize {
        GREATER_15CM("> 15 cm"), 
        SMALLER_15CM("< 15 cm"), 
        THIRD_TO_FIFTH_INSTAR("3. ao 5. Instar"), 
        ADULT("Adultos");

        private String name;

        PestSize(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private Long id;
    private String usualName;

    @EqualsAndHashCode.Include
    private String scientificName;

    @Enumerated(EnumType.STRING)
    private PestSize pestSize;

    private Pest() {
    }

    @Builder
    public static Pest create(String usualName, String scientificName, PestSize pestSize) {
        Pest instance = new Pest();
        instance.usualName = usualName;
        instance.scientificName = scientificName;
        instance.pestSize = pestSize;

        return instance;
    }
}