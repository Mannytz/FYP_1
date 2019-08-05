package com.mwendwa.fyp_1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentDetails extends Fragment {

    View view;
    private String myString;
    private FirebaseDatabase database;
    private TextView eventDetails;

    public FragmentDetails() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.details_fragment, container, false);

        eventDetails = view.findViewById(R.id.tvEventDetails);
        myString = getArguments().getString("mEVENTNAME");

        database = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = database.getReference("Events/" + myString);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Events events = dataSnapshot.getValue(Events.class);
                eventDetails.setText(events.geteDesc());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
