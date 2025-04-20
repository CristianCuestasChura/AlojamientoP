package com.alojamiento.AlojamientoP.controller;


import  com.alojamiento.AlojamientoP.model.Persona;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personas")
public class PersonaController {

    private static final Logger logger = LoggerFactory.getLogger(PersonaController.class);
    private final List<Persona> personas = new ArrayList<>();



    @GetMapping
    public List<Persona> getAll() {
        logger.info("Cargando Personas");
        return personas;
    }

    @GetMapping("/{id}")
    public Persona getById(@PathVariable Long id) {
        Optional<Persona> persona = personas.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        if (persona.isPresent()){
            logger.info("persona encontrada: {}", persona.get().getNombre());
            return persona.get();
        } else {
            logger.warn("la persona con el id {} no fue encontrada", id);
            return null;
        }
    }

    @PostMapping
    public Persona create(@RequestBody Persona nueva) {
        if (nueva.getId()==null || nueva.getNombre()==null || nueva.getgmail()==null){
            logger.error("Datos incompletos para la correcta realizacion de una nueva persona: {}",nueva);
            return null;
        }
        personas.add(nueva);
        logger.info("La persona fue añadida correctamente: {}",nueva);
        return nueva;


    }

    @PutMapping("/{id}")
    public Persona update(@PathVariable Long id, @RequestBody Persona actualizada) {
        for (Persona p : personas) {
            if (p.getId().equals(id)) {
                p.setNombre(actualizada.getNombre());
                p.setgmail(actualizada.getgmail());
                logger.info("Persona actualizada: {}", p);
                return p;
            }
        }
        logger.warn("No se pudo encontrar a la persona con el id: {} para la actualizacion", id);
        return null;
    }

    @PatchMapping("/{id}")
    public Persona patch(@PathVariable Long id, @RequestBody Persona datos) {
        for (Persona p : personas) {
            if (p.getId().equals(id)) {
                if (datos.getNombre() != null) p.setNombre(datos.getNombre());
                if (datos.getgmail() != null) p.setgmail(datos.getgmail());
                logger.info("Persona modificada parcialmente: {}", p);
                return p;
            }
        }
        logger.warn("No se pudo encontrar a la persona con el id: {} para la actualizacion parcial", id);
        return null;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        boolean eliminado = personas.removeIf(p -> p.getId().equals(id));
        if (eliminado){
            logger.info("la persona con el id {} fue correctamente eliminado", id);
            return "persona eliminada";
        }else {
            logger.warn("No se pudo encontrar a la persona con el id: {} para ser eliminado", id);
            return "persona no encontrada";
        }
    }
}
