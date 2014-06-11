<%@ page import="org.nelo.entities.UserAccount" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<div class="formPanel">

    <fieldset>
        <div class="legend" style="padding-left: 20px;">
            Hotel statistics
        </div>
        <div style="padding:10px;padding-left: 20px;">
            <div>Total income: ${totalPaidToHotel}</div>
            <div>Total income from rooms: ${totalPaidRoomsToHotel}</div>
            <div>Total income from breakfast: ${totalPaidBreakfastToHotel}</div>
            <div>Total income from lunch: ${totalPaidLunchToHotel}</div>
            <div>Total income from dinner: ${totalPaidDinnerToHotel}</div>
        </div>
    </fieldset>

</div>

<div class="formPanel">

    <fieldset>
        <div class="legend" style="padding-left: 20px;">
            Reservations statistics
        </div>
        <div style="padding:10px;padding-left: 20px;">
            <div>Total: ${totalReservationsCount}</div>
            <div>Total without registration: ${totalReservationWithoutRegistrationCount}</div>
        </div>
    </fieldset>

</div>

<div class="formPanel">

    <fieldset>
        <div class="legend" style="padding-left: 20px;">
            Registrations statistics
        </div>
        <div style="padding:10px;padding-left: 20px;">
            <div>Total: ${totalRegistrationCount}</div>
            <div>Total without reservation: ${totalRegistrationWithoutReservationCount}</div>
        </div>
    </fieldset>

</div>

<div class="formPanel">

    <fieldset>
        <div class="legend" style="padding-left: 20px;">
            Rooms statistics
        </div>
        <div style="padding:10px;padding-left: 20px;">
            <div>Total: ${totalRoomsCount}</div>
        </div>
    </fieldset>

</div>

<div class="formPanel">

    <fieldset>
        <div class="legend" style="padding-left: 20px;">
            Customers statistics
        </div>
        <div style="padding:10px;padding-left: 20px;">
            <div>Total: ${totalCustomersCount}</div>
        </div>
        <div style="padding:10px;padding-left: 20px;">
            <div>Top 10 Customers</div>
            <table class="presentationGrid">
                <thead>
                <tr>
                    <td style="width: 30%;">Name</td>
                    <td style="width: 30%;">Email</td>
                    <td style="width: 30%;">Value paid</td>
                </tr>
                </thead>
                <tbody>
                <%
                    Map<UserAccount, Long> top10Users = (Map<UserAccount, Long>) request.getAttribute("top10Users");
                    if (top10Users != null && top10Users.size() > 0) {
                        Set<Map.Entry<UserAccount, Long>> entries = top10Users.entrySet();
                        Iterator<Map.Entry<UserAccount, Long>> iterator = entries.iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<UserAccount, Long> next = iterator.next();
                %>

                <tr>
                    <td><%=next.getKey().getFirstName()%> <%=next.getKey().getLastName()%>
                    </td>
                    <td><%=next.getKey().getEmail()%>
                    </td>
                    <td><%=next.getValue()%>
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
        </div>
    </fieldset>

</div>