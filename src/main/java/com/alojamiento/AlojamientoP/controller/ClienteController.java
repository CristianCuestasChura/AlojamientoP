package com.alojamiento.AlojamientoP.controller;

import com.alojamiento.AlojamientoP.model.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
    private final List<Cliente> clientes = new ArrayList<>();

    @GetMapping
    public List<Cliente> getAll() {
        logger.info("Cargando todos los clientes");
        return clientes;
    }

    @GetMapping("/{id}")
    public Cliente getById(@PathVariable Long id) {
        Optional<Cliente> cliente = clientes.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        if (cliente.isPresent()) {
            logger.info("Cliente encontrado: {}", cliente.get());
            return cliente.get();
        } else {
            logger.warn("Cliente con ID {} no encontrado", id);
            return null;
        }
    }

    @PostMapping
    public Cliente create(@RequestBody Cliente nuevo) {
        if (nuevo.getId() == null || nuevo.getNombre() == null || nuevo.getGmail() == null || nuevo.getTelefono() == null) {
            logger.error("los datos estan incompletos para la creacion de un cliente: {}", nuevo);
            return null;
        }
        clientes.add(nuevo);
        logger.info("Cliente añadido: {}", nuevo);
        return nuevo;
    }

    @PutMapping("/{id}")
    public Cliente update(@PathVariable Long id, @RequestBody Cliente actualizado) {
        for (Cliente c : clientes) {
            if (c.getId().equals(id)) {
                c.setNombre(actualizado.getNombre());
                c.setGmail(actualizado.getGmail());
                c.setTelefono(actualizado.getTelefono());
                logger.info("Cliente actualizado: {}", c);
                return c;
            }
        }
        logger.warn("No se encontró el cliente con ID {} para actualizar", id);
        return null;
    }

    @PatchMapping("/{id}")
    public Cliente patch(@PathVariable Long id, @RequestBody Cliente datos) {
        for (Cliente c : clientes) {
            if (c.getId().equals(id)) {
                if (datos.getNombre() != null) c.setNombre(datos.getNombre());
                if (datos.getGmail() != null) c.setGmail(datos.getGmail());
                if (datos.getTelefono() != null) c.setTelefono(datos.getTelefono());
                logger.info("Cliente modificado parcialmente: {}", c);
                return c;
            }
        }
        logger.warn("No se encontró el cliente con ID {} para modificación parcial", id);
        return null;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        boolean eliminado = clientes.removeIf(c -> c.getId().equals(id));
        if (eliminado) {
            logger.info("Cliente con ID {} eliminado", id);
            return "Cliente eliminado";
        } else {
            logger.warn("No se encontró el cliente con ID {} para eliminar", id);
            return "No se encontró el cliente";
        }
    }
}
