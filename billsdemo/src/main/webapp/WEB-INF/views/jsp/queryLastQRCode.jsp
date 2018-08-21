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
    <title>根据商户终端号查询此台终端最后一笔详单情况</title>
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
            根据商户号、终端号、台号查询此终端上最后一条交易的二维码信息和金额。
        </span>
    </div>

    <div align="left">
        <table border="1">
            <tr align="left">
                <td width="21%">商户号终端号后台从properties文件中读取</td>
                <td width="79%"><input type="button" id="queryLastQRCode" style="color: #398439;font-size: large" value="根据商户终端号查询"/></td>
            </tr>
        </table>
    </div>

    <div align="left">
        <span style="color: #b92c28;font-size: large;font-style: normal">查询结果：</span>
        <span id="resultMsg" style="color: #b92c28;font-size: larger;font-style: normal"></span>
<%--        <tr>
            <td><div id="resultMsg"/></td>
        </tr>--%>
    </div>
    <div align="left">
        <table border="1">
            <tr align="left">
                <td width="20%">账单金额:</td>
                <td width="80%"><span style="width: 100%;color: #b92c28" type="text" name="totalAmount" id="totalAmount"></span></td>
            </tr>
            <tr align="left">
                <td width="20%">二维码链接:</td>
                <td width="80%"><span style="width: 100%;color: #b92c28" type="text" name="qrCodeUrl" id="qrCodeUrl"></span></td>
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
                <td>账单号:</td>
                <td><span style="width: 100%;color: #b92c28" type="text" name="billNo_span" id="billNo_span"></span></td>
            </tr>
            <tr align="left">
                <td>账单日期:</td>
                <td><span style="width: 100%;color: #b92c28" type="text" name="billDate_span" id="billDate_span"></span></td>
            </tr>
            <tr align="left">
                <td width="20%">说明:</td>
                <td width="80%"><input style="width: 100%;color: #b92c28;" type="text" value="若显示其他参数，请结合接口文档自行修改"/><a id="goback" style="color: blue" href="/">返回首页</a></td>
            </tr>
        </table>
    </div>
    <script type="text/javascript">
        $("#queryLastQRCode").click(function(){
            queryLastQRCode();
        });
    </script>
</body>
</html>
