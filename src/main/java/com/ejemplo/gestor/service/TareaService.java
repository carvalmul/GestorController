package com.ejemplo.gestor.service;

import com.ejemplo.gestor.error.RecursoNoEncontradoException;
import com.ejemplo.gestor.model.Tarea;
import com.ejemplo.gestor.repository.TareaRepository;

import java.util.ArrayList;
import java.util.List;

public class TareaService {

    // Lógica de negocio de las tareas.
    // Todo pasa por este servicio antes de guardar o devolver datos al controller.
    private final TareaRepository repositorio = new TareaRepository();

    // Lista tareas y puede filtrar por si están completadas o no.
    public List<Tarea> listar(Boolean completada) {
        List<Tarea> resultado = new ArrayList<>();
        for (Tarea tarea : repositorio.findAll()) {
            if (completada == null || Boolean.TRUE.equals(tarea.isCompletada()) == completada) {
                resultado.add(tarea);
            }
        }
        return resultado;
    }

    // Busca una tarea por id; si no existe, lanza la excepción del dominio.
    public Tarea obtener(int id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("tarea", id));
    }

    // Crea una tarea nueva. Por defecto se marca como pendiente.
    public Tarea crear(Tarea tarea) {
        tarea.setCompletada(false);
        return repositorio.save(tarea);
    }

    // Sustitución completa de los datos de una tarea.
    public Tarea reemplazar(int id, Tarea datos) {
        Tarea existente = obtener(id);
        existente.setTitulo(datos.getTitulo());
        existente.setPrioridad(datos.getPrioridad());
        existente.setProyectoId(datos.getProyectoId());
        existente.setCompletada(datos.isCompletada());
        return existente;
    }

    // Modificación parcial. Solo cambia los campos que vienen informados.
    public Tarea modificar(int id, Tarea cambios) {
        Tarea existente = obtener(id);

        if (cambios.getTitulo() != null) {
            existente.setTitulo(cambios.getTitulo());
        }

        if (cambios.getPrioridad() != null) {
            existente.setPrioridad(cambios.getPrioridad());
        }

        if (cambios.getProyectoId() != 0) {
            existente.setProyectoId(cambios.getProyectoId());
        }

        if (cambios.isCompletada() != null) {
            existente.setCompletada(cambios.isCompletada());
        }

        return existente;
    }

    // Elimina una tarea del repositorio.
    public void eliminar(int id) {
        repositorio.deleteById(id);
    }
}
