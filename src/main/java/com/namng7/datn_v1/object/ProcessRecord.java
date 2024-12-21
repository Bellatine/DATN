package com.namng7.datn_v1.object;

import com.namng7.datn_v1.model.User;
import lombok.Data;

@Data
public class ProcessRecord {
    private int errorCode;
    private User user;
    private String message;
    private Object object;

    public ProcessRecord(){

    }
    public ProcessRecord(User user) {
        this.user = user;
    }
}
