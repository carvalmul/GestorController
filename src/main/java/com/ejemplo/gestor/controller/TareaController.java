package com.ejemplo.gestor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.ejemplo.gestor.memoria.*;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    private final List<Tarea> tareas;
    public TareaController(MemoriaProyecto memoria) {
        this.tareas = memoria.getTareas();
    }
    private int siguienteId = 1;

    @GetMapping
    public List<Tarea> lista(
            @RequestParam(name = "completada", required = false) Boolean completada) {
        // Consulta todas las tareas
        if (completada == null) {
            return tareas;
        }

        // Consultas la tarea dependiendo del estado de completada (?completada=false / ?completada=true)
        List<Tarea> resultado = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (tarea.isCompletada() == completada) {
                resultado.add(tarea);
            }
        }
        return resultado;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> detalle(@PathVariable(name = "id") int id) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                return ResponseEntity.ok(tarea);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public Tarea actualizar(
            @PathVariable(name = "id") int id,
            @RequestBody Tarea datos) {

        for (int i = 0; i < tareas.size(); i++) {
            if (tareas.get(i).getId() == id) {
                datos.setId(id);
                tareas.set(i, datos);
                return datos;
            }
        }
        return null;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Tarea> crear(@RequestBody Tarea tarea) {
        tarea.setId(siguienteId);
        siguienteId = siguienteId + 1;
        tareas.add(tarea);

        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tarea.getId())
                .toUri();
        return ResponseEntity.created(ubicacion).body(tarea);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable(name = "id") int id) {
        tareas.removeIf(tarea -> tarea.getId() == id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/espejo")
    public Tarea espejo(@RequestBody Tarea tarea) {
        System.out.println("He recibido: " + tarea.getTitulo()
                + " / " + tarea.getPrioridad()
                + " / completada=" + tarea.isCompletada());
        return tarea;
    }

    @PatchMapping("/{id}")
    public Tarea modificar(
            @PathVariable(name = "id") int id,
            @RequestBody Tarea cambios) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                if (cambios.getTitulo() != null) {
                    tarea.setTitulo(cambios.getTitulo());
                }
                if (cambios.getPrioridad() != null) {
                    tarea.setPrioridad(cambios.getPrioridad());
                }
                if (cambios.isCompletada() != null) {
                    tarea.setCompletada(cambios.isCompletada());
                }
                if (cambios.getProyectoId() != null) {
                    tarea.setProyectoId(cambios.getProyectoId());
                }
                return tarea;
            }
        }
        return null;
    }
}