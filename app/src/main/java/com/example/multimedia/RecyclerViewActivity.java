package com.example.multimedia;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycledview1);

        // Obtener las URIs de las imágenes
        List<Uri> imageUris = obtenerImageUris();

        // Configurar el RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Crear e configurar el adaptador con las URIs
        CustomAdapter adapter = new CustomAdapter(imageUris);
        recyclerView.setAdapter(adapter);

        // Configurar el OnClickListener para el botón de volver
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cerrar la actividad actual y volver al MainActivity
                finish();
            }
        });
    }

    private List<Uri> obtenerImageUris() {
        List<Uri> imageUris = new ArrayList<>();

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (storageDir != null) {
            File[] files = storageDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    // Convertir cada archivo a Uri y agregarlo a la lista
                    Uri uri = Uri.fromFile(file);
                    imageUris.add(uri);
                }
            }
        }
        return imageUris;
    }
}
