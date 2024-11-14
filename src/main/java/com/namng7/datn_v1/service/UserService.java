package com.namng7.datn_v1.service;

import com.namng7.datn_v1.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

public interface UserService extends UserDetailsService {
    User registerUser(User user);
    User loginUser(String username, String password);
    Map<String, User> loadAllUser();
}
