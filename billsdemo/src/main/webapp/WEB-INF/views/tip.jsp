<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="tagslib.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
    <meta name="format-detection" content="telephone=no">
    <title>
        <c:choose>
            <c:when test="${tip.status eq 'warn'}">
                警告
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${tip.status eq 'error'}">
                        操作失败
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${tip.status eq 'notify'}">
                                操作成功
                            </c:when>
                            <c:otherwise>
                                错误
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </title>
    <link rel="stylesheet" href="//res.wx.qq.com/open/libs/weui/1.0.2/weui.css"/>
    <link rel="stylesheet" href="<%=root%>/css/base.css"/>
    <script type="text/javascript" src="<%=root%>/js/common/common.js?v=<%=jvmStartTime%>"></script>
</head>
<body>
<div class="weui-msg">
    <div class="weui-msg__icon-area">
        <c:choose>
            <c:when test="${tip.status eq 'warn'}">
                <i class="weui-icon-warn weui-icon_msg-primary"></i>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${tip.status eq 'error'}">
                        <i class="weui-icon-warn weui-icon_msg"></i>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${tip.status eq 'notify'}">
                                <i class="weui-icon-success weui-icon_msg"></i>
                            </c:when>
                            <c:otherwise>
                                <i class="weui-icon-warn weui-icon_msg"></i>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="weui-msg__text-area">
        <c:choose>
            <c:when test="${not empty tip.msgTitle}">
                <h2 class="weui-msg__title">${tip.msgTitle}</h2>
            </c:when>
            <c:otherwise>
                <h2 class="weui-msg__title">
                    <c:choose>
                        <c:when test="${tip.status eq 'warn'}">
                            警告
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${tip.status eq 'error'}">
                                    操作失败
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${tip.status eq 'notify'}">
                                            操作成功
                                        </c:when>
                                        <c:otherwise>
                                            错误
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </h2>
            </c:otherwise>
        </c:choose>
        <p class="weui-msg__desc">${tip.msg}</p>
    </div>
    <div class="weui-msg__opr-area">
        <p class="weui-btn-area">
            <c:choose>
                <c:when test="${not empty tip.buttonText}">
                    <c:choose>
                        <c:when test="${not empty tip.buttonUrl}">
                            <a href="${tip.buttonUrl}" class="weui-btn weui-btn_primary">${tip.buttonText}</a>
                        </c:when>
                        <c:otherwise>
                            <a href="javascript:;" onclick="${tip.buttonAction}();"
                               class="weui-btn weui-btn_primary">${tip.buttonText}</a>
                        </c:otherwise>
                    </c:choose>
                </c:when>
            </c:choose>
        </p>
    </div>
</div>
</body>
</html>