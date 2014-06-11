<%@ page import="org.nelo.entities.Room" %>
<%@ page import="java.util.List" %>

<div class="formPanel">

    <fieldset>
        <div class="legend" style="padding-left: 20px;">
            Rooms virtually created for overbooking
        </div>

    </fieldset>

</div>

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
        List<Room> roomsList = (List<Room>) request.getAttribute("virtualRooms");
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