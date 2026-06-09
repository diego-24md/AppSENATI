package com.example.myapplication.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class RecepcionFragment extends Fragment {

    private EditText edtIdBuscar, edtNombre, edtMarca, edtDescripcion;
    private Button btnBuscar;

    private RadioButton rbtBueno, rbtRegular, rbtMalo;
    private RadioButton rbtElectrica, rbtManual;

    private RequestQueue requestQueue;

    private final String URL = "http://192.168.101.31:3000/api/herramientas/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_recepcion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        edtIdBuscar = view.findViewById(R.id.edtIdBuscar);
        edtNombre = view.findViewById(R.id.edtNombre);
        edtMarca = view.findViewById(R.id.edtMarca);
        edtDescripcion = view.findViewById(R.id.edtDescripcion);

        rbtBueno = view.findViewById(R.id.rbtBueno);
        rbtRegular = view.findViewById(R.id.rbtRegular);
        rbtMalo = view.findViewById(R.id.rbtMalo);

        rbtElectrica = view.findViewById(R.id.rbtElectrica);
        rbtManual = view.findViewById(R.id.rbtManual);

        btnBuscar = view.findViewById(R.id.btnBuscar);

        btnBuscar.setOnClickListener(v -> buscarHerramienta());
    }

    private void buscarHerramienta() {

        String id = edtIdBuscar.getText().toString().trim();

        if (id.isEmpty()) {
            Toast.makeText(getContext(),
                    "Ingrese un ID",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        requestQueue = Volley.newRequestQueue(requireContext());

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                URL + id,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {

                            boolean success = jsonObject.getBoolean("success");

                            if (success) {

                                JSONArray herramientas = jsonObject.getJSONArray("data");

                                int idBuscado = Integer.parseInt(
                                        edtIdBuscar.getText().toString()
                                );

                                boolean encontrado = false;

                                for (int i = 0; i < herramientas.length(); i++) {

                                    JSONObject herramienta = herramientas.getJSONObject(i);

                                    int id = herramienta.getInt("idherramienta");

                                    if (id == idBuscado) {

                                        edtNombre.setText(
                                                herramienta.getString("nombre")
                                        );

                                        edtMarca.setText(
                                                herramienta.getString("marca")
                                        );

                                        edtDescripcion.setText(
                                                herramienta.getString("descripcion")
                                        );

                                        String condicion =
                                                herramienta.getString("condicion");

                                        rbtBueno.setChecked(false);
                                        rbtRegular.setChecked(false);
                                        rbtMalo.setChecked(false);

                                        if (condicion.equalsIgnoreCase("Bueno")) {
                                            rbtBueno.setChecked(true);
                                        } else if (condicion.equalsIgnoreCase("Regular")) {
                                            rbtRegular.setChecked(true);
                                        } else if (condicion.equalsIgnoreCase("Malo")) {
                                            rbtMalo.setChecked(true);
                                        }

                                        String tipo =
                                                herramienta.optString("tipo", "");

                                        rbtManual.setChecked(false);
                                        rbtElectrica.setChecked(false);

                                        if (tipo.equalsIgnoreCase("Manual")) {
                                            rbtManual.setChecked(true);
                                        } else if (tipo.equalsIgnoreCase("Eléctrica")) {
                                            rbtElectrica.setChecked(true);
                                        }

                                        encontrado = true;
                                        break;
                                    }
                                }

                                if (!encontrado) {
                                    Toast.makeText(
                                            getContext(),
                                            "No existe una herramienta con ese ID",
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            }

                        } catch (Exception e) {
                            Log.e("ErrorJSON", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getContext(),
                                "Herramienta no encontrada",
                                Toast.LENGTH_LONG).show();

                        Log.e("ErrorWS", error.toString());
                    }
                }
        );

        requestQueue.add(request);
    }
}