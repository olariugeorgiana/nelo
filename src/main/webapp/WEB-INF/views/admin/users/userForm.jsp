<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<form:form commandName="userAccount" action="${pageContext.request.contextPath}/admin/user" method="POST"
           cssClass="formPanel" cssStyle="text-align: center;">

    <form:hidden path="userId"/>
    <form:hidden id="userId" path="userId"/>

    <fieldset style="display: inline-block;vertical-align: top;">
        <div class="legend">
            User details
        </div>

        <div class="fields floatLeftLabels">
            <div>
                <form:label path="firstName">First Name:</form:label>
                <form:input path="firstName"></form:input>
            </div>

            <div>
                <form:label path="lastName">Last Name:</form:label>
                <form:input path="lastName"></form:input>
            </div>

            <div>
                <form:label path="email">Email:</form:label>
                <form:input path="email"></form:input>
            </div>

            <div>
                <form:label path="description" cssStyle="float:left;">Role: </form:label>
                <form:select path="userRole">
                    <form:option value="-1">Select</form:option>
                    <form:options items="${userRoleList}" itemValue="type" itemLabel="description"/>
                </form:select>
            </div>

            <spring:bind path="firstName">
                <c:if test="${status.error}">
                    <div style="margin-bottom:10px;" id="firstNameValidator">
                        <form:errors path="email" cssStyle="color:firebrick;font-size: 0.8em;"/>
                    </div>
                </c:if>
            </spring:bind>


        </div>
    </fieldset>

</form:form>