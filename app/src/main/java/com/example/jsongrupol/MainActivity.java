// Importaciones necesarias
package com.example.jsongrupol;

import android.os.Bundle; // facilitan el ciclo de vida y acceso a la red
import android.os.StrictMode;
import android.widget.TextView; // Muestra elementos UI en pantalla

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray; // permiten procesar los JSON
import org.json.JSONObject;

import java.io.BufferedReader; // las primeras tres importaciones son para la conexion HTTP
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
// el extend nos permite compatibilidad con versiones anteriores de android
public class MainActivity extends AppCompatActivity {
// oncreate se ejecuta cuando la actividad se crea

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Permitir operaciones de red en el hilo principal, y elimina sus restricciones
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Inicializar el TextView donde mostraremos los datos
        TextView textView = findViewById(R.id.textView);

        // Hacer la solicitud HTTP y obtener el JSON
        try {
            // URL de un  API REST para traer los datos en formato JSON
            URL url = new URL("https://jsonplaceholder.typicode.com/users");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
// permite leer el texto de manera eficiente
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                // Parsear o convertir el JSON obtenido
                JSONArray jsonArray = new JSONArray(result.toString());
                StringBuilder userData = new StringBuilder();

                // Iterar sobre el arreglo de objetos JSON y extraer los datos y ubicarlos segun donde va cada uno
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject user = jsonArray.getJSONObject(i);
                    String name = user.getString("name");
                    String email = user.getString("email");
                    userData.append("Name: ").append(name).append(", Email: ").append(email).append("\n");
                }

                // Mostrar los datos en el TextView
                textView.setText(userData.toString());

            } finally {
                urlConnection.disconnect();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

