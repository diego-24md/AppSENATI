package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    Button btnIngresar;
    EditText edtUsuario, edtPassword;

    private static final String USUARIO_VALIDO = "Admin";
    private static final String PASSWORD_VALIDO = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        btnIngresar = findViewById(R.id.btnIniciarSesion);
        edtUsuario = findViewById(R.id.edtUsuario);
        edtPassword = findViewById(R.id.edtPassword);

        btnIngresar.setOnClickListener(v -> {
            String usuario = edtUsuario.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (usuario.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (usuario.equals(USUARIO_VALIDO) && password.equals(PASSWORD_VALIDO)) {
                Toast.makeText(this, "Bienvenido " + usuario, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, dashboard.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}