package com.example.myapplication.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConfiguracionFragment extends Fragment {

    private ListView lstHerramientas;
    private RequestQueue requestQueue;

    private final String URL = "http://192.168.101.31:3000/api/herramientas/";

    private ArrayList<String> listaTexto;
    private ArrayList<Integer> listaIds;

    public ConfiguracionFragment() {
        super(R.layout.fragment_configuracion);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        lstHerramientas = view.findViewById(R.id.lstHerramientas);

        listaTexto = new ArrayList<>();
        listaIds = new ArrayList<>();

        cargarHerramientas();

        lstHerramientas.setOnItemClickListener((parent, view1, position, id) -> {

            int idHerramienta = listaIds.get(position);

            new AlertDialog.Builder(requireContext())
                    .setTitle("Eliminar herramienta")
                    .setMessage("¿Desea eliminar esta herramienta?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        eliminarHerramienta(idHerramienta);
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    private void cargarHerramientas() {

        requestQueue = Volley.newRequestQueue(requireContext());

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,

                response -> {

                    try {

                        listaTexto.clear();
                        listaIds.clear();

                        JSONArray data = response.getJSONArray("data");

                        for (int i = 0; i < data.length(); i++) {

                            JSONObject herramienta = data.getJSONObject(i);

                            int id = herramienta.getInt("idherramienta");
                            String nombre = herramienta.getString("nombre");
                            String marca = herramienta.getString("marca");

                            listaIds.add(id);

                            listaTexto.add(
                                    "ID: " + id +
                                            "\nNombre: " + nombre +
                                            "\nMarca: " + marca
                            );
                        }

                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<>(
                                        requireContext(),
                                        android.R.layout.simple_list_item_1,
                                        listaTexto
                                );

                        lstHerramientas.setAdapter(adapter);

                    } catch (Exception e) {
                        Log.e("ErrorJSON", e.toString());
                    }
                },

                error -> {

                    String mensaje = "Error desconocido";

                    if (error.networkResponse != null) {
                        mensaje = "Código: " + error.networkResponse.statusCode;
                    }

                    Toast.makeText(
                            getContext(),
                            mensaje,
                            Toast.LENGTH_LONG
                    ).show();

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

                    Toast.makeText(
                            getContext(),
                            "Herramienta eliminada correctamente",
                            Toast.LENGTH_LONG
                    ).show();

                    cargarHerramientas();
                },

                error -> {

                    Toast.makeText(
                            getContext(),
                            "Error al eliminar",
                            Toast.LENGTH_LONG
                    ).show();

                    Log.e("ErrorWS", error.toString());
                }
        );

        requestQueue.add(request);
    }
}