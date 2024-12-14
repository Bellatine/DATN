package com.namng7.datn_v1.repository;

import com.namng7.datn_v1.model.ServiceConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceConfigReposiory extends JpaRepository<ServiceConfig, Long> {
    List<ServiceConfig> findAll();
}
