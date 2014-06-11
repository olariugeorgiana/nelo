<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
    $(document).ready(function () {
        $("input[name='startDate']").datepicker({ dateFormat: 'dd.mm.yy', minDate: 0 });
        $("input[name='endDate']").datepicker({ dateFormat: 'dd.mm.yy', minDate: 1 });

        $(".checkbox").checkbox({
                    singleSelection: true,
                    selectionChanged: function (source) {
                        $("#roomId").val(source.attr("id").substring(8, source.attr("id").length));
                        $("input[name='roomPrice']").val(source.attr("price"));

                        var selectedRoom = 'Number: ' + source.attr("number") + '&nbsp;&nbsp; Type: ' + source.attr("type") + '&nbsp;&nbsp; Price: ' + source.attr("price");
                        $("#selectedRoomContent")[0].innerHTML = selectedRoom;

                        $("#selectedRoomHeader").css({display: "block"});
                        $("#selectedRoomContent").css({display: "block"});
                        $("#mealsHeader").css({display: "block"});
                        $("#mealsContent").css({display: "block"});
                        $("#customerHeader").css({display: "block"});
                        $("#customerContent").css({display: "block"});
                        $("#priceHeader").css({display: "block"});
                        $("#priceContent").css({display: "block"});
                        $("#finishReservation").css({display: "block"});

                        calculatePrice();
                    }
                }
        );

        $("input[name='startDate']").change(function () {
            $("div[id='periodValidation']").hide();
            $("div[id='roomIdValidator']").hide();
        });
        $("input[name='endDate']").change(function () {
            $("div[id='periodValidation']").hide();
            $("div[id='roomIdValidator']").hide();
        });

        $("input[name='userAccount.firstName']").change(function () {
            $("div[id='firstNameValidator']").hide();
            $("div[id='roomIdValidator']").hide();
        });
        $("input[name='userAccount.lastName']").change(function () {
            $("div[id='firstNameValidator']").hide();
            $("div[id='roomIdValidator']").hide();
        });
        $("input[name='userAccount.email']").change(function () {
            $("div[id='firstNameValidator']").hide();
            $("div[id='roomIdValidator']").hide();
        });

    });

    function calculatePrice() {
        var price = $("input[name='roomPrice']").val();
        var breakfastPrice = 0;
        var lunchPrice = 0;
        var dinnerPrice = 0;


        if ($("input[name='breakfast']")[0].checked) {
            breakfastPrice = $("input[name='breakfastPrice']").val();
        }
        if ($("input[name='lunch']")[0].checked) {
            lunchPrice = $("input[name='lunchPrice']").val();
        }
        if ($("input[name='dinner']")[0].checked) {
            dinnerPrice = $("input[name='dinnerPrice']").val();
        }
        var price = parseInt(price) + parseInt(breakfastPrice) + parseInt(lunchPrice) + parseInt(dinnerPrice);
        var daydifff = daydiff($("input[name='startDate']").val(), $("input[name='endDate']").val());
        price = price * daydifff;

        $("#totalPriceId")[0].innerHTML = price + " &#8364;";
    }

    function daydiff(date1, date2) {
        var minutes = 1000 * 60;
        var hours = minutes * 60;
        var days = hours * 24;

        var foo_date1 = getDateFromFormat(date1, "dd.MM.yyyy");
        var foo_date2 = getDateFromFormat(date2, "dd.MM.yyyy");

        var diff_date = Math.round((foo_date2 - foo_date1) / days);
        return diff_date;
    }


</script>

<form:form commandName="reservationSearch" action="${pageContext.request.contextPath}/booking" method="POST"
           cssClass="formPanel" cssStyle="text-align: center;">

<form:hidden path="reservationId"/>
<form:hidden id="roomId" path="roomId"/>
<form:hidden id="roomPrice" path="roomPrice"/>
<form:hidden id="breakfastPrice" path="breakfastPrice"/>
<form:hidden id="lunchPrice" path="lunchPrice"/>
<form:hidden id="dinnerPrice" path="dinnerPrice"/>
<form:hidden id="totalPaid" path="totalPaid"/>

<h2 class="bookingTitle">
    Search for a room
</h2>

<div class="bookingContent" style="height:160px;">
    <div style="width:45%;display: inline-block;text-align: right;vertical-align: middle;">
        <div>
            <form:label path="startDate">Check in: </form:label>
            <form:input cssClass="field" path="startDate"/>
        </div>
        <div>
            <form:label path="endDate">Check out: </form:label>
            <form:input cssClass="field" path="endDate"/>
        </div>

        <spring:bind path="endDate">
            <c:if test="${status.error}">
                <div style="margin-bottom:10px;" id="periodValidation">
                    <form:errors path="endDate" cssStyle="color:firebrick;font-size: 0.8em;"/>
                </div>
            </c:if>
        </spring:bind>

    </div>
    <div style="width: 2px; display: inline-block;vertical-align: middle;">
        <img style="height: 110px;width: 2px;" src="${pageContext.request.contextPath}/resources/images/divider.png"/>
    </div>
    <div style="width:45%;display: inline-block;text-align: left;vertical-align: middle;">
        <div style="text-align: left;">
            <div style="width:100px;display: inline-block;">
                <form:checkbox path="internet" label="Internet"/>
            </div>
            <div style="width:160px;display: inline-block;">
                <form:checkbox path="airConditioner" label="Air conditioning"/>
            </div>
        </div>
        <div style="text-align: left;">
            <div style="width:100px;display: inline-block;">
                <form:checkbox path="phone" label="Phone"/></div>
            <div style="width:160px;display: inline-block;">
                <form:checkbox path="roomView" label="Balcony"/></div>
        </div>
        <div style="text-align: left;">
            <div style="width:100px;display: inline-block;">
                <form:checkbox path="pets" label="Pets"/>
            </div>
            <div style="width:160px;display: inline-block;">
                <form:checkbox path="smoking" label="Smoking room"/>
            </div>
        </div>
        <div style="text-align: left;">
            <div style="width:100px;display: inline-block;">
                <form:checkbox path="safe" label="Safe"/></div>
            <div style="display: inline-block;">
                <form:checkbox path="teaCoffee" label="Tea and coffee machine"/></div>
        </div>
        <div style="text-align: left;">
            <form:checkbox path="hydromassageTub" label="Hydro massage tub"/>
        </div>
    </div>
    <div style="margin-top: 10px;">
        <input type="submit" class="submit" name="searchAction" value="Search for rooms"/>
    </div>
</div>

<c:set var="isRoomSelected" value="display:none;"/>

<c:if test="${not empty selectedRoom}">
    <c:set var="isRoomSelected" value="display:block;"/>
</c:if>

<h2 id="selectedRoomHeader" class="bookingTitle" style="${isRoomSelected}">
    Selected room for reservation
</h2>

<div id="selectedRoomContent" class="bookingContent" style="${isRoomSelected}">
    Number: ${selectedRoom.roomNumber} &nbsp;&nbsp; Type: ${selectedRoom.niceType} &nbsp;&nbsp;
    Price: ${selectedRoom.price}
</div>

<h2 id="mealsHeader" class="bookingTitle" style="${isRoomSelected}">
    Meals
</h2>

<div id="mealsContent" class="bookingContent" style="${isRoomSelected}">
    <form:checkbox path="breakfast" label="Breakfast" onchange="calculatePrice();"/>
    <form:checkbox path="lunch" label="Lunch" onchange="calculatePrice();"/>
    <form:checkbox path="dinner" label="Dinner" onchange="calculatePrice();"/>
</div>

<h2 id="customerHeader" class="bookingTitle" style="${isRoomSelected}">
    Customer details
</h2>

<c:set var="loggedUser" value="false"/>
<c:if test="${not empty sessionScope.loggedUser}">
    <c:set var="loggedUser" value="true"/>
</c:if>

<div id="customerContent" class="bookingContent" style="width:100%;text-align: center;${isRoomSelected}">

    <div style="width:45%;display: inline-block;vertical-align: top;text-align: right;">
        <div>
            <form:label path="userAccount.email" cssClass="label">Email*: </form:label>
            <form:input path="userAccount.email" cssClass="field" readonly="${loggedUser}"/>
        </div>

        <div>
            <form:label path="userAccount.firstName" cssClass="label">Firstname*: </form:label>
            <form:input path="userAccount.firstName" cssClass="field" readonly="${loggedUser}"/>
        </div>

        <div>
            <form:label path="userAccount.lastName" cssClass="label">Lastname*: </form:label>
            <form:input path="userAccount.lastName" cssClass="field" readonly="${loggedUser}"/>
        </div>

        <div>
            <form:label path="userAccount.phoneNumber" cssClass="label">Phone: </form:label>
            <form:input path="userAccount.phoneNumber" cssClass="field" readonly="${loggedUser}"/>
        </div>
    </div>
    <div style="height: 120px; width: 2px; display: inline-block;">
        <img style="height: 120px;width: 2px;"
             src="${pageContext.request.contextPath}/resources/images/divider.png"/>
    </div>
    <div style="width:45%;display: inline-block;vertical-align: top;text-align: left;">
        <div>
            <form:label path="userAccount.identityCardNo" cssClass="label">ID Number:</form:label>
            <form:input path="userAccount.identityCardNo" cssClass="field" readonly="${loggedUser}"/>
        </div>

        <div>
            <form:label path="userAccount.identityCardEndDate" cssClass="label">ID Expiration Date:</form:label>
            <form:input path="userAccount.identityCardEndDate" cssClass="field" readonly="${loggedUser}"/>
        </div>

        <div>
            <form:label path="userAccount.nationality" cssClass="label">Nationality:</form:label>
            <form:input path="userAccount.nationality" cssClass="field" readonly="${loggedUser}"/>
        </div>

        <div>
            <form:label path="userAccount.address" cssClass="label">Address:</form:label>
            <form:input path="userAccount.address" cssClass="field" readonly="${loggedUser}"/>
        </div>
    </div>

    <spring:bind path="userAccount.firstName">
        <c:if test="${status.error}">
            <div style="margin-bottom:10px;" id="firstNameValidator">
                <form:errors path="userAccount.firstName" cssStyle="color:firebrick;font-size: 0.8em;"/>
            </div>
        </c:if>
    </spring:bind>
    <spring:bind path="roomId">
        <c:if test="${status.error}">
            <div style="margin-bottom:10px;" id="roomIdValidator">
                <form:errors path="roomId" cssStyle="color:firebrick;font-size: 0.8em;"/>
            </div>
        </c:if>
    </spring:bind>
</div>

<h2 id="priceHeader" class="bookingTitle" style="${isRoomSelected}">
    Total to pay
</h2>

<div id="priceContent" class="bookingContent" style="${isRoomSelected}">
    <form:label id="totalPriceId" path="totalPaid">${reservationSearch.totalPaid} &#8364;</form:label>
</div>

<security:authorize ifNotGranted="RECEPTIONIST,ADMINISTRATOR">
    <div id="finishReservation" class="bookingContent" style="margin-top:10px;${isRoomSelected}">
        <input type="submit" class="submit" value="Submit reservation"/>
    </div>
</security:authorize>

<c:if test="${not empty availableRooms || searchAction == true}">

    <h2 class="bookingTitle">
        Available rooms for booking
    </h2>

    <div class="bookingContent" style="width:40%;margin:auto;">

        <c:choose>
            <c:when test="${not empty availableRooms}">
                <table class="presentationGrid"
                       style="border:none!important;padding-top: 0px!important;margin-top: 0px!important;">
                    <thead>
                    <tr>
                        <td style="width: 5%;"></td>
                        <td style="width: 20%;">Number</td>
                        <td style="width: 25%;">Type</td>
                        <td style="width: 10%;">Price</td>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach items="${availableRooms}" var="room">

                        <tr>
                            <td>
                                <div id="checkbox${room.roomId}" price="${room.price}" number="${room.roomNumber}"
                                     type="${room.niceType}" class="checkbox"/>
                            </td>
                            <td>${room.roomNumber}</td>
                            <td>${room.niceType}</td>
                            <td>${room.price}</td>
                        </tr>

                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                There are no available rooms for reservations with your search criteria.
            </c:otherwise>
        </c:choose>

    </div>
</c:if>

</form:form>