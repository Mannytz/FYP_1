package com.mwendwa.fyp_1;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.google.firebase.storage.UploadTask;

public class VenueCreationFormActivity extends AppCompatActivity {

    private EditText venueName, capacity, location, facilities;
    private ImageView venPic;
    private Button chooseImage, submitVenueDetails;
    private static final int PICK_IMAGE = 1;
    private static final int PERMISSION = 11;
    private Uri image;
    private String vName, vCpacity, vLocation, vFacilities;
    private ProgressDialog progressDialog;
    private FirebaseDatabase database;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_creation_form);

        venueName = findViewById(R.id.etVenueName);
        capacity = findViewById(R.id.etVenueCapacity);
        location = findViewById(R.id.etVenueLocation);
        facilities = findViewById(R.id.etVenueFacilities);
        venPic = findViewById(R.id.ivVenuePic);
        chooseImage = findViewById(R.id.btnChooseVenueImage);
        submitVenueDetails = findViewById(R.id.btnSubmitVenueDetails);

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(VenueCreationFormActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    openGallery1();
                } else {
                    ActivityCompat.requestPermissions(VenueCreationFormActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION);
                }
            }
        });

        submitVenueDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vName = venueName.getText().toString();
                vCpacity = capacity.getText().toString();
                vLocation = location.getText().toString();
                vFacilities = facilities.getText().toString();

                boolean val = validate(vName, vCpacity, vLocation, vFacilities);

                if (val) {
                    if (image != null) {
                        uploadData(image);
                    } else {
                        Toast.makeText(VenueCreationFormActivity.this, "Please choose an image", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void openGallery1 () {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            image = data.getData();
            venPic.setImageURI(image);
        } else {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery1();
        } else {
            Toast.makeText(this, "Please provide permission", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean validate (String name, String capacity, String location, String facility) {
        boolean result = false;

        if (name.isEmpty() || capacity.isEmpty() || location.isEmpty() || facility.isEmpty()) {
            Toast.makeText(this, "Please make sure all fields are filled", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }

        return result;
    }


    public void uploadData (Uri uri) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading Data...");
        progressDialog.setProgress(0);
        progressDialog.show();

        StorageReference ref1 = storage.getReference();
        final StorageReference ref2 = ref1.child("Venues").child(vName);
        UploadTask uploadTask = ref2.putFile(uri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VenueCreationFormActivity.this, "Upload Failed.", Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currentProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
            }
        });

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                return ref2.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    Venue venue = new Venue(vName, vCpacity, vLocation, downloadUri.toString(), vFacilities);
                    DatabaseReference databaseReference = database.getReference("Venues/"+vName);
                    databaseReference.setValue(venue).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(VenueCreationFormActivity.this, "Database Access successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(VenueCreationFormActivity.this, "Database access unsuccessful", Toast.LENGTH_SHORT).show();
                            }

                            progressDialog.cancel();
                        }
                    });
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }
}
