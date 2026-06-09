package com.example.myapplication.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import org.json.JSONException;
import org.json.JSONObject;

public class HistorialFragment extends Fragment {

    private EditText edtId, edtNombre, edtMarca, edtDescripcion;
    private Button btnBuscar, btnActualizar;

    private static final String URL_BASE = "http://192.168.101.31:3000/api/herramientas/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_historial, container, false);

        edtId = view.findViewById(R.id.edtId);
        edtNombre = view.findViewById(R.id.edtNombre);
        edtMarca = view.findViewById(R.id.edtMarca);
        edtDescripcion = view.findViewById(R.id.edtDescripcion);

        btnBuscar = view.findViewById(R.id.btnBuscar);
        btnActualizar = view.findViewById(R.id.btnActualizar);

        btnBuscar.setOnClickListener(v -> buscarHerramienta());
        btnActualizar.setOnClickListener(v -> actualizarHerramienta());

        return view;
    }

    private void buscarHerramienta() {

        String id = edtId.getText().toString().trim();

        if (id.isEmpty()) {
            Toast.makeText(getContext(),
                    "Ingrese un ID",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String url = URL_BASE + id;

        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,

                response -> {
                    try {

                        JSONArray data = response.getJSONArray("data");

                        if (data.length() > 0) {

                            JSONObject herramienta = data.getJSONObject(0);

                            edtNombre.setText(herramienta.getString("nombre"));
                            edtMarca.setText(herramienta.getString("marca"));
                            edtDescripcion.setText(herramienta.getString("descripcion"));

                            Toast.makeText(getContext(),
                                    "Herramienta encontrada",
                                    Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(getContext(),
                                    "No se encontró la herramienta",
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

                    String mensaje = "Error al buscar";

                    if (error.networkResponse != null) {
                        mensaje += "\nCódigo: "
                                + error.networkResponse.statusCode;
                    }

                    Log.e("VOLLEY_ERROR",
                            error.toString());

                    Toast.makeText(getContext(),
                            mensaje,
                            Toast.LENGTH_LONG).show();
                }
        );

        queue.add(request);
    }

    private void actualizarHerramienta() {

        String id = edtId.getText().toString().trim();
        String nombre = edtNombre.getText().toString().trim();
        String marca = edtMarca.getText().toString().trim();
        String descripcion = edtDescripcion.getText().toString().trim();

        if (id.isEmpty()) {
            Toast.makeText(getContext(),
                    "Ingrese un ID",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (nombre.isEmpty()) {
            edtNombre.setError("Ingrese nombre");
            return;
        }

        if (marca.isEmpty()) {
            edtMarca.setError("Ingrese marca");
            return;
        }

        try {

            JSONObject datos = new JSONObject();
            datos.put("nombre", nombre);
            datos.put("marca", marca);
            datos.put("descripcion", descripcion);

            String url = URL_BASE + id;

            RequestQueue queue = Volley.newRequestQueue(requireContext());

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.PUT,
                    url,
                    datos,

                    response -> Toast.makeText(
                            getContext(),
                            "Herramienta actualizada correctamente",
                            Toast.LENGTH_LONG
                    ).show(),

                    error -> {

                        String mensaje = "Error al actualizar";

                        if (error.networkResponse != null) {
                            mensaje += "\nCódigo: "
                                    + error.networkResponse.statusCode;
                        }

                        Log.e("VOLLEY_ERROR",
                                error.toString());

                        Toast.makeText(
                                getContext(),
                                mensaje,
                                Toast.LENGTH_LONG
                        ).show();
                    }
            );

            queue.add(request);

        } catch (JSONException e) {

            Log.e("JSON_ERROR",
                    e.getMessage());

            Toast.makeText(
                    getContext(),
                    "Error creando JSON",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}