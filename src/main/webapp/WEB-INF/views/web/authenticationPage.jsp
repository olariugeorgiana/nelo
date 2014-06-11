<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="img_1000 content_center" style="z-index: 1; margin: 0 auto ;left: 0;right: 0;">
    <img src="${pageContext.request.contextPath}/resources/images/mask_150.png" alt="">

</div>
<div style="width: 1000px;margin:0 auto;margin-top: 20px;">
    <div class="register">
        <form:form commandName="userAccountRegister" action="${pageContext.request.contextPath}/register" method="POST">

            <div>
                <form:label path="firstName">First Name: </form:label>
                <form:input type="text" path="firstName"/>

            </div>
            <div>
                <form:label path="lastName">Last Name: </form:label>
                <form:input type="text" path="lastName"/>
            </div>
            <div>
                <form:label path="email">Email: </form:label>
                <form:input type="text" path="email"/>
            </div>
            <div>
                <form:label path="phoneNumber">Phono No. :</form:label>
                <form:input type="text" path="phoneNumber"/>
            </div>

            <c:set var="emailError" value="false"/>
            <spring:bind path="userAccountRegister.email">
                <c:if test="${status.error}">
                    <c:set var="emailError" value="true"/>
                    <div style="color:firebrick;text-align: center;margin-bottom: 10px;">
                        <c:out value="${status.errorMessage}"/>
                    </div>
                </c:if>

            </spring:bind>

            <c:if test="${emailError == false}">
                <spring:hasBindErrors name="userAccountRegister">
                    <div style="color:firebrick;text-align: center;margin-bottom: 10px;">
                        You must complete all fields to register a account!
                    </div>
                </spring:hasBindErrors>
            </c:if>

            <div style="width: 250px;margin: 0 auto;">
                <input type="submit" value="Register"/>
            </div>

        </form:form>
    </div>
    <div style="height: 420px;width: 2px; display: inline-block;">
        <img style="height: 420px;" src="${pageContext.request.contextPath}/resources/images/divider.png"/>
    </div>
    <div class="login">
        <form:form commandName="userAccountLogin" action="${pageContext.request.contextPath}/authentificate"
                   method="POST">

            <div>
                <form:label path="email">Email: </form:label>
                <form:input type="text" path="email"/>
                <form:errors path="email"/>
            </div>

            <div>
                <form:label path="userPassword">Password: </form:label>
                <form:input type="password" path="userPassword"/>
                <form:errors path="userPassword"/>
            </div>

            <c:if test="${not empty sessionScope['SPRING_SECURITY_LAST_EXCEPTION']}">
                <div style="color:firebrick;text-align: center;margin-bottom: 10px;">
                    <c:out value="${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}"/>
                    <c:remove var="SPRING_SECURITY_LAST_EXCEPTION" scope="session"/>
                </div>
            </c:if>

            <div style="width: 250px;margin: 0 auto;">
                <input type="submit" value="Login"/>
            </div>

        </form:form>
    </div>

</div>