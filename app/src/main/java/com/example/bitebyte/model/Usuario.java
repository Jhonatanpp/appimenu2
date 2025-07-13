package com.example.bitebyte.model;

public class Usuario {

    private String uid;
    private String nombre;
    private String correo;
    private int edad;
    private String rol;
    private String codigoMesa; // <- AÑADIDO

    public Usuario() {
        // Constructor vacío requerido por Firebase
    }

    public Usuario(String uid, String nombre, String correo, int edad, String rol) {
        this.uid = uid;
        this.nombre = nombre;
        this.correo = correo;
        this.edad = edad;
        this.rol = rol;
        this.codigoMesa = ""; // inicializar vacío
    }

    // Getters y Setters
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getCodigoMesa() {
        return codigoMesa;
    }

    public void setCodigoMesa(String codigoMesa) {
        this.codigoMesa = codigoMesa;
    }
    public String getId() {
        return uid;
    }
}