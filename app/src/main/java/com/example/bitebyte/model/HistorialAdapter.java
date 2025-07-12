package com.example.bitebyte.model;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bitebyte.R;
import com.example.bitebyte.model.Orden;
import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder> {

    private Context context;
    private List<Orden> listaPedidos;
    private OnReseñaClickListener listener;

    public interface OnReseñaClickListener {
        void onReseñaClick(Orden orden);
    }

    public HistorialAdapter(Context context, List<Orden> listaPedidos, OnReseñaClickListener listener) {
        this.context = context;
        this.listaPedidos = listaPedidos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HistorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.item_historial_pedido, parent, false);
        return new HistorialViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialViewHolder holder, int position) {
        Orden orden = listaPedidos.get(position);



        holder.textId.setText("Pedido: " + orden.getIdOrden());
        holder.textEstado.setText("Estado: " + orden.getEstado().name());

        if (orden.getReseña() != null && !orden.getReseña().isEmpty()) {
            holder.textReseña.setText("Reseña: " + orden.getReseña());
            holder.botonReseñar.setVisibility(View.GONE);
        } else {
            holder.textReseña.setText("Sin reseña");
            holder.botonReseñar.setVisibility(View.VISIBLE);
            holder.botonReseñar.setOnClickListener(v -> listener.onReseñaClick(orden));
        }
    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    public static class HistorialViewHolder extends RecyclerView.ViewHolder {
        TextView textId, textEstado, textReseña;
        Button botonReseñar;

        public HistorialViewHolder(@NonNull View itemView) {
            super(itemView);
            textId = itemView.findViewById(R.id.textIdPedido);
            textEstado = itemView.findViewById(R.id.textEstadoPedido);
            textReseña = itemView.findViewById(R.id.textReseña);
            botonReseñar = itemView.findViewById(R.id.btnReseñar);
        }
    }
}