package com.alojamiento.AlojamientoP.model;

public class Persona {
    private Long id;
    private String nombre;
    private String ci;

    public Persona() {
    }

    public Persona(Long id, String nombre, String ci) {
        this.id = id;
        this.nombre = nombre;
        this.ci = ci;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }
}
