package com.namng7.datn_v1.service;

public interface AesEncryptionService {
    String encrypt(String input) throws Exception;
    String decrypt(String input) throws Exception;
}
