    package com.example.multimedia;

    import androidx.activity.result.ActivityResult;
    import androidx.activity.result.ActivityResultCallback;
    import androidx.activity.result.ActivityResultLauncher;
    import androidx.activity.result.contract.ActivityResultContracts;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.content.FileProvider;

    import android.app.Activity;
    import android.content.Intent;
    import android.graphics.Bitmap;
    import android.net.Uri;
    import android.os.Bundle;
    import android.os.Environment;
    import android.provider.MediaStore;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ImageView;

    import java.io.File;
    import java.io.IOException;
    import java.text.SimpleDateFormat;
    import java.util.Arrays;
    import java.util.Comparator;
    import java.util.Date;

    public class MainActivity extends AppCompatActivity {


        private ActivityResultLauncher<Intent> someActivityResultLauncher;
        private ActivityResultLauncher<Intent> camera;
        private ActivityResultLauncher<Intent> fullsize;
        String currentPhotoPath;
        Uri photoURI;
        // Declarar imageView como un campo de clase
        private ImageView imageView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            imageView = findViewById(R.id.img);
            loadLastPhoto();

            // Inicializa el ActivityResultLauncher en el onCreate
            someActivityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                Intent data = result.getData();
                                Uri uri = data.getData();
                                imageView.setImageURI(uri);
                            }
                        }
                    });

            camera = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                Intent data = result.getData();
                                if (data != null) {
                                    Bundle extras = data.getExtras();
                                    if (extras != null) {
                                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                                        imageView.setImageBitmap(imageBitmap);
                                    }
                                }
                            }
                        }
                    });
            fullsize = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            imageView.setImageURI(photoURI);
                        }
                    });
            Button btnOpenGallery = findViewById(R.id.button);
            Button btnOpenCamera = findViewById(R.id.button2);
            Button btnOpenCamerafullsize = findViewById(R.id.button33);
            Button btnOpenReciclerview = findViewById(R.id.button3);

            btnOpenReciclerview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            btnOpenGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openSomeActivityForResult(view);
                }
            });

            btnOpenCamerafullsize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dispatchTakePictureIntent(view);
                }
            });
            btnOpenCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    camera.launch(takePictureIntent);

                }
            });

        }

        public void openSomeActivityForResult(View view) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            someActivityResultLauncher.launch(intent);
        }

        private File createImageFile() throws IOException {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = image.getAbsolutePath();

            return image;
        }


        private void dispatchTakePictureIntent(View view) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                // Create the File where the photo should go
                File photoFile = null;
                Log.i("hola","hahahahah");
                try {
                    photoFile = createImageFile();
                    System.out.println(photoFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    photoURI = FileProvider.getUriForFile(this,
                            "com.example.multimedia.fileprovider",photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                    fullsize.launch(takePictureIntent);

            }
        }

        private void loadLastPhoto() {
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);


            File[] files = storageDir.listFiles();

            if (files != null && files.length > 0) {
                Arrays.sort(files, new Comparator<File>() {
                    @Override
                    public int compare(File file1, File file2) {
                        return Long.compare(file2.lastModified(), file1.lastModified());
                    }
                });
                File lastPhotoFile = files[0];
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.multimedia.fileprovider", lastPhotoFile);
                imageView.setImageURI(photoURI);
            }
        }

    }
