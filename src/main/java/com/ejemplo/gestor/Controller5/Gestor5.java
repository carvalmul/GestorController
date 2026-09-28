package com.ejemplo.gestor.Controller5;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Gestor5 {

    @GetMapping("/diagnostico")
    public String diagnostico(
            @RequestHeader(name = "User-Agent") String cliente,
            @RequestHeader(name = "Accept") String acepta) {

        return "Me llama: " + cliente + "\nQuiere recibir: " + acepta;
    }

}