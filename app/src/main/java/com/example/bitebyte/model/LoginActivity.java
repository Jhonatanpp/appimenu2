package com.example.bitebyte.model;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitebyte.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextCorreo, editTextContrasena, editTextNombre, editTextRol;
    private Button buttonLogin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Enlazar vistas
        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextContrasena = findViewById(R.id.editTextContrasena);
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextRol = findViewById(R.id.editTextRol);
        buttonLogin = findViewById(R.id.buttonLogin);

        // Instancia de Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(v -> {
            String correo = editTextCorreo.getText().toString().trim();
            String contrasena = editTextContrasena.getText().toString().trim();
            String nombre = editTextNombre.getText().toString().trim();
            String rol = editTextRol.getText().toString().trim().toLowerCase();

            if (correo.isEmpty() || contrasena.isEmpty() || nombre.isEmpty() || rol.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Intentar iniciar sesión
            firebaseAuth.signInWithEmailAndPassword(correo, contrasena)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            redirigirPorRol(rol);
                        } else {
                            // Si no existe, lo registramos
                            firebaseAuth.createUserWithEmailAndPassword(correo, contrasena)
                                    .addOnCompleteListener(task2 -> {
                                        if (task2.isSuccessful()) {
                                            Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                                            redirigirPorRol(rol);
                                        } else {
                                            Toast.makeText(this, "Error: " + task2.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    });
        });
    }

    private void redirigirPorRol(String rol) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) return;

        Intent intent;

        switch (rol) {
            case "cliente":
                intent = new Intent(this, MenuClienteActivity.class);
                break;
            case "mesero":
                intent = new Intent(this, MesasActivity.class);
                break;
            case "cocina":
                intent = new Intent(this, PedidosEntrantesActivity.class);
                break;
            default:
                Toast.makeText(this, "Rol no válido. Usa cliente, mesero o cocina.", Toast.LENGTH_LONG).show();
                return;
        }

        startActivity(intent);
        finish();
    }
}