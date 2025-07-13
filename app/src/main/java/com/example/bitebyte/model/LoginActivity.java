package com.example.bitebyte.model;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bitebyte.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextContrasena;
    private Button buttonLogin;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference refUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // IDs corregidos según tu layout
        editTextUsername = findViewById(R.id.editTextCorreo);
        editTextContrasena = findViewById(R.id.editTextContrasena);
        buttonLogin = findViewById(R.id.buttonLogin);

        firebaseAuth = FirebaseAuth.getInstance();
        refUsuarios = FirebaseDatabase.getInstance().getReference("usuarios");

        buttonLogin.setOnClickListener(v -> {
            String correo = editTextUsername.getText().toString().trim();
            String contrasena = editTextContrasena.getText().toString().trim();

            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseAuth.signInWithEmailAndPassword(correo, contrasena)
                    .addOnSuccessListener(authResult -> {
                        FirebaseUser usuarioActual = firebaseAuth.getCurrentUser();
                        if (usuarioActual != null) {
                            redirigirPorRol(usuarioActual.getUid());
                        }
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Error de autenticación: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        });
    }

    private void redirigirPorRol(String uid) {
        refUsuarios.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);
                if (usuario != null) {
                    String rol = usuario.getRol();
                    if ("comensal".equalsIgnoreCase(rol)) {
                        Intent intent = new Intent(LoginActivity.this, MenuClienteActivity.class);
                        intent.putExtra("idUsuario", uid);
                        startActivity(intent);
                        finish();
                    } else if ("mesero".equalsIgnoreCase(rol)) {
                        Intent intent = new Intent(LoginActivity.this, MenuMeseroActivity.class);
                        intent.putExtra("idUsuario", uid);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Rol no reconocido: " + rol, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, "Error al consultar datos del usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
