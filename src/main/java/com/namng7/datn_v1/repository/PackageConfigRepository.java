package com.namng7.datn_v1.repository;

import com.namng7.datn_v1.model.PackageConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PackageConfigRepository extends JpaRepository<PackageConfig, Long> {
    List<PackageConfig> findAll();
}
