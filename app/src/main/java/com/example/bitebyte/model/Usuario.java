package com.example.bitebyte.model;


public class Usuario {
    private String id;
    private String nombre;
    private String correo;
    private String contrasena;
    private String rol;

    // Constructor vacío requerido por Firebase
    public Usuario() {
    }

    // Constructor completo
    public Usuario(String id, String nombre, String correo, String contrasena, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}