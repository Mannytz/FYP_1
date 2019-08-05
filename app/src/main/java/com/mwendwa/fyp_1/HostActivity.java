package com.mwendwa.fyp_1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import okhttp3.internal.cache.DiskLruCache;

public class HostActivity extends AppCompatActivity implements HostAdapter.OnHostListener{

    private RecyclerView recyclerView;
    private ArrayList<String> name;
    private FirebaseDatabase database;
    private Button status, refresh;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        status = findViewById(R.id.btnApplicationStatus);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        recyclerView = findViewById(R.id.rv_venue_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        name = new ArrayList<String>();

        DatabaseReference ref = database.getReference("Venues");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Venue venue = snapshot.getValue(Venue.class);
                    name.add(venue.getVenueName());
                }

                HostAdapter hostAdapter = new HostAdapter(name, HostActivity.this);
                recyclerView.setAdapter(hostAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HostActivity.this, StatusTable.class));
            }
        });


    }

    @Override
    public void onVenueClick(int position) {
        Intent intent = new Intent(HostActivity.this, VenueActivity.class);
        intent.putExtra("VENUE", name.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:
                auth.signOut();
                finish();
                startActivity(new Intent(HostActivity.this, LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
