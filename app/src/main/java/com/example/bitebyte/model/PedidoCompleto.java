package com.example.bitebyte.model;

import java.util.ArrayList;
import java.util.List;

public class PedidoCompleto {
    private Cliente cliente;
    private List<DetallePedido> detalles;
    private String estado;

    public PedidoCompleto(Cliente cliente) {
        this.cliente = cliente;
        this.detalles = new ArrayList<>();
        this.estado = "Pendiente";
    }

    public void agregarDetalle(DetallePedido detalle) {
        detalles.add(detalle);
    }

    public double calcularTotal() {
        double total = 0;
        for (DetallePedido dp : detalles) {
            total += dp.getPlato().getPrecio();
        }
        return total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Pedido de " + cliente.getNombre() + " - Estado: " + estado + " - Total: $" + String.format("%.2f", calcularTotal());
    }
}
