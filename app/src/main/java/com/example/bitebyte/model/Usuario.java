package com.example.bitebyte.model;

import java.io.Serializable;


public class Usuario {
    private String id;
    private String nombre;
    private String rol;
    private String clave;

    public Usuario() {} // Necesario para Firebase

    public Usuario(String id, String nombre, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
}