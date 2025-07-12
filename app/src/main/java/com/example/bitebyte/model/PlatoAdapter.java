package com.example.bitebyte.model;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bitebyte.model.PersonalizarPlatoActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bitebyte.R;

import java.util.List;

public class PlatoAdapter extends RecyclerView.Adapter<PlatoAdapter.PlatoViewHolder> {

    private List<Plato> listaPlatos;

    public PlatoAdapter(List<Plato> listaPlatos) {
        this.listaPlatos = listaPlatos;
    }

    @NonNull
    @Override
    public PlatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.plato_en_menu, parent, false);
        return new PlatoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull PlatoViewHolder holder, int position) {
        Plato plato = listaPlatos.get(position);
        holder.textNombre.setText(plato.getNombre());
        holder.textDescripcion.setText(plato.getDescripcion());
        holder.textPrecio.setText("$" + String.format("%.2f", plato.getPrecio()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), PersonalizarPlatoActivity.class);
            intent.putExtra("plato", plato);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaPlatos.size();
    }

    public static class PlatoViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre, textDescripcion, textPrecio;

        public PlatoViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.tvNombrePlato);
            textDescripcion = itemView.findViewById(R.id.tvDescripcionPlato);
            textPrecio = itemView.findViewById(R.id.tvPrecioPlato);
        }
    }
}
