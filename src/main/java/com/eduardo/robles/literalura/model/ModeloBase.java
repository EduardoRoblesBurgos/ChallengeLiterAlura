package com.eduardo.robles.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ModeloBase(@JsonAlias("results") List<ModeloLibro> libro) {
}
