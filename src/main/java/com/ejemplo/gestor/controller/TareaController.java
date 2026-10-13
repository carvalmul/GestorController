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

import jakarta.validation.Valid;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.ejemplo.gestor.dto.TareaResponse;
import com.ejemplo.gestor.memoria.MemoriaProyecto;
import com.ejemplo.gestor.dto.TareaRequest;
import com.ejemplo.gestor.dto.TareaPatchRequest;
import com.ejemplo.gestor.mapper.TareaMapper;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    private final List<Tarea> tareas;

    public TareaController(MemoriaProyecto memoria) {
        this.tareas = memoria.getTareas();
    }

    private int siguienteId = 1;

    @GetMapping
    public List<TareaResponse> lista(
            @RequestParam(name = "completada", required = false) Boolean completada) {

        List<Tarea> seleccion = new ArrayList<>();

        for (Tarea tarea : tareas) {
            if (completada == null || tarea.isCompletada() == completada) {
                seleccion.add(tarea);
            }
        }
        return TareaMapper.aRespuestas(seleccion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaResponse> detalle(@PathVariable(name = "id") int id) {

        Tarea tareaEncontrada = buscar(id);

        if (tareaEncontrada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(TareaMapper.aRespuesta(tareaEncontrada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TareaResponse> actualizar(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody TareaRequest peticion) {

        Tarea tarea = buscar(id);

        if (tarea == null) {
            return ResponseEntity.notFound().build();
        }

        TareaMapper.aplicar(peticion, tarea);

        return ResponseEntity.ok(TareaMapper.aRespuesta(tarea));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<TareaResponse> crear(@Valid @RequestBody TareaRequest peticion) {
        Tarea tarea = TareaMapper.aModelo(peticion);
        tarea.setId(siguienteId);
        siguienteId = siguienteId + 1;
        tareas.add(tarea);

        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(tarea.getId()).toUri();

        return ResponseEntity.created(ubicacion).body(TareaMapper.aRespuesta(tarea));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable(name = "id") int id) {

        tareas.removeIf(tarea -> tarea.getId() == id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TareaResponse> modificar(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody TareaPatchRequest cambios) {

        Tarea tarea = buscar(id);

        if (tarea == null) {
            return ResponseEntity.notFound().build();
        }

        TareaMapper.aplicar(cambios, tarea);

        return ResponseEntity.ok(TareaMapper.aRespuesta(tarea));
    }

    private Tarea buscar(int id) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                return tarea;
            }
        }
        return null;
    }
}