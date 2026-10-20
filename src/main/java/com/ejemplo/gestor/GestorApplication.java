package com.ejemplo.gestor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestorApplication {

	// Punto de entrada de la aplicación.
	// Spring Boot crea el contexto, registra los controladores, servicios y demás beans
	// y deja la API escuchando peticiones HTTP.
	public static void main(String[] args) {
		SpringApplication.run(GestorApplication.class, args);
	}

}
