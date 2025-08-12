package com.eduardo.robles.literalura;

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
        System.out.println("Hola");
    }
}