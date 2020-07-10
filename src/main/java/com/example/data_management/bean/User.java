package com.example.data_management.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {
    @Id
    @Column(name="userId")
    private int userId;
    @Column(name="userName")
    private String userName;
    @Column(name="userPassword")
    private String userPassword;
    @Column(name="userIdentity")
    private String userIdentity;

    public User(){

    }
    public User(int userId, String userName, String userPassword, String userIdentity) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userIdentity = userIdentity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
    }
}

