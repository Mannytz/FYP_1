package com.mwendwa.fyp_1;

public class Venue {

    public String venueName, venueCapacity, venueLocation, venueUrl, venueEqpt;

    public Venue () {

    }

    public Venue(String venueName, String venueCapacity, String venueLocation, String venueUrl, String venueEqpt) {
        this.venueName = venueName;
        this.venueCapacity = venueCapacity;
        this.venueLocation = venueLocation;
        this.venueUrl = venueUrl;
        this.venueEqpt = venueEqpt;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueCapacity() {
        return venueCapacity;
    }

    public void setVenueCapacity(String venueCapacity) {
        this.venueCapacity = venueCapacity;
    }

    public String getVenueLocation() {
        return venueLocation;
    }

    public void setVenueLocation(String venueLocation) {
        this.venueLocation = venueLocation;
    }

    public String getVenueUrl() {
        return venueUrl;
    }

    public void setVenueUrl(String venueUrl) {
        this.venueUrl = venueUrl;
    }

    public String getVenueEqpt() {
        return venueEqpt;
    }

    public void setVenueEqpt(String venueEqpt) {
        this.venueEqpt = venueEqpt;
    }
}
