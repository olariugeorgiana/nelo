<%@ page import="org.nelo.entities.UserAccount" %>
<%@ page import="org.nelo.entities.enums.Roles" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div class="administrationZone" onclick="document.location='${pageContext.request.contextPath}/'">
    Administration zone<br/>
    Go to hotel page
</div>
<div style="display:inline-block;">
    <ul>
        <security:authorize ifAllGranted="ADMINISTRATOR">
            <li onclick="window.location='${pageContext.request.contextPath}/admin/'">
                <img style="width: 25px;"
                     src="${pageContext.request.contextPath}/resources/images/adminMenu/statistics.png"/><br/>
                <span>Statistics</span>
            </li>
        </security:authorize>
        <security:authorize ifAnyGranted="ADMINISTRATOR,RECEPTIONIST">
            <li onclick="window.location='${pageContext.request.contextPath}/admin/reservations'">
                <img style="width: 25px;"
                     src="${pageContext.request.contextPath}/resources/images/adminMenu/reservation.png"/><br/>
                <span>Reservations</span>
            </li>
        </security:authorize>
        <security:authorize ifAnyGranted="ADMINISTRATOR,RECEPTIONIST">
            <li onclick="window.location='${pageContext.request.contextPath}/admin/registrations'">
                <img style="width: 25px;"
                     src="${pageContext.request.contextPath}/resources/images/adminMenu/cazare.png"/><br/>
                <span>Registrations</span>
            </li>
        </security:authorize>
        <security:authorize ifAllGranted="ADMINISTRATOR">
            <li onclick="window.location='${pageContext.request.contextPath}/admin/rooms'">
                <img style="width: 24px;"
                     src="${pageContext.request.contextPath}/resources/images/adminMenu/room.png"/><br/>
                <span>Rooms</span>
            </li>
        </security:authorize>
        <security:authorize ifAllGranted="ADMINISTRATOR">
            <li onclick="window.location='${pageContext.request.contextPath}/admin/overbooking'">
                <img style="width: 24px;"
                     src="${pageContext.request.contextPath}/resources/images/adminMenu/overbooking.png"/><br/>
                <span>Overbooking</span>
            </li>
        </security:authorize>
        <security:authorize ifAllGranted="ADMINISTRATOR">
            <li onclick="window.location='${pageContext.request.contextPath}/admin/foodPrice'">
                <img style="width: 25px;"
                     src="${pageContext.request.contextPath}/resources/images/adminMenu/foodPrice.png"/><br/>
                <span>Food price</span>
            </li>
        </security:authorize>
        <security:authorize ifAnyGranted="ADMINISTRATOR,RECEPTIONIST">
            <li onclick="window.location='${pageContext.request.contextPath}/admin/customers'">
                <img style="width: 25px;"
                     src="${pageContext.request.contextPath}/resources/images/adminMenu/customers.png"/><br/>
                <span>Customers</span>
            </li>
        </security:authorize>
        <security:authorize ifAllGranted="ADMINISTRATOR">
            <li onclick="window.location='${pageContext.request.contextPath}/admin/users'">
                <img style="width: 25px;"
                     src="${pageContext.request.contextPath}/resources/images/adminMenu/administrator.png"/><br/>
                <span>Users</span>
            </li>
        </security:authorize>
    </ul>
</div>
<div class="loggedUserZone" onclick="document.location='${pageContext.request.contextPath}/logout'">

    <%
        UserAccount loggedUser = (UserAccount) session.getAttribute("loggedUser");
        String userRoleDescription = "";
    %>
    Welcome: <span style="color:#1d2d44;"
                   onclick="document.location='${pageContext.request.contextPath}/admin/users'"><%=loggedUser.getFirstName()%> <%=loggedUser.getLastName()%></span>
    <br/>

    <%
        if (loggedUser.getRole().equals(Roles.ADMINISTRATOR)) {
            userRoleDescription = "Administrator - Logout";
        } else if (loggedUser.getRole().equals(Roles.RECEPTIONIST)) {
            userRoleDescription = "Receptionist - Logout";
        }
        out.print(userRoleDescription);
    %>
</div>

