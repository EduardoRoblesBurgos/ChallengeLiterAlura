package com.eduardo.robles.literalura.repository;

import com.eduardo.robles.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {
    @Query("SELECT l FROM Libro l WHERE l.idLibro = :idDelLibro")
    Optional<Libro> buscarLibro(Long idDelLibro);

    //Utilizada esta Native Query antes, pero se realiza la consulta en el punto 3 del menu utilizando
    // los conceptos de ORM, solo con la consulta de los autores
    @Query(value = "SELECT l.titulo FROM libros l JOIN libros_autores la ON l.id = la.id_libro " +
            "JOIN autores a ON la.id_autor = a.id WHERE a.nombre = :nombreAutor", nativeQuery = true)
    List<String> buscarLibrosPorAutor(@Param("nombreAutor") String nombre);
}