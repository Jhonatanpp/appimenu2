package com.example.bitebyte.model;


import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitebyte.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private EditText editNombre, editCorreo, editContrasena;
    private Spinner spinnerRol;
    private Button btnRegistrar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference refUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_usuario);

        editNombre = findViewById(R.id.ruNombre);
        editCorreo = findViewById(R.id.ruCorreo);
        editContrasena = findViewById(R.id.ruContrasena);
        spinnerRol = findViewById(R.id.ruRol);
        btnRegistrar = findViewById(R.id.ruRegistrar);

        String[] roles = {"cliente", "mesero", "cocina"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roles);
        spinnerRol.setAdapter(adapter);

        firebaseAuth = FirebaseAuth.getInstance();
        refUsuarios = FirebaseDatabase.getInstance().getReference("usuarios");

        btnRegistrar.setOnClickListener(v -> {
            String nombre = editNombre.getText().toString().trim();
            String correo = editCorreo.getText().toString().trim();
            String contrasena = editContrasena.getText().toString().trim();
            String rol = spinnerRol.getSelectedItem().toString();

            if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(correo) || TextUtils.isEmpty(contrasena)) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseAuth.createUserWithEmailAndPassword(correo, contrasena)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String id = firebaseAuth.getCurrentUser().getUid(); // ID único del usuario
                            Usuario nuevoUsuario = new Usuario(id, nombre, correo, contrasena, rol);
                            refUsuarios.child(id).setValue(nuevoUsuario);
                            Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }
}