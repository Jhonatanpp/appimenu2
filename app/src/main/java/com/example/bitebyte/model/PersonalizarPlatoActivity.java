package com.example.bitebyte.model;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitebyte.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class PersonalizarPlatoActivity extends AppCompatActivity {

    private TextView nombrePlatoTextView, descripcionPlatoTextView, precioPlatoTextView;
    private EditText comentarioEditText;
    private Button botonConfirmar;

    private Plato platoSeleccionado;
    private DatabaseReference refOrdenes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalizar_plato);

        nombrePlatoTextView = findViewById(R.id.tvNombrePersonalizado);
        descripcionPlatoTextView = findViewById(R.id.tvDescripcionPersonalizada);
        precioPlatoTextView = findViewById(R.id.tvPrecioPersonalizado);
        comentarioEditText = findViewById(R.id.editTextComentario);
        botonConfirmar = findViewById(R.id.btnConfirmarPedido);

        refOrdenes = FirebaseDatabase.getInstance().getReference("ordenes");

        platoSeleccionado = (Plato) getIntent().getSerializableExtra("plato");

        if (platoSeleccionado != null) {
            nombrePlatoTextView.setText(platoSeleccionado.getNombre());
            descripcionPlatoTextView.setText(platoSeleccionado.getDescripcion());
            precioPlatoTextView.setText("$" + platoSeleccionado.getPrecio());
        }

        botonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comentario = comentarioEditText.getText().toString().trim();
                if (platoSeleccionado != null) {
                    platoSeleccionado.setComentario(comentario);
                    crearOrdenConPlato(platoSeleccionado);
                }
            }
        });
    }

    private void crearOrdenConPlato(Plato plato) {
        String idOrden = UUID.randomUUID().toString();
        ArrayList<Plato> platos = new ArrayList<>();
        platos.add(plato);

        Orden nuevaOrden = new Orden(idOrden, "usuario1", platos, EstadoOrden.PENDIENTE);
        refOrdenes.child(idOrden).setValue(nuevaOrden)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Pedido enviado", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error al enviar pedido", Toast.LENGTH_SHORT).show()
                );
    }
}
