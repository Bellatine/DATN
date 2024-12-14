package com.namng7.datn_v1.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "service_config")
public class ServiceConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long company_id;

    @Column(nullable = false)
    private Long gamecode_model_id;

    @Column(nullable = false)
    private Date start_date;

    @Column
    private Date valid_date;

    @Column(nullable = false)
    private Date create_date;

    @Column(nullable = false)
    private Long export_status;

}
