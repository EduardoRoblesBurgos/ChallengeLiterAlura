package com.eduardo.robles.literalura.repository;

import com.eduardo.robles.literalura.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdiomaRepository extends JpaRepository<Idioma,Long> {
    //Derived Query
    Optional<Idioma> findBySigla(String sigla);
    //Otra forma, usando JPQL (Java Persistence Query Lang)
    @Query("SELECT i FROM Idioma i WHERE i.sigla = :siglaDelIdioma")
    Optional<Idioma> buscarIdiomaPorSigla(@Param("siglaDelIdioma") String idioma);
}