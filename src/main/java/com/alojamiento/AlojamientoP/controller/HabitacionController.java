package com.alojamiento.AlojamientoP.controller;

import com.alojamiento.AlojamientoP.model.Habitacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/habitaciones")

public class HabitacionController {
    private static final Logger logger = LoggerFactory.getLogger(HabitacionController.class);
    private final List<Habitacion> habitaciones = new ArrayList<>();

    @GetMapping
    public List<Habitacion> getAll() {
        logger.info("Cargando todas las habitaciones");
        return habitaciones;
    }

    @GetMapping("/{id}")
    public Habitacion getById(@PathVariable Long id) {
        Optional<Habitacion> habitacion = habitaciones.stream()
                .filter(h -> h.getId().equals(id))
                .findFirst();
        if (habitacion.isPresent()) {
            logger.info("Habitación encontrada: {}", habitacion.get().getNumero());
            return habitacion.get();
        } else {
            logger.warn("Habitación con ID {} no encontrada", id);
            return null;
        }
    }

    @PostMapping
    public Habitacion create(@RequestBody Habitacion nueva) {
        if (nueva.getId() == null || nueva.getNumero() == null || nueva.getTipo() == null) {
            logger.error("Datos incompletos para crear una nueva habitación: {}", nueva);
            return null;
        }
        habitaciones.add(nueva);
        logger.info("Habitación añadida: {}", nueva);
        return nueva;
    }

    @PutMapping("/{id}")
    public Habitacion update(@PathVariable Long id, @RequestBody Habitacion actualizada) {
        for (Habitacion h : habitaciones) {
            if (h.getId().equals(id)) {
                h.setNumero(actualizada.getNumero());
                h.setTipo(actualizada.getTipo());
                logger.info("Habitación actualizada: {}", h);
                return h;
            }
        }
        logger.warn("No se encontró la habitación con ID {} para actualizar", id);
        return null;
    }

    @PatchMapping("/{id}")
    public Habitacion patch(@PathVariable Long id, @RequestBody Habitacion datos) {
        for (Habitacion h : habitaciones) {
            if (h.getId().equals(id)) {
                if (datos.getNumero() != null) h.setNumero(datos.getNumero());
                if (datos.getTipo() != null) h.setTipo(datos.getTipo());
                logger.info("Habitación modificada parcialmente: {}", h);
                return h;
            }
        }
        logger.warn("No se encontró la habitación con ID {} para modificación parcial", id);
        return null;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        boolean eliminado = habitaciones.removeIf(h -> h.getId().equals(id));
        if (eliminado) {
            logger.info("Habitación con ID {} eliminada", id);
            return "Habitación eliminada";
        } else {
            logger.warn("No se encontró la habitación con ID {} para eliminar", id);
            return "No se encontró la habitación";
        }
    }
}
