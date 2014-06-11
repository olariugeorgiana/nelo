<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/default.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/menu.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/forms.css"/>
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/js/jquery/base/jquery.ui.all.css"/>
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/responsiveslides.css"/>
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/presentationGrid.css"/>

    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery/jquery-1.8.3.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery/jquery.ui.core.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery/jquery.ui.widget.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery/jquery.ui.position.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery/jquery.ui.menu.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery/jquery.ui.autocomplete.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery/jquery.ui.datepicker.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery/responsiveslides.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/applicationAdmin.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customCheckbox.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/date.js"></script>

    <script type="text/javascript">

        resizeComponents();

    </script>

</head>
<body>

<div id="wrapper">
    <div id="menu">
        <tiles:insertAttribute name="menu"/>
    </div>
    <div id="contentWrapper">

        <tiles:useAttribute name="actions" id="actions" classname="String" ignore="true"/>
        <c:if test="${not empty actions}">
            <div id="pageActions">
                <tiles:insertAttribute name="actions"/>
            </div>
        </c:if>

        <tiles:useAttribute name="filters" id="filters" classname="String" ignore="true"/>
        <c:if test="${not empty filters}">
            <div id="pageFilters" style="margin-top: 5px;">
                <tiles:insertAttribute name="filters"/>
            </div>
        </c:if>

        <div id="content">
            <tiles:insertAttribute name="content"/>
        </div>
    </div>
    <div id="footer">
        <div>
            <tiles:insertAttribute name="footer"/>
        </div>
    </div>
</div>
<div id="contextPath" style="display:none;">${pageContext.request.contextPath}</div>
</body>
</html>