package com.ejemplo.gestor.controller;

public class Tarea {

    private Integer id;
    private String titulo;
    private String prioridad;
    private Boolean completada;
    private int proyectoId;

    // Dato interno que NO queremos mostrar en las respuestas
    private String notaInterna = "pendiente de revisión interna";

    public Tarea() {
    }

    public Tarea(Integer id, String titulo, String prioridad, Boolean completada, int proyectoId) {
        this.id = id;
        this.titulo = titulo;
        this.prioridad = prioridad;
        this.completada = completada;
        this.proyectoId = proyectoId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(int proyectoId) {
        this.proyectoId = proyectoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public Boolean isCompletada() {
        return completada;
    }

    public void setCompletada(Boolean completada) {
        this.completada = completada;
    }

    public String getNotaInterna() {
        return notaInterna;
    }
}