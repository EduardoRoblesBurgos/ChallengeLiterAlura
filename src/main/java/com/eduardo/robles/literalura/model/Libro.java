package com.eduardo.robles.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Long idLibro;
    private String titulo;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "libros_autores",
            joinColumns = @JoinColumn(name = "id_libro"),
            inverseJoinColumns = @JoinColumn(name = "id_autor")
    )
    private List<Autor> autores;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "libros_idiomas",
            joinColumns = @JoinColumn(name = "id_libro"),
            inverseJoinColumns = @JoinColumn(name = "id_idioma")
    )
    private List<Idioma> idiomas;
    private Long numeroDescargas;

    public Libro(){
    }

    public Libro(ModeloLibro modeloLibro) {
        this.idLibro = modeloLibro.idLibro();
        this.titulo = modeloLibro.titulo();
        this.autores = modeloLibro.autores().stream()
                .map(a -> new Autor(a))
                .collect(Collectors.toList());
        this.idiomas = modeloLibro.idiomas().stream()
                .map(i -> new Idioma(i))
                .collect(Collectors.toList());
        this.numeroDescargas = modeloLibro.numeroDescargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
//        List<Libro> susLibros = autores.stream().map(a -> this).toList();
//        autores.forEach(a -> a.setLibros(susLibros)); //guarda los datos de los autores (del  libro actual)
        this.autores = autores;
    }

    public List<Idioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<Idioma> idiomas) {
//        List<Libro> susLibros = idiomas.stream().map(i -> this).toList();
//        idiomas.forEach(i -> i.setLibros(susLibros)); //guarda los datos de los idiomas (del  libro actual)
        this.idiomas = idiomas;
    }

    public Long getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Long numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
//        return "idLibro: " + getIdLibro() + ", Título: " + getTitulo() +
//                ", Autores: " + getAutores() + ", Idiomas: " + getIdiomas() +
//                ", Número de descargas = " + getNumeroDescargas();
//        return "+++++++++++LIBRO+++++++++++++++" +
//                "\nId: " + idLibro +
//                "\nTítulo: " + titulo +
//                "\nAutores: " + autores.stream().map(Autor::getNombre).toList() +
//                "\nIdiomas: " + idiomas.stream().map(Idioma::getSigla).toList() +
//                "\nNúmero de descargas = " + numeroDescargas +
//                "\n+++++++++++++++++++++++++++++++\n";
        return "Id: " + idLibro +
                "\nTítulo: " + titulo +
                "\nAutores: " + autores.stream().map(Autor::getNombre).toList() +
                "\nIdiomas: " + idiomas.stream().map(Idioma::getSigla).toList() +
                "\nNúmero de descargas = " + numeroDescargas;
    }
}