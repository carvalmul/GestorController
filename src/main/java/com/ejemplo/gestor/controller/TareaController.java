package com.ejemplo.gestor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import com.ejemplo.gestor.dto.TareaPatchRequest;
import com.ejemplo.gestor.dto.TareaRequest;
import com.ejemplo.gestor.dto.TareaResponse;
import com.ejemplo.gestor.mapper.TareaMapper;
import com.ejemplo.gestor.memoria.MemoriaProyecto;
import com.ejemplo.gestor.model.Tarea;
import com.ejemplo.gestor.service.TareaService;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    // Este controlador maneja el ciclo de vida de las tareas.
    // La secuencia típica es: llega JSON -> se convierte a modelo -> servicio aplica reglas -> repositorio guarda -> se devuelve DTO.
    private final TareaService servicio = new TareaService();

    public TareaController() {
    }

    public TareaController(MemoriaProyecto memoria) {
    }

    // Lista tareas. El filtro "completada" permite ver solo tareas finales o pendientes.
    @GetMapping
    public List<TareaResponse> lista(
            @RequestParam(name = "completada", required = false) Boolean completada) {

        return TareaMapper.aRespuestas(servicio.listar(completada));
    }

    // Devuelve una tarea puntual por id. Si no existe, el servicio lanza una excepción
    // que será capturada por el manejador global de errores.
    @GetMapping("/{id}")
    public TareaResponse detalle(@PathVariable(name = "id") int id) {
        return TareaMapper.aRespuesta(servicio.obtener(id));
    }

    // Sustitución completa de una tarea.
    // Reemplaza todos los campos relevantes de la entidad con los datos recibidos en la petición.
    @PutMapping("/{id}")
    public ResponseEntity<TareaResponse> actualizar(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody TareaRequest peticion) {

        Tarea tareaActualizada = servicio.reemplazar(id, TareaMapper.aModelo(peticion));
        return ResponseEntity.ok(TareaMapper.aRespuesta(tareaActualizada));
    }

    // Crea una tarea nueva.
    // El service marca por defecto la tarea como no completada, y luego el repositorio la guarda.
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<TareaResponse> crear(@Valid @RequestBody TareaRequest peticion) {
        Tarea creada = servicio.crear(TareaMapper.aModelo(peticion));

        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(creada.getId()).toUri();

        return ResponseEntity.created(ubicacion).body(TareaMapper.aRespuesta(creada));
    }

    // Elimina una tarea por id.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable(name = "id") int id) {

        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Actualización parcial de una tarea.
    // Solo cambia los campos que vienen distintos de null en el JSON.
    @PatchMapping("/{id}")
    public ResponseEntity<TareaResponse> modificar(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody TareaPatchRequest cambios) {

        Tarea tareaActualizada = servicio.modificar(id, TareaMapper.aModelo(cambios));
        return ResponseEntity.ok(TareaMapper.aRespuesta(tareaActualizada));
    }
}