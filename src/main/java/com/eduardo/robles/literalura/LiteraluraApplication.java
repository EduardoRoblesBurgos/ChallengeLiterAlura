package com.eduardo.robles.literalura;

import com.eduardo.robles.literalura.principal.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Agrego la interfaz CommanLineRunner implementándola en la clase
@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
    @Autowired
    private Principal principal; //⚠️ Inyecta la clase Principal

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

    @Override
    public void run(String... args) {
        principal.MenuPrincipal();
    }
}