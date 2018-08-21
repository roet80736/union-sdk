<%@ page import="java.lang.management.RuntimeMXBean" %>
<%@ page import="java.lang.management.ManagementFactory" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!DOCTYPE html>
<%
    String root = request.getContextPath();

    // Get JVM's thread system bean
    RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
    // Get start time
    long jvmStartTime = runtimeMXBean.getStartTime();
%>
<script type="text/javascript">
    var root = "<%=root%>"; //js中存放当前页面的root路径方便调用
    var jvmStartTime = "<%=jvmStartTime%>"; //js中存放当前页面的root路径方便调用
</script>
<%-- jquery --%>
<script type="text/javascript" src="<%=root%>/js/jquery/jquery-1.7.2.js"></script>

<script type="text/javascript" src="<%=root%>/js/jquery.mobile.js"></script>
<script type="text/javascript" src="<%=root%>/js/qrcode.min.js"></script>
<link rel="stylesheet" href="<%=root%>/css/jquery.mobile.css"/>
