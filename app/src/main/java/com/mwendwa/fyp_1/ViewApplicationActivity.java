package com.mwendwa.fyp_1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewApplicationActivity extends AppCompatActivity implements ViewApplicationAdapter.onViewApplicationListener {

    private RecyclerView recyclerView;
    private ArrayList<Events> events;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_application);

        recyclerView = findViewById(R.id.rvViewApplications);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        events = new ArrayList<Events>();

        database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference("Events");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Events events1 = snapshot.getValue(Events.class);
                    events.add(events1);
                }

                ViewApplicationAdapter viewApplicationAdapter = new ViewApplicationAdapter(events, ViewApplicationActivity.this);
                recyclerView.setAdapter(viewApplicationAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onApplicationClick(int position) {
        Intent intent = new Intent(ViewApplicationActivity.this, ApplicationDetailsActivity.class);
        intent.putExtra("NAME", events.get(position).geteName());
        startActivity(intent);
    }
}
