package com.example.sockets_android_pdytr2021;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApplication extends Application {
        // Crea dos hilos para ejecutar una instancia que actua como cliente y una de servidor
        public ExecutorService executorService = Executors.newFixedThreadPool(2);
}

