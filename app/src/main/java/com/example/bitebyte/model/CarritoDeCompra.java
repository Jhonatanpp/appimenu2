package com.example.bitebyte.model;

import java.util.ArrayList;
import java.util.List;

public class CarritoDeCompra {
    private List<Plato> items;
    private String nombreCliente;   // para asociar el pedido al cliente
    private String notasEspeciales; // comentarios o preferencias del cliente

    public CarritoDeCompra(String nombreCliente) {
        this.nombreCliente = nombreCliente;
        this.items = new ArrayList<>();
        this.notasEspeciales = "";
    }

    public void agregarPlato(Plato plato) {
        items.add(plato);
    }

    public void eliminarPlato(Plato plato) {
        items.remove(plato);
    }

    public double calcularTotal() {
        double total = 0;
        for (Plato plato : items) {
            total += plato.getPrecio();
        }
        return total;
    }

    public List<Plato> getItems() {
        return items;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNotasEspeciales(String notas) {
        this.notasEspeciales = notas;
    }

    public String getNotasEspeciales() {
        return notasEspeciales;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido de ").append(nombreCliente).append("\n");
        for (Plato p : items) {
            sb.append("- ").append(p.getNombre())
                    .append(": ").append(p.getDescripcion())
                    .append(" ($").append(String.format("%.2f", p.getPrecio())).append(")\n");
        }
        sb.append("Notas: ").append(notasEspeciales.isEmpty() ? "Ninguna" : notasEspeciales).append("\n");
        sb.append("Total: $").append(String.format("%.2f", calcularTotal()));
        return sb.toString();
    }
}
