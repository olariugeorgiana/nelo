<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<form:form commandName="reservationSearch" action="${pageContext.request.contextPath}/admin/reservations" method="POST">
    Search by:
    <span style="font-weight: bold;">Room Number: </span><form:input path="roomNumber"/>
    <span style="font-weight: bold;">Email:</span> <form:input path="userAccount.email"/>
    <span style="font-weight: bold;">Start date: </span><form:input path="startDate"/>
    <span style="font-weight: bold;">End date: </span><form:input path="endDate"/>
</form:form>
<a onclick="document.forms['reservationSearch'].submit();">Search reservations</a>
<a onclick="clearFilters();">Clear filters</a>

<script type="text/javascript">

    $("input[name='startDate']").datepicker({ dateFormat: 'dd.mm.yy' });
    $("input[name='endDate']").datepicker({ dateFormat: 'dd.mm.yy' });

    function clearFilters(){
        $("input[name='roomNumber']").val("");
        $("input[name='userAccount.email']").val("");
        $("input[name='startDate']").val("");
        $("input[name='endDate']").val("");

        document.forms['reservationSearch'].submit();
    }
</script>

