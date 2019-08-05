package com.mwendwa.fyp_1;

public class StatusTableItem {

    public String evItemName, evItemComment;
    public boolean evItemStatus, evItemAction;

    public StatusTableItem () {

    }

    public StatusTableItem(String evItemName, String evItemComment, boolean evItemStatus, boolean evItemAction) {
        this.evItemName = evItemName;
        this.evItemComment = evItemComment;
        this.evItemStatus = evItemStatus;
        this.evItemAction = evItemAction;
    }

    public String getEvItemName() {
        return evItemName;
    }

    public void setEvItemName(String evItemName) {
        this.evItemName = evItemName;
    }

    public String getEvItemComment() {
        return evItemComment;
    }

    public void setEvItemComment(String evItemComment) {
        this.evItemComment = evItemComment;
    }

    public boolean isEvItemStatus() {
        return evItemStatus;
    }

    public void setEvItemStatus(boolean evItemStatus) {
        this.evItemStatus = evItemStatus;
    }

    public boolean isEvItemAction() {
        return evItemAction;
    }

    public void setEvItemAction(boolean evItemAction) {
        this.evItemAction = evItemAction;
    }
}
