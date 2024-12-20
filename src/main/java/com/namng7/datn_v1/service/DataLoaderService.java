package com.namng7.datn_v1.service;

import com.namng7.datn_v1.model.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;
import java.util.List;

public interface DataLoaderService extends UserDetailsService {
    Map<String, User> loadAllUser();
    Map<Long, User> mapUserByUserID();
    Map<Long, Company> loadAllCompany();
    List<GamecodeModel> loadAllGamecodeModel();
    Map<Long, PackageConfig> loadAllPackageConfig();
    Map<Long, WebserviceConfig> loadAllWebserviceConfig();
    Map<String, String> loadAllConfiguration();

}
