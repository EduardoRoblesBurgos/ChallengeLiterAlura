package com.eduardo.robles.literalura.service;

import com.eduardo.robles.literalura.model.*;
import com.eduardo.robles.literalura.repository.AutorRepository;
import com.eduardo.robles.literalura.repository.IdiomaRepository;
import com.eduardo.robles.literalura.repository.LibroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class ServicioLibro {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConversorDatos conversorDelJson = new ConversorDatos();

    @Autowired
    LibroRepository repositorioLibro;
    @Autowired
    AutorRepository repositorioAutor;
    @Autowired
    IdiomaRepository repositorioIdioma;

    //1 busca libro en la api Gutenbex para guardarlo en la base de datos
    @Transactional
    public void buscarLibroEnApi(String opcion){
        //Consumo de datos
        var json = consumoAPI.obtenerDatos("https://gutendex.com/books/?search="+opcion.replace(" ","%20"));
        var datos = conversorDelJson.obtenerDatos(json, ModeloBase.class);
        Optional<ModeloLibro> libroBuscado = datos.libro().stream().findFirst();

        //Comienza el proceso si encuentra el libro en la API
        if(libroBuscado.isPresent()){
            try {
                //Verificar que el libro no exista en la base de datos
                Optional<Libro> buscarLibro = repositorioLibro.buscarLibro(libroBuscado.get().idLibro());

                if(buscarLibro.isEmpty()){
                    Libro nuevoLibro = new Libro(libroBuscado.get());

                    nuevoLibro.setId(null); //⚠️ importante: evitar conflictos con id asignado manualmente

                    //1. Manejo de autores
                    // a. Creamos una lista segura para evitar NullPointerException si la lista es nula
                    List<Autor> autoresAPI = nuevoLibro.getAutores() != null ? nuevoLibro.getAutores() : new ArrayList<>();
                    if(autoresAPI.isEmpty()){
                        Autor autorAnonimo = new Autor();
                        autorAnonimo.setNombre("Anonymous");
                        autorAnonimo.setAnoNacimiento(null);
                        autorAnonimo.setAnoMuerte(null);
                        autoresAPI.add(autorAnonimo);
                    }

                    // b. Mapeamos los autores
                    List<Autor> autoresManejados = autoresAPI.stream()
                            .map(autor -> {
                                // Si el autor existe, lo recuperamos
                                Optional<Autor> autorExistente = repositorioAutor.findByNombre(autor.getNombre());

                                if (autorExistente.isPresent()) {
                                    Autor autorRecuperado = autorExistente.get();

                                    // Sincroniza la relación: agrega el nuevo libro a la lista del autor ya existente.
                                    // Esto es vital para que JPA/Hibernate sepa que la tabla intermedia (libros_autores)
                                    // debe actualizarse con la nueva relación. Si no se hace, la relación no se guardará.
                                    autorRecuperado.getLibros().add(nuevoLibro);
                                    return autorRecuperado;
                                } else {
                                    // Sincroniza la relación: el nuevo libro se añade a la lista del autor
                                    // antes de ser guardado.
                                    autor.getLibros().add(nuevoLibro);

                                    // Guarda el autor nuevo y devuelve la instancia gestionada por JPA.
                                    return repositorioAutor.save(autor);
                                }
                            }).collect(Collectors.toList());

                    nuevoLibro.setAutores(autoresManejados);

                    //2. Manejo de idiomas
                    // a. Creamos una lista segura para evitar NullPointerException si la lista es nula
                    List<Idioma> idiomasAPI = nuevoLibro.getIdiomas() != null ? nuevoLibro.getIdiomas() : new ArrayList<>();
                    if(idiomasAPI.isEmpty()){
                        Idioma idiomaDefault = new Idioma();
                        idiomaDefault.setSigla("n/a");
                        idiomasAPI.add(idiomaDefault);
                    }

                    // b. Mapeamos los idiomas
                    List<Idioma> idiomasManejados = idiomasAPI.stream()
                            .map(idioma -> {
                                // Si el idioma existe, lo recuperamos
                                Optional<Idioma> idiomaExistente = repositorioIdioma.findBySigla(idioma.getSigla());

                                if (idiomaExistente.isPresent()) {
                                    Idioma idiomaRecuperado = idiomaExistente.get();

                                    // Sincroniza la relación: agrega el nuevo libro a la lista del idioma ya existente.
                                    // Esto es necesario para que JPA/Hibernate pueda gestionar correctamente la
                                    // relación bidireccional y persista la nueva entrada en la tabla intermedia (libros_idiomas).
                                    idiomaRecuperado.getLibros().add(nuevoLibro);
                                    return idiomaRecuperado;
                                } else {
                                    // Sincroniza la relación: el nuevo libro se añade a la lista del idioma nuevo.
                                    idioma.getLibros().add(nuevoLibro);

                                    // Guarda el idioma nuevo y devuelve la instancia gestionada por JPA
                                    return repositorioIdioma.save(idioma);
                                }
                            }).collect(Collectors.toList());

                    nuevoLibro.setIdiomas(idiomasManejados);

                    //3. Guarda el libro en la base de datos.
                    // Dentro de una transacción, el metodo save() de Spring Data JPA se encarga de:
                    // a. Persistir el nuevo libro.
                    // b. Como las relaciones son de tipo ManyToMany con cascade = MERGE, JPA/Hibernate
                    // detecta que las entidades de autores e idiomas asociadas ya tienen un ID.
                    // c. En lugar de intentar persistirlas de nuevo (lo que causaría el error),
                    // crea las entradas correspondientes en las tablas de unión (libros_autores y
                    // libros_idiomas) para establecer la relación, utilizando los IDs existentes de
                    // autores e idiomas y el nuevo ID del libro recién guardado. Esto garantiza la
                    // integridad referencial.
                    repositorioLibro.save(nuevoLibro);
                    System.out.println("¡Libro guardado en la base de datos!:\n"+nuevoLibro);
                } else {
                    System.out.println("Libro ya existe en la base de datos");
                }
            } catch (DataIntegrityViolationException h){
                System.out.println("Valor único ya existe. Error tipo: " + h + ", " +h.getMessage());
                System.out.println("----------------------------------------------------------------------");
            }
        }
        else{
            System.out.println("Libro no encontrado en la API");
        }

        /*
        //Otra forma de buscar el libro:
         if(datos.conteo() != 0){
             ModeloLibro libroExiste = datos.libro().getFirst();
             //Verificar que el libro no exista en la base de datos
             Optional<Libro> buscarLibro2 = repositorioLibro.buscarLibro(libroExiste.idLibro());

             //Sigue el tratamiento de libro ya hecho con la otra lógica --->
         } else {
         System.out.println("Libro no encontrado en la API");
         }
         */

        System.out.println("-------------------\nApriete Enter para mostrar menu principal nuevamente");
        teclado.nextLine();
    }



    //2 lista libros en base de datos
    public void listarLibrosGuardados() {
        List<Libro> listaLibros = repositorioLibro.findAll();

        if(!listaLibros.isEmpty()){
            for(Libro libro:listaLibros){
                System.out.println("************LIBRO************\n"+libro+"\n***********************\n");
            }
        } else{
            System.out.println("No hay libros en la base de datos");
        }
        System.out.println("-------------------\nApriete Enter para mostrar menu principal nuevamente");
        teclado.nextLine();
    }



    //3 lista autores registrados
    public void listarAutores(){
        List<Autor> listaAutores = repositorioAutor.findAll();

        /*
        The next method executes an additional database query for each author, leading to N+1 queries in total (1 for all authors, and N for each author's books).
        This can severely impact performance with a large dataset:
        */
//        if(!listaAutores.isEmpty()){
//            for (Autor a:listaAutores){
//                System.out.print("*******AUTOR*******\n" + a);
//                List<String> titulosLibrosdeAutor = repositorioLibro.buscarLibrosPorAutor(a.getNombre());
//                System.out.println("\nLibros: " + titulosLibrosdeAutor);
//            }
//        } else{
//            System.out.println("No hay autores en la base de datos");
//        }
//        System.out.println("-------------------\nApriete Enter para mostrar menu principal nuevamente");
//        teclado.nextLine();


        /*
        la forma más limpia y eficiente de lograrlo es aprovechar la relación ya existente entre las entidades Autor y Libro.
        La entidad Autor ya tiene un List<Libro> libros y un fetch de tipo EAGER. Esto es clave. El fetch = FetchType.EAGER significa
        que cada vez que consultes la base de datos para obtener un Autor, Hibernate traerá automáticamente todos los Libros asociados
        a ese autor en la misma consulta (con JOINs implícitos). Esto evita el problema de las consultas N+1, que es innecesario y redundante,
        ya ahora todo se carga de una vez
        */
        if(!listaAutores.isEmpty()){
            for (Autor autor : listaAutores) {
                System.out.println("*****************AUTOR*********************");
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Año de Nacimiento: " + autor.getAnoNacimiento());
                System.out.println("Año de Muerte: " + autor.getAnoMuerte());

                // Se obtiene la lista de títulos directamente de la entidad Autor
                List<String> titulosLibros = autor.getLibros().stream()
                        .map(Libro::getTitulo)
                        .toList();
                System.out.println("Libros: " + titulosLibros);
                System.out.println("********************************\n");
            }
        } else{
            System.out.println("No hay autores en la base de datos");
        }
        System.out.println("-------------------\nApriete Enter para mostrar menu principal nuevamente");
        teclado.nextLine();
    }



    //4 lista autores vivos en un año
    public void listarAutoresVivos(int anio){
        List<Autor> listaAutores = repositorioAutor.buscarAutorVivo(anio);

        if(!listaAutores.isEmpty()){
            for (Autor autor : listaAutores) {
                System.out.println("*****************AUTOR*********************");
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Año de Nacimiento: " + autor.getAnoNacimiento());
                System.out.println("Año de Muerte: " + autor.getAnoMuerte());

                // Se obtiene la lista de títulos directamente de la entidad Autor
                List<String> titulosLibros = autor.getLibros().stream()
                        .map(Libro::getTitulo)
                        .toList();
                System.out.println("Libros: " + titulosLibros);
                System.out.println("********************************\n");
            }
        } else{
            System.out.println("No existen autores vivos para el año dado");
        }

        System.out.println("-------------------\nApriete Enter para mostrar menu principal nuevamente");
        teclado.nextLine();
    }



    //5 lista libros de un idioma
    public void listarLibrosGuardadosIdioma(String sigla){
        List<Libro> listaLibros = repositorioLibro.buscarLibrosPorIdioma(sigla);

        if(!listaLibros.isEmpty()){;
            for(Libro libro:listaLibros){
                System.out.println("************ LIBRO ************\n"+libro+"\n***********************\n");
            }
        } else{
            System.out.println("Todavía no hay libros en la base de datos en ese idioma");
        }
        System.out.println("-------------------\nApriete Enter para mostrar menu principal nuevamente");
        teclado.nextLine();
    }
}