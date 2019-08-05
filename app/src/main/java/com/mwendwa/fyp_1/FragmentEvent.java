package com.mwendwa.fyp_1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mwendwa.fyp_1.R;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class FragmentEvent extends Fragment {

    View view;
    private String myStr;
    private FirebaseDatabase database;
    private TextView tvEventName, tvEventDates, tvEventVenue, tvOrganizerName;
    private ArrayList<Events> eveList = new ArrayList<>();
    private Button register;

    public FragmentEvent() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.eve_fragment, container, false);

        myStr = getArguments().getString("mEVENTNAME");

        tvEventName = view.findViewById(R.id.tvTabLayoutEventName);
        tvEventDates = view.findViewById(R.id.tvEventDates);
        tvEventVenue = view.findViewById(R.id.tvEventVenue);
        tvOrganizerName = view.findViewById(R.id.tvOrganizerName);
        register = view.findViewById(R.id.btnUserRegister);

        tvEventName.setText(myStr);
        database = FirebaseDatabase.getInstance();

        final DatabaseReference databaseReference = database.getReference("Events/" + myStr);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Events events = dataSnapshot.getValue(Events.class);
                tvEventDates.setText(events.geteStDate() + " to " + events.geteEdate());
                tvEventVenue.setText(events.geteVenue());
                tvOrganizerName.setText(events.geteOrgName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText muserEmail = new EditText(getContext());
                final EditText muserNumber = new EditText(getContext());
                final EditText muserName = new EditText(getContext());
                muserEmail.setHint("Enter your email");
                muserNumber.setHint("Enter your phone Number");
                muserName.setHint("Enter your Name");
                layout.addView(muserName);
                layout.addView(muserNumber);
                layout.addView(muserEmail);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Register for Event");
                builder.setMessage("Please Enter your Details below");
                builder.setView(layout);

                builder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       DatabaseReference ref = database.getReference("Attendees/" + myStr);
                       String email = muserEmail.getText().toString();
                       String number = muserNumber.getText().toString();
                       String name = muserName.getText().toString();
                       
                       if (name.isEmpty() || number.isEmpty() || email.isEmpty()) {
                           Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show(); 
                       } else {
                           Attendee attendee = new Attendee(name, number, email);
                           ref.setValue(attendee);
                           Toast.makeText(getContext(), "Successfully registered. \uD83D\uDE0A", Toast.LENGTH_SHORT).show();
                       }
                       
                       
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        return view;
    }
    
}
