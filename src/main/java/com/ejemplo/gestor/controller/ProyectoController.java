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

import com.ejemplo.gestor.dto.ProyectoPatchRequest;
import com.ejemplo.gestor.dto.ProyectoRequest;
import com.ejemplo.gestor.dto.ProyectoResponse;
import com.ejemplo.gestor.dto.TareaResponse;
import com.ejemplo.gestor.mapper.ProyectoMapper;
import com.ejemplo.gestor.mapper.TareaMapper;
import com.ejemplo.gestor.memoria.MemoriaProyecto;
import com.ejemplo.gestor.model.Proyecto;
import com.ejemplo.gestor.service.ProyectoService;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    // Este controlador es la capa HTTP del dominio de proyectos.
    // Aquí llega la petición, se valida, se llama al servicio y se devuelve el resultado ya transformado.
    private final ProyectoService servicio = new ProyectoService();

    public ProyectoController() {
    }

    public ProyectoController(MemoriaProyecto memoria) {
    }

    // Lista proyectos. Si el cliente envía el filtro "activo", el service devuelve solo
    // proyectos activos o inactivos. El resultado se convierte a DTO antes de mandarlo al cliente.
    @GetMapping
    public List<ProyectoResponse> lista(
            @RequestParam(name = "activo", required = false) Boolean activo) {

        return ProyectoMapper.aRespuestas(servicio.listar(activo));
    }

    // Devuelve un proyecto concreto por su id.
    // El service lo busca en el repositorio y si no existe lanza una excepción controlada.
    @GetMapping("/{id}")
    public ProyectoResponse detalle(
            @PathVariable(name = "id") int id) {

        return ProyectoMapper.aRespuesta(servicio.obtener(id));
    }

    // Busca las tareas asociadas a un proyecto.
    // El controller solo delega; la lógica real de filtrado por proyecto está en el service/repository.
    @GetMapping("/{id}/tareas")
    public List<TareaResponse> tareasDelProyecto(
            @PathVariable(name = "id") int id) {

        return TareaMapper.aRespuestas(servicio.tareasDelProyecto(id));
    }

    // Crea un proyecto nuevo.
    // Primero se convierte el JSON a modelo, luego se valida la lógica de negocio y se guarda.
    @PostMapping
    public ResponseEntity<ProyectoResponse> crear(@Valid @RequestBody ProyectoRequest peticion) {

        Proyecto creado = servicio.crear(ProyectoMapper.aModelo(peticion));

        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(creado.getId()).toUri();

        return ResponseEntity.created(ubicacion).body(ProyectoMapper.aRespuesta(creado));
    }

    // Sustituye completamente un proyecto por uno nuevo.
    // El service toma el proyecto existente y reemplaza sus propiedades principales.
    @PutMapping("/{id}")
    public ResponseEntity<ProyectoResponse> actualizar(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody ProyectoRequest peticion) {

        Proyecto actualizado = servicio.reemplazar(id, ProyectoMapper.aModelo(peticion));
        return ResponseEntity.ok(ProyectoMapper.aRespuesta(actualizado));
    }

    // Elimina un proyecto por id.
    // El controller no borra nada directamente; delega la operación al service.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable(name = "id") int id) {

        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Modificación parcial. Se recibe solo los campos que vienen en el JSON y se actualizan los que no sean nulos.
    @PatchMapping("/{id}")
    public ResponseEntity<ProyectoResponse> modificar(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody ProyectoPatchRequest cambios) {

        Proyecto actualizado = servicio.modificar(id, ProyectoMapper.aModelo(cambios));
        return ResponseEntity.ok(ProyectoMapper.aRespuesta(actualizado));
    }
}