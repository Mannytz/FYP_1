package com.mwendwa.fyp_1;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StatusTable extends AppCompatActivity {

    private String value;
    private ArrayList<StatusTableItem> statusTableItems;
    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_table);

        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.tableRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        statusTableItems = new ArrayList<StatusTableItem>();

        Toast.makeText(this, firebaseAuth.getUid(), Toast.LENGTH_SHORT).show();

        database.getReference("StatusTable/" + firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    StatusTableItem statusTableItem = snapshot.getValue(StatusTableItem.class);
                    statusTableItems.add(statusTableItem);
                }

                Toast.makeText(StatusTable.this, Integer.toString(statusTableItems.size()), Toast.LENGTH_SHORT).show();
                StatusTableAdapter statusTableAdapter = new StatusTableAdapter(statusTableItems, StatusTable.this);
                recyclerView.setAdapter(statusTableAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(StatusTable.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
