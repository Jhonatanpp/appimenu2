package com.example.bitebyte.model;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitebyte.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarPlato extends AppCompatActivity {

    private EditText inputNombre, inputDescripcion, inputPrecio, inputCategoria, inputComentario;
    private Button btnRegistrar;
    private DatabaseReference refPlatos;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_plato);

        inputNombre = findViewById(R.id.rpinputNombre);
        inputDescripcion = findViewById(R.id.rpinputDescripcion);
        inputPrecio = findViewById(R.id.rpinputPrecio);
        inputCategoria = findViewById(R.id.rpinputCategoria);
        inputComentario = findViewById(R.id.rpinputComentario);
        btnRegistrar = findViewById(R.id.rpbtnRegistrarPlato);

        auth = FirebaseAuth.getInstance();
        refPlatos = FirebaseDatabase.getInstance().getReference("platos");

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = inputNombre.getText().toString().trim();
                String descripcion = inputDescripcion.getText().toString().trim();
                String precioStr = inputPrecio.getText().toString().trim();
                String categoria = inputCategoria.getText().toString().trim();
                String comentario = inputComentario.getText().toString().trim();

                if (nombre.isEmpty() || descripcion.isEmpty() || precioStr.isEmpty() || categoria.isEmpty()) {
                    Toast.makeText(RegistrarPlato.this, "Completa todos los campos obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }

                double precio;
                try {
                    precio = Double.parseDouble(precioStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(RegistrarPlato.this, "Precio inválido", Toast.LENGTH_SHORT).show();
                    return;
                }

                String idPlato = refPlatos.push().getKey();
                String idCocinero = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "sin-id";

                if (idPlato == null) {
                    Toast.makeText(RegistrarPlato.this, "Error generando ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                Plato nuevoPlato = new Plato(idPlato, nombre, descripcion, precio, categoria, idCocinero, comentario);
                refPlatos.child(idPlato).setValue(nuevoPlato);

                Toast.makeText(RegistrarPlato.this, "Plato registrado", Toast.LENGTH_SHORT).show();

                inputNombre.setText("");
                inputDescripcion.setText("");
                inputPrecio.setText("");
                inputCategoria.setText("");
                inputComentario.setText("");
            }
        });
    }
}
