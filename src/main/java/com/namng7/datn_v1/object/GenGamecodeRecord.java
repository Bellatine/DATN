package com.namng7.datn_v1.object;

import lombok.Data;

import java.util.List;

@Data
public class GenGamecodeRecord {
    private int amount;
    private List<GamecodeDetail> listGamecode;
    private Integer errorCode;
}
