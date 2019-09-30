package br.edu.utfpr.cp.emater.midmipsystem.entity.mip;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.AuditingPersistenceEntity;
import java.io.Serializable;
import javax.persistence.Entity;
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
public class PestDisease extends AuditingPersistenceEntity implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @EqualsAndHashCode.Include
    @Size(min = 5, max = 50, message = "O nome deve ter entre 5 e 50 caracteres")
    protected String usualName;

    //Não normaliza o nome.

    @Builder
    public static PestDisease create (Long id, String usualName) {
        PestDisease instance = new PestDisease();
        instance.setId(id);
        instance.setUsualName(usualName);
        
        return instance;
    }    
}
