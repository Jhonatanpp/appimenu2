package com.example.bitebyte.model;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bitebyte.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class MenuGeneralRestaurante extends AppCompatActivity {

    private RecyclerView recyclerViewMesas, recyclerViewPlatos;
    private MesaAdapter mesaAdapter;
    private PlatoAdapter platoAdapter;
    private ArrayList<Mesa> listaMesas = new ArrayList<>();
    private ArrayList<Plato> listaPlatos = new ArrayList<>();
    private DatabaseReference refMesas, refPlatos;
    private ProgressBar progressBar;
    private TextView tvIdCocinero, tvSinMesas;
    private String idCocinero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_general_restaurante);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewMesas = findViewById(R.id.recyclerViewMesas);
        recyclerViewPlatos = findViewById(R.id.recyclerViewPlatos);
        progressBar = findViewById(R.id.progressBarMenu);
        tvIdCocinero = findViewById(R.id.tvIdCocinero);
        tvSinMesas = findViewById(R.id.tvSinMesas);

        recyclerViewMesas.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPlatos.setLayoutManager(new LinearLayoutManager(this));

        mesaAdapter = new MesaAdapter(this, listaMesas);
        recyclerViewMesas.setAdapter(mesaAdapter);

        platoAdapter = new PlatoAdapter(this, listaPlatos);
        recyclerViewPlatos.setAdapter(platoAdapter);

        idCocinero = FirebaseAuth.getInstance().getCurrentUser().getUid();
        tvIdCocinero.setText("Tu ID es: " + idCocinero);

        refMesas = FirebaseDatabase.getInstance().getReference("mesas");
        refPlatos = FirebaseDatabase.getInstance().getReference("platos");

        cargarMesas();
        cargarPlatos();
    }

    private void cargarMesas() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        refMesas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaMesas.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Mesa mesa = ds.getValue(Mesa.class);
                    if (mesa != null && idCocinero.equals(mesa.getIdCocinero())) {
                        listaMesas.add(mesa);
                    }
                }
                mesaAdapter.notifyDataSetChanged();
                progressBar.setVisibility(ProgressBar.GONE);

                tvSinMesas.setVisibility(listaMesas.isEmpty() ? TextView.VISIBLE : TextView.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MenuGeneralRestaurante.this, "Error al cargar mesas", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(ProgressBar.GONE);
            }
        });
    }

    private void cargarPlatos() {
        refPlatos.orderByChild("idCocinero").equalTo(idCocinero)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listaPlatos.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Plato plato = ds.getValue(Plato.class);
                            if (plato != null) {
                                listaPlatos.add(plato);
                            }
                        }
                        platoAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MenuGeneralRestaurante.this, "Error al cargar platos", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mis_platos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_agregar_plato) {
            startActivity(new Intent(this, RegistrarPlato.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
