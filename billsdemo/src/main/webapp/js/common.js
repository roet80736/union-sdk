/**
 * 返回时间戳格式：yyyy-MM-dd HH:mm:ss
 * type:"1":yyyy-MM-dd HH:mm:ss
 *      "2":YYYYMMDDHHmmssSSS
 *      "3":YYYY-MM-dd
 * @returns {String}
 */


/**
 * 获取二维码模块
 */
function getQrCode() {
	//定义变量
	var url = "";
	var params = {};

	//变量赋值
    params.url = $("#url").val();
	params.mid = $("#mid").val();
	params.tid = $("#tid").val();
    params.msgSrc = $("#msgSrc").val();
    params.msgType = $("#msgType").val();
    params.instMid = $("#instMid").val();
    params.billNo = $("#billNo").val();
    params.billDate = $("#billDate").val();
    params.totalAmount = $("#totalAmount").val()*100;
    params.key = $("#key").val();

    //字段校验
    $("#totalAmount_span").empty();
    if(params.totalAmount == null || params.totalAmount == ""){
        $("#totalAmount_span").append("金额为必传字段");
        return ;
    }
	//post请求
	$.post("/bills/getQrCode.do",
		params,
		function (data, status) {
			// alert("data:"+data+"                "+"status:"+status);
			var result = eval("("+data+")");
			var resultErrCode = result.errCode;
            //二维码图片对应该链接，发至自己后台
            // var imageQRCode ="http://172.27.49.224:8080/bills/sendBillQRCode.do?billQRCode="+billQRCode;
			var resultMsg = "";
            // alert("result.billQRCode:"+result.billQRCode);
            if(status == "success" && resultErrCode == "SUCCESS"){
                var billQRCode = result.billQRCode;
				if(billQRCode.length != 0){
					$("#qr").empty();
					$("#qr").qrcode({
						render:"table",
						width:100,
						height:100,
						text:billQRCode
					});
					resultMsg = "微信支付宝等扫码支付";
				}else if (resultErrCode == "URLFailed"){
					resultMsg = "请求地址不对";
				}else if (resultErrCode == "HttpURLException"){
					resultMsg = result.msg;
				}
			}else if (resultErrCode = "BAD_REQUEST"){
                resultMsg = "请求字段输入不对";
            } else {
                resultMsg = "二维码获取,银商平台返回失败";
			}
            $("#resultMsg").empty();
			$("#resultMsg").append(resultMsg);
        });
}


/**
* 交易查询模块
 */
function billQuery() {
    //定义变量
    var url = "";
    var params = {};

    //变量赋值
    params.billNo = $("#billNo").val();
    params.billDate = $("#billDate").val();

    //字段校验
    $("#billNo_span2").empty();
    if(params.billNo == null || params.billNo == ""){
        $("#billNo_span2").append("账单号为必传字段");
        return ;
    }

    $("#billDate_span2").empty();
    if(params.billDate == null || params.billDate == ""){
        $("#billDate_span2").append("账单日期为必传字段");
        return ;
    }

    //post请求
    $.post("/bills/billQuery.do",
        params,
        function (data, status) {
            // alert("data:"+data+"                "+"status:"+status);
            var result = eval("("+data+")");
            var resultErrCode = result.errCode;
            //二维码图片对应该链接，发至自己后台
            // var imageQRCode ="http://172.27.49.224:8080/bills/sendBillQRCode.do?billQRCode="+billQRCode;
            var resultMsg = "";
            // alert("result.billQRCode:"+result.billQRCode);
            if(resultErrCode == "SUCCESS"){
                    var respStr = eval("("+result.respStr+")");
                    var resultBillStatus = respStr.billStatus;
                    var targetSys = respStr.billPayment.targetSys;
                    if(resultBillStatus == "PAID"){

                        if(targetSys == "WXPay"){
                            resultMsg = "微信支付成功";
                        }else if(targetSys == "Alipay 2.0"){
                            resultMsg = "支付宝支付成功";
                        }else {
                            resultMsg = "支付成功";
                        }
                        //显示查询返回信息
                        $("#totalAmount").empty().append(respStr.totalAmount/100+"元");
                        $("#mid").empty().append(respStr.mid);
                        $("#tid").empty().append(respStr.tid);
                        $("#msgSrc").empty().append(respStr.msgSrc);
                        $("#msgType").empty().append(respStr.msgType);
                        $("#instMid").empty().append(respStr.instMid);
                        $("#billNo_span").empty().append(respStr.billNo);
                        $("#billDate_span").empty().append(respStr.billDate);
                    }else {
                        resultMsg = "支付未成功";
                    }
            }else if(resultErrCode == "BAD_REQUEST"){
                    resultMsg = "请求输入字段不对";
            }else if (resultErrCode == "URLFailed"){
                    resultMsg = "请求地址不对";
                }else if (resultErrCode == "HttpURLException"){
                    resultMsg = result.msg;
                } else {
                    resultMsg = "账单查询,银商平台返回失败";
            }
            $("#resultMsg").empty();
            $("#resultMsg").append(resultMsg);
        });
}


/**
 * 退款模块
 * */
function refund() {
    //定义变量
    var url = "";
    var params = {};

    //变量赋值
    params.billNo = $("#billNo").val();
    params.billDate = $("#billDate").val();
    params.refundAmount = $("#refundAmount").val()*100;

    //字段校验
    $("#billNo_span2").empty();
    if(params.billNo == null || params.billNo == ""){
        $("#billNo_span2").append("账单号为必传字段");
        return ;
    }

    $("#billDate_span2").empty();
    if(params.billDate == null || params.billDate == ""){
        $("#billDate_span2").append("账单日期为必传字段");
        return ;
    }

    $("#refundAmount_span2").empty();
    if(params.refundAmount == null || params.refundAmount == ""){
        $("#refundAmount_span2").append("账单日期为必传字段");
        return ;
    }

    //post请求
    $.post("/bills/refund.do",
        params,
        function (data, status) {
            var result = eval("("+data+")");
            var resultErrCode = result.errCode;
            // var targetSys = respStr.billPayment.targetSys;
            //二维码图片对应该链接，发至自己后台
            // var imageQRCode ="http://172.27.49.224:8080/bills/sendBillQRCode.do?billQRCode="+billQRCode;
            var resultMsg = "";
            // alert("result.billQRCode:"+result.billQRCode);
            if(resultErrCode == "SUCCESS"){
                var respStr = eval("("+result.respStr+")");
                var resultBillStatus = respStr.billStatus;
                if(resultBillStatus == "REFUND"){
                    resultMsg = "退款成功";
                    //显示查询返回信息
                    $("#refundAmount").empty().append(respStr.refundAmount/100+"元");
                    $("#totalAmount").empty().append(respStr.totalAmount/100+"元");
                    $("#refundOrderId").empty().append(respStr.refundOrderId);
                    $("#mid").empty().append(respStr.mid);
                    $("#tid").empty().append(respStr.tid);
                    $("#msgSrc").empty().append(respStr.msgSrc);
                    $("#msgType").empty().append(respStr.msgType);
                    $("#instMid").empty().append(respStr.instMid);
                    $("#billNo_span").empty().append(respStr.billNo);
                    $("#billDate_span").empty().append(respStr.billDate);
                }else {
                    resultMsg = "退款未成功";
                }
            }else if (resultErrCode == "HAS_REFUNDED"){
                resultMsg = "该笔交易已成功退款，无需再次执行退款";
            }else if (resultErrCode == "URLFailed"){
                resultMsg = "请求地址不对";
            }else if (resultErrCode == "HttpURLException"){
                resultMsg = result.msg;
            } else {
                resultMsg = "账单查询,银商平台返回失败";
            }
            $("#resultMsg").empty();
            $("#resultMsg").append(resultMsg);
        });
}


/**
 * 根据商户终端号查询此台终端最后一笔详单情况
 * */
function queryLastQRCode() {
    //定义变量
    var url = "";
    var params = {};

    //post请求
    $.post("/bills/queryLastQRCode.do",
        params,
        function (data, status) {
            var result = eval("("+data+")");
            var resultErrCode = result.errCode;
            var respStr = eval("("+result.respStr+")");
            var resultBillStatus = respStr.billStatus;
            // var targetSys = respStr.billPayment.targetSys;
            //二维码图片对应该链接，发至自己后台
            // var imageQRCode ="http://172.27.49.224:8080/bills/sendBillQRCode.do?billQRCode="+billQRCode;
            var resultMsg = "";
            // alert("result.billQRCode:"+result.billQRCode);
            if(resultErrCode == "SUCCESS"){
                resultMsg = "查询成功";
                //显示查询返回信息
                $("#qrCodeUrl").empty().append(respStr.qrCodeUrl);
                $("#totalAmount").empty().append(respStr.totalAmount/100+"元");
                $("#mid").empty().append(respStr.mid);
                $("#tid").empty().append(respStr.tid);
                $("#msgSrc").empty().append(respStr.msgSrc);
                $("#msgType").empty().append(respStr.msgType);
                $("#instMid").empty().append(respStr.instMid);
                $("#billNo_span").empty().append(respStr.billNo);
                $("#billDate_span").empty().append(respStr.billDate);
            }else if (resultErrCode == "URLFailed"){
                resultMsg = "请求地址不对";
            }else if (resultErrCode == "HttpURLException"){
                resultMsg = result.msg;
            } else {
                resultMsg = "账单查询,银商平台返回失败";
            }
            $("#resultMsg").empty();
            $("#resultMsg").append(resultMsg);
        });
}

/**
 * 查询二维码静态信息接口
 * */
function queryQRCodeInfo() {
    //定义变量
    var url = "";
    var params = {};

    //变量赋值
    params.qrCodeId = $("#qrCodeId").val();

    //字段校验
    $("#qrCodeId_span2").empty();
    if(params.qrCodeId == null || params.qrCodeId == ""){
        $("#qrCodeId_span2").append("二维码ID为必传字段");
        return ;
    }
    //post请求
    $.post("/bills/queryQRCodeInfo.do",
        params,
        function (data, status) {
            var result = eval("("+data+")");
            var resultErrCode = result.errCode;
            // var targetSys = respStr.billPayment.targetSys;
            //二维码图片对应该链接，发至自己后台
            // var imageQRCode ="http://172.27.49.224:8080/bills/sendBillQRCode.do?billQRCode="+billQRCode;
            var resultMsg = "";
            // alert("result.billQRCode:"+result.billQRCode);
            if(resultErrCode == "SUCCESS"){
                var respStr = eval("("+result.respStr+")");
                var resultBillStatus = respStr.billStatus;
                resultMsg = "查询成功";
                //显示查询返回信息
                $("#qrCodeUrl").empty().append(respStr.qrCodeUrl);
                $("#totalAmount").empty().append(respStr.totalAmount/100+"元");
                $("#mid").empty().append(respStr.mid);
                $("#tid").empty().append(respStr.tid);
                $("#msgSrc").empty().append(respStr.msgSrc);
                $("#msgType").empty().append(respStr.msgType);
                $("#instMid").empty().append(respStr.instMid);
                $("#billNo_span").empty().append(respStr.billNo);
                $("#billDate_span").empty().append(respStr.billDate);
            }else if (resultErrCode == "URLFailed"){
                resultMsg = "请求地址不对";
            }else if (resultErrCode == "HttpURLException"){
                resultMsg = result.msg;
            } else {
                resultMsg = "账单查询,银商平台返回失败";
            }
            $("#resultMsg").empty();
            $("#resultMsg").append(resultMsg);
        });
}


/**
 * 关闭二维码模块
 * */
function closeQRCode() {
    //定义变量
    var url = "";
    var params = {};

    //变量赋值
    params.qrCodeId = $("#qrCodeId").val();
    //字段校验
    $("#qrCodeId_span2").empty();
    if(params.qrCodeId == null || params.qrCodeId == ""){
        $("#qrCodeId_span2").append("二维码ID为必传字段");
        return ;
    }
    //post请求
    $.post("/bills/closeQRCode.do",
        params,
        function (data, status) {
            var result = eval("("+data+")");
            var resultErrCode = result.errCode;
            // var targetSys = respStr.billPayment.targetSys;
            //二维码图片对应该链接，发至自己后台
            // var imageQRCode ="http://172.27.49.224:8080/bills/sendBillQRCode.do?billQRCode="+billQRCode;
            var resultMsg = "";
            // alert("result.billQRCode:"+result.billQRCode);
            if(resultErrCode == "SUCCESS"){
                var respStr = eval("("+result.respStr+")");
                var resultBillStatus = respStr.billStatus;
                resultMsg = "二维码已关闭";
                //显示查询返回信息
                $("#qrCodeId_span").empty().append(respStr.qrCodeId);
                $("#mid").empty().append(respStr.mid);
                $("#tid").empty().append(respStr.tid);
                $("#msgSrc").empty().append(respStr.msgSrc);
                $("#msgType").empty().append(respStr.msgType);
                $("#instMid").empty().append(respStr.instMid);
            }else if (resultErrCode == "URLFailed"){
                resultMsg = "请求地址不对";
            }else if (resultErrCode == "HttpURLException"){
                resultMsg = result.msg;
            } else {
                resultMsg = "账单查询,银商平台返回失败";
            }
            $("#resultMsg").empty();
            $("#resultMsg").append(resultMsg);
        });
}




/**
 * 模拟对银商平台的支付通知进行验签
 */
function checkSign() {
    //定义变量
    var url = "";
    var params = {};

    //变量赋值
    params.notifyStr = $("#notifyStr").val().trim();

    //字段校验
    $("#billNo_span2").empty();
    if(params.notifyStr == null || params.notifyStr == ""){
        $("#billNo_span2").append("请输入原始待签字串");
        return ;
    }

    //post请求
    $.post("/bills/checkSign.do",
        params,
        function (data, status) {
            // alert("data:"+data+"                "+"status:"+status);
            var result = eval("("+data+")");
            var preStr = result.preStr;
            var sign = result.sign;

            $("#preStr").empty().append(preStr);
            $("#sign").empty().append(sign);
        });
}
