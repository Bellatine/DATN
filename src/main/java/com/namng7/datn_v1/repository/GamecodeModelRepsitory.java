package com.namng7.datn_v1.repository;

import com.namng7.datn_v1.model.GamecodeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GamecodeModelRepsitory extends JpaRepository<GamecodeModel, Long> {
    @Query("SELECT m FROM GamecodeModel m WHERE m.status = 1")
    List<GamecodeModel> findAll();
}
