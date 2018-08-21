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
    <title>交易退款</title>
    <script type="text/javascript"
            src="/js/jquery/jquery-1.9.1.min.js"></script>
    <script type="text/javascript"
            src="/js/jquery-qrcode/jquery.qrcode.min.js"></script>
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
            退款只能操作一次，退款金额小于支付金额!!<br>
            当成功交易之后一段时间内，由于买家或商户的原因需要退款时，<br>
            商户可以通过本接口将支付款退还给买家，<br>
            退款请求验证成功之后，银商将通知支付渠道方按照退款规则把支付款按原路退回到买家帐号上。
        </span>
    </div>

    <div align="left">
        <table border="1">
            <tr align="left">
                <td width="21%">账单号:</td>
                <td width="79%"><input style="width: 100%" type="text" name="billNo" id="billNo" placeholder="请输入账单号（支付后的账单页面上有显示）"/>
                    <span style="color: #b92c28" id="billNo_span2"></span></td></td></td>
            </tr>
            <tr align="left">
                <td>账单日期:</td>
                <td><input style="width: 100%" type="text" name="billDate" id="billDate" placeholder="请输入账单日期（支付后的账单页面上有显示）"/>
                    <span style="color: #b92c28" id="billDate_span2"></span></td></td></td>
            </tr>
            <tr align="left">
                <td>退款金额:</td>
                <td><input style="width: 100%" type="text" name="refundAmount" id="refundAmount" placeholder="（单位：元。只能操作一次退款，退款金额小于支付金额）"/>
                    <span style="color: #b92c28" id="refundAmount_span2"></span></td></td></td>
            </tr>
            <tr align="left">
                <td><input type="button" id="refund" style="color: #398439;font-size: large" value="交易退款"/></td>
            </tr>
        </table>
    </div>

    <div align="left">
        <span style="color: #b92c28;font-size: large;font-style: normal">退款结果：</span>
        <span id="resultMsg" style="color: #b92c28;font-size: larger;font-style: normal"></span>
    </div>
    <div align="left">
        <table border="1">
            <tr align="left">
                <td width="20%">退款金额:</td>
                <td width="80%"><span style="width: 100%;color: #b92c28" type="text" name="refundAmount_span" id="refundAmount_span"></span></td>
            </tr>
            <tr align="left">
                <td width="20%">支付金额:</td>
                <td width="80%"><span style="width: 100%;color: #b92c28" type="text" name="totalAmount" id="totalAmount"></span></td>
            </tr>
            <tr align="left">
                <td>银商退款单号:</td>
                <td><span style="width: 100%" type="text" name="refundOrderId" id="refundOrderId"></span></td>
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
                <td>机构商户号:</td>
                <td><span style="width: 100%" type="text" name="instMid" id="instMid"></span></td>
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
        $(function () {
            //button点击次数限制
            var clickNum = 0;
            $("#refund").click(function(){
                refund();
                clickNum++;
                if(clickNum >= 5){
                    $("#refund").attr("disabled",true);
                }
            });
        });
    </script>
</body>
</html>
