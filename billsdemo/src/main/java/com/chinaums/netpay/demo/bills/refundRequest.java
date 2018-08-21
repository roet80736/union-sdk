package com.chinaums.netpay.demo.bills;

import lombok.Data;

@Data
public class refundRequest {
    public String billDate = "";
    public String billNo = "";
    public String refundAmount = "";

    @Override
    public String toString() {
        return "refundRequest{" +
                "billDate='" + billDate + '\'' +
                ", billNo='" + billNo + '\'' +
                ", refundAmount='" + refundAmount + '\'' +
                '}';
    }
}
