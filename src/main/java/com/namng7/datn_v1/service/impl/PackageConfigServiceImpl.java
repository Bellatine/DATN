package com.namng7.datn_v1.service.impl;

import com.namng7.datn_v1.cache.CacheManager;
import com.namng7.datn_v1.cache.Key;
import com.namng7.datn_v1.model.PackageConfig;
import com.namng7.datn_v1.object.ProcessRecord;
import com.namng7.datn_v1.repository.PackageConfigRepository;
import com.namng7.datn_v1.service.PackageConfigService;
import com.namng7.datn_v1.util.MessageUtil;
import com.namng7.datn_v1.util.ServiceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PackageConfigServiceImpl implements PackageConfigService {
    private static final Logger logger = LogManager.getLogger(PackageConfigService.class);
    private static StringBuilder log = new StringBuilder();

    @Autowired
    private PackageConfigRepository packageConfigRepository;

    private void validateProcess(ProcessRecord record){
        if (CacheManager.Users.AUTH_USER.getRole() != Key.Role.ADMIN && CacheManager.Users.AUTH_USER.getRole() != Key.Role.BUSSINESS) {
            record.setErrorCode(Key.ErrorCode.NOT_AUTH_CHANGE_INFO);
            record.setMessage(MessageUtil.getMessage(Key.Message.NOT_AUTH_CHANGE_INFO, logger));
            log.setLength(0);
            log.append("User: ").append(CacheManager.Users.AUTH_USER.getUsername()).
                    append(": tai khoan khong du quyen thay doi thong tin. Role: ").append(CacheManager.Users.AUTH_USER.getRole());
            logger.warn(log.toString());
            return;
        }
        PackageConfig packageConfig = (PackageConfig) record.getObject();
        if (ServiceUtil.validatePackageConfig(packageConfig) != Key.ErrorCode.SUCCESS) {
            record.setErrorCode(Key.ErrorCode.INVALID_PACKAGE);
            record.setMessage(MessageUtil.getMessage(Key.Message.INVALID_ADD_PACKAGE, logger));
            log.setLength(0);
            log.append("User: ").append(record.getUser().getUsername()).
                    append(": thong tin goi dich vu khong hop le.");
            logger.warn(log.toString());
            return;
        }
        record.setErrorCode(Key.ErrorCode.SUCCESS);
    }

    @Override
    public void addPackageConfig(ProcessRecord record) {
        try {
            validateProcess(record);
            if(record.getErrorCode() == Key.ErrorCode.SUCCESS) {
                PackageConfig packageConfig = (PackageConfig) record.getObject();
                packageConfig.setCreate_date(new Date());
                packageConfig.setCreate_user_id(CacheManager.Users.AUTH_USER.getId());
                PackageConfig savedPackage = ServiceUtil.savePackage(packageConfig, packageConfigRepository);
                record.setObject(savedPackage);
                record.setErrorCode(Key.ErrorCode.SUCCESS);
                record.setMessage(Key.Message.ADD_PACKAGE_SUCCESS);
                log.setLength(0);
                log.append("User: ").append(CacheManager.Users.AUTH_USER.getUsername()).
                        append(": them moi thanh cong goi dich vu: ").append(savedPackage.getPackage_name());
                logger.info(log.toString());
            }
        }catch (Exception e){
            log.setLength(0);
            record.setErrorCode(Key.ErrorCode.SYSTEM_FAULT);
            record.setMessage(MessageUtil.getMessage(Key.Message.SYSTEM_FAULT, logger));
            log.append("User: ").append(record.getUser().getUsername()).
                    append(": loi khi them moi goi dich vu.");
            logger.error(log.toString(), e);
        }

    }

    @Override
    public void updatePackageConfig(ProcessRecord record) {
        try{
            validateProcess(record);
            if(record.getErrorCode() == Key.ErrorCode.SUCCESS) {
                PackageConfig mergePackage = (PackageConfig) record.getObject();
                if(mergePackage.getUpdated_reason() == null || mergePackage.getUpdated_reason().isEmpty()){
                    record.setErrorCode(Key.ErrorCode.INVALID_PACKAGE);
                    record.setMessage(MessageUtil.getMessage(Key.Message.INVALID_ADD_PACKAGE, logger));
                    log.setLength(0);
                    log.append("User: ").append(record.getUser().getUsername()).
                            append(": thong tin goi dich vu khong hop le.");
                    logger.warn(log.toString());
                    return;
                }
                PackageConfig targetPackage = CacheManager.MapPackageConfigByID.get(mergePackage.getId());
                ServiceUtil.mergePackageConfigInfor(record, targetPackage);
                PackageConfig savedPackage = packageConfigRepository.save(targetPackage);
                record.setObject(savedPackage);
                record.setErrorCode(Key.ErrorCode.SUCCESS);
                record.setMessage(Key.Message.UPDATE_PACKAGE_SUCCESS);
                log.setLength(0);
                log.append("User: ").append(CacheManager.Users.AUTH_USER.getUsername()).
                        append(": cap nhat thanh cong goi dich vu: ").append(savedPackage.getPackage_name());
                logger.info(log.toString());
            }
        }catch (Exception e){
            log.setLength(0);
            record.setErrorCode(Key.ErrorCode.SYSTEM_FAULT);
            record.setMessage(MessageUtil.getMessage(Key.Message.SYSTEM_FAULT, logger));
            log.append("User: ").append(record.getUser().getUsername()).
                    append(": loi khi cap nhat goi dich vu.");
            logger.error(log.toString(), e);
        }
    }
}
