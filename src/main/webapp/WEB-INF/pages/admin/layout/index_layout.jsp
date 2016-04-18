<%--
  Created by IntelliJ IDEA.
  User: butioy
  Date: 2016/4/8
  Time: 10:13
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/WEB-INF/pages/admin/snippet/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <tiles:insertAttribute name="meta"/>
    <title><tiles:insertAttribute name="title"/></title>
    <tiles:insertAttribute name="styles"/>
</head>
<body class="fixed-sidebar full-height-layout gray-bg ${USER_INFO_MAP_KEY.sysSkin}" style="overflow:hidden">
    <div id="wrapper">
        <tiles:insertAttribute name="menu" />
        <div id="page-wrapper" class="gray-bg dashbard-1">
            <tiles:insertAttribute name="navigation" />
            <tiles:insertAttribute name="body" />
        </div>
        <tiles:insertAttribute name="footer" />
    </div>
    <tiles:insertAttribute name="scripts"/>
</body>
</html>
