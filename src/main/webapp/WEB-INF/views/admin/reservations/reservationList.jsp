<%@ page import="org.nelo.entities.Reservation" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>

<script>

    $(function () {
        $(".checkbox").checkbox();
    });

</script>

<table class="presentationGrid">
    <thead>
    <tr>
        <td style="width: 15%;">Customer name</td>
        <td style="width: 15%;">Customer email</td>
        <td style="width: 15%;">Room number</td>
        <td style="width: 10%;">Virtual</td>
        <td style="width: 15%;">Reservation Date</td>
        <td style="width: 15%;">Start Date</td>
        <td style="width: 15%;">End Date</td>
        <td style="width: 10%;"></td>
    </tr>
    </thead>
    <tbody>
    <%
        List<Reservation> reservationList = (List<Reservation>) request.getAttribute("reservationList");

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        if (reservationList != null) {
            for (int i = 0; i < reservationList.size(); i++) {
    %>

    <tr>
        <td><%=reservationList.get(i).getUserAccount().getFirstName()%>
            &nbsp;<%=reservationList.get(i).getUserAccount().getLastName()%>
        </td>
        <td><%=reservationList.get(i).getUserAccount().getEmail()%>
        </td>
        <td><%=reservationList.get(i).getRoom().getRoomNumber()%>
        </td>
        <td><%=reservationList.get(i).getRoom().getVirtualRoom() == true ? "Yes" : "No"%>
        </td>
        <td><%=dateFormat.format(reservationList.get(i).getReservationDate())%>
        </td>
        <td><%=dateFormat.format(reservationList.get(i).getStartDate())%>
        </td>
        <td><%=dateFormat.format(reservationList.get(i).getEndDate())%>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/admin/reservation?reservationId=<%=reservationList.get(i).getReservationId()%>">View</a>
            <a href="${pageContext.request.contextPath}/admin/deleteReservation?reservationId=<%=reservationList.get(i).getReservationId()%>">Delete</a>
            <a href="${pageContext.request.contextPath}/admin/registration?reservationId=<%=reservationList.get(i).getReservationId()%>">Registration</a>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr class="noSelection">
        <td colspan="6">Nu exista date.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

