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

    public PersonaController() {
        personas.add(new Persona(1L, "Ana", "ana@email.com"));
    }

    @GetMapping
    public List<Persona> getAll() {
        logger.info("Listando personas");
        return personas;
    }

    @GetMapping("/{id}")
    public Persona getById(@PathVariable Long id) {
        Optional<Persona> persona = personas.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        return persona.orElse(null);
    }

    @PostMapping
    public Persona create(@RequestBody Persona nueva) {
        personas.add(nueva);
        logger.info("Persona añadida: {}", nueva);
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
        return null;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        boolean eliminado = personas.removeIf(p -> p.getId().equals(id));
        return eliminado ? "Persona eliminada" : "No se encontró la persona";
    }
}
