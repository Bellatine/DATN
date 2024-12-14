package com.namng7.datn_v1.util;

import com.namng7.datn_v1.cache.CacheManager;
import com.namng7.datn_v1.cache.Key;
import com.namng7.datn_v1.model.User;
import com.namng7.datn_v1.object.ProcessRecord;
import com.namng7.datn_v1.repository.UserRepository;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

public class UserUtil {
    public static int SUCCESS = 0;
    public static int validateUser(User user){

        return Key.ErrorCode.SUCCESS;
    }

    public static void mergeInfor(User userFrom, User userTo){
        boolean isChange = false;
        if(userFrom.getPassword() != null && !userFrom.getPassword().isEmpty()){
            userTo.setPassword(userFrom.getPassword());
            isChange = true;
        }
        if(userFrom.getEmail() != null && !userFrom.getEmail().isEmpty()){
            userTo.setEmail(userFrom.getEmail());
            isChange = true;
        }
        if(userFrom.getFullname() != null && !userFrom.getFullname().isEmpty()){
            userTo.setFullname(userFrom.getFullname());
            isChange = true;
        }
        if(userFrom.getStatus() != null){
            userTo.setStatus(userFrom.getStatus());
            isChange = true;
        }
        if(isChange){
            userTo.setUpdated_time(new Date());
        }
    }

    public static User saveUser(User user, PasswordEncoder passwordEncoder, UserRepository userRepository) throws Exception{
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        CacheManager.Users.MapUserByUsername.put(savedUser.getUsername(), savedUser);
        CacheManager.Users.MapUserByUserID.put(savedUser.getId(), savedUser);
        return savedUser;
    }

}
