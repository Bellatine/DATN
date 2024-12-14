package com.namng7.datn_v1.repository;

import com.namng7.datn_v1.model.GamecodeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GamecodeModelRepsitory extends JpaRepository<GamecodeModel, Long> {
    List<GamecodeModel> findAll();
}
