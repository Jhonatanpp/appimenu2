package com.example.bitebyte.model;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bitebyte.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private EditText ruNombre, ruCorreo, ruContrasena;
    private Spinner ruRol;
    private Button ruRegistrar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_usuario);

        ruNombre = findViewById(R.id.ruNombre);
        ruCorreo = findViewById(R.id.ruCorreo);
        ruContrasena = findViewById(R.id.ruContrasena);
        ruRol = findViewById(R.id.ruRol);
        ruRegistrar = findViewById(R.id.ruRegistrar);

        firebaseAuth = FirebaseAuth.getInstance();

        // Cargar roles al spinner
        List<String> roles = Arrays.asList("comensal", "mesero", "cocina");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                roles
        );
        ruRol.setAdapter(adapter);

        ruRegistrar.setOnClickListener(v -> registrarUsuario());
    }

    private void registrarUsuario() {
        String nombre = ruNombre.getText().toString().trim();
        String correo = ruCorreo.getText().toString().trim();
        String contrasena = ruContrasena.getText().toString().trim();
        String rol = ruRol.getSelectedItem().toString();

        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            Usuario nuevoUsuario = new Usuario(user.getUid(), nombre, correo, 0, rol);

                            FirebaseDatabase.getInstance().getReference("usuarios")
                                    .child(user.getUid())
                                    .setValue(nuevoUsuario)
                                    .addOnSuccessListener(unused -> {
                                        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                                        if (rol.equalsIgnoreCase("comensal")) {
                                            startActivity(new Intent(this, MenuClienteActivity.class));
                                        } else {
                                            startActivity(new Intent(this, HomeActivity.class));
                                        }
                                        finish();
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(this, "Error al guardar usuario: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    } else {
                        Toast.makeText(this, "Error al registrar: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
