package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.AuditingPersistenceEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.apache.commons.text.WordUtils;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode (onlyExplicitlyIncluded = true)
public class Pest extends AuditingPersistenceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @EqualsAndHashCode.Include
    @Size(min = 5, max = 50, message = "O nome deve ter entre 5 e 50 caracteres")
    protected String usualName;

    private String scientificName;

    @EqualsAndHashCode.Include
    @Enumerated(EnumType.STRING)
    private PestSize pestSize;

    public void setUsualName(String usualName) {
        this.usualName = WordUtils.capitalize(usualName.toLowerCase());
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
//        this.scientificName = WordUtils.capitalize(scientificName.toLowerCase());
    }

    @Builder
    public static Pest create(Long id, String usualName, String scientificName, PestSize pestSize) {
        Pest instance = new Pest();
        instance.setId(id);
        instance.setUsualName(usualName);
        instance.setScientificName(scientificName);
        instance.setPestSize(pestSize);

        return instance;
    }

    public String getPestSizeName() {
        return this.getPestSize().getName();
    }
}
