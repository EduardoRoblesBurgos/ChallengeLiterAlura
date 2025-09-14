package com.eduardo.robles.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "idiomas")
public class Idioma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sigla;
    @ManyToMany(mappedBy = "idiomas", fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>(); //¡Inicializa la lista aquí!, es una excelente práctica para evitar NullPointerException

    public Idioma() {
    }

    // Constructor que puedes usar para crear un nuevo Autor
    public Idioma(String idioma) {
        this.sigla = idioma;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        // Asegura que la lista nunca sea nula
        this.libros = (libros != null) ? libros : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Sigla: " + getSigla() +";";
    }
}