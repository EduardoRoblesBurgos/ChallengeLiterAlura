package com.eduardo.robles.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

//Esta notación ignora los datos que no queremos mapear del json
@JsonIgnoreProperties(ignoreUnknown = true)
public record ModeloBase(@JsonAlias("count") Long conteo,
                         @JsonAlias("results") List<ModeloLibro> libro) {
}