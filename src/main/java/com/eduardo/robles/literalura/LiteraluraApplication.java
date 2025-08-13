package com.eduardo.robles.literalura;

import com.eduardo.robles.literalura.model.ModeloLibro;
import com.eduardo.robles.literalura.service.ConsumoAPI;
import com.eduardo.robles.literalura.service.ConversorDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Agrego la interfaz CommanLineRunner implementándola en la clase
@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        var consumoAPI = new ConsumoAPI();
        //var json = consumoAPI.obtenerDatos("http://gutendex.com/books/?ids=2641");
        var json = consumoAPI.obtenerDatos("http://gutendex.com/books?search=dickens");
        System.out.println(json);

        ConversorDatos conversorDelJson = new ConversorDatos();
        var datos = conversorDelJson.obtenerDatos(json, ModeloLibro.class);
        System.out.println(datos);
    }
}