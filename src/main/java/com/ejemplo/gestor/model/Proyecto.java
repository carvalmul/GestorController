package com.ejemplo.gestor.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Proyecto {

    // Modelo principal del dominio: un proyecto tiene un id, un nombre y una descripción.
    // Este objeto es el que viaja entre el service y el repository, no el DTO recibido por HTTP.
    private Integer id;
    private String nombre;
    private String descripcion;
    private Boolean activo;
    private int numeroDeIncidencias;

    @JsonCreator
    public Proyecto() {
    }

    public Proyecto(Integer id, String nombre, String descripcion, Boolean activo, int numeroDeIncidencias) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
        this.numeroDeIncidencias = numeroDeIncidencias;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean isActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public int getNumeroDeIncidencias() {
        return numeroDeIncidencias;
    }

    public void setNumeroDeIncidencias(int numeroDeIncidencias) {
        this.numeroDeIncidencias = numeroDeIncidencias;
    }
}
