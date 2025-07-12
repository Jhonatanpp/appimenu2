package com.example.bitebyte.model;


import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bitebyte.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class PedidosEntrantesActivity extends AppCompatActivity {

    private ListView listViewPedidos;
    private DatabaseReference refOrdenes;
    private ArrayList<Orden> listaOrdenes = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_pedidos_entrantes);

        listViewPedidos = findViewById(R.id.listViewPedidos);
        refOrdenes = FirebaseDatabase.getInstance().getReference("ordenes");

        cargarPedidos();

        listViewPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Orden ordenSeleccionada = listaOrdenes.get(position);
                marcarComoListo(ordenSeleccionada.getIdOrden());
            }
        });
    }

    private void cargarPedidos() {
        refOrdenes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> listaVisual = new ArrayList<>();
                listaOrdenes.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Orden orden = ds.getValue(Orden.class);
                    if (orden != null && (orden.getEstado() == EstadoOrden.PENDIENTE || orden.getEstado() == EstadoOrden.EN_PREPARACION)) {
                        StringBuilder detalle = new StringBuilder();
                        detalle.append("Pedido de usuario: ").append(orden.getIdUsuario()).append("\n");
                        for (Plato p : orden.getPlatos()) {
                            detalle.append("- ").append(p.getNombre());
                            if (p.getComentario() != null && !p.getComentario().isEmpty()) {
                                detalle.append(" (" + p.getComentario() + ")");
                            }
                            detalle.append("\n");
                        }
                        detalle.append("Estado: ").append(orden.getEstado().name());

                        listaOrdenes.add(orden);
                        listaVisual.add(detalle.toString());
                    }
                }

                adapter = new ArrayAdapter<>(PedidosEntrantesActivity.this, android.R.layout.simple_list_item_1, listaVisual);
                listViewPedidos.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PedidosEntrantesActivity.this, "Error al cargar pedidos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void marcarComoListo(String idOrden) {
        HashMap<String, Object> actualizacion = new HashMap<>();
        actualizacion.put("estado", EstadoOrden.LISTO_PARA_ENTREGAR);

        refOrdenes.child(idOrden).updateChildren(actualizacion).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Pedido marcado como listo", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al actualizar estado", Toast.LENGTH_SHORT).show();
            }
        });
    }
}