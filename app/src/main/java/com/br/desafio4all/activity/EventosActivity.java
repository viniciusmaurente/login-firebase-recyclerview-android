package com.br.desafio4all.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.br.desafio4all.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class EventosActivity extends AppCompatActivity {


    private static final int REQUEST_CODE_IMAGE = 101;
    private ImageView imageViewAdd;
    private EditText inputImageName;
    private EditText inputDescricao;
    private Button btnUpload;

    DatabaseReference DataRef;
    StorageReference StorageRef;

    Uri imageUri;
    boolean isImageAdded = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);
        imageViewAdd    = findViewById(R.id.imageViewAdd);
        inputImageName  = findViewById(R.id.inputImageName);
        inputDescricao  = findViewById(R.id.inputDescricao);
        btnUpload       = findViewById(R.id.btnUpload);

        DataRef = FirebaseDatabase.getInstance().getReference().child("Evento");
        StorageRef = FirebaseStorage.getInstance().getReference().child("EventoImage");

        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String imageName      = inputImageName.getText().toString();

                if (isImageAdded != false && imageName != null){
                    uploadImage(imageName);
                }
            }
        });
    }
    private void uploadImage(final String imageName) {

        final String key = DataRef.push().getKey();
        StorageRef.child(key + ".jpg").putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                StorageRef.child(key + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("NomeEvento", imageName);
                        hashMap.put("ImageUrl", uri.toString());



                        DataRef.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                // Toast.makeText(EventoActivity.this, "Sucesso ao salvar o Evento", Toast.LENGTH_SHORT).show();

                            }
                        });
                        finish();
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE && data != null){
            imageUri = data.getData();
            isImageAdded = true;
            imageViewAdd.setImageURI(imageUri);

        }
    }
}