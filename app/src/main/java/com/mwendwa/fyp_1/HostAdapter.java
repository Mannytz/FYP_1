package com.mwendwa.fyp_1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class HostAdapter extends RecyclerView.Adapter<HostAdapter.HostViewHolder> {

    private ArrayList<String> names = new ArrayList<>();
    private OnHostListener mOnHostListener;

    public HostAdapter (ArrayList<String> name, OnHostListener onHostListener) {
        this.names = name;
        this.mOnHostListener = onHostListener;
    }

    @NonNull
    @Override
    public HostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.venue_list_item, viewGroup, false);
        HostViewHolder hostViewHolder = new HostViewHolder(view, mOnHostListener);
        return hostViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HostViewHolder hostViewHolder, int i) {
        hostViewHolder.vName.setText(names.get(i));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class HostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView vName;
        OnHostListener onHostListener;

        public HostViewHolder(@NonNull View itemView, OnHostListener onHostListener) {
            super(itemView);
            vName = itemView.findViewById(R.id.tvLvname);
            this.onHostListener = onHostListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onHostListener.onVenueClick(getAdapterPosition());
        }
    }

    public interface OnHostListener {
        void onVenueClick(int position);
    }
}
