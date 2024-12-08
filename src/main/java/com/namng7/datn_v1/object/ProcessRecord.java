package com.namng7.datn_v1.object;

import com.namng7.datn_v1.model.User;

public class ProcessRecord {
    private int errorCode;
    private User user;
    private String message;

    public ProcessRecord(){

    }
    public ProcessRecord(User user) {
        this.user = user;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
