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


    @Query("select s from ServiceConfig s where s.start_date < CURRENT_TIMESTAMP and s.valid_date > CURRENT_TIMESTAMP and s.company_id = :company_id")
    List<ServiceConfig> getServiceConfigByCompanyId(@Param("company_id") Long company_id);
}
