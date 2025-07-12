package com.example.bitebyte.model;

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
        TextView textMesa = findViewById(R.id.tvMesaAsignada);
        textMesa.setText("Mesa: " + idMesa);
    }
}