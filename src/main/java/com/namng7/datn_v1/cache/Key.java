package com.namng7.datn_v1.cache;

import jakarta.servlet.http.PushBuilder;

public class Key {
    public static class Status{
        public static final int ACTIVE = 1;
        public static final int INACTIVE = 0;
    }
    public static  class Role{
        public static final int ADMIN = 1;
        public static final int VIEWER = 2;
    }

    public static class ErrorCode{
        public static final int SYSTEM_FAULT = -1;
        public static final int SUCCESS = 0;
        public static final int INVALID_USER = 1;
    }

    public static class Message{
        public static final String NULL_MESSAGE = "Chưa cấu hình thông điệp";
        public static final String SYSTEM_FAULT = "SYSTEM_FAULT";
        public static final String USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS";
        public static final String INVALID_USER = "INVALID_USER";
        public static final String REGISTER_USER_SUCCESS = "REGISTER_USER_SUCCESS";
    }

}
