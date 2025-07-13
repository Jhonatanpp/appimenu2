package com.example.bitebyte.model;

import java.util.ArrayList;

public class Orden {
    private String idOrden;
    private String idUsuario;
    private String idCocinero;  // ⚠️ Este campo debe existir
    private String codigoMesa;
    private ArrayList<Plato> platos;
    private EstadoOrden estado;

    private String reseña;

    public Orden() {
        // Constructor vacío para Firebase
    }

    public Orden(String idOrden, String idUsuario, String codigoMesa, ArrayList<Plato> platos, EstadoOrden estado, String idCocinero) {
        this.idOrden = idOrden;
        this.idUsuario = idUsuario;
        this.codigoMesa = codigoMesa;
        this.platos = platos;
        this.estado = estado;
        this.idCocinero = idCocinero;
    }

    public String getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(String idOrden) {
        this.idOrden = idOrden;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCodigoMesa() {
        return codigoMesa;
    }

    public void setCodigoMesa(String codigoMesa) {
        this.codigoMesa = codigoMesa;
    }

    public ArrayList<Plato> getPlatos() {
        return platos;
    }

    public void setPlatos(ArrayList<Plato> platos) {
        this.platos = platos;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
    }
    public String getReseña() {
        return reseña;
    }

    public void setReseña(String reseña) {
        this.reseña = reseña;
    }

    public String getIdCocinero() {
        return idCocinero;
    }

    public void setIdCocinero(String idCocinero) {
        this.idCocinero = idCocinero;
    }
}
