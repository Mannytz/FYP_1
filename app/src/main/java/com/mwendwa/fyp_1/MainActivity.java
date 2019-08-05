package com.mwendwa.fyp_1;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EventListAdapter.OnEventListener {

    private DrawerLayout drawerLayout;
    private FirebaseDatabase database;
    private FirebaseAuth auth;

    private static final String TAG = "MainActivity";
    private ArrayList<Events> eventsListMain;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        recyclerView = findViewById(R.id.rv_events);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventsListMain = new ArrayList<Events>();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        database.getReference("Events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Events upload = snapshot.getValue(Events.class);
                    eventsListMain.add(upload);
                }

                for (int i = 0; i < eventsListMain.size(); i++) {
                    if (eventsListMain.get(i).isPassStatus()) {

                    } else {
                        eventsListMain.remove(i);
                    }
                }

                EventListAdapter adapter = new EventListAdapter(eventsListMain,MainActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()){
                    case R.id.nav_admin:
                        if (auth != null) {
                            auth.signOut();
                        }
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        break;
                    case R.id.nav_apply:
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        break;
                    case R.id.nav_about:
                        startActivity(new Intent(MainActivity.this, About.class));
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home :
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnEventClick(int position) {
        Intent intent = new Intent(MainActivity.this, EventActivity.class);
        intent.putExtra("EVENTNAME", eventsListMain.get(position).geteName());

        startActivity(intent);
    }
}
