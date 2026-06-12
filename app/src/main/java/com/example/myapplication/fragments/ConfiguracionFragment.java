package com.example.myapplication.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.adapters.HerramientaEliminarAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConfiguracionFragment extends Fragment {

    private RecyclerView rvHerramientas;
    private RequestQueue requestQueue;

    private final String URL = "http://192.168.101.31:3000/api/herramientas/";

    private ArrayList<String> listaNombres;
    private ArrayList<String> listaMarcas;
    private ArrayList<Integer> listaIds;

    public ConfiguracionFragment() {
        super(R.layout.fragment_configuracion);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvHerramientas = view.findViewById(R.id.rvHerramientas);
        rvHerramientas.setLayoutManager(new LinearLayoutManager(requireContext()));

        listaNombres = new ArrayList<>();
        listaMarcas  = new ArrayList<>();
        listaIds     = new ArrayList<>();

        cargarHerramientas();
    }

    private void cargarHerramientas() {

        requestQueue = Volley.newRequestQueue(requireContext());

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,

                response -> {
                    try {
                        listaNombres.clear();
                        listaMarcas.clear();
                        listaIds.clear();

                        JSONArray data = response.getJSONArray("data");

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject h = data.getJSONObject(i);
                            listaIds.add(h.getInt("idherramienta"));
                            listaNombres.add(h.getString("nombre"));
                            listaMarcas.add(h.getString("marca"));
                        }

                        HerramientaEliminarAdapter adapter = new HerramientaEliminarAdapter(
                                listaNombres,
                                listaMarcas,
                                position -> {
                                    int idHerramienta = listaIds.get(position);
                                    new AlertDialog.Builder(requireContext())
                                            .setTitle("Eliminar herramienta")
                                            .setMessage("¿Desea eliminar esta herramienta?")
                                            .setPositiveButton("Sí", (dialog, which) ->
                                                    eliminarHerramienta(idHerramienta))
                                            .setNegativeButton("No", null)
                                            .show();
                                }
                        );

                        rvHerramientas.setAdapter(adapter);

                    } catch (Exception e) {
                        Log.e("ErrorJSON", e.toString());
                    }
                },

                error -> {
                    String mensaje = "Error desconocido";
                    if (error.networkResponse != null)
                        mensaje = "Código: " + error.networkResponse.statusCode;

                    Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
                    Log.e("ErrorWS", error.toString());
                }
        );

        requestQueue.add(request);
    }

    private void eliminarHerramienta(int id) {

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                URL + id,
                null,

                response -> {
                    Toast.makeText(getContext(),
                            "Herramienta eliminada correctamente",
                            Toast.LENGTH_LONG).show();
                    cargarHerramientas();
                },

                error -> {
                    Toast.makeText(getContext(),
                            "Error al eliminar",
                            Toast.LENGTH_LONG).show();
                    Log.e("ErrorWS", error.toString());
                }
        );

        requestQueue.add(request);
    }
}