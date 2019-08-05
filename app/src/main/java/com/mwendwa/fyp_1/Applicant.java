package com.mwendwa.fyp_1;

public class Applicant {

    public String applicantName, applicantOrganisation;

    public Applicant () {

    }

    public Applicant(String applicantName, String applicantOrganisation) {
        this.applicantName = applicantName;
        this.applicantOrganisation = applicantOrganisation;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantOrganisation() {
        return applicantOrganisation;
    }

    public void setApplicantOrganisation(String applicantOrganisation) {
        this.applicantOrganisation = applicantOrganisation;
    }
}
