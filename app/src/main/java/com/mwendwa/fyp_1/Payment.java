package com.mwendwa.fyp_1;

public class Payment {
    public String transId,transDate, fullSMS, transSender, transAmount;


    public Payment() {

    }

    public Payment(String transId, String transDate, String fullSMS, String transSender, String transAmount) {
        this.transId = transId;
        this.transDate = transDate;
        this.fullSMS = fullSMS;
        this.transSender = transSender;
        this.transAmount = transAmount;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getFullSMS() {
        return fullSMS;
    }

    public void setFullSMS(String fullSMS) {
        this.fullSMS = fullSMS;
    }

    public String getTransSender() {
        return transSender;
    }

    public void setTransSender(String transSender) {
        this.transSender = transSender;
    }

    public String getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }
}
