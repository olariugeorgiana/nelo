package org.nelo.entities;

import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int customerId = -1;

    @Column
    private String phonoNo;
    @Column
    private String identityCardNo;
    @Column
    private String identityCardEndDate;
    @Column
    private String nationality;
    @Column
    private String address;

    @OneToOne
    @JoinColumn(name = "userId")
    private UserAccount userAccount;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getPhonoNo() {
        return phonoNo;
    }

    public void setPhonoNo(String phonoNo) {
        this.phonoNo = phonoNo;
    }

    public String getIdentityCardNo() {
        return identityCardNo;
    }

    public void setIdentityCardNo(String identityCardNo) {
        this.identityCardNo = identityCardNo;
    }

    public String getIdentityCardEndDate() {
        return identityCardEndDate;
    }

    public void setIdentityCardEndDate(String identityCardEndDate) {
        this.identityCardEndDate = identityCardEndDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserAccount getUserAccount() {

        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
