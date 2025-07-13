package com.example.bitebyte.model;

public class Plato {
    private String idPlato;
    private String nombre;
    private String descripcion;
    private double precio;
    private String categoria;
    private String idCocinero;
    private String comentario;

    public Plato() {
        // Constructor vacío requerido por Firebase
    }

    public Plato(String idPlato, String nombre, String descripcion, double precio, String categoria, String idCocinero) {
        this.idPlato = idPlato;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.idCocinero = idCocinero;
        this.comentario = ""; // por defecto vacío
    }

    public Plato(String idPlato, String nombre, String descripcion, double precio, String categoria, String idCocinero, String comentario) {
        this.idPlato = idPlato;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.idCocinero = idCocinero;
        this.comentario = comentario;
    }

    // Getters y setters

    public String getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(String idPlato) {
        this.idPlato = idPlato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getIdCocinero() {
        return idCocinero;
    }

    public void setIdCocinero(String idCocinero) {
        this.idCocinero = idCocinero;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    private String imagenUrl;

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

}
