package com.mwendwa.fyp_1;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ApplicationDetailsActivity extends AppCompatActivity {

    private String value;
    private FirebaseDatabase database;
    private TextView name, organiser, dates, brief, full;
    private EditText comment;
    private ImageView image;
    private Button accept, reject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_details);

        value = getIntent().getExtras().getString("NAME");
        database = FirebaseDatabase.getInstance();
        name = findViewById(R.id.tvDetEventName);
        organiser = findViewById(R.id.tvDetEventOrgainser);
        dates = findViewById(R.id.tvDetEventDates);
        brief = findViewById(R.id.tvDetBriefDescription);
        full = findViewById(R.id.tvDetFullDesc);
        image = findViewById(R.id.ivDetImage);
        accept = findViewById(R.id.btnAccept);
        reject = findViewById(R.id.btnReject);
        comment = findViewById(R.id.etDetComment);

        DatabaseReference ref = database.getReference("Events/" + value);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Events events = dataSnapshot.getValue(Events.class);
                name.setText(events.geteName());
                organiser.setText(events.geteOrgName() + "  " + events.geteOrgEmail());
                dates.setText(events.geteStDate() + " - " + events.geteEdate());
                brief.setText(events.geteShDesc());
                full.setText(events.geteDesc());
                Picasso.get().load(events.geteUrl()).fit().into(image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DatabaseReference ref1 = database.getReference("Events/" + value);
                ref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Events events = dataSnapshot.getValue(Events.class);
                        ref1.setValue(events);

                        if (events.getUid() != null) {
                            final DatabaseReference ref2 = database.getReference("StatusTable/" + events.getUid() + "/" + value);
                            ref2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    StatusTableItem statusTableItem = dataSnapshot.getValue(StatusTableItem.class);
                                    statusTableItem.setEvItemAction(true);
                                    statusTableItem.setEvItemStatus(true);
                                    statusTableItem.setEvItemComment(comment.getText().toString());
                                    ref2.setValue(statusTableItem);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref3 = database.getReference("Events/" + value);
                ref3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Events events = dataSnapshot.getValue(Events.class);

                        if (events.getUid() != null) {
                            final DatabaseReference ref4 = database.getReference("StatusTable/" + events.getUid() + "/" + value);
                            ref4.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    StatusTableItem statusTableItem = dataSnapshot.getValue(StatusTableItem.class);
                                    statusTableItem.setEvItemAction(false);
                                    statusTableItem.setEvItemStatus(true);
                                    statusTableItem.setEvItemComment(comment.getText().toString());
                                    ref4.setValue(statusTableItem);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }



}
