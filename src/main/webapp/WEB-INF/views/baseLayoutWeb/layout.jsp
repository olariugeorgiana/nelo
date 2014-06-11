<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/js/jquery/base/jquery.ui.all.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/defaultWeb.css"/>
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/responsiveslides.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/defaultWeb.css"/>
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/presentationGrid.css"/>

    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery/jquery-1.8.3.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery/jquery.ui.core.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery/jquery.ui.datepicker.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/jquery/responsiveslides.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/customCheckbox.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/date.js"></script>

    <script>
        $(function () {
            $(".rslides").responsiveSlides({
                maxwidth: 1000,
                speed: 800
            });
        });
    </script>
    <script>
        $(document).ready(function () {
            function footer() {
                var wheight = $(window).height();
                var xbody = $("#wrapper").height();

                console.log(wheight, xbody);
                if (xbody < 600) {
                    $("#footer").addClass("footer_d");
                }
                else {
                    $("#footer").removeClass("footer_d");
                }
            }

            footer();
            $(window).resize(function () {
                footer();
            })

        });
    </script>
</head>
<body>
<div id="wrapper">
    <div id="header">
        <tiles:insertAttribute name="header"/>
    </div>
    <div id="content">
        <tiles:insertAttribute name="content"/>
    </div>
    <div id="footer">
        <tiles:insertAttribute name="footer"/>
    </div>
</div>
<div id="contextPath" style="display:none;">${pageContext.request.contextPath}</div>
</body>
</html>