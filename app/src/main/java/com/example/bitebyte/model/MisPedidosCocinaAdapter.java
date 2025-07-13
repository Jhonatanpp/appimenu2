package com.example.bitebyte.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bitebyte.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MisPedidosCocinaAdapter extends RecyclerView.Adapter<MisPedidosCocinaAdapter.ViewHolder> {

    private final ArrayList<Orden> lista;
    private final Context context;

    public MisPedidosCocinaAdapter(ArrayList<Orden> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mesa, resumen;
        Spinner spinnerEstado;
        Button btnActualizar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mesa = itemView.findViewById(R.id.txtMesa);
            resumen = itemView.findViewById(R.id.txtResumen);
            spinnerEstado = itemView.findViewById(R.id.spinnerEstado);
            btnActualizar = itemView.findViewById(R.id.btnActualizarEstado);
        }
    }

    @NonNull
    @Override
    public MisPedidosCocinaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido_cocina, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull MisPedidosCocinaAdapter.ViewHolder holder, int position) {
        Orden orden = lista.get(position);
        holder.mesa.setText("Mesa: " + orden.getIdUsuario());
        holder.resumen.setText("Platos: " + generarResumen(orden.getPlatos()));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.estados_orden, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinnerEstado.setAdapter(adapter);

        // Posicionar spinner en el estado actual
        int pos = orden.getEstado().ordinal();
        holder.spinnerEstado.setSelection(pos);

        holder.btnActualizar.setOnClickListener(v -> {
            int nuevoEstadoPos = holder.spinnerEstado.getSelectedItemPosition();
            orden.setEstado(EstadoOrden.values()[nuevoEstadoPos]);
            FirebaseDatabase.getInstance().getReference("ordenes")
                    .child(orden.getIdOrden())
                    .setValue(orden);
            Toast.makeText(context, "Estado actualizado", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    private String generarResumen(ArrayList<Plato> platos) {
        StringBuilder resumen = new StringBuilder();
        for (Plato p : platos) {
            resumen.append("- ").append(p.getNombre()).append("\n");
        }
        return resumen.toString().trim();
    }
}
