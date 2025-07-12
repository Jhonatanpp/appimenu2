package com.example.bitebyte.model;

import java.util.HashMap;
import com.google.firebase.database.*;

import android.util.Log;

public class GestionUsuarios {

    private final HashMap<String, Usuario> usuarios = new HashMap<>();
    private final DatabaseReference refUsuarios = FirebaseDatabase.getInstance().getReference("usuarios");

    public GestionUsuarios() {
        escucharCambiosUsuarios();
    }

    public void agregarUsuario(Usuario usuario) {
        refUsuarios.child(usuario.getId()).setValue(usuario);
    }

    public void borrarUsuario(String id) {
        refUsuarios.child(id).removeValue();
    }

    public Usuario obtenerUsuario(String id) {
        return usuarios.get(id);
    }

    private void escucharCambiosUsuarios() {
        refUsuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                usuarios.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Usuario usuario = ds.getValue(Usuario.class);
                    if (usuario != null) {
                        usuarios.put(usuario.getId(), usuario);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("GestionUsuarios", "Error al leer usuarios: " + error.getMessage());
            }
        });
    }
}
