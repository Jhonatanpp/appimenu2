package com.example.bitebyte.model;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.List;

public class MisPlatosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MisPlatosAdapter adapter;
    private List<Plato> listaPlatos = new ArrayList<>();
    private DatabaseReference refPlatos;
    private String idCocinero;
    private TextView textCocineroId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_platos);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarMisPlatos);
        setSupportActionBar(toolbar);

        // Vistas
        textCocineroId = findViewById(R.id.textCocineroId);
        recyclerView    = findViewById(R.id.recyclerMisPlatos);

        // UID
        idCocinero = FirebaseAuth.getInstance()
                .getCurrentUser()
                .getUid();
        textCocineroId.setText("Tu ID es: " + idCocinero);

        // Recycler
        adapter = new MisPlatosAdapter(this, listaPlatos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Firebase
        refPlatos = FirebaseDatabase.getInstance()
                .getReference("platos");
        cargarPlatos();
    }

    private void cargarPlatos() {
        refPlatos.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot snap) {
                listaPlatos.clear();
                for (DataSnapshot ds : snap.getChildren()) {
                    Plato p = ds.getValue(Plato.class);
                    if (p != null && idCocinero.equals(p.getIdCocinero())) {
                        listaPlatos.add(p);
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override public void onCancelled(@NonNull DatabaseError err) {
                Toast.makeText(MisPlatosActivity.this,
                        "Error al cargar platos", Toast.LENGTH_SHORT).show();
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
        int id = item.getItemId();

        if (id == R.id.action_monitorear_ordenes) {
            startActivity(new Intent(this, MonitorearMesasActivity.class));
            return true;
        }
        else if (id == R.id.action_agregar_plato) {
            startActivity(new Intent(this, RegistrarPlato.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
