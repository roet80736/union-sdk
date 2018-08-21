<%--
  Created by IntelliJ IDEA.
  User: dsq
  Date: 2017/12/21
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>全民付移动c扫b接口调用示例</title>
</head>
<body>
    全民付移动C扫码B接口调用示例：<br>
    <a href="/bills/toPageGetQrCode.do">获取二维码</a><br>
<%--    <a href="/bills/toPageGetQrCode.do">更新二维码</a><br>--%>
    <a href="/bills/toCloseQRCode.do">关闭二维码</a><br>
    <a href="/bills/toQueryQRCodeInfo.do">获取二维码静态信息</a><br>
    <a href="/bills/toBillQuery.do">账单查询</a><br>
    <a href="/bills/toRefund.do">交易退款</a><br>
<%--    <a href="/bills/toQueryLastQRCode.do">根据商户终端号查询此台终端最后一笔详单情况</a><br>--%>
    <br>
<%--    <a href="/bills/toPagePayResult.do">跳转至支付完成页面</a>--%>
    <a href="/bills/toCheckSignPage.do">模拟对银商平台的支付通知进行验签</a>
    <br>
</body>
</html>
