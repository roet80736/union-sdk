package com.chinaums.netpay.demo.bills;

//import net.sf.json.JSON;
import net.sf.json.JSONObject;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.chinaums.netpay.demo.bills.Util.makeSign;

@Controller
@RequestMapping("/bills")
public class BillsDemoController {
    //读取资源配置参数
    @Value("${url}")
    private String APIurl;
    @Value("${mid}")
    private String mid;
    @Value("${tid}")
    private String tid;
    @Value("${instMid}")
    private String instMid;
    @Value("${msgSrc}")
    private String msgSrc;
    @Value("${key}")
    private String key;

    @Value("${msgType_getQRCode}")
    private String msgType_getQRCode;
    @Value("${msgType_refund}")
    private String msgType_refund;
    @Value("${msgType_query}")
    private String msgType_query;
    @Value("${msgType_queryLastQRCode}")
    private String msgType_queryLastQRCode;
    @Value("${msgType_queryQRCodeInfo}")
    private String msgType_queryQRCodeInfo;
    @Value("${msgType_closeQRCode}")
    private String msgType_closeQRCode;

    /**
     * 页面跳转
     * */
    //跳转获取二维码页面
    @RequestMapping(value = "/toPageGetQrCode.do",method = RequestMethod.GET)
    public String toPageGetQrCode(){
        return "/WEB-INF/views/jsp/getQrCodePage.jsp";
    }

    //临时使用：跳转支付完成页面
    @RequestMapping(value = "/toPagePayResult.do",method = RequestMethod.GET)
    public String toPayResult(){
        return "/WEB-INF/views/jsp/payResult.jsp";
    }

    //跳转交易查询页面
    @RequestMapping(value = "/toBillQuery.do",method = RequestMethod.GET)
    public String toBillQuery(){
        return "/WEB-INF/views/jsp/billQuery.jsp";
    }

    //跳转交易查询页面
    @RequestMapping(value = "/toRefund.do",method = RequestMethod.GET)
    public String toRefund(){
        return "/WEB-INF/views/jsp/refund.jsp";
    }

    //跳转 根据商户终端号查询此台终端最后一笔详单情况 页面
    @RequestMapping(value = "/toQueryLastQRCode.do",method = RequestMethod.GET)
    public String toQueryLastQRCode(){
        return "/WEB-INF/views/jsp/queryLastQRCode.jsp";
    }

    //跳转 查询二维码静态信息 页面
    @RequestMapping(value = "/toQueryQRCodeInfo.do",method = RequestMethod.GET)
    public String toQueryQRCodeInfo(){
        return "/WEB-INF/views/jsp/queryQRCodeInfo.jsp";
    }

    //跳转 关闭二维码 页面
    @RequestMapping(value = "/toCloseQRCode.do",method = RequestMethod.GET)
    public String toCloseQRCode(){
        return "/WEB-INF/views/jsp/closeQRCode.jsp";
    }

    //跳转模拟对银商平台的支付通知进行验签页面
    @RequestMapping(value = "/toCheckSignPage.do",method = RequestMethod.GET)
    public String toCheckSignPage(){
        return "/WEB-INF/views/jsp/checkSign.jsp";
    }

    /**
     * 获取二维码模块
     * */
    //获取二维码页面
    @ResponseBody
    @RequestMapping(value = "/getQrCode.do",method = RequestMethod.POST)
    public String getQrCode(HttpServletResponse response,GetQrCodeRequest requestParams){
        System.out.println("请求参数对象："+requestParams);
        //组织请求报文
        JSONObject json = new JSONObject();
        json.put("mid", mid);
        json.put("tid", tid);
        json.put("msgType", msgType_getQRCode);
        json.put("msgSrc", msgSrc);
        json.put("instMid", instMid);
        json.put("billNo", Util.genMerOrderId("3194"));

        //是否要在商户系统下单，看商户需求  createBill()

        json.put("billDate",DateFormatUtils.format(new Date(),"yyyy-MM-dd"));
        json.put("totalAmount", requestParams.getTotalAmount());
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

        Map<String, String> paramsMap = Util.jsonToMap(json);
        paramsMap.put("sign", makeSign(key, paramsMap));
        System.out.println("paramsMap："+paramsMap);

        String strReqJsonStr = JSON.toJSONString(paramsMap);
        System.out.println("strReqJsonStr:"+strReqJsonStr);

        //调用银商平台获取二维码接口
        HttpURLConnection httpURLConnection = null;
        BufferedReader in = null;
        PrintWriter out = null;
//        OutputStreamWriter out = null;
        String resultStr = null;
        Map<String,String> resultMap = new HashMap<String,String>();
        if (!StringUtils.isNotBlank(APIurl)) {
            resultMap.put("errCode","URLFailed");
            resultStr = JSONObject.fromObject(resultMap).toString();
            return resultStr;
        }

        try {
            URL url = new URL(APIurl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content_Type","application/json");
            httpURLConnection.setRequestProperty("Accept_Charset","UTF-8");
            httpURLConnection.setRequestProperty("contentType","UTF-8");
            //发送POST请求参数
            out = new PrintWriter(httpURLConnection.getOutputStream());
//            out = new OutputStreamWriter(httpURLConnection.getOutputStream(),"utf-8");
            out.write(strReqJsonStr);
//            out.println(strReqJsonStr);
            out.flush();

            //读取响应
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuffer content = new StringBuffer();
                String tempStr = null;
                in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                while ((tempStr=in.readLine()) != null){
                    content.append(tempStr);
                }
                System.out.println("content:"+content.toString());

                //转换成json对象
                com.alibaba.fastjson.JSONObject respJson = JSON.parseObject(content.toString());
                String resultCode = respJson.getString("errCode");
                resultMap.put("errCode",resultCode);
                if (resultCode.equals("SUCCESS")) {
                    String billQRCode = (String) respJson.get("billQRCode");
                    resultMap.put("billQRCode",billQRCode);
                    resultMap.put("respStr",respJson.toString());
                }else {
                    resultMap.put("respStr",respJson.toString());
                }
                resultStr = JSONObject.fromObject(resultMap).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("errCode","HttpURLException");
            resultMap.put("msg","调用银商接口出现异常："+e.toString());
            resultStr = JSONObject.fromObject(resultMap).toString();
            return resultStr;
        }finally {
            if (out != null) {
                out.close();
            }
            httpURLConnection.disconnect();
        }

        System.out.println("resultStr:"+resultStr);
        return resultStr;
    }

    //暂未使用：请求转发，用户端扫码时 二维码链接对应该方法，并由该方法转发至银商平台
    @RequestMapping(value = "/sendBillQRCode.do",method = RequestMethod.GET)
    public String sendBillQRCode(HttpServletRequest request,HttpServletResponse response){
        //接受参数
        String billQRCode = request.getParameter("billQRCode");
        try {
            response.sendRedirect(billQRCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 支付通知模块
     * */
    //用于：在支付成功后 接受银商平台发送的支付结果通知
    @RequestMapping(value = "/notify.do",method = RequestMethod.POST)
    public String getNotify(HttpServletRequest request,HttpServletResponse response){
        //接受参数
        Map<String,String> params = Util.getRequestParams(request);
        System.out.println("params:"+params);
        //验签

        //更新商户系统的订单

        //跳转支付完成页面
        return "/WEB-INF/views/jsp/payResult.jsp";
    }

    /**
     * 账单查询模块
    */
    //账单查询
    @ResponseBody
    @RequestMapping(value = "/billQuery.do",method = RequestMethod.POST)
    public String billQuery(HttpServletResponse response,BillQueryRequest requestParams){
        System.out.println("请求参数对象："+requestParams);
        //组织请求报文
        JSONObject json = new JSONObject();
        json.put("mid", mid);
        json.put("tid", tid);
        json.put("msgType", msgType_query);
        json.put("msgSrc", msgSrc);
        json.put("instMid", instMid);
        json.put("billNo", requestParams.getBillNo());

        //是否要在商户系统下单，看商户需求  createBill()

        json.put("billDate",requestParams.getBillDate());
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

        Map<String, String> paramsMap = Util.jsonToMap(json);
        paramsMap.put("sign", makeSign(key, paramsMap));
        System.out.println("paramsMap："+paramsMap);

        String strReqJsonStr = JSON.toJSONString(paramsMap);
        System.out.println("strReqJsonStr:"+strReqJsonStr);

        //调用银商平台获取二维码接口
        HttpURLConnection httpURLConnection = null;
        BufferedReader in = null;
        PrintWriter out = null;
//        OutputStreamWriter out = null;
        String resultStr = null;
        Map<String,String> resultMap = new HashMap<String,String>();
        if (!StringUtils.isNotBlank(APIurl)) {
            resultMap.put("errCode","URLFailed");
            resultStr = JSONObject.fromObject(resultMap).toString();
            return resultStr;
        }

        try {
            URL url = new URL(APIurl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content_Type","application/json");
            httpURLConnection.setRequestProperty("Accept_Charset","UTF-8");
            httpURLConnection.setRequestProperty("contentType","UTF-8");
            //发送POST请求参数
            out = new PrintWriter(httpURLConnection.getOutputStream());
//            out = new OutputStreamWriter(httpURLConnection.getOutputStream(),"utf-8");
            out.write(strReqJsonStr);
//            out.println(strReqJsonStr);
            out.flush();

            //读取响应
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuffer content = new StringBuffer();
                String tempStr = null;
                in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                while ((tempStr=in.readLine()) != null){
                    content.append(tempStr);
                }
                System.out.println("content:"+content.toString());

                //转换成json对象
                com.alibaba.fastjson.JSONObject respJson = JSON.parseObject(content.toString());
                String resultCode = respJson.getString("errCode");
                resultMap.put("errCode",resultCode);
                resultMap.put("respStr",respJson.toString());
                resultStr = JSONObject.fromObject(resultMap).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("errCode","HttpURLException");
            resultMap.put("msg","调用银商接口出现异常："+e.toString());
            resultStr = JSONObject.fromObject(resultMap).toString();
            return resultStr;
        }finally {
            if (out != null) {
                out.close();
            }
            httpURLConnection.disconnect();
        }

        System.out.println("resultStr:"+resultStr);
        return resultStr;
    }

    /**
     * 交易退款模块
     * */

    @ResponseBody
    @RequestMapping(value = "/refund.do",method = RequestMethod.POST)
    public String refund(HttpServletResponse response,refundRequest requestParams){
        System.out.println("请求参数对象："+requestParams);
        //组织请求报文
        JSONObject json = new JSONObject();
        json.put("mid", mid);
        json.put("tid", tid);
        json.put("msgType", msgType_refund);
        json.put("msgSrc", msgSrc);
        json.put("instMid", instMid);
        json.put("billNo", requestParams.getBillNo());

        //是否要在商户系统下单，看商户需求  createBill()

        json.put("billDate",requestParams.getBillDate());
        json.put("refundAmount",requestParams.getRefundAmount());
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

        Map<String, String> paramsMap = Util.jsonToMap(json);
        paramsMap.put("sign", makeSign(key, paramsMap));
        System.out.println("paramsMap："+paramsMap);

        String strReqJsonStr = JSON.toJSONString(paramsMap);
        System.out.println("strReqJsonStr:"+strReqJsonStr);

        //调用银商平台获取二维码接口
        HttpURLConnection httpURLConnection = null;
        BufferedReader in = null;
        PrintWriter out = null;
//        OutputStreamWriter out = null;
        String resultStr = null;
        Map<String,String> resultMap = new HashMap<String,String>();
        if (!StringUtils.isNotBlank(APIurl)) {
            resultMap.put("errCode","URLFailed");
            resultStr = JSONObject.fromObject(resultMap).toString();
            return resultStr;
        }

        try {
            URL url = new URL(APIurl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content_Type","application/json");
            httpURLConnection.setRequestProperty("Accept_Charset","UTF-8");
            httpURLConnection.setRequestProperty("contentType","UTF-8");
            //发送POST请求参数
            out = new PrintWriter(httpURLConnection.getOutputStream());
//            out = new OutputStreamWriter(httpURLConnection.getOutputStream(),"utf-8");
            out.write(strReqJsonStr);
//            out.println(strReqJsonStr);
            out.flush();

            //读取响应
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuffer content = new StringBuffer();
                String tempStr = null;
                in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                while ((tempStr=in.readLine()) != null){
                    content.append(tempStr);
                }
                System.out.println("content:"+content.toString());

                //转换成json对象
                com.alibaba.fastjson.JSONObject respJson = JSON.parseObject(content.toString());
                String resultCode = respJson.getString("errCode");
                resultMap.put("errCode",resultCode);
                resultMap.put("respStr",respJson.toString());
                resultStr = JSONObject.fromObject(resultMap).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("errCode","HttpURLException");
            resultMap.put("msg","调用银商接口出现异常："+e.toString());
            resultStr = JSONObject.fromObject(resultMap).toString();
            return resultStr;
        }finally {
            if (out != null) {
                out.close();
            }
            httpURLConnection.disconnect();
        }

        System.out.println("resultStr:"+resultStr);
        return resultStr;
    }


    /**
     * 根据商户终端号查询此台终端最后一笔详单情况
     * */

    @ResponseBody
    @RequestMapping(value = "/queryLastQRCode.do",method = RequestMethod.POST)
    public String queryLastQRCode(HttpServletResponse response){
        //组织请求报文
        JSONObject json = new JSONObject();
        json.put("mid", mid);
        json.put("tid", tid);
        json.put("msgType", msgType_queryLastQRCode);
        json.put("msgSrc", msgSrc);
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

        Map<String, String> paramsMap = Util.jsonToMap(json);
        paramsMap.put("sign", makeSign(key, paramsMap));
        System.out.println("paramsMap："+paramsMap);

        String strReqJsonStr = JSON.toJSONString(paramsMap);
        System.out.println("strReqJsonStr:"+strReqJsonStr);

        //调用银商平台获取二维码接口
        HttpURLConnection httpURLConnection = null;
        BufferedReader in = null;
        PrintWriter out = null;
//        OutputStreamWriter out = null;
        String resultStr = null;
        Map<String,String> resultMap = new HashMap<String,String>();
        if (!StringUtils.isNotBlank(APIurl)) {
            resultMap.put("errCode","URLFailed");
            resultStr = JSONObject.fromObject(resultMap).toString();
            return resultStr;
        }

        try {
            URL url = new URL(APIurl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content_Type","application/json");
            httpURLConnection.setRequestProperty("Accept_Charset","UTF-8");
            httpURLConnection.setRequestProperty("contentType","UTF-8");
            //发送POST请求参数
            out = new PrintWriter(httpURLConnection.getOutputStream());
//            out = new OutputStreamWriter(httpURLConnection.getOutputStream(),"utf-8");
            out.write(strReqJsonStr);
//            out.println(strReqJsonStr);
            out.flush();

            //读取响应
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuffer content = new StringBuffer();
                String tempStr = null;
                in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                while ((tempStr=in.readLine()) != null){
                    content.append(tempStr);
                }
                System.out.println("content:"+content.toString());

                //转换成json对象
                com.alibaba.fastjson.JSONObject respJson = JSON.parseObject(content.toString());
                String resultCode = respJson.getString("errCode");
                resultMap.put("errCode",resultCode);
                resultMap.put("respStr",respJson.toString());
                resultStr = JSONObject.fromObject(resultMap).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("errCode","HttpURLException");
            resultMap.put("msg","调用银商接口出现异常："+e.toString());
            resultStr = JSONObject.fromObject(resultMap).toString();
            return resultStr;
        }finally {
            if (out != null) {
                out.close();
            }
            httpURLConnection.disconnect();
        }

        System.out.println("resultStr:"+resultStr);
        return resultStr;
    }

    /**
     * 查询二维码静态信息接口
     * */
    @ResponseBody
    @RequestMapping(value = "/queryQRCodeInfo.do",method = RequestMethod.POST)
    public String queryQRCodeInfo(HttpServletResponse response, String qrCodeId){
        //组织请求报文
        JSONObject json = new JSONObject();
        json.put("msgType", msgType_queryQRCodeInfo);
        json.put("msgSrc", msgSrc);
        json.put("qrCodeId", qrCodeId);
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

        Map<String, String> paramsMap = Util.jsonToMap(json);
        paramsMap.put("sign", makeSign(key, paramsMap));
        System.out.println("paramsMap："+paramsMap);

        String strReqJsonStr = JSON.toJSONString(paramsMap);
        System.out.println("strReqJsonStr:"+strReqJsonStr);

        //调用银商平台获取二维码接口
        HttpURLConnection httpURLConnection = null;
        BufferedReader in = null;
        PrintWriter out = null;
//        OutputStreamWriter out = null;
        String resultStr = null;
        Map<String,String> resultMap = new HashMap<String,String>();
        if (!StringUtils.isNotBlank(APIurl)) {
            resultMap.put("errCode","URLFailed");
            resultStr = JSONObject.fromObject(resultMap).toString();
            return resultStr;
        }

        try {
            URL url = new URL(APIurl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content_Type","application/json");
            httpURLConnection.setRequestProperty("Accept_Charset","UTF-8");
            httpURLConnection.setRequestProperty("contentType","UTF-8");
            //发送POST请求参数
            out = new PrintWriter(httpURLConnection.getOutputStream());
//            out = new OutputStreamWriter(httpURLConnection.getOutputStream(),"utf-8");
            out.write(strReqJsonStr);
//            out.println(strReqJsonStr);
            out.flush();

            //读取响应
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuffer content = new StringBuffer();
                String tempStr = null;
                in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                while ((tempStr=in.readLine()) != null){
                    content.append(tempStr);
                }
                System.out.println("content:"+content.toString());

                //转换成json对象
                com.alibaba.fastjson.JSONObject respJson = JSON.parseObject(content.toString());
                String resultCode = respJson.getString("errCode");
                resultMap.put("errCode",resultCode);
                resultMap.put("respStr",respJson.toString());
                resultStr = JSONObject.fromObject(resultMap).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("errCode","HttpURLException");
            resultMap.put("msg","调用银商接口出现异常："+e.toString());
            resultStr = JSONObject.fromObject(resultMap).toString();
            return resultStr;
        }finally {
            if (out != null) {
                out.close();
            }
            httpURLConnection.disconnect();
        }

        System.out.println("resultStr:"+resultStr);
        return resultStr;
    }


    /**
     * 关闭二维码接口
     * */
    @ResponseBody
    @RequestMapping(value = "/closeQRCode.do",method = RequestMethod.POST)
    public String closeQRCode(HttpServletResponse response, String qrCodeId){
        //组织请求报文
        JSONObject json = new JSONObject();
        json.put("msgType", msgType_closeQRCode);
        json.put("msgSrc", msgSrc);
        json.put("mid", mid);
        json.put("tid", tid);
        json.put("instMid", instMid);
        json.put("qrCodeId", qrCodeId);
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

        Map<String, String> paramsMap = Util.jsonToMap(json);
        paramsMap.put("sign", makeSign(key, paramsMap));
        System.out.println("paramsMap："+paramsMap);

        String strReqJsonStr = JSON.toJSONString(paramsMap);
        System.out.println("strReqJsonStr:"+strReqJsonStr);

        //调用银商平台获取二维码接口
        HttpURLConnection httpURLConnection = null;
        BufferedReader in = null;
        PrintWriter out = null;
//        OutputStreamWriter out = null;
        String resultStr = null;
        Map<String,String> resultMap = new HashMap<String,String>();
        if (!StringUtils.isNotBlank(APIurl)) {
            resultMap.put("errCode","URLFailed");
            resultStr = JSONObject.fromObject(resultMap).toString();
            return resultStr;
        }

        try {
            URL url = new URL(APIurl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content_Type","application/json");
            httpURLConnection.setRequestProperty("Accept_Charset","UTF-8");
            httpURLConnection.setRequestProperty("contentType","UTF-8");
            //发送POST请求参数
            out = new PrintWriter(httpURLConnection.getOutputStream());
//            out = new OutputStreamWriter(httpURLConnection.getOutputStream(),"utf-8");
            out.write(strReqJsonStr);
//            out.println(strReqJsonStr);
            out.flush();

            //读取响应
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuffer content = new StringBuffer();
                String tempStr = null;
                in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                while ((tempStr=in.readLine()) != null){
                    content.append(tempStr);
                }
                System.out.println("content:"+content.toString());

                //转换成json对象
                com.alibaba.fastjson.JSONObject respJson = JSON.parseObject(content.toString());
                String resultCode = respJson.getString("errCode");
                resultMap.put("errCode",resultCode);
                resultMap.put("respStr",respJson.toString());
                resultStr = JSONObject.fromObject(resultMap).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("errCode","HttpURLException");
            resultMap.put("msg","调用银商接口出现异常："+e.toString());
            resultStr = JSONObject.fromObject(resultMap).toString();
            return resultStr;
        }finally {
            if (out != null) {
                out.close();
            }
            httpURLConnection.disconnect();
        }

        System.out.println("resultStr:"+resultStr);
        return resultStr;
    }








    /**
     * 模拟对银商平台的支付通知进行验签
     */
    //账单查询
    @ResponseBody
    @RequestMapping(value = "/checkSign.do",method = RequestMethod.POST)
    public String checkSign(HttpServletResponse response,BillQueryRequest requestParams,String notifyStr){
        System.out.println("请求参数对象："+notifyStr);

        String resultStr = NotifyUtilTest.makePreStrs(notifyStr);

        System.out.println("resultStr:"+resultStr);
        return resultStr;
    }

}
