package com.namng7.datn_v1.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "webservice_config")

public class WebserviceConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String ws_name;

    @Column
    private String api_url;

    @Column
    private String msg_template;

    @Column(nullable = false)
    private int status;

    @Column(nullable = false)
    private int retry_num;
}
