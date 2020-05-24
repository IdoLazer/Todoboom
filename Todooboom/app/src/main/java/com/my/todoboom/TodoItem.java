package com.my.todoboom;

import java.util.Calendar;
import java.util.Date;

public class TodoItem {
    private String content;
    private Date creationTimestamp;
    private Date lastEditTimestamp;
    private String id;
    private boolean isDone;

    public TodoItem() {

    }

    public TodoItem(String content, boolean isDone) {
        this.content = content;
        this.isDone = isDone;
        this.creationTimestamp = Calendar.getInstance().getTime();
        this.lastEditTimestamp = Calendar.getInstance().getTime();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        lastEditTimestamp = Calendar.getInstance().getTime();
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
        lastEditTimestamp = Calendar.getInstance().getTime();
    }

    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    public Date getLastEditTimestamp() {
        return lastEditTimestamp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
