package com.example.bitebyte.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bitebyte.R;

import java.util.List;

public class PlatoAdapter extends RecyclerView.Adapter<PlatoAdapter.PlatoViewHolder> {

    private final List<Plato> listaPlatos;
    private final Context context;

    public PlatoAdapter(List<Plato> listaPlatos, Context context) {
        this.listaPlatos = listaPlatos;
        this.context = context;
    }

    @NonNull
    @Override
    public PlatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.item_plato, parent, false);
        return new PlatoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull PlatoViewHolder holder, int position) {
        Plato plato = listaPlatos.get(position);
        holder.nombre.setText(plato.getNombre());
        holder.descripcion.setText(plato.getDescripcion());
        holder.precio.setText(String.format("$ %.2f", plato.getPrecio()));
    }

    @Override
    public int getItemCount() {
        return listaPlatos.size();
    }

    public static class PlatoViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, descripcion, precio;

        public PlatoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.textNombrePlato);
            descripcion = itemView.findViewById(R.id.textDescripcionPlato);
            precio = itemView.findViewById(R.id.textPrecioPlato);
        }
    }
}
