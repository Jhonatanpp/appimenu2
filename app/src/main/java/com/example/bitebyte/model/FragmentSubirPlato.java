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

public class FragmentSubirPlato extends Fragment {

    private EditText editTextNombrePlato, editTextDescripcionPlato, editTextPrecioPlato, editTextComentarioPlato;
    private Button botonSubirPlato;

    private DatabaseReference refPlatos;

    public FragmentSubirPlato() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subir_plato, container, false);

        editTextNombrePlato = view.findViewById(R.id.editTextNombrePlato);
        editTextDescripcionPlato = view.findViewById(R.id.editTextDescripcionPlato);
        editTextPrecioPlato = view.findViewById(R.id.editTextPrecioPlato);
        editTextComentarioPlato = view.findViewById(R.id.editTextComentarioPlato);
        botonSubirPlato = view.findViewById(R.id.botonSubirPlato);

        refPlatos = FirebaseDatabase.getInstance().getReference("platos");

        botonSubirPlato.setOnClickListener(v -> subirPlato());

        return view;
    }

    private void subirPlato() {
        String nombre = editTextNombrePlato.getText().toString().trim();
        String descripcion = editTextDescripcionPlato.getText().toString().trim();
        String precioStr = editTextPrecioPlato.getText().toString().trim();
        String comentario = editTextComentarioPlato.getText().toString().trim();

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(descripcion) || TextUtils.isEmpty(precioStr)) {
            Toast.makeText(getContext(), "Por favor completa todos los campos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(precioStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Precio inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        Plato nuevoPlato = new Plato(nombre, descripcion, precio, comentario);
        String id = refPlatos.push().getKey();

        if (id != null) {
            refPlatos.child(id).setValue(nuevoPlato);
            Toast.makeText(getContext(), "Plato subido correctamente", Toast.LENGTH_SHORT).show();
            editTextNombrePlato.setText("");
            editTextDescripcionPlato.setText("");
            editTextPrecioPlato.setText("");
            editTextComentarioPlato.setText("");
        } else {
            Toast.makeText(getContext(), "Error al generar ID para el plato", Toast.LENGTH_SHORT).show();
        }
    }
}
