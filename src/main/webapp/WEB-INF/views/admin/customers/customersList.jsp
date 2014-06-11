<%@ page import="org.nelo.entities.UserAccount" %>
<%@ page import="java.util.List" %>

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
        <td style="width: 10%;"></td>
    </tr>
    </thead>
    <tbody>
    <%
        List<UserAccount> customerList = (List<UserAccount>) request.getAttribute("customersList");
        if (customerList != null) {
            for (int i = 0; i < customerList.size(); i++) {
    %>

    <tr>
        <td><%=customerList.get(i).getFirstName()%>
        </td>
        <td><%=customerList.get(i).getLastName()%>
        </td>
        <td><%=customerList.get(i).getEmail()%>
        </td>
        <td></td>
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

