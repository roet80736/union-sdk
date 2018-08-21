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
    <title>扫码支付已完成</title>
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
        <span style="color: yellowgreen;font-size: xx-large;font-style: normal">
            已完成支付<br>
        </span>
    </div>
    <div>
        <span style="color: green;font-size: larger;font-style: normal">
            主动调银商平台交易查询接口，查询交易结果<br>
            <a style="color: blue" href="/bills/toBillQuery.do">交易查询</a><br>
            或<br>
            调银商平台退款接口，进行退款<br>
            <a style="color: blue" href="/bills/toTradeRefund.do">交易退款</a>
        </span>
    </div>
</body>
</html>
