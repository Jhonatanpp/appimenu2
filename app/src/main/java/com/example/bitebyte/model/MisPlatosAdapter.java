package com.example.bitebyte.model;

import android.app.AlertDialog;
import android.content.Context;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.bitebyte.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MisPlatosAdapter extends RecyclerView.Adapter<MisPlatosAdapter.ViewHolder> {

    private Context context;
    private List<Plato> listaPlatos;

    public MisPlatosAdapter(Context context, List<Plato> listaPlatos) {
        this.context = context;
        this.listaPlatos = listaPlatos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.item_plato, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Plato plato = listaPlatos.get(position);
        holder.txtNombre.setText(plato.getNombre());
        holder.txtDescripcion.setText(plato.getDescripcion());
        holder.txtPrecio.setText("$" + plato.getPrecio());

        Glide.with(context).load(plato.getImagenUrl()).placeholder(R.drawable.food).into(holder.imgPlato);

        holder.itemView.setOnLongClickListener(v -> {
            mostrarDialogoEditar(plato);
            return true;
        });
    }

    private void mostrarDialogoEditar(Plato plato) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_editar_plato, null);
        EditText inputNombre = view.findViewById(R.id.dialogNombre);
        EditText inputDescripcion = view.findViewById(R.id.dialogDescripcion);
        EditText inputPrecio = view.findViewById(R.id.dialogPrecio);

        inputNombre.setText(plato.getNombre());
        inputDescripcion.setText(plato.getDescripcion());
        inputPrecio.setText(String.valueOf(plato.getPrecio()));

        new AlertDialog.Builder(context)
                .setTitle("Editar plato")
                .setView(view)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    plato.setNombre(inputNombre.getText().toString());
                    plato.setDescripcion(inputDescripcion.getText().toString());
                    plato.setPrecio(Double.parseDouble(inputPrecio.getText().toString()));

                    FirebaseDatabase.getInstance().getReference("platos")
                            .child(plato.getIdPlato()).setValue(plato);

                    notifyDataSetChanged();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public int getItemCount() {
        return listaPlatos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtDescripcion, txtPrecio;
        ImageView imgPlato;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombrePlato);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);
            imgPlato = itemView.findViewById(R.id.imgPlato);
        }
    }
}
