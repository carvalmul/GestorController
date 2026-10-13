package com.ejemplo.gestor.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TareaPatchRequest {

    @Size(min = 3, max = 120)
    private String titulo;

    @Pattern(regexp = "baja|media|alta")
    private String prioridad;

    private Boolean completada;

    public TareaPatchRequest() {
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

    public Boolean getCompletada() {
        return completada;
    }

    public void setCompletada(Boolean completada) {
        this.completada = completada;
    }
}