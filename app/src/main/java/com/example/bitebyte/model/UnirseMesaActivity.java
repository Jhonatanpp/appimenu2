package com.example.bitebyte.model;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitebyte.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

public class UnirseMesaActivity extends AppCompatActivity {

    private EditText editCodigoMesa;
    private Button btnUnirse;
    private DatabaseReference refMesas;
    private DatabaseReference refUsuarios;
    private FirebaseUser usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unirse_mesa);

        editCodigoMesa = findViewById(R.id.editCodigoMesa);
        btnUnirse = findViewById(R.id.btnUnirse);

        refMesas = FirebaseDatabase.getInstance().getReference("mesas");
        refUsuarios = FirebaseDatabase.getInstance().getReference("usuarios");
        usuarioActual = FirebaseAuth.getInstance().getCurrentUser();

        btnUnirse.setOnClickListener(v -> {
            String codigoMesa = editCodigoMesa.getText().toString().trim();

            if (codigoMesa.isEmpty()) {
                Toast.makeText(this, "Ingrese el código de la mesa", Toast.LENGTH_SHORT).show();
                return;
            }

            refMesas.child(codigoMesa).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        refUsuarios.child(usuarioActual.getUid()).child("mesaAsignada").setValue(codigoMesa);
                        Toast.makeText(UnirseMesaActivity.this, "Te has unido a la mesa correctamente", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(UnirseMesaActivity.this, "Mesa no encontrada", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(UnirseMesaActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}