package com.example.bitebyte.model;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitebyte.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private EditText editUsername, editPassword;
    private Button btnRegistrar;

    private DatabaseReference refUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_usuario);

        // Referencias visuales
        editUsername = findViewById(R.id.ruUsername);
        editPassword = findViewById(R.id.ruPassword);
        btnRegistrar = findViewById(R.id.ruRegistrar);

        // Referencia Firebase
        refUsuarios = FirebaseDatabase.getInstance().getReference("usuarios");

        btnRegistrar.setOnClickListener(v -> registrarUsuario());
    }

    private void registrarUsuario() {
        String nombre = editUsername.getText().toString().trim();
        String clave = editPassword.getText().toString().trim();

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(clave)) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crea ID único
        String idUsuario = refUsuarios.push().getKey();

        // Objeto de usuario (puedes personalizarlo)
        Usuario nuevo = new Usuario(idUsuario, nombre, "cliente"); // rol por defecto
        nuevo.setClave(clave);

        refUsuarios.child(idUsuario).setValue(nuevo)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                    finish(); // Vuelve a la pantalla anterior
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
                    Log.e("RegistroFirebase", "Error", e);
                });
    }
}