package com.namng7.datn_v1.controller;

import com.namng7.datn_v1.model.GamecodeModel;
import com.namng7.datn_v1.model.PackageConfig;
import com.namng7.datn_v1.model.ServiceConfig;
import com.namng7.datn_v1.object.ProcessRecord;
import com.namng7.datn_v1.service.GamecodeModelService;
import com.namng7.datn_v1.service.PackageConfigService;
import com.namng7.datn_v1.service.ServiceConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/datn/service")
public class ServiceController {
    private static final Logger logger = LogManager.getLogger(ServiceController.class);

    @Autowired
    private GamecodeModelService gamecodeModelService;

    @Autowired
    private PackageConfigService packageConfigService;

    @Autowired
    private ServiceConfigService serviceConfigService;

    @PostMapping("/addPackage")
    public ResponseEntity<?> addPackageConfig(@RequestBody PackageConfig packageConfig){
        ProcessRecord record = new ProcessRecord();
        record.setObject(packageConfig);
        try{
            packageConfigService.addPackageConfig(record);
            return ResponseEntity.ok(record);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(record);
        }
    }

    @PostMapping("/addModel")
    public ResponseEntity<?> addGamecodeModel(@RequestBody GamecodeModel gamecodeModel){
        ProcessRecord record = new ProcessRecord();
        record.setObject(gamecodeModel);
        try{
            gamecodeModelService.addGamecodeModel(record);
            return ResponseEntity.ok(record);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(record);
        }
    }

    @PostMapping("/addServiceConfig")
    public ResponseEntity<?> addServiceConfig(@RequestBody ServiceConfig serviceConfig){
        ProcessRecord record = new ProcessRecord();
        record.setObject(serviceConfig);
        try{
            serviceConfigService.addServiceConfig(record);
            return ResponseEntity.ok(record);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(record);
        }
    }
}
