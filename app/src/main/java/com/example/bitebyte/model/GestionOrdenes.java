package com.example.bitebyte.model;

import android.util.Log;
import com.google.firebase.database.*;
import java.util.TreeMap;

public class GestionOrdenes {

    private TreeMap<String, Orden> ordenes = new TreeMap<>();
    private DatabaseReference refOrdenes = FirebaseDatabase.getInstance().getReference("ordenes");

    public GestionOrdenes() {
        escucharCambiosOrdenes();
    }

    public void agregarOrden(Orden orden) {
        refOrdenes.child(orden.getIdOrden()).setValue(orden);
    }

    public void borrarOrden(String idOrden) {
        refOrdenes.child(idOrden).removeValue();
        ordenes.remove(idOrden);
    }

    public Orden obtenerOrden(String idOrden) {
        return ordenes.get(idOrden);
    }

    public void actualizarEstado(String idOrden, EstadoOrden nuevoEstado) {
        refOrdenes.child(idOrden).child("estado").setValue(nuevoEstado);
    }

    private void escucharCambiosOrdenes() {
        refOrdenes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ordenes.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Orden orden = ds.getValue(Orden.class);
                    if (orden != null && orden.getIdOrden() != null) {
                        ordenes.put(orden.getIdOrden(), orden);
                    } else {
                        Log.w("GestionOrdenes", "Orden nula o sin ID en snapshot");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("GestionOrdenes", "Error al escuchar órdenes: " + error.getMessage());
            }
        });
    }

    public TreeMap<String, Orden> getOrdenes() {
        return ordenes;
    }

    public DatabaseReference getRefOrdenes() {
        return refOrdenes;
    }
}
