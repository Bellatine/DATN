package com.namng7.datn_v1.util;

import com.namng7.datn_v1.cache.CacheManager;
import com.namng7.datn_v1.cache.Key;
import com.namng7.datn_v1.model.GamecodeModel;
import com.namng7.datn_v1.model.PackageConfig;
import com.namng7.datn_v1.model.ServiceConfig;
import com.namng7.datn_v1.repository.GamecodeModelRepsitory;
import com.namng7.datn_v1.repository.PackageConfigRepository;
import com.namng7.datn_v1.repository.ServiceConfigReposiory;

import java.util.HashMap;

public class ServiceUtil {

    public static int validatePackageConfig(PackageConfig packageConfig){
        return Key.ErrorCode.SUCCESS;
    }

    public static int validateModelConfig(GamecodeModel gamecodeModel){
        return Key.ErrorCode.SUCCESS;
    }

    public static int validateServiceConfig(ServiceConfig serviceConfig){
        return Key.ErrorCode.SUCCESS;
    }

    public static PackageConfig savePackage(PackageConfig packageConfig, PackageConfigRepository packageConfigRepository) throws Exception{
        PackageConfig savedPackage = packageConfigRepository.save(packageConfig);
        if(CacheManager.MapPackageConfigByID == null){
            CacheManager.MapPackageConfigByID = new HashMap<>();
        }
        CacheManager.MapPackageConfigByID.put(savedPackage.getId(), savedPackage);
        return savedPackage;
    }

    public static GamecodeModel saveGamecodeModel(GamecodeModel gamecodeModel, GamecodeModelRepsitory gamecodeModelRepsitory) throws Exception{
        GamecodeModel savedModel = gamecodeModelRepsitory.save(gamecodeModel);
        if(CacheManager.MapGamecodeModelByID == null){
            CacheManager.MapGamecodeModelByID = new HashMap<>();
        }
        CacheManager.MapGamecodeModelByID.put(savedModel.getId(), savedModel);
        return savedModel;
    }
}
