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
import androidx.fragment.app.Fragment;

import com.example.bitebyte.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

public class FragmentPerfilMesero extends Fragment {

    private EditText editNombre, editCorreo, editEdad;
    private Button btnGuardar;
    private FirebaseUser usuarioActual;
    private DatabaseReference refUsuarios;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil_mesero, container, false);

        editNombre = view.findViewById(R.id.editNombreMesero);
        editCorreo = view.findViewById(R.id.editCorreoMesero);
        editEdad = view.findViewById(R.id.editEdadMesero);
        btnGuardar = view.findViewById(R.id.btnGuardarPerfilMesero);

        usuarioActual = FirebaseAuth.getInstance().getCurrentUser();
        refUsuarios = FirebaseDatabase.getInstance().getReference("usuarios");

        cargarDatos();

        btnGuardar.setOnClickListener(v -> guardarCambios());

        return view;
    }

    private void cargarDatos() {
        if (usuarioActual == null) return;
        refUsuarios.child(usuarioActual.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario u = snapshot.getValue(Usuario.class);
                if (u != null) {
                    editNombre.setText(u.getNombre());
                    editCorreo.setText(u.getCorreo());
                    editEdad.setText(String.valueOf(u.getEdad()));
                }
            }
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void guardarCambios() {
        String nombre = editNombre.getText().toString().trim();
        String correo = editCorreo.getText().toString().trim();
        String edadTxt = editEdad.getText().toString().trim();

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(correo) || TextUtils.isEmpty(edadTxt)) {
            Toast.makeText(getContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int edad = Integer.parseInt(edadTxt);
        Usuario nuevo = new Usuario(usuarioActual.getUid(), nombre, correo, edad, "mesero");
        refUsuarios.child(usuarioActual.getUid()).setValue(nuevo)
                .addOnSuccessListener(unused -> Toast.makeText(getContext(), "Perfil actualizado", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Error al guardar", Toast.LENGTH_SHORT).show());
    }
}
