package com.namng7.datn_v1.service;

public interface PasswordEncoderService {
    String encodePassword(String password);
    boolean matches(String rawPassword, String encodedPassword);
}
