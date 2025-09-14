package com.eduardo.robles.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer anoNacimiento;
    private Integer anoMuerte;
    @ManyToMany(mappedBy = "autores", fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>(); //¡Inicializa la lista aquí!, es una excelente práctica para evitar NullPointerException

    public Autor() {
    }

    // Constructor que puedes usar para crear un nuevo Autor
    public Autor(ModeloAutor modeloAutor) {
        //this.nombre = modeloAutor.nombre();
        this.nombre = (modeloAutor.nombre().length() > 255 ? modeloAutor.nombre().substring(0,255) : modeloAutor.nombre());
        this.anoNacimiento = modeloAutor.anoNacimieto();
        this.anoMuerte = modeloAutor.anoMuerte();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnoNacimiento() {
        return anoNacimiento;
    }

    public void setAnoNacimiento(Integer anoNacimiento) {
        this.anoNacimiento = anoNacimiento;
    }

    public Integer getAnoMuerte() {
        return anoMuerte;
    }

    public void setAnoMuerte(Integer anoMuerte) {
        this.anoMuerte = anoMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    // Si tu constructor recibe la lista, asegúrate de manejar el caso nulo
    public void setLibros(List<Libro> libros) {
        // Asegura que la lista nunca sea nula
        this.libros = (libros != null) ? libros : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre +
                "\nAño de Nacimiento: " + anoNacimiento +
                "\nAño de Muerte: " + anoMuerte;
    }
}