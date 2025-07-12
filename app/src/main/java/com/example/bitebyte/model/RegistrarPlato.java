package com.example.bitebyte.model;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitebyte.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarPlato extends AppCompatActivity {

    private EditText inputNombre, inputDescripcion, inputPrecio;
    private Button btnRegistrar;
    private DatabaseReference refPlatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_plato);

        inputNombre = findViewById(R.id.rpinputNombre);
        inputDescripcion = findViewById(R.id.rpinputDescripcion);
        inputPrecio = findViewById(R.id.rpinputPrecio);
        btnRegistrar = findViewById(R.id.rpbtnRegistrarPlato);

        refPlatos = FirebaseDatabase.getInstance().getReference("platos");

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = inputNombre.getText().toString().trim();
                String descripcion = inputDescripcion.getText().toString().trim();
                String precioStr = inputPrecio.getText().toString().trim();

                if (nombre.isEmpty() || descripcion.isEmpty() || precioStr.isEmpty()) {
                    Toast.makeText(RegistrarPlato.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
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
                if (idPlato == null) {
                    Toast.makeText(RegistrarPlato.this, "Error generando ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                Plato nuevoPlato = new Plato(nombre, descripcion, precio, "");
                refPlatos.child(idPlato).setValue(nuevoPlato);

                Toast.makeText(RegistrarPlato.this, "Plato registrado", Toast.LENGTH_SHORT).show();

                inputNombre.setText("");
                inputDescripcion.setText("");
                inputPrecio.setText("");
            }
        });
    }
}