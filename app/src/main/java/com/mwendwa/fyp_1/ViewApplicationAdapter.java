package com.mwendwa.fyp_1;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewApplicationAdapter extends RecyclerView.Adapter<ViewApplicationAdapter.ApplicationViewHolder> {

    private ArrayList<Events> events = new ArrayList<>();
    private onViewApplicationListener mOnViewAppListener;
    private FirebaseDatabase database;

    public ViewApplicationAdapter (ArrayList<Events> events, onViewApplicationListener onViewApplicationListener) {
        this.events = events;
        this.mOnViewAppListener = onViewApplicationListener;
    }

    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_applications_item, viewGroup, false);
        ApplicationViewHolder applicationViewHolder = new ApplicationViewHolder(view, mOnViewAppListener);
        return applicationViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ApplicationViewHolder applicationViewHolder, int i) {
        applicationViewHolder.name.setText(events.get(i).geteName());
        if (events.get(i).isPassStatus()) {
            applicationViewHolder.status.setText("Viewed");
            applicationViewHolder.status.setTextColor(Color.parseColor("#90EE90"));
        } else {
            applicationViewHolder.status.setText("Not paid");
            applicationViewHolder.status.setTextColor(Color.parseColor("#ff0000"));
        }


        if (events.get(i).getUid() != null) {

            database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("StatusTable/" + events.get(i).getUid() + "/" + events.get(i).geteName());
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    StatusTableItem statusTableItem = dataSnapshot.getValue(StatusTableItem.class);
                    if (statusTableItem.isEvItemStatus() == true) {
                        applicationViewHolder.status.setText("Viewed");
                        applicationViewHolder.status.setTextColor(Color.parseColor("#90EE90"));
                    } else {
                        applicationViewHolder.status.setText("Not viewed");
                        applicationViewHolder.status.setTextColor(Color.parseColor("#ff0000"));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ApplicationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, status;
        onViewApplicationListener onViewApplicationListener;

        public ApplicationViewHolder(@NonNull View itemView, onViewApplicationListener onViewApplicationListener) {
            super(itemView);
            this.name = itemView.findViewById(R.id.tvViewName);
            this.status = itemView.findViewById(R.id.tvViewStatus);
            this.onViewApplicationListener = onViewApplicationListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
                onViewApplicationListener.onApplicationClick(getAdapterPosition());
        }
    }


    public interface onViewApplicationListener {
        void onApplicationClick(int position);
    }
}
