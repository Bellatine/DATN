package com.namng7.datn_v1.service;

import com.namng7.datn_v1.model.Company;
import com.namng7.datn_v1.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

public interface DataLoaderService extends UserDetailsService {
    Map<String, User> loadAllUser();
    Map<Long, Company> loadAllCompany();

}
