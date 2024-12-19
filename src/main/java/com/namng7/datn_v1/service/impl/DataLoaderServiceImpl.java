package com.namng7.datn_v1.service.impl;

import com.namng7.datn_v1.model.*;
import com.namng7.datn_v1.repository.*;
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

    @Autowired
    private GamecodeModelRepsitory gamecodeModelRepsitory;

    @Autowired
    private PackageConfigRepository packageConfigRepository;

    @Autowired
    private WebserviceConfigRepository webserviceConfigRepository;

    @Autowired
    private ConfigurationRepository configurationRepository;

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
    public Map<Long, User> mapUserByUserID() {
        List<User> listUsers = userRepository.findAll();
        Map<Long, User> mapUsers = new HashMap<>();
        if(listUsers == null || listUsers.isEmpty()){
            logger.error("Load user fail! ");
            return null;
        }
        for(User user : listUsers){
            mapUsers.put(user.getId(), user);
        }
        return mapUsers;
    }

    @Override
    public Map<Long, Company> loadAllCompany() {
        List<Company> listAllCompany = companyRepository.findAll();
        Map<Long, Company> mapAllCompany = new HashMap<>();
        if(listAllCompany == null || listAllCompany.isEmpty()){
            logger.warn("Load company fail!");
            return null;
        }
        for(Company company : listAllCompany){
            mapAllCompany.put(company.getUser_id(), company);
        }
        return mapAllCompany;
    }

    @Override
    public List<GamecodeModel> loadAllGamecodeModel() {
        List<GamecodeModel> listAllGamecodeModel = gamecodeModelRepsitory.findAll();
        if(listAllGamecodeModel == null || listAllGamecodeModel.isEmpty()) {
            logger.warn("Load gamecodeModel fail!");
            return null;
        }
        return listAllGamecodeModel;
    }

    @Override
    public Map<Long, PackageConfig> loadAllPackageConfig() {
        List<PackageConfig> listAllPackageConfig = packageConfigRepository.findAll();
        Map<Long, PackageConfig> mapAllPackageConfig = new HashMap<>();
        if(listAllPackageConfig == null || listAllPackageConfig.isEmpty()) {
            logger.warn("Load packageConfig fail!");
            return null;
        }
        for(PackageConfig packageConfig : listAllPackageConfig){
            mapAllPackageConfig.put(packageConfig.getId(), packageConfig);
        }
        return mapAllPackageConfig;
    }

    @Override
    public Map<Long, WebserviceConfig> loadAllWebserviceConfig() {
        List<WebserviceConfig> listAllWebserviceConfig = webserviceConfigRepository.findAll();
        Map<Long, WebserviceConfig> mapAllWsConfig = new HashMap<>();
        if(listAllWebserviceConfig == null || listAllWebserviceConfig.isEmpty()) {
            logger.warn("Load webserviceConfig fail!");
            return null;
        }
        for(WebserviceConfig webserviceConfig : listAllWebserviceConfig){
            mapAllWsConfig.put(webserviceConfig.getId(), webserviceConfig);
        }
        return mapAllWsConfig;
    }

    @Override
    public Map<String, String> loadAllConfiguration() {
        List<Configuration> listAllConfiguration = configurationRepository.findAll();
        Map<String, String> mapAllConfig = new HashMap<>();
        if(listAllConfiguration == null || listAllConfiguration.isEmpty()){
            logger.warn("Load configuration fail!");
            return null;
        }
        for(Configuration configuration : listAllConfiguration){
            mapAllConfig.put(configuration.getKey(), configuration.getContent());
        }
        return mapAllConfig;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
