package com.namng7.datn_v1.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.namng7.datn_v1.cache.CacheManager;
import com.namng7.datn_v1.cache.Key;
import com.namng7.datn_v1.model.Company;
import com.namng7.datn_v1.model.User;
import com.namng7.datn_v1.object.ProcessRecord;
import com.namng7.datn_v1.repository.CompanyRepository;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CompanyUtil {
    public static int validateCompany(Company company){

        return Key.ErrorCode.SUCCESS;
    }

    public static Company saveCompany(Company company, CompanyRepository companyRepository) throws Exception{
        Company savedCompany = companyRepository.save(company);
        if (CacheManager.Companys.mapCompany == null) {
            CacheManager.Companys.mapCompany = new HashMap<>();
        }

        CacheManager.Companys.mapCompany.put(savedCompany.getBussiness_care(), savedCompany);
        return savedCompany;
    }

    public static void mergeInfor(ProcessRecord record, Company targetCompany) throws Exception{
        Company mergeCompany = (Company) record.getObject();

        boolean isChange = false;
        Field[] fields = Company.class.getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);

            Object mergeValue = field.get(mergeCompany);

            if(mergeValue != null){
                field.set(targetCompany, mergeValue);
                isChange = true;
            }
        }

        if(isChange){
            targetCompany.setUpdated_by(CacheManager.Users.AUTH_USER.getId());
            targetCompany.setUpdated_time(new Date());
        }
    }

    public static Company convertMaptoPojo(LinkedHashMap<String, Object> map) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(map, Company.class);
    }
}
