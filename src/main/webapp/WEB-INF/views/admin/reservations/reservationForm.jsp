<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<script>
    $(document).ready(function () {
        //datepickers
        $("input[name='startDate']").datepicker({ dateFormat: 'dd.mm.yy', minDate: 0  });
        $("input[name='endDate']").datepicker({ dateFormat: 'dd.mm.yy', minDate: 1 });

        //autocomplete jquery
        $("input[name='userAccount.email']").autocomplete({
            source: function (request, response) {
                //ajax call for get users list
                $.getJSON("${pageContext.request.contextPath}/admin/searchUser", {
                    term: request.term
                }, function (result) {
                    response($.map(result, function (item) {
                        return {
                            // following property gets displayed in drop down
                            label: item.firstName + " " + item.lastName,
                            // following property gets entered in the textbox
                            value: item.email,
                            firstName: item.firstName,
                            lastName: item.lastName,
                            phoneNumber: item.phoneNumber
                        }
                    }));
                });
            },
            select: function (event, ui) {
                if (ui.item) {
//                    event.preventDefault();
                    $("input[name='userAccount.firstName']").val(ui.item.firstName);
                    $("input[name='userAccount.lastName']").val(ui.item.lastName);
                    $("input[name='userAccount.phoneNumber']").val(ui.item.phoneNumber);
                }
            },
            minLength: 2
        });

        //checkboxs for room grid
        $(".checkbox").checkbox({
                    singleSelection: true,
                    selectionChanged: function (source) {
                        $("#roomId").val(source.attr("id").substring(8, source.attr("id").length));
                        $("input[name='roomPrice']").val(source.attr("price"));

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

<form:form commandName="reservationSearch" action="${pageContext.request.contextPath}/admin/reservation" method="POST"
           cssClass="formPanel" cssStyle="text-align: center;">

    <form:hidden path="reservationId"/>
    <form:hidden id="roomId" path="roomId"/>
    <form:hidden id="roomPrice" path="roomPrice"/>
    <form:hidden id="breakfastPrice" path="breakfastPrice"/>
    <form:hidden id="lunchPrice" path="lunchPrice"/>
    <form:hidden id="dinnerPrice" path="dinnerPrice"/>
    <form:hidden id="totalPaid" path="totalPaid"/>

    <fieldset style="width:350px;display: inline-block;vertical-align: top;">
        <div class="legend">
            Customer details
        </div>
        <div class="fields floatLeftLabels">

            <div>
                <form:label path="userAccount.email">Email: </form:label>
                <form:input path="userAccount.email"/>
            </div>

            <div>
                <form:label path="userAccount.firstName">Firstname: </form:label>
                <form:input path="userAccount.firstName"/>
            </div>

            <div>
                <form:label path="userAccount.lastName">Lastname: </form:label>
                <form:input path="userAccount.lastName"/>
            </div>

            <div>
                <form:label path="userAccount.phoneNumber">Phone: </form:label>
                <form:input path="userAccount.phoneNumber"/>
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
    </fieldset>

    <fieldset style="width:350px;display: inline-block;">
        <div class="legend">
            Reservation details
        </div>
        <div class="fields floatLeftLabels">

            <div>
                <form:label path="startDate">Check in: </form:label>
                <form:input path="startDate"/>
            </div>

            <div>
                <form:label path="endDate">Check out: </form:label>
                <form:input path="endDate"/>
            </div>

            <spring:bind path="endDate">
                <c:if test="${status.error}">
                    <div style="margin-bottom:10px;" id="periodValidation">
                        <form:errors path="endDate" cssStyle="color:firebrick;font-size: 0.8em;"/>
                    </div>
                </c:if>
            </spring:bind>

            <input type="submit" class="searchRoomsButton" name="searchAction" value="Search for rooms"/>

        </div>
    </fieldset>

    <fieldset style="width:350px;display: inline-block; vertical-align: top;">
        <div class="legend">
            Room options
        </div>
        <div class="fields" style="padding-left: 50px;">
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
    </fieldset>

    <fieldset>
        <div class="legend">
            Meals
        </div>
        <div class="fields">

            <form:checkbox path="breakfast" label="Breakfast" onchange="calculatePrice();"/>
            <form:checkbox path="lunch" label="Lunch" onchange="calculatePrice();"/>
            <form:checkbox path="dinner" label="Dinner" onchange="calculatePrice();"/>

        </div>
    </fieldset>

    <fieldset>
        <div class="legend">
            Total to pay
        </div>
        <div class="fields">

            <form:label id="totalPriceId" path="totalPaid">${reservationSearch.totalPaid} &#8364;</form:label>

        </div>
    </fieldset>

    <c:if test="${not empty selectedRoom}">
        <fieldset>
            <div class="legend">
                Selected room for reservation
            </div>
            <div class="fields" style="font-weight: bold;">

                Number: ${selectedRoom.roomNumber} &nbsp;&nbsp; Type: ${selectedRoom.niceType} &nbsp;&nbsp;
                Price: ${selectedRoom.price} &nbsp;&nbsp; Virtual: ${selectedRoom.virtualRoom == true ? "Yes" : "No"}

            </div>
        </fieldset>
    </c:if>

    <c:if test="${not empty availableRooms}">
        <fieldset style="">
            <div class="legend">
                Available rooms
            </div>
            <div class="fields ">

                <table class="presentationGrid"
                       style="border:none!important;padding-top: 0px!important;margin-top: 0px!important;">
                    <thead>
                    <tr>
                        <td style="width: 5%;"></td>
                        <td style="width: 20%;">Number</td>
                        <td style="width: 25%;">Type</td>
                        <td style="width: 10%;">Price</td>
                        <td style="width: 10%;">Virtual</td>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach items="${availableRooms}" var="room">

                        <tr>
                            <td>
                                <div id="checkbox${room.roomId}" price="${room.price}" class="checkbox"/>
                            </td>
                            <td>${room.roomNumber}</td>
                            <td>${room.niceType}</td>
                            <td>${room.price}</td>
                            <td>${room.virtualRoom == true ? "Yes" : "No"}</td>
                        </tr>

                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </fieldset>
    </c:if>
</form:form>

