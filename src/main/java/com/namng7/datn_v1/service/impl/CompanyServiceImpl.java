package com.namng7.datn_v1.service.impl;

import com.namng7.datn_v1.cache.CacheManager;
import com.namng7.datn_v1.cache.Key;
import com.namng7.datn_v1.model.Company;
import com.namng7.datn_v1.model.User;
import com.namng7.datn_v1.model.Wallet;
import com.namng7.datn_v1.object.ProcessRecord;
import com.namng7.datn_v1.repository.CompanyRepository;
import com.namng7.datn_v1.repository.WalletRepository;
import com.namng7.datn_v1.service.CompanyService;
import com.namng7.datn_v1.service.UserService;
import com.namng7.datn_v1.util.CompanyUtil;
import com.namng7.datn_v1.util.MessageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyServiceImpl implements CompanyService {
    private static final Logger logger = LogManager.getLogger(CompanyService.class);
    private static StringBuilder log = new StringBuilder();

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserService userService;

    @Override
    public ProcessRecord getCompanyByUserId(ProcessRecord record) {

        return null;
    }

    @Override
    public void registerCompany(ProcessRecord record  ){
        try{
            if(CacheManager.Users.AUTH_USER.getRole() != Key.Role.ADMIN && CacheManager.Users.AUTH_USER.getRole() != Key.Role.BUSSINESS){
                record.setErrorCode(Key.ErrorCode.NOT_AUTH_CHANGE_INFO);
                record.setMessage(MessageUtil.getMessage(Key.Message.NOT_AUTH_CHANGE_INFO, logger));
                log.setLength(0);
                log.append("User: ").append(CacheManager.Users.AUTH_USER.getUsername()).
                        append(": tai khoan khong du quyen thay doi thong tin. Role: ").append(CacheManager.Users.AUTH_USER.getRole());
                logger.warn(log.toString());
                return;
            }
            Company company = (Company) record.getObject();
            if(company.getUser_id() == null || company.getUser_id() == 0l){
                record.setErrorCode(Key.ErrorCode.INVALID_COMPANY);
                record.setMessage(MessageUtil.getMessage(Key.Message.INVALID_REGISTER_COMPANY, logger));
                log.setLength(0);
                log.append("User: ").append(record.getUser().getUsername()).
                        append(": thong tin doanh nghiep khong hop le.");
                logger.warn(log.toString());
                return;
            }
            User companyUser = CacheManager.Users.MapUserByUserID.get(company.getUser_id());
            if(companyUser == null || companyUser.getRole() != Key.Role.COMPANY){
                record.setErrorCode(Key.ErrorCode.INVALID_COMPANY_USER);
                record.setMessage(MessageUtil.getMessage(Key.Message.INVALID_COMPANY_USER, logger));
                log.setLength(0);
                log.append("User: ").append(record.getUser().getUsername()).
                        append(": tai khoan doanh nghiep khong ton tai hoac khong phai tai khoan doanh nghiep.");
                logger.warn(log.toString());
                return;
            }
            record.setUser(companyUser);
            if(CompanyUtil.validateCompany(company) != Key.ErrorCode.SUCCESS || companyUser.getStatus() != Key.Status.INACTIVE){
                record.setErrorCode(Key.ErrorCode.INVALID_COMPANY);
                record.setMessage(MessageUtil.getMessage(Key.Message.INVALID_REGISTER_COMPANY, logger));
                log.setLength(0);
                log.append("User: ").append(record.getUser().getUsername()).
                        append(": thong tin doanh nghiep khong hop le.");
                logger.warn(log.toString());
                return;
            }

            record.getUser().setStatus(Key.Status.ACTIVE);
            userService.updateInfor(record);
            if(record.getErrorCode() != Key.ErrorCode.SUCCESS){
                log.setLength(0);
                log.append("User: ").append(CacheManager.Users.AUTH_USER.getUsername()).
                        append(": loi khi cap nhat status cho user: ").append(companyUser.getUsername());
                logger.error(log.toString());
                return;
            }
            company.setCreate_by(CacheManager.Users.AUTH_USER.getId());
            company.setCreated_time(new Date());
            if(CacheManager.Users.AUTH_USER.getRole() == Key.Role.BUSSINESS){
                company.setBussiness_care(CacheManager.Users.AUTH_USER.getId());
            }
            Company savedCompany = CompanyUtil.saveCompany(company,companyRepository);

            Wallet wallet = new Wallet();
            wallet.setCompany_id(savedCompany.getId());
            wallet.setBalance(0l);
            wallet.setLast_topup_id(0l);
            wallet.setStatus(1);
            walletRepository.save(wallet);
            record.setObject(savedCompany);
            record.setErrorCode(Key.ErrorCode.SUCCESS);
            record.setMessage(Key.Message.REGISTER_COMPANY_SUCCESS);
            log.setLength(0);
            log.append("User: ").append(CacheManager.Users.AUTH_USER.getUsername()).
                    append(": dang ky thong tin doanh nghiep cho user: ").append(companyUser.getUsername());
            logger.info(log.toString());
        }catch (Exception e){
            log.setLength(0);
            record.setErrorCode(Key.ErrorCode.SYSTEM_FAULT);
            record.setMessage(MessageUtil.getMessage(Key.Message.SYSTEM_FAULT, logger));
            log.append("User: ").append(record.getUser().getUsername()).
                    append(": loi khi dang ky thong tin doanh nghiep.");
            logger.error(log.toString(), e);
        }
    }

    @Override
    public void updateCompany(ProcessRecord record){
        try{
            Company mergeCompany = (Company) record.getObject();
            if(mergeCompany.getUser_id() == null || mergeCompany.getUser_id() < 0l){
                record.setErrorCode(Key.ErrorCode.INVALID_USER);
                record.setMessage(Key.Message.INVALID_USER);
                log.setLength(0);
                log.append("User: ").append(CacheManager.Users.AUTH_USER.getUsername()).
                        append(": Cap nhat thong tin company fail do khong tim thay tai khoan");
                logger.info(log.toString());
                return;
            }
            Company targetCompany = CacheManager.Companys.mapCompany.get(mergeCompany.getUser_id());
            if(targetCompany == null){
                record.setErrorCode(Key.ErrorCode.INVALID_COMPANY);
                record.setMessage(Key.Message.INVALID_COMPANY);
                log.setLength(0);
                log.append("User: ").append(CacheManager.Users.AUTH_USER.getUsername()).
                        append(": cap nhat thong tin doanh nghiep khong thanh cong do khong co thong tin doanh nghiep.");
                logger.warn(log.toString());
                return;
            }
            if(mergeCompany.getUpdated_reason() == null || mergeCompany.getUpdated_reason().isEmpty()){
                record.setErrorCode(Key.ErrorCode.NOT_CONDITION);
                record.setMessage(Key.Message.NOT_CONDITION_UPDATE_COMPANY);
                log.setLength(0);
                log.append("User: ").append(CacheManager.Users.AUTH_USER.getUsername()).
                        append(": cap nhat thong tin doanh nghiep khong thanh cong do khong co thong tin ly do cap nhat.");
                logger.warn(log.toString());
                return;
            }
            if(CacheManager.Users.AUTH_USER.getRole() == Key.Role.ADMIN ||
                    (CacheManager.Users.AUTH_USER.getRole() == Key.Role.BUSSINESS && targetCompany.getBussiness_care().equals(CacheManager.Users.AUTH_USER.getId()))||
                    (CacheManager.Users.AUTH_USER.getRole() == Key.Role.COMPANY && targetCompany.getUser_id().equals(CacheManager.Users.AUTH_USER.getId()))){
                CompanyUtil.mergeInfor(record, targetCompany);
                companyRepository.save(targetCompany);
                log.setLength(0);
                log.append("User: ").append(CacheManager.Users.AUTH_USER.getUsername()).
                        append(": Cap nhat thong tin doanh nghiep thanh cong.");
                logger.warn(log.toString());
            }else{
                record.setErrorCode(Key.ErrorCode.NOT_AUTH_CHANGE_INFO);
                record.setMessage(MessageUtil.getMessage(Key.Message.NOT_AUTH_CHANGE_INFO, logger));
                log.setLength(0);
                log.append("User: ").append(CacheManager.Users.AUTH_USER.getUsername()).
                        append(": tai khoan khong du quyen thay doi thong tin. Role: ").append(CacheManager.Users.AUTH_USER.getRole());
                logger.warn(log.toString());
            }
        }catch (Exception e){
            log.setLength(0);
            record.setErrorCode(Key.ErrorCode.SYSTEM_FAULT);
            record.setMessage(MessageUtil.getMessage(Key.Message.SYSTEM_FAULT, logger));
            log.append("User: ").append(CacheManager.Users.AUTH_USER.getUsername()).
                    append(": loi khi cap nhat thong tin doanh nghiep.");
            logger.error(log.toString(), e);
        }
    }

}
