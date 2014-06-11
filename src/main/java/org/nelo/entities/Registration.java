package org.nelo.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "REGISTRATION")
@NamedQueries(value = {
        @NamedQuery(name = Registration.TOTAL_PAID_REGISTRATION_TOTAL, query = Registration.TOTAL_PAID_REGISTRATION_TOTAL_QUERY),
        @NamedQuery(name = Registration.TOTAL_PAID_REGISTRATION_ROOMS, query = Registration.TOTAL_PAID_REGISTRATION_ROOMS_QUERY),
        @NamedQuery(name = Registration.TOTAL_PAID_REGISTRATION_BREAKFAST, query = Registration.TOTAL_PAID_REGISTRATION_BREAKFAST_QUERY),
        @NamedQuery(name = Registration.TOTAL_PAID_REGISTRATION_LUNCH, query = Registration.TOTAL_PAID_REGISTRATION_LUNCH_QUERY),
        @NamedQuery(name = Registration.TOTAL_PAID_REGISTRATION_DINNER, query = Registration.TOTAL_PAID_REGISTRATION_DINNER_QUERY),
        @NamedQuery(name = Registration.TOTAL_PAID_REGISTRATION_USERS, query = Registration.TOTAL_PAID_REGISTRATION_USERS_QUERY)
})
public class Registration {

    public static final String TOTAL_PAID_REGISTRATION_TOTAL = "TOTAL_PAID_REGISTRATION_TOTAL";

    public static final String TOTAL_PAID_REGISTRATION_ROOMS = "TOTAL_PAID_REGISTRATION_ROOMS";

    public static final String TOTAL_PAID_REGISTRATION_BREAKFAST = "TOTAL_PAID_REGISTRATION_BREAKFAST";

    public static final String TOTAL_PAID_REGISTRATION_LUNCH = "TOTAL_PAID_REGISTRATION_LUNCH";

    public static final String TOTAL_PAID_REGISTRATION_DINNER = "TOTAL_PAID_REGISTRATION_DINNER";

    public static final String TOTAL_PAID_REGISTRATION_USERS = "TOTAL_PAID_REGISTRATION_USERS";

    static final String TOTAL_PAID_REGISTRATION_TOTAL_QUERY = "select sum(totalPaid) from Registration";

    static final String TOTAL_PAID_REGISTRATION_ROOMS_QUERY = "select sum(room.price) from Registration as registration join registration.room as room";

    static final String TOTAL_PAID_REGISTRATION_BREAKFAST_QUERY = "select sum(breakfastPrice) from Registration where breakfast = true";

    static final String TOTAL_PAID_REGISTRATION_LUNCH_QUERY = "select sum(lunchPrice) from Registration where lunch = true";

    static final String TOTAL_PAID_REGISTRATION_DINNER_QUERY = "select sum(dinnerPrice) from Registration where dinner = true";

    static final String TOTAL_PAID_REGISTRATION_USERS_QUERY = "select sum(registration.totalPaid),userAccount from Registration as registration join registration.userAccount as userAccount group by userAccount.userId order by sum(registration.totalPaid) desc";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int registrationId = -1;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @OneToOne
    @JoinColumn(name = "roomId")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserAccount userAccount;

    @OneToOne
    @JoinColumn(name = "reservationId")
    private Reservation reservation;

    @Column
    private Boolean breakfast;

    @Column
    private Integer breakfastPrice;

    @Column
    private Boolean lunch;

    @Column
    private Integer lunchPrice;

    @Column
    private Boolean dinner;

    @Column
    private Integer dinnerPrice;

    @Column
    private Integer totalPaid;

    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
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
}
