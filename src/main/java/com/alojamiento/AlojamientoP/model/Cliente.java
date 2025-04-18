package com.alojamiento.AlojamientoP.model;

public class Cliente {
    private Long id;
    private Persona persona;

    public Cliente() {
    }

    public Cliente(Long id, Persona persona) {
        this.id = id;
        this.persona = persona;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
