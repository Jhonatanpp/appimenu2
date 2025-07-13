package com.example.bitebyte.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bitebyte.R;

import java.util.ArrayList;

public class SeleccionarPlatosAdapter extends RecyclerView.Adapter<SeleccionarPlatosAdapter.ViewHolder> {

    private final ArrayList<Plato> listaPlatos;
    private final ArrayList<Plato> platosSeleccionados = new ArrayList<>();
    private final Context context;

    public SeleccionarPlatosAdapter(Context context, ArrayList<Plato> listaPlatos) {
        this.context = context;
        this.listaPlatos = listaPlatos;
    }

    @NonNull
    @Override
    public SeleccionarPlatosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plato_seleccionable, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Plato plato = listaPlatos.get(position);
        holder.textNombrePlato.setText(plato.getNombre());
        holder.textDescripcionPlato.setText(plato.getDescripcion());
        holder.checkBoxSeleccionar.setChecked(platosSeleccionados.contains(plato));

        // Imagen por defecto (opcional si no usas Picasso o Glide)
        holder.imagenPlato.setImageResource(android.R.drawable.ic_menu_gallery);

        holder.checkBoxSeleccionar.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!platosSeleccionados.contains(plato)) {
                    platosSeleccionados.add(plato);
                }
            } else {
                platosSeleccionados.remove(plato);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPlatos.size();
    }

    public ArrayList<Plato> getPlatosSeleccionados() {
        return new ArrayList<>(platosSeleccionados);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagenPlato;
        TextView textNombrePlato, textDescripcionPlato;
        CheckBox checkBoxSeleccionar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagenPlato = itemView.findViewById(R.id.imagenPlato);
            textNombrePlato = itemView.findViewById(R.id.textNombrePlato);
            textDescripcionPlato = itemView.findViewById(R.id.textDescripcionPlato);
            checkBoxSeleccionar = itemView.findViewById(R.id.checkBoxSeleccionar);
        }
    }
}
