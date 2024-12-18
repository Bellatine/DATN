package com.namng7.datn_v1.service.impl;

import com.namng7.datn_v1.cache.CacheManager;
import com.namng7.datn_v1.cache.Key;
import com.namng7.datn_v1.model.GamecodeModel;
import com.namng7.datn_v1.model.PackageConfig;
import com.namng7.datn_v1.object.ProcessRecord;
import com.namng7.datn_v1.repository.GamecodeModelRepsitory;
import com.namng7.datn_v1.service.GamecodeModelService;
import com.namng7.datn_v1.util.MessageUtil;
import com.namng7.datn_v1.util.ServiceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GamecodeModelServiceImpl implements GamecodeModelService {
    private static final Logger logger = LogManager.getLogger(GamecodeModelService.class);
    private static StringBuilder log = new StringBuilder();

    @Autowired
    private GamecodeModelRepsitory gamecodeModelRepsitory;

    private void validateProcess(ProcessRecord record) {
        if (CacheManager.Users.AUTH_USER.getRole() != Key.Role.ADMIN && CacheManager.Users.AUTH_USER.getRole() != Key.Role.BUSSINESS) {
            record.setErrorCode(Key.ErrorCode.NOT_AUTH_CHANGE_INFO);
            record.setMessage(MessageUtil.getMessage(Key.Message.NOT_AUTH_CHANGE_INFO, logger));
            log.setLength(0);
            log.append("User: ").append(CacheManager.Users.AUTH_USER.getUsername()).
                    append(": tai khoan khong du quyen thay doi thong tin. Role: ").append(CacheManager.Users.AUTH_USER.getRole());
            logger.warn(log.toString());
            return;
        }
        GamecodeModel gamecodeModel = (GamecodeModel) record.getObject();
        if (ServiceUtil.validateModelConfig(gamecodeModel) != Key.ErrorCode.SUCCESS) {
            record.setErrorCode(Key.ErrorCode.INVALID_MODEL);
            record.setMessage(MessageUtil.getMessage(Key.Message.INVALID_ADD_MODEL, logger));
            log.setLength(0);
            log.append("User: ").append(record.getUser().getUsername()).
                    append(": thong tin dich vu khong hop le.");
            logger.warn(log.toString());
            return;
        }

        record.setErrorCode(Key.ErrorCode.SUCCESS);
    }

    @Override
    public void addGamecodeModel(ProcessRecord record) {
        try {
            validateProcess(record);
            if (record.getErrorCode() == Key.ErrorCode.SUCCESS) {
                GamecodeModel gamecodeModel = (GamecodeModel) record.getObject();
                gamecodeModel.setCreate_date(new Date());
                gamecodeModel.setCreate_user_id(CacheManager.Users.AUTH_USER.getId());
                gamecodeModel.setUpdated_user_id(CacheManager.Users.AUTH_USER.getId());
                GamecodeModel savedModel = ServiceUtil.saveGamecodeModel(gamecodeModel, gamecodeModelRepsitory);
                record.setObject(savedModel);
                record.setErrorCode(Key.ErrorCode.SUCCESS);
                record.setMessage(Key.Message.ADD_MODEL_SUCCESS);
                log.setLength(0);
                log.append("User: ").append(CacheManager.Users.AUTH_USER.getUsername()).
                        append(": them moi thanh cong dich vu: ").append(savedModel.getModel_name());
                logger.info(log.toString());
            }
        } catch (Exception e) {
            log.setLength(0);
            record.setErrorCode(Key.ErrorCode.SYSTEM_FAULT);
            record.setMessage(MessageUtil.getMessage(Key.Message.SYSTEM_FAULT, logger));
            log.append("User: ").append(record.getUser().getUsername()).
                    append(": loi khi them moi dich vu.");
            logger.error(log.toString(), e);
        }
    }

    @Override
    public void updateGamecodeModel(ProcessRecord record) {
        try {
            validateProcess(record);
            if (record.getErrorCode() == Key.ErrorCode.SUCCESS) {
                GamecodeModel mergeModel = (GamecodeModel) record.getObject();
                GamecodeModel targetModel = CacheManager.MapGamecodeModelByID.get(mergeModel.getId());
                if(targetModel == null){
                    record.setErrorCode(Key.ErrorCode.INVALID_MODEL);
                    record.setMessage(MessageUtil.getMessage(Key.Message.INVALID_ADD_MODEL, logger));
                    log.setLength(0);
                    log.append("User: ").append(record.getUser().getUsername()).
                            append(": thong tin dich vu khong hop le.");
                    logger.warn(log.toString());
                    return;
                }
                ServiceUtil.mergeGamecodeModelInfor(record, targetModel);
                GamecodeModel savedModel = gamecodeModelRepsitory.save(targetModel);
                record.setObject(savedModel);
                record.setErrorCode(Key.ErrorCode.SUCCESS);
                record.setMessage(Key.Message.UPDATE_MODEL_SUCCESS);
                log.setLength(0);
                log.append("User: ").append(CacheManager.Users.AUTH_USER.getUsername()).
                        append(": cap nhat thanh cong dich vu: ").append(savedModel.getModel_name());
                logger.info(log.toString());
            }
        } catch (Exception e) {
            log.setLength(0);
            record.setErrorCode(Key.ErrorCode.SYSTEM_FAULT);
            record.setMessage(MessageUtil.getMessage(Key.Message.SYSTEM_FAULT, logger));
            log.append("User: ").append(record.getUser().getUsername()).
                    append(": loi khi cap nhat dich vu.");
            logger.error(log.toString(), e);
        }
    }
}
