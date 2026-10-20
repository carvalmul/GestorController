package com.ejemplo.gestor.memoria;

import com.ejemplo.gestor.model.Proyecto;
import com.ejemplo.gestor.model.Tarea;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MemoriaProyecto {

    // Contenedor auxiliar para mantener listas de proyectos y tareas en memoria.
    // Aunque el flujo principal usa repositorios, esta clase es un punto de almacenamiento compartido.
    private final List<Proyecto> proyectos = new ArrayList<>();
    private final List<Tarea> tareas = new ArrayList<>();

    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }
}