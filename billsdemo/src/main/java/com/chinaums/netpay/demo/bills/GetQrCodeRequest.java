package com.chinaums.netpay.demo.bills;

import lombok.Data;

@Data
public class GetQrCodeRequest {
//    public String url = "";
//    public String mid = "";
//    public String tid = "";
//    public String instMid = "";
//    public String msgSrc = "";
//    public String msgType = "";
    public String billDate = "";
    public String totalAmount = "";

//    public String key = "";

    @Override
    public String toString() {
        return "GetQrCodeRequest{" +
                "billDate='" + billDate + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                '}';
    }
}
