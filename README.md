<h1 align="center">Biblioteca LiterAlura</h1>

LiterAlura es una aplicación de consola desarrollada en Java y Spring Boot que funciona como un catálogo de libros que fueron recuperados desde la api Gutendex.
Permite a los usuarios buscar libros, y guardar información relevante en una base de datos PostgreSQL y realizar diversas consultas sobre los libros y autores almacenados.

Proyecto desarrollado como parte del Challenge de Programación de Alura Latam, enfocado en poner en práctica conceptos de desarrollo backend, consumo de APIs, y persistencia de datos con JPA.
Se aplicaron conocimientos de Java Streams, inyección de dependencias, notaciones, Native Queries, JPQL.

**Funcionalidades Principales**<br>
Se presenta un menú interactivo en la consola con las siguientes opciones:

1. Buscar libro por su título en la API de Gutendex: si se encuentra, guarda información del libro, sus autores y sus idiomas en la base de datos local.
2. Listar libros registrados: muestra una lista de todos los libros que han sido guardados en la base de datos.
3. Listar autores registrados: muestra una lista de todos los autores almacenados, sus datos y los títulos de sus libros.
4. Listar autores vivos en un año determinado: pide al usuario que digite un año y muestra una lista de los autores que estaban vivos en ese año, si es que hay registrados en la base de datos
5. Listar libros por idioma: permite al usuario elegir entre 4 idiomas y, de este modo ver todos los libros registrados.


**Tecnologías Utilizadas**<br>
Lenguaje: Java 21.0.7
Framework: Spring Boot 3.5.5
Persistencia de Datos: Spring Data JPA
Base de Datos: PostgreSQL
Cliente HTTP: HttpClient nativo de Java
Manejo de JSON: Jackson Databind (ObjectMapper) 2.19.2
Gestión de Dependencias: Apache Maven

Version 1.1