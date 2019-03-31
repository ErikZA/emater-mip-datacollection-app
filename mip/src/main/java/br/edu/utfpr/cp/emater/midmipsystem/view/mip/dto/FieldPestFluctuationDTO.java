package br.edu.utfpr.cp.emater.midmipsystem.view.mip.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FieldPestFluctuationDTO {

    private String harvestName;
    private String fieldName;
    private String fieldLocation;
    private String fieldCity;
    private String seedName;
    private String farmerName;
    private String supervisorNames[];

    private FieldPestFluctuationDTO() {}
}