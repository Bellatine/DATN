package com.namng7.datn_v1.model;

import lombok.Data;

import jakarta.persistence.*;

import java.util.Date;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String fullname;

    @Column(nullable = false)
    private int status;

    @Column(nullable = false)
    private int role;

    @Column(nullable = false)
    private Date created_time;

    @Column(nullable = false)
    private Date updated_time;

}
