package org.nelo.entities;

import org.nelo.entities.enums.Roles;

import javax.persistence.*;

@Entity
@Table(name = "USER_ACCOUNT")
public class UserAccount {

    @Id
    @Column(name = "UID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ACCOUNT_SEQ")
    @SequenceGenerator(name = "USER_ACCOUNT_SEQ", sequenceName = "USER_ACCOUNT_SEQ")
    private int userId = -1;

    @Column(name = "USER_PASSWORD", nullable = false)
    private String userPassword;

    @Column(name = "USER_ROLE", nullable = false)
    private String userRole;

    @Column
    private String description;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column
    private String phoneNumber;

    @Column
    private String identityCardNo;

    @Column
    private String identityCardEndDate;

    @Column
    private String nationality;

    @Column
    private String address;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Roles getRole() {
        for (Roles value : Roles._values) {
            if (userRole.equals(value.getType()))
                return value;
        }
        return null;
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
}

