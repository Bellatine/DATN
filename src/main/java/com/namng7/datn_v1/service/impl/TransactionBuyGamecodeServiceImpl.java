package com.namng7.datn_v1.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.namng7.datn_v1.cache.CacheManager;
import com.namng7.datn_v1.cache.Key;
import com.namng7.datn_v1.model.*;
import com.namng7.datn_v1.object.GamecodeDetail;
import com.namng7.datn_v1.object.GenGamecodeRecord;
import com.namng7.datn_v1.object.ProcessRecord;
import com.namng7.datn_v1.repository.TransactionBuyGamecodeRepository;
import com.namng7.datn_v1.repository.WalletRepository;
import com.namng7.datn_v1.service.GamecodeModelService;
import com.namng7.datn_v1.service.TransactionBuyGamecodeService;
import com.namng7.datn_v1.util.MessageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionBuyGamecodeServiceImpl implements TransactionBuyGamecodeService {
    private static final Logger logger = LogManager.getLogger(TransactionBuyGamecodeService.class);
    private static StringBuilder log = new StringBuilder();

    @Autowired
    private GamecodeClientService gamecodeClientService;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionBuyGamecodeRepository transactionBuyGamecodeRepository;

    @Autowired
    private GamecodeModelService gamecodeModelService;

    @Override
    public void getAllTransactionBuyGamecode(ProcessRecord record) {
        try{

            //if()
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
        try {
            TransactionBuyGamecode transactionBuyGamecode =  convertMapToPojo((LinkedHashMap<String, Object>)record.getObject());
            if(transactionBuyGamecode == null ||
                    transactionBuyGamecode.getModel_id() == null || transactionBuyGamecode.getModel_id() < 1l ||
                    transactionBuyGamecode.getTotal_item() == null || transactionBuyGamecode.getTotal_item() < 1){
                log.setLength(0);
                record.setErrorCode(Key.ErrorCode.INVALID_COMPANY);
                record.setMessage(MessageUtil.getMessage(Key.Message.INVALID_COMPANY, logger));
                log.append("User: ").append(record.getUser().getUsername()).
                        append(": khong co thong tin giao dich.");
                logger.error(log.toString());
                return;
            }
            GamecodeModel gamecodeModel = CacheManager.MapGamecodeModelByID.get(transactionBuyGamecode.getModel_id());
            gamecodeModelService.getAllGameCodeModelByRole(record);
            if(record.getErrorCode() != Key.ErrorCode.SUCCESS){
                log.setLength(0);
                record.setErrorCode(Key.ErrorCode.INVALID_COMPANY);
                record.setMessage(MessageUtil.getMessage(Key.Message.INVALID_COMPANY, logger));
                log.append("User: ").append(record.getUser().getUsername()).
                        append(": loi khi lay goi dich vu duoc cau hinh cho doanh nghiep.");
                logger.error(log.toString());
                return;
            }
            boolean isPassValidateServiceConfig = false;
            List<GamecodeModel> offerModel = (List<GamecodeModel>) record.getObject();

            for(GamecodeModel model : offerModel){
                if(model.getId().equals(gamecodeModel.getId())){
                    isPassValidateServiceConfig = true;
                }
            }
            if(!isPassValidateServiceConfig){
                log.setLength(0);
                record.setErrorCode(Key.ErrorCode.INVALID_COMPANY);
                record.setMessage(MessageUtil.getMessage(Key.Message.INVALID_COMPANY, logger));
                log.append("User: ").append(record.getUser().getUsername()).
                        append(": doanh nghiep chua duoc cau hinh dich vu.");
                logger.error(log.toString());
                return;
            }
            Company company = CacheManager.Companys.mapCompany.get(record.getUser().getId());
            if (company == null) {
                log.setLength(0);
                record.setErrorCode(Key.ErrorCode.INVALID_COMPANY);
                record.setMessage(MessageUtil.getMessage(Key.Message.INVALID_COMPANY, logger));
                log.append("User: ").append(record.getUser().getUsername()).
                        append(": khong tim thay thong tin cong ty.");
                logger.error(log.toString());
                return;
            }
            Wallet wallet = walletRepository.getWalletByCompany_id(company.getId());
            if (wallet == null) {
                log.setLength(0);
                record.setErrorCode(Key.ErrorCode.INVALID_COMPANY);
                record.setMessage(MessageUtil.getMessage(Key.Message.INVALID_COMPANY, logger));
                log.append("User: ").append(record.getUser().getUsername()).
                        append(": khong tim thay thong tin vi cong ty.");
                logger.error(log.toString());
                return;
            }
            PackageConfig packageConfig = CacheManager.MapPackageConfigByID.get(gamecodeModel.getPackage_id());
            long totalPrice = packageConfig.getPrice() * transactionBuyGamecode.getTotal_item() * (100 - gamecodeModel.getDiscount())/100;
            long walletBefore = wallet.getBalance();
            long walletAfter = walletBefore - totalPrice;
            if(walletAfter < 0){
                log.setLength(0);
                record.setErrorCode(Key.ErrorCode.INVALID_COMPANY);
                record.setMessage(MessageUtil.getMessage(Key.Message.INVALID_COMPANY, logger));
                log.append("User: ").append(record.getUser().getUsername()).
                        append(": khong du tien trong tai khoan.");
                logger.error(log.toString());
                return;
            }
            transactionBuyGamecode.setWallet_before(wallet.getBalance());
            wallet.setBalance(walletBefore - totalPrice);
            walletRepository.save(wallet);
            GenGamecodeRecord genGamecodeRecord = new GenGamecodeRecord();
            genGamecodeRecord.setAmount(transactionBuyGamecode.getTotal_item());
            String apiUrl = CacheManager.MapWsConfigByID.get(packageConfig.getWs_id()).getApi_url();
            GenGamecodeRecord response = gamecodeClientService.sendPostRequest(apiUrl, genGamecodeRecord).block();
            if(response.getErrorCode() != Key.ErrorCode.SUCCESS || response.getListGamecode() == null || response.getListGamecode().size()<1){
                wallet.setBalance(walletBefore);
                walletRepository.save(wallet);
                log.setLength(0);
                record.setErrorCode(Key.ErrorCode.INVALID_COMPANY);
                record.setMessage(MessageUtil.getMessage(Key.Message.INVALID_COMPANY, logger));
                log.append("User: ").append(record.getUser().getUsername()).
                        append(": loi khi tao game code.");
                logger.error(log.toString());
                return;
            }
            transactionBuyGamecode.setWallet_consumption(totalPrice);
            transactionBuyGamecode.setWallet_after(walletAfter);
            transactionBuyGamecode.setTransaction_time(new Date());
            transactionBuyGamecode.setCompany_id(company.getId());
            TransactionBuyGamecode savedTrans = transactionBuyGamecodeRepository.save(transactionBuyGamecode);
            for(GamecodeDetail detail : response.getListGamecode()){

            }
            record.setObject(savedTrans);
            record.setErrorCode(Key.ErrorCode.SUCCESS);
            record.setMessage(MessageUtil.getMessage(Key.Message.INVALID_COMPANY, logger));
            log.append("User: ").append(record.getUser().getUsername()).
                    append(": mua gamecode thanh cong.");
            logger.error(log.toString());
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
    public void updateTransactionBuyGamecode(ProcessRecord record) {

    }

    public TransactionBuyGamecode convertMapToPojo(LinkedHashMap<String, Object> map) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(map, TransactionBuyGamecode.class);
    }
}
