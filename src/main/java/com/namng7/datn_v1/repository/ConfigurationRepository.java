package com.namng7.datn_v1.repository;

import com.namng7.datn_v1.model.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfigurationRepository extends JpaRepository<Configuration,Long> {
    List<Configuration> findAll();
}
