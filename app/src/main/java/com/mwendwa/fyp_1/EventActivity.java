package com.mwendwa.fyp_1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EventActivity  extends AppCompatActivity {

    private static final String TAG = "EventActivity";
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private String value;
    private ImageView eventLogo;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);

        Log.d(TAG, "onCreate: started");

        tabLayout = findViewById(R.id.tabLayout_id);
        appBarLayout = findViewById(R.id.appbarid);
        viewPager = findViewById(R.id.viewpageLayout_id);
        eventLogo = findViewById(R.id.ivEventLogo);

        database = FirebaseDatabase.getInstance();

        value = getIntent().getExtras().getString("EVENTNAME");

        DatabaseReference databaseReference = database.getReference("Events/" + value);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Events events = dataSnapshot.getValue(Events.class);
                Picasso.get().load(events.geteUrl()).fit().into(eventLogo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Bundle bundle = new Bundle();
        bundle.putString("mEVENTNAME", value);

        FragmentEvent fragmentEvent = new FragmentEvent();
        fragmentEvent.setArguments(bundle);

        FragmentDetails fragmentDetails = new FragmentDetails();
        fragmentDetails.setArguments(bundle);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(fragmentEvent, "Welcome");
        adapter.addFragment(fragmentDetails, "Details");
        //adapter.addFragment(new FragmentSpeakers(), "Speakers");

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);


    }
}
