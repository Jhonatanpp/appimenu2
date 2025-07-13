package com.example.bitebyte.model;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.bitebyte.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MeseroMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_mesero);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationMesero);
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            if (item.getItemId() == R.id.crear_mesa) {
                fragment = new FragmentCrearMesa();
            } else if (item.getItemId() == R.id.perfil_mesero) {
                fragment = new FragmentPerfilMesero();
            }

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameContainerMesero, fragment)
                        .commit();
                return true;
            }
            return false;
        });

        // Mostrar pantalla inicial
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameContainerMesero, new FragmentCrearMesa())
                .commit();
    }
}
