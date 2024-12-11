package com.namng7.datn_v1.cache;

import com.namng7.datn_v1.model.Company;
import com.namng7.datn_v1.model.User;

import java.util.List;
import java.util.Map;

public class CacheManager {

    public static class Users{
        public static Map<String, User> MapUserByUsername;
        public static User AUTH_USER;
    }

    public static class Companys{
        public static List<Company> ListAllCompany;
        public static Map<Long, Company> mapCompany;

    }

    public static class Message{
        public static Map<String, String> MapMessageByMessageCode;
    }
}