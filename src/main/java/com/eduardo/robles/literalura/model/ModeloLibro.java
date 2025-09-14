package com.eduardo.robles.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

//Esta notación ignora los datos que no queremos mapear del json
@JsonIgnoreProperties(ignoreUnknown = true)
public record ModeloLibro(@JsonAlias("id") Long idLibro,
                          @JsonAlias("title") String titulo,
                          @JsonAlias("authors") List<ModeloAutor> autores,
                          @JsonAlias("languages")List<String> idiomas,
                          @JsonAlias("download_count")Long numeroDescargas) {
}