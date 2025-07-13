package com.example.bitebyte.model;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.bitebyte.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

public class MenuClienteActivity extends AppCompatActivity {

    private FirebaseUser usuarioActual;
    private DatabaseReference refUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationCliente);

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int id = item.getItemId();

            if (id == R.id.menu_cliente) {
                fragment = new FragmentMenuCliente();
            } else if (id == R.id.unirse_mesa) {
                fragment = new FragmentUnirseMesa();
            } else if (id == R.id.perfil_usuario) {
                fragment = new FragmentPerfilUsuario();
            }

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameContainerCliente, fragment)
                        .commit();
                return true;
            }
            return false;
        });

        usuarioActual = FirebaseAuth.getInstance().getCurrentUser();
        refUsuarios = FirebaseDatabase.getInstance().getReference("usuarios");

        verificarMesaAsignadaYMostrarFragmento();
    }

    private void verificarMesaAsignadaYMostrarFragmento() {
        if (usuarioActual == null) return;

        refUsuarios.child(usuarioActual.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mesa = snapshot.child("mesaAsignada").getValue(String.class);
                Fragment fragment;

                if (mesa != null && !mesa.isEmpty()) {
                    fragment = new FragmentMenuCliente();
                } else {
                    fragment = new FragmentUnirseMesa();
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameContainerCliente, fragment)
                        .commit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MenuClienteActivity.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
