<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="org.nelo.entities.Registration" %>
<%@ page import="org.nelo.entities.Reservation" %>
<%@ page import="java.util.List" %>
<%
    List<Registration> registrationList = (List<Registration>) request.getAttribute("registrationList");
    List<Reservation> reservationList = (List<Reservation>) request.getAttribute("reservationList");
%>


<div class="reservationTitle">
    <h2>My registrations</h2>
</div>
<div class="reservationPage">
    <c:choose>
        <c:when test="${not empty registrationList}">
            <c:forEach items="${registrationList}" var="registration">
                <div class="reservationRecord">
                    Room number: ${registration.room.roomNumber} &nbsp;&nbsp; Check in: <fmt:formatDate
                        pattern="dd.MM.yyyy" value="${registration.startDate}"/> &nbsp;&nbsp; Check out: <fmt:formatDate
                        pattern="dd.MM.yyyy" value="${registration.endDate}"/></br>
                    Total paid: ${registration.totalPaid}
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div>You don't have registrations yet.</div>
        </c:otherwise>
    </c:choose>
</div>

<div class="reservationTitle">
    <h2>My reservations</h2>
</div>
<div class="reservationPage">
    <c:choose>
        <c:when test="${not empty reservationList}">
            <c:forEach items="${reservationList}" var="reservation">
                <div class="reservationRecord">
                    Room number: ${reservation.room.roomNumber} &nbsp;&nbsp; Check in: <fmt:formatDate
                        pattern="dd.MM.yyyy" value="${reservation.startDate}"/> &nbsp;&nbsp; Check out: <fmt:formatDate
                        pattern="dd.MM.yyyy" value="${reservation.endDate}"/></br>
                    Total to pay: ${reservation.totalPaid} &nbsp;&nbsp; <a
                        href="${pageContext.request.contextPath}/deleteReservation?reservationId=${reservation.reservationId}">Cancel
                    reservation</a>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div>You don't have reservations yet.</div>
        </c:otherwise>
    </c:choose>
</div>
