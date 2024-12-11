package com.namng7.datn_v1.service.impl;

import com.namng7.datn_v1.model.User;
import com.namng7.datn_v1.repository.UserRepository;
import com.namng7.datn_v1.service.DataLoaderService;
import com.namng7.datn_v1.util.UserUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataLoaderServiceImpl implements DataLoaderService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private static StringBuilder log = new StringBuilder();

    @Autowired
    private UserRepository userRepository;

    @Override
    public Map<String, User> loadAllUser() {
        List<User> listUsers = userRepository.findAll();
        Map<String, User> mapUsers = new HashMap<>();
        if(listUsers == null || listUsers.isEmpty()){
            logger.error("Load user fail! ");
            return null;
        }
        for(User user : listUsers){
            mapUsers.put(user.getUsername(), user);
        }
        return mapUsers;
    }
}
