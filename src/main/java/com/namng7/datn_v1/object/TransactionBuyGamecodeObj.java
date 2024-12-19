package com.namng7.datn_v1.object;

import java.util.Date;

public class TransactionBuyGamecodeObj {
    String username;
    String companyName;
    Integer walletBefore;
    Integer walletAfter;
    Integer walletConsumption;
    Date transTime;
    Integer totalItem;
    String modelName;
    String modelDescription;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getWalletBefore() {
        return walletBefore;
    }

    public void setWalletBefore(Integer walletBefore) {
        this.walletBefore = walletBefore;
    }

    public Integer getWalletAfter() {
        return walletAfter;
    }

    public void setWalletAfter(Integer walletAfter) {
        this.walletAfter = walletAfter;
    }

    public Integer getWalletConsumption() {
        return walletConsumption;
    }

    public void setWalletConsumption(Integer walletConsumption) {
        this.walletConsumption = walletConsumption;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public Integer getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Integer totalItem) {
        this.totalItem = totalItem;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }
}
