package com.ejemplo.gestor.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.ejemplo.gestor.validacion.PrioridadValida;

public class TareaPatchRequest {

    @Size(min = 3, max = 120, message = "El título debe tener entre 3 y 120 caracteres")
    private String titulo;

    @PrioridadValida
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