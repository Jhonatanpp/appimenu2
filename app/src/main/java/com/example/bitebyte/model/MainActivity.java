package com.example.bitebyte.model;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bitebyte.R;
import com.example.bitebyte.model.LoginActivity;
import com.example.bitebyte.model.RegistrarUsuarioActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnIngresar, btnCrearUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIngresar = findViewById(R.id.btnIngresar);
        btnCrearUsuario = findViewById(R.id.btnCrearUsuario);

        btnIngresar.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        btnCrearUsuario.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RegistrarUsuarioActivity.class);
            startActivity(intent);
        });
    }
}
