package com.example.bitebyte.model;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bitebyte.R;
import com.example.bitebyte.model.EstadoOrden;
import com.example.bitebyte.model.HistorialAdapter;
import com.example.bitebyte.model.Orden;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistorialPedidosActivity extends AppCompatActivity {

    private RecyclerView recyclerHistorial;
    private HistorialAdapter adapter;
    private List<Orden> listaPedidos;
    private DatabaseReference refOrdenes;
    private String idUsuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pedidos);

        recyclerHistorial = findViewById(R.id.recyclerHistorial);
        recyclerHistorial.setLayoutManager(new LinearLayoutManager(this));

        listaPedidos = new ArrayList<>();
        idUsuarioActual = FirebaseAuth.getInstance().getCurrentUser().getUid();
        refOrdenes = FirebaseDatabase.getInstance().getReference("ordenes");

        adapter = new HistorialAdapter(this, listaPedidos, this::mostrarDialogoReseña);
        recyclerHistorial.setAdapter(adapter);
        recyclerHistorial.addItemDecoration(new SpacesItemDecoration(16, true));

        cargarPedidos();
    }

    private void cargarPedidos() {
        refOrdenes.orderByChild("idUsuario").equalTo(idUsuarioActual)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        listaPedidos.clear();
                        for (DataSnapshot ordenSnap : snapshot.getChildren()) {
                            Orden orden = ordenSnap.getValue(Orden.class);
                            if (orden != null) {
                                listaPedidos.add(orden);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(HistorialPedidosActivity.this, "Error al cargar pedidos", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void mostrarDialogoReseña(Orden orden) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Deja tu reseña");

        final EditText input = new EditText(this);
        input.setHint("Escribe tu comentario");
        builder.setView(input);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String texto = input.getText().toString().trim();
            if (!texto.isEmpty()) {
                refOrdenes.child(orden.getIdOrden()).child("reseña").setValue(texto);
                orden.setReseña(texto);
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
