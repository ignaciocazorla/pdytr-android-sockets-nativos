package com.example.sockets_android_pdytr2021;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.sockets_android_pdytr2021.databinding.ActivityMainBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'sockets_android_pdytr2021' library on application startup.
    static {
        System.loadLibrary("sockets_android_pdytr2021");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Crea dos hilos para ejecutar una instancia que actua como cliente y una de servidor
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button startServerButton = binding.startServerButton;

        Button startClientButton = binding.startClientButton;

        startServerButton.setOnClickListener(v -> {

            // Hilo servidor
            executorService.execute(() -> {
                int value = runServer(30000);
                Log.d("Valor de retorno (SRV)", String.valueOf(value));
            });

            // Habilita boton del cliente
            startClientButton.setEnabled(true);

            // Deshabilita boton del servidor
            startServerButton.setEnabled(false);


        });

        startClientButton.setOnClickListener(v -> {
                // Hilo cliente
                executorService.execute(() -> {
                    int value = runClient(30000);
                    Log.d("Valor de retorno (CLI)", String.valueOf(value));
                });
            // Deshabilita boton del cliente
            startClientButton.setEnabled(false);
        });


    }

    /**
     * Metodos nativos que estan implementados en la libreria nativa 'sockets_android_pdytr2021'
     */

    public native int runServer(int port);

    public native int runClient(int port);
}