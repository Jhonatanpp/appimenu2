package com.example.bitebyte.model;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bitebyte.R;

public class MenuClienteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente);

        String idMesa = getIntent().getStringExtra("idMesa");
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textMesa = findViewById(R.id.btnCrearMesa);
        textMesa.setText("Mesa: " + idMesa);
    }
}