<%--
  Created by IntelliJ IDEA.
  User: dsq
  Date: 2017/12/21
  Time: 15:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%--<meta http-equiv="Content-Type" content="text/html; charset=gbk">--%>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>模拟对银商平台的支付通知进行验签</title>
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
        <span style="color: #b92c28;font-size:small;font-style: normal">
            烦请详细阅读接口文档等对接资料！！
        </span>
    </div>
    <div>
        <span style="color: #b92c28;font-size: small;font-style: normal">
            将收到的支付通知字串原样粘贴在输入框 (也可以是没有附上秘钥的待签字串 或  get请求报文中的键值对)<br>
        </span>
    </div>

    <div align="left">
        <table border="1">
            <tr align="left">
                <td width="21%">银商平台发出的原样支付通知字串(也可以是没有附上秘钥的待签字串 或  get请求报文中的键值对):</td>
                <td width="79%"><input style="width: 100%" type="text" name="notifyStr" id="notifyStr" placeholder="银商平台发出的原样支付通知字串"/>
                    <span style="color: #b92c28" id="billNo_span2"></span></td>
            </tr>
            <tr align="left">
                <td><input type="button" id="checkSign" style="color: #398439;font-size: large" value="模拟获取待签字串和签名"/></td>
            </tr>
        </table>
    </div>

    <div align="left">
        <span style="color: #b92c28;font-size: large;font-style: normal">模拟结果：</span>
        <span id="resultMsg" style="color: #b92c28;font-size: larger;font-style: normal"></span>
<%--        <tr>
            <td><div id="resultMsg"/></td>
        </tr>--%>
    </div>
    <div align="left">
        <table border="1">
            <tr align="left">
                <td width="20%">待签字串:</td>
                <td width="80%"><span style="width: 100%;color: #b92c28" type="text" name="preStr" id="preStr"></span></td>
            </tr>
            <tr align="left">
                <td>签名:</td>
                <td><span style="width: 100%" type="text" name="sign" id="sign"></span></td>
            </tr>
        </table>
    </div>

    <script type="text/javascript">
        $(function () {
            //button点击次数限制
            var clickNum = 0;
            $("#checkSign").click(function(){
                checkSign();
                clickNum++;
                if(clickNum >= 5){
                    $("#checkSign").attr("disabled",true);
                }
            });
        });
    </script>
</body>
</html>
