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

public class MenuMeseroActivity extends AppCompatActivity {

    private FirebaseUser usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_mesero);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationMesero);
        bottomNav.setOnItemSelectedListener(navegacionMesero);

        usuarioActual = FirebaseAuth.getInstance().getCurrentUser();

        if (usuarioActual != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameContainerMesero, new FragmentPerfilMesero())
                    .commit();
        } else {
            Toast.makeText(this, "Error al cargar usuario", Toast.LENGTH_SHORT).show();
        }
    }

    private final BottomNavigationView.OnItemSelectedListener navegacionMesero =
            new BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;
                    int itemId = item.getItemId();

                    if (itemId == R.id.perfil_mesero) {
                        fragment = new FragmentPerfilMesero();
                    } else if (itemId == R.id.crear_mesa) {
                        fragment = new FragmentCrearMesa();
                    }

                    if (fragment != null) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameContainerMesero, fragment)
                                .commit();
                        return true;
                    }
                    return false;
                }
            };
}
