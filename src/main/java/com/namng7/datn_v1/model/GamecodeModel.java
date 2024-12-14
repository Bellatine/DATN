package com.namng7.datn_v1.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "gamecode_model")
public class GamecodeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String model_code;

    @Column(nullable = false)
    private String model_name;

    @Column(nullable = false)
    private int discount;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int status;

    @Column(nullable = false)
    private Date start_date;

    @Column
    private Date valid_date;

    @Column(nullable = false)
    private Date create_date;

    @Column(nullable = false)
    private Long create_user_id;

    @Column
    private Date updated_date;

    @Column
    private Long updated_user_id;

    @Column(nullable = false)
    private Long package_id;

    @Column(nullable = false)
    private int number_required;
}
