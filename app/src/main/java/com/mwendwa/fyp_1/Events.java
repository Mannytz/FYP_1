package com.mwendwa.fyp_1;

public class Events {

    public String eName, eOrgName, eOrgEmail, eShDesc, eDesc, eStDate, eEdate, eVenue, eUrl, uid;
    public boolean passStatus;

    public Events () {

    }

    public Events(String eName, String eOrgName, String eOrgEmail, String eShDesc, String eDesc, String eStDate, String eEdate, String eVenue, String eUrl, String uid, boolean passStatus) {
        this.eName = eName;
        this.eOrgName = eOrgName;
        this.eOrgEmail = eOrgEmail;
        this.eShDesc = eShDesc;
        this.eDesc = eDesc;
        this.eStDate = eStDate;
        this.eEdate = eEdate;
        this.eVenue = eVenue;
        this.eUrl = eUrl;
        this.uid = uid;
        this.passStatus = passStatus;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String geteOrgName() {
        return eOrgName;
    }

    public void seteOrgName(String eOrgName) {
        this.eOrgName = eOrgName;
    }

    public String geteOrgEmail() {
        return eOrgEmail;
    }

    public void seteOrgEmail(String eOrgEmail) {
        this.eOrgEmail = eOrgEmail;
    }

    public String geteShDesc() {
        return eShDesc;
    }

    public void seteShDesc(String eShDesc) {
        this.eShDesc = eShDesc;
    }

    public String geteDesc() {
        return eDesc;
    }

    public void seteDesc(String eDesc) {
        this.eDesc = eDesc;
    }

    public String geteStDate() {
        return eStDate;
    }

    public void seteStDate(String eStDate) {
        this.eStDate = eStDate;
    }

    public String geteEdate() {
        return eEdate;
    }

    public void seteEdate(String eEdate) {
        this.eEdate = eEdate;
    }

    public String geteVenue() {
        return eVenue;
    }

    public void seteVenue(String eVenue) {
        this.eVenue = eVenue;
    }

    public String geteUrl() {
        return eUrl;
    }

    public void seteUrl(String eUrl) {
        this.eUrl = eUrl;
    }

    public boolean isPassStatus() {
        return passStatus;
    }

    public void setPassStatus(boolean passStatus) {
        this.passStatus = passStatus;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
