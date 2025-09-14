package com.eduardo.robles.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Esta notación ignora los datos que no queremos mapear del json
@JsonIgnoreProperties(ignoreUnknown = true)
public record ModeloAutor(@JsonAlias("name") String nombre,
                          @JsonAlias("birth_year") Integer anoNacimiento,
                          @JsonAlias("death_year") Integer anoMuerte) {
}