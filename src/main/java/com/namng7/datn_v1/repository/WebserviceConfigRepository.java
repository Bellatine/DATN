package com.namng7.datn_v1.repository;

import com.namng7.datn_v1.model.WebserviceConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebserviceConfigRepository extends JpaRepository<WebserviceConfig, Long> {
    List<WebserviceConfig> findAll();
}
