package com.eduardo.robles.literalura.principal;

import com.eduardo.robles.literalura.service.ServicioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component // ⚠️ ¡Importante! Anota esta clase como un componente.
public class Principal {
    private Scanner teclado = new Scanner(System.in);

    // ⚠️ Inyecta el ServicioLibro. Spring se encarga de crearlo y de inyectar sus repositorios.
    @Autowired
    private ServicioLibro servicioLibro;

    String menu1 = """
                        -----------------¡Literalura!------------------
                        Elija una opción:
                        1. Buscar libro por su título
                        2. Listar libros registrados
                        3. Listar autores registrados y sus libros
                        4. Listar autores vivos en un año registrados
                        5. Mostrar menú de idiomas para los libros
                        0. Salir
                        -----------------------------------------------
                        """;

    public void MenuPrincipal(){
        int opcionMenu = -1;

        while(opcionMenu != 0){
            System.out.print(menu1);
            try{
                opcionMenu = teclado.nextInt();

                switch (opcionMenu){
                    case 1:
                        teclado.nextLine();
                        System.out.println("Escriba un libro para buscar en la api:");
                        var opcion1 = teclado.nextLine();
                        servicioLibro.buscarLibroEnApi(opcion1);
                        break;
                    case 2:
                        servicioLibro.listarLibrosGuardados();
                        break;
                    case 3:
                        servicioLibro.listarAutores();
                        break;
                    case 4:
                        System.out.println("Digite el año:");
                        var opcion2 = teclado.nextInt();
                        servicioLibro.listarAutoresVivos(opcion2);
                        break;
                    case 5:
                        MenuSecundario();
                        break;
                    case 0:
                        System.out.println("******************************\nPrograma Finalizado, Adiós\n******************************");
                        teclado.close();
                        break;
                    default:
                        System.out.println("Opción "+ opcionMenu + " presente en las opciones. Elija nuevamente:");
                        break;
                }
            } catch (InputMismatchException e){
                System.out.println("No ingresate un número entero. Error tipo: " + e + ", " +e.getMessage());
                teclado.nextLine(); //Limpia el buffer de la entrada inválida, al tomar el último entero
                //teclado.next(); //Consume the invalid input to prevent an infinite loop, but have a limited time of wait
                System.out.println("-------------------\nApriete Enter para mostrar menu principal nuevamente");
                teclado.nextLine();
            }
        }
        teclado.close();
    }

    //Menú secundario para la opción de idiomas
    private void MenuSecundario(){
        String opcion = "";
        String menu2 = """
                        ***************Menu 5******************
                        ¡Escriba la sigla del idioma!:
                        en - inglés
                        es - español
                        fr - francés
                        pt - portugués
                        m -  volver menú principal
                        ****************************************
                        """;

        while (!opcion.equals("m")) {
            System.out.print(menu2);
            System.out.println("Escriba una opción:");

            try {
                opcion = teclado.next().toLowerCase();

                switch (opcion){
                    case "en", "es", "fr", "pt":
                        servicioLibro.listarLibrosGuardadosIdioma(opcion);
                        break;
                    case "m":
                        break;
                    default:
                        System.out.println("\n" + opcion + ", no está en las opciones. Elija nuevamente:");
                        break;
                }
            } catch (Exception f) {
                System.out.println("Error f tipo: " + f + ", " + f.getMessage());
                System.out.println("???????????????????????????????????????????");
            }
        }
    }
}