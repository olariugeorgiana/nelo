<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<img style="margin: 0 auto;left: 0;right: 0;position: absolute;z-index: -1;width: 250px;height: 114px;" onclick=""
     src="${pageContext.request.contextPath}/resources/images/booking-back.png">

<div class="booking_button" onclick="window.location='${pageContext.request.contextPath}/booking'"><a>BOOK NOW </a>
</div>
<div class="content_center">
    <div class="menu_background_left">

    </div>
    <div class="menu_background">


        <div class="menu_button_left" onclick="window.location='${pageContext.request.contextPath}/'">
            <a href="#">Home</a>
        </div>
        <div class="menu_button_left" onclick="window.location='${pageContext.request.contextPath}/roomweb'">
            <a href="#">Rooms</a>
        </div>

        <div class="menu_button_left" onclick="window.location='${pageContext.request.contextPath}/facilities'">
            <a href="#">Facilities</a>
        </div>
        <div style="float:left;padding-left: 20px;padding-right: 20px;width: 122px;margin-top: -42px;margin-left: 40px;"
             onclick="window.location='${pageContext.request.contextPath}/'">
            <img onclick="" src="${pageContext.request.contextPath}/resources/images/logos.png">
        </div>

        <security:authorize ifAnyGranted="USER,RECEPTIONIST,ADMINISTRATOR">
            <div class="menu_button_left" style="float: right;"
                 onclick="window.location='${pageContext.request.contextPath}/logout'">
                <a href="#">Logout</a>
            </div>
        </security:authorize>
        <security:authorize ifAllGranted="USER">
            <div class="menu_button_left" style="float: right;"
                 onclick="window.location='${pageContext.request.contextPath}/personalInfo'">
                <a href="#">Account</a>
            </div>
        </security:authorize>
        <security:authorize ifNotGranted="USER,RECEPTIONIST,ADMINISTRATOR">
            <div class="menu_button_left" style="float: right;"
                 onclick="window.location='${pageContext.request.contextPath}/authentification'">
                <a href="#">Sign & Account</a>
            </div>
        </security:authorize>
        <security:authorize ifAnyGranted="RECEPTIONIST,ADMINISTRATOR">
            <div class="menu_button_left" style="float: right;"
                 onclick="window.location='${pageContext.request.contextPath}/admin'">
                <a href="#">Admin</a>
            </div>
        </security:authorize>
        <div class="menu_button_left" style="float:right;"
             onclick="window.location='${pageContext.request.contextPath}/infoPage'">
            <a href="#">Info</a>
        </div>


    </div>
    <div class="menu_background_right">

    </div>
</div>