package com.mwendwa.fyp_1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class VenueActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView name, location, capacity, facilities;
    private String value;
    private Button book;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue);

        imageView = findViewById(R.id.ivVenuePicture);
        name = findViewById(R.id.tvVenueName);
        location = findViewById(R.id.tvVenueLocation);
        capacity = findViewById(R.id.tvVenueCapacity);
        facilities = findViewById(R.id.tvVenueFacilitieDetails);
        book = findViewById(R.id.btnBookVenue);
        database = FirebaseDatabase.getInstance();

        value = getIntent().getExtras().getString("VENUE");

        DatabaseReference databaseReference = database.getReference("Venues/" + value);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Venue venue = dataSnapshot.getValue(Venue.class);
                Picasso.get().load(venue.getVenueUrl()).rotate(90).fit().into(imageView);
                name.setText(value);
                location.setText("Location: " + venue.getVenueLocation());
                capacity.setText("Capacity: " + venue.getVenueCapacity());
                facilities.setText(venue.getVenueEqpt());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(VenueActivity.this, RegisterEventActivity.class);
                intent.putExtra("VENAME", value);
                startActivity(intent);
            }
        });


    }
}
