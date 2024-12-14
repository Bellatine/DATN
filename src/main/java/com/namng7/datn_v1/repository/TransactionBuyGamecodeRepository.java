package com.namng7.datn_v1.repository;

import com.namng7.datn_v1.model.TransactionBuyGamecode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionBuyGamecodeRepository extends JpaRepository<TransactionBuyGamecode, Long> {
    List<TransactionBuyGamecode> findAll();
}
