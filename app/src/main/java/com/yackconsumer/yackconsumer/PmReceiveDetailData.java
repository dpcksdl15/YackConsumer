package com.yackconsumer.yackconsumer;

public class PmReceiveDetailData {
    private String med_nm;
    private String med_price;
    private String med_count;
    private String med_tot_price;

    public PmReceiveDetailData(String med_nm, String med_price, String med_count, String med_tot_price){
        this.med_nm = med_nm;
        this.med_price = med_price;
        this.med_count = med_count;
        this.med_tot_price = med_tot_price;
    }


    public String getMed_nm() {
        return med_nm;
    }


    public String getMed_price() {
        return med_price;
    }


    public String getMed_count() {
        return med_count;
    }


    public String getMed_tot_price() {
        return med_tot_price;
    }

}
