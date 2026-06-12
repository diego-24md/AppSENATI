package com.example.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class HerramientaEliminarAdapter extends RecyclerView.Adapter<HerramientaEliminarAdapter.ViewHolder> {

    public interface OnEliminarClick {
        void onEliminar(int position);
    }

    private final ArrayList<String> nombres;
    private final ArrayList<String> marcas;
    private final OnEliminarClick listener;

    public HerramientaEliminarAdapter(ArrayList<String> nombres,
                                      ArrayList<String> marcas,
                                      OnEliminarClick listener) {
        this.nombres  = nombres;
        this.marcas   = marcas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_herramienta_eliminar, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNombre.setText(nombres.get(position));
        holder.tvMarca.setText(marcas.get(position));
        holder.btnEliminar.setOnClickListener(v -> listener.onEliminar(position));
    }

    @Override
    public int getItemCount() {
        return nombres.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvMarca;
        Button btnEliminar;

        ViewHolder(View itemView) {
            super(itemView);
            tvNombre    = itemView.findViewById(R.id.edtNombreRV);
            tvMarca     = itemView.findViewById(R.id.edtDescripcionRV);
            btnEliminar = itemView.findViewById(R.id.btnEliminarRV);
        }
    }
}