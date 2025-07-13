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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentSubirPlato extends Fragment {

    private EditText editTextNombre, editTextDescripcion, editTextPrecio, editTextCategoria;
    private Button botonSubir;
    private DatabaseReference refPlatos;
    private FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subir_plato, container, false);

        editTextNombre = view.findViewById(R.id.editTextNombrePlato);
        editTextDescripcion = view.findViewById(R.id.editTextDescripcionPlato);
        editTextPrecio = view.findViewById(R.id.editTextPrecioPlato);
        editTextCategoria = view.findViewById(R.id.editTextCategoriaPlato);
        botonSubir = view.findViewById(R.id.botonSubirPlato);

        auth = FirebaseAuth.getInstance();
        refPlatos = FirebaseDatabase.getInstance().getReference("platos");

        botonSubir.setOnClickListener(v -> subirPlato());

        return view;
    }

    private void subirPlato() {
        String nombre = editTextNombre.getText().toString().trim();
        String descripcion = editTextDescripcion.getText().toString().trim();
        String precioStr = editTextPrecio.getText().toString().trim();
        String categoria = editTextCategoria.getText().toString().trim();
        String idCocinero = auth.getCurrentUser().getUid();

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(descripcion) || TextUtils.isEmpty(precioStr) || TextUtils.isEmpty(categoria)) {
            Toast.makeText(getContext(), "Por favor llena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(precioStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Precio inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        String idPlato = refPlatos.push().getKey();
        Plato plato = new Plato(idPlato, nombre, descripcion, precio, categoria, idCocinero);

        refPlatos.child(idPlato).setValue(plato).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Plato subido correctamente", Toast.LENGTH_SHORT).show();
                editTextNombre.setText("");
                editTextDescripcion.setText("");
                editTextPrecio.setText("");
                editTextCategoria.setText("");
            } else {
                Toast.makeText(getContext(), "Error al subir plato", Toast.LENGTH_SHORT).show();
            }
        });
    }
}