package com.namng7.datn_v1.controller;

import com.namng7.datn_v1.cache.CacheManager;
import com.namng7.datn_v1.model.Company;
import com.namng7.datn_v1.model.User;
import com.namng7.datn_v1.object.ProcessRecord;
import com.namng7.datn_v1.service.CompanyService;
import com.namng7.datn_v1.service.impl.CompanyServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/datn/company")
public class CompanyController {
    private static final Logger logger = LogManager.getLogger(CompanyController.class);

    @Autowired
    private CompanyService companyService;

    @PostMapping("/register")
    public ResponseEntity<?> registerCompany(@RequestBody Company company){
        ProcessRecord record = new ProcessRecord();
        record.setObject(company);
        try{
            companyService.registerCompany(record);
            return ResponseEntity.ok(record);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(record);
        }
    }


}
