package com.chinaums.netpay.demo.bills;

/**
 * Created by dsq on 2018/2/22.
 */
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

public class NotifyUtilTest {
    @Test
    public void makePreStrsTest(){
        String preNotifyStrs = "billPayment=%7B%22payTime%22%3A%222018-02-13+20%3A10%3A03%22%2C%22paySeqId%22%3A%2200151100146N%22%2C%22invoiceAmount%22%3A1%2C%22settleDate%22%3A%222018-02-13%22%2C%22buyerId%22%3A%22oOUAZv3ZVItUXZt0UZNuZtCesV94%22%2C%22totalAmount%22%3A1%2C%22couponAmount%22%3A0%2C%22billBizType%22%3A%22bills%22%2C%22buyerPayAmount%22%3A1%2C%22targetOrderId%22%3A%224200000061201802132292046794%22%2C%22payDetail%22%3A%22%E7%8E%B0%E9%87%91%E6%94%AF%E4%BB%980.01%E5%85%83%E3%80%82%22%2C%22merOrderId%22%3A%2231942018021320090608770316100%22%2C%22status%22%3A%22TRADE_SUCCESS%22%2C%22targetSys%22%3A%22WXPay%22%7D&billDesc=%E6%94%BF%E5%BA%9C%E9%9D%9E%E7%A8%8E%E6%94%B6%E5%85%A5%E7%BC%B4%E8%B4%B9&sign=C51E0BA3C8A3881DA8DB097786BE814F&merName=%E4%BB%B2%E6%99%B6%E6%99%B6%E4%BA%8C%E7%BB%B4%E7%A0%81%E6%B5%8B%E8%AF%95&mid=898340149000005&billDate=2018-02-13&tid=88880001&instMid=QRPAYDEFAULT&totalAmount=1&gY=Snwz&createTime=2018-02-13+20%3A09%3A10&billStatus=PAID¬ifyId=911b4376-bf8a-4dc5-a058-459607a6929f&billNo=3194201802132009060877031610&subInst=UMS-MARKET&billQRCode=https%3A%2F%2Fqr-test2.chinaums.com%2Fbills%2FqrCode.do%3Fid%3D31941802138610910207207542&memberId=410800-1916499526";
        makePreStrs(preNotifyStrs);//调用过程中包含的控制台打印
    }

    public static String makePreStrs(String notifyStr){
        String md5Key = "fcAmtnx7MwismjWNhNKdHC44mNXtnEQeJkRrhKJwyrW2ysRR";

        //对支付通知字串进行解码
//        String preNotifyStrs = "billPayment=%7B%22payTime%22%3A%222018-02-13+20%3A10%3A03%22%2C%22paySeqId%22%3A%2200151100146N%22%2C%22invoiceAmount%22%3A1%2C%22settleDate%22%3A%222018-02-13%22%2C%22buyerId%22%3A%22oOUAZv3ZVItUXZt0UZNuZtCesV94%22%2C%22totalAmount%22%3A1%2C%22couponAmount%22%3A0%2C%22billBizType%22%3A%22bills%22%2C%22buyerPayAmount%22%3A1%2C%22targetOrderId%22%3A%224200000061201802132292046794%22%2C%22payDetail%22%3A%22%E7%8E%B0%E9%87%91%E6%94%AF%E4%BB%980.01%E5%85%83%E3%80%82%22%2C%22merOrderId%22%3A%2231942018021320090608770316100%22%2C%22status%22%3A%22TRADE_SUCCESS%22%2C%22targetSys%22%3A%22WXPay%22%7D&billDesc=%E6%94%BF%E5%BA%9C%E9%9D%9E%E7%A8%8E%E6%94%B6%E5%85%A5%E7%BC%B4%E8%B4%B9&sign=C51E0BA3C8A3881DA8DB097786BE814F&merName=%E4%BB%B2%E6%99%B6%E6%99%B6%E4%BA%8C%E7%BB%B4%E7%A0%81%E6%B5%8B%E8%AF%95&mid=898340149000005&billDate=2018-02-13&tid=88880001&instMid=QRPAYDEFAULT&totalAmount=1&gY=Snwz&createTime=2018-02-13+20%3A09%3A10&billStatus=PAID¬ifyId=911b4376-bf8a-4dc5-a058-459607a6929f&billNo=3194201802132009060877031610&subInst=UMS-MARKET&billQRCode=https%3A%2F%2Fqr-test2.chinaums.com%2Fbills%2FqrCode.do%3Fid%3D31941802138610910207207542&memberId=410800-1916499526";
        String preNotifyStrs = notifyStr;
        String trueColors = null;
        try {
            trueColors = URLDecoder.decode(preNotifyStrs, "utf-8");
            trueColors = trueColors.replace("¬","&not");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("对支付通知字串进行解码后为:"+trueColors);

        //将解码后的支付通知字串 转成map
        String[] splitPreStrs=trueColors.split("&");
        String[] subStr = null;
        Map<String,String> map = new HashMap<String,String>();
        for (String str :splitPreStrs) {
            subStr = str.split("=");
            if (subStr[0].equals("sign") || subStr[0] == "sign") {
                System.out.println("支付通知中附带签名："+subStr[0]+"："+subStr[1]);
                //组装待签字串时 去除原有待签字串中的sign
                continue;
            }
            if (subStr[0].equals("billQRCode") || subStr[0] == "billQRCode") {
                map.put(subStr[0],subStr[1]+"="+subStr[2]);
            }else {
                map.put(subStr[0],subStr[1]);
            }
            //            System.out.println(str);
        }
        System.out.println("将解码后的支付通知字串 转成map为:"+map);

        //生成待签字串 和  sign
        String preStrNew = buildSignString(map); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String preStrNew_md5Key = preStrNew + md5Key;
        System.out.println("生成待签字串为："+preStrNew_md5Key);

        String sign = DigestUtils.md5Hex(getContentBytes(preStrNew_md5Key)).toUpperCase();
        System.out.println("生成签名sign为："+sign);

        //返回结果
        String resultStr = null;
        Map<String,String> resultMap = new HashMap<String,String>();
        resultMap.put("preStr",preStrNew_md5Key);
        resultMap.put("sign",sign);
        resultStr = JSONObject.fromObject(resultMap).toString();

        return resultStr;
    }

    // 构建签名字符串
    public static String buildSignString(Map<String, String> params) {

        if (params == null || params.size() == 0) {
            return "";
        }

        List<String> keys = new ArrayList<>(params.size());

        for (String key : params.keySet()) {
            if ("sign".equals(key))
                continue;
            if (StringUtils.isEmpty(params.get(key)))
                continue;
            keys.add(key);
        }

        Collections.sort(keys);

        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                buf.append(key + "=" + value);
            } else {
                buf.append(key + "=" + value + "&");
            }
        }

        return buf.toString();
    }

    // 根据编码类型获得签名内容byte[]
    public static byte[] getContentBytes(String content) {
        try {
            return content.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("签名过程中出现错误");
        }
    }

}
