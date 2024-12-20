package com.namng7.datn_v1.service.impl;

import com.namng7.datn_v1.cache.Key;
import com.namng7.datn_v1.object.ProcessRecord;
import com.namng7.datn_v1.repository.GamecodeModelRepsitory;
import com.namng7.datn_v1.repository.TransactionBuyGamecodeRepository;
import com.namng7.datn_v1.service.GamecodeModelService;
import com.namng7.datn_v1.service.TransactionBuyGamecodeService;
import com.namng7.datn_v1.util.MessageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionBuyGamecodeServiceImpl implements TransactionBuyGamecodeService {
    private static final Logger logger = LogManager.getLogger(TransactionBuyGamecodeService.class);
    private static StringBuilder log = new StringBuilder();

    @Autowired
    private TransactionBuyGamecodeRepository transactionBuyGamecodeRepository;

    @Override
    public void getAllTransactionBuyGamecode(ProcessRecord record) {
        try{
            if()
        }catch (Exception e) {
            log.setLength(0);
            record.setErrorCode(Key.ErrorCode.SYSTEM_FAULT);
            record.setMessage(MessageUtil.getMessage(Key.Message.SYSTEM_FAULT, logger));
            log.append("User: ").append(record.getUser().getUsername()).
                    append(": loi khi lay thong tin dich vu.");
            logger.error(log.toString(), e);
        }
    }

    @Override
    public void buyGameCodeService(ProcessRecord record) {

    }

    @Override
    public void updateTransactionBuyGamecode(ProcessRecord record) {

    }
}
