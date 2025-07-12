package com.example.bitebyte.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Orden {

    private String id;  // Este campo debe existir en Firebase
    private String idUsuario;
    private ArrayList<Plato> platos;
    private EstadoOrden estado;
    private String reseña;

    // Constructor vacío obligatorio para Firebase
    public Orden() {
        this.platos = new ArrayList<>();
        this.estado = EstadoOrden.PENDIENTE;
    }

    // Constructor básico
    public Orden(String id, String idUsuario) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.platos = new ArrayList<>();
        this.estado = EstadoOrden.PENDIENTE;
    }

    // Constructor completo
    public Orden(String id, String idUsuario, ArrayList<Plato> platos, EstadoOrden estado) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.platos = platos != null ? platos : new ArrayList<>();
        this.estado = estado != null ? estado : EstadoOrden.PENDIENTE;
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public String getIdOrden() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public ArrayList<Plato> getPlatos() {
        if (platos == null) platos = new ArrayList<>();
        return platos;
    }

    public void setPlatos(ArrayList<Plato> platos) {
        this.platos = platos != null ? platos : new ArrayList<>();
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrden estado) {
        this.estado = estado != null ? estado : EstadoOrden.PENDIENTE;
    }

    public String getReseña() {
        return reseña;
    }

    public void setReseña(String reseña) {
        this.reseña = reseña;
    }

    public void agregarPlato(Plato plato) {
        if (this.platos == null) {
            this.platos = new ArrayList<>();
        }
        this.platos.add(plato);
    }

    public ArrayList<Plato> getPlatosSeleccionados() {
        return getPlatos();
    }
}