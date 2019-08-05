package com.mwendwa.fyp_1;

public class Attendee {

    public String attendeeName, attendeeNumber,attendeeEmail;

    public Attendee() {
    }

    public Attendee(String attendeeName, String attendeeNumber, String attendeeEmail) {
        this.attendeeName = attendeeName;
        this.attendeeNumber = attendeeNumber;
        this.attendeeEmail = attendeeEmail;
    }

    public String getAttendeeName() {
        return attendeeName;
    }

    public void setAttendeeName(String attendeeName) {
        this.attendeeName = attendeeName;
    }

    public String getAttendeeNumber() {
        return attendeeNumber;
    }

    public void setAttendeeNumber(String attendeeNumber) {
        this.attendeeNumber = attendeeNumber;
    }

    public String getAttendeeEmail() {
        return attendeeEmail;
    }

    public void setAttendeeEmail(String attendeeEmail) {
        this.attendeeEmail = attendeeEmail;
    }
}
