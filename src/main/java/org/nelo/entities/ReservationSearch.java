package org.nelo.entities;

import java.util.Date;

public class ReservationSearch {

    private int reservationId = -1;

    private Date startDate;

    private Date endDate;

    private int roomId = -1;
    private Integer roomPrice = 0;

    private Integer roomNumber; // camp folosit la filtrare

    private UserAccount userAccount;

    private Boolean internet;

    private Boolean airConditioner;

    private Boolean phone;

    private Boolean roomView;

    private Boolean smoking;

    private Boolean pets;

    private Boolean safe;

    private Boolean teaCoffee;

    private Boolean hydromassageTub;

    private Boolean breakfast;

    private Boolean lunch;

    private Boolean dinner;

    private Integer totalPaid;

    private Integer breakfastPrice;
    private Integer lunchPrice;
    private Integer dinnerPrice;

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Boolean getInternet() {
        return internet;
    }

    public void setInternet(Boolean internet) {
        this.internet = internet;
    }

    public Boolean getAirConditioner() {
        return airConditioner;
    }

    public void setAirConditioner(Boolean airConditioner) {
        this.airConditioner = airConditioner;
    }

    public Boolean getPhone() {
        return phone;
    }

    public void setPhone(Boolean phone) {
        this.phone = phone;
    }

    public Boolean getRoomView() {
        return roomView;
    }

    public void setRoomView(Boolean roomView) {
        this.roomView = roomView;
    }

    public Boolean getSmoking() {
        return smoking;
    }

    public void setSmoking(Boolean smoking) {
        this.smoking = smoking;
    }

    public Boolean getPets() {
        return pets;
    }

    public void setPets(Boolean pets) {
        this.pets = pets;
    }

    public Boolean getSafe() {
        return safe;
    }

    public void setSafe(Boolean safe) {
        this.safe = safe;
    }

    public Boolean getTeaCoffee() {
        return teaCoffee;
    }

    public void setTeaCoffee(Boolean teaCoffee) {
        this.teaCoffee = teaCoffee;
    }

    public Boolean getHydromassageTub() {
        return hydromassageTub;
    }

    public void setHydromassageTub(Boolean hydromassageTub) {
        this.hydromassageTub = hydromassageTub;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Boolean getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Boolean breakfast) {
        this.breakfast = breakfast;
    }

    public Boolean getLunch() {
        return lunch;
    }

    public void setLunch(Boolean lunch) {
        this.lunch = lunch;
    }

    public Boolean getDinner() {
        return dinner;
    }

    public void setDinner(Boolean dinner) {
        this.dinner = dinner;
    }

    public Integer getTotalPaid() {
        if (totalPaid == null)
            totalPaid = new Integer(0);
        return totalPaid;
    }

    public void setTotalPaid(Integer totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Integer getBreakfastPrice() {
        return breakfastPrice;
    }

    public void setBreakfastPrice(Integer breakfastPrice) {
        this.breakfastPrice = breakfastPrice;
    }

    public Integer getLunchPrice() {
        return lunchPrice;
    }

    public void setLunchPrice(Integer lunchPrice) {
        this.lunchPrice = lunchPrice;
    }

    public Integer getDinnerPrice() {
        return dinnerPrice;
    }

    public void setDinnerPrice(Integer dinnerPrice) {
        this.dinnerPrice = dinnerPrice;
    }

    public Integer getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(Integer roomPrice) {
        this.roomPrice = roomPrice;
    }
}


