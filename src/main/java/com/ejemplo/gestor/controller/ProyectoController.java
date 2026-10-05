package com.ejemplo.gestor.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import com.ejemplo.gestor.dto.ProyectoResponse;
import com.ejemplo.gestor.dto.TareaResponse;
import com.ejemplo.gestor.memoria.MemoriaProyecto;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    private final List<Proyecto> proyectos;
    private final List<Tarea> tareas;

    public ProyectoController(MemoriaProyecto memoria) {
        this.proyectos = memoria.getProyectos();
        this.tareas = memoria.getTareas();
    }

    private int siguienteId = 1;

    @GetMapping
    public List<ProyectoResponse> lista(
            @RequestParam(name = "activo", required = false) Boolean activo) {

        List<ProyectoResponse> resultado = new ArrayList<>();

        for (Proyecto proyecto : proyectos) {
            if (activo == null || proyecto.isActivo() == activo) {
                resultado.add(ProyectoResponse.desde(proyecto));
            }
        }

        return resultado;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoResponse> detalle(
            @PathVariable(name = "id") int id) {

        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {
                return ResponseEntity.ok(ProyectoResponse.desde(proyecto));
            }
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/tareas")
    public ResponseEntity<List<TareaResponse>> tareasDelProyecto(
            @PathVariable(name = "id") int id) {

        boolean existe = false;

        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {
                existe = true;
                break;
            }
        }

        if (!existe) {
            return ResponseEntity.notFound().build();
        }

        List<TareaResponse> resultado = new ArrayList<>();

        for (Tarea tarea : tareas) {
            if (tarea.getProyectoId() == id) {
                resultado.add(TareaResponse.desde(tarea));
            }
        }

        return ResponseEntity.ok(resultado);
    }

    @PostMapping
    public ProyectoResponse crear(@RequestBody Proyecto proyecto) {

        proyecto.setId(siguienteId);
        siguienteId++;

        proyectos.add(proyecto);

        return ProyectoResponse.desde(proyecto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProyectoResponse> actualizar(
            @PathVariable(name = "id") int id,
            @RequestBody Proyecto datos) {

        for (int i = 0; i < proyectos.size(); i++) {

            if (proyectos.get(i).getId() == id) {

                datos.setId(id);
                proyectos.set(i, datos);

                return ResponseEntity.ok(ProyectoResponse.desde(datos));
            }
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable(name = "id") int id) {

        proyectos.removeIf(proyecto -> proyecto.getId() == id);

        return ResponseEntity.noContent().build();
    }
}