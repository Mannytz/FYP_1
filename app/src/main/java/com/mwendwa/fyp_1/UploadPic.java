package com.mwendwa.fyp_1;

public class UploadPic {
    public String eventUploadName;
    public String eventUploadUrl;

    public UploadPic () {

    }

    public UploadPic(String eventUploadName, String eventUploadUrl) {
        this.eventUploadName = eventUploadName;
        this.eventUploadUrl = eventUploadUrl;
    }

    public String getEventUploadName() {
        return eventUploadName;
    }

    public void setEventUploadName(String eventUploadName) {
        this.eventUploadName = eventUploadName;
    }

    public String getEventUploadUrl() {
        return eventUploadUrl;
    }

    public void setEventUploadUrl(String eventUploadUrl) {
        this.eventUploadUrl = eventUploadUrl;
    }
}
