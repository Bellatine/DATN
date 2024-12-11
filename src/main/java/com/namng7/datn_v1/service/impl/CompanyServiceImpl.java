package com.namng7.datn_v1.service.impl;

import com.namng7.datn_v1.model.Company;
import com.namng7.datn_v1.object.ProcessRecord;
import com.namng7.datn_v1.repository.CompanyRepository;
import com.namng7.datn_v1.service.CompanyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyServiceImpl implements CompanyService {
    private static final Logger logger = LogManager.getLogger(CompanyService.class);
    private static StringBuilder log = new StringBuilder();

    @Autowired
    private CompanyRepository companyRepository;
    @Override
    public ProcessRecord getCompanyByUserId(ProcessRecord record) {

        return null;
    }

}
