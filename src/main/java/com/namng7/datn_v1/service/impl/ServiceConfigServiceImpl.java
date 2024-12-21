package com.namng7.datn_v1.service.impl;

import com.namng7.datn_v1.cache.CacheManager;
import com.namng7.datn_v1.cache.Key;
import com.namng7.datn_v1.model.Company;
import com.namng7.datn_v1.model.PackageConfig;
import com.namng7.datn_v1.model.ServiceConfig;
import com.namng7.datn_v1.object.ProcessRecord;
import com.namng7.datn_v1.repository.PackageConfigRepository;
import com.namng7.datn_v1.repository.ServiceConfigReposiory;
import com.namng7.datn_v1.service.PackageConfigService;
import com.namng7.datn_v1.service.ServiceConfigService;
import com.namng7.datn_v1.util.MessageUtil;
import com.namng7.datn_v1.util.ServiceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ServiceConfigServiceImpl implements ServiceConfigService {
    private static final Logger logger = LogManager.getLogger(ServiceConfigService.class);
    private static StringBuilder log = new StringBuilder();

    @Autowired
    private ServiceConfigReposiory serviceConfigReposiory;

    private void validateServiceConfig(ProcessRecord record) {
        if (CacheManager.Users.AUTH_USER.getRole() != Key.Role.ADMIN && CacheManager.Users.AUTH_USER.getRole() != Key.Role.BUSSINESS) {
            record.setErrorCode(Key.ErrorCode.NOT_AUTH_CHANGE_INFO);
            record.setMessage(MessageUtil.getMessage(Key.Message.NOT_AUTH_CHANGE_INFO, logger));
            log.setLength(0);
            log.append("User: ").append(CacheManager.Users.AUTH_USER.getUsername()).
                    append(": tai khoan khong du quyen thay doi thong tin. Role: ").append(CacheManager.Users.AUTH_USER.getRole());
            logger.warn(log.toString());
            return;
        }
        ServiceConfig serviceConfig = (ServiceConfig) record.getObject();
        if (ServiceUtil.validateServiceConfig(serviceConfig) != Key.ErrorCode.SUCCESS) {
            record.setErrorCode(Key.ErrorCode.INVALID_SERVICE);
            record.setMessage(MessageUtil.getMessage(Key.Message.INVALID_ADD_SERVICE, logger));
            log.setLength(0);
            log.append("User: ").append(record.getUser().getUsername()).
                    append(": thong tin goi cau hinh dich vu khong hop le.");
            logger.warn(log.toString());
            return;
        }
        record.setErrorCode(Key.ErrorCode.SUCCESS);
    }

    @Override
    public void addServiceConfig(ProcessRecord record) {
        try {
            validateServiceConfig(record);
            if (record.getErrorCode() == Key.ErrorCode.SUCCESS) {
                ServiceConfig serviceConfig = (ServiceConfig) record.getObject();
                serviceConfig.setCreate_date(new Date());
                serviceConfig.setExport_status(0l);
                ServiceConfig savedPackage = serviceConfigReposiory.save(serviceConfig);
                record.setObject(savedPackage);
                record.setErrorCode(Key.ErrorCode.SUCCESS);
                record.setMessage(Key.Message.ADD_SERVICE_SUCCESS);
                log.setLength(0);
                log.append("User: ").append(CacheManager.Users.AUTH_USER.getUsername()).
                        append(": them moi thanh cong cau hinh dich vu");
                logger.info(log.toString());
            }
        } catch (Exception e) {
            log.setLength(0);
            record.setErrorCode(Key.ErrorCode.SYSTEM_FAULT);
            record.setMessage(MessageUtil.getMessage(Key.Message.SYSTEM_FAULT, logger));
            log.append("User: ").append(record.getUser().getUsername()).
                    append(": loi khi them moi goi dich vu.");
            logger.error(log.toString(), e);
        }
    }

    @Override
    public void updateServiceConfig(ProcessRecord record) {
        try {
            validateServiceConfig(record);
            ServiceConfig serviceConfig = (ServiceConfig) record.getObject();
            List<ServiceConfig> targetServices = serviceConfigReposiory.getServiceConfigById(serviceConfig.getId());
            if(targetServices == null || targetServices.isEmpty()){
                record.setErrorCode(Key.ErrorCode.INVALID_SERVICE);
                record.setMessage(MessageUtil.getMessage(Key.Message.INVALID_ADD_SERVICE, logger));
                log.setLength(0);
                log.append("User: ").append(CacheManager.Users.AUTH_USER.getUsername()).
                        append(": thong tin goi cau hinh dich vu khong hop le.");
                logger.warn(log.toString());
                return;
            }
            ServiceConfig targetService = targetServices.get(0);
            ServiceUtil.mergeServiceConfigInfor(record, targetService);
            ServiceConfig savedService = serviceConfigReposiory.save(targetService);
            record.setObject(savedService);
            record.setUser(CacheManager.Users.AUTH_USER);
            record.setErrorCode(Key.ErrorCode.SUCCESS);
            record.setMessage(Key.Message.UPDATE_SERVICE_SUCCESS);
            log.setLength(0);
            log.append("User: ").append(CacheManager.Users.AUTH_USER.getUsername()).
                    append(": them moi thanh cong cau hinh dich vu");
            logger.info(log.toString());
        } catch (Exception e) {
            log.setLength(0);
            record.setErrorCode(Key.ErrorCode.SYSTEM_FAULT);
            record.setMessage(MessageUtil.getMessage(Key.Message.SYSTEM_FAULT, logger));
            log.append("User: ").append(CacheManager.Users.AUTH_USER.getUsername()).
                    append(": loi khi thay doi thong tin goi dich vu.");
            logger.error(log.toString(), e);
        }
    }

    @Override
    public void getCompanyServiceConfig(ProcessRecord record){
//        Company company = CacheManager.Companys.mapCompany.get()
//        if(record.getUser().getRole().equals(Key.Role.ADMIN)){
//
//        }else if(record.getUser().getRole().equals(Key.Role.BUSSINESS && )){
//
//        }else{
//
//        }
    }
}
