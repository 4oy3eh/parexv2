package com.example.demo;

import java.math.BigDecimal;
import java.util.UUID;

public class Form {

    private UUID id = UUID.randomUUID();
    private String sen_name;
    private String sen_document;
    private String sen_account;
    private String rec_name;
    private String rec_document;
    private String rec_country;
    private String rec_account;
    private String rec_IBAN;
    private String rec_bank;


    private String trade_currecy;
    private String trade_date;
    private BigDecimal trade_sum;
    private String trade_details;



    public UUID getId() {
        return id;
    }

    public String getTrade_currecy() {
        return trade_currecy;
    }

    public void setTrade_currecy(String trade_currecy) {
        this.trade_currecy = trade_currecy;
    }

    public void setSen_name(String sen_name) {
        this.sen_name = sen_name;
    }

    public void setSen_document(String sen_document) {
        this.sen_document = sen_document;
    }

    public void setSen_account(String sen_account) {
        this.sen_account = sen_account;
    }

    public void setRec_name(String rec_name) {
        this.rec_name = rec_name;
    }

    public void setRec_document(String rec_document) {
        this.rec_document = rec_document;
    }

    public void setRec_country(String rec_country) {
        this.rec_country = rec_country;
    }

    public void setRec_account(String rec_account) {
        this.rec_account = rec_account;
    }

    public void setRec_IBAN(String rec_IBAN) {
        this.rec_IBAN = rec_IBAN;
    }

    public void setRec_bank(String rec_bank) {
        this.rec_bank = rec_bank;
    }

    public void setTrade_date(String trade_date) {
        this.trade_date = trade_date;
    }

    public void setTrade_sum(BigDecimal trade_sum) {
        this.trade_sum = trade_sum;
    }

    public void setTrade_details(String trade_details) {
        this.trade_details = trade_details;
    }

    public String getSen_name() {
        return sen_name;
    }

    public String getSen_document() {
        return sen_document;
    }

    public String getSen_account() {
        return sen_account;
    }

    public String getRec_name() {
        return rec_name;
    }

    public String getRec_document() {
        return rec_document;
    }

    public String getRec_country() {
        return rec_country;
    }

    public String getRec_account() {
        return rec_account;
    }

    public String getRec_IBAN() {
        return rec_IBAN;
    }

    public String getRec_bank() {
        return rec_bank;
    }

    public String getTrade_date() {
        return trade_date;
    }

    public BigDecimal getTrade_sum() {
        return trade_sum;
    }

    public String getTrade_details() {
        return trade_details;
    }
}
