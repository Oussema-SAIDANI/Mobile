package com.example.projetjava.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetjava.Livre;
import com.example.projetjava.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Ajouter extends Fragment {

    private ImageView ProfileImage;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private TextView titre,genre,nom,prenom,resume,edition,isbn,date;
    private Button AjouterBook;
    private StorageTask mUploadTask;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;

    public Ajouter() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_ajouter, container, false);


    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProfileImage = (ImageView) view.findViewById(R.id.imageView2);
        titre = (TextView) view.findViewById(R.id.editTextTextPersonName6);
        nom = (TextView) view.findViewById(R.id.editTextTextPersonName8);
        prenom = (TextView) view.findViewById(R.id.editTextTextPersonName9);
        resume = (TextView) view.findViewById(R.id.editTextTextPersonName10);
        edition=(TextView) view.findViewById(R.id.editTextTextPersonName14);
        genre = (TextView) view.findViewById(R.id.editTextTextPersonName11);
        isbn = (TextView) view.findViewById(R.id.editTextTextPersonName15);
        date = (TextView) view.findViewById(R.id.editTextDate);
        AjouterBook = (Button) view.findViewById(R.id.ajouterBook);
        mStorageRef = FirebaseStorage.getInstance().getReference("Images");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Database");
        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery();
            }
        });
        view.findViewById(R.id.ajouterBook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask != null) {
                    Log.d("erreur","erreur");
                } else {
                    Save();
                 NavHostFragment.findNavController(Ajouter.this)
                            .navigate(R.id.action_ajout_to_liste);

                    /*Intent intent = new Intent(getActivity(), Liste.class);
                    startActivity(intent);*/
                }


            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE);
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR =  getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void Save() {
        if(!TextUtils.isEmpty(isbn.getText().toString())&&
                !TextUtils.isEmpty(date.getText().toString())&&
                !TextUtils.isEmpty(genre.getText().toString())&&
                !TextUtils.isEmpty(titre.getText().toString())&&
                !TextUtils.isEmpty(nom.getText().toString())&&
                !TextUtils.isEmpty(prenom.getText().toString())&&
                !TextUtils.isEmpty(edition.getText().toString())&&
                !TextUtils.isEmpty(resume.getText().toString())&&
                imageUri != null
        ) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));
            mUploadTask = fileReference.putFile(imageUri);

            Task<Uri> urlTask = mUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        Livre livre = new Livre(titre.getText().toString().trim(),
                                nom.getText().toString().trim(),
                                prenom.getText().toString().trim(),
                                resume.getText().toString().trim(),
                                downloadUri.toString(),
                                genre.getText().toString().trim(),
                                date.getText().toString().trim(),
                                edition.getText().toString().trim(),
                                isbn.getText().toString().trim()
                        );
                        String uploadId = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId).setValue(livre);
                    } else {
                        Log.d("erreur","ERREUR HANDLER");
                    }
                }
            });
           /* mUploadTask = fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Livre livre = new Livre(titre.getText().toString().trim(),
                                    nom.getText().toString().trim(),
                                    prenom.getText().toString().trim(),
                                    resume.getText().toString().trim(),
                                    taskSnapshot.getDownloadUrl().toString(),
                                    genre.getText().toString().trim(),
                                    date.getText().toString().trim(),
                                    edition.getText().toString().trim(),
                                    isbn.getText().toString().trim()
                            );
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(livre);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });*/
        }
        else {
            Log.d("erreur","erreurSAVE");
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            ProfileImage.setImageURI(imageUri);
        }
    }
}