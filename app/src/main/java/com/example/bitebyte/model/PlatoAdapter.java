package com.example.bitebyte.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bitebyte.R;

import java.util.List;

public class PlatoAdapter extends RecyclerView.Adapter<PlatoAdapter.PlatoViewHolder> {

    private Context context;
    private List<Plato> listaPlatos;

    public PlatoAdapter(Context context, List<Plato> listaPlatos) {
        this.context = context;
        this.listaPlatos = listaPlatos;
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
        holder.txtNombre.setText(plato.getNombre());
        holder.txtDescripcion.setText(plato.getDescripcion());
        holder.txtPrecio.setText("$" + plato.getPrecio());

        if (plato.getImagenUrl() != null && !plato.getImagenUrl().isEmpty()) {
            Glide.with(context)
                    .load(plato.getImagenUrl())
                    .placeholder(R.drawable.food)
                    .into(holder.imgPlato);
        } else {
            holder.imgPlato.setImageResource(R.drawable.food);
        }
    }

    @Override
    public int getItemCount() {
        return listaPlatos.size();
    }

    public static class PlatoViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtDescripcion, txtPrecio;
        ImageView imgPlato;

        public PlatoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombrePlato);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);
            imgPlato = itemView.findViewById(R.id.imgPlato);
        }
    }
}
