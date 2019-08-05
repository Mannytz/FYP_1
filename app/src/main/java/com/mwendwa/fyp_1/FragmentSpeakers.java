package com.mwendwa.fyp_1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FragmentSpeakers extends Fragment {

    View view;
    private RecyclerView recyclerView;
    private ArrayList<String> labels;

    public FragmentSpeakers() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.speakers_fragment, container, false);
        recyclerView = view.findViewById(R.id.rv_main);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<SectionModel> sectionModelArrayList = new ArrayList<>();

        labels = new ArrayList<>();
        labels.add("Exhibitionists/Speakers");
        labels.add("Facilitators");

        for (int i = 0; i < labels.size(); i++) {
            ArrayList<String> itemArrayList = new ArrayList<>();
            //for loop for items
            for (int j = 1; j <= 10; j++) {
                itemArrayList.add("Item " + j);
            }

            //add the section and items to array list
            sectionModelArrayList.add(new SectionModel(labels.get(i), itemArrayList));
        }

        SectionRecyclerViewAdapter sectionRecyclerViewAdapter = new SectionRecyclerViewAdapter(getContext(), sectionModelArrayList);
        recyclerView.setAdapter(sectionRecyclerViewAdapter);


        return view;
    }
}
