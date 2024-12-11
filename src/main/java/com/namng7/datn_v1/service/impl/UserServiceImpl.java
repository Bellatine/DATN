package com.namng7.datn_v1.service.impl;

import com.namng7.datn_v1.model.User;
import com.namng7.datn_v1.object.ProcessRecord;
import com.namng7.datn_v1.repository.UserRepository;
import com.namng7.datn_v1.service.UserService;
import com.namng7.datn_v1.cache.CacheManager;
import com.namng7.datn_v1.cache.Key;
import com.namng7.datn_v1.util.MessageUtil;
import com.namng7.datn_v1.util.UserUtil;
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
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private static StringBuilder log = new StringBuilder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ProcessRecord registerUser(ProcessRecord registerProcessRecord) {
        User user = registerProcessRecord.getUser();
        if(UserUtil.validateUser(user) != UserUtil.SUCCESS){
            registerProcessRecord.setErrorCode(Key.ErrorCode.INVALID_USER);
            registerProcessRecord.setMessage(MessageUtil.getMessage(Key.Message.INVALID_USER, logger));
            return registerProcessRecord;
        }
        User cacheUser = CacheManager.Users.MapUserByUsername.get(user.getUsername());
        if(cacheUser != null){
            registerProcessRecord.setErrorCode(Key.ErrorCode.INVALID_USER);
            registerProcessRecord.setMessage(MessageUtil.getMessage(Key.Message.USER_ALREADY_EXISTS, logger));
            log.setLength(0);
            log.append("User: ").append(registerProcessRecord.getUser().getUsername()).
                    append("Da ton tai.");
            logger.info(log.toString());
            return registerProcessRecord;
        }
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            registerProcessRecord.setUser(savedUser);
            registerProcessRecord.setErrorCode(Key.ErrorCode.SUCCESS);
            registerProcessRecord.setMessage(MessageUtil.getMessage(Key.Message.REGISTER_USER_SUCCESS,logger));
            log.setLength(0);
            log.append("User: ").append(registerProcessRecord.getUser().getUsername()).
                    append(" da dang ky thanh cong!");
            logger.info(log.toString());
        }catch (Exception e){
            log.setLength(0);
            registerProcessRecord.setErrorCode(Key.ErrorCode.SYSTEM_FAULT);
            registerProcessRecord.setMessage(MessageUtil.getMessage(Key.Message.SYSTEM_FAULT, logger));
            log.append("User: ").append(registerProcessRecord.getUser().getUsername()).
                    append(" loi khi dang ky tai khoan");
            logger.error(log.toString(), e);
        }

        return registerProcessRecord;
    }

    @Override
    public ProcessRecord loginUser(ProcessRecord loginRecord) {
        User user = CacheManager.Users.MapUserByUsername.get(loginRecord.getUser().getUsername());
        if (user != null && passwordEncoder.matches(loginRecord.getUser().getPassword(), user.getPassword())) {
            CacheManager.Users.AUTH_USER = user;
            loginRecord.setUser(user);
            loginRecord.setErrorCode(Key.ErrorCode.SUCCESS);
            log.setLength(0);
            log.append("User: ").append(loginRecord.getUser().getUsername()).
                    append(" da dang nhap thanh cong!");
            return loginRecord;
        } else {
            loginRecord.setErrorCode(Key.ErrorCode.INVALID_USER);
            loginRecord.setMessage(MessageUtil.getMessage(Key.Message.INVALID_USER, logger));
            log.setLength(0);
            log.append("User: ").append(loginRecord.getUser().getUsername()).
                    append(" tai khoan hoac mat khau khong hop le.");
            return loginRecord;
        }
    }

    @Override
    public ProcessRecord updateInfor(ProcessRecord updateRecord) {
        User user = updateRecord.getUser();
        if(CacheManager.Users.AUTH_USER.getUsername().equals(user.getUsername())){
            try{

            }catch (Exception e){

            }
        }
        return updateRecord;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
