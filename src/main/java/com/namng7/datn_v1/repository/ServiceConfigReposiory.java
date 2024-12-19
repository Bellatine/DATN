package com.namng7.datn_v1.repository;

import com.namng7.datn_v1.model.ServiceConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceConfigReposiory extends JpaRepository<ServiceConfig, Long> {
    List<ServiceConfig> findAll();

    @Query("SELECT s FROM ServiceConfig s WHERE s.id = :id")
    List<ServiceConfig> getServiceConfigById(@Param("id") Long id);

    @Query("SELECT s FROM ServiceConfig s WHERE s.company_id = :id")
    List<ServiceConfig> getServiceConfigByCompanyId(@Param("id") Long id);
}
