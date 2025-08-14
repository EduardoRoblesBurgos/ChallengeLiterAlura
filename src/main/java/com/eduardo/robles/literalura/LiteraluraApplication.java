package com.eduardo.robles.literalura;

import com.eduardo.robles.literalura.model.ModeloBase;
import com.eduardo.robles.literalura.service.ConsumoAPI;

import com.eduardo.robles.literalura.service.ConversorDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

//Agrego la interfaz CommanLineRunner implementándola en la clase
@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

    @Override
    public void run(String... args) {
        var consumoAPI = new ConsumoAPI();
        var json = consumoAPI.obtenerDatos("https://gutendex.com/books/?ids=2641");
        //var json = consumoAPI.obtenerDatos("https://gutendex.com/books/?search=pride");
        //var json = consumoAPI.obtenerDatos("https://gutendex.com/books/");
        System.out.println(json);

        ConversorDatos conversorDelJson = new ConversorDatos();
        var datos = conversorDelJson.obtenerDatos(json, ModeloBase.class);
        System.out.println(datos);
        System.out.println(datos.libro());
        System.out.println(datos.libro().size());

        int opcion1 = -1;
        //int opcion2 = -1;

        Scanner teclado = new Scanner(System.in);
        //ConversorMonedas convertir = new ConversorMonedas();

        String menu1 = """
                        ****¡Literaluta!:****
                        1. Buscar libro por su título
                        2. Listar libros registrados
                        3. Listar autores registrados
                        4. Listar autores vivos en un año registrados
                        5. Listra libros por idioma
                        0. Salir""";
        System.out.println(menu1);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$");
        while(opcion1 != 0){
            System.out.println("Elija una opción principal:");
            try{
                opcion1 = teclado.nextInt();

                switch (opcion1){
                    case 1:
                        System.out.println("Opción: " + opcion1);
                        break;
                    case 2:
                        System.out.println("Opción: " + opcion1);
                        break;
                    case 3:
                        System.out.println("Opción: " + opcion1);
                        break;
                    case 4:
                        System.out.println("Opción: " + opcion1);
                        break;
                    case 5:
                        String opcion2 = "";

                        String menu2 = """
                        ****¡Escriba la sigla del idioma! :****
                        en - inglés
                        es - español
                        fr - francés
                        pt - portugués
                        m -  volver menú principal""";
                        System.out.println(menu2);
                        System.out.println("??????????????????????????");
                        while (!opcion2.equals("m")) {
                            System.out.println("Escriba la sigla:");
                            try {
                                opcion2 = teclado.next().toLowerCase();

                                switch (opcion2){
                                    case "en":
                                        System.out.println("Opción en: " + opcion2);
                                        break;
                                    case "es":
                                        System.out.println("Opción es: " + opcion2);
                                        break;
                                    case "fr":
                                        System.out.println("Opción fr: " + opcion2);
                                        break;
                                    case "pt":
                                        System.out.println("Opción pt: " + opcion2);
                                        break;
                                    case "m":
                                        System.out.println(menu1);
                                        break;
                                    default:
                                        System.out.println(opcion2 + "No está en las opciones. Elija nuevamente");
                                        break;
                                }
                            } catch (Exception f) {
                                System.out.println("Error f tipo: " + f + ", " + f.getMessage());
                                //teclado.nextLine(); // Limpia el buffer de la entrada inválida, al tomar el último enter
                                //teclado.next(); // Consume the invalid input to prevent an infinite loop, but have a limited time of wait
                                System.out.println("----------------------------------------------------------------------");
                            }
                        }
                        break;











                    case 0:
                        System.out.println("********************************");
                        System.out.println("Programa Finalizado");
                        teclado.close();
                        break;
                    default:
                        System.out.println("Número no presente en las opciones. Elija nuevamente");
                        break;
                }
            } catch (Exception e){
                System.out.println("No ingresate un número entero. Error tipo: " + e + ", " +e.getMessage());
                teclado.nextLine(); // Limpia el buffer de la entrada inválida, al tomar el último enter
                //teclado.next(); // Consume the invalid input to prevent an infinite loop, but have a limited time of wait
                System.out.println("----------------------------------------------------------------------");
            }
        }
    }
}