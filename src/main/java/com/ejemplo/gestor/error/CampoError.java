package com.ejemplo.gestor.error;

// Representa un error de validación asociado a un campo concreto del JSON recibido.
// Se usa en la respuesta del ManejadorDeErrores cuando falla Bean Validation.
public record CampoError(String campo, String motivo) {
}