package com.example.bitebyte.model.viewmodel;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitebyte.R;
import com.example.bitebyte.model.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText correoEditText, contrasenaEditText;
    private Button loginButton;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correoEditText = findViewById(R.id.editTextCorreo);
        contrasenaEditText = findViewById(R.id.editTextContrasena);
        loginButton = findViewById(R.id.buttonLogin);

        firebaseAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(v -> {
            String correo = correoEditText.getText().toString().trim();
            String contrasena = contrasenaEditText.getText().toString().trim();

            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseAuth.signInWithEmailAndPassword(correo, contrasena)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Inicio exitoso", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}