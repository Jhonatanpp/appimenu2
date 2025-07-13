package com.example.bitebyte.model;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.example.bitebyte.R;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference refUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseAuth = FirebaseAuth.getInstance();
        refUsuarios = FirebaseDatabase.getInstance().getReference("usuarios");

        new Handler().postDelayed(this::verificarUsuario, 2000);
    }

    private void verificarUsuario() {
        // Fuerza cierre de sesión para pruebas
        firebaseAuth.signOut();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            // No hay sesión activa: voy a MainActivity (login/registro)
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        // (El resto del código no llega a ejecutarse en modo prueba)
        String uid = user.getUid();
        refUsuarios.child(uid).child("rol")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String rol = snapshot.getValue(String.class);
                        Class<?> destino;
                        if ("cliente".equals(rol))       destino = MenuClienteActivity.class;
                        else if ("mesero".equals(rol))   destino = MenuMeseroActivity.class;
                        else if ("cocinero".equals(rol)) destino = MisPlatosActivity.class;
                        else                              destino = MainActivity.class;

                        startActivity(new Intent(SplashActivity.this, destino));
                        finish();
                    }
                    @Override public void onCancelled(@NonNull DatabaseError error) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }
}
