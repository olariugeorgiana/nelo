<%@ page import="org.nelo.entities.Room" %>
<%@ page import="java.util.List" %>

<script>

    $(function () {
        $(".checkbox").checkbox();
    });

</script>

<table class="presentationGrid">
    <thead>
    <tr>
        <td style="width: 10%;">Type</td>
        <td style="width: 10%;">Number</td>
        <td>Description</td>
        <td style="width: 10%;"></td>
    </tr>
    </thead>
    <tbody>
    <%
        List<Room> roomsList = (List<Room>) request.getAttribute("roomsList");
        if (roomsList != null && roomsList.size() > 0) {
            for (int i = 0; i < roomsList.size(); i++) {
    %>

    <tr>
        <td><%=roomsList.get(i).getNiceType()%>
        </td>
        <td><%=roomsList.get(i).getRoomNumber()%>
        </td>
        <td style="word-break: break-all;"><%=roomsList.get(i).getDescription()%>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/admin/room?roomId=<%=roomsList.get(i).getRoomId()%>">Edit</a>
            <a href="${pageContext.request.contextPath}/admin/deleteRoom?roomId=<%=roomsList.get(i).getRoomId()%>">Delete</a>
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

