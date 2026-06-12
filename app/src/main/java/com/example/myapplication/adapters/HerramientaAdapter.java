package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entity.Herramienta;

import java.util.ArrayList;

public class HerramientaAdapter extends RecyclerView.Adapter<HerramientaAdapter.ViewHolder> {

    private ArrayList<Herramienta> listaHerramientas;
    private Context context;

    public HerramientaAdapter(ArrayList<Herramienta> listaHerramientas, Context context) {
        this.listaHerramientas = listaHerramientas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.item_herramienta, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Herramienta herramienta = listaHerramientas.get(position);
        holder.edtNombreRV.setText(herramienta.getNombre());
        holder.edtDescripcionRV.setText(herramienta.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return listaHerramientas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView edtNombreRV, edtDescripcionRV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            edtNombreRV = itemView.findViewById(R.id.edtNombreRV);
            edtDescripcionRV = itemView.findViewById(R.id.edtDescripcionRV);
        }
    }
}