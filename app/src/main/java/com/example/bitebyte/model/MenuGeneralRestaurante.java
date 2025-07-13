package com.example.bitebyte.model;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bitebyte.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuGeneralRestaurante extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlatoAdapter adapter;
    private List<Plato> listaPlatos;
    private ProgressBar progressBar;

    private DatabaseReference refPlatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_general_restaurante);

        recyclerView = findViewById(R.id.recyclerViewMenu);
        progressBar = findViewById(R.id.progressBarMenu);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listaPlatos = new ArrayList<>();
        adapter = new PlatoAdapter(listaPlatos, this); // CORREGIDO
        recyclerView.setAdapter(adapter);

        refPlatos = FirebaseDatabase.getInstance().getReference("platos");

        cargarPlatosDesdeFirebase();
    }

    private void cargarPlatosDesdeFirebase() {
        progressBar.setVisibility(View.VISIBLE);

        refPlatos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaPlatos.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Plato plato = ds.getValue(Plato.class);
                    if (plato != null) {
                        listaPlatos.add(plato);
                    }
                }
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MenuGeneralRestaurante.this, "Error al cargar platos", Toast.LENGTH_SHORT).show();
                Log.e("MenuGeneral", error.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
