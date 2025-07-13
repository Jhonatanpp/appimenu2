package com.example.bitebyte.model;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bitebyte.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class FragmentCrearMesa extends Fragment {

    private EditText editTextIdMesa, editTextIdCocinero;
    private Button botonCrearMesa;
    private DatabaseReference refMesas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_mesa, container, false);

        editTextIdMesa = view.findViewById(R.id.editTextIdMesa);
        editTextIdCocinero = view.findViewById(R.id.editTextIdCocinero);
        botonCrearMesa = view.findViewById(R.id.botonCrearMesa);

        refMesas = FirebaseDatabase.getInstance().getReference("mesas");

        botonCrearMesa.setOnClickListener(v -> crearMesa());

        return view;
    }

    private void crearMesa() {
        String idMesa = editTextIdMesa.getText().toString().trim();
        String idCocinero = editTextIdCocinero.getText().toString().trim();

        if (TextUtils.isEmpty(idMesa) || TextUtils.isEmpty(idCocinero)) {
            Toast.makeText(getContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, Object> datosMesa = new HashMap<>();
        datosMesa.put("idMesa", idMesa);
        datosMesa.put("idCocinero", idCocinero);
        datosMesa.put("activa", true);

        refMesas.child(idMesa).setValue(datosMesa).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Mesa creada correctamente", Toast.LENGTH_SHORT).show();
                editTextIdMesa.setText("");
                editTextIdCocinero.setText("");
            } else {
                Toast.makeText(getContext(), "Error al crear mesa", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
