package com.mwendwa.fyp_1;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.security.Permission;
import java.util.Calendar;

public class RegisterEventActivity extends AppCompatActivity {

    private Button uploadImage, submitButton, btnStartDate, btnEndDate, btnSelectFile;
    private FloatingActionButton deleteBtn;
    //private LinearLayout parentLayout;
    private ImageView uploadedEventImage;
    private EditText etStartDate, etEndDate, etEventName, etOrganizerName, etOrganizerEmail, etShortDesc, etDetailedDesc;
    //private TextView tvNotification;
    private DatePickerDialog datePickerDialog;
    private static final int PICK_IMAGE = 100;
    Uri imageUri, pdfUri;
    private String selectedDate, evName, orgName, orgEmail, shortDesc, detDesc, stDate, endDate, value;

    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_event);

        deleteBtn = findViewById(R.id.btnDeleteFacilitator);
        submitButton = findViewById(R.id.btnSubmit);
        uploadedEventImage = findViewById(R.id.ivUploadedEventPic);
        uploadImage = findViewById(R.id.btnUploadEventImage);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        etEventName = findViewById(R.id.etEventName);
        etOrganizerName = findViewById(R.id.etOrganizerName);
        etOrganizerEmail = findViewById(R.id.etOrganizerEmail);
        etShortDesc = findViewById(R.id.etShortDesc);
        etDetailedDesc = findViewById(R.id.etDetailedDesc);
        btnStartDate = findViewById(R.id.btnStartDate);
        btnEndDate = findViewById(R.id.btnEndDate);

        value = getIntent().getExtras().getString("VENAME");

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(RegisterEventActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    ActivityCompat.requestPermissions(RegisterEventActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 11);
                }
            }
        });

        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // String selectedDate1 = openCalendar();


                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day



                // date picker dialog
                datePickerDialog = new DatePickerDialog(RegisterEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        // set day of month , month and year value in the edit text

                        selectedDate = Integer.toString(i2) + "/"
                                + Integer.toString(i1 + 1) + "/" + Integer.toString(i);
                        etStartDate.setText(selectedDate);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day



                // date picker dialog
                datePickerDialog = new DatePickerDialog(RegisterEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        // set day of month , month and year value in the edit text

                        selectedDate = Integer.toString(i2) + "/"
                                + Integer.toString(i1 + 1) + "/" + Integer.toString(i);
                        etEndDate.setText(selectedDate);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        /*btnSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(RegisterEventActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectPdf();
                } else {
                    ActivityCompat.requestPermissions(RegisterEventActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }

            }
        });*/

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                evName = etEventName.getText().toString();
                orgName = etOrganizerName.getText().toString();
                orgEmail = etOrganizerEmail.getText().toString();
                shortDesc = etShortDesc.getText().toString();
                detDesc = etDetailedDesc.getText().toString();
                stDate = etStartDate.getText().toString();
                endDate = etEndDate.getText().toString();

                boolean valid = validate(evName, orgName, orgEmail, shortDesc, detDesc, stDate, endDate);

                if (valid) {
                    if ((imageUri != null)) {
                        uploadFile(imageUri);
                    } else {
                        Toast.makeText(RegisterEventActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

    }

    private boolean validate (String evName, String orgName, String orgEmail, String shortDesc, String detDesc, String stDate, String endDate) {
        boolean result = false;

        if (evName.isEmpty() || orgName.isEmpty() || orgEmail.isEmpty() || shortDesc.isEmpty() || detDesc.isEmpty() || stDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }

        return result;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 11 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            Toast.makeText(this, "Please provide permission", Toast.LENGTH_SHORT).show();
        }
    }

    public void openGallery () {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    public void uploadFile (Uri uri) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading Data...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName = etEventName.getText().toString();

        StorageReference storageReference = storage.getReference();
        final StorageReference ref = storageReference.child("Event Logos").child(fileName);
        UploadTask uploadTask = ref.putFile(uri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterEventActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

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

                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    //UploadPic uploadPic = new UploadPic(fileName, downloadUri.toString());
                    Events events = new Events(evName, orgName, orgEmail, shortDesc, detDesc, stDate, endDate, value, downloadUri.toString(), firebaseAuth.getUid(),false);
                    DatabaseReference databaseReference = database.getReference("Events/" + fileName);
                    databaseReference.setValue(events).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterEventActivity.this, "File successfuly uploaded", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegisterEventActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                            }

                            progressDialog.cancel();

                        }
                    });

                    StatusTableItem statusTableItem = new StatusTableItem(evName, "N/A", false, false);
                    DatabaseReference databaseReference1 = database.getReference("StatusTable/" + firebaseAuth.getUid()+ "/" + fileName);
                    databaseReference1.setValue(statusTableItem);
                    startActivity(new Intent(RegisterEventActivity.this, StatusTable.class));
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            uploadedEventImage.setImageURI(imageUri);
        } else if(requestCode == 1 && resultCode == RESULT_OK && data != null) {
            pdfUri = data.getData();
            //tvNotification.setText("A file is selected: " + data.getData().getLastPathSegment());
        } else {
            Toast.makeText(this, "Please Select a file", Toast.LENGTH_SHORT).show();
        }

    }


}
