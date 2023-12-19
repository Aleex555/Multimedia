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


        List<Uri> imageUris = obtenerImageUris();


        RecyclerView recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        CustomAdapter adapter = new CustomAdapter(imageUris);
        recyclerView.setAdapter(adapter);


        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
