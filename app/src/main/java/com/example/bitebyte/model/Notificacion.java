package com.example.bitebyte.model;

import java.util.Date;

public class Notificacion {
    private String titulo;
    private String mensaje;
    private Date fecha;
    private TipoNotificacion tipo;

    public enum TipoNotificacion {
        INFO,
        ALERTA,
        PEDIDO_LISTO,
        ERROR
    }

    public Notificacion(String titulo, String mensaje, TipoNotificacion tipo) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.fecha = new Date();
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public TipoNotificacion getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "[" + tipo + "] " + titulo + ": " + mensaje + " (" + fecha + ")";
    }
}
