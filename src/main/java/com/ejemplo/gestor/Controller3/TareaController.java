package com.ejemplo.gestor.Controller3;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    @GetMapping("/ejemplo")
    public Tarea ejemplo() {
        return new Tarea(1, "Revisar el login", "alta", false);
    }

    @GetMapping("/lista")
    public ArrayList<Tarea> lista() {

        ArrayList<Tarea> tareas = new ArrayList<>();

        tareas.add(new Tarea(1, "Revisar el login", "alta", false));
        tareas.add(new Tarea(2, "Actualizar la base de datos", null, true));
        tareas.add(new Tarea(3, "Enviar correo de notificación", "baja", false));

        return tareas;
    }
}