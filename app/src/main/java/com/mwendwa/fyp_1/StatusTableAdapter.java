package com.mwendwa.fyp_1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StatusTableAdapter extends RecyclerView.Adapter<StatusTableAdapter.StatusTableViewHolder>{
    private ArrayList<StatusTableItem> tables = new ArrayList<>();
    private Context mContext;
    private ArrayList<String> IDs = new ArrayList<>();

    public class StatusTableViewHolder extends RecyclerView.ViewHolder {

        TextView evName, comment, status;
        Button payUp;

        public StatusTableViewHolder(@NonNull View itemView) {
            super(itemView);
            evName = itemView.findViewById(R.id.tvStatusEventName);
            comment = itemView.findViewById(R.id.statusComments);
            status = itemView.findViewById(R.id.statusColor);
            payUp = itemView.findViewById(R.id.btnPay4Event);
        }
    }

    public StatusTableAdapter(ArrayList<StatusTableItem> tables, Context context) {
        this.tables = tables;
        this.mContext = context;
    }

    @NonNull
    @Override
    public StatusTableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.status_table_element, viewGroup, false);
        StatusTableViewHolder holder = new StatusTableViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final StatusTableViewHolder statusTableViewHolder, int i) {

        statusTableViewHolder.evName.setText(tables.get(i).getEvItemName());
        statusTableViewHolder.comment.setText(tables.get(i).getEvItemComment());

        if (tables.get(i).isEvItemStatus()) {
            statusTableViewHolder.status.setBackgroundColor(Color.GREEN);
        } else {
            statusTableViewHolder.status.setBackgroundColor(Color.RED);
        }

        if (tables.get(i).isEvItemAction()) {
            statusTableViewHolder.payUp.setEnabled(true);
            statusTableViewHolder.payUp.setVisibility(View.VISIBLE);
        } else {
            statusTableViewHolder.payUp.setEnabled(false);
            statusTableViewHolder.payUp.setVisibility(View.GONE);
        }


        statusTableViewHolder.payUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final EditText editText = new EditText(mContext);
                editText.setHint("Reference Number: Eg 7H74NTLPIR");
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Payment");
                builder.setMessage("To pay, send money to 0759205941. After paying insert obtained transaction ID to complete payment.");
                builder.setView(editText);

                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final FirebaseDatabase database;
                        database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference("Payments");
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                    Payment payment = snapshot.getValue(Payment.class);
                                    IDs.add(payment.getTransId());
                                }

                                if (IDs.contains(editText.getText().toString())) {
                                    Toast.makeText(mContext, "There's a match", Toast.LENGTH_LONG).show();
                                    final DatabaseReference ref2 = database.getReference("Events/" + statusTableViewHolder.evName.getText());
                                    ref2.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Events events = dataSnapshot.getValue(Events.class);
                                            events.setPassStatus(true);
                                            ref2.setValue(events);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                } else {
                                    Toast.makeText(mContext, "No match", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return tables.size();
    }
}
