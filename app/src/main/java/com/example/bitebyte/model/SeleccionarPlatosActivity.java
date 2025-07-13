package com.example.bitebyte.model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bitebyte.R;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class SeleccionarPlatosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SeleccionarPlatosAdapter adapter;
    private ArrayList<Plato> listaPlatos = new ArrayList<>();
    private ProgressBar progressBar;
    private Button btnConfirmar;

    private String idMesa;
    private String idCocinero;
    private String idUsuario;

    private DatabaseReference refMesas;
    private DatabaseReference refPlatos;
    private DatabaseReference refOrdenes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_platos);

        recyclerView = findViewById(R.id.recyclerSeleccionarPlatos);
        progressBar = findViewById(R.id.progressSeleccionar);
        btnConfirmar = findViewById(R.id.btnConfirmarPedido);

        idUsuario = getIntent().getStringExtra("idUsuario");
        idMesa = getIntent().getStringExtra("idMesa");

        refMesas = FirebaseDatabase.getInstance().getReference("mesas");
        refPlatos = FirebaseDatabase.getInstance().getReference("platos");
        refOrdenes = FirebaseDatabase.getInstance().getReference("ordenes");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SeleccionarPlatosAdapter(this, listaPlatos);
        recyclerView.setAdapter(adapter);

        cargarIdCocinero();

        btnConfirmar.setOnClickListener(v -> registrarOrden());
    }

    private void cargarIdCocinero() {
        progressBar.setVisibility(View.VISIBLE);
        refMesas.child(idMesa).child("idCocinero").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                idCocinero = snapshot.getValue(String.class);
                if (idCocinero != null) {
                    cargarPlatosDelCocinero(idCocinero);
                } else {
                    Toast.makeText(SeleccionarPlatosActivity.this, "No se encontró cocinero para esta mesa", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SeleccionarPlatosActivity.this, "Error al obtener el cocinero", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void cargarPlatosDelCocinero(String idCocinero) {
        refPlatos.orderByChild("idCocinero").equalTo(idCocinero)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listaPlatos.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Plato plato = ds.getValue(Plato.class);
                            if (plato != null) listaPlatos.add(plato);
                        }
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(SeleccionarPlatosActivity.this, "Error al cargar platos", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void registrarOrden() {
        ArrayList<Plato> seleccionados = adapter.getPlatosSeleccionados();
        if (seleccionados.isEmpty()) {
            Toast.makeText(this, "Selecciona al menos un plato", Toast.LENGTH_SHORT).show();
            return;
        }

        String idOrden = refOrdenes.push().getKey();
        if (idOrden == null) return;

        Orden orden = new Orden(
                idOrden,
                idUsuario,
                idMesa,
                seleccionados,
                EstadoOrden.PENDIENTE,
                idCocinero
        );

        refOrdenes.child(idOrden).setValue(orden).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Pedido registrado", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al registrar pedido", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
