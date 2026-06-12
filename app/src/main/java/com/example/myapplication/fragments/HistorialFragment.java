package com.example.myapplication.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.adapters.HerramientaAdapter;
import com.example.myapplication.entity.Herramienta;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistorialFragment extends Fragment {

    private RecyclerView rvHerramientas;
    private HerramientaAdapter adapter;
    private ArrayList<Herramienta> listaHerramientas;

    private static final String URL_BASE = "http://192.168.101.31:3000/api/herramientas";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_historial, container, false);

        rvHerramientas = view.findViewById(R.id.rvHerramientas);
        rvHerramientas.setLayoutManager(new LinearLayoutManager(getContext()));

        listaHerramientas = new ArrayList<>();
        adapter = new HerramientaAdapter(listaHerramientas, getContext());
        rvHerramientas.setAdapter(adapter);

        listarHerramientas();

        return view;
    }

    private void listarHerramientas() {

        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                URL_BASE,
                null,

                response -> {
                    try {

                        JSONArray data = response.getJSONArray("data");

                        listaHerramientas.clear();

                        for (int i = 0; i < data.length(); i++) {

                            JSONObject obj = data.getJSONObject(i);

                            Herramienta herramienta = new Herramienta();
                            herramienta.setIdherramienta(obj.getInt("idherramienta"));
                            herramienta.setNombre(obj.getString("nombre"));
                            herramienta.setMarca(obj.getString("marca"));
                            herramienta.setDescripcion(obj.getString("descripcion"));

                            listaHerramientas.add(herramienta);
                        }

                        adapter.notifyDataSetChanged();

                        if (listaHerramientas.isEmpty()) {
                            Toast.makeText(getContext(),
                                    "No hay herramientas registradas",
                                    Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {

                        e.printStackTrace();

                        Toast.makeText(getContext(),
                                "Error procesando datos",
                                Toast.LENGTH_LONG).show();
                    }
                },

                error -> {

                    String mensaje = "Error al listar herramientas";

                    if (error.networkResponse != null) {
                        mensaje += "\nCódigo: " + error.networkResponse.statusCode;
                    }

                    Log.e("VOLLEY_ERROR", error.toString());

                    Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
                }
        );

        queue.add(request);
    }
}