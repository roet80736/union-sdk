<%--
  Created by IntelliJ IDEA.
  User: dsq
  Date: 2017/12/21
  Time: 15:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<html>
<head>
    <title>获取二维码页面</title>
<%--    jquery--%>
<%--    <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>--%>
    <script type="text/javascript"
            src="/js/jquery/jquery-1.9.1.min.js"></script>
<%--    jquery-qrcode--%>
<%--    <script src="http://cdn.staticfile.org/jquery.qrcode/1.0/jquery.qrcode.min.js"></script>--%>
    <script type="text/javascript"
            src="/js/jquery-qrcode/jquery.qrcode.min.js"></script>
<%--    引入common.js--%>
    <script src="/js/common.js"></script>
</head>
<body>
    <div>
        <span style="color: #b92c28;font-size: larger;font-style: normal">
            烦请详细阅读对接资料！！！<br>
            下列待填参数多数已在后台yudanParams.properties文件中配置（为了便于理解 也将其显示了出来），少数需要填写<br>
            详见见对接资料中 接口文档、测试参数 和 对接流程手册！！，<br>
            请求签名功能 可以复用对接资料中 签名工具！！！
        </span>
    </div>
    <div align="left">
        <table border="1">
            <tr align="left" style="font-size: xx-large;font-style: normal">
                <td width="30%">请求地址：</td>
                <td width="70%"><input style="width: 100%" type="text" name="url" id="url"
                                       placeholder="后台已配置，值为：https://qr-test2.chinaums.com/netpay-route-server/api/"/></td>
            </tr>
            <tr align="left">
                <td>商户号:</td>
                <td><input style="width: 100%" type="text" name="mid" id="mid" placeholder="后台已配置，值为：898340149000005"/></td>
            </tr>
            <tr align="left">
                <td>终端号:</td>
                <td><input style="width: 100%" type="text" name="tid" id="tid" placeholder="后台已配置，值为：88880001"/></td>
            </tr>
            <tr align="left">
                <td>机构商户号:</td>
                <td><input style="width: 100%" type="text" name="instMid" id="instMid" placeholder="后台已配置，值为：QRPAYDEFAULT"/></td>
            </tr>
            <tr align="left">
                <td>来源系统:</td>
                <td><input style="width: 100%" type="text" name="msgSrc" id="msgSrc" placeholder="后台已配置，值为：WWW.TEST.COM"/></td>
            </tr>
            <tr align="left">
                <td>消息类型:</td>
                <td><input style="width: 100%" type="text" name="msgType" id="msgType" placeholder="后台已配置，值为：bills.getQRCode"/></td>
            </tr>
            <tr align="left">
                <td>账单号:</td>
                <td><input style="width: 100%" type="text" name="billNo" id="billNo" placeholder="后台自动生成，字段值须以四位msgSrcId开头"/></td>
            </tr>
            <tr align="left">
                <td>账单日期:</td>
                <td><input style="width: 100%" type="text" name="billDate" id="billDate" placeholder="后台自动生成，默认当天，格式：yyyy-MM-dd"/></td>
            </tr>
            <tr align="left">
                <td>时间戳:</td>
                <td><input style="width: 100%" type="text" name="requestTimestamp" id="requestTimestamp" placeholder="后台自动生成，默认当下时间，格式：yyyy-MM-dd hh:mm:ss"/></td>
            </tr>
            <tr align="left" style="font-size: xx-large;font-style: normal">
                <td>通讯秘钥:</td>
                <td><input style="width: 100%" type="text" name="key" id="key" placeholder="后台已配置，值为：fcAmtnx7MwismjWNhNKdHC44mNXtnEQeJkRrhKJwyrW2ysRR"/></td>
            </tr>
            <tr align="left">
                <td style="color: #b92c28">金额:</td>
                <td>
                    <input style="width: 100%" type="text" name="totalAmount" id="totalAmount" placeholder="自行填写，单位：元，例如 0.01"/><br>
                    <span style="color: #b92c28" id="totalAmount_span"></span>
                </td>
            </tr>
            <tr align="left">
                <td style="color: #b92c28">后台通知url:</td>
                <td><input style="width: 100%" type="text" name="notifyUrl" id="notifyUrl" placeholder="自行填写，值为：http://（接受通知的域名或ip端口）/bills/notify.do，商户后台url，支付成功后银商平台会将支付通知发至该url"
                value="http://172.27.49.214:8080/bills/notify.do"/></td>
            </tr>
            <tr align="left">
                <td style="color: #b92c28">用户端跳转url:</td>
                <td><input style="width: 100%" type="text" name="returnUrl" id="returnUrl" placeholder="自行填写，用户端url，支付成功后用户端会跳转至该url"
                value="www.baidu.com"/></td>
            </tr>
            <tr>
                <td><div id="resultMsg"/></td>
                <td><div id="qr" align="left" style="width: 50%;height: 100px"/></td>
            </tr>
            <tr align="left">
                <td><input type="button" id="getQrCode" style="color: #398439;font-size: large" value="获取二维码"/></td>
                <td><a id="goback" style="color: blue" href="/">返回首页</a></td>
            </tr>
        </table>
    </div>
    <script type="text/javascript">
        $(function () {
            //button点击次数限制
            var clickNum = 0;
            $("#getQrCode").click(function(){
                getQrCode();
                clickNum++;
                if(clickNum >= 5){
                    $("#getQrCode").attr("disabled",true);
                }
            });

        });
    </script>

</body>
</html>
