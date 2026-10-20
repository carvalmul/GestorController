package com.ejemplo.gestor.repository;

import com.ejemplo.gestor.model.Proyecto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProyectoRepository {

    // Repositorio en memoria para los proyectos.
    // Es la capa que guarda la información real de la app en una lista, sin base de datos externa.
    private final List<Proyecto> proyectos = new ArrayList<>();
    private int siguienteId = 1;

    // Devuelve una copia de la lista para evitar modificaciones externas desde fuera del repositorio.
    public List<Proyecto> findAll() {
        return new ArrayList<>(proyectos);
    }

    // Busca por id y devuelve Optional para diferenciar entre "encontrado" y "no encontrado".
    public Optional<Proyecto> findById(int id) {
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() != null && proyecto.getId() == id) {
                return Optional.of(proyecto);
            }
        }
        return Optional.empty();
    }

    // Guarda un proyecto. Si no tiene id, se le asigna uno nuevo y se añade a la lista.
    public Proyecto save(Proyecto proyecto) {
        if (proyecto.getId() == null || proyecto.getId() == 0) {
            proyecto.setId(siguienteId);
            siguienteId++;
            proyectos.add(proyecto);
        }
        return proyecto;
    }

    // Elimina un proyecto por id. Devuelve true si se borró algo.
    public boolean deleteById(int id) {
        return proyectos.removeIf(proyecto -> proyecto.getId() != null && proyecto.getId() == id);
    }

    // Comprueba si existe un proyecto con ese id.
    public boolean existsById(int id) {
        return findById(id).isPresent();
    }

    // Revisa si ya hay un proyecto con el mismo nombre ignorando mayúsculas/minúsculas.
    // Este método lo usa el service para impedir duplicados.
    public boolean existsByNombreIgnoreCase(String nombre) {
        if (nombre == null) {
            return false;
        }
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getNombre() != null && proyecto.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }
}
