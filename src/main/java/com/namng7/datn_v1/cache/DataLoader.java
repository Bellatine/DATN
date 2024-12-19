package com.namng7.datn_v1.cache;

import com.namng7.datn_v1.model.Company;
import com.namng7.datn_v1.model.GamecodeModel;
import com.namng7.datn_v1.model.User;
import com.namng7.datn_v1.service.CompanyService;
import com.namng7.datn_v1.service.DataLoaderService;
import com.namng7.datn_v1.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.namng7.datn_v1.cache.CacheManager.ListAllGamecodeModel;
import static com.namng7.datn_v1.cache.CacheManager.MapGamecodeModelByID;

@Component
public class DataLoader implements ApplicationRunner {

    private static final Logger logger = LogManager.getLogger(DataLoader.class);

    private final DataLoaderService dataLoaderService;

    @Autowired
    public DataLoader(DataLoaderService dataLoaderService) {
        this.dataLoaderService = dataLoaderService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Start load data...");
        try {
            CacheManager.Users.MapUserByUsername = dataLoaderService.loadAllUser();
            CacheManager.Users.MapUserByUserID = dataLoaderService.mapUserByUserID();
            CacheManager.Companys.mapCompany = dataLoaderService.loadAllCompany();
            ListAllGamecodeModel = dataLoaderService.loadAllGamecodeModel();
            for(GamecodeModel gamecodeModel : ListAllGamecodeModel){
                MapGamecodeModelByID.put(gamecodeModel.getId(), gamecodeModel);
            }
            CacheManager.MapPackageConfigByID = dataLoaderService.loadAllPackageConfig();
            CacheManager.MapWsConfigByID = dataLoaderService.loadAllWebserviceConfig();
            CacheManager.MapMessageByMessageCode = dataLoaderService.loadAllConfiguration();
            logger.info("Load data success! ");
        } catch (Exception e) {
            logger.error("Load data fail.", e);
        }


    }
}
