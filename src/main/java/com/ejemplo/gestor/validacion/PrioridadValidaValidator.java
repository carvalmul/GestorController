package com.ejemplo.gestor.validacion;

import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PrioridadValidaValidator implements ConstraintValidator<PrioridadValida, String> {

    // Lista de valores admitidos para la prioridad de una tarea.
    private static final List<String> VALIDAS = List.of("baja", "media", "alta");

    // Este validador se ejecuta automáticamente por Spring cuando se usa @PrioridadValida.
    // Si el valor no está en la lista, la petición falla antes de llegar al servicio.
    @Override
    public boolean isValid(String valor, ConstraintValidatorContext contexto) {
        if (valor == null) {
            return true;
        }
        return VALIDAS.contains(valor);
    }
}