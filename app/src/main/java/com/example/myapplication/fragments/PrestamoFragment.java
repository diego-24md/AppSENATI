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

import java.nio.charset.StandardCharsets;

public class PrestamoFragment extends Fragment {

    Button btnTestWS, btnGuardarHerramienta;
    RequestQueue requestQueue;
    String condicion = "", tipo = "";
    private final String URL = "http://192.168.101.31:3000/api/herramientas/";

    EditText edtNombre, edtMarca, edtDescripcion;
    RadioButton rbtBueno, rbtRegular, rbtMalo;
    RadioButton rbtManual, rbtElectrica;

    public PrestamoFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prestamo, container, false);
    }

    private void registrarHerramienta(){
        //0. Preparar el JSON
        //Definir que condicion tiene
        condicion = "";
        if (rbtBueno.isChecked()) { condicion = "Bueno"; }
        if (rbtRegular.isChecked()) { condicion = "Regular"; }
        if (rbtMalo.isChecked()) { condicion = "Malo"; }

        tipo = "";
        if (rbtManual.isChecked()) { tipo = "Manual"; }
        if (rbtElectrica.isChecked()) { tipo = "Eléctrica"; }

        JSONObject datosEnviar = new JSONObject();
        try{
            datosEnviar.put("nombre", edtNombre.getText().toString()); //EditText
            datosEnviar.put("marca", edtMarca.getText().toString()); //EditText
            datosEnviar.put("descripcion", edtDescripcion.getText().toString()); //EditText
            datosEnviar.put("condicion", condicion);
            datosEnviar.put("tipo", tipo);
        }catch (Exception e){
            Log.e("ErrorJSON", e.toString());
        }

        //1. Canal de comunicación
        requestQueue = Volley.newRequestQueue(requireContext().getApplicationContext());

        // 2. Consumir WS > Lectura de datos (JSON resultado)
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                datosEnviar,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            boolean success = jsonObject.getBoolean("success");
                            String message = jsonObject.getString("message");
                            int id = jsonObject.getInt("id");

                            if (success) {
                                resetUI();
                                Toast.makeText(getContext(), message + " - ID: " + id, Toast.LENGTH_LONG).show();
                                edtNombre.requestFocus();
                            }

                        } catch (Exception e) {
                            Log.e("ErrorJSON", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("ErrorWS", volleyError.toString());
                    }
                }
        );

        //3. Ejecución
        requestQueue.add(jsonObjectRequest);
    }

    private void resetUI() {
        // FALTA CONTENIDO....
    }

    private void testWS(){
        //¿Qué nos devolverá en la consulta / request?
        //GET (listar) => [{},{},{}]
        //GET (buscador) => {}
        requestQueue = Volley.newRequestQueue(requireContext().getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            boolean success = jsonObject.getBoolean("success");
                            String resultado = "";

                            if (success){
                                //JSONArrayRequest = solicitud / pedido
                                //JSONArray = contenedor
                                //Iterar la clave data = []
                                JSONArray listaHerramientas = jsonObject.getJSONArray("data");

                                //Ahora para terminar, iteramos recorremos el JSONArray
                                for (int i = 0; i < listaHerramientas.length(); i++){
                                    JSONObject herramienta = listaHerramientas.getJSONObject(i);
                                    resultado += herramienta.getString("nombre") + ", ";
                                }

                                Toast.makeText(getContext(), resultado, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e){
                            Log.e("ErrorJSON", "No podemos leer JSON");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("ErrorWS", volleyError.toString());
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnTestWS             = view.findViewById(R.id.btnTestWS);
        btnGuardarHerramienta = view.findViewById(R.id.btnGuardarHerramienta);

        edtNombre      = view.findViewById(R.id.edtNombre);
        edtMarca       = view.findViewById(R.id.edtMarca);
        edtDescripcion = view.findViewById(R.id.edtDescripcion);

        rbtBueno   = view.findViewById(R.id.rbtBueno);
        rbtRegular = view.findViewById(R.id.rbtRegular);
        rbtMalo    = view.findViewById(R.id.rbtMalo);

        rbtManual    = view.findViewById(R.id.rbtManual);
        rbtElectrica = view.findViewById(R.id.rbtElectrica);

        btnTestWS.setOnClickListener(v -> testWS());
        btnGuardarHerramienta.setOnClickListener(v -> registrarHerramienta());
    }
}