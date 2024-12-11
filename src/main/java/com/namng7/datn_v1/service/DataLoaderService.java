package com.namng7.datn_v1.service;

import com.namng7.datn_v1.model.User;

import java.util.Map;

public interface DataLoaderService {
    Map<String, User> loadAllUser();

}
