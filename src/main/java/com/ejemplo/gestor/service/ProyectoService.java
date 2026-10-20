package com.ejemplo.gestor.service;

import com.ejemplo.gestor.error.ConflictoException;
import com.ejemplo.gestor.error.RecursoNoEncontradoException;
import com.ejemplo.gestor.model.Proyecto;
import com.ejemplo.gestor.model.Tarea;
import com.ejemplo.gestor.repository.ProyectoRepository;
import com.ejemplo.gestor.repository.TareaRepository;

import java.util.ArrayList;
import java.util.List;

public class ProyectoService {

    // Aquí está la lógica de negocio del dominio de proyectos.
    // El controller llama a este servicio, y este decide si se permite o se rechaza la operación.
    private final ProyectoRepository repositorio = new ProyectoRepository();
    private final TareaRepository tareas = new TareaRepository();

    // Devuelve la lista de proyectos, opcionalmente filtrada por estado activo/inactivo.
    public List<Proyecto> listar(Boolean activo) {
        List<Proyecto> resultado = new ArrayList<>();
        for (Proyecto proyecto : repositorio.findAll()) {
            if (activo == null || Boolean.TRUE.equals(proyecto.isActivo()) == activo) {
                resultado.add(proyecto);
            }
        }
        return resultado;
    }

    // Busca un proyecto por id; si no existe, lanza la excepción estándar del dominio.
    public Proyecto obtener(int id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("proyecto", id));
    }

    // Crea un proyecto nuevo y valida la regla de negocio: no puede haber dos con el mismo nombre.
    public Proyecto crear(Proyecto proyecto) {
        // Regla de negocio: el nombre de un proyecto no puede repetirse, aunque solo cambie mayúsculas y minúsculas.
        if (repositorio.existsByNombreIgnoreCase(proyecto.getNombre())) {
            throw new ConflictoException("Ya existe un proyecto con el nombre: " + proyecto.getNombre());
        }

        proyecto.setActivo(true);
        return repositorio.save(proyecto);
    }

    // Sustitución completa del proyecto existente.
    // Se toma la entidad actual y se rellenan todos los campos con los nuevos valores.
    public Proyecto reemplazar(int id, Proyecto datos) {
        Proyecto existente = obtener(id);
        existente.setNombre(datos.getNombre());
        existente.setDescripcion(datos.getDescripcion());
        existente.setActivo(datos.isActivo());
        existente.setNumeroDeIncidencias(datos.getNumeroDeIncidencias());
        return existente;
    }

    // Modificación parcial: solo se actualizan los campos que vienen a cambio.
    public Proyecto modificar(int id, Proyecto cambios) {
        Proyecto existente = obtener(id);

        if (cambios.getNombre() != null) {
            existente.setNombre(cambios.getNombre());
        }

        if (cambios.getDescripcion() != null) {
            existente.setDescripcion(cambios.getDescripcion());
        }

        if (cambios.isActivo() != null) {
            existente.setActivo(cambios.isActivo());
        }

        return existente;
    }

    // Devuelve todas las tareas asociadas a un proyecto concreto.
    // Antes de filtrar, comprueba que el proyecto exista.
    public List<Tarea> tareasDelProyecto(int id) {
        obtener(id);
        return tareas.findByProyectoId(id);
    }

    // Elimina un proyecto del repositorio.
    public void eliminar(int id) {
        repositorio.deleteById(id);
    }
}
