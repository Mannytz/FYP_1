package com.mwendwa.fyp_1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SectionRecyclerViewAdapter extends RecyclerView.Adapter<SectionRecyclerViewAdapter.SectionViewHolder> {
    private Context mContext;
    private ArrayList<SectionModel> sectionModelArrayList;

    public SectionRecyclerViewAdapter(Context mContext, ArrayList<SectionModel> sectionModelArrayList) {
        this.mContext = mContext;
        this.sectionModelArrayList = sectionModelArrayList;
    }

    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.section_layout, viewGroup, false);
        SectionViewHolder holder = new SectionViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder sectionViewHolder, int i) {
        sectionViewHolder.sectionLabel.setText(sectionModelArrayList.get(i).getSectionLabel());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        sectionViewHolder.recyclerView.setLayoutManager(linearLayoutManager);

        ItemRecyclerViewAdapter itemRecyclerViewAdapter = new ItemRecyclerViewAdapter(mContext, sectionModelArrayList.get(i).getItemList());
        sectionViewHolder.recyclerView.setAdapter(itemRecyclerViewAdapter);
    }

    @Override
    public int getItemCount() {
        return sectionModelArrayList.size();
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder {
        private TextView sectionLabel;
        private RecyclerView recyclerView;

        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);

            sectionLabel = itemView.findViewById(R.id.tv_section_label);
            recyclerView = itemView.findViewById(R.id.rv_section);
        }
    }
}
