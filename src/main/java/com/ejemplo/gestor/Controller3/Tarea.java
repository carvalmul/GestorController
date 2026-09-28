package com.ejemplo.gestor.Controller3;

public class Tarea {

    // Integer y Boolean envez de Int y boolean para poder ser nulos y asi poder diferenciar entre no enviado y enviado con valor false
    private Integer id;
    private String titulo;
    private String prioridad;
    private Boolean completada;

    public Tarea() {
    }

    public Tarea(Integer id, String titulo, String prioridad, Boolean completada) {
        this.id = id;
        this.titulo = titulo;
        this.prioridad = prioridad;
        this.completada = completada;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}