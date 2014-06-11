<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.nelo.entities.Room" %>
<%@ page import="java.util.List" %>
<%
    List<Room> roomList = (List<Room>) request.getAttribute("roomsList");

    if (roomList != null && roomList.size() > 0) {
%>

<c:forEach items="${roomsList}" var="room">


    <div class="roomtitle">
        <h2>Room ${room.roomNumber} - ${room.niceType}
        </h2>
    </div>
    <div class="roompage">
        <div class="roomimg_single">
            <img src="${pageContext.request.contextPath}/resources/images/maskimg.png"/>
            <ul class="rslides" style="margin-top: -236px;">
                <c:forEach items="${room.images}" var="roomImage">
                    <li><img src="data:image/png;base64,${roomImage.imgBase64}" style="width: 350px;height: 236px;"/>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="roomcontinut">
                <%--Camera din Hotel International este mobilata modern, spatioasa si curata. Are grup sanitar--%>
                <%--complet, este dodata cu televizor, televiziune prin cablu, sistem de ventilatie, minibar.</br>--%>
                <%--Camera este ideala pentru cazarea persoanelor aflate in trecere/vizita prin Mamaia.</br>--%>
                <%--Tariful de cazare si locatia sa, alaturi de serviciile de receptie, recomanda Hotel International drept o--%>
                <%--alternativa de cazare in oras convenabila pentru turistii aflati in cautatea de cazare in Mamaia. </br>--%>
                ${room.description}
        </div>
        <div class="roomcontinut"
             style="background-image:url( ${pageContext.request.contextPath}/resources/images/inner-divider.png) ; background-repeat: no-repeat;margin-top: 10px;color:#3b8281;font-size:20px;text-align: left; ">
            Room facilities:</br>
            <c:if test="${room.internet == true}">
                <div class="facilitate_camera">
                    <img src="${pageContext.request.contextPath}/resources/images/internet.png"/> Internet
                </div>
            </c:if>
            <c:if test="${room.airConditioner == true}">
                <div class="facilitate_camera">
                    <img src="${pageContext.request.contextPath}/resources/images/aer.png"/> Air conditioning
                </div>
            </c:if>
            <c:if test="${room.phone == true}">
                <div class="facilitate_camera">
                    <img src="${pageContext.request.contextPath}/resources/images/phone.png"/> Phone
                </div>
            </c:if>
            <c:if test="${room.roomView == true}">
                <div class="facilitate_camera">
                    <img src="${pageContext.request.contextPath}/resources/images/balcon.png"/> Balcony
                </div>
            </c:if>
            <c:if test="${room.smoking == true}">
                <div class="facilitate_camera">
                    <img src="${pageContext.request.contextPath}/resources/images/no_smoking.png"/> Smoking room
                </div>
            </c:if>
            <c:if test="${room.pets == true}">
                <div class="facilitate_camera">
                    <img src="${pageContext.request.contextPath}/resources/images/pets.png"/> Pets
                </div>
            </c:if>
            <c:if test="${room.safe == true}">
                <div class="facilitate_camera">
                    <img src="${pageContext.request.contextPath}/resources/images/safe.png"/> Safe box
                </div>
            </c:if>
            <c:if test="${room.teaCoffee == true}">
                <div class="facilitate_camera">
                    <img src="${pageContext.request.contextPath}/resources/images/tea_coffe.png"/> Tea and coffee
                    machine
                </div>
            </c:if>
            <c:if test="${room.hydromassageTub == true}">
                <div class="facilitate_camera">
                    <img src="${pageContext.request.contextPath}/resources/images/hidro_masaj.png"/> Hydro massage tub
                </div>
            </c:if>
        </div>
    </div>

</c:forEach>
<%

} else {
%>
<div class="roomtitle" style="margin-bottom: 30px;">Nu exista camere!</div>
<%
    }
%>


<%--<div class="roomtitle">--%>
<%--<h2>Matrimonial Room</h2>--%>
<%--</div>--%>
<%--<div class="roompage ">--%>
<%--<div class="roomimg_tripla">--%>
<%--<img src="${pageContext.request.contextPath}/resources/images/maskimg.png"/>--%>
<%--</div>--%>
<%--<div class="roomcontinut">--%>
<%--Camera Single Standard din Hotel International este mobilata modern, spatioasa si curata. Are grup sanitar--%>
<%--complet, este dodata cu telefon, televizor, televiziune prin cablu, sistem de ventilatie, minibar.</br>--%>
<%--Camera este ideala pentru cazarea unei persoane aflate in trecere/vizita prin Mamaia.</br>--%>
<%--Tariful de cazare si locatia sa, alaturi de serviciile de receptie, recomanda Hotel International drept o--%>
<%--alternativa de cazare in oras convenabila pentru turistii aflati in cautatea de cazare in Mamaia. </br>--%>
<%--</div>--%>
<%--<div class="roomcontinut"--%>
<%--style="background-image:url( ${pageContext.request.contextPath}/resources/images/inner-divider.png) ; background-repeat: no-repeat;margin-top: 10px;color:#3b8281;font-size:20px;text-align: left; ">--%>
<%--Facilitati camera:</br>--%>
<%--<div class="facilitate_camera">--%>
<%--<img src="${pageContext.request.contextPath}/resources/images/internet.png"/> Internet--%>
<%--</div>--%>
<%--<div class="facilitate_camera">--%>
<%--<img src="${pageContext.request.contextPath}/resources/images/aer.png"/> Aer conditionat--%>
<%--</div>--%>
<%--<div class="facilitate_camera">--%>
<%--<img src="${pageContext.request.contextPath}/resources/images/balcon.png"/> Balcon--%>
<%--</div>--%>
<%--<div class="facilitate_camera">--%>
<%--<img src="${pageContext.request.contextPath}/resources/images/hidro_masaj.png"/> Hidro-masaj--%>
<%--</div>--%>
<%--<div class="facilitate_camera">--%>
<%--<img src="${pageContext.request.contextPath}/resources/images/no_smoking.png"/> Nefumatori--%>
<%--</div>--%>
<%--<div class="facilitate_camera">--%>
<%--<img src="${pageContext.request.contextPath}/resources/images/pets.png"/> Animale--%>
<%--</div>--%>
<%--<div class="facilitate_camera">--%>
<%--<img src="${pageContext.request.contextPath}/resources/images/phone.png"/> Telefon--%>
<%--</div>--%>
<%--<div class="facilitate_camera">--%>
<%--<img src="${pageContext.request.contextPath}/resources/images/safe.png"/> Cudie de valor--%>
<%--</div>--%>
<%--<div class="facilitate_camera">--%>
<%--<img src="${pageContext.request.contextPath}/resources/images/tea_coffe.png"/> Masina ceai/cafea--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>