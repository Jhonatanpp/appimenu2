package com.example.bitebyte.model;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bitebyte.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

public class FragmentPerfilUsuario extends Fragment {

    private EditText editNombre, editCorreo;
    private Button btnGuardar;
    private DatabaseReference refUsuarios;
    private String idUsuario;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil_usuario, container, false);

        editNombre = view.findViewById(R.id.editNombrePerfil);
        editCorreo = view.findViewById(R.id.editCorreoPerfil);
        btnGuardar = view.findViewById(R.id.btnGuardarPerfil);

        idUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();
        refUsuarios = FirebaseDatabase.getInstance().getReference("usuarios");

        cargarDatosUsuario();

        btnGuardar.setOnClickListener(v -> guardarCambios());

        return view;
    }

    private void cargarDatosUsuario() {
        refUsuarios.child(idUsuario).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nombre = snapshot.child("nombre").getValue(String.class);
                String correo = snapshot.child("correo").getValue(String.class);

                editNombre.setText(nombre);
                editCorreo.setText(correo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error al cargar perfil", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarCambios() {
        String nuevoNombre = editNombre.getText().toString().trim();
        String nuevoCorreo = editCorreo.getText().toString().trim();

        if (TextUtils.isEmpty(nuevoNombre) || TextUtils.isEmpty(nuevoCorreo)) {
            Toast.makeText(getContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        refUsuarios.child(idUsuario).child("nombre").setValue(nuevoNombre);
        refUsuarios.child(idUsuario).child("correo").setValue(nuevoCorreo);

        Toast.makeText(getContext(), "Perfil actualizado", Toast.LENGTH_SHORT).show();
    }
}
