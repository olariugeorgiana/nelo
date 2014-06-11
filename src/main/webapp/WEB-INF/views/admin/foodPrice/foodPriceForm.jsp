<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form commandName="foodPrice" action="${pageContext.request.contextPath}/admin/foodPrice" cssClass="formPanel">

    <form:hidden path="foodPriceId"/>

    <fieldset>
        <div class="legend">
            <div style="padding-left:40px;">Food price</div>
        </div>
        <div class="fields floatLeftLabels">

            <div>
                <form:label path="breakfast">Breakfast:</form:label>
                <form:input path="breakfast"></form:input>
            </div>

            <div>
                <form:label path="lunch">Lunch:</form:label>
                <form:input path="lunch"></form:input>
            </div>

            <div>
                <form:label path="dinner">Dinner:</form:label>
                <form:input path="dinner"></form:input>
            </div>

        </div>
    </fieldset>

</form:form>