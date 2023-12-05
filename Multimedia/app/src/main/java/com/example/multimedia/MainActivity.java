package com.example.multimedia;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public static int RC_PHOTO_PICKER = 0;

    private ActivityResultLauncher<Intent> someActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa el ActivityResultLauncher en el onCreate
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // No hay códigos de solicitud en este ejemplo
                            Intent data = result.getData();
                            Uri uri = data.getData();
                            ImageView imageView = findViewById(R.id.img);
                            imageView.setImageURI(uri);
                        }
                    }
                });

        // Asocia el botón con el método
        Button btnOpenGallery = findViewById(R.id.button);
        btnOpenGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSomeActivityForResult(view);
            }
        });
    }

    public void openSomeActivityForResult(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        someActivityResultLauncher.launch(intent);
    }
}
