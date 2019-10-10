package br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation;

import br.edu.utfpr.cp.emater.midmipsystem.entity.mip.*;
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
public class Target extends AuditingPersistenceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @EqualsAndHashCode.Include
    @Size(min = 3, max = 50, message = "A descrição deve ter entre 3 e 50 caracteres")
    protected String description;

    @EqualsAndHashCode.Include
    @Enumerated(EnumType.STRING)
    private TargetCategory category;
    //normaliza o nome somente se esta função for chamada
    public void description(String usualName) {
        this.description = WordUtils.capitalize(usualName.toLowerCase());
    }

    @Builder
    public static Target create(Long id, String description, TargetCategory category) {
        Target instance = new Target();
        instance.setId(id);
        instance.setDescription(description);
        instance.setCategory(category);

        return instance;
    }

}
