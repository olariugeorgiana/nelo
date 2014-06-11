<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form commandName="customer" action="${pageContext.request.contextPath}/admin/registerCustomer">
    <div>
        <form:label path="userAccount.firstName">First Name:</form:label>
        <form:input path="userAccount.firstName"></form:input>
        <form:errors path="userAccount.firstName"></form:errors>
    </div>

    <div>
        <form:label path="userAccount.lastName">Last Name:</form:label>
        <form:input path="userAccount.lastName"></form:input>
        <form:errors path="userAccount.lastName"></form:errors>
    </div>

    <div>
        <form:label path="userAccount.email">Email:</form:label>
        <form:input path="userAccount.email"></form:input>
        <form:errors path="userAccount.email"></form:errors>
    </div>

    <div>
        <form:label path="userAccount.phoneNumber">Phone Number:</form:label>
        <form:input path="userAccount.phoneNumber"></form:input>
        <form:errors path="userAccount.phoneNumber"></form:errors>
    </div>

    <div>
        <form:label path="identityCardNo">Indentity Card Number:</form:label>
        <form:input path="identityCardNo"></form:input>
        <form:errors path="identityCardNo"></form:errors>
    </div>

    <div>
        <form:label path="identityCardEndDate">Indentity Card Expiration Date:</form:label>
        <form:input path="identityCardEndDate"></form:input>
        <form:errors path="identityCardEndDate"></form:errors>
    </div>

    <div>
        <form:label path="nationality">Nationality:</form:label>
        <form:input path="nationality"></form:input>
        <form:errors path="nationality"></form:errors>
    </div>

    <div>
        <form:label path="address">Address:</form:label>
        <form:input path="address"></form:input>
        <form:errors path="address"></form:errors>
    </div>

</form:form>