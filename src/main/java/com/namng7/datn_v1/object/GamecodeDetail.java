package com.namng7.datn_v1.object;


import lombok.Data;

import java.util.Date;

@Data
public class GamecodeDetail {

    private Long id;
    private String gamecode;
    private String serial;
    private Date start_date;
    private Date create_date;
    private Integer status;
}
