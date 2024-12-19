package com.namng7.datn_v1.service;

import com.namng7.datn_v1.model.TransactionBuyGamecode;
import com.namng7.datn_v1.object.ProcessRecord;

import java.util.List;

public interface TransactionBuyGamecodeService {
    void getAllTransactionBuyGamecode(ProcessRecord record);
    void buyGameCodeService(ProcessRecord record);
    void updateTransactionBuyGamecode(ProcessRecord record);
}
