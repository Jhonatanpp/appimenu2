package com.example.bitebyte.model.viewmodel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bitebyte.R;
import com.example.bitebyte.model.Usuario;
import com.example.bitebyte.model.MenuGeneralRestaurante;
import com.example.bitebyte.model.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText correoEditText, contrasenaEditText, nombreEditText, rolEditText;
    private Button loginButton;

    private FirebaseAuth auth;
    private DatabaseReference refUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correoEditText = findViewById(R.id.editTextCorreo);
        contrasenaEditText = findViewById(R.id.editTextContrasena);
        nombreEditText = findViewById(R.id.editTextNombre);
        rolEditText = findViewById(R.id.spinnerRol);
        loginButton = findViewById(R.id.buttonLogin);

        auth = FirebaseAuth.getInstance();
        refUsuarios = FirebaseDatabase.getInstance().getReference("usuarios");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = correoEditText.getText().toString().trim();
                String contrasena = contrasenaEditText.getText().toString().trim();
                String nombre = nombreEditText.getText().toString().trim();
                String rol = rolEditText.getText().toString().trim().toLowerCase();

                if (correo.isEmpty() || contrasena.isEmpty() || nombre.isEmpty() || rol.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(correo, contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            abrirSiguienteActividad(rol);
                        } else {
                            registrarUsuario(correo, contrasena, nombre, rol);
                        }
                    }
                });
            }
        });
    }

    private void registrarUsuario(String correo, String contrasena, String nombre, String rol) {
        auth.createUserWithEmailAndPassword(correo, contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {
                        Usuario nuevoUsuario = new Usuario(user.getUid(), nombre, rol);
                        refUsuarios.child(user.getUid()).setValue(nuevoUsuario);
                        abrirSiguienteActividad(rol);
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Error al registrar: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void abrirSiguienteActividad(String rol) {
        Intent intent;
        switch (rol) {
            case "cocina":
                intent = new Intent(LoginActivity.this, MainActivity.class); // Reemplaza si tienes CocinaActivity
                break;
            case "mesero":
                intent = new Intent(LoginActivity.this, MainActivity.class); // Reemplaza si tienes MesasActivity
                break;
            case "cliente":
            default:
                intent = new Intent(LoginActivity.this, MenuGeneralRestaurante.class);
                break;
        }
        startActivity(intent);
        finish();
    }
}
