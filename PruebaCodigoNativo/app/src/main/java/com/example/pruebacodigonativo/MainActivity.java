package com.example.pruebacodigonativo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.pruebacodigonativo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'pruebacodigonativo' library on application startup.
    static {
        System.loadLibrary("pruebacodigonativo");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
        TextView tv = binding.sampleText;
        tv.setText(saludar());
    }

    /**
     * A native method that is implemented by the 'pruebacodigonativo' native library,
     * which is packaged with this application.
     */
    public native String saludar();
}