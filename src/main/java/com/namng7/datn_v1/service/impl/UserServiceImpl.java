package com.namng7.datn_v1.service.impl;

import com.namng7.datn_v1.model.User;
import com.namng7.datn_v1.repository.UserRepository;
import com.namng7.datn_v1.service.UserService;
import com.namng7.datn_v1.util.CacheManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User loginUser(String username, String password) {
        User user = CacheManager.Users.ALLUSERS.get(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            logger.error("no user map to username" + username );
            throw new RuntimeException("Invalid username or password");
        }
    }

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
