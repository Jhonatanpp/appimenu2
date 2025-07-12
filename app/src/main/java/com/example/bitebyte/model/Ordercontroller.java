package com.example.bitebyte.model;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Ordercontroller {

    private final Context context;
    private final GestionOrdenes gestionOrdenes;

    public Ordercontroller(Context context) {
        this.context = context;
        this.gestionOrdenes = new GestionOrdenes();
    }

    public void cargarPedidosParaCocina(ListView listView) {
        gestionOrdenes.getRefOrdenes().addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {
                ArrayList<String> lista = new ArrayList<>();
                for (com.google.firebase.database.DataSnapshot ds : snapshot.getChildren()) {
                    Orden orden = ds.getValue(Orden.class);
                    if (orden != null && (orden.getEstado() == EstadoOrden.PENDIENTE || orden.getEstado() == EstadoOrden.EN_PREPARACION)) {
                        String resumen = "Usuario: " + orden.getIdUsuario() + "\nPlatos: ";
                        for (Plato p : orden.getPlatosSeleccionados()) {
                            resumen += "\n- " + p.getNombre();
                        }
                        resumen += "\nEstado: " + orden.getEstado().name();
                        lista.add(resumen);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, lista);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError error) {
                Toast.makeText(context, "Error al cargar pedidos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void marcarOrdenComoLista(String idOrden) {
        Orden orden = gestionOrdenes.obtenerOrden(idOrden);
        if (orden != null) {
            gestionOrdenes.actualizarEstado(idOrden, EstadoOrden.COMPLETADA);
            Toast.makeText(context, "Pedido marcado como COMPLETADO", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Orden no encontrada", Toast.LENGTH_SHORT).show();
        }
    }

    public void cargarPedidosListosParaMesero(ListView listView) {
        gestionOrdenes.getRefOrdenes().addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {
                ArrayList<String> pedidosListos = new ArrayList<>();
                for (com.google.firebase.database.DataSnapshot ds : snapshot.getChildren()) {
                    Orden orden = ds.getValue(Orden.class);
                    if (orden != null && orden.getEstado() == EstadoOrden.COMPLETADA) {
                        String resumen = "Pedido listo de usuario: " + orden.getIdUsuario();
                        for (Plato p : orden.getPlatosSeleccionados()) {
                            resumen += "\n- " + p.getNombre();
                        }
                        pedidosListos.add(resumen);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, pedidosListos);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError error) {
                Toast.makeText(context, "Error al cargar pedidos listos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void marcarComoEntregado(String idOrden) {
        Orden orden = gestionOrdenes.obtenerOrden(idOrden);
        if (orden != null) {
            gestionOrdenes.actualizarEstado(idOrden, EstadoOrden.EN_PROCESO);
            Toast.makeText(context, "Pedido entregado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Orden no encontrada", Toast.LENGTH_SHORT).show();
        }
    }
}
