package com.example.bitebyte.model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bitebyte.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MesasActivity extends AppCompatActivity {

    private ListView listViewMesas;
    private List<String> listaMesas = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private DatabaseReference refMesas;
    private String idMesero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);

        listViewMesas = findViewById(R.id.listViewMesas);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaMesas);
        listViewMesas.setAdapter(adapter);

        idMesero = FirebaseAuth.getInstance().getCurrentUser().getUid();
        refMesas = FirebaseDatabase.getInstance().getReference("mesas");

        cargarMesas();

        listViewMesas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idMesa = listaMesas.get(position);
                unirseAMesa(idMesa);
            }
        });
    }

    private void cargarMesas() {
        refMesas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaMesas.clear();
                for (DataSnapshot mesaSnap : snapshot.getChildren()) {
                    String id = mesaSnap.getKey();
                    String mesero = mesaSnap.child("mesero").getValue(String.class);
                    if (mesero == null || mesero.isEmpty()) {
                        listaMesas.add(id);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MesasActivity.this, "Error al cargar mesas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void unirseAMesa(String idMesa) {
        Map<String, Object> update = new HashMap<>();
        update.put("mesero", idMesero);
        refMesas.child(idMesa).updateChildren(update);
        Toast.makeText(this, "Unido a mesa " + idMesa, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MesasActivity.this, MenuClienteActivity.class);
        intent.putExtra("idMesa", idMesa);
        startActivity(intent);
        finish();
    }
}
