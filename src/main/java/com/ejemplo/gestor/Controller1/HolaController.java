package com.ejemplo.gestor.Controller1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaController {
    @GetMapping("/ejemplo")
    public String ejemplo() {
        return "Hola, mundo. Te responde mi servidor.";
    }

    @GetMapping("/miau")
    public String miau() {
        return "Miau, mundo. Te responde mi servidor.";
    }
}