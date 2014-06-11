<%@ page import="java.util.List" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.nelo.entities.Registration" %>

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
        <td style="width: 15%;">Start Date</td>
        <td style="width: 15%;">End Date</td>
        <td style="width: 10%;"></td>
    </tr>
    </thead>
    <tbody>
    <%
        List<Registration> registrationList = (List<Registration>) request.getAttribute("registrationList");

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        if (registrationList != null) {
            for (int i = 0; i < registrationList.size(); i++) {
    %>

    <tr>
        <td><%=registrationList.get(i).getUserAccount().getFirstName()%>&nbsp;<%=registrationList.get(i).getUserAccount().getLastName()%>
        </td>
        <td><%=registrationList.get(i).getUserAccount().getEmail()%>
        </td>
        <td><%=registrationList.get(i).getRoom().getRoomNumber()%></td>
        <td><%=dateFormat.format(registrationList.get(i).getStartDate())%></td>
        <td><%=dateFormat.format(registrationList.get(i).getEndDate())%></td>
        <td>
            <a href="${pageContext.request.contextPath}/admin/registration?registrationId=<%=registrationList.get(i).getRegistrationId()%>">View</a>
            <a href="${pageContext.request.contextPath}/admin/deleteRegistration?registrationId=<%=registrationList.get(i).getRegistrationId()%>">Delete</a>
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

