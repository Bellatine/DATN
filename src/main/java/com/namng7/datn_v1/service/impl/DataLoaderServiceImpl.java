package com.namng7.datn_v1.service.impl;

import com.namng7.datn_v1.model.Company;
import com.namng7.datn_v1.model.User;
import com.namng7.datn_v1.repository.CompanyRepository;
import com.namng7.datn_v1.repository.UserRepository;
import com.namng7.datn_v1.service.CompanyService;
import com.namng7.datn_v1.service.DataLoaderService;
import com.namng7.datn_v1.util.UserUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataLoaderServiceImpl implements DataLoaderService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private static StringBuilder log = new StringBuilder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

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
    public Map<Long, Company> loadAllCompany() {
        List<Company> listAllCompany = companyRepository.findAll();
        Map<Long, Company> mapAllCompany = new HashMap<>();
        if(listAllCompany == null || listAllCompany.size() == 0){
            logger.warn("Load data fail!");
            return null;
        }
        for(Company company : listAllCompany){
            mapAllCompany.put(company.getUser_id(), company);
        }
        return mapAllCompany;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
