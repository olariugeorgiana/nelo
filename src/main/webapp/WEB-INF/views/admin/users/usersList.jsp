<%@ page import="org.nelo.entities.Customer" %>
<%@ page import="java.util.List" %>
<%@ page import="org.nelo.entities.UserAccount" %>

<script>

    $(function () {
        $(".checkbox").checkbox();
    });

</script>

<table class="presentationGrid">
    <thead>
    <tr>
        <td style="width: 25%;">Firstname</td>
        <td style="width: 20%;">Lastname</td>
        <td style="width: 20%;">Email</td>
        <td style="width: 20%;">Role</td>
        <td style="width: 10%;"></td>
    </tr>
    </thead>
    <tbody>
    <%
        List<UserAccount> usersList = (List<UserAccount>) request.getAttribute("usersList");
        if (usersList != null) {
            for (int i = 0; i < usersList.size(); i++) {
    %>

    <tr>
        <td><%=usersList.get(i).getFirstName()%>
        </td>
        <td><%=usersList.get(i).getLastName()%>
        </td>
        <td><%=usersList.get(i).getEmail()%>
        </td>
        <td><%=usersList.get(i).getRole().getDescription()%>
        </td>
        <td></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr class="noSelection">
        <td colspan="3">Nu exista date.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

