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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.ejemplo.gestor.dto.ProyectoPatchRequest;
import com.ejemplo.gestor.dto.ProyectoRequest;
import com.ejemplo.gestor.dto.ProyectoResponse;
import com.ejemplo.gestor.dto.TareaResponse;
import com.ejemplo.gestor.mapper.ProyectoMapper;
import com.ejemplo.gestor.mapper.TareaMapper;
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

        List<Proyecto> seleccion = new ArrayList<>();

        for (Proyecto proyecto : proyectos) {
            if (activo == null || proyecto.isActivo() == activo) {
                seleccion.add(proyecto);
            }
        }

        return ProyectoMapper.aRespuestas(seleccion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoResponse> detalle(
            @PathVariable(name = "id") int id) {

        Proyecto proyecto = buscar(id);

        if (proyecto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ProyectoMapper.aRespuesta(proyecto));
    }

    @GetMapping("/{id}/tareas")
    public ResponseEntity<List<TareaResponse>> tareasDelProyecto(
            @PathVariable(name = "id") int id) {

        Proyecto proyecto = buscar(id);

        if (proyecto == null) {
            return ResponseEntity.notFound().build();
        }

        List<Tarea> seleccion = new ArrayList<>();

        for (Tarea tarea : tareas) {
            if (tarea.getProyectoId() == id) {
                seleccion.add(tarea);
            }
        }

        return ResponseEntity.ok(TareaMapper.aRespuestas(seleccion));
    }

    @PostMapping
    public ResponseEntity<ProyectoResponse> crear(@Valid @RequestBody ProyectoRequest peticion) {

        Proyecto proyecto = ProyectoMapper.aModelo(peticion);

        proyecto.setId(siguienteId);
        siguienteId++;

        proyectos.add(proyecto);

        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(proyecto.getId()).toUri();

        return ResponseEntity.created(ubicacion).body(ProyectoMapper.aRespuesta(proyecto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProyectoResponse> actualizar(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody ProyectoRequest peticion) {

        Proyecto proyecto = buscar(id);

        if (proyecto == null) {
            return ResponseEntity.notFound().build();
        }

        ProyectoMapper.aplicar(peticion, proyecto);

        return ResponseEntity.ok(ProyectoMapper.aRespuesta(proyecto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable(name = "id") int id) {

        proyectos.removeIf(proyecto -> proyecto.getId() == id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProyectoResponse> modificar(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody ProyectoPatchRequest cambios) {

        Proyecto proyecto = buscar(id);

        if (proyecto == null) {
            return ResponseEntity.notFound().build();
        }

        ProyectoMapper.aplicar(cambios, proyecto);

        return ResponseEntity.ok(ProyectoMapper.aRespuesta(proyecto));
    }

    private Proyecto buscar(int id) {

        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {
                return proyecto;
            }
        }

        return null;
    }

    
}