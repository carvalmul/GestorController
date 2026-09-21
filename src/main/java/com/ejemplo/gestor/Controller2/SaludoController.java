package com.ejemplo.gestor.Controller2;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class SaludoController {
    @GetMapping("/saludo")
    public String saludo(
        @RequestParam(name = "nombre", defaultValue = "mundo") String nombre) {
        return "Hola, " + nombre + ". Te responde mi servidor.";
    }

    //@GetMapping("/incidencias")
    //public String incidencias(
    //    @RequestParam(name = "estado", defaultValue = "todas") String estado,
    //    @RequestParam(name = "pagina", defaultValue = "1") int pagina) {
    //    return "Buscando incidencias con estado: " + estado + ", en la página: " + pagina;
    //}

    // no va este por ahora
    @GetMapping("/informes")
    public String informes(
        @RequestParam(name = "desde") LocalDateTime desde,
        @RequestParam(name = "activo", defaultValue = "true") boolean activo) {
        return "Desde: " + desde + " (día " + desde.getDayOfMonth() + " del mes " + desde.getMonth() + "), activo: " + activo;
    }

    // @GetMapping("/usuarios/{id}")
    // public String usuario(@PathVariable(name = "id") int id) {
    //    return "Id del usuario: " + id;
    // }

    //@GetMapping("/proyectos/{proyectoId}/incidencias/{incidenciaId}")
    //public String incidenciaEnProyecto(
    //    @PathVariable(name = "proyectoId") int proyectoId,
    //    @PathVariable(name = "incidenciaId") int incidenciaId) {
    //    return "Incidencia con id: " + incidenciaId + " en el proyecto con id: " + proyectoId;
    //}
    
}