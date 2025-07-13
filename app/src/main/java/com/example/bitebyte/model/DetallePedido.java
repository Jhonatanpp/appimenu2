package com.example.bitebyte.model;

public class DetallePedido {
    private Plato plato;
    private String comentarios;

    public DetallePedido(Plato plato, String comentarios) {
        this.plato = plato;
        this.comentarios = comentarios;
    }

    public Plato getPlato() {
        return plato;
    }

    public String getComentarios() {
        return comentarios;
    }

    @Override
    public String toString() {
        return plato.getNombre() + " - " + comentarios + " ($" + plato.getPrecio() + ")";
    }
}
