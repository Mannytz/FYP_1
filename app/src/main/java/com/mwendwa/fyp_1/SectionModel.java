package com.mwendwa.fyp_1;

import java.util.ArrayList;

public class SectionModel {
    private String sectionLabel;
    private ArrayList<String> itemList;

    public SectionModel(String sectionLabel, ArrayList<String> itemList) {
        this.sectionLabel = sectionLabel;
        this.itemList = itemList;
    }

    public String getSectionLabel() {
        return sectionLabel;
    }

    public ArrayList<String> getItemList() {
        return itemList;
    }
}
