package com.namng7.datn_v1.service;

import com.namng7.datn_v1.model.User;
import com.namng7.datn_v1.object.ProcessRecord;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

public interface UserService extends UserDetailsService {
    ProcessRecord registerUser(ProcessRecord record);
    ProcessRecord loginUser(ProcessRecord record);
    ProcessRecord updateInfor(ProcessRecord record);
    Map<String, User> loadAllUser();
}
