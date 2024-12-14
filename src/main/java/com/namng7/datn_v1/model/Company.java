package com.namng7.datn_v1.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String code;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private String industry;

    @Column(nullable = false)
    private String tax;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String represent;

    @Column
    private String address;

    @Column(nullable = false)
    private String bank;

    @Column
    private Integer status;

    @Column
    private Long create_by;

    @Column
    private Date created_time;

    @Column
    private Long updated_by;

    @Column
    private Date updated_time;

    @Column
    private String updated_reason;

    @Column(nullable = false)
    private Long bussiness_care;

    @Column(nullable = false)
    private Long user_id;

}
