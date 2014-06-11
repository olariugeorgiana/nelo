<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form:form commandName="room" action="${pageContext.request.contextPath}/admin/room" method="POST"
           enctype="multipart/form-data" cssClass="formPanel">

    <form:hidden path="roomId"/>
    <form:hidden path="virtualRoom"/>

    <fieldset>
        <div class="legend">
            <div style="display:inline-block;width: 50%;">Room details</div>
            <div style="display:inline-block;width: 49%;">The room is equipped with</div>
        </div>
        <div class="fields">
            <div style="display:inline-block;width: 50%; vertical-align: top;">
                <div>
                    <form:label path="roomNumber" cssStyle="float:left;">Number: </form:label>
                    <form:input path="roomNumber"/>
                    <form:errors path="roomNumber"/>
                </div>

                <div>
                    <form:label path="description" cssStyle="float:left;">Type: </form:label>
                    <form:select path="roomType">
                        <form:option value="-1">Select</form:option>
                        <form:options items="${roomTypeList}" itemValue="type" itemLabel="description"/>
                    </form:select>
                    <form:errors path="roomType"/>
                </div>

                <div>
                    <form:label path="price" cssStyle="float:left;">Price: </form:label>
                    <form:input path="price"/>
                    <form:errors path="price"/>
                </div>

                <div>
                    <form:label path="description" cssStyle="float:left;">Description: </form:label>
                    <form:textarea path="description"/>
                    <form:errors path="description"/>
                </div>

            </div>
            <div style="display: inline-block;width: 49%;vertical-align: top;">
                <div style="display: inline-block;width: 50%;">
                    <form:checkbox path="internet" label="Internet" dir="ltr"/><br/>
                    <form:checkbox path="airConditioner" label="Air conditioning" dir="ltr"/><br/>
                    <form:checkbox path="phone" label="Phone"/><br/>
                    <form:checkbox path="roomView" label="Balcony"/><br/>
                    <form:checkbox path="smoking" label="Smoking room"/><br/>
                    <form:checkbox path="pets" label="Pets"/><br/>
                    <form:checkbox path="safe" label="Safe box"/><br/>
                    <form:checkbox path="teaCoffee" label="Tea and coffee machine"/><br/>
                    <form:checkbox path="hydromassageTub" label="Hydro massage tub"/>

                </div>
            </div>
        </div>
    </fieldset>
    <fieldset>
        <div class="legend">Room pictures</div>
        <div id="pictureField" class="fields">

            <div id="selectPictureDiv">
                <div id="selectPicture" style="font-weight: bold;color:#000;padding:10px;">Add picture</div>
                <input class="currentFileInput" id="files[0]" style="display:none;" name="files[0]" type="file"
                       accept="image/png"/>
            </div>

            <spring:bind path="room.images">

            </spring:bind>

            <form:hidden path="roomdIdsToDelete"/>

            <c:forEach items="${room.images}" var="image">
                <ul id="picture${image.roomImgId}">
                    <li><img src="data:image/png;base64,${image.imgBase64}" width="350" height="236"/></li>

                    <li>${image.imgName}</li>
                    <li><input type="button" value="Delete picture" onclick="deletePicture(this,false)"/></li>
                </ul>
            </c:forEach>

        </div>
    </fieldset>

</form:form>

<div id="dropZone">

</div>

<style>
    .fileInput {
        display: none;
    }
</style>

<script type="text/javascript">
    var filesUploaded = 1;

    $('.currentFileInput').change(changeForFile);

    $('#selectPicture').on("click", function () {
        $('.currentFileInput').click();
    });
    $('#selectPicture').hover(function () {
        $('#selectPicture').css({cursor: "pointer"});
    });

    function changeForFile() {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('<ul id="justAdded' + filesUploaded + '"><li><img  width="350" height="236" src="' + e.target.result + '"/></li><li>denumire fisier</li><li><input type="button" value="Delete picture" onclick="deletePicture(this,true)"/></li></ul>').appendTo($("#pictureField"));
            filesUploaded++;
        };

        reader.readAsDataURL($('.currentFileInput')[0].files[0]);

        $('.currentFileInput').removeClass("currentFileInput");

        var newInput = $('<input class="currentFileInput" style="display:none;" type="file" id="files' + filesUploaded + '" name="files[' + filesUploaded + ']" style="display:none;"/> ')
        newInput.change(changeForFile);

        newInput.appendTo($('#selectPictureDiv'));
    }

    function deletePicture(source, justAdded) {
        var parent = $(source).parent().parent();
        var imageId = parent.attr("id");
        if (justAdded) {
            imageId = imageId.substr(9, imageId.length);
            $("#files" + imageId).remove();
            parent.remove();
        } else {
            var imageId = parent.attr("id");
            imageId = imageId.substr(7, imageId.length);
            $("#roomdIdsToDelete").val($("#roomdIdsToDelete").val() + ";" + imageId);
            parent.remove();
        }
    }

    function createPictureElementForRoom(parent) {
        var newInput = $('<input style="display:none;" type="file" id="files' + filesUploaded + '" name="files[' + filesUploaded + ']" style="display:none;"/> ')

        newInput.appendTo(parent);
    }

</script>

