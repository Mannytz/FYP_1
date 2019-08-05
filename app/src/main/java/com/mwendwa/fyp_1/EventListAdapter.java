package com.mwendwa.fyp_1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class EventListAdapter  extends RecyclerView.Adapter<EventListAdapter.ViewHolder>{

    private OnEventListener mOnEventListener;
    private ArrayList<Events> events = new ArrayList<>();

    public EventListAdapter(ArrayList<Events> events, OnEventListener onEventListener) {
        this.events = events;
        this.mOnEventListener = onEventListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_list_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view, mOnEventListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

           Picasso.get().load(events.get(i).geteUrl()).fit().into(viewHolder.image);

           viewHolder.eventTitle.setText(events.get(i).geteName());
           viewHolder.eventDate.setText(events.get(i).geteStDate() + " - " + events.get(i).geteEdate());
           viewHolder.description.setText(events.get(i).geteShDesc());


    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        CircleImageView image;
        TextView eventTitle;
        TextView description;
        TextView eventDate;
        RelativeLayout parentLayout;
        OnEventListener onEventListener;

        public ViewHolder(@NonNull View itemView, OnEventListener onEventListener) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_event);
            eventTitle = itemView.findViewById(R.id.tv_title);
            description = itemView.findViewById(R.id.tv_description);
            eventDate = itemView.findViewById(R.id.tv_date);
            parentLayout = itemView.findViewById(R.id.parent_layout);

            this.onEventListener = onEventListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onEventListener.OnEventClick(getAdapterPosition());
        }
    }

    public interface OnEventListener {
        void OnEventClick(int position);
    }
}
