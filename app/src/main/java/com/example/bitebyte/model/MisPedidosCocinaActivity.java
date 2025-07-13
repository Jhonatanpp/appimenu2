package com.example.bitebyte.model;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bitebyte.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class MisPedidosCocinaActivity extends AppCompatActivity {

    private TextView textViewIdCocinero;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private MisPedidosCocinaAdapter adapter;
    private ArrayList<Orden> listaPedidos;
    private DatabaseReference refOrdenes;
    private String idCocinero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_pedidos_cocina);

        textViewIdCocinero = findViewById(R.id.textViewIdCocinero);
        progressBar = findViewById(R.id.progressPedidosCocina);
        recyclerView = findViewById(R.id.recyclerPedidosCocina);

        idCocinero = FirebaseAuth.getInstance().getCurrentUser().getUid();
        textViewIdCocinero.setText("ID: " + idCocinero);

        listaPedidos = new ArrayList<>();
        adapter = new MisPedidosCocinaAdapter(listaPedidos, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        refOrdenes = FirebaseDatabase.getInstance().getReference("ordenes");
        cargarPedidos();
    }

    private void cargarPedidos() {
        progressBar.setVisibility(View.VISIBLE);

        refOrdenes.orderByChild("idUsuario").equalTo(idCocinero)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listaPedidos.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Orden orden = ds.getValue(Orden.class);
                            if (orden != null) {
                                listaPedidos.add(orden);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}
