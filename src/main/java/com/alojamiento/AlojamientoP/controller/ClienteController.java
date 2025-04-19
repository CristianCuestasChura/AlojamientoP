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

    public ClienteController() {
        clientes.add(new Cliente(1L, "Carlos", "Reservado"));
    }

    @GetMapping
    public List<Cliente> getAll() {
        logger.info("Listando clientes");
        return clientes;
    }

    @GetMapping("/{id}")
    public Cliente getById(@PathVariable Long id) {
        Optional<Cliente> cliente = clientes.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        return cliente.orElse(null);
    }

    @PostMapping
    public Cliente create(@RequestBody Cliente nuevo) {
        clientes.add(nuevo);
        logger.info("Cliente añadido: {}", nuevo);
        return nuevo;
    }

    @PutMapping("/{id}")
    public Cliente update(@PathVariable Long id, @RequestBody Cliente actualizado) {
        for (Cliente c : clientes) {
            if (c.getId().equals(id)) {
                c.setNombre(actualizado.getNombre());
                c.setEstado(actualizado.getEstado());
                logger.info("Cliente actualizado: {}", c);
                return c;
            }
        }
        return null;
    }

    @PatchMapping("/{id}")
    public Cliente patch(@PathVariable Long id, @RequestBody Cliente datos) {
        for (Cliente c : clientes) {
            if (c.getId().equals(id)) {
                if (datos.getNombre() != null) c.setNombre(datos.getNombre());
                if (datos.getEstado() != null) c.setEstado(datos.getEstado());
                logger.info("Cliente parcialmente modificado: {}", c);
                return c;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        boolean eliminado = clientes.removeIf(c -> c.getId().equals(id));
        return eliminado ? "Cliente eliminado" : "No se encontró el cliente";
    }
}

