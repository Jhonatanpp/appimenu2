package com.example.bitebyte.model;

import java.util.ArrayList;
import java.util.List;

public class ReportePedidos {
    private List<CarritoDeCompra> pedidos;

    public ReportePedidos() {
        this.pedidos = new ArrayList<>();
    }

    public void agregarPedido(CarritoDeCompra carrito) {
        pedidos.add(carrito);
    }

    public double calcularTotalGeneral() {
        double total = 0;
        for (CarritoDeCompra pedido : pedidos) {
            total += pedido.calcularTotal();
        }
        return total;
    }

    public double calcularPromedioPorPedido() {
        if (pedidos.isEmpty()) return 0;
        return calcularTotalGeneral() / pedidos.size();
    }

    public int cantidadDePedidos() {
        return pedidos.size();
    }

    public void imprimirReporte() {
        System.out.println("==== Reporte de Pedidos ====");
        for (CarritoDeCompra pedido : pedidos) {
            System.out.println(pedido);
            System.out.println("----------------------------");
        }
        System.out.println("Total de pedidos: " + cantidadDePedidos());
        System.out.printf("Total general: $%.2f\n", calcularTotalGeneral());
        System.out.printf("Promedio por pedido: $%.2f\n", calcularPromedioPorPedido());
        System.out.println("============================");
    }

    public List<CarritoDeCompra> getPedidos() {
        return pedidos;
    }
}

