package com.ejemplo.gestor.repository;

import com.ejemplo.gestor.model.Tarea;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TareaRepository {

    // Repositorio en memoria para las tareas.
    // Es el punto de acceso que guarda las tareas y permite consultarlas por id o por proyecto.
    private final List<Tarea> tareas = new ArrayList<>();
    private int siguienteId = 1;

    // Devuelve una copia de la colección para no exponer la lista interna directamente.
    public List<Tarea> findAll() {
        return new ArrayList<>(tareas);
    }

    // Busca una tarea por su identificador.
    public Optional<Tarea> findById(int id) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() != null && tarea.getId() == id) {
                return Optional.of(tarea);
            }
        }
        return Optional.empty();
    }

    // Filtra las tareas que pertenecen a un proyecto concreto.
    // Este método lo usa ProyectoService.tareasDelProyecto().
    public List<Tarea> findByProyectoId(int proyectoId) {
        List<Tarea> resultado = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (tarea.getProyectoId() == proyectoId) {
                resultado.add(tarea);
            }
        }
        return resultado;
    }

    // Guarda una tarea nueva y le asigna id si todavía no lo tiene.
    public Tarea save(Tarea tarea) {
        if (tarea.getId() == null || tarea.getId() == 0) {
            tarea.setId(siguienteId);
            siguienteId++;
            tareas.add(tarea);
        }
        return tarea;
    }

    // Elimina la tarea con ese id.
    public boolean deleteById(int id) {
        return tareas.removeIf(tarea -> tarea.getId() != null && tarea.getId() == id);
    }

    // Comprueba si existe una tarea con ese id.
    public boolean existsById(int id) {
        return findById(id).isPresent();
    }
}
