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
    <title>关闭二维码</title>
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
            当二维码的内容需要变更，且旧的二维码已经以实体形式发布时，安全起见，<br>
            可调用本接口关闭旧的二维码，使之无效（即：用户扫码后不会再产生交易）。
        </span>
    </div>

    <div align="left">
        <table border="1">
            <tr align="left">
                <td width="21%">二维码ID:</td>
                <td width="79%"><input style="width: 100%" type="text" name="qrCodeId" id="qrCodeId" placeholder="二维码ID（调用获取二维码接口时返回的billQRCode字段）"/>
                    <span style="color: #b92c28" id="qrCodeId_span2"></span></td></td>
            </tr>
            <tr align="left">
                <td><input type="button" id="closeQRCode" style="color: #398439;font-size: large" value="关闭二维码"/></td>
            </tr>
        </table>
    </div>

    <div align="left">
        <span style="color: #b92c28;font-size: large;font-style: normal">返回结果：</span>
        <span id="resultMsg" style="color: #b92c28;font-size: larger;font-style: normal"></span>
<%--        <tr>
            <td><div id="resultMsg"/></td>
        </tr>--%>
    </div>
    <div align="left">
        <table border="1">
            <tr align="left">
                <td width="18%">二维码ID:</td>
                <td width="82%"><span style="width: 100%;color: #b92c28" type="text" name="qrCodeId_span" id="qrCodeId_span"></span></td>
            </tr>
            <tr align="left">
                <td>商户号:</td>
                <td><span style="width: 100%" type="text" name="mid" id="mid"></span></td>
            </tr>
            <tr align="left">
                <td>终端号:</td>
                <td><span style="width: 100%" type="text" name="tid" id="tid"></span></td>
            </tr>
            <tr align="left">
                <td>来源系统:</td>
                <td><span style="width: 100%" type="text" name="msgSrc" id="msgSrc"></span></td>
            </tr>
            <tr align="left">
                <td>消息类型:</td>
                <td><span style="width: 100%" type="text" name="msgType" id="msgType"></span></td>
            </tr>
            <tr align="left">
                <td width="18%">说明:</td>
                <td width="82%"><input style="width: 100%;color: #b92c28;" type="text" value="若显示其他参数，请结合接口文档自行修改"/><a id="goback" style="color: blue" href="/">返回首页</a></td>
            </tr>
        </table>
    </div>
    <script type="text/javascript">
        $(function () {
            //button点击次数限制
            var clickNum = 0;
            $("#closeQRCode").click(function(){
                closeQRCode();
                clickNum++;
                if(clickNum >= 5){
                    $("#closeQRCode").attr("disabled",true);
                }
            });
        });
    </script>
</body>
</html>
