package com.namng7.datn_v1.util;

import com.namng7.datn_v1.cache.CacheManager;
import com.namng7.datn_v1.cache.Key;
import com.namng7.datn_v1.model.Company;
import com.namng7.datn_v1.repository.CompanyRepository;

import java.util.HashMap;

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
}
