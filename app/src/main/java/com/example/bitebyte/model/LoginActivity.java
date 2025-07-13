package com.example.bitebyte.model;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bitebyte.R;
import com.example.bitebyte.model.MenuClienteActivity;
import com.example.bitebyte.model.MenuMeseroActivity;
import com.example.bitebyte.model.MisPlatosActivity;
import com.example.bitebyte.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextCorreo, editTextContrasena;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference refUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextContrasena = findViewById(R.id.editTextContrasena);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        firebaseAuth = FirebaseAuth.getInstance();
        refUsuarios = FirebaseDatabase.getInstance().getReference("usuarios");

        buttonLogin.setOnClickListener(v -> {
            String correo = editTextCorreo.getText().toString().trim();
            String contrasena = editTextContrasena.getText().toString().trim();

            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseAuth.signInWithEmailAndPassword(correo, contrasena)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user == null) return;

                            refUsuarios.child(user.getUid()).child("rol")
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String rol = snapshot.getValue(String.class);
                                            if (rol == null) {
                                                Toast.makeText(LoginActivity.this, "Usuario sin rol definido", Toast.LENGTH_SHORT).show();
                                                return;
                                            }

                                            switch (rol) {
                                                case "cliente":
                                                    startActivity(new Intent(LoginActivity.this, MenuClienteActivity.class));
                                                    break;
                                                case "mesero":
                                                    startActivity(new Intent(LoginActivity.this, MenuMeseroActivity.class));
                                                    break;
                                                case "cocinero":
                                                    startActivity(new Intent(LoginActivity.this, MisPlatosActivity.class));
                                                    break;
                                                default:
                                                    Toast.makeText(LoginActivity.this, "Rol desconocido", Toast.LENGTH_SHORT).show();
                                                    break;
                                            }
                                            finish();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(LoginActivity.this, "Error al obtener rol", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        } else {
                            Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
