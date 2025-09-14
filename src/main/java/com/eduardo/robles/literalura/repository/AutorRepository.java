package com.eduardo.robles.literalura.repository;

import com.eduardo.robles.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor,Long>{
    //Derived Query
    Optional<Autor> findByNombre(String nombre);
    //Otra forma, usando JPQL (Java Persistence Query Language)
    @Query("SELECT a FROM Autor a WHERE a.nombre = :nombreDelAutor")
    Optional<Autor> buscarAutorPorNombre(@Param("nombreDelAutor") String nombre);

    //JPQL
    @Query("SELECT a FROM Autor a WHERE a.anoMuerte >= :anio AND a.anoNacimiento <= :anio")
    List<Autor> buscarAutorVivo(int anio);
}