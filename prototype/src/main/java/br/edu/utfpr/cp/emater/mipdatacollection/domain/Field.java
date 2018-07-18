package br.edu.utfpr.cp.emater.mipdatacollection.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class Field implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private boolean sporeCollector;

    @ManyToOne(targetEntity = Farmer.class)
    private Farmer farmer;

    @ManyToOne(targetEntity = Supervisor.class)
    private Supervisor supervisor;

    @ManyToOne(targetEntity = City.class)
    private City city;

}