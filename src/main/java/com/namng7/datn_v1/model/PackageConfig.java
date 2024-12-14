package com.namng7.datn_v1.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "package_config")
public class PackageConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String package_code;

    @Column(nullable = false)
    private String package_name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int status;

    @Column(nullable = false)
    private int add_value;

    @Column(nullable = false)
    private int ws_id;

    @Column(nullable = false)
    private Date valid_time;

    @Column(nullable = false)
    private Date create_date;

    @Column(nullable = false)
    private Long create_user_id;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private Long updated_user_id;

    @Column(nullable = false)
    private String updated_reason;
}
