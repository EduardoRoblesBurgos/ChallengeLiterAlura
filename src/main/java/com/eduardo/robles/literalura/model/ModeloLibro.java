package com.eduardo.robles.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Esta notación ignora los datos que no queremos mapear del json
@JsonIgnoreProperties(ignoreUnknown = true)
public record ModeloLibro(@JsonAlias("title") String titulo,
                          @JsonAlias("authors") String autores,
                          @JsonAlias("languages")String idiomas,
                          @JsonAlias("download_count")Integer numeroDescargas) {
}